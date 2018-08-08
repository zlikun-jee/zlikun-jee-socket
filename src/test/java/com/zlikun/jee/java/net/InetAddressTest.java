package com.zlikun.jee.java.net;

import org.junit.Test;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/8 17:26
 */
public class InetAddressTest {

    private int[] toUnsignedInts(byte[] bytes) {
        int[] buf = new int[bytes.length];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = Byte.toUnsignedInt(bytes[i]);
        }
        return buf;
    }

    @Test
    public void ipv4() throws IOException {

        // 返回IPv4的回环地址
        InetAddress address = Inet4Address.getLoopbackAddress();

        // 获取主机名
        assertEquals("localhost", address.getHostName());
        // 返回标准主机名
        // assertEquals("zlikun", address.getCanonicalHostName());
        assertEquals("127.0.0.1", address.getCanonicalHostName());
        // [127, 0, 0, 1]
        System.out.println(Arrays.toString(toUnsignedInts(address.getAddress())));
        // 返回主机地址
        assertEquals("127.0.0.1", address.getHostAddress());
        // 检查一个InetAddress对象是否是网络回环地址
        assertTrue(address.isLoopbackAddress());

        // getLoopbackAddress() 不等价于 getLocalHost()
        // localhost/127.0.0.1 <==> ZLIKUN/192.168.0.9
        assertNotEquals(address, Inet4Address.getLocalHost());

        // 通过一个IPv4获取InetAddress实例
        address = Inet4Address.getByAddress(new byte[]{(byte) 192, (byte) 168, (byte) 0, (byte) 9});


        // 获取主机名
        assertEquals("ZLIKUN", address.getHostName());
        // 返回标准主机名
        assertEquals("ZLIKUN", address.getCanonicalHostName());
        // [192, 168, 0, 9]，注意转换为无符号整数，否则可能是负数（符号位的问题）
        System.out.println(Arrays.toString(toUnsignedInts(address.getAddress())));
        // 返回主机地址
        assertEquals("192.168.0.9", address.getHostAddress());
        // 检查一个InetAddress对象是否是网络回环地址
        assertFalse(address.isLoopbackAddress());

        // 其它判断方法（意义尚不明确，后续弄清）
        assertFalse(address.isAnyLocalAddress());       // 检查IP是否属于“任意”本地地址
        assertFalse(address.isLinkLocalAddress());      // 检查IP是否属于“任意”本地链接地址
        assertTrue(address.isReachable(1000));  // 检查IP是否真的能与指定主机进行数据交换（会通过网络，所以允许设置超时时间）
        assertFalse(address.isMulticastAddress());      // 检查IP是否为一个多播地址

    }

    @Test
    public void ipv6() throws UnknownHostException {

        // 通过IPv4与IPv6实际取得的是同一主机地址
        assertEquals(Inet4Address.getLoopbackAddress(), Inet6Address.getLoopbackAddress());
        assertEquals(Inet4Address.getLocalHost(), Inet6Address.getLocalHost());

        // 其它暂略


    }

    @Test
    public void hostname() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("zlikun.com");
        assertEquals("zlikun.com", address.getHostName());
        assertEquals("47.97.4.23", address.getCanonicalHostName());
        assertEquals("47.97.4.23", address.getHostAddress());
        // 如果主机不存在（未找到），抛出异常
        try {
            address = InetAddress.getByName("empty.zlikun.com");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
