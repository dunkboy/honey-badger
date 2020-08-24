package com.scissor.hand;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


/**
 * +---------------------------+
 * |I am the most handsome coding peasant.|
 * +---------------------------+
 * <p>Title: DateUtils</p>
 * <p>Description: DateUtils</p>
 * <p>Copyright:Copyright(c) coder 2018/p>
 * <p>Company: poor</p>
 * <p>CreateTime: 2019/3/5 11:24</p>
 * @author cb
 * @version 1.0
 **/
public abstract class DateUtils
{

    /**
     * 时间格式
     */
    public static final String TIME_FORMAT = "yyyy-MM-dd HH.mm.ss";
    public static final String TIME_FORMAT_2 = "yyyyMMddHHmmss";
    public static final String TIME_FORMAT_3 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_4 = "yyyy-MM-dd";
    public static final String TIME_FORMAT_5 = "yyyyMMdd";
    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_A = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * <p>Description: 格式化当前时间</p>
     * <p>CreateTime:2019/3/5 11:24</p>
     * <p>@author cb</p>
     *
     * @param  pattern pattern
     * @return String
     */
    public static String formatNow(String pattern)
    {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * <p>Description: 当前时间秒数</p>
     * <p>CreateTime:2019/3/5 11:24</p>
     * <p>@author cb</p>
     *
     * @return Long
     */
    public static Long nowSecond()
    {
        return LocalDateTime.now().toEpochSecond(ZONE_OFFSET);
    }

    /**
     * <p>Description: 当前时间豪秒数</p>
     * <p>CreateTime:2019/3/5 11:24</p>
     * <p>@author cb</p>
     *
     * @return Long
     */
    public static Long nowMilliSecond()
    {
        return LocalDateTime.now().toInstant(ZONE_OFFSET).toEpochMilli();
    }

    /**
     * <p>Description: 将时间戳格式化</p>
     * <p>CreateTime:2019/3/5 11:24</p>
     * <p>@author cb</p>
     *
     * @param  timestamp timestamp
     * @param  pattern pattern
     * @return String
     */
    public static String formatTimestamp(Long timestamp, String pattern)
    {
        Date date = new Date(timestamp);
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * <p>Description: 获取当前时间得前几天时间或后几天时间</p>
     * <p>CreateTime:2020/1/9 16:42</p>
     * <p>@author cb</p>
     *
     * @param  amount 前几天 -x 后几天+x
     * @return 时间戳
     */
    public static Long getNowAfterOrBefore(int amount)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, amount);
        Date time = c.getTime();
        return time.getTime();
    }

}
