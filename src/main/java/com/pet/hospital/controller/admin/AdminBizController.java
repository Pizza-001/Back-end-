package com.pet.hospital.controller.admin;

import com.pet.hospital.common.Result;
import com.pet.hospital.common.PageResult;
import com.pet.hospital.model.entity.BizBanner;
import com.pet.hospital.service.BizBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "业务管理", description = "核心业务的后台管理接口 (轮播图、文章、疫苗记录、挂号等)")
@RestController
@RequestMapping("/admin/biz")
public class AdminBizController {

    @Autowired
    private BizBannerService bannerService;

    // ----- Banner 管理 -----
    @Operation(summary = "获取轮播图列表(分页)")
    @GetMapping("/banner/list")
    public Result<PageResult<BizBanner>> listBanner(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(bannerService.getPage(pageNum, pageSize));
    }

    @Operation(summary = "新增轮播图")
    @PostMapping("/banner")
    public Result<String> addBanner(@RequestBody BizBanner banner) {
        bannerService.addBanner(banner);
        return Result.success("新增轮播图成功");
    }

    @Operation(summary = "更新轮播图信息")
    @PutMapping("/banner")
    public Result<String> updateBanner(@RequestBody BizBanner banner) {
        bannerService.updateBanner(banner);
        return Result.success("修改轮播图成功");
    }

    @Operation(summary = "删除轮播图")
    @DeleteMapping("/banner/{id}")
    public Result<String> deleteBanner(@PathVariable Long id) {
        bannerService.deleteBanner(id);
        return Result.success("删除成功");
    }
    
    @Autowired
    private com.pet.hospital.service.BizArticleService articleService;

    // ----- Article (养宠知识) 管理 -----
    @Operation(summary = "获取养宠知识文章列表")
    @GetMapping("/article/list")
    public Result<PageResult<com.pet.hospital.model.entity.BizArticle>> listArticle(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        com.github.pagehelper.PageInfo<com.pet.hospital.model.entity.BizArticle> pageInfo = articleService.getPage(pageNum, pageSize);
        return Result.success(new PageResult<>(pageInfo.getTotal(), pageInfo.getList()));
    }

    @Operation(summary = "发布新文章")
    @PostMapping("/article")
    public Result<String> addArticle(@RequestBody com.pet.hospital.model.entity.BizArticle article) {
        articleService.addArticle(article);
        return Result.success("新增文章成功");
    }

    @Operation(summary = "更新文章数据")
    @PutMapping("/article")
    public Result<String> updateArticle(@RequestBody com.pet.hospital.model.entity.BizArticle article) {
        articleService.updateArticle(article);
        return Result.success("修改文章成功");
    }

    @Operation(summary = "删除指定的文章")
    @DeleteMapping("/article/{id}")
    public Result<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Result.success("删除文章成功");
    }

    @Autowired
    private com.pet.hospital.service.BizVaccineRecordService vaccineService;

    // ----- Vaccine (疫苗) 管理 -----
    @Operation(summary = "分页获取所有的疫苗接种记录")
    @GetMapping("/vaccine/list")
    public Result<PageResult<com.pet.hospital.model.entity.BizVaccineRecord>> listVaccine(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        com.github.pagehelper.PageInfo<com.pet.hospital.model.entity.BizVaccineRecord> pageInfo = vaccineService.getPage(pageNum, pageSize);
        return Result.success(new PageResult<>(pageInfo.getTotal(), pageInfo.getList()));
    }

    @Operation(summary = "为宠物新增一条疫苗接种记录")
    @PostMapping("/vaccine")
    public Result<String> addVaccine(@RequestBody com.pet.hospital.model.entity.BizVaccineRecord record) {
        vaccineService.addRecord(record);
        return Result.success("新增疫苗记录成功");
    }

    @Operation(summary = "更新现存的疫苗接种记录")
    @PutMapping("/vaccine")
    public Result<String> updateVaccine(@RequestBody com.pet.hospital.model.entity.BizVaccineRecord record) {
        vaccineService.updateRecord(record);
        return Result.success("修改疫苗记录成功");
    }

    @Operation(summary = "取消/删除指定的疫苗记录")
    @DeleteMapping("/vaccine/{id}")
    public Result<String> deleteVaccine(@PathVariable Long id) {
        vaccineService.deleteRecord(id);
        return Result.success("删除疫苗记录成功");
    }

    @Autowired
    private com.pet.hospital.service.BizAppointmentService appointmentService;

    // ----- Appointment (预约) 管理 -----
    @Operation(summary = "获取全医院的挂号预约记录查询")
    @GetMapping("/appointment/list")
    public Result<PageResult<com.pet.hospital.model.vo.AppointmentVO>> listAppointment(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        com.github.pagehelper.PageInfo<com.pet.hospital.model.vo.AppointmentVO> pageInfo = appointmentService.getPage(pageNum, pageSize);
        return Result.success(new PageResult<>(pageInfo.getTotal(), pageInfo.getList()));
    }

    @Operation(summary = "更改挂号预约的状态 (接诊、取消等)")
    @PutMapping("/appointment/{id}/status")
    public Result<String> updateAppointmentStatus(@PathVariable Long id, @RequestParam Integer status) {
        appointmentService.updateAppointmentStatus(id, status);
        return Result.success("更新预约状态成功");
    }

    // ================== Dashboard 工作台统计 ==================
    @Operation(summary = "获取后台仪表盘工作台的总体统计数据")
    @GetMapping("/dashboard/stats")
    public Result<java.util.Map<String, Object>> getDashboardStats() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalPets", 128);       // 模拟数据：宠物总数
        stats.put("totalDoctors", 12);     // 模拟数据：医生总数
        stats.put("todayAppointments", 5); // 模拟数据：今日预约数
        stats.put("totalIncome", 3450.00); // 模拟数据：总收入
        
        // 模拟折线图数据
        stats.put("chartData", java.util.Arrays.asList(120, 200, 150, 80, 70, 110, 130));
        return Result.success(stats);
    }
}
