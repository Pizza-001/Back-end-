package com.pet.hospital.controller.admin;

import com.pet.hospital.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 简单的代码生成器/表结构读取接口
 */
@Tag(name = "代码生成器", description = "用于读取表结构并预览生成的 Java 配置代码")
@RestController
@RequestMapping("/admin/sys/generator")
public class GeneratorController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取数据库所有表信息
     */
    @Operation(summary = "获取当前数据库所有的表信息")
    @GetMapping("/tables")
    public Result<List<Map<String, Object>>> getTables() {
        String sql = "SELECT TABLE_NAME as tableName, TABLE_COMMENT as tableComment, CREATE_TIME as createTime " +
                     "FROM information_schema.TABLES " +
                     "WHERE TABLE_SCHEMA = (SELECT DATABASE())";
        List<Map<String, Object>> tables = jdbcTemplate.queryForList(sql);
        return Result.success(tables);
    }

    /**
     * 获取表的所有列信息
     */
    @Operation(summary = "获取指定表结构的所有列的元数据")
    @GetMapping("/columns")
    public Result<List<Map<String, Object>>> getColumns(@RequestParam String tableName) {
        String sql = "SELECT COLUMN_NAME as columnName, DATA_TYPE as dataType, COLUMN_COMMENT as columnComment " +
                     "FROM information_schema.COLUMNS " +
                     "WHERE TABLE_SCHEMA = (SELECT DATABASE()) AND TABLE_NAME = ?";
        List<Map<String, Object>> columns = jdbcTemplate.queryForList(sql, tableName);
        return Result.success(columns);
    }

    /**
     * 预览生成的代码
     */
    @Operation(summary = "预览针对某张表生成的后端 CRUD 代码")
    @GetMapping("/preview")
    public Result<Map<String, String>> previewCode(@RequestParam String tableName) {
        String entityName = toCamelCase(tableName, true);
        Map<String, String> result = new java.util.HashMap<>();
        
        result.put("Entity.java", "package com.pet.hospital.model.entity;\n\nimport lombok.Data;\n\n@Data\npublic class " + entityName + " {\n    // TODO: 自动生成字段\n}\n");
        result.put("Mapper.java", "package com.pet.hospital.mapper;\n\nimport org.apache.ibatis.annotations.Mapper;\n\n@Mapper\npublic interface " + entityName + "Mapper {\n}\n");
        result.put("Service.java", "package com.pet.hospital.service;\n\npublic interface " + entityName + "Service {\n}\n");
        result.put("Controller.java", "package com.pet.hospital.controller.admin;\n\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class " + entityName + "Controller {\n}\n");
        
        return Result.success(result);
    }

    private String toCamelCase(String s, boolean firstUpper) {
        String[] parts = s.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].isEmpty()) continue;
            if (i == 0 && !firstUpper) {
                sb.append(parts[i].toLowerCase());
            } else {
                sb.append(Character.toUpperCase(parts[i].charAt(0)));
                if (parts[i].length() > 1) {
                    sb.append(parts[i].substring(1).toLowerCase());
                }
            }
        }
        return sb.toString();
    }
}
