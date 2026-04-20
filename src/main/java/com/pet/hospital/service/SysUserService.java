package com.pet.hospital.service;

import com.pet.hospital.model.dto.LoginDTO;
import com.pet.hospital.model.dto.PhoneLoginDTO;
import com.pet.hospital.model.dto.ResetPasswordDTO;
import java.util.Map;

public interface SysUserService {
    String loginByPassword(LoginDTO loginDTO);
    String loginByPhone(PhoneLoginDTO phoneLoginDTO);
    void sendPhoneCode(String phone);
    void resetPassword(ResetPasswordDTO resetPasswordDTO);
    Map<String, Object> getUserPermissions(Long userId);
    
    void register(com.pet.hospital.model.dto.RegisterDTO dto);
    com.pet.hospital.model.entity.SysUser getProfile(Long userId);
    
    com.pet.hospital.common.PageResult<com.pet.hospital.model.entity.SysUser> listUsers(Integer pageNum, Integer pageSize, String username);
    void addUser(com.pet.hospital.model.entity.SysUser user);
    void updateUser(com.pet.hospital.model.entity.SysUser user);
    void deleteUser(Long id);
}
