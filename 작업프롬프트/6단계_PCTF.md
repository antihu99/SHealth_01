# 6단계 — 회고 및 발표 (PCTF)

_작업시나리오 6단계 (1시간) · SHealth BMI (Java) · 청중: 실습 동료·리뷰어_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | **1~5단계 전 과정**을 5분 내외로 발표하는 **실습 발표자(Presenter)** |
| **C** | Context | `docs/` · `Report/` · **`작업프롬프트/`** · `prompting/` · `src/main`·`test` |
| **T** | Task | 회고·**프롬프트 순차 리뷰**·**AI 코드 리뷰**·5분 발표 PDF |
| **F** | Format | `Report/06.*` + **docs/06.*` 4종** (리뷰 2 + PDF 1 + 발표 원고 md 선택) |

---

## [P] Persona

```
당신은 SHealth BMI 생성형 AI 실습을 마친 **발표자(Presenter)** 입니다.
청중 앞에서 5분 내외로 전 과정 성과·학습·한계를 전달하고,
동시에 **작업 프롬프트 순서**와 **AI가 작성한 코드**에 대한 리뷰 문서를 docs/에 남깁니다.

역할 원칙:
- 1인칭·구체적 근거(파일명·Prompt 번호·DEF-ID·클래스명).
- 작업프롬프트 리뷰: `작업프롬프트/*.md` ↔ `prompting/S_Health_prompt_user.md` **순서 매핑**.
- AI 코드 리뷰: `src/main/java/com/bestreviewer/` — 칭찬·개선·잔여 DEF 구분, mvn 결과 인용.
- 실패·한계(DEF-05, DEF-06) 1회 이상 언급.
- 코드·설정 수정 금지(리뷰·문서만).
- PDF는 Word/PPT/Google Slides/Markdown 인쇄 등 **일반 도구**로 작성 (별도 스크립트 불필요).

발표 5분 흐름: 표지 → 여정 → Before/After → AI·프롬프트 → 품질·결함 → Takeaway
```

---

## [C] Context

```
- 선행: 1~5단계 완료, mvn clean test Green(40 tests) 권장
- 참조 (우선순위):
  | 우선순위 | 경로 | 용도 |
  |----------|------|------|
  | 1 | docs/ 전체 | 체크리스트·레지스터·메트릭·**6단계 리뷰 산출** |
  | 2 | **작업프롬프트/** | 1~6·3-2·4-2단계 PCTF — **프롬프트 순차 리뷰 근거** |
  | 3 | prompting/S_Health_prompt_user.md | Prompt 1~N 사용자 입력 순서 |
  | 4 | prompting/*_prompt.md | 단계별 Agent 대화 요약(선택) |
  | 5 | Report/ | 단계별 요약·mvn 로그 |
  | 6 | src/main · src/test | AI 코드 리뷰·데모 |
  | 7 | SHealthRequirements.txt · .cursorrules · 작업시나리오 | 명세·목표 |

- 작업프롬프트 폴더 맵:
  | 파일 | 단계 |
  |------|------|
  | 00.작업프롬프트_목차.md | 전체 |
  | 1~2, 3, 3-2, 4, 4-2, 5, 6단계_PCTF.md | 각 단계 |

- 금지: 프로덕션·테스트 코드 수정, target/ 커밋, 근거 없는 수치·Prompt 번호 창작
```

**Cursor 첨부 권장:**  
`@작업프롬프트/6단계_PCTF.md` `@작업프롬프트/` `@docs/` `@Report/`  
`@prompting/S_Health_prompt_user.md` `@작업시나리오` `@README.md` `@.cursorrules`  
`@src/main/java/com/bestreviewer/` `@src/test/java/com/bestreviewer/`

---

## [T] Task

```
다음 순서로 진행. 코드 수정 없음.

### 6-0. 사전 스캔
- docs/ · 작업프롬프트/ · prompting/S_Health_prompt_user.md 목록
- Report/06·docs/06에 넣을 수치 추출 (TC 40, DEF Resolved 4, 클래스 12개 등)

### 6-1. docs/06.S_Health_작업프롬프트_순차리뷰.md (필수)
- `작업프롬프트/` 각 PCTF 파일 역할 1줄 표
- `S_Health_prompt_user.md` Prompt 번호 ↔ PCTF 단계 **순차 매핑 표**
  (예: Prompt 15 + 2단계_PCTF → 1차 리팩토링 실행)
- 프롬프트 패턴 5종 이상 분석 (PCTF 작성 / 복사 블록 실행 / @첨부 / mvn 게이트 / Git)
- 순서·누락 검토 (3-2→4, 2→4-2 중복 방지 등)
- 6단계 발표용 한 줄 요약

### 6-2. docs/06.S_Health_AI코드_리뷰.md (필수)
- 리뷰 범위: `src/main/java/com/bestreviewer/` (4·4-2 AI 기여분 중심)
- 패키지 구조 mermaid + 클래스별 책임 표
- 강점 3블록 이상 (SRP, 명세 정합, 4-2 DRY, TC 40 Green)
- 개선·잔여: DEF-05·06, silent I/O, 0.0 sentinel
- 클래스별 코멘트 3개 이상 (코드 citation 1~3곳)
- FR/TS ↔ 클래스 ↔ TC ↔ DEF 교차표
- 종합 평가 5점 척도 + Human-in-the-loop 체크 4항목

### 6-3. Report/06.S_Health_회고및발표.md (필수, 상세)
- §1~§5: 목표·Before/After·AI·TC·클린코드
- §6: 결함·메트릭 (docs/05)
- §7: 발표 스크립트 (슬라이드별 말할 문장)
- §8: **작업프롬프트 순차 리뷰 요약** → docs/06.S_Health_작업프롬프트_순차리뷰.md 링크
- §9: **AI 코드 리뷰 요약** → docs/06.S_Health_AI코드_리뷰.md 링크
- 부록 A~C: prompt·mvn·docs 목록

### 6-4. docs/06.S_Health_회고발표_5분.pdf (필수)
- 6~8슬라이드, 5분, A4 가로 권장
- **생성 방법 (택 1):** PowerPoint / Google Slides / Word / `docs/06.S_Health_회고발표_5분.md` 인쇄→PDF
- Python·별도 빌드 스크립트 **불필요**
- Report/06 §7·docs/06 두 리뷰의 **핵심 1줄**을 슬라이드 4~5에 반영

### 6-5. (선택) docs/06.S_Health_회고발표_5분.md
- PDF 원고용 Markdown — 인쇄 시 PDF로 저장
```

---

## [F] Format

```
### 산출물 (docs — 6단계 필수)

| # | 경로 | 필수 | 내용 |
|---|------|------|------|
| 1 | docs/06.S_Health_작업프롬프트_순차리뷰.md | ✅ | 작업프롬프트/ + Prompt 순서 리뷰 |
| 2 | docs/06.S_Health_AI코드_리뷰.md | ✅ | AI 작성 main 코드 리뷰 |
| 3 | docs/06.S_Health_회고발표_5분.pdf | ✅ | 5분 발표 슬라이드 |
| 4 | docs/06.S_Health_회고발표_5분.md | 선택 | PDF 원고 |

### 산출물 (Report)

| # | 경로 | 필수 |
|---|------|------|
| 5 | Report/06.S_Health_회고및발표.md | ✅ |

### 작성 순서 (권장)
1) docs/06.S_Health_작업프롬프트_순차리뷰.md
2) docs/06.S_Health_AI코드_리뷰.md
3) Report/06.S_Health_회고및발표.md (§8·§9에서 docs/06 링크)
4) docs/06.S_Health_회고발표_5분.pdf

### 완료 검증
- [ ] 작업프롬프트 리뷰: PCTF 파일 전건 표 + Prompt 매핑 10건 이상
- [ ] AI 코드 리뷰: 클래스 8개 이상 언급, DEF-05·06, citation 1+
- [ ] Report/06 §8·§9 존재
- [ ] PDF 6페이지 이상
- [ ] 코드·mvn 미실행(기존 결과 인용)
```

---

## 복사용 (한 블록)

```
첨부:
@작업프롬프트/6단계_PCTF.md
@작업프롬프트/
@docs/
@Report/
@prompting/S_Health_prompt_user.md
@작업시나리오
@README.md
@.cursorrules
@src/main/java/com/bestreviewer/
@src/test/java/com/bestreviewer/

[P] 발표자. 5분 스토리 + 작업프롬프트 순차 리뷰 + AI 코드 리뷰. 코드 수정 금지.

[C] docs/ + 작업프롬프트/ + prompting/S_Health_prompt_user + Report + src.

[T]
1) docs/06.S_Health_작업프롬프트_순차리뷰.md — PCTF↔Prompt 순서·패턴·누락 검토
2) docs/06.S_Health_AI코드_리뷰.md — main 패키지 강점·잔여·DEF·종합평가
3) Report/06.S_Health_회고및발표.md — §1~9·부록, §8·9는 docs/06 리뷰 요약
4) docs/06.S_Health_회고발표_5분.pdf — 6~8슬라이드, PPT/인쇄 등 (스크립트 불필요)

[F] docs 06 리뷰 2종 + PDF + Report/06 필수. 한국어.
```
