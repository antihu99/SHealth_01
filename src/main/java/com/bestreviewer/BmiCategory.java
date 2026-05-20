package com.bestreviewer;

/** BMI 4분류 (SHealthRequirements.txt §5). */
enum BmiCategory {
    UNDERWEIGHT(HealthConstants.TYPE_UNDERWEIGHT, 0),
    NORMAL(HealthConstants.TYPE_NORMAL, 1),
    OVERWEIGHT(HealthConstants.TYPE_OVERWEIGHT, 2),
    OBESITY(HealthConstants.TYPE_OBESITY, 3);

    private final int legacyTypeCode;
    private final int categoryIndex;

    BmiCategory(int legacyTypeCode, int categoryIndex) {
        this.legacyTypeCode = legacyTypeCode;
        this.categoryIndex = categoryIndex;
    }

    int getLegacyTypeCode() {
        return legacyTypeCode;
    }

    int getCategoryIndex() {
        return categoryIndex;
    }

    static BmiCategory fromLegacyTypeCode(int typeCode) {
        for (BmiCategory category : values()) {
            if (category.legacyTypeCode == typeCode) {
                return category;
            }
        }
        return null;
    }
}
