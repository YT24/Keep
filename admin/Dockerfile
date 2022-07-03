#openjdk:8-jre 为基础镜像，来构建此镜像，可以理解为运行的需要基础环境
#FROM java:8
FROM openjdk:8-jre-alpine
#WORKDIR指令用于指定容器的一个目录， 容器启动时执行的命令会在该目录下执行。

# 安装 命令工具
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories
RUN apk add curl
RUN apk add busybox-extras

ENV  TIME_ZONE Asia/Shanghai
ENV  LANG C.UTF-8
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

RUN mkdir -p /opt/app
RUN mkdir -p /opt/opt

WORKDIR  /opt/app
#将当前kmp-indicator.jar 复制到容器根目录下
ADD ./target/admin.jar  /opt/app/admin.jar

EXPOSE 8003
#容器启动时执行的命令

#结合k8s使用
#", --spring.profiles.active=prod"   改用了deployment中的     args: ["--spring.config.additional-location=application.yml"]
ENTRYPOINT ["java", "-jar","-Xmx100m","-Xms50m", "admin.jar"]
