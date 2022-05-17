# [Chapter 11](https://livebook.manning.com/book/modern-java-in-action/chapter-11/)

# null 대신 Optional 클래스

## 이 장의 내용

- `null` 참조의 문제점과 null을 멀리해야 하는 이유
- `null` 대신 `Optional` : null로부터 안전한 도메인 모델 재구현하기
- `Optional` 활용 : `null` 확인 코드 제거하기
- `Optional에` 저장된 값을 확인하는 방법
- 값이 없을 수도 있는 상황을 고려하는 프로그래밍


- `null` 의 유래
    - 1965년 토니 호어(Tony Hoare)라는 영국 컴퓨터과학자가 힙에 할당되는 레코드를 사용하며 형식을 갖는 최초의 프로그래밍 언어 중 하나인 알골(ALGOL W)을
      설계하면서 처음 null 참조가 등장했다. 그는 '구현하기가 쉬웠기 때문에 null을 도입했다'라고 그 당시를 회상한다. '컴파일러의 자동 확인 기능으로 모든 참조를
      안전하게 사용할 수 있을 것’을 목표로 정했다. 그 당시에는 null 참조 및 예외로 값이 없는 상황을 가장 단순하게 구현할 수 있다고 판단했고 결과적으로 null 및
      관련 예외가 탄생했다. 여러 해가 지난 후 호어는 당시 null 및 예외를 만든 결정을 가리켜 '십억 달러짜리 실수'라고 표현했다. 모든 자바 프로그래머라면 무심코 어떤
      객체의 필드를 사용하려 할 때 NullPointerException이라는 귀찮은 예외가 발생하는 상황을 몸소 겪었을 것이다(모든 객체가 null일 수 있기 때문에).
    - 자바를 포함해서 최근 수십 년간 탄생한 대부분의 언어 설계에는 null 참조 개념을 포함한다. 예전 언어와 호환성을 유지하려는 목적도 있었겠지만 호어가 말한 것처럼 '
      구현하기 쉬웠기 때문에' null 참조 개념을 포함했을 것이다.

## 11.1 값이 없는 상황을 어떻게 처리할까?

### 11.1.1 보수적인 자세로 NullPointerException 줄이기

### 11.1.2 null 때문에 발생하는 문제

- 에러의 근원이다
    - `NullPointerException`은 자바에서 가장 흔히 발생하는 에러다.
- 코드를 어지럽힌다
    - 때로는 중첩된 `null` 확인 코드를 추가해야 하므로 `null` 때문에 코드 가독성이 떨어진다.
- 아무 의미가 없다
    - `null`은 아무 의미도 표현하지 않는다. 특히 정적 형식 언어에서 값이 없음을 표현하는 방법으로는 적절하지 않다.
- 자바 철학에 위배된다
    - 자바는 개발자로부터 모든 포인터를 숨겼다. 하지만 예외가 있는데 그것이 바로 `null` 포인터다.
- 형식 시스템에 구멍을 만든다
    - `null`은 무형식이며 정보를 포함하고 있지 않으므로 모든 참조 형식에 `null`을 할당할 수 있다. 이런 식으로 `null`이 할당되기 시작하면서 시스템의 다른
      부분으로 `null`이 퍼졌을 때 애초에 `null`이 어떤 의미로 사용되었는지 알 수 없다.

### 11.1.3 다른 언어는 null 대신 무얼 사용하나?

- 그루비
    - 안전 내비게이션 연산자(navigation operator) (?. )를 도입
- ```groovy
    def carlnsuranceName = person?.car?.insurance?.name
    ```
- 그루비 안전 내비게이션 연산자를 이용하면 null 참조 예외 걱정 없이 객체에 접근할 수 있다. 이때 호출 체인에 null인 참조가 있으면 결과로 null이 반환된다.

> 사실 우리 데이터 모델이나 알고리즘이 null을 제대로 처리하는지 고민할 필요 없이 단순하게 늘 그래왔던 것처럼 null 예외 문제를 해결할 수 있지만 이는 문제의 본질을 해결하는 것이 아니라 문제를 뒤로 미루고 숨기는 것이나 마찬가지다. 나중에 코드를 다시 사용하는 사람은 점점 문제를 해결하기 어려운 상황에 놓인다. 물론 나중에 코드를 다시 사용하는 사람은 바로 우리 자신이 될 가능성이 크다.

- 하스켈, 스칼라 등의 함수형 언어
    - 하스켈은 선택형값(optional value)을 저장할 수 있는 Maybe라는 형식을 제공한다. Maybe는 주어진 형식의 값을 갖 거나 아니면 아무 값도 갖지 않을 수
      있다.
        - 따라서 null 참조 개념은 자연스럽게 사라진다.
    - 스칼라도 T 형식의 값을 갖거나 아무 값도 갖지 않을 수 있는 Option[T]라는 구조를 제공한다.
        - 그리고 Option 형식에서 제공하는 연산을 사용해서 값이 있는지 여부를 명시적으로 확인해야 한다(즉, null 확인). 형식 시스템에서 이를 강제하므로
          null과 관련한 문제가 일어날 가능성이 줄어든다.

## 11.2 Optional 클래스 소개

- 자바 8은 하스켈과 스칼라의 영향을 받아서 `java.util.Optional<T>`라는 새로운 클래스를 제공한다.
- `Optional`은 선택형값을 캡슐화하는 클래스다.
- ![그림 11-1 Optional Car](https://drek4537l1klr.cloudfront.net/urma2/Figures/11fig01.jpg)

## 11.3 Optional 적용 패턴

### 11.3.1 Optional 객체 만들기

- 빈 Optional

- null 이 아닌 값으로 Optional 만들기

- null값으로 Optional 만들기

### 11.3.2 맵으로 Optional의 값을 추출하고 변환하기

- ![그림 11-2 스트림과 Optional의 map 메서드 비교](https://drek4537l1klr.cloudfront.net/urma2/Figures/11fig02_alt.jpg)

### 11.3.3 flatMap으로 Optional 객체 연결

- ![그림 11-3 이차원 Optional](https://drek4537l1klr.cloudfront.net/urma2/Figures/11fig03.jpg)
- ![그림 11-4 스트림과 Optional의 flatMap 메서드 비교](https://drek4537l1klr.cloudfront.net/urma2/Figures/11fig04_alt.jpg)

- Optional로 자동차의 보험회사 이름 찾기
- ![그림 11-5 Optional을 활용한 Person/Car/lnsurance 참조 체인](https://drek4537l1klr.cloudfront.net/urma2/Figures/11fig05_alt.jpg)

- 도메인 모델에 Optional을 사용했을 때 데이터를 직렬화할 수 없는 이유

> [예제 11-4]에서는 Optional로 우리 도메인 모델에서 값이 꼭 있어야 하는지 아니면 값이 없을 수 있는지 여부를 구체적으로 표현할 수 있었다. 놀랍게도 Optional 클래스의 설계자는 이와 는 다른 용도로만 Optional 클래스를 사용할 것을 가정했다. 자바 언어 아키텍트인 브라이언 고츠(Brian Goetz)는 Optional의 용도가 선택형 반환 값을 지원하는 것이라고 명확하게 못 박았다.
>
> Optional 클래스는 필드 형식으로 사용할 것을 가정하지 않았으므로 Serializable 인터페이스를 구현하지 않는다. 따라서 우리 도메인 모델에 Optional을 사용한다면 직렬화(serializable) 모델을 사용하는 도구나 프레임워크에서 문제가 생길 수 있다. 이와 같은 단점에도 불구하고 여전히 Optional을 사용해서 도메인 모델을 구성하는 것이 바람직하다고 생각한다. 특히 객체 그래프에서 일부 또는 전체 객체가 null일 수 있는 상황이라면 더욱 그렇다. 직렬화 모델이 필요하다면 다음 예제에서 보여주는 것처럼 Optional로 값을 반환받을 수 있는 메서드를 추가하는 방식을 권장한다.
> ```java
> public class Person {
>   private Car car;
>   public Optional<Car> getCarAsOptional() {
>       return Optional.ofNullable(car);
>   }
> }
> ```
- Q:optional 쓰면 안되는 경우?

### 11.3.4 Optional 스트림 조작

### 11.3.5 디폴트 액션과 Optional 언랩

### 11.3.6 두 Optional 합치기

### 11.3.7 필터로 특정값 거르기

- Q: getName하면 빈 optional은 filter자체를 안하는건지?

## 11.4 Optional을 사용한 실용 예제

### 11.4.1 잠재적으로 null이 될 수 있는 대상을 Optional로 감싸기

### 11.4.2 예외와 Optional 클래스

### 11.4.3 기본형 Optional을 사용하지 말아야 하는 이유

### 11.4.4 응용

### 11.5 마치며

- 역사적으로 프로그래밍 언어에서는 null 참조로 값이 없는 상황을 표현해왔다.
- 자바 8에서는 값이 있거나 없음을 표현할 수 있는 클래스 java.util.Optional를 제공한다.
- 팩토리 메서드 Optional.empty, Optional.of, Optional.of Nullable 등을 이용해서 Optional 객체를 만들 수 있다.
- Optional 클래스는 스트림과 비슷한 연산을 수행하는 map, flatMap, filter 등의 메소드를 제공한다.
- Optional로 값이 없는 상황을 적절하게 처리하도록 강제할 수 있다. 즉, Optional로 예상치 못한 null 예외를 방지할 수 있다.
- Optional을 활용하면 더 좋은 API를 설계할 수 있다. 즉, 사용자는 메서드의 시그니처만 보고도 Optional 값이 사용되거나 반환되는지 예측할 수 있다.