package vip.smartfamily.tools.utils;

/**
 * JAVA 程序环境变量
 */
public class EnvironmentVariableUtil {
    /**
     * @return 程序所在目录
     */
    public static String appLocalPath() {
        return System.getProperty("user.dir");
    }

    /**
     * @return 操作系统名称
     */
    public static String osName() {
        return System.getProperty("os.name");
    }

    /**
     * @return 操作系统架构
     */
    public static String osArch() {
        return System.getProperty("os.arch");
    }

    /**
     * @return 操作系统版本
     */
    public static String osVersion() {
        return System.getProperty("os.version");
    }

    /**
     * @return 用户名称
     */
    public static String userName() {
        return System.getProperty("user.name");
    }

    /**
     * @return 用户主目录录
     */
    public static String userHome() {
        return System.getProperty("user.home");
    }
}
