package com.pet.hospital.service;

import com.pet.hospital.model.entity.BizDoctor;
import java.util.List;

public interface BizDoctorService {
    List<BizDoctor> listByDepartment(String department);
    BizDoctor getById(Long id);
    
    // ----------- 追加数据管理 (CRUD) 接口 -----------
    void addDoctor(BizDoctor doctor);
    void updateDoctor(BizDoctor doctor);
    void deleteDoctor(Long id);
    
    // ----------- 补充：查医生可用档期 -----------
    java.util.List<com.pet.hospital.model.entity.BizSchedule> getDoctorSchedules(Long doctorId);
}
