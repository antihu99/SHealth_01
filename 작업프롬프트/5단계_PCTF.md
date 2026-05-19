# 5단계 — 회고 및 발표 (PCTF)

_작업시나리오 5단계 (1시간) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | 실습 **회고·발표 자료**를 정리하는 **기술 리드·교육 코치** (코드 변경 없음) |
| **C** | Context | 1~4단계 산출물·Git 이력·Before/After 코드·테스트 결과 종합 |
| **T** | Task | 실습 목표 달성도, 품질 Before/After, AI 활용, TC 영향, 클린코드 소감 정리 |
| **F** | Format | `Report/05.S_Health_회고및발표.md` (발표용 슬라이드 아웃라인 포함) |

---

## [P] Persona

```
당신은 소프트웨어 품질 실습의 회고·발표를 돕는 기술 리드입니다.
이 단계에서는 프로덕션 코드를 수정하지 않고, 문서·표·mermaid로 성과와 학습을 정리합니다.
솔직한 Before/After, AI 활용의 장단점, 구체적 예시(파일명·테스트명)를 포함합니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI — 6시간 생성형 AI 활용 Activities
- 완료 단계:
  | 단계 | 내용 | 참고 산출물 |
  | 1 | 코드 스멜 분석 | docs/01.*, Report/01.* |
  | 2 | 1차 리팩토링 | Report/02.*, Git diff |
  | 3 | 단위 테스트 | src/test/..., Report/03.* |
  | 4 | 기능 개선 | Report/04.*, 패키지 구조 |
  | 5 | 회고·발표 | (본 단계 산출) |
- 참조: 작업시나리오 5단계 bullet, README.md, .cursorrules
- 입력 자료 (가능한 범위에서 첨부·인용):
  - 초기 SHealth.java 스냅샷 또는 1단계 분석 시 코드 인용
  - 최종 main/test 구조
  - mvn clean test 최종 결과
  - prompting/S_Health_prompt_user.md — 사용한 프롬프트 이력
  - 각 Report/*.md
- 금지: 요청 없는 코드·설정 변경, target/ 커밋 권장 문구
```

**Cursor 첨부 권장:** `@작업시나리오` `@README.md` `@.cursorrules` `@Report/` `@src/main/java/com/bestreviewer/` `@src/test/java/com/bestreviewer/`

---

## [T] Task

```
다음 주제를 **모두** 다루는 회고·발표 문서를 작성하세요. (코드 수정 없음)

### 5-1. 실습 목표와 달성도
- README·작업시나리오의 목표를 3~5개 bullet로 재정의
- 단계별(1~4) 완료 여부·부분 완료·미완 항목 표 (달성률 % 또는 ✅/△/❌)
- 남은 기술 부채·다음 스프린트 제안 3가지

### 5-2. 코드 품질 Before & After
- 구조: 클래스 수, calculateBmi 라인 수, cyclomatic complexity 체감
- 스멜: 1단계 Top 5 중 해결/잔존 표
- 코드 인용 2~3쌍 (Before 스니펫 vs After 스니펫 — 파일·라인)
- 테스트: 3단계 전/후 커버리지·TC 개수 (mvn test 요약)
- mermaid: 처리 흐름 Before vs After

### 5-3. AI를 어떻게 활용했나?
- 단계별 사용한 프롬프트 유형 (PCTF, @첨부, mvn test 피드백 등)
- **도움이 된 순간** 3가지 (구체적: 예 — 경계값 TC 표 생성, DRY 루프 통합 제안)
- **한계·주의** 3가지 (구체적: 예 — BMI=25 경계 오류 미검출, 과도한 파일 분리 제안)
- 본인이 검증·수정한 사례 1~2건 (Human-in-the-loop)

### 5-4. TC를 추가하면서 개선에 미친 영향 & TC 작성 팁
- Red→Green으로 발견·수정한 버그 (예: BMI=25 비만 분류 누락 등) — 있으면 사례
- 테스트가 리팩토링 안전망이 된 예 (2→3→4단계)
- 팁 5개: Given–When–Then, @ParameterizedTest 경계, 소규모 픽스처, DisplayName 한글, 전체 dat 의존 지양

### 5-5. 클린코드와 리팩토링 — 장점과 어려운 점
- 장점 3~5 (가독성, 변경 용이성, 팀 온보딩 등) — 프로젝트 예시
- 어려운 점 3~5 (레거시 API 유지, 나눗셈 0, 점진적 SRP 등) — 프로젝트 예시
- 팀에 전달할 한 줄 교훈 (Takeaway)

### 5-6. 발표 아웃라인 (10~15분 가정)
- 슬라이드 8~12장 제목·핵심 bullet만 (본문은 Report에 상세)
- 데모 시나리오: calculateBmi → getBmiRatio → (4단계) 정상 목록·전체 비율
```

---

## [F] Format

```
- 산출물: Report/05.S_Health_회고및발표.md
- 문서 구조:
  ## 1. 실습 목표와 달성도 (표)
  ## 2. 코드 품질 Before & After (표·mermaid·코드 인용)
  ## 3. 생성형 AI 활용 회고 (도움/한계 표)
  ## 4. 단위 테스트 영향과 작성 팁
  ## 5. 클린코드·리팩토링 소감
  ## 6. 발표 슬라이드 아웃라인 (8~12장)
  ## 부록 A. 사용 프롬프트·참고 링크
  ## 부록 B. mvn clean test 최종 로그 요약
- 톤: 발표 가능한 명확한 한국어, 표·mermaid 적극 사용
- 코드·mvn test 실행은 하지 않음 (이미 있는 결과·diff 인용)
- 선택: 1페이지 Executive Summary (상단)
```

---

## 복사용 (한 블록)

```
[P] 실습 회고·발표 기술 리드. 코드 변경 없이 문서만 작성.

[C] SHealth 1~4단계 산출물, Report, test/main, prompt 이력, 작업시나리오.

[T] Report/05.S_Health_회고및발표.md 작성:
1) 목표·달성도 표
2) Before/After 품질·스멜·코드 인용·mermaid
3) AI 활용 도움/한계/검증 사례
4) TC가 리팩토링·버그에 미친 영향 + 작성 팁 5개
5) 클린코드 장단점 + Takeaway
6) 10~15분 발표 슬라이드 아웃라인 8~12장

[F] 한국어 Markdown. 코드 수정·mvn 실행 금지.
```
