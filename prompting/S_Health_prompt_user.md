# SHealth_01 — 사용자 입력 프롬프트 모음

_작업 주제: 원격 저장소 연결 · 요구사항 정의서 · .cursorrules · 1~2단계 · 3단계 단위테스트 · TC 브랜치_  
_수집일: 2026-05-19 (갱신: 2026-05-20)_

---

## Prompt 1

```
원격 GIT 저장소에서 파일을 받아줘
```

---

## Prompt 2

```
https://github.com/antihu99/SHealth_01.git 와 LOCAL 연결해줘
```

_(GitHub 저장소 URL 제공 — [antihu99/SHealth_01](https://github.com/antihu99/SHealth_01))_

---

## Prompt 3

```
@README.md 파일을 읽어서, 요구사항정의서를 SHealthRequirements.txt 파일명으로 만들어줘
```

---

## Prompt 4

```
@작업시나리오 @SHealthRequirements.txt 를 참고하여 SAMSUNG HEALTH BMI 프로젝트에서 Cursor AI가 항상 따라야 하는 규칙을 프로젝트 루트의 .cursorrules로 작성해줘
```

---

## Prompt 5

```
Report 폴더에 보고서를 "00.S_Health_cursorrules_작성" 파일에 작성해주세요, 그리고 prompting 폴더에 내가 입력한 prompt 를 "S_Health_prompt_user" 파일에, agent 와 대화한 전체는 "00.S_Health_cursorrules_작성_prompt" 파일에 저장해줘
```

---

## Prompt 6

```
@작업시나리오 @README.md  @.cursorrules 
1단계 
1. 문제 코드 분석 및 코드 스멜 찾기
- 기본 코드구조, BMI 로직 이해 
- 코드 스멜 찾기 
를 해결하기 위한, PCTF 방식으로 PROMPT 를 만들어줘
```

---

## Prompt 7

```
1단계_PCTF 파일로 작업프롬프트 폴더에 저장해줘
```

---

## Prompt 8

```
@작업프롬프트/1단계_PCTF.md  에서 산출물 위치를 docs 폴더로 바꿔줘
```

---

## Prompt 9

**첨부:** `@SHealth.java` `@SHealthBMI.java` `@SHealthRequirements.txt` `@README.md` `@.cursorrules` `@작업시나리오`

**참조:** `@작업프롬프트/1단계_PCTF.md` (112-124)

```
[P] 당신은 레거시 Java 코드 QA·리팩토링을 돕는 시니어 Java 엔지니어입니다. 코드 변경 없이 분석·문서화만 수행합니다.

[C] SHealth BMI (Java 8, Maven, com.bestreviewer). shealth.dat → 체중0 보정 → BMI → 4분류 → 나이대별 비율(%).
참조: SHealthRequirements.txt, README.md, .cursorrules, 작업시나리오.
분석: SHealth.java, SHealthBMI.java, SHealthBMITest.java.

[T]
1. 코드 구조·calculateBmi/getBmiRatio 역할 설명
2. BMI·보정·분류 로직을 요구사항과 대조해 설명, 경계값·엣지 케이스 점검
3. Code Smell 목록(위치·증상·원칙·개선방향·우선순위), 잠재 버그 별도 표
4. 2단계 리팩토링 우선순위 Top 5

[F] docs/01.S_Health_코드스멜_분석보고서.md — 표·mermaid·한국어. 코드 수정·mvn test 금지.
```

---

## Prompt 10

```
Report 폴더에 보고서를 "01.S_Health_code_smell_보고서" 파일에 작성해주세요,
그리고 prompting 폴더에 agent 와 대화한 전체는 "01.S_Health_code_smell_prompt" 파일에 저장하고
내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

---

## Prompt 11

```
@작업시나리오 @README.md  @.cursorrules 

2단계. 1차 리펙토링 (클린코드 관점, 아래 내용을 순차적으로 수행) 
- 네이밍 개선
- 하드코드 및 전역변수 제거 
- 함수 추출
- 반복/중복 제거
3단계. UnitTest 작성 
- BMI 계산 로직 TC
- Age 평균치 보정 로직 TC
- 정상/저체중/과체중/비만 분류 TC
- 예외상황 TC
4단계. 기능 개선 
- SRP에 따른 책임 분리등 리팩토링 
- 특정 연령대의 BMI 분포 비율 계산 기능 추가
- Height가 0인 경우에 대한 평균치 보정 로직 추가 
- BMI 정상 범위 사용자 목록 조회 기능 추가
- 전체 사용자 대비 각 BMI 범주 비율 계산 기능 추가
5단계. 회고 및 발표 
- 실습 목표와 달성도 
- 코드 품질 Before & After
- AI를 어떻게 활용했나? 도움이 된 순간과 한계는? 
- TC를 추가보면서 개선에 미친 영향, TC 작성 팁
- 클린코드와 리팩토링에서 느낀 장점과 어려운점

를 해결하기 위한, 단계별 PCTF 방식으로 PROMPT 를 만들고 "작업프롬프트"폴더에 단계별로 파일을 만들어줘
```

---

## Prompt 12

```
@작업프롬프트/2단계_PCTF.md 파일의 [F] Format 단계에  단계별 체크리스트 + 각 단계 검증 방법 (mvn test) 항목을 추가해줘
```

---

## Prompt 13

`@작업프롬프트/2단계_PCTF.md` (157-170) — 복사용 블록 보강 요청

---

## Prompt 14

`@작업프롬프트/2단계_PCTF.md:54` `@작업프롬프트/2단계_PCTF.md:156-176` — Cursor 첨부 권장·복사용 블록 정합

---

## Prompt 15

**첨부:** `@SHealth.java` `@SHealthBMI.java` `@SHealthRequirements.txt` `@.cursorrules` `@작업시나리오` `@docs/01.S_Health_코드스멜_분석보고서.md` 또는 `@Report/01.S_Health_code_smell_보고서.md`

```
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
산출물: 2-1~2-4 단계별 체크리스트와 각 단계 검증 방법(mvn test)을 docs 폴더 밑에 파일 생성
  + 리팩토링된 SHealth.java + Report/02.S_Health_1차리팩토링_보고서.md
  (§1 체크리스트 ✅/❌, §6 단계별 mvn test 결과). 한국어.
```

---

## Prompt 16

```
산출물 Report/02.S_Health_1차리팩토링_보고서.md  를 만들어주고, 실제 리팩토링까지 실행해줘
```

---

## Prompt 17

```
prompting 폴더에 agent 와 대화한 전체를 파일로 저장해줘.
그리고 그중에서 내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

---

## Prompt 18

```
GITHUB 에 TC 브랜치를 만들어줘, 그리고 COMMENT 는 "테스트 계획 (Test Plan)
테스트 케이스 작성 (Test Cases)
테스트 실행 & 결함 분석 (Defect Detection)" 라고 입력해줘
```

---

## Prompt 19

```
@작업프롬프트/3단계_PCTF.md  문서에서 [F] 단계 산출문 테스트 소스의 CLASS 명은 마지막에"Test" 라고 붙여서 생성해 주고, 보고서는 선택이 아닌 필수도 바꿔서 저자해줘
```

---

## Prompt 20

```
@작업프롬프트/3단계_PCTF.md 복사용 한 블룩에 참조할 문서도 전부 표기해줘
```

---

## Prompt 21

```
@작업프롬프트/3단계_PCTF.md  산출물에 단위 test 계획에 관한 문서도 docs 폴더에 03.S_Health_단위테스트계획서.md 로 남길 수 있게 보완해줘
```

---

## Prompt 22

**첨부:** `@작업프롬프트/3단계_PCTF.md` `@작업시나리오` `@README.md` `@.cursorrules` `@SHealthRequirements.txt` `@pom.xml` `@SHealth.java` `@SHealthBMI.java` `@src/test/java/com/bestreviewer/SHealthBMITest.java` `@Report/02.S_Health_1차리팩토링_보고서.md` `@docs/02.S_Health_1차리팩토링_체크리스트.md` `@docs/01.S_Health_코드스멜_분석보고서.md` (또는 `@Report/01.S_Health_code_smell_보고서.md`)

**3단계 PCTF 복사용 블록 전체** — 계획서 → TC 구현 → 보고서, TS-01~06, `*Test` 5클래스, `docs/03.*`·`Report/03.*` 필수

_(전문은 `작업프롬프트/3단계_PCTF.md` 복사용 섹션 및 `prompting/03.S_Health_단위테스트_prompt.md` Turn 5 참조)_

---

## Prompt 23

```
github tc 브랜치에 지금까지 작업한 파일을 업로드 해줘 comment 는 " 3단계 단위 테스트 계획 및 테스트 케이스 작성" 이라고 해줘
```

---

## Prompt 24

```
prompting 폴더에 agent 와 대화한 전체내용을 새로운 파일에 저장하고 내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

---

## 프롬프트 흐름 요약

| # | 의도 | 참조 |
|---|------|------|
| 1~2 | 개발 환경 준비 (Git clone·원격 연결) | GitHub URL |
| 3 | 요구사항 명세화 | README.md |
| 4 | AI 작업 규칙 고정 | 작업시나리오, SHealthRequirements.txt |
| 5 | 00단계 산출물 문서화 | Report/, prompting/ |
| 6 | 1단계 PCTF 프롬프트 작성 | 작업시나리오, README, .cursorrules |
| 7 | PCTF 파일 저장 | 작업프롬프트/1단계_PCTF.md |
| 8 | 산출물 경로 변경 | docs/ |
| 9 | 1단계 코드 스멜 분석 실행 | SHealth.java, 1단계_PCTF |
| 10 | 01단계 Report·prompting 문서화 | Report/, prompting/ |
| 11 | 2~5단계 PCTF 프롬프트 작성 | 작업시나리오, README, .cursorrules |
| 12 | 2단계 [F] 체크리스트·mvn 검증 추가 | 2단계_PCTF.md |
| 13~14 | 2단계 복사용·첨부 정합 | 2단계_PCTF.md |
| 15 | 2단계 [F] docs 산출물·PCTF 실행 지시 | 1단계 스멜 보고서 |
| 16 | 2단계 리팩토링·Report/02 실행 | SHealth.java |
| 17 | 02단계 prompting 문서화 | prompting/ |
| 18 | GitHub `TC` 브랜치 생성·푸시 | origin/TC |
| 19 | 3단계 PCTF — `*Test` 명명·보고서 필수 | 3단계_PCTF.md |
| 20 | 3단계 복사용 블록 참조 문서 전체 | 3단계_PCTF.md |
| 21 | 3단계 `docs/03` 단위테스트계획서 산출물 | 3단계_PCTF.md |
| 22 | 3단계 PCTF 실행 (계획·TC·보고서) | TS-01~06, *Test |
| 23 | `TC` 브랜치 커밋·푸시 | GitHub |
| 24 | 03단계 prompting 문서화 | prompting/ |
