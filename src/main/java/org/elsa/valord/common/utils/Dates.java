package org.elsa.valord.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author valor
 * @date 2018/9/10 21:07
 */
public class Dates {


    public static Date addDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
}
