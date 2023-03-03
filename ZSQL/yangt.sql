/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : yangt

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 03/03/2023 17:00:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for branch_table
-- ----------------------------
DROP TABLE IF EXISTS `branch_table`;
CREATE TABLE `branch_table` (
  `branch_id` bigint NOT NULL,
  `xid` varchar(128) NOT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `resource_group_id` varchar(32) DEFAULT NULL,
  `resource_id` varchar(256) DEFAULT NULL,
  `branch_type` varchar(8) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `client_id` varchar(64) DEFAULT NULL,
  `application_data` varchar(2000) DEFAULT NULL,
  `gmt_create` datetime(6) DEFAULT NULL,
  `gmt_modified` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`branch_id`),
  KEY `idx_xid` (`xid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for global_table
-- ----------------------------
DROP TABLE IF EXISTS `global_table`;
CREATE TABLE `global_table` (
  `xid` varchar(128) NOT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `status` tinyint NOT NULL,
  `application_id` varchar(32) DEFAULT NULL,
  `transaction_service_group` varchar(32) DEFAULT NULL,
  `transaction_name` varchar(128) DEFAULT NULL,
  `timeout` int DEFAULT NULL,
  `begin_time` bigint DEFAULT NULL,
  `application_data` varchar(2000) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`xid`),
  KEY `idx_status_gmt_modified` (`status`,`gmt_modified`),
  KEY `idx_transaction_id` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for keep_access_token
-- ----------------------------
DROP TABLE IF EXISTS `keep_access_token`;
CREATE TABLE `keep_access_token` (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `device_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型',
  `last_time_used` int DEFAULT NULL COMMENT '上次使用时间',
  `time_to_live` int DEFAULT NULL COMMENT '存活时间（有效期），单位s：\ncreate_time + time_to_live  <  当前时间  有效',
  `time_to_die` int DEFAULT NULL COMMENT '死亡时间，单位s：\nlast_time_used + time_to_live  <  当前时间',
  `tgt_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'tgt_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `service_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for keep_dept
-- ----------------------------
DROP TABLE IF EXISTS `keep_dept`;
CREATE TABLE `keep_dept` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for keep_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `keep_refresh_token`;
CREATE TABLE `keep_refresh_token` (
  `id` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `device_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型',
  `last_time_used` int DEFAULT NULL COMMENT '上次使用时间',
  `time_to_live` int DEFAULT NULL COMMENT '存活时间（有效期），单位s：\ncreate_time + time_to_live  <  当前时间  有效',
  `time_to_die` int DEFAULT NULL COMMENT '死亡时间，单位s：\nlast_time_used + time_to_live  <  当前时间',
  `tgt_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'tgt_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `service_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for keep_tgt_token
-- ----------------------------
DROP TABLE IF EXISTS `keep_tgt_token`;
CREATE TABLE `keep_tgt_token` (
  `id` varchar(11) COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `device_type` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型',
  `last_time_used` int DEFAULT NULL COMMENT '上次使用时间',
  `time_to_live` int DEFAULT NULL COMMENT '存活时间（有效期），单位s：\ncreate_time + time_to_live  <  当前时间  有效',
  `time_to_die` int DEFAULT NULL COMMENT '死亡时间，单位s：\nlast_time_used + time_to_live  <  当前时间',
  `descendant_tickets` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '后代ticket（AT，RT）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for keep_token
-- ----------------------------
DROP TABLE IF EXISTS `keep_token`;
CREATE TABLE `keep_token` (
  `id` int NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'token',
  `expired_time` int DEFAULT NULL COMMENT '过期时间戳，毫秒值',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for keep_user
-- ----------------------------
DROP TABLE IF EXISTS `keep_user`;
CREATE TABLE `keep_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `real_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `must_change_password` tinyint(1) DEFAULT '0',
  `updated_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `dept_id` int DEFAULT NULL,
  `order` int DEFAULT NULL COMMENT '序号',
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  KEY `dept_id` (`dept_id`) USING BTREE,
  KEY `phone_email` (`mobile`,`email`) USING BTREE,
  KEY `create_time` (`created_time`) USING BTREE,
  KEY `order` (`order` DESC) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5000022 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for lock_table
-- ----------------------------
DROP TABLE IF EXISTS `lock_table`;
CREATE TABLE `lock_table` (
  `row_key` varchar(128) NOT NULL,
  `xid` varchar(128) DEFAULT NULL,
  `transaction_id` bigint DEFAULT NULL,
  `branch_id` bigint NOT NULL,
  `resource_id` varchar(256) DEFAULT NULL,
  `table_name` varchar(32) DEFAULT NULL,
  `pk` varchar(36) DEFAULT NULL,
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '0:locked ,1:rollbacking',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`row_key`),
  KEY `idx_status` (`status`),
  KEY `idx_branch_id` (`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码 MD5值',
  PRIMARY KEY (`id`),
  KEY `index_username_password` (`username`,`password`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Procedure structure for creat_user_data
-- ----------------------------
DROP PROCEDURE IF EXISTS `creat_user_data`;
delimiter ;;
CREATE PROCEDURE `creat_user_data`()
BEGIN
	DECLARE
		has_data INT DEFAULT 1;
	REPEAT
			INSERT INTO sys_user ( username, `password`, real_name, mobile, email, dept_id,create_time )
		VALUES
			( getStrUsername ( has_data ), '1qaz@WSX', getStrRealname ( has_data ), getStrPhone ( has_data ), getStrEmail ( has_data ), has_data % 5 ,NOW() - INTERVAL 5 MINUTE);
			SET has_data = has_data + 1;
		UNTIL has_data = 5000000 
	END REPEAT;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getStrEmail
-- ----------------------------
DROP FUNCTION IF EXISTS `getStrEmail`;
delimiter ;;
CREATE FUNCTION `getStrEmail`(id BIGINT)
 RETURNS varchar(50) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
  DECLARE phoneStr VARCHAR(255) DEFAULT '';
	DECLARE phone BIGINT DEFAULT 13000000000;
	set phone = phone + id;
	set phoneStr = CONCAT(phone,'@163.com');
  return phoneStr;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getStrPhone
-- ----------------------------
DROP FUNCTION IF EXISTS `getStrPhone`;
delimiter ;;
CREATE FUNCTION `getStrPhone`(id BIGINT)
 RETURNS varchar(50) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
  DECLARE phoneStr VARCHAR(255) DEFAULT '';
	DECLARE phone BIGINT DEFAULT 13000000000;
	set phone = phone + id;
	set phoneStr = CONCAT(phone);
  return phone;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getStrRealname
-- ----------------------------
DROP FUNCTION IF EXISTS `getStrRealname`;
delimiter ;;
CREATE FUNCTION `getStrRealname`(str int)
 RETURNS varchar(50) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
  DECLARE strName VARCHAR(50) DEFAULT '';
	set strName = CONCAT('测试',CAST(str as char(10)));
  return strName;
END
;;
delimiter ;

-- ----------------------------
-- Function structure for getStrUsername
-- ----------------------------
DROP FUNCTION IF EXISTS `getStrUsername`;
delimiter ;;
CREATE FUNCTION `getStrUsername`(str int)
 RETURNS varchar(50) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
  DECLARE strName VARCHAR(50) DEFAULT '';
	set strName = CONCAT('yangt',CAST(str as char(10)));
  return strName;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
