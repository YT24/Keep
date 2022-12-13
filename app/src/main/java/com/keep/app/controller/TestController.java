package com.keep.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/v1")
public class TestController {


    @Value("${k1}")
    private String k1;

    @GetMapping("get")
    public String get() {

        Lock lock = new ReentrantLock();
        lock.lock();
        try {

        }finally {
            lock.unlock();
        }

        return k1;

    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(10);
        for (int i = 0; i < 11; i++) {
            list.add(i);
            System.out.println("添加了："+i);
        }
        list = Collections.synchronizedList(list);

        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add(1);
        copyOnWriteArrayList.add(2);
        for (Integer integer : copyOnWriteArrayList) {
            if (1 == integer){
                copyOnWriteArrayList.remove(integer);
            }
        }

//        Map phone = new HashMap();
//        phone.put("Apple", 7299);
//        phone.put("SAMSUNG", 6000);
//        phone.put("Meizu", 2698);
//        phone.put("Xiaomi", 2400);
//        //key-sort
//        Set set = phone.keySet();
//        Object[] arr = set.toArray();
//        Arrays.sort(arr);
//        for (Object key : arr) {
//            System.out.println(key + ": " + phone.get(key));
//        }
//        System.out.println();
//        //value-sort
//        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(phone.entrySet());
//        //list.sort()
//        list.sort(new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//        //collections.sort()
//        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//                return o2.getValue().compareTo(o1.getValue());
//            }
//        });
//        //for-each
//        for (Map.Entry<String, Integer> mapping : list) {
//            System.out.println(mapping.getKey() + ": " + mapping.getValue());
//        }
    }
}
