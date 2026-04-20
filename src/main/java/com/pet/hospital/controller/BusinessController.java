package com.pet.hospital.controller;

import com.pet.hospital.common.Result;
import com.pet.hospital.model.entity.BizDoctor;
import com.pet.hospital.model.vo.PetVaccineVO;
import com.pet.hospital.service.BizAppointmentService;
import com.pet.hospital.service.BizDoctorService;
import com.pet.hospital.service.BizPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 宠物医院核心业务控制器
 */
@Tag(name = "前台业务中心", description = "给前端 C 端用户暴露的挂号、建档、查询等核心业务逻辑")
@RestController
@RequestMapping("/biz")
public class BusinessController {

    @Autowired
    private BizDoctorService doctorService;
    
    @Autowired
    private BizAppointmentService appointmentService;
    
    @Autowired
    private BizPetService petService;

    /**
     * 医生管理：检索医生（支持按科室筛选）
     */
    @Operation(summary = "前台获取医生列表和展示过滤")
    @GetMapping("/doctors")
    public Result<List<BizDoctor>> listDoctors(@RequestParam(required = false) String department) {
        return Result.success(doctorService.listByDepartment(department));
    }

    /**
     * 预约挂号 (要求用户处于登录态获取 userId)
     */
    @Operation(summary = "进行预约挂号挂靠", description = "提交某宠物的看病诉求与目标医生排班的扣减")
    @PostMapping("/appointment")
    public Result<String> createAppointment(
            @RequestAttribute("userId") Long userId,
            @RequestParam Long petId,
            @RequestParam Long scheduleId) {
        try {
            appointmentService.createAppointment(userId, petId, scheduleId);
            return Result.success("预约成功！");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // ----- 医生管理 (CRUD) -----
    @Operation(summary = "添加新医生信息")
    @PostMapping("/doctor")
    public Result<String> addDoctor(@RequestBody BizDoctor doctor) {
        doctorService.addDoctor(doctor);
        return Result.success("新增医生成功");
    }

    @Operation(summary = "修改医生资料信息")
    @PutMapping("/doctor")
    public Result<String> updateDoctor(@RequestBody BizDoctor doctor) {
        doctorService.updateDoctor(doctor);
        return Result.success("修改医生资料成功");
    }

    @Operation(summary = "删除医生建制档案")
    @DeleteMapping("/doctor/{id}")
    public Result<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return Result.success("删除医生成功");
    }

    @Autowired
    private com.pet.hospital.service.BizBannerService bannerService;
    
    @Autowired
    private com.pet.hospital.service.BizArticleService articleService;

    // ----- 首页动态展示区 -----
    @Operation(summary = "获取正生效的前台主页轮播图")
    @GetMapping("/banners")
    public Result<List<com.pet.hospital.model.entity.BizBanner>> getBanners() {
        return Result.success(bannerService.getActiveBanners());
    }

    @Operation(summary = "前台检索查阅所有养宠知识文章")
    @GetMapping("/articles")
    public Result<List<com.pet.hospital.model.entity.BizArticle>> getArticles() {
        return Result.success(articleService.getAllArticles());
    }

    @Operation(summary = "查询获取某篇文章的正文详情与图片")
    @GetMapping("/articles/{id}")
    public Result<com.pet.hospital.model.entity.BizArticle> getArticleDetail(@PathVariable Long id) {
        return Result.success(articleService.getArticleById(id));
    }

    /**
     * 复杂联表查询：获取“我的”宠物列表 + 对应的疫苗接种记录
     */
    @Operation(summary = "获取我的萌宠建档列表及对应疫苗记录")
    @GetMapping("/pets")
    public Result<List<PetVaccineVO>> getMyPets(@RequestAttribute("userId") Long userId) {
        return Result.success(petService.listPetsWithVaccines(userId));
    }

    // ----- 宠物档案 (CRUD) -----
    @Operation(summary = "新增宠物电子档案")
    @PostMapping("/pet")
    public Result<String> addPet(@RequestAttribute("userId") Long userId, @RequestBody com.pet.hospital.model.entity.BizPet pet) {
        pet.setUserId(userId);
        petService.addPet(pet);
        return Result.success("新增宠物成功");
    }

    @Operation(summary = "更新维护宠物电子信息")
    @PutMapping("/pet")
    public Result<String> updatePet(@RequestAttribute("userId") Long userId, @RequestBody com.pet.hospital.model.entity.BizPet pet) {
        pet.setUserId(userId);
        petService.updatePet(pet);
        return Result.success("修改宠物信息成功");
    }

    @Operation(summary = "标记宠物状态销户或者死亡")
    @DeleteMapping("/pet/{id}")
    public Result<String> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return Result.success("删除成功");
    }

    // ----- 在线预约 (排班查询与挂号记录) -----
    @Operation(summary = "查询某一天的医生排班与接诊大厅放号状况")
    @GetMapping("/schedules")
    public Result<List<com.pet.hospital.model.entity.BizSchedule>> getSchedules(@RequestParam Long doctorId) {
        return Result.success(doctorService.getDoctorSchedules(doctorId));
    }

    @Operation(summary = "查询我的全部挂号账单与预约")
    @GetMapping("/appointments")
    public Result<List<com.pet.hospital.model.vo.AppointmentVO>> getMyAppointments(@RequestAttribute("userId") Long userId) {
        return Result.success(appointmentService.listMyAppointments(userId));
    }

    @Operation(summary = "由于顾客原因主动取消未执行的门诊挂号")
    @PutMapping("/appointment/{id}/cancel")
    public Result<String> cancelAppointment(@RequestAttribute("userId") Long userId, @PathVariable Long id) {
        try {
            appointmentService.cancelAppointment(id, userId);
            return Result.success("取消预约成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
