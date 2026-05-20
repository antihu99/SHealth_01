package com.bestreviewer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** 4-4 정상 BMI(18.5 < BMI < 23) 사용자 ID 목록. */
class SHealthNormalBmiListTest {

    @Test
    @DisplayName("저체중·정상·비만 각 1명 픽스처에서 정상 BMI 사용자 ID만 반환한다")
    void getNormalBmiUserIds_returnsOnlyNormalRangeUsers() {
        SHealth shealth = new SHealth();
        shealth.calculateBmi(TestFixtures.path("normal_bmi_three.csv"));

        List<String> normalIds = shealth.getNormalBmiUserIds();

        assertEquals(1, normalIds.size());
        assertEquals("u2", normalIds.get(0));
    }
}
