package vip.smartfamily.tools.http;

import vip.smartfamily.tools.http.entity.HttpData;
import vip.smartfamily.tools.http.exception.HttpDataException;

import java.net.Socket;

/**
 * Socket 数据处理多线程
 */
public class DataHandle implements Runnable {
    private Socket socket;

    public DataHandle(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        HttpDataUtil httpDataUtil = new HttpDataUtil();

        try {
            HttpData httpData = httpDataUtil.setHttpData(socket);

            ReData reData = new ReData.Builder()
                    .setStatus(200)
                    .setContentType("application/json;charset=UTF-8")
                    .setConnection("Keep-Alive")
                    .setAcceptEncoding("gzip, deflate")
                    .addHead("momd", "ddddd")
                    .data(httpData.getData())
                    .builder();

            httpDataUtil.send(reData);
        } catch (HttpDataException e) {
            e.printStackTrace();
        } finally {
            httpDataUtil.close();
        }
    }
}
