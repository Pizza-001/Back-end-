package com.pet.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetHospitalApplication {

    public static void main(String[] args) {
        // 设置无头模式以解决 Linux 环境下验证码绘图报错
        System.setProperty("java.awt.headless", "true");
        SpringApplication.run(PetHospitalApplication.class, args);
        System.out.println("=========================================");
        System.out.println(" 🚀 宠物医院Java后端已成功启动！");
        System.out.println(" 数据库连接正常，Hugging Face 端口已映射 ");
        System.out.println("=========================================");
    }

}
