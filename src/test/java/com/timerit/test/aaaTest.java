package com.timerit.test;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by trpgm on 2015-11-28.
 */
public class aaaTest {
    @Test
    public void test1() {
        UUID uuid2 = UUID.randomUUID();
        System.out.println(uuid2.toString());
        UUID uuid = UUID.fromString(uuid2.toString());
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        System.out.println(Base64.getEncoder().encodeToString(bb.array()));

        System.out.println(RandomStringUtils.randomAlphanumeric(16).toUpperCase());

    }

}
