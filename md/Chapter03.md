# [Chapter 03](https://livebook.manning.com/book/modern-java-in-action/chapter-3/)

## 람다 표현식

- 메서드로 전달할 수 있는 익명 함수를 단순화한 것
    - 익명
        - 보통의 메서드와 달리 이름이 없으므로 익명이라 표현한다. 구현해야 할 코드에 대한 걱정거리가 줄어든다.
    - 함수
        - 람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다. 하지만 메서드처럼 파라미터 리스트, 바디, 반환 형식, 가능한 예외 리스트를 포함한다
    - 전달
        - 람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다.
    - 간결성
        - 익명 클래스처럼 많은 자질구레한 코드를 구현할 필요가 없다.


![그림 3-1 람다 표현식은 파라미터, 화살표, 바디로 이루어진다.](https://drek4537l1klr.cloudfront.net/urma2/Figures/03fig01_alt.jpg)

- 예제 3-1 자바 8의 유효한 람다 표현식

```
(String s) -> s.length()
(Apple a) -> a.getWeight() > 150
(int x, int y) -> {
    System.out.println("Result:");
    System.out.println(x + y);
}
() -> 42
(Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
```

- [Java 8 lambda Void argument](https://stackoverflow.com/questions/29945627/java-8-lambda-void-argument)

    - Use [Supplier](https://docs.oracle.com/javase/8/docs/api/java/util/function/Supplier.html) if it takes nothing, but returns something.
    - Use [Consumer](https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html) if it takes something, but returns nothing.
    - Use [Callable](https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/Callable.html) if it returns a result and might throw (most akin to [Thunk](https://en.wikipedia.org/wiki/Thunk) in general CS terms).
    - Use [Runnable](https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html) if it does neither and cannot throw.

Use case

- Examples of lambdas

```
A boolean expression	(List<String> list) -> list.isEmpty()
Creating objects	() -> new Apple(10)
Consuming from an object	(Apple a) -> {
System.out.println(a.getWeight());
}
Select/extract from an object	(String s) -> s.length()
Combine two values	(int a, int b) -> a * b
Compare two objects	(Apple a1, Apple a2) ->
a1.getWeight().compareTo(a2.getWeight())
```

- 왜 함수형 인터페이스를 인수로 받는 메서드에만 람다 표현식을 사용할 수 있을까?
    - 언어 설계자들은 자바에 함수 형식(람다 표현식을 표현하는데 사용한 시그니처와 같은 특별한 표기법. 이 부분은 20장과 21장에서 다시 살펴본다)을 추가하는 방법도 대안으로 고려했다. 하지만 언어 설계자들은 언어를 더 복잡하게 만들지 않는 현재 방법을 선택했다. 또한 대부분의 자바 프로그래머가 하나의 추상 메서드를 갖는 인터페이스(예를 들면 이벤트 처리 인터페이스)에 이미 익숙하는 점도 고려했다.
    

- @FunctionalInterface
    - 함수형 인터페이스가 아니면 컴파일 에러

    
- 람다 활용 : 실행 어라운드 패턴(execute around pattern)

![Figure 3.3. Four-step process to apply the execute-around pattern](https://drek4537l1klr.cloudfront.net/urma2/Figures/03fig03_alt.jpg) 

- 함수 디스크립터(function descriptor)
    - 함수형 인터페이스의 추상 메서드 시그니처

![Table 3.2. Common functional interfaces added in Java 8]

![Table 3.3. Examples of lambdas with functional interfaces]

