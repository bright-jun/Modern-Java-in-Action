package exercise.chapter02;

// 제네릭을 사용하여 추상화
public interface Predicate<T> {
	boolean test(T t);
}
