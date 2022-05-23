package exercise.chapter12;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Solution12_1 {

    public static void main(String[] args) {
        // Listing 12.1. Creating a LocalDate and reading its values
        // 정적 팩토리 메서드 `of` 사용
        LocalDate date1 = LocalDate.of(2017, 9, 21);
        int year1 = date1.getYear();
        Month month1 = date1.getMonth();
        int day1 = date1.getDayOfMonth();
        DayOfWeek dow1 = date1.getDayOfWeek();
        int len1 = date1.lengthOfMonth();
        boolean leap1 = date1.isLeapYear();

        // 팩토리 메서드 `now`는 시스템 시계의 정보를 이용해서 현재 날짜 정보를 얻는다.
        LocalDate today1 = LocalDate.now();

        // Listing 12.2. Reading LocalDate values by using a TemporalField
        // TemporalField는 시간 관련 객체에서 어떤 필드의 값에 접근할지 정의하는 인터페이스다.
        // 열거자 ChronoField는 TemporalField 인터페이스를 정의
        int year2 = date1.get(ChronoField.YEAR);
        int month2 = date1.get(ChronoField.MONTH_OF_YEAR);
        int day2 = date1.get(ChronoField.DAY_OF_MONTH);

        // Listing 12.3. Creating a LocalTime and reading its values
        LocalTime time3 = LocalTime.of(13, 45, 20);
        int hour3 = time3.getHour();
        int minute3 = time3.getMinute();
        int second3 = time3.getSecond();

        // 날짜와 시간 문자열로 인스턴스 생성 by `parse`
        // DateTimeFormatter를 전달가능
        LocalDate date3 = LocalDate.parse("2017-09-21");
        LocalTime time31 = LocalTime.parse("13:45:20");

        // Listing 12.4. Creating a LocalDateTime directly or by combining a date and a time
        // LocalDateTime은 LocalDate와 LocalTime을 쌍으로 갖는 복합 클래스
        // 2017-09-21T13:45:20
        LocalDateTime dtt1 = LocalDateTime.of(2017, Month.SEPTEMBER, 21, 13, 45, 20);
        LocalDateTime dtt2 = LocalDateTime.of(date1, time3);
        LocalDateTime dtt3 = date1.atTime(13, 45, 20);
        LocalDateTime dtt4 = date1.atTime(time3);
        LocalDateTime dtt5 = time3.atDate(date1);

        LocalDate date14 = dtt1.toLocalDate();
        LocalTime time14 = dtt1.toLocalTime();

        // Instant 기계전용 시간 클래스
        Instant instant1 = Instant.ofEpochSecond(3);
        Instant instant2 = Instant.ofEpochSecond(3, 0);
        Instant instant3 = Instant.ofEpochSecond(2, 1_000_000_000);
        Instant instant4 = Instant.ofEpochSecond(4, -1_000_000_000);

        // java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: DayOfMonth
//        int day3 = Instant.now().get(ChronoField.DAY_OF_MONTH);

        // 두 시간 객체 사이의 지속시간
        // LocalDateTime은 사람이 사용하도록，Instant는 기계가 사용하도록 만들어진 클래스로 두 인스턴스는 서로 혼합할 수 없다.
        Duration d1 = Duration.between(dtt1, dtt2);
        Duration d2 = Duration.between(time14, time14);
        Duration d3 = Duration.between(instant1, instant2);
        // 한 Duration 클래스는 초와 나노초로 시간 단위를 표현하므로 between 메서드에 LocalDate를 전달할 수 없다
        // java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Seconds
//        Duration d4 = Duration.between(date3, date3);

        Period tenDays = Period.between(LocalDate.of(2017, 9, 11),
            LocalDate.of(2017, 9, 21));

        // Listing 12.5. Creating Durations and Periods
        Duration threeMinutes5 = Duration.ofMinutes(3);
        Duration threeMinutes52 = Duration.of(3, ChronoUnit.MINUTES);
        Period tenDays5 = Period.ofDays(10);
        Period threeWeeks5 = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay5 = Period.of(2, 6, 1);

        return;
    }
}
