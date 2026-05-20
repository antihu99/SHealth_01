package com.bestreviewer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** SHealth BMI 파사드 — 기존 public API 유지 + 4단계 확장 API. */
public class SHealth {

    static final int TYPE_UNDERWEIGHT = HealthConstants.TYPE_UNDERWEIGHT;
    static final int TYPE_NORMAL = HealthConstants.TYPE_NORMAL;
    static final int TYPE_OVERWEIGHT = HealthConstants.TYPE_OVERWEIGHT;
    static final int TYPE_OBESITY = HealthConstants.TYPE_OBESITY;

    private final CsvUserRecordReader csvReader = new CsvUserRecordReader();
    private final BmiCalculator bmiCalculator = new BmiCalculator();
    private final BmiClassifier bmiClassifier = new BmiClassifier();

    private final List<UserRecord> users = new ArrayList<>();
    private AgeGroupStatisticsService ageGroupStatistics;
    private OverallBmiStatisticsService overallStatistics;

    public int calculateBmi(String filename) {
        users.clear();
        ageGroupStatistics = null;
        overallStatistics = null;
        try {
            users.addAll(csvReader.read(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        MissingValueImputationService.imputeZeroValuesByAgeGroup(
                users, MissingValueImputationService.weightExtractor());
        MissingValueImputationService.imputeZeroValuesByAgeGroup(
                users, MissingValueImputationService.heightExtractor());

        computeBmisAndClassify();
        ageGroupStatistics = new AgeGroupStatisticsService(users);
        overallStatistics = new OverallBmiStatisticsService(users);
        return users.size();
    }

    public double getBmiRatio(int ageGroupStart, int bmiCategoryType) {
        if (ageGroupStatistics == null) {
            return 0.0;
        }
        return ageGroupStatistics.getRatio(ageGroupStart, bmiCategoryType);
    }

    /** 전체 사용자 대비 BMI 범주 비율(%) — getBmiRatio(나이대별)와 구분. */
    public double getOverallBmiRatio(int bmiCategoryType) {
        if (overallStatistics == null) {
            return 0.0;
        }
        return overallStatistics.getOverallRatio(bmiCategoryType);
    }

    /** 18.5 < BMI < 23 정상 범위 사용자 ID 목록 (calculateBmi 실행 후). */
    public List<String> getNormalBmiUserIds() {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        return users.stream()
                .filter(UserRecord::isNormalBmiRange)
                .map(UserRecord::getId)
                .collect(Collectors.toList());
    }

    /** TS-01 단위 테스트용 (동일 패키지). */
    double computeBmiFromKgAndCm(double weightKg, double heightCm) {
        return bmiCalculator.computeFromKgAndCm(weightKg, heightCm);
    }

    /** TS-03 단위 테스트용 (동일 패키지). */
    int classifyBmiCategoryIndex(double bmi) {
        return bmiClassifier.classifyCategoryIndex(bmi);
    }

    /** TS-04 단위 테스트용 (동일 패키지). */
    boolean belongsToAgeGroup(int age, int ageGroupStart) {
        return AgeGroupHelper.belongsToAgeGroup(age, ageGroupStart);
    }

    private void computeBmisAndClassify() {
        for (UserRecord user : users) {
            double bmi = bmiCalculator.computeFromKgAndCm(user.getWeight(), user.getHeight());
            user.setBmi(bmi);
            user.setCategory(bmiClassifier.classify(bmi));
        }
    }
}
