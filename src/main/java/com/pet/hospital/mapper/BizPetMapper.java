package com.pet.hospital.mapper;

import com.pet.hospital.model.vo.PetVaccineVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 宠物及全量数据 Mapper
 */
@Mapper
public interface BizPetMapper {
    /**
     * 根据用户Id查询名下的宠物列表及其注射过的疫苗
     * 此处展现复杂的 MyBatis 联表查询
     */
    List<PetVaccineVO> findPetWithVaccinesByUserId(@Param("userId") Long userId);
    
    // ----------- 补充宠物 CRUD -----------
    int insert(com.pet.hospital.model.entity.BizPet pet);
    int update(com.pet.hospital.model.entity.BizPet pet);
    int deleteById(@Param("id") Long id);
}
