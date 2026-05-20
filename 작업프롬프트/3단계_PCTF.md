# 3단계 — UnitTest 작성 (PCTF)

_작업시나리오 3단계 (1시간) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | **JUnit 5** 기반 단위 테스트를 설계·구현하는 **시니어 QA·Java 엔지니어** |
| **C** | Context | 2단계 리팩토링 후(또는 병행) 핵심 도메인 로직에 대한 자동화 테스트 확보 |
| **T** | Task | BMI 계산 · 나이대 평균 보정 · 4분류 · 예외·경계 TC 작성 |
| **F** | Format | `docs/03.S_Health_단위테스트계획서.md` + `*Test.java` + `Report/03.S_Health_단위테스트_보고서.md` (필수) + `mvn clean test` Green |

---

## [P] Persona

```
당신은 Java 8 + Maven + JUnit 5(Jupiter)로 단위 테스트를 작성하는 시니어 QA 엔지니어입니다.
Given–When–Then 구조와 @ParameterizedTest를 활용합니다.
로그(System.out)가 아닌 Assert·반환값으로 검증합니다.
비즈니스 규칙은 SHealthRequirements.txt를 단일 기준으로 합니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI — Java 8, Maven, JUnit 5, Mockito·Hamcrest(필요 시)
- 테스트 위치: src/test/java/com/bestreviewer/
- 기존: SHealthBMITest.java — 현재 수준 참고·확장 또는 `*Test` 명명 규칙에 맞게 전용 클래스 분리
- 대상 로직 (프로덕션):
  - SHealth.java — calculateBmi, getBmiRatio, (리팩토링 후) extract된 메서드
  - 테스트 가능하도록 package-private 메서드 또는 테스트 전용 생성자·팩토리 고려 (최소 변경)
- 데이터: 전체 shealth.dat 의존 지양 → 임시 CSV·픽스처·@TempDir 소규모 파일
- 참조:
  - SHealthRequirements.txt — TS-01~06
  - .cursorrules — Given–When–Then, @ParameterizedTest, 경계값
  - 작업시나리오 3단계 TC 목록
- BMI 경계: 18.5, 23, 25 및 나이대 경계 19/20, 29/30, 69/70
- 체중 누락: weight == 0 만 해당
```

**Cursor 첨부 권장:** `@SHealth.java` `@SHealthRequirements.txt` `@.cursorrules` `@pom.xml` `@작업시나리오`

---

## [T] Task

```
다음 순서로 진행하세요.
1) docs/03.S_Health_단위테스트계획서.md — 테스트 계획·TC 목록·경계값·픽스처 설계 (구현 전 작성)
2) src/test/.../*Test.java — 계획서에 따른 TC 구현 (Red → Green)
3) Report/03.S_Health_단위테스트_보고서.md — 실행 결과·결함 분석 (구현·실행 후 작성)
4) (선택·권장) 3-2단계 — [`3-2단계_PCTF.md`](./3-2단계_PCTF.md) 로 1~3단계 결함·권장사항 통합 보고서 작성
필요 시 프로덕션 최소 수정(테스트 가능성)만 허용합니다.

### 3-1. BMI 계산 로직 TC (TS-01)
- 공식: BMI = weight(kg) / (height(cm)/100)²
- 예시 검증: README 예시 79.5kg, 158.3cm → 기대 BMI (허용 오차 double delta)
- cm→m 변환 누락·잘못된 식 방지
- @ParameterizedTest: (weight, heightCm, expectedBmi) 여러 케이스

### 3-2. Age(나이대) 평균치 보정 로직 TC (TS-02, TS-04)
- weight==0 인 사용자에게 **동일 나이대 [a,a+10)** 비-zero 체중 평균 적용
- 보정 **후** BMI 계산되는 순서 검증
- 나이대 경계: 19세(20대 아님), 20세(20대), 29/30, 69/70
- 빈 나이대·해당 구간 전원 weight==0 → ageCount==0 나눗셈 등 엣지 (기대 동작 문서화 또는 방어 코드와 함께 테스트)
- 소규모 인메모리/임시 CSV로 3~5명 픽스처 구성

### 3-3. 정상/저체중/과체중/비만 분류 TC (TS-03)
- 경계값 표 기반 @CsvSource 또는 @MethodSource:
  | BMI | 기대 분류 |
  | <=18.5 | 저체중 |
  | 18.5 초과 ~ 23 미만 | 정상 |
  | 23 이상 ~ 25 미만 | 과체중 |
  | >=25 | 비만 |
- **반드시** 18.5, 23, 25 및 직전·직후 값 (예: 18.499, 18.501, 22.999, 23.0, 24.999, 25.0)
- 레거시 getBmiRatio type 100/200/300/400 과 집계 결과 일치(통합 TC 선택)

### 3-4. 예외·비정상 상황 TC (TS-06)
- 파일 없음 → IOException 또는 명시적 실패 (현재 구현에 맞게 기대값 고정 후, 개선은 별도 이슈로 기록)
- 잘못된 CSV 형식·숫자 파싱 실패
- 빈 파일(헤더만)
- (선택) 헤더 스킵 후 빈 행 종료 조건

### 3-5. 나이대별 비율 TC (TS-05, 통합)
- 특정 나이대 N명 중 분류별 인원 → getBmiRatio(ageClass, type) 기대 % (delta 허용)
- 빈 나이대 sum==0 시 동작 (0 반환 또는 예외 — 현재 코드 기준으로 테스트 고정)

### 구현 지침
- @DisplayName 한글 시나리오 설명 권장
- 테스트 클래스 예시 분리(권장, **클래스명은 반드시 `Test` 접미사**):
  - SHealthBmiCalculationTest.java
  - SHealthWeightImputationTest.java
  - SHealthBmiClassificationTest.java
  - SHealthExceptionTest.java
  - SHealthAgeGroupRatioTest.java (통합)
- private 메서드만 테스트 불가 시: package-private 전환 또는 동일 패키지 테스트·공개 래퍼 최소 추가
- 완료: mvn clean test 전부 Green
```

---

## [F] Format

```
### 산출물 1 — 단위테스트 계획서 (필수, 구현 전)
- 경로: docs/03.S_Health_단위테스트계획서.md
- 미작성 시 3단계 완료로 보지 않음
- 목적: 테스트 계획(Test Plan) → 테스트 케이스 설계(Test Cases)를 코드 작성 전에 문서화
- 필수 목차:
  ## 1. 테스트 목표·범위 (3단계 작업시나리오·TS-01~06)
  ## 2. 요구사항 매핑 표 (TS-01~06 ↔ 대상 메서드·`*Test` 클래스)
  ## 3. 테스트 클래스 설계 (`*Test` 접미사 클래스·역할)
  ## 4. TC 목록 (TC-ID, @DisplayName, Given–When–Then, 입력, 기대값, 우선순위)
  ## 5. 경계값·픽스처·@ParameterizedTest 설계 표
  ## 6. 테스트 환경·도구 (Java 8, Maven, JUnit 5, mvn clean test)
  ## 7. 실행·완료 기준 (Green, 커버리지 목표는 선택)
  ## 8. 제외 범위 (4단계 기능·스텁 TODO)
- 2단계 `docs/02.S_Health_1차리팩토링_체크리스트.md` 형식 참고(표·체크리스트 위주)

### 산출물 2 — 테스트 소스 (JUnit 5)
- 경로: src/test/java/com/bestreviewer/
- 패키지: com.bestreviewer
- **클래스 명명 규칙: public 클래스명은 반드시 `Test`로 끝남**
  - 파일명 = 클래스명 + `.java` (예: SHealthBmiCalculationTest.java)
  - JUnit 5 `@Test` 메서드는 클래스 내부에 정의; 클래스 접미사 `Test`와 혼동하지 않도록 구분
- 권장 분리 예 (각각 `*Test` 접미사):
  - SHealthBmiCalculationTest — TS-01 BMI 계산
  - SHealthWeightImputationTest — TS-02·TS-04 체중 보정
  - SHealthBmiClassificationTest — TS-03 4분류
  - SHealthExceptionTest — TS-06 예외·비정상
  - SHealthAgeGroupRatioTest — TS-05 나이대별 비율(통합)
- 완료 기준: mvn clean test 전부 Green

### 산출물 3 — 단위테스트 보고서 (필수, 실행 후)
- 경로: Report/03.S_Health_단위테스트_보고서.md
- 미작성 시 3단계 완료로 보지 않음
- 목적: 테스트 실행 & 결함 분석(Defect Detection) 결과 기록
- `docs/03.S_Health_단위테스트계획서.md` 와 대응·차이(추가 TC·변경 사항) 명시
- 필수 목차:
  ## 1. 계획 대비 수행 요약 (계획서 §4 TC 목록 ↔ 실제 `*Test` 메서드)
  ## 2. 테스트 클래스·메서드 목록 (TS-01~06 매핑 표, 클래스명 `*Test` 기재)
  ## 3. 경계값·픽스처 설계 표 (계획서 대비 변경 시 이유)
  ## 4. Red→Green 과정에서 발견한 결함(있을 경우)
  ## 5. mvn clean test 결과 (실행 일시·성공/실패 건수)
  ## 6. 4단계 전 보강 권장 TC
- 보고서에 코드 인용·Given–When–Then 주석 예시 포함
- 테스트 소스: 한국어 @DisplayName

### 범위 제외
- 4단계 기능(키 0 보정, 정상 목록, 전체 비율) TC는 이 단계에서 **작성하지 않음** (스텁 TODO만 가능)
```

---

## 복사용 (한 블록)

```
첨부:
@작업프롬프트/3단계_PCTF.md
@작업시나리오
@README.md
@.cursorrules
@SHealthRequirements.txt
@pom.xml
@SHealth.java
@SHealthBMI.java
@src/test/java/com/bestreviewer/SHealthBMITest.java
@Report/02.S_Health_1차리팩토링_보고서.md
@docs/02.S_Health_1차리팩토링_체크리스트.md
@docs/01.S_Health_코드스멜_분석보고서.md (또는 @Report/01.S_Health_code_smell_보고서.md)

[P] JUnit 5 단위 테스트 시니어 QA. Given–When–Then, @ParameterizedTest, Assert 검증. SHealthRequirements.txt 비즈니스 규칙 변경 금지.

[C] SHealth BMI — Java 8, Maven, JUnit 5. 테스트 위치: src/test/java/com/bestreviewer/. 소규모 CSV·@TempDir 픽스처(TS-01~06). 2단계 리팩토링 후(또는 병행) 프로덕션 최소 수정(테스트 가능성)만 허용.
참조 문서:
- 작업프롬프트/3단계_PCTF.md — 본 단계 PCTF
- 작업시나리오 — 3단계 TC 목록(BMI·나이대 보정·4분류·예외)
- SHealthRequirements.txt — TS-01~06 단일 기준
- .cursorrules — Given–When–Then, @ParameterizedTest, 경계값
- README.md — BMI 계산 예시(79.5kg, 158.3cm)
- pom.xml — JUnit 5·Mockito·Hamcrest 의존
- SHealth.java — calculateBmi, getBmiRatio, (리팩토링 후) 추출 메서드
- SHealthBMI.java — 실행·통합 맥락
- src/test/java/com/bestreviewer/SHealthBMITest.java — 기존 테스트 참고·확장
- Report/02.S_Health_1차리팩토링_보고서.md — 2단계 리팩토링 결과
- docs/02.S_Health_1차리팩토링_체크리스트.md — 2-1~2-4 검증 이력
- docs/01.S_Health_코드스멜_분석보고서.md (또는 Report/01.S_Health_code_smell_보고서.md) — 1단계 스멜·구조 이해

[T] 순서: 계획서 → TC 구현 → 보고서
0) docs/03.S_Health_단위테스트계획서.md — TS-01~06 TC·경계값·`*Test` 클래스 설계 (구현 전)
1) BMI 계산 TC — TS-01 (cm→m, @ParameterizedTest, README 예시)
2) 체중 0 → 동일 나이대 평균 보정 TC — TS-02·TS-04 + 나이대 경계(19/20, 29/30, 69/70)
3) 4분류 TC — TS-03 (18.5, 23, 25 및 직전·직후 경계 필수)
4) 예외 TC — TS-06 (파일 없음·형식 오류·빈 데이터)
5) 나이대별 비율·getBmiRatio 통합 TC — TS-05
Red → Green. 완료: mvn clean test 전부 Green.

[F] 산출물:
  1) docs/03.S_Health_단위테스트계획서.md (필수, 구현 전)
     §1 목표·범위 · §2 TS 매핑 · §3 `*Test` 클래스 · §4 TC 목록(GWT) · §5 경계·픽스처 · §6 환경 · §7 완료 기준 · §8 제외
  2) src/test/java/com/bestreviewer/*Test.java — public 클래스명 `Test` 접미사 필수
     (권장: SHealthBmiCalculationTest, SHealthWeightImputationTest, SHealthBmiClassificationTest, SHealthExceptionTest, SHealthAgeGroupRatioTest)
  3) Report/03.S_Health_단위테스트_보고서.md (필수, 실행 후)
     §1 계획 대비 수행 · §2 클래스·메서드 · §3 경계·픽스처 · §4 Red→Green 결함 · §5 mvn test 결과 · §6 4단계 보강 TC
한국어 @DisplayName. 4단계 기능(키 0 보정, 정상 목록, 전체 비율) TC는 작성하지 않음(스텁 TODO만).
```
