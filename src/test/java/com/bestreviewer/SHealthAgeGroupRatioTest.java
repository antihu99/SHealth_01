package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** TS-05 나이대별 비율·getBmiRatio 통합. */
class SHealthAgeGroupRatioTest {

  private static final double DELTA = 1e-3;

  @Test
  @DisplayName("20대 1명 정상체중이면 정상 비율 100%이다")
  void getBmiRatio_singleNormalUserInTwenties() {
    SHealth shealth = new SHealth();
    shealth.calculateBmi(TestFixtures.path("single_normal_20s.csv"));

    assertEquals(0.0, shealth.getBmiRatio(20, SHealth.TYPE_UNDERWEIGHT), DELTA);
    assertEquals(100.0, shealth.getBmiRatio(20, SHealth.TYPE_NORMAL), DELTA);
    assertEquals(0.0, shealth.getBmiRatio(20, SHealth.TYPE_OVERWEIGHT), DELTA);
    assertEquals(0.0, shealth.getBmiRatio(20, SHealth.TYPE_OBESITY), DELTA);
  }

  @Test
  @DisplayName("빈 나이대(30대 무인원) 조회 시 0으로 나누기로 NaN이 반환된다 (현재 구현)")
  void getBmiRatio_emptyAgeGroup_returnsNaN() {
    SHealth shealth = new SHealth();
    shealth.calculateBmi(TestFixtures.path("single_normal_20s.csv"));

    assertTrue(Double.isNaN(shealth.getBmiRatio(30, SHealth.TYPE_NORMAL)));
  }
}
