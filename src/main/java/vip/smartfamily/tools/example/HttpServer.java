package vip.smartfamily.tools.example;

import vip.smartfamily.tools.http.HttpDataUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private HttpDataUtil httpDataUtil = new HttpDataUtil("","");

    public HttpServer() throws Exception {
        serverSocket = new ServerSocket(10222, 1);
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
                socket.setSoTimeout(10000);
                httpDataUtil.setHttpData(socket);


                httpDataUtil.send();
                httpDataUtil.close();
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
