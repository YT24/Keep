//package com.keep.kafka.controller;
//
////import com.keep.kafka.er.KafkaProducer;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RestController
//public class KafkaController {
//
//    public static final String topic = "TEST";
//
////    @Autowired
////    KafkaProducer kafkaProducer;
//
//    @GetMapping("/api/test")
//    public Map  senfKafkaMsg(){
//        Map<String,Object> msg = new HashMap<>();
//        for (int i = 0; i < 1000; i++) {
//            msg.put("k"+ i,"v"+ i);
//        }
//        log.info("msg data add finish");
//
//        return msg;
//    }
//
//
//    // java.lang.OutOfMemoryError: GC overhead limit exceeded
//    @GetMapping("oom/gcOverHead")
//    public void gcOverHead(){
//        List<String> strList = new ArrayList<>();
//        int count = 1000000;
//        String str1 = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
//        for(int i=0; i< count; i++) {
//            strList.add(str1 + "i" + i);
//        }
//        System.out.println("...");
//
//        for(int i=0; i< count; i++) {
//            strList.add(str1 + "i" + i);
//            strList.add(str1 + "i" + i);
//            strList.add(str1 + "i" + i);
//        }
//        System.out.println("done");
//    }
//
//
//    @GetMapping("out/of/memory")
//    public void out() throws InterruptedException {
//        List<Dd> list = new ArrayList<>();
//
//        Runtime run = Runtime.getRuntime();
//
//        int i = 1;
//
//        while (true) {
//            Dd dd = new Dd();
//            int[] arr = new int[1024 * 8];
//
//            dd.setList(arr);
//            list.add(dd);
//            if (i++ % 1000 == 0) {
//                System.out.print("i=" + i);
//                System.out.print("最大内存=" + run.maxMemory() / 1024 / 1024 + "M,");
//                System.out.print("已分配内存=" + run.totalMemory() / 1024 / 1024 + "M,");
//                System.out.print("剩余空间内存=" + run.freeMemory() / 1024 / 1024 + "M");
//                System.out.println("最大可用内存=" + (run.maxMemory() - run.totalMemory() + run.freeMemory()) / 1024 / 1024 + "M");
//
//                Thread.sleep(3 * 1000L);
//
//            }
//
//        }
//    }
//}
//
//class Dd{
//    private int[] list;
//
//    public int[] getList() {
//        return list;
//    }
//
//    public void setList(int[] list) {
//        this.list = list;
//    }
//}