package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 宠物疫苗接种记录实体
 */
@Data
public class BizVaccineRecord {
    private Long id;
    private Long petId;
    private String vaccineName;
    private Date injectTime;
    private Date nextInjectTime;
    private String remarks;
    private Date createTime;
    private Date updateTime;
}
