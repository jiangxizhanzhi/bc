package com.cyberpeace.cr.teamtrain.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DateRangeUtils {

    /**
     * 获取日期范围内的所有年份
     */
    public static List<String> getAllYears(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startTime, formatter);
        LocalDate endDate = LocalDate.parse(endTime, formatter);

        List<String> years = new ArrayList<>();

        LocalDate current = startDate.withDayOfYear(1); // 从当年的1月1日开始
        LocalDate endYear = endDate.withDayOfYear(1);   // 结束年的1月1日

        while (!current.isAfter(endYear)) {
            String year = String.valueOf(current.getYear());
            if (!years.contains(year)) {
                years.add(year);
            }
            // 跳到下一年
            current = current.plusYears(1);
        }

        return years;
    }

    /**
     * 获取日期范围内的所有月份（yyyy-MM格式）
     */
    public static List<String> getAllMonths(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startTime, formatter);
        LocalDate endDate = LocalDate.parse(endTime, formatter);

        List<String> months = new ArrayList<>();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // 设置起始日期为该月的第一天
        LocalDate current = startDate.withDayOfMonth(1);
        LocalDate endMonth = endDate.withDayOfMonth(1);

        while (!current.isAfter(endMonth)) {
            String month = current.format(monthFormatter);
            if (!months.contains(month)) {
                months.add(month);
            }
            current = current.plusMonths(1);
        }

        return months;
    }

    /**
     * 获取日期范围内的所有天（yyyy-MM-dd格式）
     */
    public static List<String> getAllDays(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startTime, formatter);
        LocalDate endDate = LocalDate.parse(endTime, formatter);

        List<String> days = new ArrayList<>();
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        for (int i = 0; i < numOfDays; i++) {
            LocalDate date = startDate.plusDays(i);
            days.add(date.format(formatter));
        }

        return days;
    }

    /**
     * 测试示例
     */
    public static void main(String[] args) {
        String startTime = "2023-01-15";
        String endTime = "2024-03-20";

        System.out.println("基础版本:");
        System.out.println("所有年份: " + getAllYears(startTime, endTime));
        System.out.println("所有月份: " + getAllMonths(startTime, endTime));
        System.out.println("所有天数: " + getAllDays(startTime, endTime));


        // 测试边界情况
        System.out.println("\n边界测试 - 同一天:");
        System.out.println("所有年份: " + getAllYears("2023-01-01", "2023-01-01"));
        System.out.println("所有月份: " + getAllMonths("2023-01-01", "2023-01-01"));
        System.out.println("所有天数: " + getAllDays("2023-01-01", "2023-01-01"));

        // 跨年测试
        System.out.println("\n跨年测试:");
        System.out.println("所有年份: " + getAllYears("2022-12-25", "2024-01-05"));
    }
}
