-- 管理员表（t_admin）
CREATE TABLE `t_admin` (
   `admin_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
   `admin_phone` varchar(50) NOT NULL UNIQUE COMMENT '管理员邮箱',
   `admin_password` varchar(256) NOT NULL COMMENT '管理员密码',
   `admin_name` varchar(50) NOT NULL COMMENT '管理员昵称',
   `is_delete` int DEFAULT '0' COMMENT '0：未删除，1：已删除',
   PRIMARY KEY (`admin_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC ;


-- 店铺表
CREATE TABLE `t_store`(
    `store_id` int NOT NULL  AUTO_INCREMENT COMMENT '店铺id' ,
    `store_uuid` varchar(30) NOT NULL UNIQUE  COMMENT '店铺uuid：伪随机六位数 + 时间戳',
    `store_name` varchar(50) NOT NULL COMMENT '店铺名',
    `store_owner` varchar(50) NOT NULL COMMENT '店铺老板姓名',
    `store_phone` varchar(50) NOT NULL COMMENT '店铺老板手机号',
    `store_address` varchar(128) NOT NULL  COMMENT '店铺地址',
    `store_business_scope` varchar(1024) NOT NULL COMMENT '店铺经营范围',
    `store_remark` varchar(1024) NOT NULL DEFAULT '' COMMENT '店铺备注',
    `store_status` int DEFAULT '0' COMMENT '0：未删除，1：已删除',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`store_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


--operator 店铺管理员表
CREATE TABLE `t_operator` (
   `operator_id` int NOT NULL AUTO_INCREMENT COMMENT '操作员id',
   `operator_belong` varchar(50) NOT NULL  COMMENT '操作员隶属于店铺',
   `operator_level` varchar(256) NOT NULL DEFAULT '店员' COMMENT '操作员等级',
   `operator_phone` varchar(50) NOT NULL COMMENT '操作员账号',
   `operator_salt` varchar(5) NOT NULL DEFAULT '00000' COMMENT '盐值加密',
   `operator_password` varchar(256) NOT NULL  COMMENT '操作员密码',
   `operator_name` varchar(50) NOT NULL DEFAULT  '优秀的店员' COMMENT '操作员姓名',
   `operator_status` int DEFAULT '0' COMMENT '0：未删除，1：已删除',
   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   PRIMARY KEY (`operator_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC ;


-- 顾客表
CREATE TABLE `t_consumer`(
    `consumer_id` int NOT NULL  AUTO_INCREMENT COMMENT '车主id' ,
    `consumer_uuid` varchar(30) NOT NULL UNIQUE  COMMENT '用户uuid：伪随机六位数 + 时间戳',
    `consumer_salt` varchar(5) NOT NULL DEFAULT '00000' COMMENT '盐值加密',
    `consumer_name` varchar(50) NOT NULL  COMMENT '车主',
    `consumer_sex` varchar(50) NOT NULL DEFAULT '男' COMMENT '车主性别',
    `consumer_birthday` varchar(50) NOT NULL DEFAULT '2000-01-01' COMMENT '车主生日',
    `consumer_level` varchar(128) NOT NULL DEFAULT '无等级' COMMENT '车主等级',
    `consumer_phone` varchar(50) NOT NULL UNIQUE COMMENT '（唯一）车主手机号/账号',
    `consumer_password` varchar(50) NOT NULL COMMENT '密码',
    `consumer_status` int NOT NULL DEFAULT '0' COMMENT '是否被删除',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`consumer_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


-- 顾客店铺关系中间表

CREATE TABLE `t_consumer_store`(
    `id` INT NOT NULL AUTO_INCREMENT COMMENT '用户店铺中间表id',
    `consumer_uuid` varchar(30) NOT NULL COMMENT 'consumer表uuid',
    `store_uuid` varchar(30) NOT NULL COMMENT 'store表的uuid',
    PRIMARY KEY (`id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


-- 车辆表
CREATE TABLE `t_car`(
    `car_id` int NOT NULL  AUTO_INCREMENT COMMENT '车辆id' ,
    `car_owner_phone` varchar(50) NOT NULL COMMENT '车主手机号',
    `car_type` varchar(128) NOT NULL  COMMENT '车类型',
    `car_number` varchar(50) NOT NULL  COMMENT '车牌号',
    `car_config_1` varchar(50) NOT NULL COMMENT '车辆配置',
    `car_config_2` varchar(50) NOT NULL COMMENT '车辆配置',
    `car_config_3` varchar(50) NOT NULL COMMENT '车辆配置',
    `car_config_4` varchar(50) NOT NULL COMMENT '车辆配置',
    `car_config_5` varchar(50) NOT NULL COMMENT '车辆配置',
    `car_status` int DEFAULT '0' COMMENT '0为正常状态，1为禁用',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`car_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


-- 订单表
CREATE TABLE `t_order`(
    `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
    `order_uuid` varchar(30) NOT NULL UNIQUE COMMENT '订单序列号',
    `order_consumer_uuid` varchar(30) NOT NULL COMMENT '订单生成的顾客',
    `order_store_uuid` varchar(30) NOT NULL COMMENT '订单请求的店铺',
    `order_car_number` varchar(50) NOT NULL DEFAULT '' COMMENT '预约的车牌号',
    `order_service` varchar(50) NOT NULL  COMMENT '预约的服务',
    `order_status` int NOT NULL DEFAULT '0' COMMENT '0: 预约中，1：取消，2：预约成功，3：操作中，4：订单完成，5：订单关闭',
    `order_note` varchar(1024) COMMENT '订单备注',
    `order_preordain_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预约时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`order_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


-- 消息通知表
CREATE TABLE `t_notice`(
    `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '消息通知id',
    `notice_uuid` varchar(30) NOT NULL UNIQUE COMMENT '生成的唯一编号，伪随机六位数+时间戳',
    `notice_from` varchar(30) NOT NULL DEFAULT 0 COMMENT '消息通知的发起者，店铺或者用户的uuid',
    `notice_to` varchar(30) NOT NULL DEFAULT 0 COMMENT '接收者，店铺或者用户的uuid',
    `notice_title` varchar(30) NOT NULL DEFAULT '' COMMENT '消息通知的标题',
    `notice_content` varchar(1024) NOT NULL DEFAULT '' COMMENT '消息通知的内容',
    `notice_status` int NOT NULL DEFAULT 0 COMMENT '消息通知状态：0：未确认，1：已确认',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`notice_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


-- 顾客余额表（钱和服务）
CREATE TABLE `t_consumer_balance`(
     `balance_id` int NOT NULL  AUTO_INCREMENT COMMENT '顾客id',
     `balance_store_uuid` varchar (30) NOT NULL  COMMENT '顾客当前绑定的店铺id',
     `balance_owner_phone` varchar(50) NOT NULL  COMMENT '顾客手机号',
     `balance_money` int  DEFAULT '0' COMMENT '在当前店铺顾客拥有金额',
     `balance_service_1` int  DEFAULT '0' COMMENT '服务1',
     `balance_service_2` int  DEFAULT '0' COMMENT '服务2',
     `balance_service_3` int  DEFAULT '0' COMMENT '服务3',
     `balance_service_4` int  DEFAULT '0' COMMENT '服务4',
     `balance_service_5` int  DEFAULT '0' COMMENT '服务5',
     `balance_status` int  DEFAULT '0' COMMENT '是否被删除',
     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     PRIMARY KEY (`balance_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


--店铺管理员充值记录
CREATE TABLE `t_balance_log`(
    `balance_log_id` int NOT NULL  AUTO_INCREMENT COMMENT '车主id' ,
    `balance_log_store` varchar (30) NOT NULL  COMMENT '顾客当前绑定的店铺id',
    `balance_log_phone` varchar(50) NOT NULL COMMENT '消费充值车主的手机',
    `balance_log_money` int DEFAULT '0' COMMENT '消耗充值的服务类型',
    `balance_log_service1` int DEFAULT '0' COMMENT '消耗充值的服务类型',
    `balance_log_service2` int DEFAULT '0' COMMENT '',
    `balance_log_service3` int DEFAULT '0' COMMENT '',
    `balance_log_service4` int DEFAULT '0' COMMENT '',
    `balance_log_service5` int DEFAULT '0' COMMENT '',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`balance_log_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


-- 订单表
CREATE TABLE `t_order`(
    `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单序号',
    `order_num` varchar(50) NOT NULL COMMENT '订单号',
    `order_sponsor_phone` varchar(50) NOT NULL COMMENT '订单发起人手机号',
    `order_sponsor_name` varchar (50) NOT NULL COMMENT '订单发起人姓名',
    `order_service` varchar (50) NOT NUll COMMENT '需要的服务',
    `order_desc` varchar (128) DEFAULT '' COMMENT '情况描述',
    `order_status` int DEFAULT 0 COMMENT '订单状态，0: 预约中，1: 预约成功，2：服务中，3：服务完成，4：订单结束',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
)

-- 产品原料
CREATE TABLE `t_product`(
    `product_id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
    `store_uuid` varchar (30) NOT NULL  COMMENT '绑定的店铺id',
    `product_uuid` varchar(30) NOT NULL  COMMENT '唯一编号',
    `product_name` varchar(30) NOT NULL COMMENT '产品名称',
    `product_quantity` int NOT NULL COMMENT '产品数量',
    `product_unit` varchar(30) NOT NULL COMMENT '产品单位',
    `product_price` float(9,2) NOT NULL COMMENT '产品价格',
    `product_in` varchar(30) COMMENT '产品供应商',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`product_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


-- 产品消耗记录
CREATE TABLE `t_product_log`(
    `product_log_id` int NOT NULL AUTO_INCREMENT COMMENT '序号',
    `store_uuid` varchar (30) NOT NULL  COMMENT '绑定的店铺id',
    `product_log_uuid` varchar(30) NOT NULL  COMMENT '唯一编号',
    `product_log_name` varchar(30) NOT NULL COMMENT '产品名称',
    `product_log_quantity` int NOT NULL COMMENT '产品数量',
    `product_log_unit` varchar(30) NOT NULL COMMENT '产品单位',
    `product_log_price` float(9,2) NOT NULL COMMENT '产品价格',
    `product_log_type` varchar(30) NOT NULL COMMENT '增加or消耗',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`product_log_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;


--人员变动记录
CREATE TABLE `t_consumer_log`(
                                 `consumer_log_id` int NOT NULL  AUTO_INCREMENT COMMENT '车主id' ,
                                 `consumer_log_phone` varchar(50) NOT NULL COMMENT '消费车主的手机',
                                 `consumer_log_type` varchar(50) NOT NULL COMMENT '顾客增加还是删除',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (`consumer_log_id`)
) ENGINE = InnoDb DEFAULT CHARSET=utf8 ROW_FORMAT = DYNAMIC;





