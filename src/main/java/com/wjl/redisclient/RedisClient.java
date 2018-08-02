package com.wjl.redisclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RedisClient {

    private String host;
    private int port;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public RedisClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
//        this.socket = new Socket(host, port);
//        this.inputStream = this.socket.getInputStream();
//        this.outputStream = this.socket.getOutputStream();
    }

    public void connect() throws IOException {
        if (isConnect())
            return;
        socket = new Socket(host, port);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }

    public boolean isConnect() {
        return this.socket != null && this.socket.isBound() && !this.socket.isClosed() && this.socket.isConnected() && !this.socket.isInputShutdown() && !this.socket.isOutputShutdown();
    }

    public String set(String key, String value) throws IOException {
        connect();
        StringBuilder sb = new StringBuilder();
        sb.append("*3").append("\r\n");
        sb.append("$3").append("\r\n");
        sb.append("set").append("\r\n");
        sb.append("$").append(key.length()).append("\r\n");
        sb.append(key).append("\r\n");
        sb.append("$").append(value.length()).append("\r\n");
        sb.append(value).append("\r\n");
        this.outputStream.write(sb.toString().getBytes());
        byte[] bytes = new byte[1024];
        this.inputStream.read(bytes);
        return new String(bytes);
    }


    public String get(String key) throws IOException {
        connect();
        StringBuilder sb = new StringBuilder();
        sb.append("*2").append("\r\n");
        sb.append("$3").append("\r\n");
        sb.append("get").append("\r\n");
        sb.append("$").append(key.length()).append("\r\n");
        sb.append(key).append("\r\n");
        this.outputStream.write(sb.toString().getBytes());
        byte[] bytes = new byte[1024];
        this.inputStream.read(bytes);

        return new String(bytes);
    }


    public void close() throws IOException {
        this.inputStream.close();
        this.outputStream.close();
        this.socket.close();
    }

    public static void main(String[] args) throws IOException {
        RedisClient redisClient = new RedisClient("localhost", 6379);
        String setResult = redisClient.set("ttt", "hlllaha");
        System.out.println("set result:" + setResult);
        redisClient.close();
        String getResult = redisClient.get("ttt");
        System.out.println("get result:" + getResult);
    }

}
