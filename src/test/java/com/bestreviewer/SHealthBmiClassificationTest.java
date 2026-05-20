package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** TS-03 BMI 4분류 경계 (레거시 classifyBmiCategoryIndex). */
class SHealthBmiClassificationTest {

  @ParameterizedTest(name = "BMI {0} → category {1}")
  @CsvSource({
    "18.5, 0",
    "18.499, 0",
    "18.501, 1",
    "22.999, 1",
    "23.0, 2",
    "24.999, 2",
    "25.0, 3",
    "25.001, 3",
    "30.0, 3"
  })
  @DisplayName("BMI 경계값·직전·직후 분류가 레거시 규칙과 일치한다")
  void classifyBmiCategoryIndex_boundaryValues(double bmi, int expectedCategory) {
    // Given
    SHealth shealth = new SHealth();

    // When
    int category = shealth.classifyBmiCategoryIndex(bmi);

    // Then
    assertEquals(expectedCategory, category);
  }

  @ParameterizedTest
  @CsvSource({
    "15.0, 0",
    "20.0, 1",
    "24.0, 2",
    "27.0, 3"
  })
  @DisplayName("대표 구간 중앙값 분류 검증")
  void classifyBmiCategoryIndex_representativeMidpoints(double bmi, int expectedCategory) {
    assertEquals(expectedCategory, new SHealth().classifyBmiCategoryIndex(bmi));
  }
}
