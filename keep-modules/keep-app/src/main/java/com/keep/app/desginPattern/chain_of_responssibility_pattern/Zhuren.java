package com.keep.app.desginPattern.chain_of_responssibility_pattern;

//主任
public class Zhuren extends Leader{

    public Zhuren(String name){
        super(name);
    }
    
    
    @Override
    public void handleRequest(QingjiaRequest qingjiaRequest) {
        if(qingjiaRequest.getDays()<3){
            System.out.println("员工请假小于3天");
            System.out.println("审批通过");
        }else{
            if(this.nextLeader!=null){
                this.nextLeader.handleRequest(qingjiaRequest);
            }
        }
        
        
    }

}