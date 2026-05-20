package com.bestreviewer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SHealth {

    private static final int MAX_USERS = 10000;
    private static final int[] AGE_GROUP_STARTS = {20, 30, 40, 50, 60, 70};
    private static final int AGE_GROUP_WIDTH = 10;
    private static final double CM_PER_METER = 100.0;
    private static final double PERCENT_MULTIPLIER = 100.0;

    private static final double BMI_UNDERWEIGHT_MAX = 18.5;
    private static final double BMI_NORMAL_MAX = 23.0;
    private static final double BMI_OVERWEIGHT_MAX = 25.0;

    static final int TYPE_UNDERWEIGHT = 100;
    static final int TYPE_NORMAL = 200;
    static final int TYPE_OVERWEIGHT = 300;
    static final int TYPE_OBESITY = 400;

    private static final int CATEGORY_UNDERWEIGHT = 0;
    private static final int CATEGORY_NORMAL = 1;
    private static final int CATEGORY_OVERWEIGHT = 2;
    private static final int CATEGORY_OBESITY = 3;

    private int userCount;
    private final int[] ages = new int[MAX_USERS];
    private final double[] heights = new double[MAX_USERS];
    private final double[] weights = new double[MAX_USERS];
    private final double[] bmis = new double[MAX_USERS];

    /** [나이대 인덱스 0=20대..5=70대][분류 0=저체중..3=비만] 비율(%) */
    private final double[][] ratioPercentByAgeGroup = new double[AGE_GROUP_STARTS.length][4];

    public int calculateBmi(String filename) {
        userCount = 0;
        readUsersFromFile(filename);
        imputeMissingWeightsByAgeGroup();
        computeBmisForAllUsers();
        aggregateRatiosByAgeGroup();
        return userCount;
    }

    public double getBmiRatio(int ageGroupStart, int bmiCategoryType) {
        int ageGroupIndex = indexOfAgeGroupStart(ageGroupStart);
        int categoryIndex = indexOfCategoryType(bmiCategoryType);
        if (ageGroupIndex < 0 || categoryIndex < 0) {
            return 0.0;
        }
        return ratioPercentByAgeGroup[ageGroupIndex][categoryIndex];
    }

    private void readUsersFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> tokens = splitCsvLine(line, ',');
                if (tokens.isEmpty()) {
                    break;
                }
                ages[userCount] = Integer.parseInt(tokens.get(1));
                weights[userCount] = Double.parseDouble(tokens.get(2));
                heights[userCount] = Double.parseDouble(tokens.get(3));
                userCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void imputeMissingWeightsByAgeGroup() {
        for (int ageGroupStart : AGE_GROUP_STARTS) {
            double nonZeroWeightSum = 0;
            int nonZeroWeightCount = 0;
            for (int i = 0; i < userCount; i++) {
                if (belongsToAgeGroup(ages[i], ageGroupStart) && weights[i] != 0.0) {
                    nonZeroWeightSum += weights[i];
                    nonZeroWeightCount++;
                }
            }
            for (int i = 0; i < userCount; i++) {
                if (belongsToAgeGroup(ages[i], ageGroupStart) && weights[i] == 0.0) {
                    weights[i] = nonZeroWeightSum / nonZeroWeightCount;
                }
            }
        }
    }

    private void computeBmisForAllUsers() {
        for (int i = 0; i < userCount; i++) {
            bmis[i] = computeBmiFromKgAndCm(weights[i], heights[i]);
        }
    }

    /** TS-01 단위 테스트용 (동일 패키지). */
    double computeBmiFromKgAndCm(double weightKg, double heightCm) {
        double heightMeters = heightCm / CM_PER_METER;
        return weightKg / (heightMeters * heightMeters);
    }

    private void aggregateRatiosByAgeGroup() {
        for (int ageGroupIndex = 0; ageGroupIndex < AGE_GROUP_STARTS.length; ageGroupIndex++) {
            int ageGroupStart = AGE_GROUP_STARTS[ageGroupIndex];
            int[] categoryCounts = new int[4];
            int usersInAgeGroup = 0;

            for (int i = 0; i < userCount; i++) {
                if (!belongsToAgeGroup(ages[i], ageGroupStart)) {
                    continue;
                }
                usersInAgeGroup++;
                int categoryIndex = classifyBmiCategoryIndex(bmis[i]);
                if (categoryIndex >= 0) {
                    categoryCounts[categoryIndex]++;
                }
            }

            storeRatioPercentages(ageGroupIndex, categoryCounts, usersInAgeGroup);
        }
    }

    private void storeRatioPercentages(int ageGroupIndex, int[] categoryCounts, int usersInAgeGroup) {
        ratioPercentByAgeGroup[ageGroupIndex][CATEGORY_UNDERWEIGHT] =
                (double) categoryCounts[CATEGORY_UNDERWEIGHT] * PERCENT_MULTIPLIER / usersInAgeGroup;
        ratioPercentByAgeGroup[ageGroupIndex][CATEGORY_NORMAL] =
                (double) categoryCounts[CATEGORY_NORMAL] * PERCENT_MULTIPLIER / usersInAgeGroup;
        ratioPercentByAgeGroup[ageGroupIndex][CATEGORY_OVERWEIGHT] =
                (double) categoryCounts[CATEGORY_OVERWEIGHT] * PERCENT_MULTIPLIER / usersInAgeGroup;
        ratioPercentByAgeGroup[ageGroupIndex][CATEGORY_OBESITY] =
                (double) categoryCounts[CATEGORY_OBESITY] * PERCENT_MULTIPLIER / usersInAgeGroup;
    }

    /**
     * 레거시 분류 조건 유지 (BMI=25는 어느 분류에도 포함되지 않음).
     * TS-03 단위 테스트용 (동일 패키지).
     */
    int classifyBmiCategoryIndex(double bmi) {
        if (bmi <= BMI_UNDERWEIGHT_MAX) {
            return CATEGORY_UNDERWEIGHT;
        }
        if (bmi > BMI_UNDERWEIGHT_MAX && bmi < BMI_NORMAL_MAX) {
            return CATEGORY_NORMAL;
        }
        if (bmi >= BMI_NORMAL_MAX && bmi < BMI_OVERWEIGHT_MAX) {
            return CATEGORY_OVERWEIGHT;
        }
        if (bmi > BMI_OVERWEIGHT_MAX) {
            return CATEGORY_OBESITY;
        }
        return -1;
    }

    /** TS-04 단위 테스트용 (동일 패키지). */
    boolean belongsToAgeGroup(int age, int ageGroupStart) {
        return age >= ageGroupStart && age < ageGroupStart + AGE_GROUP_WIDTH;
    }

    private int indexOfAgeGroupStart(int ageGroupStart) {
        for (int i = 0; i < AGE_GROUP_STARTS.length; i++) {
            if (AGE_GROUP_STARTS[i] == ageGroupStart) {
                return i;
            }
        }
        return -1;
    }

    private int indexOfCategoryType(int bmiCategoryType) {
        switch (bmiCategoryType) {
            case TYPE_UNDERWEIGHT:
                return CATEGORY_UNDERWEIGHT;
            case TYPE_NORMAL:
                return CATEGORY_NORMAL;
            case TYPE_OVERWEIGHT:
                return CATEGORY_OVERWEIGHT;
            case TYPE_OBESITY:
                return CATEGORY_OBESITY;
            default:
                return -1;
        }
    }

    private List<String> splitCsvLine(String line, char delimiter) {
        List<String> tokens = new ArrayList<>();
        int start = 0;
        int end = line.indexOf(delimiter);
        while (end != -1) {
            tokens.add(line.substring(start, end));
            start = end + 1;
            end = line.indexOf(delimiter, start);
        }
        tokens.add(line.substring(start));
        return tokens;
    }
}
