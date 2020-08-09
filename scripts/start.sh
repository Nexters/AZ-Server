#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/app/deploy
PROJECT_NAME=AZ-Server

echo "> Build 파일 복사"

cp $REPOSITORY/*.jar $REPOSITORY/zip

echo "> 구동중인 애플리케이션 pid가 있다면 죽입니다"
fuser -k -n tcp 8080

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/zip/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

source $HOME/.bashrc

nohup java -jar -Dspring.config.location=/home/ec2-user/app/application-real-db.properties $JAR_NAME > $REPOSITORY/zip/nohup.out 2>&1 &
