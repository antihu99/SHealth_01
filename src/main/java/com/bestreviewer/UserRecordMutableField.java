package com.bestreviewer;

/** 체중·키 등 UserRecord의 mutable double 필드 접근 (누락값 보정 DRY). */
interface UserRecordMutableField {

    UserRecordMutableField WEIGHT = new UserRecordMutableField() {
        @Override
        public double getValue(UserRecord userRecord) {
            return userRecord.getWeight();
        }

        @Override
        public void setValue(UserRecord userRecord, double value) {
            userRecord.setWeight(value);
        }
    };

    UserRecordMutableField HEIGHT = new UserRecordMutableField() {
        @Override
        public double getValue(UserRecord userRecord) {
            return userRecord.getHeight();
        }

        @Override
        public void setValue(UserRecord userRecord, double value) {
            userRecord.setHeight(value);
        }
    };

    double getValue(UserRecord userRecord);

    void setValue(UserRecord userRecord, double value);

    default boolean isMissing(double value) {
        return value == 0.0;
    }
}
