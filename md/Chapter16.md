# [Chapter 16](https://livebook.manning.com/book/modern-java-in-action/chapter-16/)

# CompletableFuture : 안정적 비동기 프로그래밍

# 이 장의 내용

- 비동기 작업을 만들고 결과 얻기
- 비블록 동작으로 생산성 높이기
- 비동기 API 설계와 구현
- 동기 API를 비동기적으로 소비하기
- 두 개 이상의 비동기 연산을 파이프라인으로 만들고 합치기
- 비동기 작업 완료에 대응하기

16장에서는 실용적인 예제를 통해 자바 8에서 제공하는 Future의 구현 CompletableFuture이 비동기 프로그램에 얼마나 큰 도움을 주는지 설명한다. 또한 자바9에서
추가된 내용도 소개할 것이다.

## 16.1 Future의 단순 활용

Future 인터페이스(자바 5)

- 미래의 어느 시점에 결과를 얻는 모델에 활용
- 비동기 계산을 모델링
- 계산이 끝났을 때 결과에 접근할 수 있는 참조를 제공
- 시간이 걸릴 수 있는 작업을 Future 내부로 설정하면 호출자 스레드가 결과를 기다리는 동안 다른 유용한 작업을 수행할 수 있다.
- 저수준의 스레드에 비해 직관적으로 이해하기 쉽다

> 세탁소에 한 무더기의 옷을 드라이클리닝 서비스를 맡기는 동작에 비유
>
> 세탁소 주인은 드라이클리닝이 언제 끝날지 적힌 영수증(Future)을줄 것이다. 드라이클리닝이 진행되는 동안 우리는 원하는 일을 할 수 있다

Future를 이용하려면 시간이 오래 걸리는 작업을 Callable 객체 내부로 감싼 다음에 ExecutorService에 제출해야 한다.

- [그림 16-1] Future로 시간이 오래 걸리는 작업을 비동기적으로 실행하기
    - ![](https://drek4537l1klr.cloudfront.net/urma2/Figures/16fig01_alt.jpg)
    - idle(유휴)
- 오래 걸리는 작업이 영원히 끝나지 않는 문제가 있을수 있음
    - get 메서드를 오버로드해서 우리 스레드가 대기할 최대 타임아웃 시간을 설정하는 것이 좋다.

### 16.1.1 Future 제한

- Future 인터페이스가 비동기 계산이 끝났는지 확인할 `isDone` 메서드
- 계산이 끝나길 기다리는 메서드
- 결과 회수 메서드

하지만 이들 메서드만으로는 간결한 동시 실행 코드를 구현하기에 충분하지 않다.

- 여러 Future의 결과가 있을 때 이들의 의존성은 표현하기가 어렵다
- > 오래 걸리는 A라는 계산이 끝나면 그 결과를 다른 오래 걸리는 계산 B로 전달하시오.
  > 그리고 B의 결과가 나오면 다른 질의의 결과와 B의 결과를 조합하시오

선언형 기능이 있다면 유용할 것

- 두 개의 비동기 계산 결과를 하나로 합친다. 두 가지 계산 결과는 서로 독립적일 수 있으며 또는 두 번째 결과가 첫 번째 결과에 의존하는 상황일 수 있다.
- Future 집합이 실행하는 모든 태스크의 완료를 기다린다.
- Future 집합에서 가장 빨리 완료되는 태스크를 기다렸다가 결과를 얻는다(예를 들어 여러 태스크가 다양한 방식으로 같은 결과를 구하는 상황).
- 프로그램적으로 Future를 완료시킨다(즉, 비동기 동작에 수동으로 결과 제공).
- Future 완료 동작에 반응한다(즉, 결과를 기다리면서 블록되지 않고 결과가 준비되었다는 알림을 받은 다음에 Future의 결과로 원하는 추가 동작을 수행할 수 있음).

### 16.1.2 CompletableFuture로 비동기 애플리케이션 만들기

CompletableFuture 클래스 (Future 인터페이스를 구현한 클래스)

- 지금까지 설명한 기능을 선언형으로 이용할 수 있도록 자바 8에서 새로 제공
- 람다 표현식과 파이프라이닝을 활용
    - Future ~ CompletableFuture === Collection ~ Stream

## 16.2 비동기 API 구현

> 어떤 제품이나 서비스를 이용해야 하는 상황이라고 가정하자. 예산을 줄일 수 있도록 여러 온라인상점 중 가장 저렴한 가격을 제시하는 상점을 찾는 애플리케이션을 완성해가는 예제

1. 고객에게 비동기 API를 제공하는 방법을 배운다
    1. 온라인상점을 운영하고 있는 독자에게 특히 유용한 기술
2. 동기 API를 사용해야 할 때 코드를 비블록으로 만드는 방법을 배운다.
    1. 두 개의 비동기 동작을 파이프라인으로 만드는 방법과 두 개의 동작 결과를 하나의 비동기 계산으로 합치는 방법을 살펴본다.
    2. 예를 들어 온라인상점에서 우리가 사려는 물건에 대응하는 할인 코드를 반환한다고 가정하자. 우리는 다른 원격 할인 서비스에 접근해서 할인 코드에 해당하는 할인율을 찾아야
       한다. 그래야 원래 가격에 할인율을 적용해서 최종 결과를 계산할 수 있다.
3. 비동기 동작의 완료에 대응하는 방법을 배운다.
    1. 모든 상점에서 가격 정보를 얻을 때까지 기다리는 것이 아니라 각 상점에서 가격 정보를 얻을 때마다 즉시 최저가격을찾는 애플리케이션을 갱신하는 방법을 설명한다
    2. 그렇지 않으면 서버가 다운되는 등 문제가 발생했을 때 사용자에게 검은 화면만 보여주게 될 수 있다.

### 동기 API와 비동기 API

> 전통적인 동기 API에서는 메서드를 호출한 다음에 메서드가 계산을 완료할 때까지 기다렸다가 메서드가 반환되면 호출자는 반환된 값으로 계속 다른 동작을 수행한다. 호출자와 피호출자가 각각 다른 스레드에서 실행되는 상황이었더라도 호출자는 피호출자의 동작 완료를 기다렸을 것이다. 이처럼 동기 API를 사용하는상황을 블록 호출(blocking call) 이라고 한다.
>
> 반면 비동기 API에서는 메서드가 즉시 반환되며 끝내지 못한 나머지 작업을 호출자 스레드와 동기적으로 실행될 수 있도록 다른 스레드에 할당한다. 이와 같은 비동기 API를 사용하는 상황을 비블록 호출(non-blocking call)이라고 한다. 다른 스레드에 할당된 나머지 계산 결과는 콜백 메서드를 호출해서 전달하거나 호출자가 '계산 결과가 끝날 때까지 기다림' 메서드를 추가로 호출하면서 전달된다. 주로 I/O 시스템 프로그래밍에서 이와 같은 방식으로 동작을 수행한다. 즉, 계산 동작을 수행하는 동안 비동기적으로 디스크 접근을 수행한다. 그리고 더 이상 수행할 동작이 없으면 디스크 블록이 메모리로 로딩될 때까지 기다린다.

### 16.2.1 동기 메서드를 비동기 메서드로 변환

`java.util.concurrent.Future` 인터페이스(자바 5)

- 비동기 계산의 결과를 표현할수 있음
- 호출자 스레드가 블록되지 않고 다른 작업을 실행할 수 있다
- Future는 결과값의 핸들일 뿐
    - 계산이 완료되면 결과를 Future를 통해 결과를 얻을 수 있다.
    - 단 인터페이스 구현체는 즉시 반환되므로 호출자 스레드는 다른 작업을 수행할 수 있다 CompletableFuture 클래스(자바8)
    - 동기 메서드를 비동기 메서드로 변환하는데 도움이 되는 기능을 제공
    - 비동기 계산과 완료 결과를 포함하는 CompletableFuture 인스턴스 생성 가능
    - **계산이 끝나기 전에 Future 인스턴스를 바로 반환**
    - complete 메서드를 이용해서 CompletableFuture를 종료할 수 있다

### 16.2.2 에러 처리 방법

가격을 계산하는 동안 에러가 발생하면 어떻게 될까?

- 예외가 발생하면 해당 스레드에만 영향을 미친다.
- 에러가 발생해도 가격 계산은 계속 진행되며 일의 순서가 꼬인다.
- 결과적으로 클라이언트는 get 메서드가 반환될 때까지 영원히 기다리게 될 수도 있다.

// 타임아웃값을 받는 get 메서드의 오버로드 버전을 만들어 문제를 해결

메인 쓰레드에 exception을 제공

## 16.3 비블록 코드 만들기

### 16.3.1 병렬 스트림으로 요청 병렬화하기

- CompletableFuture 클래스의 join 메서드는 Future 인터페이스의 get 메서드와 같은 의미를 갖는다.
- join은 아무 예외도 발생시키지 않는다는 점이 다르다

?? .get()은 값이 올때까지 block을 함. ?? .join()도 값이 올때까지 block을 하는가?

1. 은 무조건 다 순차적으로 기다림
2. 도 기다릴수는 있지만, 1처럼 아예 줄줄이 기다리지는 않음. 병렬수행하면서 결과얻을 때만 기다림

### 16.3.2 CompletableFuture로 비동기 호출 구현하기

[그림 16-2] 스트림의 게으름 때문에 순차 계산이 일어나는 이유와 순차 계산을 회피하는 방법
![](https://drek4537l1klr.cloudfront.net/urma2/Figures/16fig02_alt.jpg)

### 16.3.3 더 확장성이 좋은 해결 방법

### 16.3.4 커스텀 Executor 사용하기

#### 💡 스레드 풀 크기 조절

> "자바 병렬 프로그래밍(Java Concurrency in Practice), 브라이언 게츠 공저" 참고

- 스레드 풀이 너무 크면 CPU와 메모리 자원을 서로 경쟁하느라 시간을 낭비
- 스레드 풀이 너무 작으면 CPU의 일부 코어는 활용되지 않을 수 있음
- `N_threads = N_cpu * U_cpu * (1 + W/C)`
    - N_cpu : Runtime.getRuntime().availableProcessors()가 반환히는 코어 수
    - U_cpu : 0과 1 사이의 값을 갖는 CPU 활용 비율
    - W/C : 는 대기시간과 계산시간의 비율


- 예시
    - W/C = 100
        - 우리 애플리케이션은 상점의 응답을 대략 99퍼센트의 시간만큼 기다리므로 W/C 비율을 100 으로 간주할 수 있다
    - N_cpu = 4
    - U_cpu = 100
        - 대상 CPU 활용률이 100퍼센트라면
    - 400스레드를 갖는 풀을 만들어야 함을 의미
    - 상점 수보다 많은 스레드를 갖는 것은 낭비
        - 하지만 상점 수보다 많은 스레드를 가지고 있어 봐야 사용할 가능성이 전혀없으므로
    - 한상점에 하나의 스레드가 할당될 수 있도록
        - 가격 정보를 검색하려는 상점 수만큼 스레드를 갖도록 Executor를 설정한다


- 하나의 Executor에서 사용할 스레드의 최대 개수는 100 이하로 설정하는 것이 바람직
    - 스레드 수가 너무 많으면 오히려 서버가 크래시될 수 있으므로

- 우리가 만드는 풀은 데몬 스레드(daemon thread)를 포함한다.
- 일반 스레드가 실행 중이면 자바 프로그램은 종료되지 않는다. 따라서 어떤 이벤트를 한없이 기다리면서 종료되지 않는 일반 스레드가 있으면 문제가 될 수 있다.
- 데몬 스레드는 자바 프로그램이 종료될 때 강제로 실행이 종료될 수 있다.
- 두 스레드의 성능은 같다

## 16.4 비동기 작업 파이프라인 만들기

클라이언트가 블록되는 상황을 거의 완벽하게 회피하는 방법

- 블록하지 않고 Future의 작업이 끝났을 때만 이를 통지받으면서 람다 표현식이나 메서드 참조로 정의된 콜백 메서드를 실행

### 16.4.1 할인서비스구현

### 16.4.2 할인 서비스 사용

### 16.4.3 동기 작업과 비동기 작업 조합하기

[그림 16-3] 동기 작업과 비동기 작업 조합하기

![](https://drek4537l1klr.cloudfront.net/urma2/Figures/16fig03.jpg)

#### 가격 정보 얻기

- 팩토리 메서드 supplyAsync 에 람다 표현식을 전달해서 비동기적으로 상점에서 정보를 조회
- 변환 결과 `Stream<CompletableFuture<String>>`
- 커스텀 Executor 로 CompletableFuture 를 설정

#### Quote 파싱하기

- 첫 번째 결과 문자열을 Quote로 변환
- 파싱 동작에서는 원격 서비스나 I/O가 없으므로 원하는 즉시 지연 없이 동작을 수행할 수 있다
- 생성된 CompletableFuture에 thenApply 메서드를 호출한 다음에 문자열을 Quote 인스턴스로 변환하는 Function으로 전달한다
- thenApply 메서드는 CompletableFuture가 끝날 때까지 블록하지 않는다
    - CompletableFuture가 동작을 완전히 완료한 다음에 thenApply 메서드로 전달된 람다표현식을 적용할 수 있다
- `CompletableFuture<String>` -> `CompletableFuture<Quote>` 로 변환

#### CompletableFuture를 조합해서 할인된 가격 계산하기

- 상점에서 받은 할인전 가격에 원격 Discount 서비스에서 제공하는 할인율을 적용
- 이번에는 원격 실행이 포함되므로 이전 두 변환과 다르며 동기적으로 작업을수행해야 한다(1초의 지연으로 원격 실행을 흉내낸다)
- 람다 표현식(lambda expression)으로 이 동작을 팩토리 메서드 supplyAsync에 전달할 수 있다. 그러면 다른 CompletableFuture가 반환된다.
    - 두 가지 CompletableFuture로 이루어진 연쇄적으로 수행되는 두 개의 비동기 동작을 만들 수 있다.
    - 상점에서 가격 정보를 얻어 와서 Quote로 변환하기
    - 변환된 Quote를 Discount 서비스로 전달해서 할인된 최종가격 획득하기
- `List<CompletableFuture<String>>` 반환


- 자바 8의 CompletableFuture API는 이와 같이 두 비동기 연산을 파이프라인으로 만들 수 있도록 thenCompose 메서드를 제공
    - Future가 여러 상점에서 Quote를 얻는 동안 메인 스레드는 UI 이벤트에 반응하는 등 유용한 작업을 수행할 수 있다.


- 스레드 전환 오버헤드가 적게 발생하면서 효율성이 좀 더 좋은 thenCompose를 사용
    - Async로 끝나지 않는 메서드는 이전 작업을 수행한 스레드와 같은 스레드에서 작업을 실행함을 의미하며 Async로 끝나는 메서드는 다음 작업이 다른 스레드에서
      실행되도록 스레드 풀로 작업을 제출한다
    - 여기서 두 번째 CompletableFuture의 결과는 첫 번째 CompletableFuture에 의존하므로 두 CompletableFuture를 하나로 조합하든
      Async 버전의 메서드를 사용하든 최종 결과나 개괄적인 실행시간에는 영향을 미치지 않는다

### 16.4.4 독립 CompletableFuture와 비독립 CompletableFuture 합치기

```java
Future<Double> futurePriceInUSD=
    // 제품가격 정보를 요청하는 첫 번째 태스크를 생성한다
    CompletableFuture.supplyAsync(()->shop.getPrice(product))
    .thenCombine(
    CompletableFuture.supplyAsync(
    // USD, EUR의 횐율정보를요청하는 독립적인 두 번째 태스크를 생성한다.
    ()->exchangeService.getRate(Money.EUR,Money.USD)),
    // 두 결과를 곱해서 가격과 환율 정보를 합친다
    (price,rate)->price*rate
    ));
```

[그림 16-4] 독립적인 두 개의 비동기 태스크 합치기

![](https://drek4537l1klr.cloudfront.net/urma2/Figures/16fig04_alt.jpg)

### 16.4.5 Future의 리플렉션과 CompletableFuture의 리플렉션

```java
// 태스크를 스레드 풀에 제출할수 있도록 ExecutorService를 생성한다.
ExecutorService executor=Executors.newCachedThreadPool();
final Future<Double> futureRate=executor.submit(new Callable<Double>(){
public Double call(){
    // EUR, USD 환율정보를 가져올 Future를 생성한다
    return exchangeService.getRate(Money.EUR,Money.USD);
    }});
    Future<Double> futurePriceInUSD=executor.submit(new Callable<Double>(){
public Double call(){
    // 두 번째 Future 로 상점에서 요청 제품의 가격을 검색한다.
    double priceInEUR=shop.getPrice(product);
    // 가격을 검색한 Future 를 이용해서 가격과 환율을 곱한다.
    return priceInEUR*futureRate.get();
    }});
```

?? 장점 : 

### 16.4.6 타임아웃 효과적으로 사용하기

![](https://drek4537l1klr.cloudfront.net/urma2/Figures/java.jpg)

```java
Future<Double> futurePriceInUSD=
    CompletableFuture.supplyAsync(()->shop.getPrice(product))
    .thenCombine(
    CompletableFuture.supplyAsync(
    ()->exchangeService.getRate(Money.EUR,Money.USD)),
    (price,rate)->price*rate
    ))
    // 3초뒤에 작업이 완료되지 않으면 Future가 TimeoutException 을 발생시키도록 설정.
    // 자바9에서는비동기 타임아웃관리 기능이 추가됨
    .orTimeout(3,TimeUnit.SECONDS);
```

```java
Future<Double> futurePriceInUSD=
    CompletableFuture.supplyAsync(()->shop.getPrice(product))
    .thenCombine(
    CompletableFuture.supplyAsync(
    ()->exchangeService.getRate(Money.EUR,Money.USD))
    // 환전 서비스가 일 초 안에 결과를 제공하지 않으면 기본환율값을 사용
    .completeOnTimeout(DEFAULT_RATE,1,TimeUnit.SECONDS),
    (price,rate)->price*rate
    ))
    .orTimeout(3,TimeUnit.SECONDS);


```

## 16.5 CompletableFuture의 종료에 대응하는 방법

[예제 16-21] 0.5초에서 2.5초 사이의 임의의 지연을 흉내 내는 메서드

```java
private static final Random random=new Random();
public static void randomDelay(){
    int delay=500+random.nextInt(2000);
    try{
    Thread.sleep(delay);
    }catch(InterruptedException e){
    throw new RuntimeException(e);
    }
    }
```

### 16.5.1 최저가격 검색 애플리케이션 리팩터링

[예제 16-22] Future 스트림을 반환하도록 findPrices 메서드 리팩터링하기

```java
public Stream<CompletableFuture<String>>findPricesStream(String product){
    return shops.stream()
    .map(shop->CompletableFuture.supplyAsync(
    ()->shop.getPrice(product),executor))
    .map(future->future.thenApply(Quote::parse))
    .map(future->future.thenCompose(quote->
    CompletableFuture.supplyAsync(
    ()->Discount.applyDiscount(quote),executor)));
    }
```

우리가 원하는 동작은 이 값을 출력하는 것이다.

```java
findPricesStream("myPhone").map(f->f.thenAccept(System.out::println));
```

[예제 16-23] CompletableFuture 종료에 반응하기

```java
CompletableFuture[]futures=findPricesStream("myPhone")
    .map(f->f.thenAccept(System.out::println))
    .toArray(size->new CompletableFuture[size]);
    // 실행완료 전부 기다렸다가 실행
    CompletableFuture.allOf(futures).join();
```

- `allOf` <-> `anyOf`

### 16.5.2 응용

```java
long start=System.nanoTime();
    CompletableFuture[]futures=findPricesStream("myPhone27S")
    .map(f->f.thenAccept(
    s->System.out.println(s+" (done in "+
    ((System.nanoTime()-start)/1_000_000)+" msecs)")))
    .toArray(size->new CompletableFuture[size]);
    CompletableFuture.allOf(futures).join();
    System.out.println("All shops have now responded in "
    +((System.nanoTime()-start)/1_000_000)+" msecs");
```

```java
BuyItAll price is184.74(done in 2005msecs)
    MyFavoriteShop price is192.72(done in 2157msecs)
    LetsSaveBig price is135.58(done in 3301msecs)
    ShopEasy price is167.28(done in 3869msecs)
    BestPrice price is110.93(done in 4188msecs)
    All shops have now responded in 4188msecs
```

## 16.6 로드맵

17장에서는 CompletableFuture（연산 또는 값으로 종료하는 일회성 기법）의 기능이 한 번에 종료되지 않고 일련의 값을 생산하도록 일반화하는 자바 9 플로 API를
살펴본다.

## 16.7 마치며

- 한 개 이상의 원격 외부 서비스를 시용하는 긴 동작을 실행할 때는 비동기 방식으로 애플리케이션의 성능과 반응성을 향상시킬 수 있다.
- 우리 고객에게 비동기 API를 제공하는 것을 고려해야 한다. CompletableFuture의 기능을 이용하면 쉽게 비동기 API를 구현할 수 있다.
- CompletableFuture를 이용할 때 비동기 태스크에서 발생한 에러를 관리하고 전달할 수 있다.
- 동기 API를 CompletableFuture로 감싸서 비동기적으로 소비할 수 있다.
- 서로 독립적인 비동기 동작이든 아니면 하나의 비동기 동작이 다른 비동기 동작의 결과에 의존하는 상황이든 여러 비동기 동작을 조립하고 조합할 수 있다.
- CompletableFuture에 콜백을 등록해서 Future가 동작을 끝내고 결과를 생산했을 때 어떤 코드를 실행하도록 지정할 수 있다.
- CompletableFuture 리스트의 모든 값이 완료될 때까지 기다릴지 아니면 첫 값만 완료되길 기다릴지 선택할 수 있다.
- 자바 9에서는 orTimeout, completeOnTimeout 메서드로 CompletableFuture에 비동기 타임아웃 기능을 추가했다.