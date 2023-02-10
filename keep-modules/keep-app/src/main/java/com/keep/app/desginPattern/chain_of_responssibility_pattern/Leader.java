package com.keep.app.desginPattern.chain_of_responssibility_pattern;

//抽象类
public abstract class Leader {
    
    String name;
    
    Leader nextLeader;

    public Leader(String name) {
        this.name = name;
    }

    public void setNextLeader(Leader nextLeader) {
        this.nextLeader = nextLeader;
    }
    
    //设置责任链的处理对象（核心）
    public abstract void handleRequest(QingjiaRequest qingjiaRequest);

}