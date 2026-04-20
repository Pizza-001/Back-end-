package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 宠物实体
 */
@Data
public class BizPet {
    private Long id;
    private Long userId;
    private String nickname;
    private String species;
    private String breed;
    private BigDecimal age;
    private Integer gender;
    private Date createTime;
    private Date updateTime;
}
