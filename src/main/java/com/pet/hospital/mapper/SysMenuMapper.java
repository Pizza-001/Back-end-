package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findMenusByUserId(@Param("userId") Long userId);

    List<SysMenu> listAllMenus();
    int insert(SysMenu menu);
    int update(SysMenu menu);
    int deleteById(@Param("id") Long id);
}
