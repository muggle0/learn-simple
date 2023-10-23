package com.muggle.aop;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author zhaiya
 * @since 2022/10/6.
 */
public class IDUtil {
    // 随机字符串
    private static final String _INT = "0123456789";
    private static final String _STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String _ALL = _INT + _STR;

    private static final Random RANDOM = new Random();

    public synchronized static String getTransactionId() {
/*		if (custOrderCount > 999) {
            custOrderCount = 100;
		}*/
        //int t = getDateRelation() + Integer.parseInt(getIPStamp());
        Random r = new Random();
        int num = 1000 + r.nextInt(9000);
        String custOrder = genDateStr("yyMMddHHmmssSSSS") + num;//+t+custOrderCount;
        //custOrderCount++;
        return custOrder;
    }

    /**
     * @return 时间
     * @desc 获取当前时间
     */
    public static String genDateStr(String format) {

        if (StringUtils.isEmpty(format)) {
            format = "yyyyMMddHHmmssSSSS";
        }
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    /**
     * 生成的随机数类型
     */
    public static enum RandomType {
        INT, STRING, ALL;
    }

    /**
     * 随机数生成
     *
     * @param count
     * @return {String}
     */
    public static String random(int count, RandomType randomType) {
        if (count == 0) return "";
        if (count < 0) {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }
        char[] buffer = new char[count];
        for (int i = 0; i < count; i++) {
            if (randomType.equals(RandomType.INT)) {
                buffer[i] = _INT.charAt(RANDOM.nextInt(_INT.length()));
            } else if (randomType.equals(RandomType.STRING)) {
                buffer[i] = _STR.charAt(RANDOM.nextInt(_STR.length()));
            } else {
                buffer[i] = _ALL.charAt(RANDOM.nextInt(_ALL.length()));
            }
        }
        return new String(buffer);
    }

    private final static char[] digits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
}
