package com.wjl.redisclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisclientApplicationTests {

    @Test
    public void contextLoads() {
    }



    //截取jedis发送的字节格式
    @Test
    public  void buildServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1111);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            inputStream.read(bytes);
            System.out.println(new String(bytes));
        }
    }

    @Test
    public void get(){
        Jedis jedis = new Jedis("localhost", 1111);
        jedis.get("aaa");
        //测试结果为
//        *2
//        $3
//        GET
//        $3
//        aaa
    }

}
