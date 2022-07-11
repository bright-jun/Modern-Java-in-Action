# [Chapter 18](https://livebook.manning.com/book/modern-java-in-action/chapter-18/)

# 함수형 관점으로 생각하기

## 이 장의 내용

- 왜 함수형 프로그래밍을 사용하는가?
- 함수형 프로그래밍은 어떻게 정의하는가?
- 선언형 프로그래밍과 참조 투명성
- 함수형 스타일의 자바 구현 가이드라인
- 반복과 재귀

### 함수형(functional)

- 람다
- 일급 함수
- 객체 변화를 제한

## 18.1 시스템 구현과 유지보수

### 좋은 유지보수성의 조건

- **불변성(immutability)** <-> Stateless
    - `synchronized`키워드를 멀리해라.
    - 동시성 버그를 고치는 일은 정말 어렵다.
    - 자바 8의 스트림을 이용하면 잠금(locking) 문제를 신경 쓰지 않을 수 있었다
        - 단，자바 8의 스트림을 이용하려면 상태 없는 동작이어야 한다는 조건을 만족해야 한다
        - 즉, 스트림 처리 파이프라인의 함수는 다른 누군가가 변수의 값을 바꿀 수 있는 상태에 있는 변수를 사용하지 않는다
- **부작용 없음(no side effect)** <-> 프로그램이 시스템의 구조를 이해하기 쉽게 클래스 계층으로 반영
    - 결합성(coupling)
        - 시스템의 각 부분의 상호 의존성
    - 응집성(cohesion)
        - 시스템의 다양한 부분이 서로 어떤 관계를 갖는지 가리킴

### 18.1.1 공유된 가변 데이터

부작용(side effect)

- 함수 내에 포함되지 못한 기능
    - 자료구조를 고치거나 필드에 값을 할당(setter 메서드 같은 생성자 이외의 초기화 동작)
    - 예외 발생
    - 파일에 쓰기 등의 I/O 동작 수행
    - 변수가 예상하지 못한 값을 가짐
        - 시스템의 여러 메서드에서 공유된 가변 데이터 구조를 읽고 갱신하기 때문
        - 프로그램 전체에서 데이터 갱신 사실을 추적하기가 어려워진다

[그림 18-1] 여러 클래스에서 공유하는 가변 리스트. 어느 클래스가 소유자인지 구분하기 어렵다.
![Figure 18.1. A mutable shared across multiple classes. It’s difficult to understand what owns the list.](https://drek4537l1klr.cloudfront.net/urma2/Figures/18fig01_alt.jpg)

#### 해결 방법

순수(pure) 메서드 또는 부작용 없는(side effect free)메서드

- 자신을 포함하는 클래스의 상태 그리고 다른 객체의 상태를 바꾸지 않으며 `return` 문을 통해서만 자신의 결과를 반환하는 메서드

불변 객체

- 인스턴스화한 다음에는 객체의 상태를 바꿀 수 없는 객체이므로 함수 동작에 영향을 받지 않는다
- 인스턴스화한 불변 객체의 상태는 결코 예상하지 못한 상태로 바뀌지 않는다
- 복사하지 않고 공유할 수 있으며, 객체의 상태를 바꿀 수 없으므로 스레드 안전성을 제공

#### 장점

부작용 없는 시스템 컴포넌트

- 메서드가 서로 간섭하는 일이 없으므로 잠금을 사용하지 않고도 멀티코어 병렬성을 사용할 수 있다.
- 또한 프로그램의 어떤 부분이 독립적인지 바로 이해할 수 있다.

### 18.1.2 선언형 프로그래밍

명령형 프로그래밍(imperative programming)

- 어떻게(how)에 집중하는 프로그래밍 형식
- 명령어(할당，조건문，분기문，루프 등)가 컴퓨터의 저수준 언어와 비슷하게 생겼음
```java
Transaction mostExpensive = transactions.get(0);
if(mostExpensive == null)
    throw new IllegalArgumentException("Empty list of transactions");
for(Transaction t: transactions.subList(1, transactions.size())){
    if(t.getValue() > mostExpensive.getValue()){
        mostExpensive = t;
    }
}
```
선언형 프로그래밍(declarative programming)
- 무엇을(what)에 집중하는 프로그래밍 형식
- 내부 반복(internal iteration)
  - 질의문 구현 방법은 라이브러리가 결정한다
- 문제 자체가 코드로 명확하게 드러난다
```java
Optional<Transaction> mostExpensive =
    transactions.stream()
                .max(comparing(Transaction::getValue));
```

### 18.1.3 왜 함수형 프로그래밍인가?

좀더 쉽게 시스템을 구현하고 유지보수하는 데 도움을 준다.

- 함수형 프로그래밍은 선언형 프로그래밍을 따르는 대표적인 방식
- 부작용이 없는 계산을 지향

## 18.2 함수형 프로그래밍이란 무엇인가?

[그림 18-2] 부작용을 포함하는 함수

![](https://drek4537l1klr.cloudfront.net/urma2/Figures/18fig02.jpg)

[그림 18-3] 부작용이 없는 함수

![](https://drek4537l1klr.cloudfront.net/urma2/Figures/18fig03.jpg)

함수형 프로그래밍에서 함수

- 수학적인 함수
    - 함수는 0개 이상의 인수를 가지며, 한 개 이상의 결과를 반환하지만 **부작용이 없어야 한다**

함수형

- 수학의 함수처럼 **부작용이 없다**는 것을 의미

[메서드 vs 함수](https://sustainable-dev.tistory.com/33)

- 자바와 같은 언어에서는 바로 수학적인 함수냐 아니냐가 메서드와 함수를 구분하는 핵심
- 메서드
    - 객체에 종속적
        - (JavaScript)함수가 객체의 프로퍼티로 저장된다면, 이것은 메소드이다.
        - 메소드는 호출된 객체에 암시적으로 전달된다.
        - 메소드는 클래스 안에 있는 data를 조작할 수 있다.
    - 멤버 함수(Member Function)
    - Java의 람다식
        - 메서드? 함수?
            - Java의 경우 람다식을 사용할 수 있지만 Java는 메소드를 단독으로 선언할 수 없고 항상 클래스의 구성 멤버로 선언하기 때문에 람다식은 단순히 메소드를
              선언하는 것이 아니라 이 메소드를 가지고 있는 객체를 생성해 낸다. 따라서 함수같지만 알고보면 여전히 메소드인 것으로 보인다.
- 함수
    - 객체로부터 독립적
        - (JavaScript)함수가 객체의 프로퍼티가 아니라면, 이것은 함수이다.

순수 함수형 프로그래밍

- 함수 그리고 `if-then-else` 등의 수학적 표현만 사용

함수형 프로그래밍

- 시스템의 다른 부분에 영향을 미치지 않는다면 내부적으로는 함수형이 아닌 기능도 사용
    - 내부적으로는 부작용이 발생하지만 호출자가 이를 알아차리지 못한다면 실제로 부작용이 발생한 것이라고 말할 수 없음
    - 호출자에 아무 영향을 미치지 않는다면 호출자는 내부적인 부작용을 파악하거나 신경 쓸 필요가 없다.

### 18.2.1 함수형자바

실질적으로 자바로는 완벽한 순수 함수형 프로그래밍을 구현하기 어렵다.

- 자바의 I/O 모델 자체에는 부작용 메서드가 포함
  - `Scanner.nextLine`을 호출하면 파일의 행을 소비한다.
  - 즉, `Scanner.nextLine`을 두 번 호출하면 다른 결과가 반환될 가능성이 있다.

시스템의 컴포넌트가 순수한 함수형인 것처럼 동작하도록 코드를 구현할 수 있다

- 실제 부작용이 있지만 아무도 이를 보지 못하게 함으로써 함수형을 달성할 수 있다.
  - 부작용을 일으키지 않는 어떤 함수나 메서드가 있는데, 다만 진입할 때 어떤 필드의 값을 증가시켰다가 빠져나올 때 필드의 값을 돌려놓는다고 가정
  - 단일 스레드로 실행되는 프로그램의 입장에서는 이 메서드가 아무 부작용을 일으키지 않으므로 이 메서드는 함수형이라 간주할 수 있다
  - 다른 스레드가 필드의 값을 확인 한다든가 아니면 동시에 이 메서드를 호출하는 상황이 발생할 수 있다면 이 메서드는 함수형이 아니다
  - 메서드의 바디를 잠금(lock)으로써 이 문제를 해결 -> 이 메서드는 함수형이라 할 수 있다
- 멀티코어 프로세서의 두 코어를 활용해서 메서드를 병렬로 호출할 수 없게 된다
  - 프로그램 입장에서 부작용이 사라졌으나
  - 프로그래머 관점에서 프로그램의 실행 속도가 느려지게 됨

함수형 함수나 메서드

- 지역 변수만을 변경해야 함
- 함수나 메서드에서 참조하는 객체가 있다면 그 객체는 불변 객체여야 한다
  - 객체의 모든 필드가 `final`
  - 모든 참조 필드는 불변 객체를 직접 참조
- 예외적으로 메서드 내에서 생성한 객체의 필드는 갱신할 수 있다
  - 단, 새로 생성한 객체의 필드 갱신이 외부에 노출되지 않아야 하고 다음에 메서드를 다시 호출한 결과에 영향을 미치지 않아야 한다.
- 함수나 메서드가 어떤 예외도 일으키지 않아야 한다
  - 예외가 발생하면 이전에 설명한 것처럼 블랙박스 모델에서 `return`으로 결과를 반환할 수 없게 될 수 있기 때문
  - [그림 18-4] 예외를 일으키는 함수
  - ![Figure 18.4. A function throwing an exception](https://drek4537l1klr.cloudfront.net/urma2/Figures/18fig04_alt.jpg)
  - 수학적 함수(mathematical function)
    - 주어진 인수값에 대응하는 하나의 결과를 반환한다
    - 부분 함수(partial function)으로 활용
  - 이러한 제약은 함수형을 수학적으로 활용하는 데 큰 걸림돌이 될 것이다
    - 예외자체도 하나의 결과로써 반환받으면 된다.
    - `Optional<T>`
      - 결과값으로 연산을 성공적으로 수행했는지 아니면 요청된 연산을 성공적으로 수행하지 못했는지 확인할 수 있다
      - 호출자는 메서드 호출 결과로 빈 `Optional`이 반환되는지 확인
        - 귀찮은 작업처럼 보일 수 있지만, 모든 코드가 `Optional`을 사용하도록 반드시 고쳐야 하는 것은 아니며 여러분이 함수형 프로그래밍과 순수 함수형
          프로그래밍의 장단점을 실용적으로 고려해서 다른 컴포넌트에 영향을 미치지 않도록 지역적으로만 예외를 사용하는 방법도 고려할 수 있다
- 함수형에서는 비함수형 동작을 감출 수 있는 상황에서만 부작용을 포함하는 라이브러리 함수를 사용해야 한다
  - 자료구조를 복사한다든가 발생할 수 있는 예제를 적절하게 내부적으로 처리함으로써 자료구조의 변경을 호출자가 알 수 없도록 감춰야 한다
  - `insertAll`이라는 메서드 내에서 `List.add`를 호출하기 전에 미리 리스트를 복사함으로써 라이브러리 함수에서 일으키는 부작용을 감춤
  - 이와 같은 설명을 주석으로 표현하거나 마커 어노테이션(marker annotation)으로 메서드를 정의할 수 있다

우리가 만든 함수형 코드에서는 일종의 로그 파일로 디버깅 정보를 출력하도록 구현하는 것이 좋다.

- 디버깅 정보를 출력하는 것은 함수형의 규칙에 위배되지만 로그 출력을 제외하고는 함수형 프로그래밍의 장점을 문제없이 누릴 수 있다.

### 18.2.2 참조 투명성

참조 투명성(referential transparency)

- 부작용을 감춰야 한다
- 같은 인수로 함수를 호출했을 때 항상 같은 결과를 반환
  - 함수는 어떤 입력이 주어졌을 때 언제, 어디서 호출하든 같은 결과를 생성
  - `String.replace`는 참조적으로 투명하다
    - `"raoul".replace('r', 'R')` 이라는 코드는 항상 같은 결과가 나옴
    - `String.replace`는 this 객체를 갱신하는 것이 아니라 대문자 'R'을 소문자 'r'로 바꿔서 새로운 `String`을 반환한다.
  - `Random.nextInt`는 함수형이 될 수 없다
  - 자바의 `Scanner` 객체로 사용자의 키보드 입력을 받는다면 참조 투명성을 위배할 수도 있고 아닐수도 있다.
    - 두 개의 `final int` 변수를 더하는 연산에서는 두 변수를 바꿀 수 없으므로 이 연산은 항상 같은 결과를 생성
    - `nextLine` 메서드를 호출했을 때 매번 다른 결과가 나올 수 있다

장점

- 프로그램 이해에 큰 도움을 준다
- 비싸거나 오랜 시간이 걸리는 연산을 기억화(memorization) 또는 캐싱(caching)을 통해 다시 계산하지 않고 저징하는 최적화 기능도 제공

자바에서의 문제

- `List`를 반환하는 메서드를 두 번 호출한다고 가정
- 두 번의 호출 결과로 같은 요소를 포함하지만 서로 다른 메모리 공간에 생성된 리스트를 참조
- 결과 리스트가 가변 객체라면
  - 반환된 두 리스트가 같은 객체라 할 수 없으므로
  - 리스트를 반환하는 메서드는 참조적으로 투명한 메서드가 아니다
- 결과 리스트를 불변의 순수값으로 사용할 것이라면
  - 두 리스트가 같은 객체라고 볼 수 있으므로
  - 리스트 생성 함수는 참조적으로 투명한 메서드이다

### 18.2.3 객체지향 프로그래밍과 함수형 프로그래밍

함수형 프로그래밍 vs 기존의 익스트림 객체지향 프로그래밍

- 자바 8은 함수형 프로그래밍을 익스트림 객체지향 프로그래밍의 일종으로 간주
- 대부분의 자바 프로그래머는 무의식적으로 함수형 프로그래밍의 일부 기능과 익스트림 객체지향 프로그래밍의 일부 기능을 사용하게 될 것
- 자바 소프트웨어 엔지니어의 프로그래밍 형식이 좀 더 함수형으로 다가갈 것
  - (멀티코어 등의) 하드웨어 변경
  - (데이터베이스의 질의와 비슷한 방식으로 데이터를 조작하는 등의) 프로그래머의 기대치

익스트림 객체지향 방식

- 모든 것을 객체로 간주하고 프로그램이 객체의 필드를 갱신하고, 메서드를 호출하고, 관련 객체를 갱신하는 방식으로 동작

함수형 프로그래밍 방식

- 참조적 투명성을 중시하는, 즉, 변화를 허용하지 않는 함수형 프로그래밍 형식

실제로 자바 프로그래머는 이 두 가지 프로그래밍 형식을 혼합

- `Iterator`로 가변 내부 상태를 포함하는 자료구조를 탐색하면서 함수형 방식으로 자료구조에 들어 있는 값의 합계를 계산할 수 있다
  - 자바에서는 지역 변수 변화가 수반될 수 있다

### 18.2.4 함수형 실전 연습

함수형 프로그래밍의 기능

- 모듈성이 좋고 멀티코어 프로세서에 적합한 프로그램을 구현하는 데 도움을 줌

서브집합 구하는 함수

- input : `List<Integer> = {1, 4, 9}`
- output : `List<List<Integer>> = {{}, {1}, {4}, {9}, {1, 4}, {1, 9}, {4, 9}, {1,4,9}}`

```java
static List<List<Integer>> subsets(List<Integer> list) {
    // 입력 리스트가 비어있다면
    if (list.isEmpty()) {
        List<List<Integer>> ans = new ArrayList<>();
        // 빈 리스트가 서브 집합
        ans.add(Collections.emptyList());
        return ans;
    }
    Integer fst = list.get(0);
    List<Integer> rest = list.subList(1,list.size());
    
    // 빈 리스트가 아니면 먼저 하나의 요소를 꺼내고 나머지 요소의 모든 서브집합을 찾아서 subans 로 전달한다.
    // subAns는 절반의 정답을포함한다.
    List<List<Integer>> subAns = subsets(rest);
    // 정답의 나머지 절반을 포함하는 subAns2는 subAns의 모든 리스트에 처음 꺼낸 요소를 앞에 추가해서 만든다.
    List<List<Integer>> subAns2 = insertAll(fst, subAns);
    // subans, subans2S 연결
    return concat(subAns, subAns2);
}
```

```java
static List<List<Integer>> insertAll(Integer fst,
                                     List<List<Integer>> lists) {
    List<List<Integer>> result = new ArrayList<>();
    for (List<Integer> list : lists) {
        List<Integer> copyList = new ArrayList<>();
        // 리스트를 복사한 다음에 복사한 리스트에 요소를 추가한다.
        // 구조체가 가변이더라도 저수준 구조를 복사하진 않는다
        // Integer는 가변이 아니다.
        copyList.add(fst);
        copyList.addAll(list);
        result.add(copyList);
    }
    return result;
}
```
- 호출자가 리스트를 복사하는 코드를 추가하는게 아니라 메소드 내부에서 처리해야 함수형으로 쓰기에 자연스러움

1.
  ```java
  static List<List<Integer>> concat(List<List<Integer>> a,
                                    List<List<Integer>> b) {
      a.addAll(b);
      return a;
  }
  ```
2. 
  ```java
  static List<List<Integer>> concat(List<List<Integer>> a,
                                    List<List<Integer>> b) {
      List<List<Integer>> r = new ArrayList<>(a);
      r.addAll(b);
      return r;
  }
  ```

- 첫 번째 버전의 `concat`은 순수 함수가 아님
  - `concat(subans, subans2)`를 호출한 다음에 `subans`의 값을 다시 참조하지 않는다는 가정을 함
  - 그렇지 않으면 값이 바뀔 수 있음
  - 그렇지 않기 때문에 두 번째 버전보다 가벼움
  - 잠재적인 버그가 있음
- 두 번째 버전의 `concat`은 순수 함수
  - 내부적으로는 리스트 `r`에 요소를 추가하는 변화가 발생하지만 반환 결과는 오로지 인수에 의해 이루어지며 인수의 정보는 변경하지 않는다

인수에 의해 출력이 결정되는 함수형 메서드의 관점에서 프로그램 문제를 생각하자

- 무엇을 해야 하는가에 중점을 둔다
- 설계 단계에서 어떻게 문제를 해결할 것이고 무엇을 변화할 것인지 결정하는 기존 방식에 비해 더 생산적일 때가 많다
  - 설계 단계는 이와 같은 결정을 내리기에 너무 이른 상황이기 때문이다

## 18.3 재귀와 반복

재귀

- 무엇을 해야 하는가에 집중할수 있도록 도움을 주는 함수형 프로그래밍의 한 기법

순수 함수형 프로그래밍 언어에서는 `while`, `for` 같은 반복문을 포함하지 않는다.

- 이러한 반복문 때문에 변화가 자연스럽게 코드에 스며들 수 있기 때문
  - `while`루프의 조건문을 갱신해야 할 때가 있다. 그렇지 않으면 루프가 아예 실행되지 않거나 무한으로 반복될 수 있다
  - 이 외의 일반적인 상황에서는 루프를 안전하게 사용할 수 있다
  - 함수형 스타일에서는 다른 누군가가 변화를 알아차리지만 못한다면 아무 상관이 없다
    - 지역 변수는 자유롭게 갱신할 수 있다
```java
Iterator<Apple> it = apples.iterator();
while (it.hasNext()) {
   Apple apple = it.next();
   // ...
}
```
- 호출자는 변화를 확인할 수 없으므로 아무 문제가 없다
  - `next`로 `Iterator`의 상태를 변환했고, `while` 바디 내부에서 `apple` 변수에 할당하는 동작을 할 수 있다
```java
public void searchForGold(List<String> l, Stats stats){
    for(String s: l){
        if("gold".equals(s)){
            stats.incrementFor("gold");
        }
    }
}
```
- 루프의 바디에서 함수형과 상충하는 부작용이 발생한다.
  - 루프 내부에서 프로그램의 다른 부분과 공유되는 `stats` 객체의 상태를 변화시킨다.

이러한 문제 때문에 하스켈 같은 순수 함수형 프로그래밍 언어에서는 부작용 연산을 원천적으로 제거

혹은 재귀를 이용

- 이론적으로 반복을 이용하는 모든 프로그램은 재귀로도 구현할 수 있다
- 재귀를 이용하면 변화가 일어나지 않는다
- 루프 단계마다 갱신되는 반복 변수를 제거할 수 있다

```java
static long factorialIterative(long n) {
    long r = 1;
    for (int i = 1; i <= n; i++) {
        r *= i;
    }
    return r;
}
```
- 일반적인 루프를 사용한 코드로 매 반복마다 변수 `r`과 `i`가 갱신
```java
static long factorialStreams(long n){
    return LongStream.rangeClosed(1, n)
                     .reduce(1, (long a, long b) -> a * b);
}
```
- 스트림
```java
static long factorialRecursive(long n) {
    return n == 1 ? 1 : n * factorialRecursive(n-1);
}
```
- 재귀(자신을 호출하는 함수)방식의 코드로 좀 더 수학적인 형식으로 문제를 해결
  - 반복코드보다 재귀 코드가 더 비싸다.
    - `factorialRecursive` 함수를 호출할 때마다 호출 스택에 각 호출시 생성되는 정보를 저장할 새로운 스택 프레임이 만들어진다.
    - 재귀 팩토리얼의 입력값에 비례해서 메모리 사용량이 증가한다.
    - 따라서 큰 입력값을 사용하면 다음처럼 `StackOverflowError` 가 발생한다.
    - `Exception in thread "main" java.lang.StackOverflowError`
  - 함수형 언어에서는 꼬리 호출 최적화(tail call optimization) 라는 해결책을 제공
```java
static long factorialTailRecursive(long n) {
    return factorialHelper(1, n);
}
static long factorialHelper(long acc, long n) {
    return n == 1 ? acc : factorialHelper(acc * n, n-1);
}
```

- `factorialHelper`에서 재귀 호출이 가장 마지막에서 이루어지므로 꼬리 재귀다.
  - 중간 결과를 각각의 스택 프레임으로 저장해야 하는 일반 재귀와 달리 꼬리 재귀에서는 컴파일러가 하나의 스택 프레임을 재활용할 가능성이 생긴다.
  - `factorialHelper`의 정의에서는 중간 결과(팩토리얼의 부분결과)를 함수의 인수로 직접 전달한다.
- `factorialRecursive`에서 마지막으로 수행한 연산은 `n`과 재귀 호출의 결과값의 곱셈이다.
- [그림 18-5] 여러 스택 프레임을 사용하는 팩토리얼의 재귀 정의
- ![Figure 18.5. Recursive definition of factorial, which requires several stack frames](https://drek4537l1klr.cloudfront.net/urma2/Figures/18fig05_alt.jpg)
- [그림 18-6] 단일 스택 프레임을 재사용하는 팩토리얼의 꼬리 재귀 정의
- ![Figure 18.6. Tail-recursive definition of factorial, which can reuse a single stack frame](https://drek4537l1klr.cloudfront.net/urma2/Figures/18fig06_alt.jpg)
- 안타깝게도 자바는 이와 같은 최적화를 제공하지 않는다
  - 그럼에도 여전히 고전적인 재귀보다는 여러 컴파일러 최적화 여지를 남겨둘 수 있는 꼬리 재귀를 적용하는 것이 좋다
- 스칼라, 그루비 같은 최신 JVM 언어는 이와 같은 재귀를 반복으로 변환하는 최적화를 제공한다(속도의 손실 없이).
  - 결과적으로 순수 함수형을 유지하면서도 유용성뿐 아니라 효율성까지 두 마리의 토끼를 모두 잡을수 있다

### 결론

- 자바 8에서는 반복을 스트림으로 대체해서 변화를 피할 수 있다.
- 또한 반복을 재귀로 바꾸면 더 간결하고, 부작용이 없는 알고리즘을 만들 수 있다.
- 재귀를 이용하면 좀 더 쉽게 읽고, 쓰고, 이해할 수 있는 예제를 만들 수 있다.
- 약간의 실행시간 차이보다는 프로그래머의 효율성이 더 중요할 때도 많다.

## 18.4 마치며

- 공유된 가변 자료구조를 줄이는 것은 장기적으로 프로그램을 유지 보수하고 디버깅하는 데 도움이 된다.
- 함수형 프로그래밍은 부작용이 없는 메서드와 선언형 프로그래밍 방식을 지향한다.
- 함수형 메서드는 입력 인수와 출력 결과만을 갖는다.
- 같은 인수 값으로 함수를 호출했을 때 항상 같은 값을 반환하면 참조 투명성을 갖는 함수다. while 루프 같은 반복문은 재귀로 대체할 수 있다.
- 자바에서는 고전 방식의 재귀보다는 꼬리 재귀를 사용해야 추가적인 컴파일러 최적화를 기대할 수 있다.