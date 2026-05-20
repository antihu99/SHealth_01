# 4단계 — 기능 개선 (SRP·신규 기능) (PCTF)

_작업시나리오 4단계 (2시간) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | **SRP·도메인 설계**에 능숙한 **시니어 Java 엔지니어**. TDD·테스트 우선 확장 |
| **C** | Context | 2~3단계 리팩토링·단위 테스트 Green 상태에서 기능·구조 개선 |
| **T** | Task | 책임 분리 + 연령대 BMI 비율 + 키 0 보정 + 정상 BMI 목록 + 전체 범주 비율 |
| **F** | Format | 패키지 구조·신규 API·테스트 + `mvn clean test` Green + `Report/04.S_Health_기능개선_보고서.md` (필수) |

---

## [P] Persona

```
당신은 SRP와 도메인 주도 설계를 적용해 레거시 Java를 확장하는 시니어 Java 엔지니어입니다.
기존 public API(calculateBmi, getBmiRatio)는 하위 호환을 유지하거나,
변경 시 단위 테스트로 회귀를 입증합니다.
신규 기능마다 Given–When–Then 단위 테스트를 함께 추가합니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI — Java 8, Maven, JUnit 5
- 선행: 2단계 1차 리팩토링, 3단계 단위 테스트 Green
- 참조:
  - SHealthRequirements.txt — FR-01~06, 처리 흐름
  - .cursorrules — 4단계 확장 항목(키 0, 정상 목록, 전체 비율)
  - 작업시나리오 4단계
- 기존 API 유지:
  - calculateBmi(String filename)
  - getBmiRatio(int ageClass, int type)  // 100/200/300/400
- 신규·확장 요구 (작업시나리오·.cursorrules):
  1) SRP에 따른 책임 분리 리팩토링
  2) 특정 연령대의 BMI 분포 비율 계산 (기존 강화·명확한 API)
  3) Height==0 → 동일 나이대 비-zero 키 평균 보정 (체중 0과 동일 패턴)
  4) BMI 정상 범위 사용자 목록: 18.5 < BMI < 23 → 사용자 ID(또는 레코드) 조회
  5) 전체 사용자 대비 각 BMI 범주 비율(%) — 나이대 무관 전체 기준 4분류
- 처리 순서(권장):
  파일 읽기 → 체중 보정 → 키 보정 → BMI 계산 → 분류 → 집계·조회
```

**Cursor 첨부 권장:**  
`@작업프롬프트/4단계_PCTF.md` `@SHealth.java` `@SHealthRequirements.txt` `@.cursorrules` `@작업시나리오`  
`@docs/03-2.S_Health_결함_레지스터.md` `@docs/03-2.S_Health_권장사항_레지스터.md` `@Report/03-2.S_Health_결함및권장사항_통합보고서.md`  
`@docs/03.S_Health_단위테스트계획서.md` `@Report/03.S_Health_단위테스트_보고서.md` `@src/test/java/com/bestreviewer/`

---

## [T] Task

```
다음을 수행하세요. 각 기능마다 단위 테스트를 추가하고 mvn clean test를 유지합니다.

### 4-1. SRP에 따른 책임 분리
- 권장 패키지/클래스 예 (이름은 프로젝트에 맞게 조정):
  | 책임 | 예시 클래스 |
  | CSV 읽기·파싱 | CsvUserRecordReader / UserRecordParser |
  | 사용자 모델 | UserRecord (id, age, weight, height) |
  | 누락 보정 | WeightImputationService, HeightImputationService |
  | BMI 계산 | BmiCalculator |
  | 분류 | BmiClassifier → enum BmiCategory |
  | 통계·비율 | AgeGroupStatisticsService, OverallBmiStatisticsService |
  | 파사드·기존 API | SHealth (calculateBmi, getBmiRatio 위임) |
- SHealth는 얇은 파사드로 축소하거나, 기존 클래스를 단계적으로 위임
- 매직 넘버·나이대 상수는 도메인 상수 클래스로 일원화

### 4-2. 특정 연령대 BMI 분포 비율 계산 (기존 FR-05·FR-06 강화)
- getBmiRatio(ageClass, type) 동작 유지·테스트 보강
- (선택) 명시적 API 예:
  - getAgeGroupDistribution(int ageGroupStart) → Map<BmiCategory, Double> 또는 DTO
- 20·30·40·50·60·70대 각 4분류 비율(%) 합이 100에 근사 (반올림 허용 범위 문서화)

### 4-3. Height가 0인 경우 평균치 보정
- 규칙: height==0 → 동일 나이대 [a,a+10) 비-zero height 평균 적용
- 체중 0 보정과 **동일 알고리즘 패턴** 재사용 (DRY)
- 순서: 체중 보정 완료 후 키 보정 → 그 다음 BMI 계산
- 단위 테스트: TS-02와 대칭되는 키 보정 픽스처·빈 나이대 엣지

### 4-4. BMI 정상 범위 사용자 목록 조회
- 조건: 18.5 < BMI < 23 (SHealthRequirements.txt 5.1)
- API 예: List<String> getNormalBmiUserIds() 또는 List<UserRecord> getUsersWithNormalBmi()
- calculateBmi 실행 후 조회 가능
- 테스트: 픽스처 3명 — 저체중/정상/비만 각 1명 → 정상만 ID 반환

### 4-5. 전체 사용자 대비 BMI 범주 비율
- 나이대가 아닌 **전체 count** 기준 4분류 비율(%)
- API 예: double getOverallBmiRatio(BmiCategory category) 또는 Map<BmiCategory, Double>
- getBmiRatio와 구분되는 메서드명·문서화
- 테스트: 전체 4명 고정 픽스처 → 저체중 25%, 정상 50% 등

### 공통
- 기존 3단계 테스트 전부 Green 유지
- 신규 기능별 테스트 클래스 또는 기존 클래스에 @Nested 추가
- 불필요한 README·빌드 파일 추가 금지 (.cursorrules)
- 완료: mvn clean test
```

---

## [F] Format

```
- 산출물(코드, 필수):
  1) src/main/java/com/bestreviewer/ — SRP 분리 클래스·SHealth 파사드
  2) src/test/java/com/bestreviewer/ — 4-2~4-5 신규 TC, 기존 *Test Green 유지
  3) mvn clean test — 전체 Green (로그 요약을 보고서 §6에 기재)
- 산출물(문서, 필수):
  Report/04.S_Health_기능개선_보고서.md
  ## 1. 패키지·클래스 구조 (mermaid)
  ## 2. 신규·변경 API 표 (메서드·입력·출력·규칙)
  ## 3. 처리 순서 다이어그램 (보정→BMI→집계)
  ## 4. 기능별 테스트 매핑 (4-1~4-5 ↔ TC-ID·*Test 클래스)
  ## 5. 기존 API 호환성·DEF/REC(03-2) 해소·잔여 제한
  ## 6. mvn clean test 결과 (성공·실패·스킵 건수)
- 한국어·표 위주 작성
- 완료 체크:
  - [ ] 4-1~4-5 코드·TC 반영
  - [ ] calculateBmi / getBmiRatio 하위 호환 또는 변경 시 회귀 TC
  - [ ] Report/04.S_Health_기능개선_보고서.md 존재, §1~§6 기재
  - [ ] mvn clean test Green
```

---

## 복사용 (한 블록)

```
첨부:
@작업프롬프트/4단계_PCTF.md
@작업시나리오
@SHealthRequirements.txt
@.cursorrules
@SHealth.java
@src/test/java/com/bestreviewer/
@docs/03-2.S_Health_결함_레지스터.md
@docs/03-2.S_Health_권장사항_레지스터.md
@Report/03-2.S_Health_결함및권장사항_통합보고서.md
@docs/03.S_Health_단위테스트계획서.md
@Report/03.S_Health_단위테스트_보고서.md

[P] SRP·TDD 시니어 Java. 기존 calculateBmi/getBmiRatio 호환·테스트로 회귀 입증.

[C] 2~3단계 Green. SHealthRequirements.txt + .cursorrules 4단계 확장.
참조 문서:
- 작업프롬프트/4단계_PCTF.md — 본 단계 PCTF
- 작업시나리오 — 4단계 기능 목록(SRP·연령대 비율·키0·정상목록·전체비율)
- SHealthRequirements.txt — FR-01~06, TS-01~06, 분류·보정 규칙
- .cursorrules — 4단계 확장 항목·TDD·Given–When–Then
- SHealth.java — calculateBmi, getBmiRatio, 리팩토링 대상
- src/test/java/com/bestreviewer/*Test.java — 3단계 Green TC(회귀 기준)
- docs/03-2.S_Health_결함_레지스터.md — DEF-xx(4단계 이관·P0)
- docs/03-2.S_Health_권장사항_레지스터.md — REC-xx(4단계 Feature)
- Report/03-2.S_Health_결함및권장사항_통합보고서.md — 4단계 진입 체크리스트
- docs/03.S_Health_단위테스트계획서.md — TC-ID·경계·픽스처
- Report/03.S_Health_단위테스트_보고서.md — D-xx·§6 4단계 보강 TC

[T]
1) SRP 분리: Reader, UserRecord, Imputation, BmiCalculator, Classifier, Statistics, SHealth 파사드
2) 연령대별 BMI 분포 비율 (getBmiRatio 유지·강화)
3) height==0 → 동일 나이대 키 평균 보정 (체중 보정 후, BMI 전)
4) 정상 BMI(18.5<BMI<23) 사용자 ID 목록 조회
5) 전체 사용자 4분류 비율(%)
기능마다 JUnit5 TC. mvn clean test Green.

[F] 산출물 (필수):
  1) src/main/java/com/bestreviewer/ — SRP 분리 클래스·SHealth 파사드
  2) src/test/java/com/bestreviewer/ — 4-2~4-5 신규 TC, 기존 *Test Green
  3) Report/04.S_Health_기능개선_보고서.md (필수, 구현·테스트 후)
     §1 패키지·클래스(mermaid) · §2 신규·변경 API · §3 처리 순서 · §4 기능↔TC 매핑 · §5 API 호환·DEF/REC 해소 · §6 mvn clean test
mvn clean test 전체 Green. 한국어.
```
