USE pet_hospital;

-- 清空原有业务测试数据，避免重复运行报错
TRUNCATE TABLE biz_doctor;
TRUNCATE TABLE biz_schedule;
TRUNCATE TABLE biz_pet;
TRUNCATE TABLE biz_vaccine_record;
TRUNCATE TABLE biz_banner;
TRUNCATE TABLE biz_article;
TRUNCATE TABLE biz_appointment;

-- 1. 插入医生数据
INSERT INTO biz_doctor (id, name, department, title, specialty, introduction) VALUES
(1, '张伟', '内科', '主治医师', '猫狗常见内科疾病、消化系统疾病', '拥有10年宠物临床经验，对小动物内科有深入研究。'),
(2, '李静', '外科', '主任医师', '骨科手术、软组织外科手术', '外科专家，曾主刀上千台复杂手术，技术精湛。'),
(3, '王强', '影像科', '医师', 'B超、X光片解读', '影像学诊断能手，为临床确诊提供关键依据。'),
(4, '赵丽', '特宠科', '主治医师', '兔子、仓鼠、鸟类疾病', '专注于异宠/特宠的医疗保健，经验丰富。');

-- 2. 插入医生排班数据 (给两位医生排最近两天的班)
INSERT INTO biz_schedule (doctor_id, schedule_date, time_slot, max_capacity, remain_capacity) VALUES
(1, CURDATE(), '09:00-10:00', 5, 5),
(1, CURDATE(), '10:00-11:00', 5, 5),
(1, CURDATE(), '14:00-15:00', 5, 5),
(2, CURDATE(), '09:00-10:00', 3, 3),
(2, CURDATE(), '10:00-11:00', 3, 3),
(1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '09:00-10:00', 5, 5),
(2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '14:00-15:00', 3, 3);

-- 3. 插入宠物数据 (归属于 admin, userid=1)
INSERT INTO biz_pet (id, user_id, nickname, species, breed, age, gender) VALUES
(1, 1, '布丁', '猫', '布偶猫', 2.5, 1),
(2, 1, '旺财', '狗', '柴犬', 3.0, 1),
(3, 1, '雪球', '猫', '狮子猫', 1.0, 2);

-- 4. 插入疫苗接种记录
INSERT INTO biz_vaccine_record (id, pet_id, vaccine_name, inject_time, next_inject_time, remarks) VALUES
(1, 1, '猫三联 (妙三多)', DATE_SUB(CURDATE(), INTERVAL 6 MONTH), DATE_ADD(CURDATE(), INTERVAL 6 MONTH), '接种顺利，无不良反应'),
(2, 1, '狂犬疫苗', DATE_SUB(CURDATE(), INTERVAL 6 MONTH), DATE_ADD(CURDATE(), INTERVAL 6 MONTH), '常规驱虫与疫苗'),
(3, 2, '犬八联', DATE_SUB(CURDATE(), INTERVAL 1 YEAR), CURDATE(), '该打下一针了');

-- 5. 插入轮播图数据
INSERT INTO biz_banner (id, image_url, link_url, sort_no, is_active) VALUES
(1, 'https://images.unsplash.com/photo-1548199973-03cce0bbc87b?auto=format&fit=crop&w=1200&q=80', '/articles/1', 1, 1),
(2, 'https://images.unsplash.com/photo-1583337130417-3346a1be7dee?auto=format&fit=crop&w=1200&q=80', '/articles/2', 2, 1),
(3, 'https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=1200&q=80', '', 3, 1);

-- 6. 插入宠物知识文章
INSERT INTO biz_article (id, title, cover_image, summary, content, views) VALUES
(1, '春季宠物养护指南', 'https://images.unsplash.com/photo-1450778869180-41d0601e046e?auto=format&fit=crop&w=800&q=80', '春天到了，该给毛孩子们做哪些防护呢？', '春季是寄生虫活跃的季节，请务必给猫狗按时做好体内外驱虫。另外，春季也是换毛期，需要勤梳毛，预防猫咪毛球症...', 1024),
(2, '猫咪绝育的坏处与好处', 'https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?auto=format&fit=crop&w=800&q=80', '客观分析猫咪绝育的利弊。', '绝育可以降低猫咪患生殖系统疾病的风险（如子宫蓄脓、乳腺肿瘤等），发情期的嚎叫和乱尿行为也会大大减少。缺点是如果术后不控制饮食，容易发胖...', 2048),
(3, '狗粮怎么选？新手必看', '...dog-food.jpg', '市面上狗粮五花八门，到底用什么标准来挑选？', '看配料表第一位是不是肉类，以及肉类的占比...', 356);

-- 7. 插入一条我的预约记录
INSERT INTO biz_appointment (id, user_id, pet_id, doctor_id, schedule_id, status) VALUES
(1, 1, 1, 1, 1, 0);

-- 将第一条排班的剩余容量减1，以对应上面这条预约
UPDATE biz_schedule SET remain_capacity = remain_capacity - 1 WHERE id = 1;

