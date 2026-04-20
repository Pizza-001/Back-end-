package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser findByUsername(@Param("username") String username);
    SysUser findByPhone(@Param("phone") String phone);
    int updatePassword(@Param("phone") String phone, @Param("password") String password);
    
    int insert(SysUser user);
    SysUser findById(@Param("id") Long id);
    
    List<SysUser> listUsers(@Param("username") String username);
    int update(SysUser user);
    int deleteById(@Param("id") Long id);
}
