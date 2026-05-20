package com.bestreviewer;

/** SHealthRequirements.txt §5.3 분류 규칙. */
final class BmiClassifier {

    int classifyCategoryIndex(double bmi) {
        BmiCategory category = classify(bmi);
        return category != null ? category.getCategoryIndex() : -1;
    }

    BmiCategory classify(double bmi) {
        if (Double.isNaN(bmi) || Double.isInfinite(bmi)) {
            return null;
        }
        if (bmi <= HealthConstants.BMI_UNDERWEIGHT_MAX) {
            return BmiCategory.UNDERWEIGHT;
        }
        if (bmi < HealthConstants.BMI_NORMAL_MAX) {
            return BmiCategory.NORMAL;
        }
        if (bmi < HealthConstants.BMI_OVERWEIGHT_MAX) {
            return BmiCategory.OVERWEIGHT;
        }
        return BmiCategory.OBESITY;
    }
}
