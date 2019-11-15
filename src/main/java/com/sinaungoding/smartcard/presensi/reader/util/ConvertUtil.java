package com.sinaungoding.smartcard.presensi.reader.util;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;

public class ConvertUtil {
    public static String dateToHex(Date date) {
        return Long.toHexString(date.getTime()).toUpperCase();
    }

    public static Long hexToLong(String hex) {
        return Long.valueOf(hex);
    }

    public static String byteArrayToHex(byte data[], boolean space) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02x", b));
            if (space) {
                sb.append(" ");
            }
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String zeroPadding(String text, int size) {
        StringBuilder builder = new StringBuilder(text);
        String temp = "";
        int length = builder.length();
        while (length < size) {
            temp += "0";
            length++;
        }
        temp += builder.toString();
        return temp;
    }

    public static byte[] trim(byte[] bytes) {
        int i = bytes.length - 1;
        while (i >= 0 && bytes[i] == 0) {
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(dateToHex(new Date()));
        String hex = byteArrayToHex("NOPRIANTO".getBytes(), false);
        System.out.println(hex);

        byte[] names = "NOPRIANTO".getBytes();
        for (byte b : names) {
            System.out.print(b);
        }
        System.out.println();

        byte[] bytes = hexStringToByteArray(hex);
        for (byte b : bytes) {
            System.out.print(b);
        }
        System.out.println();

        System.out.println(new String(bytes));
        System.out.println(zeroPadding("NOPRIANTO", 10));

    }
}
