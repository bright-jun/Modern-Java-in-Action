package playground;

class Person {
	String name;

	Person(String name) {
		this.name = name;
	}
}

class Student extends Person {
	String check;

	Student(String name) {
		super(name);
	}
}

public class Casting {
	public static void main(String[] args) {
		Student s1 = new Student("홍길동");
		s1.check = "TEST";
		Person p1 = s1;	// 업캐스팅
//		p1.name = "이름이다.";
//		p1.check = "컴파일 에러 발생";	// 컴파일 에러 발생
		
		
		Person p2 = new Student("홍길동");

		Student s2 = (Student) p2; // 다운캐스팅
//		s2.name = "김유신";
//		s2.check = "check!";

//		Student s3 = new Person("홍길동");
//		s3.check = "TEST";
//		Person p3 = s3;	// 업캐스팅
//		p1.name = "이름이다.";
//		p1.check = "컴파일 에러 발생";	// 컴파일 에러 발생

		
		return;
	}
}
