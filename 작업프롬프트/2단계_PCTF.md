# 2단계 — 1차 리팩토링 (클린코드 관점) (PCTF)

_작업시나리오 2단계 (1시간) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | 레거시 Java **리팩토링**을 수행하는 **시니어 Java 엔지니어**. 비즈니스 규칙은 변경하지 않고 구조·가독성만 개선 |
| **C** | Context | 1단계 코드 스멜 분석 완료 후, `SHealth.java` 중심 1차 클린코드 리팩토링 |
| **T** | Task | **순서 고정**: (1) 네이밍 → (2) 하드코드·전역 상태 제거 → (3) 함수 추출 → (4) 반복·중복 제거 |
| **F** | Format | `docs/02.*` 체크리스트·검증 문서 + 리팩토링 코드 + `Report/02.*` + `mvn clean test` Green |

---

## [P] Persona

```
당신은 레거시 Java 코드 QA·리팩토링을 수행하는 시니어 Java 엔지니어입니다.
SHealthRequirements.txt의 비즈니스 규칙(BMI 공식, 분류 경계, 나이대, 체중 0 보정)은
임의로 바꾸지 않습니다. 동작은 유지하고 구조·이름·중복만 개선합니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI (Samsung Health) — Java 8, Maven, 패키지 com.bestreviewer
- 선행 단계: 1단계 코드 스멜 분석 보고서(docs 또는 Report) 참고 권장
- 참조 문서 (우선순위):
  1) SHealthRequirements.txt — 비즈니스 규칙·경계값의 단일 기준
  2) README.md — 개요·데이터 샘플
  3) .cursorrules — 리팩토링·테스트·API 호환 규칙
  4) 작업시나리오 — 2단계: 네이밍 → 하드코드·전역 제거 → 함수 추출 → DRY
- 수정 대상(우선):
  - src/main/java/com/bestreviewer/SHealth.java
  - 필요 최소 범위: SHealthBMI.java, pom.xml(테스트 의존성만)
- 유지해야 할 public API (시그니처·의미 동일):
  - calculateBmi(String filename)
  - getBmiRatio(int ageClass, int type)  // type: 100/200/300/400
- 도메인 요약 (변경 금지):
  | 항목 | 규칙 |
  | BMI | 체중(kg) / (키(m))², cm→m: height/100 |
  | 체중 누락 | weight==0 → 동일 나이대 [a,a+10) 비-zero 체중 평균 |
  | 나이대 | 20,30,40,50,60,70 시작, 구간 [a,a+10) |
  | 분류 | 저체중≤18.5 / 정상 18.5~23미만 / 과체중 23~25미만 / 비만≥25 |
- 리팩토링 전제: 기존 테스트가 있으면 Green 확인 후 진행. 없으면 수동·스모크로 동작 동일성 확인.
- 금지: SRP 대규모 클래스 분리, 신규 기능(키 0 보정·정상 목록 등) — 4단계 범위
```

**Cursor 첨부 권장:** `@SHealth.java` `@SHealthBMI.java` `@SHealthRequirements.txt` `@.cursorrules` `@작업시나리오` `@docs/01.S_Health_코드스멜_분석보고서.md` 또는 `@Report/01.S_Health_code_smell_보고서.md`

---

## [T] Task

```
다음 4가지를 **반드시 이 순서대로** 수행하세요. 각 하위 단계 완료 후 컴파일·기존 동작을 확인합니다.

### 2-1. 네이밍 개선
- 의미 없는 축약·숫자 접미사 필드명 개선
  예: underweight20 → underweightRatioAge20 또는 ageGroup별 Map/배열 키로 의미 드러내기
- 메서드·지역 변수: sum, a, type 등 → ageGroupStart, bmiCategoryType 등
- 매직 넘버는 아직 상수화해도 되나, 2-2에서 일괄 정리해도 됨
- calculateBmi 내부 단계 주석은 "무엇을 하는지" 한글 또는 영문 명확 표현

### 2-2. 하드코드 및 전역(인스턴스) 상태 정리
- BMI 경계 18.5, 23, 25 → private static final 상수 (예: BMI_UNDERWEIGHT_MAX)
- 나이대 20~70, +10 → AGE_GROUP_STARTS, AGE_GROUP_WIDTH 상수
- getBmiRatio type 100/200/300/400 → enum BmiCategory 또는 named constant (동작 동일)
- 고정 크기 10000 배열: 가능하면 List<UserRecord> 등으로 치환하되, **동작·성능 급변 없이** 점진적 변경
- 나이대×분류별 24개 double 필드: Map<AgeGroup, Map<BmiCategory, Double>> 또는 2차원 구조로 응집 (선택)

### 2-3. 함수 추출 (Extract Method)
- calculateBmi()를 처리 흐름에 맞게 분리 (요구사항 순서 유지):
  1) readUsersFromFile(filename)
  2) imputeMissingWeightsByAgeGroup()  // 체중 0 보정
  3) computeBmisForAllUsers()
  4) aggregateRatiosByAgeGroup()
- split(line, delimiter) — 유지 또는 CsvParser 등 작은 클래스로 이동(선택)
- getBmiRatio — 긴 if-else → Map 조회 또는 switch·enum 매핑
- 각 추출 메서드는 **한 가지 일**만 수행 (SRP는 4단계에서 본격 적용, 여기서는 메서드 단위 분리)

### 2-4. 반복·중복 제거 (DRY)
- 나이대 루프 for (a = 20; a <= 70; a += 10) 중복 → 단일 루프·헬퍼
- 나이대별 if (a==20) ... else if (a==30) ... 블록 → 루프 + 배열/Map 저장
- BMI 분류 if-else 체인 → classifyBmi(double bmi) 단일 메서드
- 동일한 "나이대에 속하는지" 조건 → belongsToAgeGroup(age, ageGroupStart) 등

### 공통 제약
- FR-01~FR-06, getBmiRatio 반환값(%) 의미 유지
- IOException: printStackTrace만 남기지 말고, 최소한 로깅 정리 또는 상위 전파 검토 (동작 변경 시 테스트로 입증)
- 한 번에 과도한 파일 분리 금지 — 리뷰 가능한 작은 커밋 단위 diff
- 완료 후: mvn clean test (테스트 없으면 mvn compile 및 기존 SHealthBMI 실행으로 스모크)
```

---

## [F] Format

### 단계별 체크리스트 + 검증 방법

각 하위 단계(2-1 ~ 2-4)를 **완료할 때마다** 아래 체크리스트를 채우고 검증 명령을 실행합니다.  
실패 시 **해당 단계 변경만 롤백**한 뒤 원인을 수정하고 다시 검증합니다.

| 단계 | 체크리스트 | 검증 방법 (`mvn test`) |
|------|------------|------------------------|
| **시작 전** | [ ] 1단계 스멜 보고서·`SHealthRequirements.txt` 확인<br>[ ] Git 작업 트리 백업 또는 브랜치 생성<br>[ ] 기준선 검증 1회 실행 | `mvn clean test`<br>→ **Green**이면 2-1 진행. Red면 2단계 전에 원인 정리(3단계 TC 부재 시 `mvn compile` + `SHealthBMI` 스모크로 대체하고 보고서에 기록) |
| **2-1 네이밍** | [ ] 필드·지역 변수·매개변수 의미 드러나게 변경<br>[ ] `underweight20` 등 나이대·비율 의미가 이름에 반영<br>[ ] `calculateBmi` 단계 주석 정리<br>[ ] public API 시그니처·이름 변경 없음 | `mvn test`<br>→ Green 유지. 테스트 없으면 `mvn compile` |
| **2-2 하드코드·전역** | [ ] BMI 경계(18.5, 23, 25) 상수화<br>[ ] 나이대(20~70, +10) 상수화<br>[ ] type 100/200/300/400 → enum 또는 named constant<br>[ ] 24개 비율 필드·10000 배열 정리(점진적 변경 허용)<br>[ ] 비즈니스 규칙 수치·분류 조건 변경 없음 | `mvn test`<br>→ Green 유지. `getBmiRatio` 기존 호출 결과 동일 여부 수동·스모크 1회(테스트 없을 때) |
| **2-3 함수 추출** | [ ] `readUsersFromFile` (또는 동등) 분리<br>[ ] `imputeMissingWeightsByAgeGroup` 분리<br>[ ] `computeBmisForAllUsers` 분리<br>[ ] `aggregateRatiosByAgeGroup` 분리<br>[ ] 처리 순서: 읽기 → 체중 보정 → BMI → 집계 유지<br>[ ] `getBmiRatio` if-else 단순화 | `mvn test`<br>→ Green 유지. `calculateBmi("shealth.dat")` 후 `getBmiRatio(20,100)` 등 샘플 값 회귀 확인(가능 시) |
| **2-4 DRY** | [ ] 나이대 루프 `for (a=20; a<=70; a+=10)` 단일화<br>[ ] `if (a==20)...` 블록 제거·Map/배열 저장<br>[ ] `classifyBmi(double)` 등 분류 로직 단일화<br>[ ] `belongsToAgeGroup` 등 조건 헬퍼 추출<br>[ ] SRP 클래스 분리·4단계 기능 미포함 | `mvn test`<br>→ Green 유지 |
| **최종** | [ ] 2-1 ~ 2-4 전체 체크 완료<br>[ ] diff 리뷰 가능한 크기<br>[ ] `docs/02.S_Health_1차리팩토링_체크리스트.md` 작성·체크 반영<br>[ ] `Report/02.S_Health_1차리팩토링_보고서.md` 작성 | `mvn clean test`<br>→ **전체 Green** 필수. 실패 시 실패 TC·로그·롤백 방향을 docs·Report §6에 기록 |

**검증 명령 요약**

```bash
# 각 하위 단계(2-1 ~ 2-4) 완료 직후
mvn test

# 2단계 전체 완료 시 (최종 게이트)
mvn clean test
```

| 상황 | 대체 검증 |
|------|-----------|
| `src/test`에 TC가 아직 없음 (3단계 전) | `mvn compile` + `SHealthBMI` main 실행으로 `shealth.dat` 처리·콘솔 출력 스모크 |
| `mvn test` Red | 실패 로그·해당 단계 diff 확인 → 롤백 또는 최소 수정 후 재실행 |
| 의도적 동작 변경(버그 수정) | `SHealthRequirements.txt`와 일치하는지 확인 후, 보고서에 **Before/After** 명시 |

---

### 산출물

```
1) docs/02.S_Health_1차리팩토링_체크리스트.md (필수, 없으면 docs 폴더 생성)
   - 위 「단계별 체크리스트 + 검증 방법」표 전체 포함
   - 각 단계별 [ ] → ✅/❌ 갱신, mvn test 실행 일시·결과(Green/Red) 기록
   - 대체 검증(mvn compile + SHealthBMI 스모크) 사용 시 해당 단계에 명시

2) src/main/java/com/bestreviewer/ 리팩토링된 SHealth.java (필요 시 보조 클래스 최소 추가)

3) Report/02.S_Health_1차리팩토링_보고서.md (필수)
   ## 1. 수행 요약 — docs 체크리스트 ✅/❌ 요약
   ## 2. 변경 전·후 구조 (mermaid 또는 표)
   ## 3. 주요 리네이밍·상수화 목록
   ## 4. 추출한 메서드 목록과 책임
   ## 5. DRY로 제거한 중복 패턴
   ## 6. 단계별 mvn test 검증 결과 (2-1~2-4, 최종 mvn clean test 로그 요약)
   ## 7. 유지한 API·알려진 제한사항(미해결 스멜 → 3·4단계)

- 한국어 작성
- 3단계(단위 테스트)·4단계(기능 추가) 작업은 이 프롬프트 범위 밖
```

---

## 복사용 (한 블록)

```
첨부: @SHealth.java @SHealthBMI.java @SHealthRequirements.txt @.cursorrules @작업시나리오 @docs/01.S_Health_코드스멜_분석보고서.md (또는 @Report/01.S_Health_code_smell_보고서.md)

[P] 레거시 Java 리팩토링 시니어 엔지니어. SHealthRequirements.txt 비즈니스 규칙 변경 금지.

[C] SHealth BMI, com.bestreviewer. SHealth.java 중심. calculateBmi/getBmiRatio API·의미 유지.
참조: SHealthRequirements.txt, .cursorrules, 작업시나리오, 1단계 스멜 보고서.

[T] 순서 고정 (각 하위 단계 완료 직후 검증, 실패 시 해당 단계만 롤백):
1) 2-1 네이밍 — 필드·변수 의미화, public API 시그니처 유지
2) 2-2 하드코드·전역 — BMI 18.5/23/25·나이대 20~70 상수화, type 100/200/300/400 enum화, 24개 비율 필드·10000 배열 정리
3) 2-3 함수 추출 — readUsers → imputeWeights → computeBmis → aggregateRatios, 처리 순서 유지
4) 2-4 DRY — 나이대 루프·if(a==20) 체인·classifyBmi 단일화
금지: SRP 클래스 분리, 키0 보정 등 4단계 기능.

[F] 단계별 체크리스트 + 검증:
- 시작 전: mvn clean test → Green 후 2-1 (TC 없으면 mvn compile + SHealthBMI 스모크)
- 2-1~2-4 각 완료 후: mvn test → Green 유지 (TC 없으면 mvn compile)
- 최종: mvn clean test → 전체 Green 필수
산출물:
  1) docs/02.S_Health_1차리팩토링_체크리스트.md — 2-1~2-4 체크리스트 + 각 단계 mvn test 검증 결과
  2) 리팩토링된 SHealth.java
  3) Report/02.S_Health_1차리팩토링_보고서.md (§1 체크리스트 ✅/❌, §6 mvn test 결과). 한국어.
```
