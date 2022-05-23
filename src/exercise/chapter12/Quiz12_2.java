package exercise.chapter12;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class Quiz12_2 {

    public static void main(String[] args) {
        // TemporalAdjuster 인터페이스 구현은 Temporal 객체를 어떻게 다른 Temporal 객체로 변환할지 정의
        // TemporalAdjuster 인터페이스를 UnaryOperator<Temporal>과 같은 형식으로 간주
        LocalDate date = LocalDate.of(2014, 3, 18);
        date = date.with(new NextWorkingDay());

        // 람다 표현식
        date = date.with(temporal -> {
            DayOfWeek dow =
                DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
            else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });

        // 람다 표현식으로 정의
        // public static TemporalAdjuster ofDateAdjuster(UnaryOperator<LocalDate> dateBasedAdjuster) {
        TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(
            temporal -> {
                DayOfWeek dow =
                    DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                int dayToAdd = 1;
                if (dow == DayOfWeek.FRIDAY) dayToAdd = 3;
                else if (dow == DayOfWeek.SATURDAY) dayToAdd = 2;
                return temporal.plus(dayToAdd, ChronoUnit.DAYS);
            });
        date = date.with(nextWorkingDay);
        return;
    }
}
