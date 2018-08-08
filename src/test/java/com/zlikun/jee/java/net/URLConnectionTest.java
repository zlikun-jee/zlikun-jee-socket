package com.zlikun.jee.java.net;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/8 20:12
 */
public class URLConnectionTest {

    @Test
    public void test() throws IOException {

        URLConnection connection = URI.create("https://zlikun.com").toURL().openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

    }

}
