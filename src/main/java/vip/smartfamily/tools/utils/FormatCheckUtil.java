package vip.smartfamily.tools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式校验
 */
public class FormatCheckUtil {

    /**
     * IP 格式检查
     *
     * @param srt ip
     * @return 格式是否合法
     */
    public static boolean isIP(String srt) {
        synchronized (FormatCheckUtil.class) {
            if (srt != null) {
                if (srt.length() > 6 && srt.length() < 16) {
                    Pattern pattern = Pattern.compile("\\S{1,3}.\\S{1,3}.\\S{1,3}.\\S{1,3}");
                    Matcher matcher = pattern.matcher(srt);
                    return matcher.find();
                }
            }
            return false;
        }
    }
}
