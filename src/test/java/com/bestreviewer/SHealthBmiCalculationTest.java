package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/** TS-01 BMI 계산 (cm→m 변환). */
class SHealthBmiCalculationTest {

  private static final double DELTA = 1e-4;

  @Test
  @DisplayName("README 예시 79.5kg·158.3cm BMI가 공식과 일치한다")
  void computeBmi_readmeExample() {
    // Given
    SHealth shealth = new SHealth();
    double weightKg = 79.5;
    double heightCm = 158.3;

    // When
    double bmi = shealth.computeBmiFromKgAndCm(weightKg, heightCm);

    // Then — 79.5 / (1.583)^2
    assertEquals(31.7253, bmi, DELTA);
  }

  @ParameterizedTest(name = "weight={0}kg height={1}cm → BMI={2}")
  @CsvSource({
    "70, 170, 24.2215",
    "50, 160, 19.5313",
    "90, 180, 27.7778"
  })
  @DisplayName("Parameterized: kg·cm 입력 시 BMI가 기대값과 일치한다")
  void computeBmi_parameterized(double weightKg, double heightCm, double expectedBmi) {
    SHealth shealth = new SHealth();
    assertEquals(expectedBmi, shealth.computeBmiFromKgAndCm(weightKg, heightCm), DELTA);
  }

  @Test
  @DisplayName("cm를 m로 변환하지 않으면 BMI가 달라진다 (변환 검증)")
  void computeBmi_usesMeterConversion() {
    SHealth shealth = new SHealth();
    double withConversion = shealth.computeBmiFromKgAndCm(70, 170);
    double wrongIfCmAsM = 70 / (170 * 170);
    assertEquals(24.2215, withConversion, DELTA);
    assertNotEquals(wrongIfCmAsM, withConversion, 0.01);
  }
}
