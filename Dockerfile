# 第一阶段：构建打包 (Builder Stage)
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# 设置工作目录
WORKDIR /app

# 先把基于 Maven 项目的构建文件拷贝过去
COPY pom.xml .
COPY src ./src

# 执行打包命令，跳过测试过程以加速构建
RUN mvn clean package -DskipTests

# 第二阶段：运行环境 (Runtime Stage)
FROM eclipse-temurin:17-jre

WORKDIR /app

# 从 builder 阶段复制打包完的 jar 到运行环境中，并重命名为 app.jar
COPY --from=builder /app/target/*.jar app.jar

# 暴露 Spring Boot 的默认 8080 端口
EXPOSE 8080

# 运行 jar 包并且使用环境变量覆盖的方式 (支持上面我们在 yaml 里配置的特性)
ENTRYPOINT ["java", "-jar", "app.jar"]
