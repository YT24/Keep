package com.keep.app.desginPattern.prototype_pattern;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Person implements Cloneable{
    //姓名
    private String name;
    //年龄
    private int age;
    //朋友
    private List<String> friends;

    //重写toString方法
    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", friends=" + friends + "]";
    }
    //浅层克隆
    public Person shallowClone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    //深层克隆
    public Person deepClone() {
        try {
            Person person = (Person) super.clone();
            List<String> newFriends = new ArrayList<String>();
            for(String friend : this.getFriends()) {
                newFriends.add(friend);
            }
            person.setFriends(newFriends);
            return person;
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}