# SHealth_01 — 사용자 입력 프롬프트 모음

_작업 주제: 원격 저장소 연결 · 요구사항 정의서 · .cursorrules 작성_  
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

## 프롬프트 흐름 요약

| # | 의도 | 참조 |
|---|------|------|
| 1~2 | 개발 환경 준비 (Git clone·원격 연결) | GitHub URL |
| 3 | 요구사항 명세화 | README.md |
| 4 | AI 작업 규칙 고정 | 작업시나리오, SHealthRequirements.txt |
| 5 | 산출물 문서화 | Report/, prompting/ |
