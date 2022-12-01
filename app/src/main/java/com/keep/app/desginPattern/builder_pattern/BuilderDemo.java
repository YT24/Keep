package com.keep.app.desginPattern.builder_pattern;

public class BuilderDemo {
    public static void main(String[] args) {
        //指挥者
        Director director = new Director();
        //建造者
        Builder builder = new ConcreteBuilder();
        //老板找建造者组装电脑
        director.Construct(builder);

        //获取组装好的电脑
        Computer computer = builder.GetComputer();
        //展示
        computer.Show();

    }
}
