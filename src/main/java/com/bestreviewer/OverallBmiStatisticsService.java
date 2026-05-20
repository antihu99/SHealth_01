package com.bestreviewer;

import java.util.List;

/** 전체 사용자 기준 BMI 4분류 비율(%). */
final class OverallBmiStatisticsService {

    private final double[] ratioPercentOverall = new double[BmiCategoryStatisticsHelper.CATEGORY_COUNT];

    OverallBmiStatisticsService(List<UserRecord> userRecords) {
        int totalUserCount = userRecords.size();
        int[] categoryCounts = BmiCategoryStatisticsHelper.countCategories(userRecords);
        BmiCategoryStatisticsHelper.fillRatioPercentages(ratioPercentOverall, categoryCounts, totalUserCount);
    }

    double getOverallRatio(int bmiCategoryType) {
        BmiCategory bmiCategory = BmiCategory.fromLegacyTypeCode(bmiCategoryType);
        if (bmiCategory == null) {
            return 0.0;
        }
        return ratioPercentOverall[bmiCategory.getCategoryIndex()];
    }
}
