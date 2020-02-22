package com.sagar.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class Interview {
    public void sum(int a, long b) {
        System.out.println("undere sum int ");
    }

    public void sum(long a, int b) {
        System.out.println("under long ");
    }

    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
        String formattedDateOfUtc = FORMATTER.format(zonedDateTime);
        System.out.println(formattedDateOfUtc);

//        String s = "sagar";
//        Interview interview = new Interview();
//        interview.sum(10L, 10);
//        interview.sum(10,10L);
       /* try {
            throw new NullPointerException("file not found");
        } catch (NullPointerException e) {
            System.out.println("IOEX");
            return;
        } catch (RuntimeException e) {
            System.out.println("EXCE");
            return;
        } finally {
            System.out.println("i");
            return;
        }*/

    }
}
