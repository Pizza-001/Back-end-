package com.pet.hospital.service.impl;

import com.pet.hospital.mapper.BizPetMapper;
import com.pet.hospital.model.vo.PetVaccineVO;
import com.pet.hospital.service.BizPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BizPetServiceImpl implements BizPetService {
    
    @Autowired
    private BizPetMapper petMapper;

    @Override
    public List<PetVaccineVO> listPetsWithVaccines(Long userId) {
        return petMapper.findPetWithVaccinesByUserId(userId);
    }
    
    // ----------- 补充宠物 CRUD -----------
    @Override
    public void addPet(com.pet.hospital.model.entity.BizPet pet) {
        petMapper.insert(pet);
    }
    @Override
    public void updatePet(com.pet.hospital.model.entity.BizPet pet) {
        petMapper.update(pet);
    }
    @Override
    public void deletePet(Long id) {
        petMapper.deleteById(id);
    }
}
