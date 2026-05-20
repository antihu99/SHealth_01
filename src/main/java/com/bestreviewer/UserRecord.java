package com.bestreviewer;

/** CSV 사용자 레코드 (id, age, weight, height) + 파이프라인 산출값. */
class UserRecord {

    private final String id;
    private final int age;
    private double weight;
    private double height;
    private double bmi;
    private BmiCategory category;

    UserRecord(String id, int age, double weight, double height) {
        this.id = id;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    String getId() {
        return id;
    }

    int getAge() {
        return age;
    }

    double getWeight() {
        return weight;
    }

    void setWeight(double weight) {
        this.weight = weight;
    }

    double getHeight() {
        return height;
    }

    void setHeight(double height) {
        this.height = height;
    }

    double getBmi() {
        return bmi;
    }

    void setBmi(double bmi) {
        this.bmi = bmi;
    }

    BmiCategory getCategory() {
        return category;
    }

    void setCategory(BmiCategory category) {
        this.category = category;
    }

    boolean isNormalBmiRange() {
        return bmi > HealthConstants.BMI_UNDERWEIGHT_MAX && bmi < HealthConstants.BMI_NORMAL_MAX;
    }
}
