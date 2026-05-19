# 3단계 — UnitTest 작성 (PCTF)

_작업시나리오 3단계 (1시간) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | **JUnit 5** 기반 단위 테스트를 설계·구현하는 **시니어 QA·Java 엔지니어** |
| **C** | Context | 2단계 리팩토링 후(또는 병행) 핵심 도메인 로직에 대한 자동화 테스트 확보 |
| **T** | Task | BMI 계산 · 나이대 평균 보정 · 4분류 · 예외·경계 TC 작성 |
| **F** | Format | `src/test/java/com/bestreviewer/` 테스트 클래스 + `mvn clean test` Green |

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
- 기존: SHealthBMITest.java — 현재 수준 참고·확장 또는 전용 클래스 분리
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
다음 테스트 영역을 구현하세요. Red → Green → 필요 시 프로덕션 최소 수정(테스트 가능성)만 허용합니다.

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
- 테스트 클래스 예시 분리(권장):
  - SHealthBmiCalculationTest
  - SHealthWeightImputationTest
  - SHealthBmiClassificationTest
  - SHealthExceptionTest
  - SHealthAgeGroupRatioTest (통합)
- private 메서드만 테스트 불가 시: package-private 전환 또는 동일 패키지 테스트·공개 래퍼 최소 추가
- 완료: mvn clean test 전부 Green
```

---

## [F] Format

```
- 산출물: src/test/java/com/bestreviewer/*.java (JUnit 5)
- 선택 문서: Report/03.S_Health_단위테스트_보고서.md
  ## 1. 테스트 클래스·메서드 목록 (TS-01~06 매핑 표)
  ## 2. 경계값·픽스처 설계 표
  ## 3. Red→Green 과정에서 발견한 결함(있을 경우)
  ## 4. mvn clean test 결과
  ## 5. 4단계 전 보강 권장 TC
- 코드 인용·Given–When–Then 주석 예시 포함
- 한국어 @DisplayName
- 4단계 기능(키 0 보정, 정상 목록, 전체 비율) TC는 이 단계에서 **작성하지 않음** (스텁 TODO만 가능)
```

---

## 복사용 (한 블록)

```
[P] JUnit 5 단위 테스트 시니어 QA. Given–When–Then, Assert 검증, SHealthRequirements.txt 기준.

[C] SHealth BMI, src/test/java/com/bestreviewer. 소규모 CSV 픽스처. TS-01~06.

[T]
1) BMI 계산 TC (cm→m, Parameterized)
2) 체중 0 → 동일 나이대 평균 보정 TC + 나이대 경계
3) 4분류 TC — 18.5, 23, 25 경계 필수
4) 예외 TC — 파일 없음·형식 오류·빈 데이터
5) 나이대별 비율·getBmiRatio 통합 TC
mvn clean test Green.

[F] 테스트 소스 + (선택) Report/03.S_Health_단위테스트_보고서.md. 한국어 DisplayName.
```
