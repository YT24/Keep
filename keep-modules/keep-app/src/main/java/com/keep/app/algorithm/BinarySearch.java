package com.keep.app.algorithm;


public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 6, 7, 9, 10, 15, 17, 20}; // 有序数组
        int target = 7; // 待查找的目标元素
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                System.out.println("找到目标元素" + target + "，下标为" + mid);
                return;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println("未找到目标元素" + target);
    }
}