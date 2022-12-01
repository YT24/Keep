##openjdk:8-jre 为基础镜像，来构建此镜像，可以理解为运行的需要基础环境
##FROM java:8
#FROM openjdk:8-jre-alpine
##WORKDIR指令用于指定容器的一个目录， 容器启动时执行的命令会在该目录下执行。
#
#ENV  TIME_ZONE Asia/Shanghai
#ENV  LANG C.UTF-8
#RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
#RUN echo 'Asia/Shanghai' >/etc/timezone
#
#RUN mkdir -p /opt/app
#
#WORKDIR  /opt/app
##将当前kmp-indicator.jar 复制到容器根目录下
#ADD target/kmp-xxl-job-admin.jar  /opt/app/kmp-xxl-job-admin.jar
##将依赖包 复制到容器根目录/libs下,kmp-indicator.jar已不再需要添加其它jar包
##ADD libs /libs
##暴露容器端口为9202 Docker镜像告知Docker宿主机应用监听了9202端口
#EXPOSE 9202
##容器启动时执行的命令
##CMD ["java", "-jar", "-Xmx2048m","-Xms56m","./kmp-xxl-job-admin.jar" , "--spring.profiles.active=dev"]
##结合k8s使用
##", --spring.profiles.active=prod"   改用了deployment中的     args: ["--spring.config.additional-location=application.properties"]
#ENTRYPOINT ["java", "-jar","-Xmx2048m","-Xms128m", "kmp-xxl-job-admin.jar"]
#
FROM openjdk:8-jre-alpine
ADD target/BServer-0.0.1-SNAPSHOT.jar target/BServer.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar","-Xmx100m","-Xms50m", "target/BServer.jar"]