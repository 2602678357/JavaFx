package com.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;

public class SaltUtil {

    public static String getSalt() {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        return salt;
    }
}
