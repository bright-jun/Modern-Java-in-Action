package exercise.chapter12;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.JapaneseDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.TimeZone;

public class Solution12_3 {

    public static void main(String[] args) {

        ZoneId romeZone = ZoneId.of("Europe/Rome");
        ZoneId zoneId = TimeZone.getDefault().toZoneId();

        // Listing 12.13. Applying a time zone to a point in time
        LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
        ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);

        // ZoneOffset
        LocalDateTime dateTime13 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
//        Instant instantFromDateTime = dateTime13.toInstant(romeZone);
        Instant instantFromDateTime = dateTime13.toInstant(ZoneOffset.of("-05:00"));
        Instant instant13 = Instant.now();
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant13, romeZone);

        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        // ZoneOffset은 Zoneld
        OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(dateTime13, newYorkOffset);

        // 대안 캘린더 시스템
        // 정적 메서드로 Temporal 인스턴스 생성
        LocalDate date14 = LocalDate.of(2014, Month.MARCH, 18);
        JapaneseDate japaneseDate = JapaneseDate.from(date);

        // 정적 팩토리 메서드 ofLocale을 이용해서 Chronology의 인스턴스 생성
        Chronology japaneseChronology = Chronology.ofLocale(Locale.JAPAN);
        ChronoLocalDate now = japaneseChronology.dateNow();

        // 이슬람력
        HijrahDate ramadanDate =
            HijrahDate.now().with(ChronoField.DAY_OF_MONTH, 1)
                .with(ChronoField.MONTH_OF_YEAR, 9);
        System.out.println("Ramadan starts on " +
            IsoChronology.INSTANCE.date(ramadanDate) +
            " and ends on " +
            IsoChronology.INSTANCE.date(
                ramadanDate.with(
                    TemporalAdjusters.lastDayOfMonth())));

        return;
    }
}
