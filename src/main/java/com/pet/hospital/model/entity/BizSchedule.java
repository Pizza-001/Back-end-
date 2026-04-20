package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 医生排班实体
 */
@Data
public class BizSchedule {
    private Long id;
    private Long doctorId;
    private Date scheduleDate;
    private String timeSlot;
    private Integer maxCapacity;
    private Integer remainCapacity;
    private Date createTime;
    private Date updateTime;
}
