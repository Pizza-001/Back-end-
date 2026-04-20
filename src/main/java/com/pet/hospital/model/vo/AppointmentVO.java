package com.pet.hospital.model.vo;

import lombok.Data;
import java.util.Date;

@Data
public class AppointmentVO {
    private Long id;
    private Long petId;
    private String petNickname;
    private Long doctorId;
    private String doctorName;
    private String department;
    private Date scheduleDate;
    private String scheduleTime;
    private Integer status;
    private Date createTime;
}
