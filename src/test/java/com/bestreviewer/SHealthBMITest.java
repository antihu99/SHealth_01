package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 2단계 리팩토링 회귀 스모크 — shealth.dat 기준 getBmiRatio 값 동일성 검증.
 * (3단계에서 BMI·보정·분류·예외 TC로 확장 예정)
 */
public class SHealthBMITest {

  private static final double DELTA = 1e-4;

  @Test
  @DisplayName("shealth.dat 처리 후 나이대별 BMI 비율이 리팩토링 전과 동일하다")
  void getBmiRatio_matchesLegacyBaseline_afterRefactoring() {
    SHealth shealth = new SHealth();
    shealth.calculateBmi("shealth.dat");

    assertAgeGroupRatios(shealth, 20, 3.511053, 23.797139, 11.833550, 60.858257);
    assertAgeGroupRatios(shealth, 30, 1.863354, 15.527950, 10.062112, 72.546584);
    assertAgeGroupRatios(shealth, 40, 0.521512, 10.039113, 9.126467, 80.312907);
    assertAgeGroupRatios(shealth, 50, 2.181401, 12.629162, 9.988519, 75.200918);
    assertAgeGroupRatios(shealth, 60, 0.862895, 8.533078, 10.642378, 79.961649);
    assertAgeGroupRatios(shealth, 70, 0.529101, 12.345679, 10.758377, 76.366843);
  }

  private void assertAgeGroupRatios(
      SHealth shealth,
      int ageGroupStart,
      double expectedUnderweight,
      double expectedNormal,
      double expectedOverweight,
      double expectedObesity) {
    assertEquals(
        expectedUnderweight,
        shealth.getBmiRatio(ageGroupStart, SHealth.TYPE_UNDERWEIGHT),
        DELTA);
    assertEquals(
        expectedNormal, shealth.getBmiRatio(ageGroupStart, SHealth.TYPE_NORMAL), DELTA);
    assertEquals(
        expectedOverweight,
        shealth.getBmiRatio(ageGroupStart, SHealth.TYPE_OVERWEIGHT),
        DELTA);
    assertEquals(
        expectedObesity, shealth.getBmiRatio(ageGroupStart, SHealth.TYPE_OBESITY), DELTA);
  }
}
