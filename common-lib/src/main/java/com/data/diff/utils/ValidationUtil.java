package com.data.diff.utils;

public class ValidationUtil {
    public static boolean nullOrEmpty(String str) {
        return null == str || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim());
    }

    public static boolean isNull(Object obj) {
        return (null == obj ? true : false);
    }
}
