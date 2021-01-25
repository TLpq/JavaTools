package vip.smartfamily.tools.http;

import java.util.HashMap;
import java.util.Map;

public class ReData {
    private String heads;
    private byte[] body;

    /**
     * 服务响应数据
     *
     * @param heads 响应头
     * @param body  响应包体
     */
    public ReData(String heads, byte[] body) {
        this.heads = heads;
        this.body = body;
    }

    public String getHeads() {
        return heads;
    }

    public byte[] getBody() {
        return body;
    }

    /**
     * 构建返回数据
     */
    public static class Builder {
        private int status;
        private String connection = null;
        private String contentType = null;
        private String acceptEncoding = null;

        private byte[] data;

        private HashMap<String, String> handsMap = null;

        public Builder() {
            status = 200;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setConnection(String connection) {
            this.connection = connection;
            return this;
        }

        public Builder setContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setAcceptEncoding(String acceptEncoding) {
            this.acceptEncoding = acceptEncoding;
            return this;
        }

        public Builder addHead(String key, String value) {
            if (handsMap == null) {
                handsMap = new HashMap<>();
            }

            handsMap.put(key, value);
            return this;
        }

        public Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        public ReData builder() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HTTP/1.0 ").append(status).append(" OK\r\n");
            stringBuilder.append("Server: ").append(HttpDataUtil.SERVER_NAME).append(" /").append(HttpDataUtil.SERVER_VERSION).append("\r\n");
            if (connection != null) {
                stringBuilder.append("Connection: ").append(connection).append("\r\n");
            }

            if (contentType != null) {
                stringBuilder.append("Content-Type: ").append(contentType).append("\r\n");
            }

            if (acceptEncoding != null) {
                stringBuilder.append("Accept-Encoding: ").append(acceptEncoding).append("\r\n");
            }

            if (handsMap != null) {
                for (Map.Entry<String, String> head : handsMap.entrySet()) {
                    stringBuilder.append(head.getKey()).append(": ").append(head.getValue()).append("\r\n");
                }
            }

            stringBuilder.append("\r\n");

            return new ReData(stringBuilder.toString(), data);
        }
    }
}
