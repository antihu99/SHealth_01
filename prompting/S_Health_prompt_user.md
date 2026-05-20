# SHealth_01 — 사용자 입력 프롬프트 모음

_작업 주제: 원격 저장소 연결 · 요구사항 정의서 · .cursorrules · 1~2단계 · 3단계 단위테스트 · 3-2 결함·권장 통합 · TC 브랜치 · 4단계 기능개선 · 4-2 2차 리팩토링 · 5단계 결함관리·QA · 6단계 회고·발표_  
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

_(03단계 세션 — `prompting/03.S_Health_단위테스트_prompt.md` 생성)_

---

## Prompt 25

```
지금까지 발견된 결함이나, 권장 사항에 대한 보고서를 정리할 promtp를 pctf 방식으로 만들어서. 3-2단계 로 해서 파일 작업프롬프트 폴더에 만들어줘
```

---

## Prompt 26

```
@작업프롬프트/3-2단계_PCTF.md  의 [f] 산출물에 결함이나, 권장 사항에 대해 문서화해서 docs 폴더에 저장하도록 보완해줘
```

---

## Prompt 27

**첨부:** `@작업프롬프트/3-2단계_PCTF.md` `@작업시나리오` `@SHealthRequirements.txt` `@.cursorrules`  
`@docs/01.S_Health_코드스멜_분석보고서.md` `@Report/01.S_Health_code_smell_보고서.md`  
`@docs/02.S_Health_1차리팩토링_체크리스트.md` `@Report/02.S_Health_1차리팩토링_보고서.md`  
`@docs/03.S_Health_단위테스트계획서.md` `@Report/03.S_Health_단위테스트_보고서.md`  
`@src/test/java/com/bestreviewer/` `@SHealth.java`

**3-2 PCTF 복사용 블록** — DEF/REC 통합, docs 2 + Report 1, 코드·TC 수정 금지

```
[P] 1~3단계 산출물만 근거로 결함·권장사항 통합·중복 제거. SHealthRequirements.txt 단일 기준. 코드·TC 수정 금지.
[C] DEF-xx, REC-xx, 상태 Open/Test-Locked/Deferred-4 등.
[T] 1) 교차표 2) docs/03-2.S_Health_결함_레지스터.md 3) docs/03-2.S_Health_권장사항_레지스터.md 4) Report/03-2.* 요약
[F] docs 2종 + Report 1종 필수. ID·건수 일치.
```

_(전문은 `작업프롬프트/3-2단계_PCTF.md` 복사용 및 `prompting/03-2.S_Health_결함및권장사항_prompt.md` Turn 3 참조)_

---

## Prompt 28

```
github tc 브랜치에 지금까지 작업한 파일을 업로드 해줘 comment 는 " 3-2단계 단위 테스트 결함 목록 문서화" 이라고 해줘
```

---

## Prompt 29

```
prompting 폴더에 agent 와 대화한 전체내용을 새로운 파일에 저장하고 내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

_(03-2단계 세션 — `prompting/03-2.S_Health_결함및권장사항_prompt.md` 생성)_

---

## Prompt 30

```
github feature 브랜치에 모든 파일을 업로드 해줘 comment 는 "4단계 기능개선" 이라고 해줘
```

---

## Prompt 31

```
@작업프롬프트/4단계_PCTF.md  의 [f] 산출물 보고서를 선택이 아닌 필수로 변경해 주고, 한줄 복사용에 참고용 문서명들도 표기해줘
```

---

## Prompt 32

**첨부:** `@작업프롬프트/4단계_PCTF.md` `@작업시나리오` `@SHealthRequirements.txt` `@.cursorrules` `@SHealth.java` `@src/test/java/com/bestreviewer/` `@docs/03-2.S_Health_결함_레지스터.md` `@docs/03-2.S_Health_권장사항_레지스터.md` `@Report/03-2.S_Health_결함및권장사항_통합보고서.md` `@docs/03.S_Health_단위테스트계획서.md` `@Report/03.S_Health_단위테스트_보고서.md`

**4단계 PCTF 복사용 블록** — SRP·키0·정상목록·전체비율, Report/04 필수

```
[P] SRP·TDD 시니어 Java. 기존 calculateBmi/getBmiRatio 호환·테스트로 회귀 입증.
[C] 2~3단계 Green. SHealthRequirements.txt + .cursorrules 4단계 확장.
[T] 1) SRP 분리 2) 연령대별 비율 3) height==0 키 보정 4) 정상 BMI ID 목록 5) 전체 4분류 비율
[F] main/test + Report/04.S_Health_기능개선_보고서.md (필수). mvn clean test Green.
```

_(전문은 `작업프롬프트/4단계_PCTF.md` 복사용 및 `prompting/04.S_Health_기능개선_prompt.md` Turn 3 참조)_

---

## Prompt 33

```
GitHub 의 feature 브랜치에 모두 파일을 업로드 해줘. "4단계_기능 추가 : Reader,UserRecord,Imputation,BmiCalculator,Classifier,Statistics,SHealth 파사드" 이라고 comment 해줘
```

---

## Prompt 34

```
prompting 폴더에 agent 와 대화한 전체내용을 새로운 파일에 저장하고 내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

_(04단계 세션 — `prompting/04.S_Health_기능개선_prompt.md` 생성)_

---

## Prompt 35

```
추가된 신규기능에 대한 2차 리펙토링을 진행하려고 함.
- 네이밍 개선
- 하드코드 및 전역변수 제거 
- 함수 추출
- 반복/중복 제거

를 해결하기 위한, PCTF 방식으로 PROMPT 를 만들어줘
작업프롬프트 폴더에 있는 pctf 프롬프트들을 참고.
산출물에 docs 폴더에 리팩토링 체크리스트 문서를 만드는 것도 포함해줘, 그리고 참고문서에 대한 것도 포함해줘
만들어진 prompt 는 작업프롬프트폴더에 4-2단계 저장해줘
```

---

## Prompt 36

**첨부:** `@4-2단계_PCTF.md` (244-289) — 4-2 PCTF 복사용 (한 블록) 전체

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
[T] 0) 4-2-0 스캔·Green·REC 백로그 1) 네이밍 2) 하드코드·전역 3) 함수 추출 4) DRY
[F] docs/04-2.S_Health_2차리팩토링_체크리스트.md + main 리팩토링 + Report/04-2.* + mvn clean test Green
```

_(전문은 `작업프롬프트/4-2단계_PCTF.md` 복사용 및 `prompting/04-2.S_Health_2차리팩토링_prompt.md` Turn 2 참조)_

---

## Prompt 37

```
prompting 폴더에 agent 와 대화한 전체내용을 새로운 파일에 저장하고 내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

_(04-2단계 세션 — `prompting/04-2.S_Health_2차리팩토링_prompt.md` 생성)_

---

## Prompt 38

```
GitHub 의 feature 브랜치에 모두 파일을 업로드 해줘. "4-2단계_추가 기능에 대한 Refactoring" 이라고 comment 해줘
```

---

## Prompt 39

```
결함 분류 체계·보고서 템플릿·품질 메트릭 수집 계획 수립할 수 있는 단계를 5단계 이전에 추가해줘,
결함 관리 및 보고를 위한 PCTF 방식으로 PROMPT 를 만들어서 작업프롬프트폴더 에 저장해줘.
기존 단계들에 대한 번호도 다시 매겨줘
```

---

## Prompt 40

**첨부:** `@5단계_PCTF.md` (223-255) — 5단계 PCTF 복사용 블록

```
[P] 결함 분류·보고 표준·품질 메트릭 설계 시니어 QA. 1~4-2 산출물·3-2 DEF/REC만 근거. 코드·TC 수정 금지.
[T] 5-1 분류체계 → docs/05.S_Health_결함분류체계.md
    5-2 템플릿 4+ → docs/05.S_Health_보고서템플릿.md
    5-3 메트릭 QM 8+ → docs/05.S_Health_품질메트릭수집계획.md
    5-5 → Report/05.S_Health_결함관리체계_보고서.md
[F] docs 3종 + Report/05 필수. 한국어.
```

_(전문은 `작업프롬프트/5단계_PCTF.md` 복사용 및 `prompting/05.S_Health_결함관리및QA_prompt.md` Turn 3 참조)_

---

## Prompt 41

```
github qa브랜치에 모든 파일을 업로드 해줘 comment 는 "5단계 결함 분석 및 QA" 이라고 해줘
```

---

## Prompt 42

```
GIT 에서 5단계 에 대한 PR 을 생성해줘 "#5단계 결함 및 QA"
```

---

## Prompt 43

```
prompting 폴더에 agent 와 대화한 전체내용을 새로운 파일에 저장하고 내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

_(05단계 세션 — `prompting/05.S_Health_결함관리및QA_prompt.md` 생성)_

---

## Prompt 44

```
@작업프롬프트/6단계_PCTF.md  참고 문서를 docs 폴더 전체로 바꿔주고. [p] 단계를 전체 과정을 회고 발표하는 발표자로 해서 pctf 프롬프트를 보다 구체적으로 만들어줘 산출물에도 5분 내외로 발표할 수 있는 간단한 pdf 파일을 docs 폴더에 만들어줘
```

---

## Prompt 45

```
왜 갑자기 python 스크립트를 생성할까요?
```

---

## Prompt 46

```
넵... 그리고 산출물에 작업프롬프트 폴더를 참조하여, 작업한 순차적인 prompt 들에 대해서도 review 해주는 문서를 docs 폴더에 만들어 주고, ai 가 작성한 코드에 대한 review 를 하는 문서도 docs 폴더에 만들도록 prompt 를 수정해줘
```

---

## Prompt 47

**첨부:** `@6단계_PCTF.md` (152-174) — 6단계 PCTF 복사용 블록

```
[P] 발표자. 5분 스토리 + 작업프롬프트 순차 리뷰 + AI 코드 리뷰. 코드 수정 금지.
[C] docs/ + 작업프롬프트/ + prompting/S_Health_prompt_user + Report + src.
[T] 1) docs/06.S_Health_작업프롬프트_순차리뷰.md
    2) docs/06.S_Health_AI코드_리뷰.md
    3) Report/06.S_Health_회고및발표.md — §1~9·부록
    4) docs/06.S_Health_회고발표_5분.pdf
[F] docs 06 리뷰 2종 + PDF + Report/06 필수. 한국어.
```

_(전문은 `작업프롬프트/6단계_PCTF.md` 복사용 및 `prompting/06.S_Health_회고발표_prompt.md` Turn 4~5 참조)_

---

## Prompt 48

**첨부:** `@6단계_PCTF.md` (152-174) — 6단계 PCTF 재실행 (동일 복사용 블록)

_(Turn 5 — Report/06·발표 md·PDF 완료, `mvn test` 40/40 Green)_

---

## Prompt 49

```
prompting 폴더에 agent 와 대화한 전체내용을 새로운 파일에 저장하고 내가 입력한 prompt 는 "S_Health_prompt_user" 파일에 덧붙여줘
```

_(06단계 세션 — `prompting/06.S_Health_회고발표_prompt.md` 생성)_

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
| 24 | 03단계 prompting 문서화 | `03.S_Health_단위테스트_prompt.md` |
| 25 | 3-2단계 PCTF 프롬프트 작성 | `작업프롬프트/3-2단계_PCTF.md` |
| 26 | 3-2 [F] docs 레지스터 산출물 보완 | `3-2단계_PCTF.md` |
| 27 | 3-2 PCTF 실행 (DEF/REC·docs·Report) | 1~3단계 보고서·`*Test` |
| 28 | `TC` 브랜치 커밋·푸시 (`7101869`) | GitHub |
| 29 | 03-2 prompting 문서화 | `03-2.S_Health_결함및권장사항_prompt.md` |
| 30 | `feature` 브랜치 생성·푸시 (`2c45cc4`) | GitHub |
| 31 | 4단계 [F] 보고서 필수·복사용 참조 문서 | `4단계_PCTF.md` |
| 32 | 4단계 PCTF 실행 (SRP·신규 API·DEF 해소) | `Report/04.*`, `*Test` |
| 33 | `feature` 브랜치 커밋·푸시 (`ddb6656`) | GitHub |
| 34 | 04단계 prompting 문서화 | `04.S_Health_기능개선_prompt.md` |
| 35 | 4-2단계 PCTF 프롬프트 작성 (2차 리팩토링·docs 체크리스트) | `작업프롬프트/4-2단계_PCTF.md` |
| 36 | 4-2 PCTF 실행 (네이밍·상수·추출·DRY·docs/Report 04-2) | `04-2단계_PCTF.md` 복사용 |
| 37 | 04-2 prompting 문서화 | `04-2.S_Health_2차리팩토링_prompt.md` |
| 38 | `feature` 브랜치 커밋·푸시 (`e725d04`) 4-2 | GitHub |
| 39 | 5단계 PCTF 작성·6단계 재번호 | `5·6단계_PCTF.md`, 목차 |
| 40 | 5단계 PCTF 실행 (docs/05·Report/05) | `5단계_PCTF.md` 복사용 |
| 41 | `qa` 브랜치 커밋·푸시 (`08873b0`) | GitHub |
| 42 | PR #4 qa→feature | GitHub |
| 43 | 05단계 prompting 문서화 | `05.S_Health_결함관리및QA_prompt.md` |
| 44 | 6단계 PCTF 보강 (발표자·@docs/·PDF) | `6단계_PCTF.md` |
| 45 | PDF 스크립트 이유 질문 | — |
| 46 | 6단계 PCTF 수정 (리뷰 2종·스크립트 제거) | `docs/06.*` 리뷰 |
| 47 | 6단계 PCTF 실행 (docs·Report/06) | `6단계_PCTF.md` 복사용 |
| 48 | 6단계 PCTF 재실행 (PDF·검증) | Report/06, 발표 PDF |
| 49 | 06단계 prompting 문서화 | `06.S_Health_회고발표_prompt.md` |
