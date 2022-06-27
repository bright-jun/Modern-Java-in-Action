package exercise.chapter16;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Solution16_1 {

    public Double doSomeLongComputation() {
        System.out.println("doSomeLongComputation in");
        int sum = 0;
        for (long i = 0; i < 100000000L; i++) {
            sum++;
        }
        /*
        sum /= 0;
         */
        System.out.println("doSomeLongComputation out");
        return Double.valueOf("1");
    }

    public void doSomethingElse() {
        System.out.println("doSomethingElse");
    }


    public static void main(String[] args) {
        Solution16_1 sol = new Solution16_1();

        // 스레드 풀에 태스크를 제출하려면 ExecutorService를 만들어야 한다
        ExecutorService executor = Executors.newCachedThreadPool();
        // Callable을 ExecutorService 로 제출한다
        Future<Double> future = executor.submit(new Callable<Double>() {
            public Double call() {
                // 비동기, 다른 스레드에서
                return sol.doSomeLongComputation();
            }
        });
        // 동기
        sol.doSomethingElse();
        try {
            // 비동기 작업의 결과를 가져온다. 결과가 준비되어있지 않으면 호출 스레드가 블록된다. 하지만 최대 1초까지만 기다린다.
            Double result = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException ee) {
            // the computation threw an exception
            System.out.println("the computation threw an exception");
        } catch (InterruptedException ie) {
            // the current thread was interrupted while waiting
            System.out.println("the current thread was interrupted while waiting");
        } catch (TimeoutException te) {
            // the timeout expired before the Future completion
            System.out.println("the timeout expired before the Future completion");
        }
    }
}
