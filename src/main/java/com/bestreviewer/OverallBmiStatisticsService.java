package com.bestreviewer;

import java.util.List;

/** 전체 사용자 기준 BMI 4분류 비율(%). */
final class OverallBmiStatisticsService {

    private final double[] ratioPercentOverall = new double[4];

    OverallBmiStatisticsService(List<UserRecord> users) {
        int[] categoryCounts = new int[4];
        int totalUsers = users.size();
        for (UserRecord user : users) {
            BmiCategory category = user.getCategory();
            if (category != null) {
                categoryCounts[category.getCategoryIndex()]++;
            }
        }
        if (totalUsers == 0) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            ratioPercentOverall[i] =
                    (double) categoryCounts[i] * HealthConstants.PERCENT_MULTIPLIER / totalUsers;
        }
    }

    double getOverallRatio(int bmiCategoryType) {
        BmiCategory category = BmiCategory.fromLegacyTypeCode(bmiCategoryType);
        if (category == null) {
            return 0.0;
        }
        return ratioPercentOverall[category.getCategoryIndex()];
    }
}
