package vip.smartfamily.tools.http.entity;

import java.util.HashMap;

public class HttpData {
    private String requestType;
    private int contentLen;
    private HashMap<String, String> handsMap;
    private byte[] data;

    public HttpData() {
    }

    /**
     * HTTP 数据
     *
     * @param requestType 请求类型
     * @param contentLen  数据长度
     * @param data        数据
     * @param handsMap    头键值对
     */
    public HttpData(String requestType, int contentLen, byte[] data,
                    HashMap<String, String> handsMap) {
        this.requestType = requestType;
        this.contentLen = contentLen;
        this.data = data;
        this.handsMap = handsMap;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getContentLen() {
        return contentLen;
    }

    public void setContentLen(int contentLen) {
        this.contentLen = contentLen;
    }

    /**
     * 获取数据类型
     *
     * @return 数据类型
     */
    public String getContentType() {
        if (handsMap != null) {
            return handsMap.get("Content-Type");
        }
        return null;
    }

    /**
     * 获取用户代理
     *
     * @return 用户代理
     */
    public String getUserAgent() {
        if (handsMap != null) {
            return handsMap.get("User-Agent");
        }
        return null;
    }

    /**
     * 获取连接主机
     *
     * @return 主机地址
     */
    public String getHost() {
        if (handsMap != null) {
            return handsMap.get("Host");
        }
        return null;
    }

    /**
     * 获取自定义HTTP头值
     *
     * @param HangKey key
     * @return value
     */
    public String getHeadValue(String HangKey) {
        if (handsMap != null) {
            return handsMap.get(HangKey);
        }
        return null;
    }

    /**
     * HTTP 包体
     *
     * @return 包体
     */
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setHandsMap(HashMap<String, String> handsMap) {
        this.handsMap = handsMap;
    }
}
