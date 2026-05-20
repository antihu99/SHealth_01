package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** TS-06 파일·형식 예외. */
class SHealthExceptionTest {

  @Test
  @DisplayName("존재하지 않는 파일이면 userCount 0이다 (비율은 빈 집계로 NaN)")
  void calculateBmi_missingFile_returnsZeroUsers() {
    SHealth shealth = new SHealth();
    int count = shealth.calculateBmi("no_such_file_3stage.dat");
    assertEquals(0, count);
    assertTrue(Double.isNaN(shealth.getBmiRatio(20, SHealth.TYPE_NORMAL)));
  }

  @Test
  @DisplayName("age 필드가 숫자가 아니면 NumberFormatException이 발생한다")
  void calculateBmi_invalidAge_throwsNumberFormatException() {
    SHealth shealth = new SHealth();
    assertThrows(
        NumberFormatException.class,
        () -> shealth.calculateBmi(TestFixtures.path("bad_age.csv")));
  }

  @Test
  @DisplayName("헤더만 있는 파일이면 userCount 0이다")
  void calculateBmi_headerOnly_returnsZeroUsers() {
    SHealth shealth = new SHealth();
    int count = shealth.calculateBmi(TestFixtures.path("header_only.csv"));
    assertEquals(0, count);
  }
}
