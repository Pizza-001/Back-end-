package com.pet.hospital.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import com.pet.hospital.model.entity.BizPet;
import com.pet.hospital.model.entity.BizVaccineRecord;

/**
 * 宠物及疫苗全量记录视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PetVaccineVO extends BizPet {
    private List<BizVaccineRecord> vaccineRecords;
}
