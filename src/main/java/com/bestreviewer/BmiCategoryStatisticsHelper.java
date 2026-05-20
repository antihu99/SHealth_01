package com.bestreviewer;

import java.util.List;

/** BMI 분류 건수·비율(%) 계산 공통 로직 (나이대별·전체 집계 DRY). */
final class BmiCategoryStatisticsHelper {

    static final int CATEGORY_COUNT = BmiCategory.values().length;

    private BmiCategoryStatisticsHelper() {}

    static int[] countCategories(List<UserRecord> userRecords) {
        int[] categoryCounts = new int[CATEGORY_COUNT];
        for (UserRecord userRecord : userRecords) {
            incrementCategoryCount(categoryCounts, userRecord.getCategory());
        }
        return categoryCounts;
    }

    static AgeGroupCategoryCounts countCategoriesInAgeGroup(List<UserRecord> userRecords, int ageGroupStart) {
        int[] categoryCounts = new int[CATEGORY_COUNT];
        int usersInAgeGroup = 0;
        for (UserRecord userRecord : userRecords) {
            if (!AgeGroupHelper.belongsToAgeGroup(userRecord.getAge(), ageGroupStart)) {
                continue;
            }
            usersInAgeGroup++;
            incrementCategoryCount(categoryCounts, userRecord.getCategory());
        }
        return new AgeGroupCategoryCounts(categoryCounts, usersInAgeGroup);
    }

    static void fillRatioPercentages(double[] ratioPercentages, int[] categoryCounts, int divisor) {
        if (divisor == 0) {
            return;
        }
        for (int categoryIndex = 0; categoryIndex < CATEGORY_COUNT; categoryIndex++) {
            ratioPercentages[categoryIndex] =
                    (double) categoryCounts[categoryIndex] * HealthConstants.PERCENT_MULTIPLIER / divisor;
        }
    }

    private static void incrementCategoryCount(int[] categoryCounts, BmiCategory category) {
        if (category != null) {
            categoryCounts[category.getCategoryIndex()]++;
        }
    }

    static final class AgeGroupCategoryCounts {
        private final int[] categoryCounts;
        private final int usersInAgeGroup;

        AgeGroupCategoryCounts(int[] categoryCounts, int usersInAgeGroup) {
            this.categoryCounts = categoryCounts;
            this.usersInAgeGroup = usersInAgeGroup;
        }

        int[] getCategoryCounts() {
            return categoryCounts;
        }

        int getUsersInAgeGroup() {
            return usersInAgeGroup;
        }
    }
}
