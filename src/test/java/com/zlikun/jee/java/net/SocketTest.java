package com.zlikun.jee.java.net;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 使用Socket请求一个网页
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/8 18:09
 */
public class SocketTest {

    @Test
    public void client() throws IOException {

        // 客户端使用该语句获得套接字
        try (Socket socket = new Socket("httpbin.org", 80)) {

            // Socket连接的远程主机及端口
            InetAddress inetAddress = socket.getInetAddress();
            // 52.22.2.149，域名对应的IP有多个，所以可能变化
            assertEquals("httpbin.org", inetAddress.getHostName());
            System.out.println(inetAddress.getHostAddress());
            assertEquals(80, socket.getPort());

            // Socket连接本地主机及端口信息
            InetAddress localAddress = socket.getLocalAddress();
            assertEquals("ZLIKUN", localAddress.getHostName());
            assertEquals("192.168.0.9", localAddress.getHostAddress());
            // 本地打开的端口，每次都会变化
            assertTrue(socket.getLocalPort() <= 65535);

            // 远程主机的Socket地址
            SocketAddress remoteSocketAddress = socket.getRemoteSocketAddress();
            // httpbin.org/34.225.24.230:80
            System.out.println(remoteSocketAddress);

            // 本地主机的Socket地址
            SocketAddress localSocketAddress = socket.getLocalSocketAddress();
            // /192.168.0.9:52810
            System.out.println(localSocketAddress);

            // 读取通过Socket请求并获取数据
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(socket.getOutputStream())
            ) {
                // 发送请求（输出），参考：Telnet进行GET请求
                writer.println("GET /ip HTTP/1.1");
                writer.println("Host: httpbin.org");
                writer.println();
                writer.flush();
                // 接收响应（输入）
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    System.out.println(line);
                }
                // 循环打印网页内容后，会阻塞，原因未知
                System.out.println("--END--");
            }

        }

    }

}
