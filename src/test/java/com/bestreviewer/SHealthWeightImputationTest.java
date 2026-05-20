package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** TS-02 체중 보정(통합), TS-04 나이대 경계. */
class SHealthWeightImputationTest {

  private static final double DELTA = 1e-3;

  @ParameterizedTest(name = "age={0} in 20s → {2}")
  @CsvSource({
    "19, 20, false",
    "20, 20, true",
    "29, 20, true",
    "30, 20, false",
    "29, 30, false",
    "30, 30, true",
    "69, 60, true",
    "70, 60, false",
    "70, 70, true",
    "79, 70, true"
  })
  @DisplayName("나이대 구간 [a,a+10) 경계가 요구사항과 일치한다")
  void belongsToAgeGroup_ageBoundaries(int age, int ageGroupStart, boolean expected) {
    SHealth shealth = new SHealth();
    assertEquals(expected, shealth.belongsToAgeGroup(age, ageGroupStart));
  }

  @Test
  @DisplayName("체중 0 사용자에게 동일 20대 평균 체중(70kg)이 적용되어 과체중 비율이 산출된다")
  void calculateBmi_imputesZeroWeightWithAgeGroupAverage() {
    // Given — 60·80·0 → 평균 70, 3명 모두 20대
    SHealth shealth = new SHealth();
    String fixture = TestFixtures.path("impute_20s_three.csv");

    // When
    int count = shealth.calculateBmi(fixture);

    // Then — 3명: 정상 1, 과체중 1, 비만 1 → 각 33.33%
    assertEquals(3, count);
    assertEquals(0.0, shealth.getBmiRatio(20, SHealth.TYPE_UNDERWEIGHT), DELTA);
    assertEquals(33.3333, shealth.getBmiRatio(20, SHealth.TYPE_NORMAL), DELTA);
    assertEquals(33.3333, shealth.getBmiRatio(20, SHealth.TYPE_OVERWEIGHT), DELTA);
    assertEquals(33.3333, shealth.getBmiRatio(20, SHealth.TYPE_OBESITY), DELTA);
  }

  @Test
  @DisplayName("19세 사용자는 20대 집계에 포함되지 않는다")
  void belongsToAgeGroup_age19NotInTwenties() {
    SHealth shealth = new SHealth();
    assertFalse(shealth.belongsToAgeGroup(19, 20));
    assertTrue(shealth.belongsToAgeGroup(20, 20));
  }
}
