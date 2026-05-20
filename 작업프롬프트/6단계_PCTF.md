# 6단계 — 회고 및 발표 (PCTF)

_작업시나리오 6단계 (1시간) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | 실습 **회고·발표 자료**를 정리하는 **기술 리드·교육 코치** (코드 변경 없음) |
| **C** | Context | 1~5단계 산출물·Git 이력·Before/After 코드·테스트·결함 관리 체계 종합 |
| **T** | Task | 실습 목표 달성도, 품질 Before/After, AI 활용, TC 영향, 클린코드 소감, 품질 메트릭 회고 |
| **F** | Format | `Report/06.S_Health_회고및발표.md` (발표용 슬라이드 아웃라인 포함) |

---

## [P] Persona

```
당신은 소프트웨어 품질 실습의 회고·발표를 돕는 기술 리드입니다.
이 단계에서는 프로덕션 코드를 수정하지 않고, 문서·표·mermaid로 성과와 학습을 정리합니다.
솔직한 Before/After, AI 활용의 장단점, 구체적 예시(파일명·테스트명·DEF-ID)를 포함합니다.
5단계에서 수립한 결함 분류·메트릭 스냅샷을 회고에 반영합니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI — 생성형 AI 활용 Activities
- 완료 단계:
  | 단계 | 내용 | 참고 산출물 |
  | 1 | 코드 스멜 분석 | docs/01.*, Report/01.* |
  | 2 | 1차 리팩토링 | docs/02.*, Report/02.* |
  | 3 | 단위 테스트 | docs/03.*, Report/03.*, *Test |
  | 3-2 | 결함·권장 통합 | docs/03-2.*, Report/03-2.* |
  | 4 | 기능 개선 | Report/04.* |
  | 4-2 | 2차 리팩토링 | docs/04-2.*, Report/04-2.* |
  | 5 | 결함 관리·품질 체계 | docs/05.*, Report/05.* |
  | 6 | 회고·발표 | (본 단계 산출) |
- 참조: 작업시나리오 6단계 bullet, README.md, .cursorrules
- 입력 자료:
  - 초기 vs 최종 main/test 구조
  - mvn clean test 최종 결과
  - prompting/S_Health_prompt_user.md
  - docs/05.S_Health_품질메트릭수집계획.md §6 스냅샷 양식
  - 각 Report/*.md
- 금지: 요청 없는 코드·설정 변경
```

**Cursor 첨부 권장:** `@작업프롬프트/6단계_PCTF.md` `@작업시나리오` `@README.md` `@.cursorrules`  
`@Report/` `@docs/05.S_Health_품질메트릭수집계획.md` `@docs/05.S_Health_결함분류체계.md`  
`@src/main/java/com/bestreviewer/` `@src/test/java/com/bestreviewer/`

---

## [T] Task

```
다음 주제를 **모두** 다루는 회고·발표 문서를 작성하세요. (코드 수정 없음)

### 6-1. 실습 목표와 달성도
- README·작업시나리오 목표 3~5개 bullet 재정의
- 단계별(1~5) 완료 여부 표 (✅/△/❌)
- 남은 기술 부채·다음 스프린트 3가지

### 6-2. 코드 품질 Before & After
- 구조: 클래스 수, 파사드·SRP, calculateBmi 파이프라인
- 스멜: 1단계 Top 5 해결/잔존
- 코드 인용 2~3쌍 (Before vs After)
- 테스트: TC 개수·mvn test 요약
- mermaid: 처리 흐름 Before vs After

### 6-3. AI 활용 회고
- 단계별 프롬프트 유형 (PCTF, @첨부, mvn 피드백)
- 도움 3가지 · 한계 3가지 · Human-in-the-loop 1~2건

### 6-4. TC 영향과 작성 팁
- Red→Green 사례 · 리팩토링 안전망
- 팁 5개 (GWT, Parameterized, 픽스처, DisplayName, dat 의존 지양)

### 6-5. 클린코드·리팩토링 소감
- 장점·어려운 점 각 3~5 · Takeaway 1줄

### 6-6. 결함 관리·품질 메트릭 회고 (5단계 연계)
- docs/05 메트릭 스냅샷 표 채움 (QM-xx 현재값)
- DEF Open/Resolved·TC 통과율 요약
- 5단계 템플릿 중 실습에 유용했던 것 1~2개

### 6-7. 발표 아웃라인 (10~15분)
- 슬라이드 8~12장 제목·bullet
- 데모: calculateBmi → getBmiRatio → 정상 목록·전체 비율
```

---

## [F] Format

```
- 산출물: Report/06.S_Health_회고및발표.md
- 문서 구조:
  ## 1. 실습 목표와 달성도
  ## 2. 코드 품질 Before & After
  ## 3. 생성형 AI 활용 회고
  ## 4. 단위 테스트 영향과 작성 팁
  ## 5. 클린코드·리팩토링 소감
  ## 6. 결함 관리·품질 메트릭 회고 (5단계)
  ## 7. 발표 슬라이드 아웃라인 (8~12장)
  ## 부록 A. 프롬프트·참고 링크
  ## 부록 B. mvn clean test 최종 로그
- 한국어·표·mermaid
- 코드·mvn 실행 금지 (기존 결과 인용)
```

---

## 복사용 (한 블록)

```
첨부:
@작업프롬프트/6단계_PCTF.md
@작업시나리오
@README.md
@.cursorrules
@Report/
@docs/05.S_Health_결함분류체계.md
@docs/05.S_Health_품질메트릭수집계획.md
@Report/05.S_Health_결함관리체계_보고서.md
@src/main/java/com/bestreviewer/
@src/test/java/com/bestreviewer/

[P] 실습 회고·발표 기술 리드. 코드 변경 없이 문서만.

[C] SHealth 1~5단계 산출물, docs/05 메트릭·분류 체계, Report, test/main, prompt 이력.

[T] Report/06.S_Health_회고및발표.md:
1) 목표·달성도 2) Before/After 3) AI 회고 4) TC 팁
5) 클린코드 소감 6) 결함·메트릭 회고(5단계) 7) 발표 아웃라인 8~12장

[F] 한국어 Markdown. 코드·mvn 실행 금지.
```
