# 4-2단계 — 신규 기능 2차 리팩토링 (클린코드) (PCTF)

_4단계 하위 (기능 개선·SRP 분리 완료 후) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | 4단계에서 추가·분리된 코드를 **동작 변경 없이** 정리하는 **시니어 Java 리팩토링** 엔지니어 |
| **C** | Context | 4단계 Green·SRP 클래스·신규 API 존재. 2단계 1차 리팩토링·3단계 TC가 회귀 기준 |
| **T** | Task | **순서 고정**: (1) 네이밍 → (2) 하드코드·전역 상태 제거 → (3) 함수 추출 → (4) 반복·중복 제거 — **4단계 신규·변경 범위 중심** |
| **F** | Format | `docs/04-2.*` 2차 리팩토링 체크리스트(필수) + 리팩토링 코드 + `Report/04-2.*` + `mvn clean test` Green |

---

## [P] Persona

```
당신은 4단계에서 도입된 SRP 클래스·신규 API를 대상으로 2차 클린코드 리팩토링을 수행하는 시니어 Java 엔지니어입니다.
SHealthRequirements.txt의 비즈니스 규칙(BMI 공식, 분류 경계, 체중·키 0 보정, 정상 범위, 집계·비율)은 임의로 바꾸지 않습니다.
3단계·4단계 단위 테스트가 고정한 동작(의도적 레거시 포함)은 테스트 실패 없이 유지합니다.
신규 기능 추가·명세 변경·DEF 해소용 버그 수정은 이 단계 범위 밖입니다(구조·이름·중복·상수화만).
public API 시그니처(calculateBmi, getBmiRatio, getOverallBmiRatio, getNormalBmiUserIds 등)는 유지하거나,
변경 시 기존 TC·4단계 TC로 회귀를 입증합니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI — Java 8, Maven, JUnit 5, 패키지 com.bestreviewer
- 선행: 4단계(4-1~4-5) 기능·SRP 분리·신규 TC 구현 완료, mvn clean test Green
- 2단계와의 관계:
  | 구분 | 2단계 (1차) | 4-2단계 (2차, 본 단계) |
  |------|-------------|------------------------|
  | 대상 | SHealth.java 레거시 중심 | 4단계 신규·분리 클래스·확장 API |
  | 범위 | SRP 대규모 분리·키0 보정 등 기능 추가 금지 | 신규 기능 추가 금지, 구조·품질만 |
  | 산출물 | docs/02.* 체크리스트 | docs/04-2.* 체크리스트 |
- 수정 대상(우선, 존재하는 파일 기준):
  | 우선순위 | 경로·클래스 | 4-2에서 다룰 전형 이슈 |
  |----------|-------------|----------------------|
  | P0 | SHealth.java (파사드) | 위임·상태 필드 네이밍, 중복 초기화 |
  | P0 | MissingValueImputationService.java | 체중·키 보정 DRY, 매직 넘버 |
  | P0 | AgeGroupStatisticsService, OverallBmiStatisticsService | 중복 집계·type 매핑 |
  | P1 | CsvUserRecordReader, UserRecord, BmiClassifier, BmiCalculator | 네이밍·상수·짧은 메서드 추출 |
  | P1 | HealthConstants, BmiCategory, AgeGroupHelper | 상수 일원화·enum 활용 |
  | P2 | src/test/java/com/bestreviewer/*Test.java | 리팩토링에 따른 최소 수정(동작 동일) |
- 유지해야 할 public API (의미·반환 규칙 동일):
  - calculateBmi(String filename)
  - getBmiRatio(int ageGroupStart, int bmiCategoryType)  // 100/200/300/400
  - getOverallBmiRatio(int bmiCategoryType)
  - getNormalBmiUserIds()
  - (4단계에서 추가된 기타 public 메서드가 있으면 동일 원칙)
- 처리 순서(변경 금지):
  파일 읽기 → 체중 보정 → 키 보정 → BMI 계산·분류 → 나이대 집계 → 전체 집계
- 금지:
  - 5단계 회고 본문 작성
  - DEF-xx 결함 수정을 위한 비즈니스 규칙 변경(별도 결함 수정 단계)
  - 불필요한 README·pom 구조 변경(.cursorrules)
```

### 참조 문서 (우선순위·용도)

| 우선순위 | 경로 | 용도 |
|----------|------|------|
| 1 | `SHealthRequirements.txt` | BMI·보정·분류·FR/TS 단일 기준 — **수치·규칙 변경 금지** |
| 2 | `.cursorrules` | 리팩토링·테스트·API 호환·4단계 확장 규칙 |
| 3 | `작업시나리오` | 2단계(1차 리팩토링 4항목)와 4단계(기능) 구분 |
| 4 | `작업프롬프트/4-2단계_PCTF.md` | 본 단계 PCTF |
| 5 | `작업프롬프트/4단계_PCTF.md` | 4-1~4-5 범위·패키지 구조·신규 API 정의 |
| 6 | `작업프롬프트/2단계_PCTF.md` | 1차 리팩토링 순서·체크리스트 형식 참고 |
| 7 | `Report/04.S_Health_기능개선_보고서.md` | 4단계 구현 결과·API·TC 매핑(리팩토링 전 스냅샷) |
| 8 | `docs/02.S_Health_1차리팩토링_체크리스트.md` | 1차에서 완료한 항목 — **중복 작업 지양** |
| 9 | `Report/02.S_Health_1차리팩토링_보고서.md` | 1차 리네이밍·상수화 이력 |
| 10 | `docs/03.S_Health_단위테스트계획서.md` | TC-ID·경계·픽스처 |
| 11 | `Report/03.S_Health_단위테스트_보고서.md` | 3단계 Green·D-xx |
| 12 | `docs/03-2.S_Health_결함_레지스터.md` | DEF-xx — 리팩토링 중 수정 금지 항목 식별 |
| 13 | `docs/03-2.S_Health_권장사항_레지스터.md` | REC-xx(Refactor) — 4-2 후보 목록 |
| 14 | `Report/03-2.S_Health_결함및권장사항_통합보고서.md` | Test-Locked·4단계 이관 정리 |
| 15 | `docs/01.S_Health_코드스멜_분석보고서.md` | 4단계 후 잔존 스멜 재확인 |
| 16 | `src/main/java/com/bestreviewer/*.java` | 실제 리팩토링 대상 |
| 17 | `src/test/java/com/bestreviewer/*Test.java` | 회귀 기준(Green 유지) |

**Cursor 첨부 권장:**  
`@작업프롬프트/4-2단계_PCTF.md` `@작업프롬프트/4단계_PCTF.md` `@작업시나리오` `@SHealthRequirements.txt` `@.cursorrules`  
`@Report/04.S_Health_기능개선_보고서.md` `@docs/02.S_Health_1차리팩토링_체크리스트.md`  
`@docs/03-2.S_Health_결함_레지스터.md` `@docs/03-2.S_Health_권장사항_레지스터.md`  
`@src/main/java/com/bestreviewer/` `@src/test/java/com/bestreviewer/`

---

## [T] Task

```
다음 4가지를 **반드시 이 순서대로** 수행하세요. 각 하위 단계 완료 후 mvn test로 Green을 확인합니다.
대상은 **4단계에서 추가·분리·변경된 코드**를 우선하고, 2단계에서 이미 정리된 SHealth 레거시 패턴은 중복 개선하지 않습니다.

### 4-2-0. 시작 전 스캔 (코드 수정 전)
- Report/04.S_Health_기능개선_보고서.md §1~§2와 실제 src/main 패키지 구조 대조
- docs/03-2 REC-xx 중 유형 Refactor 항목 → 4-2 백로그 표(체크리스트 §0에 기재)
- 2단계 체크리스트에서 이미 ✅인 항목은 "1차 완료"로 표시하고 4-2에서 재작업하지 않음
- mvn clean test → Green 확인 후 4-2-1 진행

### 4-2-1. 네이밍 개선 (4단계 신규·변경 범위)
- 클래스·메서드·필드: 축약·모호한 이름 개선 (예: type → bmiCategoryType, users → loadedUsers 등 맥락에 맞게)
- 4단계 서비스·DTO: Statistics / Imputation / Reader 역할이 이름에 드러나게 정리
- boolean·Predicate: isNormalBmiRange, hasValidHeight 등 긍정형·도메인 용어
- package-private·테스트용 메서드: TS-xx 연계 주석은 유지하되 이름은 구현 의도 반영
- public API 메서드명·시그니처 변경 금지(필요 시 @Deprecated + 위임만, TC 갱신 시 문서화)

### 4-2-2. 하드코드 및 전역(인스턴스) 상태 제거
- 4단계 코드에 남은 매직 넘버: 18.5, 23, 25, 100/200/300/400, 20~70, +10 → HealthConstants·BmiCategory로 일원화
- SHealth·서비스 클래스의 mutable 공유 상태 검토:
  - calculateBmi 호출마다 null/clear 패턴이 분산되어 있으면 단일 초기화 메서드로 응집
  - 불필요한 static mutable 필드 제거(테스트 격리)
- 체중·키 impute에 중복된 나이대 루프·0 판별 → 공통 추상화(동작 동일)
- IOException 처리: printStackTrace만 남기지 않도록 정리 검토(동작 변경 시 TC·보고서 Before/After)

### 4-2-3. 함수 추출 (Extract Method)
- MissingValueImputationService: weight/height extractor 공통 흐름의 private 헬퍼 추출
- AgeGroupStatisticsService / OverallBmiStatisticsService: 비율 계산·분류 카운트 공통 로직 추출
- SHealth 파사드: calculateBmi 단계를 private 메서드로 분리(읽기→보정→계산→집계 순서 유지)
- CsvUserRecordReader: 파싱·검증·레코드 생성 단계 분리
- 각 메서드는 한 가지 책임; 4-2에서는 **클래스 파일 추가 분리(SRP 2차)**는 최소화(과도한 파일 쪼개기 금지)

### 4-2-4. 반복·중복 제거 (DRY)
- 체중 0 보정 vs 키 0 보정: 동일 알고리즘 패턴 단일 구현 + Strategy/Function 인자
- 나이대별 vs 전체 BMI 비율: 분모·분자 계산·type→BmiCategory 매핑 중복 제거
- BMI 분류 if/switch 체인: BmiClassifier 단일 진입점 유지·강화
- belongsToAgeGroup·나이대 루프: AgeGroupHelper 등 기존 헬퍼 재사용, 4단계에 새로 생긴 중복 루프 제거
- Stream/filter/collect 패턴이 동일하면 private 메서드 또는 공통 유틸(동작·순서 동일)

### 공통 제약
- 3·4단계 *Test 전부 Green 유지; 실패 시 해당 4-2-x 단계만 롤백 후 재시도
- DEF-xx(Test-Locked) 동작을 리팩토링으로 바꾸지 않음 — 변경 필요 시 보고서에 "의도적 제외" 명시
- diff는 리뷰 가능한 크기(단계별 커밋 권장)
- 완료: mvn clean test
```

---

## [F] Format

### 단계별 체크리스트 + 검증 방법

각 하위 단계(4-2-1 ~ 4-2-4)를 **완료할 때마다** 체크리스트를 갱신하고 검증합니다.  
실패 시 **해당 단계 변경만 롤백**한 뒤 원인을 수정하고 다시 검증합니다.

| 단계 | 체크리스트 | 검증 방법 |
|------|------------|-----------|
| **시작 전 (4-2-0)** | [ ] 4단계 보고서·패키지 구조 확인<br>[ ] REC Refactor 백로그 §0 기재<br>[ ] 2단계 ✅ 항목과 중복 작업 구분<br>[ ] 기준선 Green | `mvn clean test` |
| **4-2-1 네이밍** | [ ] 4단계 클래스·메서드·필드 의미화<br>[ ] public API 시그니처 유지<br>[ ] 테스트용 package-private 이름 정리 | `mvn test` |
| **4-2-2 하드코드·전역** | [ ] 매직 넘버 HealthConstants/BmiCategory 일원화<br>[ ] mutable 상태·초기화 패턴 정리<br>[ ] 체중·키 impute 공통화(동작 동일)<br>[ ] 비즈니스 규칙 수치 변경 없음 | `mvn test` |
| **4-2-3 함수 추출** | [ ] Imputation·Statistics·SHealth·Reader 추출<br>[ ] 처리 순서: 읽기→체중→키→BMI→집계 유지<br>[ ] 파사드 얇게 유지 | `mvn test` |
| **4-2-4 DRY** | [ ] 체중/키 보정 DRY<br>[ ] 나이대/전체 비율 집계 DRY<br>[ ] 분류·나이대 헬퍼 단일화<br>[ ] 신규 기능·SRP 3차 분리 미포함 | `mvn test` |
| **최종** | [ ] 4-2-1~4-2-4 전체 완료<br>[ ] `docs/04-2.S_Health_2차리팩토링_체크리스트.md` 작성<br>[ ] `Report/04-2.S_Health_2차리팩토링_보고서.md` 작성 | `mvn clean test` |

**검증 명령**

```bash
# 각 하위 단계(4-2-1 ~ 4-2-4) 완료 직후
mvn test

# 4-2단계 전체 완료 시
mvn clean test
```

---

### 산출물

```
docs/ = 상세 체크리스트·단계별 검증 기록 (5단계·회고 시 @ 첨부)
Report/ = 작업 결과 요약 (발표·회고 입력)

### 산출물 1 — 2차 리팩토링 체크리스트 (필수, docs)
- 경로: docs/04-2.S_Health_2차리팩토링_체크리스트.md
- 미작성 시 4-2단계 완료로 보지 않음
- 형식: docs/02.S_Health_1차리팩토링_체크리스트.md 와 동일하게 표·체크 위주
- 필수 목차:
  ## 0. 4-2 백로그 (REC-xx·4단계 보고서 §기반, 2단계와 중복 제외)
  ## 1. 참조 문서 목록 (본 PCTF [C] 표 요약)
  ## 2. 단계별 체크리스트 + 검증 (4-2-0 ~ 4-2-4, [ ] → ✅/❌, 실행 일시·Green/Red)
  ## 3. 대상 파일·클래스 매핑 (4-2-1~4-4별 수정 파일)
  ## 4. 1차(2단계) vs 2차(4-2) 완료 항목 대조표
  ## 5. 최종 mvn clean test 로그 요약
- 상단에 Report 링크: `Report/04-2.S_Health_2차리팩토링_보고서.md`

### 산출물 2 — 리팩토링된 코드 (필수)
- src/main/java/com/bestreviewer/ — 4단계 신규·변경 클래스 정리
- src/test/java/com/bestreviewer/ — 필요 시 import·이름만 수정, **기대값·동작 변경 금지**

### 산출물 3 — 2차 리팩토링 보고서 (필수, Report)
- 경로: Report/04-2.S_Health_2차리팩토링_보고서.md
- docs 체크리스트의 **요약본** — 상세 검증 기록은 docs에 둠
- 상단 docs 링크: `docs/04-2.S_Health_2차리팩토링_체크리스트.md`
- 필수 목차:
  ## 1. 수행 요약 (4-2-1~4-4 ✅/❌, docs §2 링크)
  ## 2. 4단계 대비 구조·변경 범위 (mermaid 또는 표)
  ## 3. 주요 리네이밍·상수화 목록
  ## 4. 추출·통합한 메서드·클래스 책임
  ## 5. DRY로 제거한 중복 (체중/키, 집계, 분류 등)
  ## 6. API 호환성·Test-Locked DEF 유지·REC-xx 반영 여부
  ## 7. mvn clean test 결과 (성공·실패·스킵)
- 한국어·표 위주

### 작성 순서 (권장)
1) 코드 리팩토링 (4-2-1 → 4-2-4, 단계마다 mvn test)
2) docs/04-2.S_Health_2차리팩토링_체크리스트.md — 검증 결과 반영
3) Report/04-2.S_Health_2차리팩토링_보고서.md — docs 기반 요약

### 범위 제외
- 5단계 회고·발표 슬라이드 본문
- 4단계 신규 요구 추가(새 API·새 FR)
- DEF-xx 해소를 위한 명세-구현 갭 수정(별도 작업)

### 완료 검증
- [ ] docs/04-2.S_Health_2차리팩토링_체크리스트.md 존재, §2 전 단계 기재
- [ ] Report/04-2.S_Health_2차리팩토링_보고서.md 존재, §1~§7 기재
- [ ] 4-2-1~4-4 순서 준수, 2단계와 중복 최소화
- [ ] mvn clean test Green
```

---

## 4단계와의 관계

| 하위 | 내용 | 산출물 |
|------|------|--------|
| **4-1** (4단계 [T]) | SRP·신규 API·4단계 TC | `src/main/...`, `Report/04.S_Health_기능개선_보고서.md` |
| **4-2** (본 문서) | 신규 기능 코드 **2차 클린코드** | `docs/04-2.S_Health_2차리팩토링_체크리스트.md`, `Report/04-2.S_Health_2차리팩토링_보고서.md` |

- 4-2는 **기능을 넣은 뒤** 코드 품질을 다듬는 단계이다. 2단계(1차)와 동일한 4항목 순서이나 **범위는 4단계 산출물**에 한정한다.
- 5단계 착수 전 `docs/04-2.*` + `Report/04-2.*` + `Report/04.*` 를 `@` 첨부하는 것을 권장한다.

---

## 복사용 (한 블록)

```
첨부:
@작업프롬프트/4-2단계_PCTF.md
@작업프롬프트/4단계_PCTF.md
@작업시나리오
@SHealthRequirements.txt
@.cursorrules
@Report/04.S_Health_기능개선_보고서.md
@docs/02.S_Health_1차리팩토링_체크리스트.md
@Report/02.S_Health_1차리팩토링_보고서.md
@docs/03.S_Health_단위테스트계획서.md
@Report/03.S_Health_단위테스트_보고서.md
@docs/03-2.S_Health_결함_레지스터.md
@docs/03-2.S_Health_권장사항_레지스터.md
@Report/03-2.S_Health_결함및권장사항_통합보고서.md
@src/main/java/com/bestreviewer/
@src/test/java/com/bestreviewer/

[P] 4단계 신규·분리 코드 2차 리팩토링 시니어 Java. SHealthRequirements.txt 규칙·3·4단계 TC 동작 유지. 신규 기능·DEF 수정 금지.

[C] 4단계 Green·SRP 클래스 존재. 2단계=1차(SHealth 레거시), 4-2=2차(4단계 범위). 순서: 네이밍→하드코드·전역→함수 추출→DRY.
참조 문서:
- 작업프롬프트/4-2단계_PCTF.md — 본 단계 PCTF
- 작업프롬프트/4단계_PCTF.md — 4-1~4-5·패키지·API
- 작업프롬프트/2단계_PCTF.md — 1차 리팩토링 순서·체크리스트 형식
- SHealthRequirements.txt — FR/TS·경계값 단일 기준
- .cursorrules — 리팩토링·테스트·API 호환
- 작업시나리오 — 2단계 4항목·4단계 기능 목록
- Report/04.S_Health_기능개선_보고서.md — 4단계 구현·TC 매핑
- docs/02.S_Health_1차리팩토링_체크리스트.md — 1차 완료 항목(중복 작업 제외)
- Report/02.S_Health_1차리팩토링_보고서.md — 1차 변경 이력
- docs/03.S_Health_단위테스트계획서.md · Report/03.S_Health_단위테스트_보고서.md — 회귀 TC
- docs/03-2.S_Health_결함_레지스터.md · docs/03-2.S_Health_권장사항_레지스터.md · Report/03-2.* — Test-Locked·REC Refactor

[T] 순서 고정 (각 단계 후 mvn test, 실패 시 해당 단계만 롤백):
0) 4-2-0 스캔·Green 확인·REC 백로그
1) 4-2-1 네이밍 — 4단계 클래스·API 주변
2) 4-2-2 하드코드·전역 — 상수 일원화·상태·impute 공통화
3) 4-2-3 함수 추출 — Imputation·Statistics·SHealth·Reader
4) 4-2-4 DRY — 체중/키·집계·분류·나이대 중복 제거
금지: 신규 기능, DEF 해소용 규칙 변경, 과도한 클래스 분리.

[F] 산출물 (필수):
  1) docs/04-2.S_Health_2차리팩토링_체크리스트.md — §0 백로그·§1 참조문서·§2 단계별 체크·§4 1차 vs 2차·§5 mvn 결과
  2) src/main/java/com/bestreviewer/ 리팩토링 코드
  3) Report/04-2.S_Health_2차리팩토링_보고서.md — §1~§7 요약
mvn clean test 전체 Green. 한국어.
```
