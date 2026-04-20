package com.pet.hospital.service.impl;

import com.pet.hospital.mapper.SysMenuMapper;
import com.pet.hospital.mapper.SysRoleMapper;
import com.pet.hospital.mapper.SysUserMapper;
import com.pet.hospital.model.dto.LoginDTO;
import com.pet.hospital.model.dto.PhoneLoginDTO;
import com.pet.hospital.model.dto.ResetPasswordDTO;
import com.pet.hospital.model.entity.SysMenu;
import com.pet.hospital.model.entity.SysRole;
import com.pet.hospital.model.entity.SysUser;
import com.pet.hospital.service.SysUserService;
import com.pet.hospital.utils.EncryptUtils;
import com.pet.hospital.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    // 实际项目中应使用 Redis 存储验证码，此处使用内存 Map 模拟
    private static final Map<String, String> CODE_CACHE = new HashMap<>();

    @Override
    public String loginByPassword(LoginDTO loginDTO) {
        // 先校验验证码
        if (loginDTO.getUuid() != null && loginDTO.getCode() != null) {
            // 【万能验证码修复】如果是 8888，则直接放行，无需校验图形
            if ("8888".equals(loginDTO.getCode())) {
                log.info("用户 [{}] 使用万能验证码 8888 登录", loginDTO.getUsername());
            } else {
                String verifyKey = loginDTO.getUuid();
                String captcha = com.pet.hospital.controller.admin.CaptchaController.CAPTCHA_MAP.get(verifyKey);
                com.pet.hospital.controller.admin.CaptchaController.CAPTCHA_MAP.remove(verifyKey);
                if (captcha == null || !captcha.equalsIgnoreCase(loginDTO.getCode())) {
                    throw new RuntimeException("图形验证码错误或已失效");
                }
            }
        }

        SysUser user = sysUserMapper.findByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在或已被禁用");
        }
        if (!EncryptUtils.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return generateUserToken(user);
    }

    @Override
    public String loginByPhone(PhoneLoginDTO phoneLoginDTO) {
        String cachedCode = CODE_CACHE.get(phoneLoginDTO.getPhone());
        // 加入测试万能验证码 1234
        if (!"1234".equals(phoneLoginDTO.getCode()) && (cachedCode == null || !cachedCode.equals(phoneLoginDTO.getCode()))) {
            throw new RuntimeException("验证码错误或已过期");
        }
        if (cachedCode != null && cachedCode.equals(phoneLoginDTO.getCode())) {
            CODE_CACHE.remove(phoneLoginDTO.getPhone());
        }
        
        SysUser user = sysUserMapper.findByPhone(phoneLoginDTO.getPhone());
        if (user == null) {
            throw new RuntimeException("该手机号未注册用户或已被禁用");
        }
        return generateUserToken(user);
    }

    @Override
    public void sendPhoneCode(String phone) {
        // 生成 6 位随机验证码
        String code = String.valueOf((int)((Math.random() * 9 + 1) * 100000));
        CODE_CACHE.put(phone, code);
        // 模拟发送短信
        log.info("【模拟短信发送】手机号: {}, 验证码: {}", phone, code);
    }

    @Override
    public void resetPassword(ResetPasswordDTO dto) {
        String cachedCode = CODE_CACHE.get(dto.getPhone());
        // 加入测试万能验证码 1234
        if (!"1234".equals(dto.getCode()) && (cachedCode == null || !cachedCode.equals(dto.getCode()))) {
            throw new RuntimeException("验证码错误或已过期");
        }
        if (cachedCode != null && cachedCode.equals(dto.getCode())) {
            CODE_CACHE.remove(dto.getPhone());
        }

        SysUser user = sysUserMapper.findByPhone(dto.getPhone());
        if (user == null) {
            throw new RuntimeException("该手机号未注册");
        }

        String encryptedNewPwd = EncryptUtils.encryptSha256(dto.getNewPassword());
        sysUserMapper.updatePassword(dto.getPhone(), encryptedNewPwd);
    }

    @Override
    public Map<String, Object> getUserPermissions(Long userId) {
        List<SysRole> roles = sysRoleMapper.findRolesByUserId(userId);
        List<SysMenu> menus = sysMenuMapper.findMenusByUserId(userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("roles", roles.stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
        result.put("menus", menus); // 此处可按需转为树状结构
        result.put("perms", menus.stream()
                .map(SysMenu::getPerms)
                .filter(p -> p != null && !p.isEmpty())
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public void register(com.pet.hospital.model.dto.RegisterDTO dto) {
        if (sysUserMapper.findByUsername(dto.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
            if (sysUserMapper.findByPhone(dto.getPhone()) != null) {
                throw new RuntimeException("手机号已被注册");
            }
        }
        SysUser user = new SysUser();
        user.setUsername(dto.getUsername());
        user.setPassword(EncryptUtils.encryptSha256(dto.getPassword()));
        user.setPhone(dto.getPhone());
        sysUserMapper.insert(user);
    }
    
    @Override
    public SysUser getProfile(Long userId) {
        SysUser user = sysUserMapper.findById(userId);
        if (user != null) {
            user.setPassword(null); // 安全性：给前端剔除密码哈希
        }
        return user;
    }

    @Override
    public com.pet.hospital.common.PageResult<com.pet.hospital.model.entity.SysUser> listUsers(Integer pageNum, Integer pageSize, String username) {
        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.listUsers(username);
        com.github.pagehelper.PageInfo<SysUser> pageInfo = new com.github.pagehelper.PageInfo<>(list);
        return new com.pet.hospital.common.PageResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void addUser(SysUser user) {
        if (sysUserMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(EncryptUtils.encryptSha256(user.getPassword()));
        } else {
            user.setPassword(EncryptUtils.encryptSha256("123456")); // 默认密码
        }
        sysUserMapper.insert(user);
    }

    @Override
    public void updateUser(SysUser user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(EncryptUtils.encryptSha256(user.getPassword()));
        } else {
            user.setPassword(null); // 不更新密码
        }
        sysUserMapper.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        sysUserMapper.deleteById(id);
    }

    private String generateUserToken(SysUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        return JwtUtils.generateToken(claims);
    }
}
