package com.animallogic.proj.markov.text.util;

public class PrefixUtil {
    public static String getKey(int keySize, String fileName) {
        return fileName + "-" + keySize;
    }

    public static String removeLine(String str) {
        return str.replace("\\r", " ")
                .replace("\\n", " ").
                replace("\r", " ")
                .replace("\n", " ")
                .replace("\\", " ");
    }
}
