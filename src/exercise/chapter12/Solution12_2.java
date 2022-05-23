package exercise.chapter12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Solution12_2 {

    public static void main(String[] args) {
        // Listing 12.6. Manipulating the attributes of a LocalDate in an absolute way
        LocalDate date1 = LocalDate.of(2017, 9, 21);
        LocalDate date2 = date1.withYear(2011);
        LocalDate date3 = date2.withDayOfMonth(25);
        LocalDate date4 = date2.withMonth(2);
        LocalDate date5 = date3.with(ChronoField.MONTH_OF_YEAR, 2);

        // Listing 12.7. Manipulating the attributes of a LocalDate in a relative way
        LocalDate date17 = LocalDate.of(2017, 9, 21);
        LocalDate date27 = date1.plusWeeks(1);
        LocalDate date37 = date2.minusYears(6);
        // ChronoUnit 열거형은 TemporalUnit 인터페이스를 쉼게 활용할 수 있는 구현을 제공
        LocalDate date47 = date3.plus(6, ChronoUnit.MONTHS);

        // Listing 12.8. Using the predefined TemporalAdjusters
        LocalDate date18 = LocalDate.of(2014, 3, 18);
        LocalDate date28 = date18.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDate date38 = date28.with(lastDayOfMonth());

        // TemporalAdjuster 인터페이스 구현은 Temporal 객체를 어떻게 다른 Temporal 객체로 변환할지 정의
        // TemporalAdjuster 인터페이스를 UnaryOperator<Temporal>과 같은 형식으로 간주

        // DateTimeFormatter
        LocalDate date = LocalDate.of(2014, 3, 18);
        String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

        LocalDate date110 = LocalDate.parse("20140318",
            DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate date210 = LocalDate.parse("2014-03-18",
            DateTimeFormatter.ISO_LOCAL_DATE);

        // Listing 12.10. Creating a DateTimeFormatter from a pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date310 = LocalDate.of(2014, 3, 18);
        String formattedDate = date1.format(formatter);
        LocalDate date410 = LocalDate.parse(formattedDate, formatter);

        // Listing 12.11. Creating a localized DateTimeFormatter
        DateTimeFormatter italianFormatter =
            DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);
        LocalDate date111 = LocalDate.of(2014, 3, 18);
        String formattedDate11 = date.format(italianFormatter); // 18. marzo 2014
        LocalDate date211 = LocalDate.parse(formattedDate11, italianFormatter);

        // Listing 12.12. Building a DateTimeFormatter
        DateTimeFormatter italianFormatter12 = new DateTimeFormatterBuilder()
            .appendText(ChronoField.DAY_OF_MONTH)
            .appendLiteral(".!!!! ")
            .appendText(ChronoField.MONTH_OF_YEAR)
            .appendLiteral("++++ ")
            .appendText(ChronoField.YEAR)
            .parseCaseInsensitive()
            .toFormatter(Locale.ITALIAN);

        LocalDate date311 = LocalDate.of(2014, 3, 18);
        String formattedDate211 = date.format(italianFormatter12); // 18. marzo 2014
        LocalDate date411 = LocalDate.parse(formattedDate211, italianFormatter12);

        return;
    }
}
