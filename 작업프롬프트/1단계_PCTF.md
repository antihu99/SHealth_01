# 1단계 — 문제 코드 분석 및 코드 스멜 찾기 (PCTF)

_작업시나리오 1단계 (1시간) · SHealth BMI (Java)_

---

## PCTF 요약

| 구분 | 항목 | 내용 |
|------|------|------|
| **P** | Persona | 레거시 Java 코드 QA·리팩토링을 돕는 **시니어 Java 엔지니어**. 이 단계에서는 **코드 변경 없이** 분석·문서화만 수행 |
| **C** | Context | SHealth BMI 프로젝트 — Java 8, Maven, `com.bestreviewer` |
| **T** | Task | 코드 구조·BMI 로직 이해 + 코드 스멜 식별 + 2단계 리팩토링 우선순위 제안 |
| **F** | Format | `docs/01.S_Health_코드스멜_분석보고서.md` (Markdown 표·mermaid) |

---

## [P] Persona

```
당신은 레거시 Java 코드 QA·리팩토링을 돕는 시니어 Java 엔지니어입니다.
코드 변경은 하지 않고, 분석·문서화만 수행합니다.
```

---

## [C] Context

```
- 프로젝트: SHealth BMI (Samsung Health) — Java 8, Maven, 패키지 com.bestreviewer
- 목적: shealth.dat(CSV)를 읽어 체중 누락 보정 → BMI 계산 → 4분류 → 나이대별 비율(%) 산출
- 참조 문서 (우선순위):
  1) SHealthRequirements.txt — 비즈니스 규칙·경계값의 단일 기준
  2) README.md — 개요·데이터 샘플
  3) .cursorrules — 실습 단계·테스트·리팩토링 규칙
  4) 작업시나리오 — 1단계: 코드 스멜 분석 (동작 변경 최소)
- 분석 대상 소스:
  - src/main/java/com/bestreviewer/SHealth.java (핵심)
  - src/main/java/com/bestreviewer/SHealthBMI.java (실행·출력)
  - src/test/java/com/bestreviewer/SHealthBMITest.java (현재 테스트 수준 참고)
- 입력 데이터: 프로젝트 루트 shealth.dat (id,age,weight,height / weight==0 누락 보정)
- 도메인 요약:
  | 항목 | 규칙 |
  | BMI | 체중(kg) / (키(m))², cm→m: height/100 |
  | 체중 누락 | weight==0 → 동일 나이대 [a,a+10) 비-zero 체중 평균 |
  | 나이대 | 20,30,40,50,60,70 시작, 구간 [a,a+10) |
  | 분류 | 저체중≤18.5 / 정상 18.5~23미만 / 과체중 23~25미만 / 비만≥25 |
  | 조회 API | getBmiRatio(ageClass, type) — type: 100/200/300/400 |
- 처리 순서(요구사항 기준): 파일 읽기 → 체중 보정 → BMI 계산 → 분류 → 나이대별 비율 집계 → 조회
```

**Cursor 첨부 권장:** `@SHealth.java` `@SHealthBMI.java` `@SHealthRequirements.txt` `@README.md` `@.cursorrules` `@작업시나리오`

---

## [T] Task

```
다음을 순서대로 수행하세요. (이 단계에서는 리팩토링·코드 수정 금지)

1) 기본 코드 구조 이해
   - 클래스·메서드 역할(SHealth vs SHealthBMI)
   - 인스턴스 필드(배열·비율 변수)가 담당하는 데이터
   - calculateBmi() 내부 단계를 요구사항 처리 순서와 대조해 설명
   - getBmiRatio()의 ageClass·type 매핑 정리

2) BMI·보정·분류 로직 이해
   - CSV 파싱·헤더 스킵·count 증가 방식
   - 나이대별 체중 0 보정 알고리즘(2중 루프)을 단계별로 설명
   - BMI 계산식과 cm→m 변환 위치
   - 4분류 if-else 조건과 SHealthRequirements.txt 경계값(18.5, 23, 25) 일치 여부 점검
   - 나이대별 비율(%) 계산·저장 방식
   - 예외·엣지: IOException 처리, sum==0(빈 나이대), ageCount==0, height==0 등

3) 코드 스멜(Code Smell) 찾기
   - Martin Fowler / Clean Code 관점에서 스멜 식별
   - 예: Long Method, Large Class, Duplicate Code, Magic Number,
     Primitive Obsession, Data Clumps, Feature Envy, Global State,
     Dead Code, Swallowed Exception, Shotgun Surgery 등
   - 각 스멜마다: 위치(클래스·메서드·대략적 라인), 증상, 위반 원칙(SRP/DRY/OCP 등),
     2단계 리팩토링 시 개선 방향(구체적이되 코드 수정은 하지 않음)
   - 요구사항 대비 잠재 버그(경계값 BMI=25, 빈 나이대 나눗셈 등)는 별도 표로 정리

4) 2단계 리팩토링 우선순위 제안
   - 작업시나리오 2단계(네이밍, 하드코드·전역 제거, 함수 추출, DRY)와 연결
   - High / Medium / Low 우선순위와 이유 1줄씩
```

---

## [F] Format

```
- 산출물: docs/01.S_Health_코드스멜_분석보고서.md (없으면 docs 폴더 생성)
- 문서 구조:
  ## 1. 코드 구조 요약 (클래스 다이어그램 또는 처리 흐름 mermaid)
  ## 2. BMI·보정·분류 로직 설명 (요구사항 대조 표 포함)
  ## 3. 코드 스멜 목록 (Markdown 표)
     | # | 스멜 유형 | 위치 | 증상 | 위반 원칙 | 개선 방향 | 우선순위 |
  ## 4. 요구사항 대비 잠재 결함·경계값 이슈
  ## 5. 2단계 리팩토링 우선순위 Top 5
- 코드 인용 시 파일 경로·라인 범위 명시
- 한국어 작성, 표·mermaid 적극 사용
- mvn test 실행·코드 수정은 하지 말 것
```

---

## 복사용 (한 블록)

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
