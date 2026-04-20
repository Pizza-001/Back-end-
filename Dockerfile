# 使用 JDK 17
FROM openjdk:17-jdk-slim

# 创建一个非 root 用户（Hugging Face 安全要求）
RUN useradd -m -u 1000 user
USER user
ENV PATH="/home/user/.local/bin:$PATH"

WORKDIR /app

# 拷贝 jar 包
COPY --chown=user target/*.jar app.jar

# 【关键】Hugging Face 强制要求监听 7860 端口
ENV SERVER_PORT=7860
EXPOSE 7860

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=7860"]