# SHealth_01 — 사용자 입력 프롬프트 모음

_작업 주제: 원격 저장소 연결 · 요구사항 정의서 · .cursorrules · 1단계 코드 스멜 분석_  
_수집일: 2026-05-19_

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
