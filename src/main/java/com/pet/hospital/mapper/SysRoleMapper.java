package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findRolesByUserId(@Param("userId") Long userId);

    List<SysRole> listRoles(@Param("roleName") String roleName);
    int insert(SysRole role);
    int update(SysRole role);
    int deleteById(@Param("id") Long id);
}
