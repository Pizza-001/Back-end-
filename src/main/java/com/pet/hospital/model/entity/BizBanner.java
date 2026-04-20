package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;

@Data
public class BizBanner {
    private Long id;
    private String imageUrl;
    private String linkUrl;
    private Integer sortNo;
    private Integer isActive;
    private Date createTime;
    private Date updateTime;
}
