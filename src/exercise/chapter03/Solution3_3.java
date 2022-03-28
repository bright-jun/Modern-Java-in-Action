package exercise.chapter03;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Solution3_3 {

	public String processFile() throws IOException {
		// try-with-resources (since java 7)
		// 자원을 명시적으로 닫을 필요가 없음
		try (BufferedReader br = new BufferedReader(new FileReader("res/chapter03/data.txt"))) {
			// 실제 필요한 작업
			return br.readLine();
		}
	}

	public String processFile(BufferedReaderProcessor p) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader("res/chapter03/data.txt"))) {
			// BufferedReader 객체 처리
			return p.process(br);
		}
	}

	public static void main(String[] args) throws IOException {
		Solution3_3 solution3_3 = new Solution3_3();
		String oneLine = solution3_3.processFile((BufferedReader br) -> br.readLine());
		String twoLines = solution3_3.processFile((BufferedReader br) -> br.readLine() + br.readLine());
		
		return;
	}
}
