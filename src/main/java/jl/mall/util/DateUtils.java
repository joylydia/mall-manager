package jl.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author jack
 */
@Slf4j
public class DateUtils {

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSS2 = "yyyyMMddHHmmssSSS";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM_SS2 = "yyyy-MM-dd HH.mm.ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HHMMSS = "HHmmss";
    public static final String HH = "HH";


    /**
     * 获取月份字符串
     *
     * @param f
     * @param format
     * @return
     */
    public static String getMonth(int f, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, f);
        DateFormat df = new SimpleDateFormat(format);
        String result = df.format(calendar.getTime());
        return result;
    }

    public static String getDay(int f, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, f);
        DateFormat df = new SimpleDateFormat(format);
        String result = df.format(calendar.getTime());
        return result;
    }

    public static String addDay(int f, String format, String dateTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate(dateTime, "yyyy-MM-dd HH:mm:ss"));
        calendar.add(Calendar.DAY_OF_MONTH, f);
        DateFormat df = new SimpleDateFormat(format);
        String result = df.format(calendar.getTime());
        return result;
    }

    public static String addDay(int f, String format, Date dateTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.DAY_OF_MONTH, f);
        DateFormat df = new SimpleDateFormat(format);
        String result = df.format(calendar.getTime());
        return result;
    }

    public static Date addDay(int f, Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.DAY_OF_MONTH, f);
        return calendar.getTime();
    }

    public static Date addSec(int f, Date dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.SECOND, f);
        return calendar.getTime();
    }

    public static Date addMin(int f, Date dateTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.MINUTE, f);
        return calendar.getTime();
    }

    public static String getDay(int f, String format, String dateStr) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        Date date = df.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, f);

        String result = df.format(calendar.getTime());
        return result;
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static Date parse(String date, String format) throws ParseException {
        if (date == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(date);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date getCurDate(Date date, String format) throws ParseException {
        if (date == null) {
            return new Date();
        }
        DateFormat df = new SimpleDateFormat(format);
        return toDate(df.format(date), format);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatForTime(Date date) {
        if (date == null) {
            return "";
        }
        String format = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getStrCurDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 当前年月日时分
     *
     * @return
     */
    public static String getCurrDateStr12() {
        return format(new Date(), "yyyyMMddHHmm");
    }

    /**
     * 当前年月日时分秒
     *
     * @return
     */
    public static String getCurrDateStr14() {
        return format(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 当前时分秒
     *
     * @return
     */
    public static String getCurrDateStr16() {
        return format(new Date(), "HHmmss");
    }

    public static boolean compareWithNow(Date date, int hours) {
        long difTime = 1000 * 60 * 60 * hours;
        Date now = new Date();
        long nowTime = now.getTime();
        long otherTime = date.getTime();
        boolean result = nowTime - otherTime > difTime;
        return result;
    }

    public static Date toDate(String str, String format) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(str);
    }

    public static Date getStartWithCurrentDay(Date currentTime) {
        Calendar calendar = Calendar.getInstance();
        currentTime.setSeconds(0);
        currentTime.setMinutes(0);
        currentTime.setHours(0);
        calendar.setTime(currentTime);
        return calendar.getTime();
    }

    public static Date getEndWithCurrentDay(Date currentTime) {
        Calendar calendar = Calendar.getInstance();
        currentTime.setSeconds(59);
        currentTime.setMinutes(59);
        currentTime.setHours(23);
        calendar.setTime(currentTime);
        return calendar.getTime();
    }

    /**
     * 获得日期数组
     *
     * @param calendarType 日期跨度的类型，
     */

    public static Date[] getDayArrays(Date start, Date end, int calendarType) {
        ArrayList<Date> ret = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Date tmpDate = calendar.getTime();
        long endTime = end.getTime();
        while (tmpDate.before(end) || tmpDate.getTime() == endTime) {
            ret.add(calendar.getTime());
            calendar.add(calendarType, 1);
            tmpDate = calendar.getTime();
        }

        Date[] dates = new Date[ret.size()];
        return ret.toArray(dates);
    }

    /**
     * 获得日期字符串数组
     *
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public static String[] getDayArrays(String start, String end) throws ParseException {
        Date dateStart = toDate(start, "yyyy-MM-dd");
        Date dateEnd = toDate(end, "yyyy-MM-dd");
        Date[] strArray = getDayArrays(dateStart, dateEnd, Calendar.DAY_OF_YEAR);
        String[] retArray = new String[strArray.length];
        int index = 0;
        for (Date string : strArray) {
            retArray[index] = format(string, "yyyy-MM-dd");
            System.out.println(format(string, "yyyy-MM-dd"));
            index++;
        }
        return retArray;
    }

    public static String getCurrentTime() {
        String beginTime = "";
        String endTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        if (cal.get(Calendar.HOUR) >= 3 && cal.get(Calendar.HOUR) < 24) {
            beginTime = sdf.format(cal.getTime()) + " 03:00:00";
            cal.add(Calendar.DATE, 1);
            endTime = sdf.format(cal.getTime()) + " 03:00:00";
        } else {
            endTime = sdf.format(cal.getTime()) + " 03:00:00";
            cal.add(Calendar.DATE, -1);
            beginTime = sdf.format(cal.getTime()) + " 03:00:00";
        }
        return beginTime + "#" + endTime;
    }

    public static String getDateByDay(Integer day) {
        Date dNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dNow);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());

    }

    public static boolean timeBetween(String fromTime, String endTime, Date valDate) {
        if (StringUtils.isEmpty(fromTime) && StringUtils.isEmpty(endTime)) {
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String valTime = sdf.format(valDate);
        if (StringUtils.isEmpty(fromTime)) {
            return Integer.parseInt(valTime.replace(":", "")) <= Integer
                    .parseInt(endTime.replace(":", ""));
        }
        if (StringUtils.isEmpty(endTime)) {
            return Integer.parseInt(valTime.replace(":", "")) >= Integer
                    .parseInt(fromTime.replace(":", ""));
        }
        int from = Integer.parseInt(fromTime.replace(":", ""));
        int end = Integer.parseInt(endTime.replace(":", ""));
        int time = Integer.parseInt(valTime.replace(":", ""));
        if (from <= end) {
            return time <= end && time >= from;
        } else {
            return time <= end || time >= from;
        }
    }

    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

//    public static Date getFirstDayOfWeek(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(addDay(-1, date));
//        calendar.set(Calendar.DAY_OF_WEEK, 2);
//        return DateUtils.truncate(calendar.getTime(), Calendar.DATE);
//    }
//
//    /**
//     * 日期最早只能到 ${diffDay} 前，最早时间时分秒回传 00:00:00
//     */
//    public static Date earliestDiffDay(Date date, int diffDay) {
//        Assert.state(diffDay >= 0);
//        Date now = new Date();
//        Date earliestDate = addDay(-diffDay, org.apache.commons.lang3.time.DateUtils.truncate(now, Calendar.DATE));
//
//        if (date == null || date.before(earliestDate)) {
//            return earliestDate;
//        } else {
//            return date;
//        }
//    }

    public static Integer getDifferenceDays(Date d1, Date d2) {
        Assert.notNull(d1);
        Assert.notNull(d2);
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

//    public static Date getMondayStartDate(Date now) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(now);
//        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//
//        Date newDate = org.apache.commons.lang3.time.DateUtils.truncate(c, Calendar.DATE).getTime();
//
//        if (newDate.after(now)) {
//            newDate = org.apache.commons.lang3.time.DateUtils.addDays(newDate, -7);
//        }
//
//        return newDate;
//    }

    /**
     * 两个日期相差的天数
     */
    public static int diffDay(Date fromDate, Date endDate) {
        long to = endDate.getTime();
        long from = fromDate.getTime();
        return (int) (to - from) / (1000 * 60 * 60 * 24);
    }

    /**
     * 两个日期相差的小时
     */
    public static int diffHours(Date fromDate, Date endDate) {
        long to = endDate.getTime();
        long from = fromDate.getTime();
        return (int) ((to - from) / (1000 * 60 * 60));
    }

    /**
     * 两个日期相差的分钟数
     */
    public static int diffMins(Date fromDate, Date endDate) {
        long to = endDate.getTime();
        long from = fromDate.getTime();
        return (int) (to - from) / (1000 * 60);
    }

    /**
     * 两个日期相差的秒数
     */
    public static int diffSecond(Date fromDate, Date endDate) {
        long to = endDate.getTime();
        long from = fromDate.getTime();
        return (int) (to - from) / (1000);
    }

//    /**
//     * 获取指定时间段内所有的周
//     *
//     * @param startTime
//     * @param endTime
//     * @return
//     */
//    public static List<Date> getWeeks(Date startTime, Date endTime) {
//        LinkedList list = new LinkedList();
//        Date week = getFirstDayOfWeek(startTime);
//        while (week.before(endTime)) {
//            list.add(week);
//            week = addDay(7, week);
//        }
//        return list;
//    }
//
//    public static List<Date> getDays(Date startTime, Date endTime) {
//        LinkedList list = new LinkedList();
//        Date date = org.apache.commons.lang3.time.DateUtils.truncate(startTime, Calendar.DATE);
//        while (date.before(endTime)) {
//            list.add(date);
//            date = addDay(1, date);
//        }
//        return list;
//    }

    /**
     * 获取当前日期上个月的第一天
     *
     * @return yyyy-MM-dd HH:mm:ss 2019-01-01 00:00:00
     */
    public static Date getFirstDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前月的第一天
     *
     * @return yyyy-MM-dd HH:mm:ss 2019-01-01 00:00:00
     */
    public static Date getFirstDayOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取下个月的第一天
     *
     * @return yyyy-MM-dd HH:mm:ss 2019-01-01 00:00:00
     */
    public static Date getFirstDayOfNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前日期上个月的最后一天
     *
     * @return yyyy-MM-dd HH:mm:ss 2019-01-31 23:59:59
     */
    public static Date getLastDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        Date date = calendar.getTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定日期上个月的最后一天
     * @param day
     * @return
     */
    public static Date getLastDayOfLastMonth(Date day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(day);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(
                " 23:59:59");
        return stringToDate(endStr.toString(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前日期这个月的最后一天
     *
     * @return yyyy-MM-dd HH:mm:ss 2019-01-31 23:59:59
     */
    public static Date getLastDayOfThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date date = calendar.getTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得指定日期所在的自然周的第一天，即周日
     *
     * @param date 日期
     * @return 自然周的第一天
     */
    public static Date getStartDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, 1);
        date = c.getTime();
        return date;
    }

    /**
     * 获得指定日期所在的自然周的最后一天，即周六
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, 7);
        date = c.getTime();
        return date;
    }

    /**
     * 获取指定日期的周一
     *
     * @param date
     * @return
     */
    public static Date getThisWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获得指定日期所在自然周的周一
     * 最小时间值 2018-12-31 00:00:00
     *
     * @param date 日期
     * @return 自然周的第一天
     */
    public static Date getCurrentMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getThisWeekMonday(date));
        Date calendarTime = calendar.getTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(calendarTime.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得指定日期所在自然周的周天
     * 最大时间值 2019-01-01 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getCurrentSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getThisWeekMonday(date));
        calendar.add(Calendar.DATE, 6);
        Date calendarTime = calendar.getTime();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(calendarTime.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天最小时间
     * 格式：2017-10-15 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天最大时间
     * 格式：2017-10-15 23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long now = sdf.parse(curTime).getTime();
            long start = sdf.parse(args[0]).getTime();
            long end = sdf.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                if (now >= end && now < start) {
                    return false;
                } else {
                    return true;
                }
            } else {
                if (now >= start && now < end) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    /**
     * 判断当前时间是否在一个区间内
     *
     * @param sourceTime 时间区间,eg:"09:00-23:00"
     * @return boolean
     * @author Chaims
     * @date 2019/1/22 12:05
     */
    public static boolean isInTime(String sourceTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String strDate = sdf.format(date);
        // 截取当前时间时分
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        String curTime = strDateH + ":" + strDateM;
        return isInTime(sourceTime, curTime);
    }

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        beginTime = addSec(-1,beginTime);
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前时间距离凌晨0点的秒数
     *
     * @return java.lang.Integer
     * @author Chaims
     * @date 2019/4/25 17:55
     */
    public static Integer getRemainSecondsOneDay() {
        Date currentDate = new Date();
        Calendar midnight = Calendar.getInstance();
        midnight.setTime(currentDate);
        midnight.add(Calendar.DAY_OF_MONTH, 1);
        midnight.set(Calendar.HOUR_OF_DAY, 0);
        midnight.set(Calendar.MINUTE, 0);
        midnight.set(Calendar.SECOND, 0);
        midnight.set(Calendar.MILLISECOND, 0);
        Integer seconds = (int) ((midnight.getTime().getTime() - currentDate.getTime()) / 1000);
        if (seconds.intValue() == 0) {
            seconds = 1;
        }

        return seconds;
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static Date monthFirstDay() {
        Date now = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(now.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 处理如 2016-11-19 18:15:12 +08:00 的问题,不需要后面的时区
     * date 有时区的日期
     * pattern 想转换的格式
     */
    public static <T extends String> Date handleDateZone(final T date, final T pattern) {
        int space = date.lastIndexOf(" ");
        LocalDateTime dt = LocalDateTime.parse(date.substring(0, space), DateTimeFormatter.ofPattern(pattern));
        return Date.from(dt.atZone(ZoneId.of(ZoneId.SHORT_IDS.get("CTT"))).toInstant());
    }
//
//    /**
//     *  ISO时间戳转Date
//     *  <br> eg:2019-05-07T11:33:25.000Z 时间戳
//     * @param dateTime
//     * @return
//     */
//    public static Date isoDateTimeToDate(String dateTime){
//        org.joda.time.format.DateTimeFormatter formatter = ISODateTimeFormat.dateTime();
//        DateTime dt = formatter.parseDateTime(dateTime);
//        return dt.toDate();
//    }

    /**
     * 年月日 转换美东时间 +12小时 后的 年月日 00:00:00
     * date 有时区的日期
     * pattern 想转换的格式
     */
    public static  Date getStartOfUTC(Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(getStartOfDay(date));
        rightNow.add(Calendar.HOUR, +12);
        return rightNow.getTime();
    }

    /**
     * 年月日 转换美东时间 +12小时 后的 年月日 23:59:59
     * date 有时区的日期
     */
    public static Date getEndOfUTC(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(getEndOfDay(date));
        rightNow.add(Calendar.HOUR, +12);
        return rightNow.getTime();
    }

    public static boolean isBetweenAnd(Date nowTime, Date beginTime, Date endTime){
        beginTime = addSec(-1, beginTime);
        Integer beginSec = getSec(beginTime);
        Integer nowSec = getSec(nowTime);
        Integer endSec = getSec(endTime);
        if(beginSec - endSec >0){//跨天
            endSec = endSec + 24*3600;
        }
        if(nowSec > beginSec && nowSec < endSec){
            return true;
        }
        return false;
    }

    public static Integer getSec(Date nowTime ){
        try {
            String nowTimeString  = format(nowTime, "HH:mm:ss");
            String[] array1 = nowTimeString.split(":");
            int total = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60 + Integer.valueOf(array1[2]);
            return total;
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        return 0;
    }

    /**
     *  获取上个星期周一
     * @return
     */
    public static Date getMondayOfLastWeek(){
        Date date = new Date();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        cal1.add(Calendar.DATE, -1 * 7);
        cal1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        date.setTime(cal1.getTime().getTime());
        return date;
    }

    /**
     *  获取上个星期周日
     *
     * @return
     */
    public static Date getSundayOfLastWeek() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -1 * 7);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return new Date(c.getTime().getTime());
    }

    /**
     * StringToDate
     * @param date
     * @return
     */
    public static Date stringToDate(String date,String format) {//yyyy-MM-dd HH:mm:ss
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取月第一天
     */
    public static Date getFirstDayMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date theDate = calendar.getTime();

        // 月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(
                " 00:00:00");
        return stringToDate(str.toString(),YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 最小时间
     * @param date
     * @return yyyy-MM-dd HH:mm:ss 2019-01-01 00:00:00
     */
    public static Date getMinTime(Date date) {
        return stringToDate(formatForTime(date), "yyyy-MM-dd");
    }

    /**
     * 最大时间
     * @param date
     * @return yyyy-MM-dd HH:mm:ss 2019-01-01 23:59:59
     */
    public static Date getMaxTime(Date date) {
        date = getMinTime(date);
        String dateStr = formatForTime(date);
        dateStr = dateStr.replaceAll("00:00:00", "23:59:59");
        return stringToDate(dateStr, YYYY_MM_DD_HH_MM_SS);
    }

    public static Date addHours(int f, Date dateTime) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.HOUR, f);
        return calendar.getTime();
    }

    /**
     * 获取月最后一天
     */
    public static Date getLastDayMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 月最后一天
        calendar.add(Calendar.MONTH, 1); // 加一个月
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last).append(
                " 23:59:59");
//        day_last = endStr.toString();
        return stringToDate(endStr.toString(),YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取某月的上个某一天
     * @param date
     * @param day
     * @return
     */
    public static Date getDateMinTimeLastMounth(Date date, String day) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, -1);
            String ym = format(cal.getTime(), YYYYMM);
            StringBuilder sb = new StringBuilder();
            sb.append(ym).append(day).append("000000");
            date = toDate(sb.toString(), YYYYMMDDHHMMSS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

}
