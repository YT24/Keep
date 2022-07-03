#!/bin/bash
set -x
set -e

mirror="yangte8240606/admin"
# p means prod，防止开发同学后续没变更版本，就往docker库里面推了，然后 如果生产要重启，就会拉到开发过程中的版本包
# （或许可以规定2.2.1 为发布版本，开发版本统一添加dev后缀2.2.1.dev）

#tag="prod219fix"
#tag="prod220"
#去掉了一个抛的异常
#tag="prod220fix"

tag="8.0.0.dev"




echo 开始构建镜像...

docker build  -f ./Dockerfile -t $mirror:$tag .
#docker login --password="yt070266" --username="yangte8240606"
docker login


echo 推送中...
docker push $mirror:$tag
echo "完成打包：$mirror:$tag"
date "+%Y-%m-%d %H:%M:%S"