package com.zlikun.jee;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 测试EchoServer服务
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/8 19:00
 */
public class EchoServerTest {

    private Thread server;

    @Before
    public void init() {
        // 以线程方式运行服务
        server = new Thread(() -> {
            try {
                EchoServer.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        server.start();
    }

    @After
    public void destroy() throws InterruptedException {
        server.join();
    }

    @Test
    public void client() throws IOException, InterruptedException {

        // 使用Socket测试EchoServer服务
        for (int i = 0; i < 10; i++) {
            try (
                    Socket socket = new Socket("127.0.0.1", 1234);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream())
            ) {
                // 发送请求
                writer.write("Number: " + i);
                writer.flush();
                // 处理响应
                System.out.println(reader.readLine());
                // 休眠1秒
                Thread.sleep(1000L);
            }
        }

        // 结束测试
        System.exit(0);

    }

}