package com.bestreviewer;

import java.io.IOException;

public class SHealthBMI {

    private static final int[] BMI_CATEGORY_TYPES = {
            HealthConstants.TYPE_UNDERWEIGHT,
            HealthConstants.TYPE_NORMAL,
            HealthConstants.TYPE_OVERWEIGHT,
            HealthConstants.TYPE_OBESITY
    };

    public static void main(String[] args) throws IOException {
        SHealth shealth = new SHealth();
        shealth.calculateBmi("shealth.dat");

        for (int ageGroupStart : HealthConstants.AGE_GROUP_STARTS) {
            printAgeGroupRatios(shealth, ageGroupStart);
        }
    }

    private static void printAgeGroupRatios(SHealth shealth, int ageGroupStart) {
        System.out.printf(
                "%d - underweight = %f, normal = %f, overweight = %f, obesity = %f\n",
                ageGroupStart,
                shealth.getBmiRatio(ageGroupStart, BMI_CATEGORY_TYPES[0]),
                shealth.getBmiRatio(ageGroupStart, BMI_CATEGORY_TYPES[1]),
                shealth.getBmiRatio(ageGroupStart, BMI_CATEGORY_TYPES[2]),
                shealth.getBmiRatio(ageGroupStart, BMI_CATEGORY_TYPES[3]));
    }
}
