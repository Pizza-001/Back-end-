# 宠物医院后台管理系统 (API 文档)

本文档整理了目前后端系统已实现的核心模块接口，包括安全鉴权、核心业务模型及 AI WebSocket 长连接。

---

## 1. 鉴权与安全模块 (Auth API)

**基础路径**: `/auth`  
**认证要求**: 无需 Token (除 `/permissions` 接口)

### 1.1 密码登录
* **路径**: `/login` (POST)

### 1.2 注册新用户
* **路径**: `/register` (POST)
* **参数**: `{"username": "test", "password": "123", "phone": "138000"}`

### 1.3 获取当前用户信息与权限
* **路径**: `/profile` (GET) (需 Token，返回脱敏基础信息)
* **路径**: `/permissions` (GET) (需 Token，返回关联角色菜单树)

---

## 2. 核心医务与首页展示模块 (Biz API)

**基础路径**: `/biz`  
**认证要求**: 除首页静态大屏数据以外，业务操作建议携带 JWT Token。

### 2.1 首页展示区
* **轮播图**: `/banners` (GET)
* **科普文章列表**: `/articles` (GET)
* **文章详情**: `/articles/{id}` (GET)

### 2.2 医生与排班查询
* **医生列表**: `/doctors` (GET) `?department=选填`
* **医生档期排班**: `/schedules` (GET) `?doctorId=选填` (自动过滤历史过期排班)

### 2.2 医生增删改 (数据管理 API)
* **新增医生**: `/doctor` (POST)
  * 请求体 `{"name":"张医生", "department":"外科", "title":"主任", "specialty":"骨折"}`
* **修改医生**: `/doctor` (PUT)
  * 请求体需包含 `id` 主键及要覆盖的字段。
* **删除医生**: `/doctor/{id}` (DELETE)
  * 路径参数传入要删除的医生ID。

### 2.3 小程序端高并发挂号预约
* **路径**: `/appointment`
* **方法**: `POST`
* **请求方式**: `x-www-form-urlencoded` / `Form-Data` (或根据要求改为 @RequestParam 等均可接收)
* **参数**:
  * `petId`: 绑定的宠物主键
  * `scheduleId`: 要预约的医生档期排班包主键
* **说明**: 该接口已植行级互斥锁设计，强防超发。如果预约档期人数爆满，将捕捉到业务异常错误。

### 2.3 获取我的宠物全量档案 (含疫苗史)
* **路径**: `/pets`
* **方法**: `GET`
* **说明**: 返回当前操作人在我院关联保存的所有宠物，连带着每一只宠物的往年 `vaccineRecords` 都会以数组形式顺带一并返回。

---

## 3. 智能 AI 问诊室 (WebSocket)

**协议**: `ws://`

### 3.1 客户端连接
* **端点**: `ws://<host>:<port>/ai/chat`
* **连接示例** (浏览器原生 JS):
  ```javascript
  const ws = new WebSocket('ws://localhost:8080/ai/chat');
  
  ws.onopen = function(event) {
      console.log('连接成功！AI即将发送欢迎信');
  };
  
  // 发送求助内容
  ws.send("我的猫咪最近食欲总是不太好");
  
  // 接收后端的打字机流式输出序列
  ws.onmessage = function(event) {
      console.log('接收到部分字符流: ' + event.data);
  };
  ```
