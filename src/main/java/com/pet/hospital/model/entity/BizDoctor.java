package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;

/**
 * 医生信息实体
 */
@Data
public class BizDoctor {
    private Long id;
    private String name;
    private String department;
    private String title;
    private String specialty;
    private String introduction;
    private Date createTime;
    private Date updateTime;
}
