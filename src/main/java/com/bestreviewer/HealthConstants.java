package com.bestreviewer;

/** BMI·나이대·레거시 type 코드 상수. */
final class HealthConstants {

    static final int MAX_USERS = 10000;
    static final int[] AGE_GROUP_STARTS = {20, 30, 40, 50, 60, 70};
    static final int AGE_GROUP_WIDTH = 10;
    static final double CM_PER_METER = 100.0;
    static final double PERCENT_MULTIPLIER = 100.0;

    static final double BMI_UNDERWEIGHT_MAX = 18.5;
    static final double BMI_NORMAL_MAX = 23.0;
    static final double BMI_OVERWEIGHT_MAX = 25.0;

    static final int TYPE_UNDERWEIGHT = 100;
    static final int TYPE_NORMAL = 200;
    static final int TYPE_OVERWEIGHT = 300;
    static final int TYPE_OBESITY = 400;

    private HealthConstants() {}
}
