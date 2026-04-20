package com.pet.hospital.model.entity;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;

@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String phone;
    private String realName;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
