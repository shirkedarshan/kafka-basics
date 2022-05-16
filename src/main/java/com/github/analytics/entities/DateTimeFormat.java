package entities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeFormat {
    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
    private static final String ZONE_INDIA = "Asia/Kolkata";

    public static String getCurrentTimeStamp() {
        TIMESTAMP_FORMAT.setTimeZone(TimeZone.getTimeZone(ZONE_INDIA));
        String timestamp = TIMESTAMP_FORMAT.format(new Date());
        return timestamp;
    }

    public static String timestampIn_yyyyMMddHHmmss(String timestamp) {
        String givenTimestamp = String.valueOf(timestamp);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localTimestamp = LocalDateTime.parse(givenTimestamp, dateFormat);

        ZoneId zoneIdOfIndia = ZoneId.of(ZONE_INDIA);
        ZonedDateTime transformedTimestamp = ZonedDateTime.of(localTimestamp, zoneIdOfIndia);
        return transformedTimestamp.toString();
    }
}