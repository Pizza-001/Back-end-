# 使用 JDK 17 轻量级基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 把编译好的 jar 包拷进去 (确保你本地运行过 mvn clean package)
COPY target/*.jar app.jar

# Koyeb 默认喜欢用 8000 或 8080 端口
EXPOSE 8080

# 【核心必杀技】：强制限制 Java 内存，最大 300M，否则 Koyeb 会直接把你的服务杀掉 (OOM)
ENTRYPOINT ["java", "-Xmx300m", "-Xms128m", "-jar", "app.jar"]