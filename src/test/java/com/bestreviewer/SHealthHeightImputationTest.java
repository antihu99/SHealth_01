package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** 4-3 height==0 동일 나이대 키 평균 보정. */
class SHealthHeightImputationTest {

    private static final double DELTA = 1e-2;

    @Test
    @DisplayName("height=0 사용자에게 동일 20대 평균 키(170cm) 보정 후 3명 모두 분류·집계된다")
    void calculateBmi_imputesZeroHeightWithAgeGroupAverage() {
        SHealth shealth = new SHealth();
        shealth.calculateBmi(TestFixtures.path("impute_height_20s.csv"));

        assertEquals(0.0, shealth.getBmiRatio(20, SHealth.TYPE_UNDERWEIGHT), DELTA);
        assertEquals(33.33, shealth.getBmiRatio(20, SHealth.TYPE_NORMAL), DELTA);
        assertEquals(33.33, shealth.getBmiRatio(20, SHealth.TYPE_OVERWEIGHT), DELTA);
        assertEquals(33.33, shealth.getBmiRatio(20, SHealth.TYPE_OBESITY), DELTA);
    }
}
