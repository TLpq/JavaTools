package vip.smartfamily.tools.example;

import vip.smartfamily.tools.http.HttpDataUtil;
import vip.smartfamily.tools.http.ReData;
import vip.smartfamily.tools.http.entity.HttpData;
import vip.smartfamily.tools.http.exception.HttpDataException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public HttpServer() throws Exception {
        serverSocket = new ServerSocket(10222, 1);
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(10000);
                HttpDataUtil httpDataUtil = new HttpDataUtil("java server", "1.0");

                try {
                    HttpData httpData = httpDataUtil.setHttpData(socket);

                    ReData reData = new ReData.Builder()
                            .setStatus(200)
                            .setContentType("application/json;charset=UTF-8")
                            .setConnection("Keep-Alive")
                            .setAcceptEncoding("gzip, deflate")
                            .addHead("momd","ddddd")
                            .data(httpData.getData())
                            .builder();

                    httpDataUtil.send(reData);
                } catch (HttpDataException e) {
                    e.printStackTrace();
                } finally {
                    httpDataUtil.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            HttpServer httpServer = new HttpServer();
            httpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
