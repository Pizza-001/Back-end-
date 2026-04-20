-- 数据库表结构设计：用户与权限模块（RBAC）

CREATE DATABASE IF NOT EXISTS pet_hospital DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pet_hospital;

-- 1. 用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(128) NOT NULL COMMENT '密码(加密后)',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    real_name VARCHAR(64) COMMENT '真实姓名',
    status TINYINT DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='系统用户表';

-- 2. 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(64) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(64) NOT NULL UNIQUE COMMENT '角色标识',
    description VARCHAR(255) COMMENT '描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='系统角色表';

-- 3. 菜单/权限表
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    menu_name VARCHAR(64) NOT NULL COMMENT '菜单或按钮名称',
    path VARCHAR(128) COMMENT '前端路由路径',
    component VARCHAR(255) COMMENT '前端组件路径',
    perms VARCHAR(128) COMMENT '权限标识(如: sys:user:add)',
    menu_type TINYINT COMMENT '类型：1-目录，2-菜单，3-按钮',
    sort_no INT DEFAULT 0 COMMENT '排序',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='系统菜单权限表';

-- 4. 用户与角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB COMMENT='用户与角色关联表';

-- 5. 角色与菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB COMMENT='角色与菜单权限关联表';

-- 插入一些基础测试数据
INSERT IGNORE INTO sys_user (id, username, password, phone, real_name) VALUES 
(1, 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', '13800138000', '超级管理员'); -- 密码为 123456 的 SHA-256

INSERT IGNORE INTO sys_role (id, role_name, role_code, description) VALUES 
(1, '超级管理员', 'admin', '拥有所有权限'),
(2, '前台接待', 'reception', '负责挂号收费等');

INSERT IGNORE INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- ----------------- 第三阶段核心业务表 -----------------

-- 6. 医生表
CREATE TABLE IF NOT EXISTS biz_doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(64) NOT NULL COMMENT '姓名',
    department VARCHAR(64) NOT NULL COMMENT '所属科室',
    title VARCHAR(64) COMMENT '职称',
    specialty VARCHAR(255) COMMENT '擅长方向',
    introduction TEXT COMMENT '简介',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='医生信息表';

-- 7. 医生排班档期表
CREATE TABLE IF NOT EXISTS biz_schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL COMMENT '医生ID',
    schedule_date DATE NOT NULL COMMENT '排班日期',
    time_slot VARCHAR(32) NOT NULL COMMENT '时间段，如 09:00-10:00',
    max_capacity INT NOT NULL DEFAULT 0 COMMENT '最大接诊量',
    remain_capacity INT NOT NULL DEFAULT 0 COMMENT '剩余可预约量，防并发靠它',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='医生排班档期表';

-- 8. 预约挂号记录表
CREATE TABLE IF NOT EXISTS biz_appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '挂号用户ID',
    pet_id BIGINT COMMENT '就诊宠物ID',
    doctor_id BIGINT NOT NULL COMMENT '医生ID',
    schedule_id BIGINT NOT NULL COMMENT '排班ID',
    status TINYINT DEFAULT 0 COMMENT '状态：0-待就诊，1-已取消，2-已完成',
    visit_time DATETIME COMMENT '实际就诊时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '预约时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='预约挂号记录表';

-- 9. 宠物表
CREATE TABLE IF NOT EXISTS biz_pet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '主人(用户)ID',
    nickname VARCHAR(64) NOT NULL COMMENT '宠物昵称',
    species VARCHAR(64) COMMENT '种类(猫/狗等)',
    breed VARCHAR(64) COMMENT '品种',
    age DECIMAL(4,1) COMMENT '年龄',
    gender TINYINT COMMENT '性别: 0-未知，1-公，2-母',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='宠物基础信息表';

-- 10. 宠物疫苗接种记录表
CREATE TABLE IF NOT EXISTS biz_vaccine_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pet_id BIGINT NOT NULL COMMENT '宠物ID',
    vaccine_name VARCHAR(128) NOT NULL COMMENT '疫苗名称',
    inject_time DATE NOT NULL COMMENT '接种日期',
    next_inject_time DATE COMMENT '下次建议接种日期',
    remarks VARCHAR(255) COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='宠物疫苗接种记录表';

-- 11. 轮播图表
CREATE TABLE IF NOT EXISTS biz_banner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255) NOT NULL COMMENT '图片地址',
    link_url VARCHAR(255) COMMENT '跳转路径',
    sort_no INT DEFAULT 0 COMMENT '排序',
    is_active TINYINT DEFAULT 1 COMMENT '是否展示 1-展示 0-不展示',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='首页轮播图表';

-- 12. 宠物知识文章表
CREATE TABLE IF NOT EXISTS biz_article (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(128) NOT NULL COMMENT '文章标题',
    cover_image VARCHAR(255) COMMENT '封面图',
    summary VARCHAR(255) COMMENT '文章摘要',
    content TEXT NOT NULL COMMENT '正文内容',
    views INT DEFAULT 0 COMMENT '浏览量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB COMMENT='宠物知识科普文章表';
