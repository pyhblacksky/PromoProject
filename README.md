# miaoshaProject
一个秒杀商城——电商相关后端项目
# 项目构建

使用Springboot + MyBatis 构建 JavaWeb项目

项目是电商秒杀基本流程及实现



#### 后端技术选型

|       选型       |        说明        |
| :--------------: | :----------------: |
|   Spring Boot    |    容器+MVC框架    |
|     MyBatis      |      ORM框架       |
| MyBatisGenerator | 数据库代码自动生成 |
|      Druid       |    数据库连接池    |
|      MySql       |      数据存储      |



#### 项目结构

```
com.miaoshaproject
├── controller -- controller层
	└── viewobject -- 前段数据对象
├── dao -- 数据库接口层
├── dataobject -- 数据库对象层
├── error -- 异常信息通用抛出接口
├── response -- 通过返回类型
├── service -- 服务接口及实现
	├── impl -- 接口实现层
	└── model -- 模型层
└── validator -- 相关通用校验方法
```



#### 数据库相关表及字段

导入目录下sqlStruct->miaosha.sql

| 表名          | 字段                                                       | 说明         |
| ------------- | ---------------------------------------------------------- | ------------ |
| item          | id,title,price,description,sales,img_url                   | 商品表       |
| item_stock    | id,stock,item_id                                           | 库存表       |
| order_info    | id,user_id,item_id,item_price_amount,order_price,promo_id  | 订单信息     |
| promo         | id,promo_name,start_date,item_id,promo_item_price,end_date | 活动信息表   |
| sequence_info | name,current,step                                          | 递增序列信息 |
| user_info     | id,name,gender,age,telephone,register_mode,third_party_id  | 用户信息     |
| user_password | id,encrpt_password,user_id                                 | 用户密码     |



#### 整体框架

![前后端分离](https://github.com/pyhblacksky/miaoshaProject/blob/master/img/%E5%89%8D%E5%90%8E%E7%AB%AF%E5%88%86%E7%A6%BB.PNG)



#### 尚未实现的部分

本地缓存，集中式缓存，redis

多商品、多库存、多活动模型尚未实现



#### 存在的问题

如何发现容量问题

如何使系统水平扩展

查询效率低下

活动开始前页面被疯狂刷新

库存行锁问题

下单操作多，缓慢

浪涌流量问题如何解决
