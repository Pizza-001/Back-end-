package com.pet.hospital.controller.admin;

import com.google.code.kaptcha.Producer;
import com.pet.hospital.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "验证码", description = "获取系统登录验证码接口")
@RestController
@RequestMapping("/admin/captcha")
public class CaptchaController {

    @Autowired
    private Producer producer;

    // 简单内存存储，实际企业项目应该用Redis，带过期时间
    public static final ConcurrentHashMap<String, String> CAPTCHA_MAP = new ConcurrentHashMap<>();

    @Operation(summary = "获取图片验证码", description = "返回验证码图片的 Base64 数据及 UUID 标识符")
    @GetMapping("/image")
    public Result<Map<String, String>> getCaptchaImage() throws Exception {
        String uuid = UUID.randomUUID().toString();
        String text = producer.createText();
        
        CAPTCHA_MAP.put(uuid, text); // 存入内存

        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);

        String base64Img = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        Map<String, String> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", "data:image/jpeg;base64," + base64Img);

        return Result.success(result);
    }
}
