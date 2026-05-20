package com.bestreviewer;

import java.util.List;

/** 나이대별 BMI 분류 비율(%) 집계. */
final class AgeGroupStatisticsService {

    private final double[][] ratioPercentByAgeGroup =
            new double[HealthConstants.AGE_GROUP_STARTS.length][4];

    AgeGroupStatisticsService(List<UserRecord> users) {
        aggregate(users);
    }

    double getRatio(int ageGroupStart, int bmiCategoryType) {
        int ageGroupIndex = AgeGroupHelper.indexOfAgeGroupStart(ageGroupStart);
        BmiCategory category = BmiCategory.fromLegacyTypeCode(bmiCategoryType);
        if (ageGroupIndex < 0 || category == null) {
            return 0.0;
        }
        return ratioPercentByAgeGroup[ageGroupIndex][category.getCategoryIndex()];
    }

    private void aggregate(List<UserRecord> users) {
        for (int ageGroupIndex = 0; ageGroupIndex < HealthConstants.AGE_GROUP_STARTS.length; ageGroupIndex++) {
            int ageGroupStart = HealthConstants.AGE_GROUP_STARTS[ageGroupIndex];
            int[] categoryCounts = new int[4];
            int usersInAgeGroup = 0;

            for (UserRecord user : users) {
                if (!AgeGroupHelper.belongsToAgeGroup(user.getAge(), ageGroupStart)) {
                    continue;
                }
                usersInAgeGroup++;
                BmiCategory category = user.getCategory();
                if (category != null) {
                    categoryCounts[category.getCategoryIndex()]++;
                }
            }
            storeRatioPercentages(ageGroupIndex, categoryCounts, usersInAgeGroup);
        }
    }

    private void storeRatioPercentages(int ageGroupIndex, int[] categoryCounts, int usersInAgeGroup) {
        if (usersInAgeGroup == 0) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            ratioPercentByAgeGroup[ageGroupIndex][i] =
                    (double) categoryCounts[i] * HealthConstants.PERCENT_MULTIPLIER / usersInAgeGroup;
        }
    }
}
