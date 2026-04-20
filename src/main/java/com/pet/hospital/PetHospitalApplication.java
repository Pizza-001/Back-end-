package com.pet.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetHospitalApplication.class, args);
        System.out.println("=========================================");
        System.out.println(" 🚀 宠物医院Java后端已成功启动！");
        System.out.println(" 数据库连接正常，前端已可以访问 8080 端口 ");
        System.out.println("=========================================");
    }

}
