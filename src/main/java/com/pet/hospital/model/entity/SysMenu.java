package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long parentId;
    private String menuName;
    private String path;
    private String component;
    private String perms;
    private Integer menuType;
    private Integer sortNo;
    private Date createTime;
    private Date updateTime;
    
    private java.util.List<SysMenu> children = new java.util.ArrayList<>();
}
