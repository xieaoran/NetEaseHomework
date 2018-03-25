# NetEaseHomework

[https://github.com/xieaoran/NetEaseHomework](https://github.com/xieaoran/NetEaseHomework)

**2018易计划之“猪崽养成记”第二站**

**“校园的修行”通关任务**

由于时间原因，前端尚未实现，该项目为一组业务逻辑完整的 Restful API。

## Demo

[http://netease.xieaoran.com/swagger-ui.html](http://netease.xieaoran.com/swagger-ui.html)

## 已实现的功能

### 用户

- 用户登录
- 用户退出
- 获取当前用户的信息
- 新增测试用户

### 商品

- 获取所有商品的基本信息
- 获取未购买商品的基本信息
- 获取指定商品的详细信息
- 新增/更新商品
- 删除商品

### 商品快照

- 获取指定商品所有快照的基本信息
- 获取指定商品指定快照的详细信息

### 购物车

- 获取所有购物车项目的信息
- 新增/更新购物车项目
- 删除购物车项目
- 购物车结算

### 已购买商品

- 获取所有已购买项目的信息

### 上传

- 上传文件

## 运行环境

- JDK 1.8.0_162
- Spring Boot 2.0.0.RELEASE
- MySQL 5.7.21

## 部署方式

项目使用 Maven 构建。

请在按需修改 `pom.xml` 文件中 `packaging` 方式后，使用 `mvn package` 命令打包并部署。

## API 文档

项目使用 Swagger 记录 API 文档。

请在部署项目后，打开根目录下的 `swagger-ui.html` 文件以获取详细 API 文档。