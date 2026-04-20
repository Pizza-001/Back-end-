package com.pet.hospital.controller;

import com.pet.hospital.common.Result;
import com.pet.hospital.model.dto.LoginDTO;
import com.pet.hospital.model.dto.PhoneLoginDTO;
import com.pet.hospital.model.dto.ResetPasswordDTO;
import com.pet.hospital.service.SysUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "认证授权", description = "用户登录注册、权限校验等前后台通用认证接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserService sysUserService;

    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public Result<String> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            String token = sysUserService.loginByPassword(loginDTO);
            return Result.success(token);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "手机号验证码登录")
    @PostMapping("/phone-login")
    public Result<String> phoneLogin(@Valid @RequestBody PhoneLoginDTO phoneLoginDTO) {
        try {
            String token = sysUserService.loginByPhone(phoneLoginDTO);
            return Result.success(token);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "发送短信验证码(模拟环境)")
    @GetMapping("/send-code")
    public Result<String> sendCode(@RequestParam String phone) {
        if (phone == null || !phone.matches("^1[3-9]\\d{9}$")) {
            return Result.error("手机号格式错误");
        }
        sysUserService.sendPhoneCode(phone);
        return Result.success("验证码已发送至控制台(模拟)");
    }

    @Operation(summary = "重置找回密码")
    @PostMapping("/reset-password")
    public Result<String> resetPassword(@Valid @RequestBody ResetPasswordDTO dto) {
        try {
            sysUserService.resetPassword(dto);
            return Result.success("密码重置成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取当前登录用户的菜单路由与按钮权限代码")
    @GetMapping("/permissions")
    public Result<Map<String, Object>> getPermissions(@RequestAttribute(value = "userId", required = false) Long userId) {
        try {
            if (userId == null) {
                return Result.error(401, "未授权的访问");
            }
            return Result.success(sysUserService.getUserPermissions(userId));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "注册新用户角色")
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody com.pet.hospital.model.dto.RegisterDTO dto) {
        try {
            sysUserService.register(dto);
            return Result.success("注册成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取当前用户的个人基础资料")
    @GetMapping("/profile")
    public Result<com.pet.hospital.model.entity.SysUser> getProfile(@RequestAttribute("userId") Long userId) {
        return Result.success(sysUserService.getProfile(userId));
    }
}
