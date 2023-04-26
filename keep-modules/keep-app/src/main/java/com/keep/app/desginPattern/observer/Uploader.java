package com.keep.app.desginPattern.observer;

import java.util.ArrayList;
import java.util.List;

public class Uploader {

    private List<Observer> observers = new ArrayList<>();
    private String msg;


    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    /**
     * 发布作品
     */
    public void sendMsg(String msg){
        this.msg = msg;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.receive(msg);
        }
    }
}
