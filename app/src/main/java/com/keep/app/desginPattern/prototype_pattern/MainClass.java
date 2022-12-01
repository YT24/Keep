package com.keep.app.desginPattern.prototype_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 从结果中可以发现，浅层克隆的person2中向friends列表中添加的shallow朋友，而在原型person1中也添加了shallow，
 * 验证了前面的说法。深层克隆person3是在person2之前克隆的，所以没有添加shallow朋友，
 * 而之后添加的deep朋友也没有影响person1和person2中的friends列表。
 *
 * 原型模式  克隆模式
 */
public class MainClass {
    public static void main(String[] args) {
        //创建对象person1
        Person person1 = new Person();
        //初始化对象
        person1.setName("zhangsan");
        person1.setAge(20);
        List<String> friends = new ArrayList<String>();
        friends.add("lisi");
        friends.add("wangwu");
        person1.setFriends(friends);
        //person2是浅层克隆
        Person person2 = person1.shallowClone();
        System.out.println("person 1 == person 2 >> "+(person1.getFriends() == person2.getFriends()));
        //person3是深层克隆
        Person person3 = person1.deepClone();

        System.out.println("person 1 == person 3 >> "+(person1.getFriends() == person3.getFriends()));
        //获取浅层克隆的friends的list对象
        List<String> person2_friends = person2.getFriends();
        person2_friends.add("shallow");
        person2.setFriends(person2_friends);

        //获取深层克隆的friends的list对象
        List<String> person3_friends = person3.getFriends();
        person3_friends.add("deep");
        person3.setFriends(person3_friends);
        
        System.out.println("原    型："+person1);
        System.out.println("浅层克隆："+person2);
        System.out.println("深层克隆："+person3);
    }
}