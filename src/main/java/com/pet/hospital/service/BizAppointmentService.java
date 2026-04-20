package com.pet.hospital.service;

public interface BizAppointmentService {
    /**
     * 预约挂号 (保证无超发的高并发安全)
     * @param userId 挂号人 ID
     * @param petId 宠物 ID
     * @param scheduleId 排班 ID
     */
    void createAppointment(Long userId, Long petId, Long scheduleId);
    
    // ----------- 补充：我的预约与取消 -----------
    java.util.List<com.pet.hospital.model.vo.AppointmentVO> listMyAppointments(Long userId);
    void cancelAppointment(Long id, Long userId);
    
    // ----------- 后台管理端 -----------
    com.github.pagehelper.PageInfo<com.pet.hospital.model.vo.AppointmentVO> getPage(int pageNum, int pageSize);
    void updateAppointmentStatus(Long id, Integer status);
}
