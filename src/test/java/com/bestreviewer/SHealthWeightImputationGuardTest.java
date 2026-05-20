package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/** DEF-03 전원 weight=0 보정 가드. */
class SHealthWeightImputationGuardTest {

    @Test
    @DisplayName("나이대 전원 weight=0이면 0 나눗셈 없이 weight 0 유지·집계는 0%")
    void calculateBmi_allZeroWeightInAgeGroup_noDivisionByZero() {
        SHealth shealth = new SHealth();
        int count = shealth.calculateBmi(TestFixtures.path("all_zero_weight_20s.csv"));

        assertEquals(2, count);
        assertEquals(0.0, shealth.getBmiRatio(20, SHealth.TYPE_NORMAL));
        assertFalse(Double.isNaN(shealth.getBmiRatio(20, SHealth.TYPE_NORMAL)));
    }
}
