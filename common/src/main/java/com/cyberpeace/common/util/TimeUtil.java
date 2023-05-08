package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TimeUtil {

    /**
     * 计算起始时间和结束时间相隔的秒数。
     *
     * @param start
     * @param end
     * @return
     */
    public static long getIntervalSeconds(Date start, Date end) {
        if (null == start || null == end || end.before(start)) {
            return 0;
        }

        return (end.getTime() - start.getTime()) / 1000;
    }

    /**
     * 计算起始时间和结束时间相隔的天数。
     *
     * @param start
     * @param end
     * @return
     */
    public static long getIntervalDay(Date start, Date end) {
        if (null == start || null == end || end.before(start)) {
            return 0;
        }

        start = getEndOfDay(start);
        end = getEndOfDay(end);

        return (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取指定位数的验证码
     *
     * @param bits 验证码位数，比如bits=6代表6位的验证码
     * @return
     */
    public static String getVerifyCode(int bits) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < bits; i++) {
            stringBuilder.append(new Random().nextInt(10));
        }

        return stringBuilder.toString();
    }

    /**
     * 获取指定月份的第一天/最后一天
     *
     * @param month yyyy-MM格式，"2018-04"
     * @param head  true为获取指定月份第一天；false获取指定月份最后一天
     * @return yyyy-MM-dd，"2018-04-30"
     */
    public static String getHeadOrTailDayOfSepecificMonth(String month, boolean head) {
        String[] ss = month.split("-");
        int yyyy = Integer.parseInt(ss[0]);
        int mm = Integer.parseInt(ss[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(yyyy, mm - 1, 1);
        if (head) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }

        return format6(calendar.getTime());
    }

    /**
     * 获取指定天的第一秒，0点0点0秒
     *
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取指定天的最后一秒，23点59分59秒
     *
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    /**
     * 获取当前小时，0-23之间的值
     *
     * @return
     */
    public static int getCurrentHour() {
        Date date = new Date();
        return date.getHours();
    }

    public static int hours2Now(Date date) {
        if (null == date) {
            return 0;
        }

        return (int) ((System.currentTimeMillis() - date.getTime()) / (1000 * 60 * 60));
    }

    /**
     * 获取当前的年和月
     *
     * @return 如果现在是2018/08/01，返回201808
     */
    public static String thisMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前月的前一个月的月份
     *
     * @return 如果现在是2018/08/01，返回201807
     */
    public static String previouseMonth(int input) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1 * input);
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMM");

        return sdFormat.format(calendar.getTime());
    }

    /**
     * 格式为2018-06-21 12:30:00的字符串转换为date
     *
     * @param time
     * @return
     */
    public static Date toDate1(String time) {
        if (null == time) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result;
        try {
            result = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    /**
     * 格式为2018-06-21的字符串转换为date
     *
     * @param time
     * @return
     */
    public static Date toDate2(String time) {
        if (null == time) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date result;
        try {
            result = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    /**
     * 格式为20180621101010的字符串转换为date
     *
     * @param time
     * @return
     */
    public static Date toDate3(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date result;
        try {
            result = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    /**
     * 格式为2018/06/21 10:10的字符串转换为date
     *
     * @param time
     * @return
     */
    public static Date toDate4(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        time = time.replace("/", "-") + ":00";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result;
        try {
            result = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    /**
     * 格式为0621101011的字符串转换为date(小于当前日期最大年份)
     *
     * @param time
     * @return
     */
    public static Date toDate5(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        time = year + time;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date result;
        try {
            result = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return null;
        }
        if (result.after(new Date())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(result);
            calendar.add(Calendar.YEAR, -1);
            result = calendar.getTime();
        }
        return result;
    }

    /**
     * 格式为05-01 12:30的字符串转换为date
     *
     * @param time
     * @return
     */
    public static Date toDate6(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));

        String toFormat = year + "-" + time;
        return toDate1(toFormat);
    }

    /**
     * 格式化成2017-07-12 18:00:20的形式
     *
     * @param date
     * @return
     */
    public static String format1(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成2017/07/12的形式
     *
     * @param date
     * @return
     */
    public static String format2(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成yyyyMMddHHmmss的形式
     *
     * @param date
     * @return
     */
    public static String format3(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成0712的形式
     *
     * @param date
     * @return
     */
    public static String format4(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成20180712的形式
     *
     * @param date
     * @return
     */
    public static String format5(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成2017-07-12的形式
     *
     * @param date
     * @return
     */
    public static String format6(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成2017/07/12 18:00:20的形式
     *
     * @param date
     * @return
     */
    public static String format7(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成07-12的形式
     *
     * @param date
     * @return
     */
    public static String format8(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化成07-12 18:00的形式
     * @param date
     * @return
     */
    public static String format9(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取传入日期00:00:00的时间
     *
     * @param date
     * @return
     */
    public static Date toZero(Date date) {
        SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat simp2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date result = null;
        try {
            result = simp2.parse(simp.format(date));
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    /**
     * 在指定的时间base基础上，增加toAdd秒
     *
     * @return
     */
    public static Date addSeconds(Date base, long toAdd) {
        return new Date(base.getTime() + toAdd * 1000);
    }

    /**
     * 在指定的时间base基础上，增加toAdd分
     *
     * @return
     */
    public static Date addMins(Date base, long toAdd) {
        return new Date(base.getTime() + toAdd * 1000 * 60);
    }

    /**
     * 在指定的时间base基础上，增加toAdd小时
     *
     * @return
     */
    public static Date addHours(Date base, long toAdd) {
        return new Date(base.getTime() + toAdd * 1000 * 60 * 60);
    }

    /**
     * 在指定的时间base基础上，增加toAdd月
     *
     * @return
     */
    public static Date addMonths(Date base, int toAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(Calendar.MONTH, toAdd);
        return calendar.getTime();
    }

    /**
     * 在指定的时间base基础上，增加toAdd年
     *
     * @return
     */
    public static Date addYears(Date base, int toAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(Calendar.YEAR, toAdd);
        return calendar.getTime();
    }

    /**
     * 在指定的时间base基础上，增加toAdd日
     *
     * @return
     */
    public static Date addDates(Date base, int toAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(Calendar.DATE, toAdd);
        return calendar.getTime();
    }

    /**
     * 在指定的时间base基础上，减少toReduce小时
     *
     * @return
     */
    public static Date reduceHours(Date base, long toReduce) {
        return new Date(base.getTime() - toReduce * 1000 * 60 * 60);
    }

    /**
     * 在指定的时间base基础上，较少toAdd日
     *
     * @return
     */
    public static Date reduceDates(Date base, int toReduce) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(base);
        calendar.add(Calendar.DATE, -toReduce);
        return calendar.getTime();
    }

    /**
     * 获取两日期时间间隔天数，包含首尾日期（传入参数为日期）
     *
     * @return
     */
    public static int getDaysBetween(Date dtBegin, Date dtEnd) {
        return new Long((dtEnd.getTime() - dtBegin.getTime()) / 86400000).intValue() + 1;
    }

    /**
     * 判断输入的日期是否符合格式标准
     *
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取当前日期（只获取日）
     */
    public static String getDay() {
        SimpleDateFormat sd = new SimpleDateFormat("dd");
        return sd.format(new Date()).toString();
    }

}
