package vip.smartfamily.tools.http;

import vip.smartfamily.tools.http.entity.HttpData;
import vip.smartfamily.tools.http.exception.HttpDataException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class HttpDataUtil {
    /**
     * 服务名称
     */
    public static String SERVER_NAME;
    /**
     * 服务版本
     */
    public static String SERVER_VERSION;

    /**
     * 客户端连接
     */
    private Socket socket = null;
    /**
     * 客户端输出
     */
    private OutputStream output = null;
    /**
     * 客户端输入
     */
    private InputStream input = null;
    /**
     * 接收缓存
     */
    private byte[] dataBuffer = new byte[1024 * 1024];

    /**
     * http服务工具
     *
     * @param serverName 服务名称
     * @param version    服务版本
     */
    public HttpDataUtil(String serverName, String version) {
        SERVER_NAME = serverName;
        SERVER_VERSION = version;
    }

    /**
     * 客户端请求接收
     *
     * @param socket 客户端连接
     * @throws HttpDataException http 数据错误
     */
    public HttpData setHttpData(Socket socket) throws HttpDataException {
        try {
            if (socket != null) {
                this.socket = socket;
                output = socket.getOutputStream();
                input = socket.getInputStream();

                if (input != null) {
                    HttpData httpData = new HttpData();

                    int readLength;
                    int allLength = 0;

                    int headLength = 0;
                    int bodyLength = 0;

                    byte[] data = null;
                    int currentLen = 0;

                    StringBuilder stringBuilder = new StringBuilder();

                    readLength = input.read(dataBuffer);

                    while (readLength > 0) {
                        allLength += readLength;

                        if (bodyLength == 0 || headLength == 0) {
                            String text = new String(dataBuffer, 0, readLength);
                            stringBuilder.append(text);
                            if (bodyLength == 0) {
                                if (text.contains("Content-Length:")) {
                                    int len = text.indexOf("Content-Length:") + "Content-Length:".length();
                                    String lenSrt = text.substring(len, text.indexOf("\r\n", len));
                                    lenSrt = lenSrt.trim();
                                    if (!lenSrt.isEmpty()) {
                                        bodyLength = Integer.parseInt(lenSrt);
                                        httpData.setContentLen(bodyLength);
                                        data = new byte[bodyLength];
                                    }
                                }
                            }

                            if (headLength == 0) {
                                if (text.contains("\r\n\r\n")) {
                                    headLength = text.indexOf("\r\n\r\n") + 4;
                                    if (data != null) {
                                        System.arraycopy(dataBuffer, headLength, data, 0, readLength - headLength);
                                        currentLen = readLength - headLength;
                                    }

                                    try {
                                        String head = stringBuilder.toString().split("\r\n\r\n")[0];

                                        String requestType = head.substring(0, head.indexOf(" "));
                                        httpData.setRequestType(requestType);

                                        head = head.substring(head.indexOf("\r\n") + 2);
                                        head = head.replace("\r\n", "&");
                                        String[] hands = head.split("&");

                                        if (hands.length > 0) {
                                            HashMap<String, String> handsMap = new HashMap<>();
                                            for (String hand : hands) {
                                                String[] handInfos = hand.split(":");
                                                handsMap.put(handInfos[0], handInfos[1]);
                                            }
                                            httpData.setHandsMap(handsMap);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } else {
                            System.arraycopy(dataBuffer, 0, data, currentLen, readLength);
                            currentLen += readLength;
                        }

                        if (allLength >= headLength + bodyLength) {
                            break;
                        }

                        readLength = input.read(dataBuffer);
                    }

                    if (data != null) {
                        httpData.setData(data);
                    }

                    return httpData;
                }
            }
        } catch (IOException e) {
            throw new HttpDataException(e);
        }

        throw new HttpDataException("NULL DATA");
    }

    /**
     * 发送响应数据
     */
    public void send(ReData reData) {
        if (output != null) {
            byte[] heads = reData.getHeads().getBytes();
            byte[] data;
            if (reData.getBody() == null) {
                data = heads.clone();
            } else {
                data = new byte[heads.length + reData.getBody().length];
                System.arraycopy(heads, 0, data, 0, heads.length);
                System.arraycopy(reData.getBody(), 0, data, heads.length, reData.getBody().length);
            }

            try {
                output.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     */
    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (input != null) {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
