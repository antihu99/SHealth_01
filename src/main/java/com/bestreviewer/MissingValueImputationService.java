package com.bestreviewer;

import java.util.List;

/** 동일 나이대 비-zero 값 평균으로 0 누락 보정 (체중·키 공통). */
final class MissingValueImputationService {

    private MissingValueImputationService() {}

    static void imputeZeroValuesByAgeGroup(List<UserRecord> userRecords, UserRecordMutableField field) {
        for (int ageGroupStart : HealthConstants.AGE_GROUP_STARTS) {
            double nonZeroAverage = computeNonZeroAverageInAgeGroup(userRecords, ageGroupStart, field);
            if (Double.isNaN(nonZeroAverage)) {
                continue;
            }
            imputeMissingValuesInAgeGroup(userRecords, ageGroupStart, field, nonZeroAverage);
        }
    }

    private static double computeNonZeroAverageInAgeGroup(
            List<UserRecord> userRecords, int ageGroupStart, UserRecordMutableField field) {
        double nonZeroSum = 0;
        int nonZeroCount = 0;
        for (UserRecord userRecord : userRecords) {
            double value = field.getValue(userRecord);
            if (AgeGroupHelper.belongsToAgeGroup(userRecord.getAge(), ageGroupStart) && !field.isMissing(value)) {
                nonZeroSum += value;
                nonZeroCount++;
            }
        }
        if (nonZeroCount == 0) {
            return Double.NaN;
        }
        return nonZeroSum / nonZeroCount;
    }

    private static void imputeMissingValuesInAgeGroup(
            List<UserRecord> userRecords,
            int ageGroupStart,
            UserRecordMutableField field,
            double imputedAverage) {
        for (UserRecord userRecord : userRecords) {
            if (AgeGroupHelper.belongsToAgeGroup(userRecord.getAge(), ageGroupStart)
                    && field.isMissing(field.getValue(userRecord))) {
                field.setValue(userRecord, imputedAverage);
            }
        }
    }
}
