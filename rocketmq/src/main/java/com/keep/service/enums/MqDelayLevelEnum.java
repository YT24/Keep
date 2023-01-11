package com.keep.service.enums;


public enum MqDelayLevelEnum {
    //"1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
    ONE(1,"1s"),
    TWO(2,"5s"),
    THREE(3,"10s"),
    FOUR(4,"30s"),
    FIVE(5,"1m"),
    SIX(6,"2m"),
    SEVEN(7,"3m"),
    EIGHT(8,"4m"),
    NIN(9,"5m"),
    TEN(10,"6m"),
    ELEVEN(11,"7m"),
    TWELVE(12,"8m"),
    THIRTEEN(13,"9m"),
    FORTEEN(14,"10m"),
    FIFTEEN(15,"20m"),
    SIXTEEN(16,"30m"),
    SEVENTEEN(17,"1h"),
    EIGHTEEN(18,"2h");

    private int level;

    private String levelDesc;

    MqDelayLevelEnum(int i, String s) {
        this.level = i;
        this.levelDesc = s;
    }

    public int getLevel(){
        return level;
    }

    public String getLevelDesc(){
        return levelDesc;
    }

}
