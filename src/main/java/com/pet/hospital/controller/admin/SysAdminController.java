package com.pet.hospital.controller.admin;

import com.pet.hospital.common.PageResult;
import com.pet.hospital.common.Result;
import com.pet.hospital.model.entity.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.pet.hospital.service.SysUserService;
import com.pet.hospital.service.SysRoleService;
import com.pet.hospital.service.SysMenuService;
import com.pet.hospital.service.SysDeptService;
import com.pet.hospital.service.SysDictTypeService;
import com.pet.hospital.service.SysDictDataService;
import com.pet.hospital.service.SysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 统一后端系统管理 API (提供前端对接各类管理布局的标准接口规范)
 */
@Tag(name = "系统管理", description = "统一后端系统管理 API (含用户、角色、菜单、部门、字典等)")
@RestController
@RequestMapping("/admin/sys")
public class SysAdminController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Autowired
    private SysDictDataService sysDictDataService;

    @Autowired
    private SysConfigService sysConfigService;

    // ================== 1. 用户管理 (SysUser) ==================
    @Operation(summary = "获取用户列表(支持分页和用户名查询)")
    @GetMapping("/user/list")
    public Result<PageResult<SysUser>> listUser(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username) {
        return Result.success(sysUserService.listUsers(pageNum, pageSize, username));
    }

    @Operation(summary = "添加新用户")
    @PostMapping("/user")
    public Result<String> addUser(@RequestBody SysUser sysUser) {
        sysUserService.addUser(sysUser);
        return Result.success("新增用户成功");
    }

    @Operation(summary = "修改用户信息")
    @PutMapping("/user")
    public Result<String> updateUser(@RequestBody SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return Result.success("修改用户成功");
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/user/{id}")
    public Result<String> deleteUser(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return Result.success("删除用户成功");
    }

    // ================== 2. 角色管理 (SysRole) ==================
    @Operation(summary = "获取角色列表")
    @GetMapping("/role/list")
    public Result<PageResult<SysRole>> listRole(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String roleName) {
        return Result.success(sysRoleService.listRoles(pageNum, pageSize, roleName));
    }

    @Operation(summary = "添加新角色")
    @PostMapping("/role")
    public Result<String> addRole(@RequestBody SysRole role) {
        sysRoleService.addRole(role);
        return Result.success("新增角色成功");
    }

    @Operation(summary = "修改角色信息")
    @PutMapping("/role")
    public Result<String> updateRole(@RequestBody SysRole role) {
        sysRoleService.updateRole(role);
        return Result.success("修改角色成功");
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/role/{id}")
    public Result<String> deleteRole(@PathVariable Long id) {
        sysRoleService.deleteRole(id);
        return Result.success("删除角色成功");
    }

    // ================== 3. 菜单管理 (SysMenu) ==================
    @Operation(summary = "获取菜单权限树")
    @GetMapping("/menu/tree")
    public Result<List<SysMenu>> treeMenu() {
        return Result.success(sysMenuService.treeMenu());
    }

    @Operation(summary = "新增菜单项")
    @PostMapping("/menu")
    public Result<String> addMenu(@RequestBody SysMenu menu) {
        sysMenuService.addMenu(menu);
        return Result.success("新增菜单成功");
    }

    @Operation(summary = "更新菜单项")
    @PutMapping("/menu")
    public Result<String> updateMenu(@RequestBody SysMenu menu) {
        sysMenuService.updateMenu(menu);
        return Result.success("修改菜单成功");
    }

    @Operation(summary = "删除菜单项")
    @DeleteMapping("/menu/{id}")
    public Result<String> deleteMenu(@PathVariable Long id) {
        sysMenuService.deleteMenu(id);
        return Result.success("删除菜单成功");
    }

    // ================== 4. 部门管理 (SysDept) ==================
    @Operation(summary = "获取部门组织架构树")
    @GetMapping("/dept/tree")
    public Result<List<SysDept>> treeDept() {
        return Result.success(sysDeptService.treeDept());
    }

    @Operation(summary = "新增部门")
    @PostMapping("/dept")
    public Result<String> addDept(@RequestBody SysDept dept) {
        sysDeptService.addDept(dept);
        return Result.success("新增部门成功");
    }

    @Operation(summary = "更新部门")
    @PutMapping("/dept")
    public Result<String> updateDept(@RequestBody SysDept dept) {
        sysDeptService.updateDept(dept);
        return Result.success("修改部门成功");
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/dept/{id}")
    public Result<String> deleteDept(@PathVariable Long id) {
        sysDeptService.deleteDept(id);
        return Result.success("删除部门成功");
    }

    // ================== 5. 字典管理 (SysDictType / SysDictData) ==================
    @Operation(summary = "获取字典类型列表(分页)")
    @GetMapping("/dict/type/list")
    public Result<PageResult<SysDictType>> listDictType(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(sysDictTypeService.listDictTypes(pageNum, pageSize));
    }

    @Operation(summary = "新增字典类型")
    @PostMapping("/dict/type")
    public Result<String> addDictType(@RequestBody SysDictType dictType) {
        sysDictTypeService.addDictType(dictType);
        return Result.success("新增字典类型成功");
    }

    @Operation(summary = "更新字典类型")
    @PutMapping("/dict/type")
    public Result<String> updateDictType(@RequestBody SysDictType dictType) {
        sysDictTypeService.updateDictType(dictType);
        return Result.success("修改字典类型成功");
    }

    @Operation(summary = "删除字典类型")
    @DeleteMapping("/dict/type/{id}")
    public Result<String> deleteDictType(@PathVariable Long id) {
        sysDictTypeService.deleteDictType(id);
        return Result.success("删除字典类型成功");
    }

    @Operation(summary = "根据条件获取字典数据(分页)")
    @GetMapping("/dict/data/list")
    public Result<PageResult<SysDictData>> listDictData(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String dictType) {
        return Result.success(sysDictDataService.listDictData(pageNum, pageSize, dictType));
    }

    @Operation(summary = "通过字典类型换取对应的下拉字典数据")
    @GetMapping("/dict/data/type/{dictType}")
    public Result<List<SysDictData>> getDictsByDictType(@PathVariable String dictType) {
        return Result.success(sysDictDataService.getDictsByDictType(dictType));
    }

    @Operation(summary = "新增具体的字典项数据")
    @PostMapping("/dict/data")
    public Result<String> addDictData(@RequestBody SysDictData dictData) {
        sysDictDataService.addDictData(dictData);
        return Result.success("新增字典数据成功");
    }

    @Operation(summary = "修改具体的字典项数据")
    @PutMapping("/dict/data")
    public Result<String> updateDictData(@RequestBody SysDictData dictData) {
        sysDictDataService.updateDictData(dictData);
        return Result.success("修改字典数据成功");
    }

    @Operation(summary = "删除具体的字典项数据")
    @DeleteMapping("/dict/data/{id}")
    public Result<String> deleteDictData(@PathVariable Long id) {
        sysDictDataService.deleteDictData(id);
        return Result.success("删除字典数据成功");
    }

    // ================== 6. 参数管理 (SysConfig) ==================
    @Operation(summary = "获取全局系统参数列表(分页)")
    @GetMapping("/config/list")
    public Result<PageResult<SysConfig>> listConfig(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(sysConfigService.listConfig(pageNum, pageSize));
    }

    @Operation(summary = "根据 ConfigKey 获取指定的参数值")
    @GetMapping("/config/configKey/{configKey}")
    public Result<String> getConfigByKey(@PathVariable String configKey) {
        return Result.success(sysConfigService.getConfigByKey(configKey));
    }

    @Operation(summary = "新增全局系统参数")
    @PostMapping("/config")
    public Result<String> addConfig(@RequestBody SysConfig config) {
        sysConfigService.addConfig(config);
        return Result.success("新增参数配置成功");
    }

    @Operation(summary = "修改全局系统参数")
    @PutMapping("/config")
    public Result<String> updateConfig(@RequestBody SysConfig config) {
        sysConfigService.updateConfig(config);
        return Result.success("修改参数配置成功");
    }

    @Operation(summary = "删除指定的系统参数")
    @DeleteMapping("/config/{id}")
    public Result<String> deleteConfig(@PathVariable Long id) {
        sysConfigService.deleteConfig(id);
        return Result.success("删除参数配置成功");
    }
}
