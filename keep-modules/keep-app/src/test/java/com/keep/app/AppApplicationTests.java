package com.keep.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AppApplicationTests {


    @Test
    void test() {
        int sizeInMb = 10;
        int byteArraySize = sizeInMb * 1024 * 1024; // 转换成字节数
        byte[] byteArray1 = new byte[byteArraySize];
        byte[] byteArray2 = new byte[byteArraySize];
        List<byte[]> arrays = Arrays.asList(byteArray1, byteArray2);
        System.out.println(arrays.stream().mapToInt(bytes -> sumArray(bytes)));


    }

    private byte[] mergeArrays(List<byte[]> arrays) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            for (byte[] array : arrays) {
                outputStream.write(array);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    private static int sumArray(byte[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
}
