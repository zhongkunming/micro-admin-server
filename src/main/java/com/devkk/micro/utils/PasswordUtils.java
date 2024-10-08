package com.devkk.micro.utils;

import org.dromara.hutool.crypto.digest.BCrypt;

/**
 * @author zhongkunming
 */
public class PasswordUtils {

    public static String encrypt(String passwd) {
        return BCrypt.hashpw(passwd);
    }

    public static boolean check(String passwd, String hash) {
        return BCrypt.checkpw(passwd, hash);
    }
}
