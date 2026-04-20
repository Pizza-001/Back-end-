package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;

@Data
public class BizArticle {
    private Long id;
    private String title;
    private String coverImage;
    private String summary;
    private String content;
    private Integer views;
    private Date createTime;
    private Date updateTime;
}
