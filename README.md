# 商品管理系统

课设实训项目，有点敷衍，主要满足尽可能使用SQL的数据库CURD操作

### 本服务(Springboot)相关配置:
- Springboot版本: 2.6.1
- HTTP端口: 8080
- HTTP服务：SpringMVC
- 前端页面模板引擎：Thymeleaf
- 数据库连接服务： Mybatis-Plus
- 数据库连接配置文件： /src/main/resources/application.yml
- 前端静态页面：/src/main/resources/static/*
- 前端动态页面: /src/main/resources/templates/*
- Mybatis-Plus Mapper: /src/main/resources/mapper/*

### 使用配套应用/框架：
1. MySQL 8.0.29(数据库)
2. 基于Java的Springboot框架(后端服务)
3. 基于Java的Thymeleaf模板引擎(Springboot内，渲染前端页面)

### 目录/文件介绍

*src/main/java部分(Java代码)*

- controller: 响应http请求的控制器类，分商品相关，用户相关，文件相关
- entity: 各种Java实体类，包括用于前端渲染的GoodsVO,UserVO和用于前端提交商品修改的GoodsUpload
- mapper: 数据库操作对象类，也就是DAO(Data Access Object)，三个数据表各一个
- model: 数据库模型类，用于操作数据库，三个数据表各一个
- service：服务类，把数据库操作对象获取到的数据加工后提供给Controller(比如把mapper获取到的Goods对象转换为GoodsVO)
- service/impl: service类先定义了接口(接口：没有代码体，只定义了方法名，形参，返回值类型，必须@Override实现后才可用)，之后在impl类实现

*src/main/resource部分(静态资源)*

- mapper: 存储mapper类对应的xml文件(里面是mapper里定义方法的sql命令)
- static: 不需要模板引擎渲染的静态html文件(本项目只有一个index.html)
- templates: Thymeleaf模板引擎用来渲染的html模板，分goods、user两部分以及一个tip.html
- application.yml: Springboot程序的配置文件，包括http端口，数据库配置等

### 数据库设计:

- 表名：GOODS

|          字段          |     数据类型      |非空|      特性       |            用途            |
|:--------------------:|:-------------:|:---:|:-------------:|:------------------------:|
|         gid          |      int      |true|    主键，自动增长    |           商品ID           |
|         name         |  varchar(30)  |true|      索引       |           商品名            |
|       img_fid        |      int      |true|               |    商品图的fid(files表主键)     |
|        price         |     float     |true|               |           商品价格           |
|       details        | varchar(4000) |true|               |           商品详情           |
|     details_type     |  varchar(10)  |true|               | 商品详情的文本格式(markdown/text) |
|    create_userid     |      int      |true| 外键(users-uid) |           创建用户           |
|    last_modified     |   timestamp   |true|               |          最后修改时间          |
| last_modified_userid |      int      |true|               |          最后修改用户          |
|    classification    |  varchar(30)  |true|               |           商品分类           |
|        status        |  varchar(10)  |true|               |     商品上下架状态(up/down)     |
|      inventory       |      int      |true|               |          商品库存数           |

- 表名： USERS

|字段|     数据类型      |非空|特性|用途|
|:---:|:-------------:|:---:|:---:|:---:|
|uid|      int      |true|主键，自动增长|商品ID|
|username|  varchar(30)  |true|索引|用户名|
|password|  varchar(50)  |true| |密码|
|avatar_fid|      int      |true| |头像fid(files表的主键)|
|last_login|   timestamp   |true| |最后登录时间|
|details_text| varchar(4000) |true| |用户介绍文本|


- 表名：FILES

|字段|    数据类型     |非空|特性|用途|
|:---:|:-----------:|:---:|:---:|:---:|
|fid|     int     |true|主键，自动增长|资源ID|
|hash| varchar(32) |true|索引|资源哈希值，防止重复资源|
|file_blob|  longblob   |true|二进制数据|资源二进制数据|

### 数据库建表语句

**建立用户及数据库，并授权：**

*请在root用户下执行*

```SQL
create database goods_manage;
create user 'user'@'localhost' IDENTIFIED BY '123456';
grant all on goods_manage.* to 'user'@'localhost';
```


**GOODS表：**

```SQL
create table goods
(
    gid                  int auto_increment
        primary key,
    name                 varchar(30)   not null,
    img_fid              int           not null,
    price                float         not null,
    details              varchar(4000) not null,
    details_type         varchar(10)   not null,
    create_userid        int           not null,
    last_modified        timestamp     not null,
    last_modified_userid int           not null,
    classification       varchar(30)   not null,
    status               varchar(10)   not null,
    inventory            int           not null
);

create index GOODS_name_index
    on goods (name);

create index goods_users_uid_fk
    on goods (create_userid);


```

**USERS表：**

```SQL
create table users
(
    sid          int auto_increment
        primary key,
    username     varchar(30)   not null,
    password     varchar(50)   not null,
    avatar_fid   int           not null,
    last_login   timestamp     not null,
    details_text varchar(4000) not null
);
```
**FILES表：**

```SQL
create table files
(
    fid       int auto_increment
        primary key,
    hash      varchar(255) not null,
    file_blob longblob     not null
);
```
测试用途，打开了未登录时注册功能(本来应该是只有管理者登录后才有注册权限)

### 最后运行：
请确认你的电脑已经联网，然后等待IDEA自动构建(根据网速不同约3-5分钟)，因为Gradle会自动下载需要的包并导入。