package com.keep.app.fork.join;

import lombok.AllArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
public class MyTask extends RecursiveTask<Integer> {

    Integer begin;

    Integer end;

    @Override
    public String toString(){
        return "{" + begin+","+end+"}";
    }

    @Override
    protected Integer compute() {
        if(begin == end){
            return begin;
        }
        if(end - begin == 1){
            return begin + end;
        }
        int mid = (begin + end) / 2;
        MyTask t1 = new MyTask(begin,mid);
        t1.fork();

        MyTask t2 = new MyTask(mid+1,end);
        t2.fork();

        int result = t1.join() + t2.join();
        return result;
    }


    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(5);
        System.out.println(forkJoinPool.invoke(new MyTask(1, 10)));
    }

}
