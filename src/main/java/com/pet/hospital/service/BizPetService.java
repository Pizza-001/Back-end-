package com.pet.hospital.service;

import com.pet.hospital.model.vo.PetVaccineVO;
import java.util.List;

public interface BizPetService {
    /**
     * 获取用户及其宠物信息 (包含疫苗关联)
     * @param userId 登录用户 ID
     * @return 复合视图集合
     */
    List<PetVaccineVO> listPetsWithVaccines(Long userId);
    
    // ----------- 补充宠物 CRUD -----------
    void addPet(com.pet.hospital.model.entity.BizPet pet);
    void updatePet(com.pet.hospital.model.entity.BizPet pet);
    void deletePet(Long id);
}
