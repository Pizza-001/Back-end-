package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 挂号预约实体
 */
@Data
public class BizAppointment {
    private Long id;
    private Long userId;
    private Long petId;
    private Long doctorId;
    private Long scheduleId;
    private Integer status;
    private Date visitTime;
    private Date createTime;
    private Date updateTime;
}
