package com.zlikun.jee.java.net;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/8 20:12
 */
public class URITest {

    @Test
    public void test() throws MalformedURLException {

        URI uri = URI.create("https://zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=10000000000");
        assertNotNull(uri);

        assertEquals("zlikun:123456@www.zlikun.com", uri.getAuthority());
        assertEquals(-1, uri.getPort());
        assertEquals("www.zlikun.com", uri.getHost());
        assertEquals("/api/v3/user", uri.getPath());
        assertEquals("id=1024&t=10000000000", uri.getQuery());
        assertEquals("zlikun:123456", uri.getUserInfo());
        assertEquals(null, uri.getFragment());
        assertEquals("https", uri.getScheme());
        assertEquals("//zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=10000000000", uri.getSchemeSpecificPart());

        assertEquals("zlikun:123456@www.zlikun.com", uri.getRawAuthority());
        assertEquals("/api/v3/user", uri.getRawPath());
        assertEquals("id=1024&t=10000000000", uri.getRawQuery());
        assertEquals(null, uri.getRawFragment());
        assertEquals("//zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=10000000000", uri.getRawSchemeSpecificPart());
        assertEquals("zlikun:123456", uri.getRawUserInfo());

        assertEquals("https://zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=10000000000", uri.toString());
        assertEquals("https://zlikun:123456@www.zlikun.com/api/v3/user?id=1024&t=10000000000", uri.toASCIIString());
        assertEquals(uri, URI.create(uri.toString()));
        assertTrue(uri.isAbsolute());
        assertEquals(new URL(uri.toString()), uri.toURL());
        assertEquals(URI.create("https://zlikun:123456@www.zlikun.com/api/v2/user"), uri.resolve("/api/v2/user"));

    }

}
