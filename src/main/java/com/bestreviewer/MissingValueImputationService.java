package com.bestreviewer;

import java.util.List;
import java.util.function.ToDoubleFunction;

/** 동일 나이대 비-zero 값 평균으로 0 누락 보정 (체중·키 공통). */
final class MissingValueImputationService {

    private MissingValueImputationService() {}

    static void imputeZeroValuesByAgeGroup(
            List<UserRecord> users, ToDoubleFunction<UserRecord> valueExtractor) {
        for (int ageGroupStart : HealthConstants.AGE_GROUP_STARTS) {
            double nonZeroSum = 0;
            int nonZeroCount = 0;
            for (UserRecord user : users) {
                double value = valueExtractor.applyAsDouble(user);
                if (AgeGroupHelper.belongsToAgeGroup(user.getAge(), ageGroupStart) && value != 0.0) {
                    nonZeroSum += value;
                    nonZeroCount++;
                }
            }
            if (nonZeroCount == 0) {
                continue;
            }
            double average = nonZeroSum / nonZeroCount;
            for (UserRecord user : users) {
                if (AgeGroupHelper.belongsToAgeGroup(user.getAge(), ageGroupStart)
                        && valueExtractor.applyAsDouble(user) == 0.0) {
                    applyImputedValue(user, valueExtractor, average);
                }
            }
        }
    }

    private static void applyImputedValue(
            UserRecord user, ToDoubleFunction<UserRecord> valueExtractor, double average) {
        if (valueExtractor == UserRecordWeightAccessor.INSTANCE) {
            user.setWeight(average);
        } else if (valueExtractor == UserRecordHeightAccessor.INSTANCE) {
            user.setHeight(average);
        }
    }

    private enum UserRecordWeightAccessor implements ToDoubleFunction<UserRecord> {
        INSTANCE;

        @Override
        public double applyAsDouble(UserRecord user) {
            return user.getWeight();
        }
    }

    private enum UserRecordHeightAccessor implements ToDoubleFunction<UserRecord> {
        INSTANCE;

        @Override
        public double applyAsDouble(UserRecord user) {
            return user.getHeight();
        }
    }

    static ToDoubleFunction<UserRecord> weightExtractor() {
        return UserRecordWeightAccessor.INSTANCE;
    }

    static ToDoubleFunction<UserRecord> heightExtractor() {
        return UserRecordHeightAccessor.INSTANCE;
    }
}
