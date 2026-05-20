package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** 4-5 전체 사용자 대비 BMI 범주 비율(%). */
class SHealthOverallBmiRatioTest {

    private static final double DELTA = 1e-2;

    @Test
    @DisplayName("전체 4명 픽스처에서 저체중 25%·정상 50%·과체중 0%·비만 25%이다")
    void getOverallBmiRatio_fourUsersFixedDistribution() {
        SHealth shealth = new SHealth();
        shealth.calculateBmi(TestFixtures.path("overall_ratio_four.csv"));

        assertEquals(25.0, shealth.getOverallBmiRatio(SHealth.TYPE_UNDERWEIGHT), DELTA);
        assertEquals(50.0, shealth.getOverallBmiRatio(SHealth.TYPE_NORMAL), DELTA);
        assertEquals(0.0, shealth.getOverallBmiRatio(SHealth.TYPE_OVERWEIGHT), DELTA);
        assertEquals(25.0, shealth.getOverallBmiRatio(SHealth.TYPE_OBESITY), DELTA);
    }
}
