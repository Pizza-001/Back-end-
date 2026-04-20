package com.pet.hospital.mapper;

import com.pet.hospital.model.entity.BizAppointment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 预约挂号 Mapper
 */
@Mapper
public interface BizAppointmentMapper {
    int insert(BizAppointment appointment);
    BizAppointment findById(@Param("id") Long id);
    
    List<com.pet.hospital.model.vo.AppointmentVO> findMyAppointments(@Param("userId") Long userId);
    int cancelAppointment(@Param("id") Long id, @Param("userId") Long userId);

    // --- 后台管理端接口 ---
    List<com.pet.hospital.model.vo.AppointmentVO> findAllAppointments();
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
