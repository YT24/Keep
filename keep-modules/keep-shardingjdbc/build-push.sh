#mirror="docker.valuesimplex.tech/knowledgegraphjava/kmp-xxl-job-admin"
# p means prod，防止开发同学后续没变更版本，就往docker库里面推了，然后 如果生产要重启，就会拉到开发过程中的版本包
# （或许可以规定2.2.1 为发布版本，开发版本统一添加dev后缀2.2.1.dev）
#tag="2.2.2.dev"
#
#echo 开始构建镜像...
#
#docker build  -f ./Dockerfile -t $mirror:$tag .
#docker login docker.valuesimplex.tech --password="Rd123!@#" --username="rd"
#
#echo 推送中...
#docker push $mirror:$tag
#echo "完成打包：$mirror:$tag"
#date "+%Y-%m-%d %H:%M:%S"

tag="v1.3"
mirror="yangte8240606/demo"
echo 开始构建镜像...
docker build  -f Dockerfile -t $mirror:$tag .
docker login --password="yt1234567890" --username="yangte8240606"
docker push $mirror:$tag
