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

    private final CsvUserRecordReader csvUserRecordReader = new CsvUserRecordReader();
    private final BmiCalculator bmiCalculator = new BmiCalculator();
    private final BmiClassifier bmiClassifier = new BmiClassifier();

    private final List<UserRecord> loadedUserRecords = new ArrayList<>();
    private AgeGroupStatisticsService ageGroupStatisticsService;
    private OverallBmiStatisticsService overallBmiStatisticsService;

    public int calculateBmi(String filename) {
        resetPipelineState();
        if (!loadUserRecordsFromFile(filename)) {
            return 0;
        }
        imputeMissingWeightsAndHeights();
        computeBmisAndClassify();
        buildStatisticsServices();
        return loadedUserRecords.size();
    }

    public double getBmiRatio(int ageGroupStart, int bmiCategoryType) {
        if (ageGroupStatisticsService == null) {
            return 0.0;
        }
        return ageGroupStatisticsService.getRatio(ageGroupStart, bmiCategoryType);
    }

    /** 전체 사용자 대비 BMI 범주 비율(%) — getBmiRatio(나이대별)와 구분. */
    public double getOverallBmiRatio(int bmiCategoryType) {
        if (overallBmiStatisticsService == null) {
            return 0.0;
        }
        return overallBmiStatisticsService.getOverallRatio(bmiCategoryType);
    }

    /** 18.5 < BMI < 23 정상 범위 사용자 ID 목록 (calculateBmi 실행 후). */
    public List<String> getNormalBmiUserIds() {
        if (loadedUserRecords.isEmpty()) {
            return Collections.emptyList();
        }
        return loadedUserRecords.stream()
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

    private void resetPipelineState() {
        loadedUserRecords.clear();
        ageGroupStatisticsService = null;
        overallBmiStatisticsService = null;
    }

    private boolean loadUserRecordsFromFile(String filename) {
        try {
            loadedUserRecords.addAll(csvUserRecordReader.read(filename));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void imputeMissingWeightsAndHeights() {
        MissingValueImputationService.imputeZeroValuesByAgeGroup(
                loadedUserRecords, UserRecordMutableField.WEIGHT);
        MissingValueImputationService.imputeZeroValuesByAgeGroup(
                loadedUserRecords, UserRecordMutableField.HEIGHT);
    }

    private void buildStatisticsServices() {
        ageGroupStatisticsService = new AgeGroupStatisticsService(loadedUserRecords);
        overallBmiStatisticsService = new OverallBmiStatisticsService(loadedUserRecords);
    }

    private void computeBmisAndClassify() {
        for (UserRecord userRecord : loadedUserRecords) {
            double bmi = bmiCalculator.computeFromKgAndCm(userRecord.getWeight(), userRecord.getHeight());
            userRecord.setBmi(bmi);
            userRecord.setCategory(bmiClassifier.classify(bmi));
        }
    }
}
