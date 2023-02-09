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

 Date: 06/02/2023 09:02:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
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
) ENGINE=InnoDB AUTO_INCREMENT=5000007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码 MD5值',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '用户状态： 0: 正常，1: 删除',
  PRIMARY KEY (`id`),
  KEY `create_date` (`create_time`) USING BTREE,
  KEY `index_username_password` (`username`,`password`) USING BTREE,
  KEY `index_age_create_date` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
