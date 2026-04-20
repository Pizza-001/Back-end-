package com.pet.hospital.service.impl;

import com.pet.hospital.mapper.BizDoctorMapper;
import com.pet.hospital.model.entity.BizDoctor;
import com.pet.hospital.service.BizDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BizDoctorServiceImpl implements BizDoctorService {
    @Autowired
    private BizDoctorMapper doctorMapper;

    @Override
    public List<BizDoctor> listByDepartment(String department) {
        return doctorMapper.findByDepartment(department);
    }
    
    @Override
    public BizDoctor getById(Long id) {
        return doctorMapper.findById(id);
    }
    
    // ----------- 追加数据管理 (CRUD) 实现 -----------
    @Override
    public void addDoctor(BizDoctor doctor) {
        doctorMapper.insert(doctor);
    }

    @Override
    public void updateDoctor(BizDoctor doctor) {
        doctorMapper.update(doctor);
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorMapper.deleteById(id);
    }
    
    @Autowired
    private com.pet.hospital.mapper.BizScheduleMapper scheduleMapper;

    @Override
    public java.util.List<com.pet.hospital.model.entity.BizSchedule> getDoctorSchedules(Long doctorId) {
        return scheduleMapper.findAvailableByDoctorId(doctorId);
    }
}
