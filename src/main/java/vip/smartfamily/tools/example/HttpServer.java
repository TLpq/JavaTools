package vip.smartfamily.tools.example;

import vip.smartfamily.tools.http.DataHandle;
import vip.smartfamily.tools.http.HttpDataUtil;
import vip.smartfamily.tools.http.ReData;
import vip.smartfamily.tools.http.entity.HttpData;
import vip.smartfamily.tools.http.exception.HttpDataException;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public HttpServer() throws Exception {
        serverSocket = new ServerSocket(10222, 12);
    }

    @Override
    public void run() {
        HttpDataUtil.SERVER_NAME = "java server";
        HttpDataUtil.SERVER_VERSION = "1.0";
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(10000);
                executorService.execute(new DataHandle(socket));
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
