package com.bestreviewer;

import java.util.List;

/** 나이대별 BMI 분류 비율(%) 집계. */
final class AgeGroupStatisticsService {

    private final double[][] ratioPercentByAgeGroup =
            new double[HealthConstants.AGE_GROUP_STARTS.length][BmiCategoryStatisticsHelper.CATEGORY_COUNT];

    AgeGroupStatisticsService(List<UserRecord> userRecords) {
        aggregateByAgeGroup(userRecords);
    }

    double getRatio(int ageGroupStart, int bmiCategoryType) {
        int ageGroupIndex = AgeGroupHelper.indexOfAgeGroupStart(ageGroupStart);
        BmiCategory bmiCategory = BmiCategory.fromLegacyTypeCode(bmiCategoryType);
        if (ageGroupIndex < 0 || bmiCategory == null) {
            return 0.0;
        }
        return ratioPercentByAgeGroup[ageGroupIndex][bmiCategory.getCategoryIndex()];
    }

    private void aggregateByAgeGroup(List<UserRecord> userRecords) {
        for (int ageGroupIndex = 0; ageGroupIndex < HealthConstants.AGE_GROUP_STARTS.length; ageGroupIndex++) {
            int ageGroupStart = HealthConstants.AGE_GROUP_STARTS[ageGroupIndex];
            BmiCategoryStatisticsHelper.AgeGroupCategoryCounts counts =
                    BmiCategoryStatisticsHelper.countCategoriesInAgeGroup(userRecords, ageGroupStart);
            BmiCategoryStatisticsHelper.fillRatioPercentages(
                    ratioPercentByAgeGroup[ageGroupIndex],
                    counts.getCategoryCounts(),
                    counts.getUsersInAgeGroup());
        }
    }
}
