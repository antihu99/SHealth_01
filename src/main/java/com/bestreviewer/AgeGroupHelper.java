package com.bestreviewer;

/** 나이대 구간 [a, a+10) 판별·인덱스 조회. */
final class AgeGroupHelper {

    private AgeGroupHelper() {}

    static boolean belongsToAgeGroup(int age, int ageGroupStart) {
        return age >= ageGroupStart && age < ageGroupStart + HealthConstants.AGE_GROUP_WIDTH;
    }

    static int indexOfAgeGroupStart(int ageGroupStart) {
        for (int i = 0; i < HealthConstants.AGE_GROUP_STARTS.length; i++) {
            if (HealthConstants.AGE_GROUP_STARTS[i] == ageGroupStart) {
                return i;
            }
        }
        return -1;
    }
}
