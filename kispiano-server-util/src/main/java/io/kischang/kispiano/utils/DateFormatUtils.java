package io.kischang.kispiano.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式化工具类
 *
 * @author KisChang
 * @version 1.0
 */
public class DateFormatUtils {

    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH");

    public static final SimpleDateFormat weekFormat = new SimpleDateFormat("yyyy-ww");
    public static final SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

    public static long time(){
        return System.currentTimeMillis();
    }

    public static Date parse(String dateStr, SimpleDateFormat format){
        if (!isEmpty(dateStr)){
            try {
                return format.parse(dateStr);
            } catch (ParseException ignored) {}
        }
        return null;
    }

    public static String formatDatetime(){
        return formatDatetime(new Date());
    }
    public static String formatDatetime(Date date){
        return date == null ? "" : datetimeFormat.format(date);
    }

    public static String formatDate(){
        return formatDate(new Date());
    }
    public static String formatDate(Date date){
        return date == null ? "" : dateFormat.format(date);
    }

    public static String formatTime(){
        return formatTime(new Date());
    }
    public static String formatTime(Date date){
        return date == null ? "" : timeFormat.format(date);
    }

    public static String formatMonth() {
        return formatMonth(new Date());
    }

    public static String formatMonth(Date date) {
        return date == null ? "" : monthFormat.format(date);
    }

    public static String formatQuarter() {
        return formatMonth(new Date());
    }

    public static String formatQuarter(Date date) {
        return date == null ? "" : (yearFormat.format(date) + monthToQuarter(date));
    }

    public static Date parseDatetime(String dateStr) {
        if (!isEmpty(dateStr)){
            try {
                return datetimeFormat.parse(dateStr);
            } catch (ParseException ignored) {}
        }
        return null;
    }

    public static Date parseMonth(String dateStr) {
        if (!isEmpty(dateStr)){
            try {
                return weekFormat.parse(dateStr);
            } catch (ParseException ignored) {}
        }
        return null;
    }

    public static Date parseQuarter(String dateStr) {
        if (!isEmpty(dateStr)){
            try {
                Date tmpYear = yearFormat.parse(dateStr.substring(0, 4));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tmpYear);
                calendar.set(Calendar.MONTH, quarterToMonth(dateStr.substring(5)));
                return calendar.getTime();
            } catch (ParseException ignored) {}
        }
        return null;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }

    public static Date parseDate(String dateStr, Date def) {
        Date rv = parseDate(dateStr);
        return rv == null ? def : rv;
    }
    public static Date parseDate(String dateStr) {
        if (!isEmpty(dateStr)){
            try {
                return dateFormat.parse(dateStr);
            } catch (ParseException ignored) {}
        }
        return null;
    }

    public static Date parseTime(String dateStr) {
        if (!isEmpty(dateStr)){
            try {
                return timeFormat.parse(dateStr);
            } catch (ParseException ignored) {}
        }
        return null;
    }

    private static String monthToQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        if (month >= 0 && month < 3){
            return "-1";
        }else if (month >= 3 && month <6){
            return "-2";
        }else if (month >= 6 && month <9){
            return "-3";
        }else{
            return "-4";
        }
    }

    private static final int default_quarter = 0;
    private static int quarterToMonth(String str) {
        if (isEmpty(str)){
            return default_quarter;
        }
        switch (str.charAt(0)){
            case '1':
                return 0;
            case '2':
                return 3;
            case '3':
                return 6;
            case '4':
                return 9;
            default:
                return default_quarter;
        }
    }

    public static int getShort(Date s, Date e, int filed) {
        Calendar start = Calendar.getInstance();
        start.setTime(s);

        Calendar end = Calendar.getInstance();
        end.setTime(e);

        return end.get(filed) - start.get(filed) + 1;
    }

}
