# p means prod，防止开发同学后续没变更版本，就往docker库里面推了，然后 如果生产要重启，就会拉到开发过程中的版本包
# （或许可以规定2.2.1 为发布版本，开发版本统一添加dev后缀2.2.1.dev）
tag="v1.4"
mirror="yangte8240606/demo"
echo 开始构建镜像...
docker build  -f Dockerfile -t $mirror:$tag .
docker login --password="yt1234567890" --username="yangte8240606"
docker push $mirror:$tag