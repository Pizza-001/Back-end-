package com.pet.hospital.service.impl;

import com.pet.hospital.mapper.BizAppointmentMapper;
import com.pet.hospital.mapper.BizScheduleMapper;
import com.pet.hospital.model.entity.BizAppointment;
import com.pet.hospital.model.entity.BizSchedule;
import com.pet.hospital.service.BizAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BizAppointmentServiceImpl implements BizAppointmentService {
    
    @Autowired
    private BizScheduleMapper scheduleMapper;
    
    @Autowired
    private BizAppointmentMapper appointmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createAppointment(Long userId, Long petId, Long scheduleId) {
        // 1. 查询档期验证合法性
        BizSchedule schedule = scheduleMapper.findById(scheduleId);
        if (schedule == null) {
            throw new RuntimeException("医生排班不存在");
        }
        
        // 2. 扣减库存 (单条 SQL 行级锁保证高并发安全防超卖)
        // UPDATE biz_schedule SET remain_capacity = remain_capacity - 1 WHERE id = #{id} AND remain_capacity > 0
        int updatedRows = scheduleMapper.decreaseRemainCapacity(scheduleId);
        if (updatedRows == 0) {
            throw new RuntimeException("抱歉，当前档期已被约满，请选择其他时段。");
        }

        // 3. 生成预约记录
        BizAppointment appointment = new BizAppointment();
        appointment.setUserId(userId);
        appointment.setPetId(petId);
        appointment.setDoctorId(schedule.getDoctorId());
        appointment.setScheduleId(scheduleId);
        appointment.setStatus(0); // 待就诊
        
        appointmentMapper.insert(appointment);
    }
    
    @Override
    public java.util.List<com.pet.hospital.model.vo.AppointmentVO> listMyAppointments(Long userId) {
        return appointmentMapper.findMyAppointments(userId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelAppointment(Long id, Long userId) {
        int rows = appointmentMapper.cancelAppointment(id, userId);
        if (rows == 0) {
            throw new RuntimeException("取消挂号失败，该订单不存在或当前状态无法取消。");
        }
    }
    
    @Override
    public com.github.pagehelper.PageInfo<com.pet.hospital.model.vo.AppointmentVO> getPage(int pageNum, int pageSize) {
        com.github.pagehelper.PageHelper.startPage(pageNum, pageSize);
        java.util.List<com.pet.hospital.model.vo.AppointmentVO> list = appointmentMapper.findAllAppointments();
        return new com.github.pagehelper.PageInfo<>(list);
    }
    
    @Override
    public void updateAppointmentStatus(Long id, Integer status) {
        appointmentMapper.updateStatus(id, status);
    }
}
