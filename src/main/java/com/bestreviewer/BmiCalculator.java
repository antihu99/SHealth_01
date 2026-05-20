package com.bestreviewer;

/** BMI = kg / (m)², 키는 cm 입력. */
final class BmiCalculator {

    double computeFromKgAndCm(double weightKg, double heightCm) {
        double heightMeters = heightCm / HealthConstants.CM_PER_METER;
        return weightKg / (heightMeters * heightMeters);
    }
}
