# 3-2단계 — 결함·권장사항 통합 보고서 (PCTF)

_3단계 하위 (3-1 TC 구현·실행 후) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | 1~3단계 산출물을 교차 검토해 **결함·권장사항을 통합·중복 제거**하는 **시니어 QA·기술 문서 작성자** |
| **C** | Context | 1단계 스멜·잠재 BUG, 2단계 리팩토링 후 유지된 레거시, 3단계 Red→Green·TC로 **고정된 동작**이 분산 기록된 상태 |
| **T** | Task | 단계별 이슈를 **단일 ID 체계**로 병합·상태(미해결/TC고정/해결/4단계 이관) 부여·우선순위·4단계 매핑 |
| **F** | Format | `docs/03-2.*` 결함·권장 **레지스터**(필수) + `Report/03-2.*` 통합 보고서(필수) · 코드 수정 없음 |

---

## [P] Persona

```
당신은 Java 레거시 BMI 프로젝트의 QA·기술 문서 담당 시니어 엔지니어입니다.
1~3단계에서 이미 작성된 보고서·체크리스트·단위테스트 결과만을 근거로 삼습니다.
추측으로 새 결함을 만들지 않고, 출처(문서·TC·코드 라인)가 없는 항목은 "근거 부족"으로 표시합니다.
비즈니스 규칙 판단은 SHealthRequirements.txt를 단일 기준으로 하며, 명세와 구현이 다르면 "명세-구현 갭"으로 분류합니다.
프로덕션 코드·테스트 코드는 이 단계에서 수정하지 않습니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI — Java 8, Maven, JUnit 5
- 선행: 3-1(또는 3단계)에서 `*Test` 구현·`mvn clean test` Green 완료 권장
- 통합 대상(입력) — 존재하는 파일만 참조, 없으면 보고서 §입력 산출물에 "미작성" 표기:
  | 단계 | 경로 | 추출할 내용 |
  |------|------|-------------|
  | 1 | docs/01.S_Health_코드스멜_분석보고서.md | BUG-01~05, EDGE-01~04, 스멜 High, 2단계 Top 5 |
  | 1 | Report/01.S_Health_code_smell_보고서.md | 잠재 결함 요약, 경계값 권장 |
  | 2 | docs/02.S_Health_1차리팩토링_체크리스트.md | 2-1~2-4 완료·미완료, 검증 결과 |
  | 2 | Report/02.S_Health_1차리팩토링_보고서.md | 의도적 레거시 유지, 3·4단계 권장 후속 |
  | 3 | docs/03.S_Health_단위테스트계획서.md | TC-ID, 기대 동작·경계 설계 |
  | 3 | Report/03.S_Health_단위테스트_보고서.md | D-01~03, §6 4단계 보강 TC, 계획 대비 차이 |
  | 3 | src/test/java/com/bestreviewer/*Test.java | @DisplayName·고정된 기대값(현 동작 증거) |
  | 기준 | SHealthRequirements.txt | TS-01~06, 분류·나이대·보정 규칙 |
- ID 매핑 규칙(병합 시):
  - BUG-01(1단계) ≈ D-02(3단계) → 통합 ID 예: **DEF-01** (BMI=25 미분류)
  - BUG-02 ≈ D-01(빈 나이대 NaN) → **DEF-02**
  - BUG-03 → **DEF-03** (전원 weight=0)
  - BUG-04 → **DEF-04** (height=0, 4단계)
  - BUG-05 ≈ D-03 → **DEF-05** (I/O silent)
  - 1단계 스멜·2단계 구조 권장 → **REC-** 접두 권장사항 ID
- 상태 값(통합 표 공통):
  | 상태 | 의미 |
  | Open | 미수정, TC 미고정 |
  | Test-Locked | 3단계 TC로 현 동작 고정(의도적 레거시 포함) |
  | Resolved | 2·3단계에서 코드 수정으로 해결됨 |
  | Deferred-4 | 4단계 기능·SRP·보정 범위로 이관 |
  | Won't-Fix | 명세 재확인 후 유지 결정(근거 필수) |
- 금지: 신규 기능 구현, 리팩토링, TC 추가·삭제, 프로덕션 동작 변경
```

**Cursor 첨부 권장:**  
`@작업프롬프트/3-2단계_PCTF.md` `@SHealthRequirements.txt` `@.cursorrules`  
`@docs/01.S_Health_코드스멜_분석보고서.md` `@Report/01.S_Health_code_smell_보고서.md`  
`@docs/02.S_Health_1차리팩토링_체크리스트.md` `@Report/02.S_Health_1차리팩토링_보고서.md`  
`@docs/03.S_Health_단위테스트계획서.md` `@Report/03.S_Health_단위테스트_보고서.md`  
`@src/test/java/com/bestreviewer/` `@SHealth.java`

---

## [T] Task

```
다음 순서로 진행하세요. 코드·테스트 수정은 하지 않습니다.

### 3-2-1. 입력 산출물 수집·교차표 작성
- 1·2·3단계 보고서에서 "결함·버그·이슈·갭·권장·후속·TODO" 키워드 섹션을 전수 스캔
- 동일 이슈는 하나의 통합 항목으로 묶고, 원본 ID(BUG-xx, D-xx, EDGE-xx)를 "출처" 열에 병기
- 2단계에서 "의도적 유지"로 명시된 항목은 상태를 Test-Locked 또는 Deferred-4로 구분

### 3-2-2. 결함 통합 레지스터 (DEF-xx)
각 항목에 대해 반드시 기재:
- 제목(한 줄), 심각도(High/Medium/Low), 상태, 요구사항(TS-xx·§), 현재 구현 요약
- 명세 vs 구현: 일치 / 갭 / 미정(근거 부족)
- 증거: 코드 위치(파일:라인) 또는 `*Test` @DisplayName·메서드명
- 3단계 TC 연결: TC-ID 또는 "TC 없음"
- 4단계 대응: 수정·신규 API·SRP·추가 TC 중 하나

필수 포함 후보(입력에 있을 때 반드시 통합표에 포함):
| 통합 후보 | 1단계 | 3단계 | 비고 |
|-----------|-------|-------|------|
| BMI=25 미분류 | BUG-01 | D-02 | 명세 ≥25 vs 코드 >25 |
| 빈 나이대 비율 NaN/0% | BUG-02 | D-01 | sum==0, usersInAgeGroup==0 |
| 나이대 전원 weight=0 | BUG-03 | (TC 있으면 연결) | ageCount 나눗셈 |
| height=0 | BUG-04 | — | 4단계 키 보정 |
| I/O 실패 silent | BUG-05 | D-03 | printStackTrace, count=0 |
| getBmiRatio 잘못된 인자 | EDGE-02 | — | 0.0 반환 |
| 나이 19·79 등 경계 | EDGE-01 | belongsToAgeGroup TC | 문서화 수준 |

### 3-2-3. 권장사항 통합 레지스터 (REC-xx)
- 1단계: 2단계 Top 5, 스멜 High, 경계값 @ParameterizedTest 권장
- 2단계: 3·4단계 권장 후속, 미완료 체크리스트(있을 경우)
- 3단계: §6 4단계 전 보강 TC, 계획 대비 변경 권고
- 유형: Refactor / Test / Feature(4단계) / Doc / Process
- 우선순위: P0(차단)·P1(높음)·P2(중간)·P3(낮음) + 근거 1줄

### 3-2-4. 단계별 진행·잔여 요약
- 1단계 → 2단계: 해결·부분 해결·유지된 스멜/결함
- 2단계 → 3단계: 리팩토링으로 나아진 점, TC로 고정된 레거시
- 3단계 → 4단계: 반드시 처리할 P0·P1 목록(최대 10건)

### 3-2-5. docs 폴더 문서화 (결함·권장사항 레지스터)
- `docs/03-2.S_Health_결함_레지스터.md` — DEF-xx 전건 상세 기록(증거·TC·명세 갭·4단계 대응)
- `docs/03-2.S_Health_권장사항_레지스터.md` — REC-xx 전건 상세 기록(유형·우선순위·조치·4단계 연계)
- docs ↔ Report ID·내용 **일치** (Report는 요약·docs는 추적·근거 원본)

### 3-2-6. 4단계 진입 체크리스트
- Green 유지 여부, Test-Locked 결함 목록, 4단계 신규 요구(키 0·정상 목록·전체 비율)와 DEF/REC 매핑 표

완료 기준: docs 레지스터 2종 + Report 통합 보고서 작성 완료, 중복 ID 없음, 모든 DEF 항목에 출처·증거·상태·4단계 대응 열 존재
```

---

## [F] Format

```
docs/ = 상세 레지스터·추적용 (4단계 작업 시 @ 첨부 우선)
Report/ = 작업 결과 요약·진입 체크리스트 (회고·발표용)
두 위치의 DEF-xx·REC-xx ID·건수는 반드시 일치한다.

### 산출물 1 — 결함 레지스터 (필수, docs)
- 경로: docs/03-2.S_Health_결함_레지스터.md
- 미작성 시 3-2단계 완료로 보지 않음
- 목적: 통합 결함(DEF-xx)의 **상세 기술 문서** — 증거·명세 갭·TC·4단계 대응을 추적
- 형식: `docs/02.S_Health_1차리팩토링_체크리스트.md` · `docs/03.S_Health_단위테스트계획서.md` 처럼 표·체크리스트 위주
- 필수 목차:
  ## 1. 문서 목적·범위·갱신 이력(일자)
  ## 2. ID 매핑 (BUG-xx / D-xx / EDGE-xx → DEF-xx)
  ## 3. 결함 상세 레지스터 (DEF-xx, 항목별 하위 절 또는 통합 표)
     필수 열: ID | 제목 | 심각도 | 상태 | TS/§ | 명세-구현 | 현재 동작 요약 | 증거(코드:라인) | 연결 TC | 출처 | 4단계 대응 | [ ] 4단계 완료
  ## 4. 명세-구현 갭 요약 (BMI 경계·나이대·I/O 등)
  ## 5. Test-Locked 목록 (수정 시 TC 갱신 필요 항목)
- 항목별 상세(표 아래 또는 별도 소절) 권장 필드:
  - 재현 조건(Given–When–Then 3줄)
  - 영향 범위(집계·비율·사용자 수)
  - 관련 코드 인용(```start:end:path```)

### 산출물 2 — 권장사항 레지스터 (필수, docs)
- 경로: docs/03-2.S_Health_권장사항_레지스터.md
- 미작성 시 3-2단계 완료로 보지 않음
- 목적: 통합 권장(REC-xx)의 **조치·우선순위·4단계 연계** 문서화
- 필수 목차:
  ## 1. 문서 목적·범위·갱신 이력(일자)
  ## 2. 우선순위 정의 (P0~P3 기준 1문단)
  ## 3. 권장사항 상세 레지스터 (REC-xx)
     필수 열: ID | 유형 | 우선순위 | 제목 | 내용 | 근거·출처 | 연관 DEF | 4단계 연계 | [ ] 완료
  ## 4. 유형별 묶음 (Refactor / Test / Feature / Doc / Process)
  ## 5. 4단계 착수 전 P0·P1 실행 목록 (체크리스트)
- 1단계 Top 5·3단계 §6 보강 TC·2단계 후속 권장은 REC-xx로 편입, 출처 단계 명시

### 산출물 3 — 통합 보고서 (필수, Report)
- 경로: Report/03-2.S_Health_결함및권장사항_통합보고서.md
- 미작성 시 3-2단계 완료로 보지 않음
- 목적: docs 레지스터를 바탕으로 한 **작업 결과 요약** (4·5·6단계·발표용)
- docs 2종의 **요약본** — 상세·코드 인용은 docs에 두고 Report는 표·개수·핵심만
- 상단에 docs 링크 명시:
  - `docs/03-2.S_Health_결함_레지스터.md`
  - `docs/03-2.S_Health_권장사항_레지스터.md`
- 필수 목차:
  ## 1. 문서 목적·범위·입력 산출물 목록(존재/미작성)
  ## 2. 통합 ID·건수 요약 (DEF N건 / REC M건, docs와 동일)
  ## 3. 결함 요약표 (DEF-xx — docs §3 링크)
  ## 4. 권장사항 요약표 (REC-xx — docs §3 링크)
  ## 5. 단계별 진행 요약 (1→2→3: 해결·고정·잔여)
  ## 6. 경계값·TC 커버리지 갭 (TS-01~06)
  ## 7. 4단계 진입 체크리스트 (P0/P1, Green, 이관 목록)
  ## 8. 부록 (핵심 DEF 3건 GWT 요약, 선택)

### 작성 순서 (권장)
1) docs/03-2.S_Health_결함_레지스터.md — DEF-xx 전건 상세
2) docs/03-2.S_Health_권장사항_레지스터.md — REC-xx 전건 상세
3) Report/03-2.S_Health_결함및권장사항_통합보고서.md — docs 기반 요약

### 품질 기준
- docs DEF 건수 ≥ 1단계 BUG 건수(병합 시 매핑 표에 사유)
- docs REC에 3단계 §6·2단계 후속·1단계 Top5 중 존재 항목 반영
- DEF·REC ID는 docs ↔ Report **완전 일치**, 제목·상태·우선순위 불일치 금지
- 동일 이슈: DEF=현상, REC=조치 권고 (중복 서술 지양)
- Test-Locked: docs 결함 레지스터 §5 + Report §6에 명시
- 코드 인용: docs에 `파일경로:시작-끝` 또는 코드 citation 블록

### 범위 제외
- 4단계 기능 구현·SRP 클래스 분리 실행
- 결함 수정을 위한 프로덕션/테스트 코드 변경
- 5·6단계 본문 선작성(docs·Report는 5·6단계 입력 자료)

### 완료 검증
- [ ] docs/03-2.S_Health_결함_레지스터.md 존재, §3 DEF 전건 기재
- [ ] docs/03-2.S_Health_권장사항_레지스터.md 존재, §3 REC 전건 기재
- [ ] Report/03-2.S_Health_결함및권장사항_통합보고서.md 존재, docs 링크·건수 일치
- [ ] DEF: BUG-01~05(또는 동등)·D-01~03 반영 또는 미발견 사유
- [ ] REC: P0/P1 최소 1건 이상(docs §5·Report §7)
- [ ] mvn clean test 미실행 또는 Green(코드 미변경)
```

---

## 3단계와의 관계

| 하위 | 내용 | 산출물 |
|------|------|--------|
| **3-1** (3단계 [T]) | TC 설계·구현·실행 | `docs/03.*계획서`, `*Test.java`, `Report/03.S_Health_단위테스트_보고서.md` |
| **3-2** (본 문서) | 결함·권장 **통합 정리** | `docs/03-2.S_Health_결함_레지스터.md`, `docs/03-2.S_Health_권장사항_레지스터.md`, `Report/03-2.S_Health_결함및권장사항_통합보고서.md` |

- `Report/03.S_Health_단위테스트_보고서.md` §4·§6는 **원본**으로 두고, 3-2에서 **전 단계 통합본**을 만든다.
- 4단계 착수 전 `docs/03-2.*` 레지스터 + `Report/03-2.*` 를 `@` 첨부하는 것을 권장한다.

---

## 복사용 (한 블록)

```
첨부:
@작업프롬프트/3-2단계_PCTF.md
@작업시나리오
@SHealthRequirements.txt
@.cursorrules
@docs/01.S_Health_코드스멜_분석보고서.md
@Report/01.S_Health_code_smell_보고서.md
@docs/02.S_Health_1차리팩토링_체크리스트.md
@Report/02.S_Health_1차리팩토링_보고서.md
@docs/03.S_Health_단위테스트계획서.md
@Report/03.S_Health_단위테스트_보고서.md
@src/test/java/com/bestreviewer/
@SHealth.java

[P] 1~3단계 산출물만 근거로 결함·권장사항을 통합·중복 제거하는 시니어 QA·기술 문서 작성자. SHealthRequirements.txt 단일 기준. 코드·TC 수정 금지.

[C] SHealth BMI Java 8. 3-1(TC Green) 완료 후 수행. 입력: 1단계 BUG/EDGE·스멜, 2단계 레거시 유지·권장 후속, 3단계 D-xx·§6 보강 TC. 통합 ID: DEF-xx(결함), REC-xx(권장). 상태: Open / Test-Locked / Resolved / Deferred-4 / Won't-Fix.

[T] 순서:
1) 1~3단계 보고서·체크리스트·*Test 전수 스캔 → 교차표·출처 병기
2) DEF-xx → docs/03-2.S_Health_결함_레지스터.md (상세·증거·TC·4단계 대응)
3) REC-xx → docs/03-2.S_Health_권장사항_레지스터.md (유형·P0~P3·4단계 연계)
4) docs 기반 → Report/03-2.S_Health_결함및권장사항_통합보고서.md (요약·진입 체크리스트)

[F] 산출물 (필수 3종, docs 2 + Report 1):
  1) docs/03-2.S_Health_결함_레지스터.md — DEF-xx 상세·Test-Locked·명세 갭
  2) docs/03-2.S_Health_권장사항_레지스터.md — REC-xx 상세·P0/P1 체크리스트
  3) Report/03-2.S_Health_결함및권장사항_통합보고서.md — docs 요약·4단계 진입 체크리스트
ID·건수 docs↔Report 일치. 한국어·표 위주. 코드·TC 수정 없음.
```
