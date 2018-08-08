package com.zlikun.jee;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于Socket实现一个简易Socket服务器，用户输出什么就响应什么
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/8 18:52
 */
public class EchoServer {

    /**
     * 可以使用Telnet测试，也可以用该类的测试方法测试
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        byte[] buf = new byte[128];

        // 构造一个ServerSocket，监听：0.0.0.0:1234
        try (ServerSocket server = new ServerSocket(1234)) {
            System.out.println("Server is running .");
            while (true) {
                // 阻塞等待请求
                try (Socket socket = server.accept()) {
                    // 接收到请求后将数据计入缓冲区
                    InputStream input = socket.getInputStream();
                    input.read(buf);
                    // 再将缓冲区中的数据作为响应写回
                    OutputStream output = socket.getOutputStream();
                    output.write(buf);
                }
            }
        }

    }

}
