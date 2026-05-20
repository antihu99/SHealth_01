# 5단계 — 결함 관리 및 품질 보고 체계 (PCTF)

_4-2단계 완료 후 · 회고(6단계) 직전 · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | **결함 분류·보고 표준화·품질 메트릭**을 설계하는 **시니어 QA·품질 엔지니어** (코드 변경 없음) |
| **C** | Context | 1~4-2단계 산출물·3-2 DEF/REC 레지스터·최종 `mvn clean test` Green 상태 |
| **T** | Task | 결함 분류 체계 수립 + 보고서 템플릿 정의 + 품질 메트릭 수집 계획 작성 |
| **F** | Format | `docs/05.*` 3종(필수) + `Report/05.S_Health_결함관리체계_보고서.md` (필수) |

---

## [P] Persona

```
당신은 소프트웨어 품질 관리(QA)와 결함 추적 체계를 설계하는 시니어 품질 엔지니어입니다.
1~4-2단계에서 이미 기록된 결함(DEF-xx)·권장(REC-xx)·테스트·리팩토링 산출물만을 근거로 삼습니다.
추측으로 새 결함을 발견하지 않고, 체계·템플릿·메트릭 **정의**에 집중합니다.
비즈니스 규칙 판단은 SHealthRequirements.txt를 단일 기준으로 하며,
명세-구현 갭은 3-2단계 DEF 레지스터와 정합되게 분류합니다.
프로덕션·테스트 코드는 이 단계에서 수정하지 않습니다(문서·표·템플릿만).
```

---

## [C] Context

```
- 프로젝트: SHealth BMI — Java 8, Maven, JUnit 5 · 생성형 AI 활용 Activities
- 선행: 4-2단계 2차 리팩토링·`mvn clean test` Green 권장
- 3-2단계와의 관계:
  | 구분 | 3-2단계 | 5단계 (본 단계) |
  |------|---------|-----------------|
  | 목적 | 1~3단계 이슈 **통합·ID 병합** | 결함 **관리 체계·보고·메트릭** 표준화 |
  | 산출물 | docs/03-2.* 레지스터, Report/03-2.* | docs/05.* 체계·템플릿·메트릭 계획 |
  | 코드 | 수정 금지 | 수정 금지 |
- 입력 산출물 (존재하는 파일만 참조):
  | 단계 | 경로 | 활용 내용 |
  |------|------|-----------|
  | 1 | docs/01.*, Report/01.* | BUG/EDGE, 스멜, 초기 품질 기준선 |
  | 2 | docs/02.*, Report/02.* | 1차 리팩토링 전·후, 잔여 스멜 |
  | 3 | docs/03.*, Report/03.*, *Test.java | TC-ID, TS 매핑, D-xx |
  | 3-2 | docs/03-2.*, Report/03-2.* | DEF-xx, REC-xx, 상태·우선순위 |
  | 4 | Report/04.* | 기능·SRP·신규 API·TC |
  | 4-2 | docs/04-2.*, Report/04-2.* | 2차 리팩토링·최종 구조 |
  | 기준 | SHealthRequirements.txt | FR/TS, 분류·보정 규칙 |
  | 검증 | mvn clean test 로그 | TC 건수·Green/Red |
- 금지: 신규 기능 구현, DEF 해소용 코드 수정, 6단계 회고 본문 선작성
```

### 참조 문서 (우선순위·용도)

| 우선순위 | 경로 | 용도 |
|----------|------|------|
| 1 | `SHealthRequirements.txt` | FR/TS·명세-구현 갭 분류 기준 |
| 2 | `.cursorrules` | 테스트·리팩토링·보고 규칙 |
| 3 | `작업프롬프트/5단계_PCTF.md` | 본 단계 PCTF |
| 4 | `작업프롬프트/3-2단계_PCTF.md` | DEF/REC ID·상태 정의 (3-2와 정합) |
| 5 | `docs/03-2.S_Health_결함_레지스터.md` | DEF-xx 원본·증거·TC 연결 |
| 6 | `docs/03-2.S_Health_권장사항_레지스터.md` | REC-xx·P0~P3 |
| 7 | `Report/03-2.S_Health_결함및권장사항_통합보고서.md` | 통합 요약·진입 체크 |
| 8 | `docs/03.S_Health_단위테스트계획서.md` | TC-ID·TS-01~06 |
| 9 | `Report/03.S_Health_단위테스트_보고서.md` | TC 실행·D-xx |
| 10 | `Report/04.S_Health_기능개선_보고서.md` | 4단계 API·DEF 해소 여부 |
| 11 | `docs/04-2.S_Health_2차리팩토링_체크리스트.md` | 최종 검증·구조 |
| 12 | `Report/04-2.S_Health_2차리팩토링_보고서.md` | 4-2 완료 상태 |
| 13 | `src/test/java/com/bestreviewer/*Test.java` | TC·@DisplayName 증거 |
| 14 | `작업시나리오` | 전체 단계 흐름 |

**Cursor 첨부 권장:**  
`@작업프롬프트/5단계_PCTF.md` `@SHealthRequirements.txt` `@.cursorrules` `@작업시나리오`  
`@docs/03-2.S_Health_결함_레지스터.md` `@docs/03-2.S_Health_권장사항_레지스터.md`  
`@Report/03-2.S_Health_결함및권장사항_통합보고서.md`  
`@docs/03.S_Health_단위테스트계획서.md` `@Report/03.S_Health_단위테스트_보고서.md`  
`@Report/04.S_Health_기능개선_보고서.md` `@docs/04-2.S_Health_2차리팩토링_체크리스트.md`  
`@Report/04-2.S_Health_2차리팩토링_보고서.md` `@src/test/java/com/bestreviewer/`

---

## [T] Task

```
다음 순서로 진행하세요. 코드·테스트 수정은 하지 않습니다.

### 5-1. 결함 분류 체계 수립
- 3-2 DEF-xx·REC-xx와 **정합**되는 분류 체계 정의 (기존 ID 폐기하지 않음)
- 필수 분류 축(표로 정의·예시 1행 이상):
  | 축 | 설명 | SHealth 예시 값 |
  |----|------|-----------------|
  | 유형(Type) | 결함 성격 | Functional / Spec-Gap / Data-Integrity / UI-API / Test-Gap / Tech-Debt |
  | 심각도(Severity) | 영향도 | Critical / High / Medium / Low |
  | 우선순위(Priority) | 처리 순서 | P0~P3 (3-2 REC와 매핑 표) |
  | 상태(Status) | 생명주기 | Open / In-Progress / Resolved / Test-Locked / Deferred / Won't-Fix / Closed |
  | 발견 단계(Origin) | 1·2·3·3-2·4·4-2·Review | |
  | 근거 유형(Evidence) | Code / TC / Spec / Log / Review | |
- DEF-xx 전건(또는 docs §3 전건)을 새 체계로 **재분류 매핑 표** 작성
- 명세-구현 갭·Test-Locked·의도적 레거시 구분 규칙 1문단
- 결함 vs 권장사항(REC) 구분 규칙 — 3-2와 동일 원칙 유지

### 5-2. 보고서 템플릿 정의
- 실무에서 바로 복사해 쓸 수 있는 **Markdown 템플릿** 4종 이상:
  1) **결함 등록·갱신 카드** (ID, 제목, GWT, 분류, 상태, 증거, TC, 담당, 이력)
  2) **결함 주간·단계 요약** (신규/해결/잔여, P0/P1, 추이 표)
  3) **테스트 실행·회귀 요약** (mvn test, TC-ID, Pass/Fail, 픽스처)
  4) **요구사항 추적 매트릭스** (FR/TS ↔ TC-ID ↔ DEF-xx ↔ 상태)
  5) (선택) **릴리스·마일스톤 품질 게이트 체크리스트**
- 각 템플릿: 목적·작성 시점·필수 필드·작성 예시 1건(SHealth DEF-01 등)

### 5-3. 품질 메트릭 수집 계획
- 수집할 메트릭 정의(최소 8개, 표):
  | 메트릭 ID | 이름 | 정의·산식 | 수집 주기 | 데이터 출처 | 담당/도구 |
  |-----------|------|-----------|-----------|-------------|-----------|
  | QM-01 | TC 통과율 | Pass/(Pass+Fail)×100 | mvn test마다 | surefire | Maven |
  | QM-02 | TC 건수 | *Test 메서드 수 | 단계 완료 시 | src/test | 수동 |
  | QM-03 | DEF 오픈 건수 | Status=Open·Deferred | 주간/단계 | docs/03-2, 05 | 레지스터 |
  | … | … | … | … | … | … |
- SHealth 프로젝트 **기준선·목표값** 1행 (예: TC 통과율 100%, P0 오픈 0)
- 1~5단계별 **수집 시점·담당·저장 위치** (docs/05 또는 Report/05 부록)
- 6단계 회고·발표에 넘길 **메트릭 스냅샷 표** 양식 포함

### 5-4. docs 폴더 문서화 (3종 필수)
- `docs/05.S_Health_결함분류체계.md` — §분류 축, §DEF 매핑, §상태 전이, §3-2 정합
- `docs/05.S_Health_보고서템플릿.md` — §템플릿 4~5종 전문 + 작성 가이드
- `docs/05.S_Health_품질메트릭수집계획.md` — §메트릭 정의, §수집 일정, §저장·갱신 규칙

### 5-5. 통합 보고서 (Report)
- `Report/05.S_Health_결함관리체계_보고서.md` — docs 3종 **요약** + 6단계 회고 입력 체크리스트

완료 기준: docs 3종 + Report 1종, DEF 전건 분류 매핑, 템플릿 4종 이상, 메트릭 8개 이상
```

---

## [F] Format

```
docs/ = 상세 체계·템플릿·메트릭 (6단계·운영 시 @ 첨부)
Report/ = 요약·회고 연계 (발표·경영 보고용)

### 산출물 1 — 결함 분류 체계 (필수, docs)
- 경로: docs/05.S_Health_결함분류체계.md
- 필수 목차:
  ## 1. 문서 목적·범위·갱신 이력
  ## 2. 분류 축 정의 (유형·심각도·우선순위·상태·발견단계·근거)
  ## 3. 상태 전이 다이어그램 (mermaid)
  ## 4. DEF-xx → 분류 매핑 표 (전건)
  ## 5. 3-2단계 ID·상태와의 정합 규칙
  ## 6. 결함 vs 권장사항(REC) 구분

### 산출물 2 — 보고서 템플릿 (필수, docs)
- 경로: docs/05.S_Health_보고서템플릿.md
- 필수 목차:
  ## 1. 템플릿 사용 가이드 (작성 시점·독자)
  ## 2. 결함 등록·갱신 카드
  ## 3. 결함 주간·단계 요약
  ## 4. 테스트 실행·회귀 요약
  ## 5. 요구사항 추적 매트릭스 (FR/TS–TC–DEF)
  ## 6. (선택) 품질 게이트 체크리스트
  ## 부록. SHealth 작성 예시 1건

### 산출물 3 — 품질 메트릭 수집 계획 (필수, docs)
- 경로: docs/05.S_Health_품질메트릭수집계획.md
- 필수 목차:
  ## 1. 목적·범위
  ## 2. 메트릭 정의표 (QM-xx, 8개 이상)
  ## 3. 기준선·목표값 (SHealth 현재 스냅샷)
  ## 4. 단계별 수집 일정 (1~5단계·mvn test)
  ## 5. 저장·갱신·담당 규칙
  ## 6. 6단계 회고용 스냅샷 양식

### 산출물 4 — 통합 보고서 (필수, Report)
- 경로: Report/05.S_Health_결함관리체계_보고서.md
- 상단 docs 링크 3종 명시
- 필수 목차:
  ## 1. Executive Summary (3~5 bullet)
  ## 2. 분류 체계 요약 (docs §2~§3 링크)
  ## 3. 템플릿 목록·용도 (docs §2~§6 링크)
  ## 4. 메트릭 요약·현재 스냅샷 (docs §2~§3 링크)
  ## 5. DEF·REC·TC 현황 (건수 표)
  ## 6. 6단계 회고·발표 입력 체크리스트
- 한국어·표 위주

### 작성 순서 (권장)
1) docs/05.S_Health_결함분류체계.md
2) docs/05.S_Health_보고서템플릿.md
3) docs/05.S_Health_품질메트릭수집계획.md
4) Report/05.S_Health_결함관리체계_보고서.md

### 범위 제외
- 프로덕션/테스트 코드 변경
- 6단계 회고 본문·슬라이드 전체 작성
- 신규 DEF 발굴(근거 없는 항목 추가)

### 완료 검증
- [ ] docs/05 3종 존재, 필수 목차 충족
- [ ] Report/05 존재, docs 링크·건수 일치
- [ ] DEF-xx 분류 매핑 전건 또는 미발견 사유
- [ ] 템플릿 4종 이상, 메트릭 8개 이상
- [ ] mvn 미실행 또는 Green(코드 미변경)
```

---

## 단계 관계

| 하위 | 내용 | 산출물 |
|------|------|--------|
| **3-2** | 결함·권장 **통합** | `docs/03-2.*`, `Report/03-2.*` |
| **5** (본 문서) | 결함 **관리 체계** | `docs/05.*`, `Report/05.*` |
| **6** | 회고·발표 | `Report/06.*` |

- 6단계 착수 전 `docs/05.*` + `Report/05.*` + `docs/03-2.*` + `Report/04-2.*` 를 `@` 첨부 권장.

---

## 복사용 (한 블록)

```
첨부:
@작업프롬프트/5단계_PCTF.md
@작업시나리오
@SHealthRequirements.txt
@.cursorrules
@docs/03-2.S_Health_결함_레지스터.md
@docs/03-2.S_Health_권장사항_레지스터.md
@Report/03-2.S_Health_결함및권장사항_통합보고서.md
@docs/03.S_Health_단위테스트계획서.md
@Report/03.S_Health_단위테스트_보고서.md
@Report/04.S_Health_기능개선_보고서.md
@docs/04-2.S_Health_2차리팩토링_체크리스트.md
@Report/04-2.S_Health_2차리팩토링_보고서.md
@src/test/java/com/bestreviewer/

[P] 결함 분류·보고 표준·품질 메트릭 설계 시니어 QA. 1~4-2 산출물·3-2 DEF/REC만 근거. 코드·TC 수정 금지.

[C] 4-2 Green 후, 6단계 회고 직전. 3-2=이슈 통합, 5=관리 체계·템플릿·메트릭.
참조 문서:
- 작업프롬프트/5단계_PCTF.md — 본 단계
- 작업프롬프트/3-2단계_PCTF.md — DEF/REC·상태
- docs/03-2.S_Health_결함_레지스터.md · docs/03-2.S_Health_권장사항_레지스터.md
- Report/03-2.* · docs/03.* · Report/03.* · Report/04.* · docs/04-2.* · Report/04-2.*
- SHealthRequirements.txt · .cursorrules · 작업시나리오

[T] 순서:
1) 5-1 결함 분류 체계 + DEF-xx 매핑 → docs/05.S_Health_결함분류체계.md
2) 5-2 보고서 템플릿 4종+ → docs/05.S_Health_보고서템플릿.md
3) 5-3 품질 메트릭 수집 계획 QM 8개+ → docs/05.S_Health_품질메트릭수집계획.md
4) 5-5 통합 요약 → Report/05.S_Health_결함관리체계_보고서.md

[F] docs 3종 + Report/05 필수. DEF 전건 분류. 템플릿 4+. 메트릭 8+. 한국어. 코드 수정 없음.
```
