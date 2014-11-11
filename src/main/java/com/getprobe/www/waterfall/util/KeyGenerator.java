package com.getprobe.www.waterfall.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyGenerator {

    public static String generateString(int numBits, int radix) {
        return new BigInteger(numBits, new SecureRandom()).toString(radix);
    }

}
