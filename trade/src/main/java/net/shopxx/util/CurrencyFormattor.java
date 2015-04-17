package net.shopxx.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 简单货币格式工具
 * 
 * @author jiejie 2014年3月3日 下午4:26:41
 */
public class CurrencyFormattor {

    private static ThreadLocal<HashMap<String, NumberFormat>> formatHolder = new ThreadLocal<HashMap<String, NumberFormat>>();
    private static final String                               CURRENCY     = "#0.00";

    /**
     * 将元转化为分
     * 
     * @param yuan 输入金额元(类似 10.00或者10)
     * @return
     */

    public static int convert2fen(String yuan) {
        return (int) (NumberUtils.toFloat(yuan) * 100);
    }

    /**
     * 将“分”格式化为“元”的样式
     * 
     * <pre>
     * format(null) 0.00
     * format(－1) 0.00
     * format(0) 0.00
     * format(1) 1.00
     * format(1000000) 10000.00
     * </pre>
     */

    public static String format(Integer fen){
        double yuan = 0;
        if(fen != null ){
            yuan = fen /100d;
        }
        NumberFormat numberInstance = getFormat(CURRENCY);
        return numberInstance.format(yuan);
    }

    private static NumberFormat getFormat(String key) {
        HashMap<String, NumberFormat> map = formatHolder.get();
        if (map == null) {
            map = new HashMap<String, NumberFormat>();
            formatHolder.set(map);// 保存回去
        }
        NumberFormat format = map.get(key);
        if (format == null) {
            format = new DecimalFormat(key);
            map.put(key, format);
            formatHolder.set(map);// 保存回去
        }
        return format;
    }

}
