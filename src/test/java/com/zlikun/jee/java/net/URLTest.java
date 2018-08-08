package com.zlikun.jee.java.net;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/8 19:55
 */
public class URLTest {

    @Test
    public void constructs() throws MalformedURLException {

        // 构建一个URL对象
        URL url = new URL("http://zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=100000000000");
        assertEquals("http://zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=100000000000", url.toString());
        // 其它构造方法
        // 按URL结构分段构造URL对象
        url = new URL("http", "www.zlikun.com", 80, "/api/v3/user");
        assertEquals("http://www.zlikun.com:80/api/v3/user", url.toString());
        // 以一个URL对象为上下文
        url = new URL(url, "/api/v2/user");
        assertEquals("http://www.zlikun.com:80/api/v2/user", url.toString());

    }

    @Test
    public void methods() throws IOException, URISyntaxException {
        URL url = new URL("http://zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=100000000000");

        // 获取信息类方法
        assertEquals(-1, url.getPort());
        assertEquals(80, url.getDefaultPort());
        assertEquals("zlikun:123456@www.zlikun.com", url.getAuthority());
        assertEquals("/api/v3/user?id=1024&t=100000000000", url.getFile());
        assertEquals("www.zlikun.com", url.getHost());
        assertEquals("/api/v3/user", url.getPath());
        assertEquals("http", url.getProtocol());
        assertEquals("id=1024&t=100000000000", url.getQuery());
        assertEquals(null, url.getRef());
        assertEquals("zlikun:123456", url.getUserInfo());

        assertEquals("http://zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=100000000000", url.toExternalForm());
        assertEquals(URI.create(url.toString()), url.toURI());
    }

    @Test
    public void stream() throws IOException {
        URL url = new URL("http://httpbin.org/ip");
        // sun.net.www.protocol.http.HttpURLConnection$HttpInputStream@1500955a
        System.out.println(url.getContent());
        // 读取打开的输入流
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    @Test
    public void connection() throws IOException {

        URL url = new URL("http://httpbin.org/ip");

        // 参考URLConnection测试
        URLConnection connection = url.openConnection();
        // 1533730292000
        System.out.println(connection.getDate());

    }

    /**
     * 判断一个URL是否合法
     */
    @Test
    public void syntax() {

        // URL默认支持：file、ftp、gopher、http、https、jar、mailto、netdoc等协议
        // 如果要处理一个不支持的协议，如：git，可以使用如下API实现（比如HDFS协议，在Hadoop中有介绍）
        // 下面是一个全局方法，所以可以放在静态代码块中执行，并且只能执行一次
        // URL.setURLStreamHandlerFactory(URLStreamHandlerFactory fac);

        // ok
        syntax("https://zlikun.com");
        // java.net.MalformedURLException: unknown protocol: git
        syntax("git://zlikun.com");
        // java.net.MalformedURLException: no protocol: admin@zlikun.com
        syntax("admin@zlikun.com");
        // ok
        syntax("mailto:admin@zlikun.com");
        // ok
        syntax("https://xyz");
    }

    private void syntax(String urlString) {
        try {
            URL url = new URL(urlString);
            // 违反下面任意一个断言即为非法
            assertNotNull(url);
            assertNotNull(url.getProtocol());
            assertNotNull(url.getHost());
            assertNotNull(url.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // 非法URL
        }
    }

}
