package com.github.fivekwbassmachine.minecraft.discordserverstatus.util;

public class Utils {

    public static String buildString(String[] str) {
        return buildString(str, 0);
    }
    public static String buildString(String[] str, int i) {
        StringBuilder sb = new StringBuilder();
        while (i < str.length) {
            sb.append(str[i]);
            i++;
        }
        return sb.toString();
    }
}
