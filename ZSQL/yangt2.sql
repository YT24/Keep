
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

 Date: 22/03/2023 17:20:59
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- ----------------------------
-- Records of branch_table
-- ----------------------------
BEGIN;
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

-- ----------------------------
-- Records of global_table
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for keep_access_token
-- ----------------------------
DROP TABLE IF EXISTS `keep_access_token`;
CREATE TABLE `keep_access_token` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `device_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型',
  `last_time_used` int DEFAULT NULL COMMENT '上次使用时间',
  `time_to_live` int DEFAULT NULL COMMENT '存活时间（有效期），单位s：\ncreate_time + time_to_live  <  当前时间  有效',
  `time_to_die` int DEFAULT NULL COMMENT '死亡时间，单位s：\nlast_time_used + time_to_live  <  当前时间',
  `tgt_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'tgt_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `service_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户token存储表';

-- ----------------------------
-- Records of keep_access_token
-- ----------------------------
BEGIN;
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMjAgMDg6NDg6MjItMy0x', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTIwIDA4OjQ4OjIyLTQtMA==', '2023-03-20 08:48:23', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMjEgMTQ6NDI6MDItMy0x', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTIxIDE0OjQyOjAxLTQtMA==', '2023-03-21 14:42:02', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMjEgMTY6MDU6MTgtMy0x', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTIxIDE2OjA1OjE4LTQtMA==', '2023-03-21 16:05:19', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMTUgMDk6MjI6MTItMy0z', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE1IDA5OjIyOjEyLTQtMg==', '2023-03-15 09:22:13', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMTUgMDk6MTU6NDctMy0w', 'yangt1', 'MAC', NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTA5IDA4OjMyOjM4LTQtMA==', '2023-03-15 09:15:48', '12345dwef-APP');
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMTUgMTE6NTM6MDktMy0x', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE1IDExOjUzOjA5LTQtMA==', '2023-03-15 11:53:09', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6Mjg6MzgtMy0xMA==', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjI4OjM4LTQtOQ==', '2023-03-16 08:28:38', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6MjI6MDYtMy00', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjIyOjA2LTQtMw==', '2023-03-16 08:22:06', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6MjU6NTEtMy03', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjI1OjUxLTQtNg==', '2023-03-16 08:25:51', NULL);
INSERT INTO `keep_access_token` VALUES ('AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6MTk6MDgtMy0x', 'yangt1', NULL, NULL, 7200, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjE5OjA4LTQtMA==', '2023-03-16 08:19:09', NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_client
-- ----------------------------
DROP TABLE IF EXISTS `keep_client`;
CREATE TABLE `keep_client` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_name` varchar(64) DEFAULT NULL COMMENT '服务名',
  `client_id` varchar(64) DEFAULT NULL COMMENT 'client_id',
  `client_secret` varchar(64) DEFAULT NULL COMMENT 'client_create',
  `callback_url` varchar(255) DEFAULT NULL COMMENT '应用回调地址',
  `protocol` varchar(32) DEFAULT NULL COMMENT '协议：cas,oauth2.0,oidc,saml,ltpa',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8556551 DEFAULT CHARSET=utf8mb4  COMMENT='应用表';

-- ----------------------------
-- Records of keep_client
-- ----------------------------
BEGIN;
INSERT INTO `keep_client` VALUES (8556545, 'QQ', 'werwdef', 'dewrfedwrf', 'http://qq.com/callback', 'Oauth2.0', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_dept
-- ----------------------------
DROP TABLE IF EXISTS `keep_dept`;
CREATE TABLE `keep_dept` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '部门名称',
  `create_time` datetime DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户部门表';

-- ----------------------------
-- Records of keep_dept
-- ----------------------------
BEGIN;
INSERT INTO `keep_dept` VALUES (3, '管理部', '2022-12-19 09:30:00', NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_menu
-- ----------------------------
DROP TABLE IF EXISTS `keep_menu`;
CREATE TABLE `keep_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int DEFAULT NULL COMMENT '父节点，第一层为0',
  `menu_order` int DEFAULT NULL COMMENT '排序，升序排列，1，2，3',
  `url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '路由地址',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单表';

-- ----------------------------
-- Records of keep_menu
-- ----------------------------
BEGIN;
INSERT INTO `keep_menu` VALUES (1, '应用管理', NULL, 1, NULL, '2023-03-20 08:57:45', NULL, '2023-03-20 08:57:49', NULL, NULL);
INSERT INTO `keep_menu` VALUES (2, '用户管理', NULL, 2, NULL, '2023-03-20 09:11:58', NULL, '2023-03-20 09:12:05', NULL, NULL);
INSERT INTO `keep_menu` VALUES (3, '角色管理', NULL, 3, NULL, '2023-03-20 09:18:04', NULL, '2023-03-20 09:18:07', NULL, NULL);
INSERT INTO `keep_menu` VALUES (4, '应用列表', 1, 1, 'clients', '2023-03-21 17:25:18', NULL, '2023-03-21 17:25:21', NULL, NULL);
INSERT INTO `keep_menu` VALUES (5, '用户列表', 2, 1, 'users', '2023-03-21 17:25:54', NULL, '2023-03-21 17:25:56', NULL, NULL);
INSERT INTO `keep_menu` VALUES (6, '角色列表', 3, 1, 'roles', '2023-03-22 11:05:21', NULL, '2023-03-22 11:05:24', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_menu_operation
-- ----------------------------
DROP TABLE IF EXISTS `keep_menu_operation`;
CREATE TABLE `keep_menu_operation` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `menu_id` int DEFAULT NULL COMMENT '菜单id',
  `operation_id` int DEFAULT NULL COMMENT '操作项id',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单关联操作项表';

-- ----------------------------
-- Records of keep_menu_operation
-- ----------------------------
BEGIN;
INSERT INTO `keep_menu_operation` VALUES (9, 1, 1, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (10, 1, 2, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (11, 1, 3, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (12, 1, 4, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (13, 2, 9, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (14, 2, 10, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (15, 2, 11, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (16, 2, 12, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (17, 3, 5, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (18, 3, 6, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (19, 3, 7, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
INSERT INTO `keep_menu_operation` VALUES (20, 3, 8, '2023-03-22 11:06:21', NULL, '2023-03-22 11:06:24', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_operation
-- ----------------------------
DROP TABLE IF EXISTS `keep_operation`;
CREATE TABLE `keep_operation` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单操作项名称',
  `operation_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单操作项url',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='菜单对应操作项表';

-- ----------------------------
-- Records of keep_operation
-- ----------------------------
BEGIN;
INSERT INTO `keep_operation` VALUES (1, '应用新增', 'keep:client:add', '2023-03-22 10:37:03', NULL, '2023-03-22 10:37:06', NULL, NULL);
INSERT INTO `keep_operation` VALUES (2, '应用列表', 'keep:clent:list', '2023-03-22 10:37:59', NULL, '2023-03-22 10:38:02', NULL, NULL);
INSERT INTO `keep_operation` VALUES (3, '应用编辑', 'keep:client:edit', '2023-03-22 10:38:34', NULL, '2023-03-22 10:38:38', NULL, NULL);
INSERT INTO `keep_operation` VALUES (4, '应用删除', 'keep: client:del', '2023-03-22 10:40:44', NULL, '2023-03-22 10:40:47', NULL, NULL);
INSERT INTO `keep_operation` VALUES (5, '角色新增', 'keep:role:add', '2023-03-22 10:37:03', NULL, '2023-03-22 10:37:06', NULL, NULL);
INSERT INTO `keep_operation` VALUES (6, '角色列表', 'keep:role:list', '2023-03-22 10:37:59', NULL, '2023-03-22 10:38:02', NULL, NULL);
INSERT INTO `keep_operation` VALUES (7, '角色编辑', 'keep:role:edit', '2023-03-22 10:38:34', NULL, '2023-03-22 10:38:38', NULL, NULL);
INSERT INTO `keep_operation` VALUES (8, '角色删除', 'keep:role:del', '2023-03-22 10:40:44', NULL, '2023-03-22 10:40:47', NULL, NULL);
INSERT INTO `keep_operation` VALUES (9, '用户新增', 'keep:user:add', '2023-03-22 10:37:03', NULL, '2023-03-22 10:37:06', NULL, NULL);
INSERT INTO `keep_operation` VALUES (10, '用户列表', 'keep:user:list', '2023-03-22 10:37:59', NULL, '2023-03-22 10:38:02', NULL, NULL);
INSERT INTO `keep_operation` VALUES (11, '用户编辑', 'keep:user:edit', '2023-03-22 10:38:34', NULL, '2023-03-22 10:38:38', NULL, NULL);
INSERT INTO `keep_operation` VALUES (12, '用户删除', 'keep:user:del', '2023-03-22 10:40:44', NULL, '2023-03-22 10:40:47', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `keep_refresh_token`;
CREATE TABLE `keep_refresh_token` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `device_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型',
  `last_time_used` int DEFAULT NULL COMMENT '上次使用时间',
  `time_to_live` int DEFAULT NULL COMMENT '存活时间（有效期），单位s：\ncreate_time + time_to_live  <  当前时间  有效',
  `time_to_die` int DEFAULT NULL COMMENT '死亡时间，单位s：\nlast_time_used + time_to_live  <  当前时间',
  `tgt_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'tgt_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `service_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户refreshToken存储表';

-- ----------------------------
-- Records of keep_refresh_token
-- ----------------------------
BEGIN;
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMjAgMDg6NDg6MjItMy0y', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTIwIDA4OjQ4OjIyLTQtMA==', '2023-03-20 08:48:23', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMjEgMTQ6NDI6MDItMy0y', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTIxIDE0OjQyOjAxLTQtMA==', '2023-03-21 14:42:02', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMjEgMTY6MDU6MTgtMy0y', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTIxIDE2OjA1OjE4LTQtMA==', '2023-03-21 16:05:19', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMTUgMDk6MjI6MTItMy00', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE1IDA5OjIyOjEyLTQtMg==', '2023-03-15 09:22:13', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMTUgMDk6MTU6NDctMy0x', 'yangt1', 'MAC', NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTA5IDA4OjMyOjM4LTQtMA==', '2023-03-15 09:15:48', '12345dwef-APP');
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMTUgMTE6NTM6MDktMy0y', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE1IDExOjUzOjA5LTQtMA==', '2023-03-15 11:53:09', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6Mjg6MzgtMy0xMQ==', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjI4OjM4LTQtOQ==', '2023-03-16 08:28:38', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6MjI6MDYtMy01', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjIyOjA2LTQtMw==', '2023-03-16 08:22:06', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6MjU6NTEtMy04', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjI1OjUxLTQtNg==', '2023-03-16 08:25:51', NULL);
INSERT INTO `keep_refresh_token` VALUES ('RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6MTk6MDgtMy0y', 'yangt1', NULL, NULL, 604800, NULL, 'TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjE5OjA4LTQtMA==', '2023-03-16 08:19:09', NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_role
-- ----------------------------
DROP TABLE IF EXISTS `keep_role`;
CREATE TABLE `keep_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色表';

-- ----------------------------
-- Records of keep_role
-- ----------------------------
BEGIN;
INSERT INTO `keep_role` VALUES (1, '角色A', '2023-03-22 16:33:14', NULL, '2023-03-22 16:33:17', NULL, NULL);
INSERT INTO `keep_role` VALUES (2, '角色B', '2023-03-22 16:33:29', NULL, '2023-03-22 16:33:31', NULL, NULL);
INSERT INTO `keep_role` VALUES (3, '角色C', '2023-03-22 16:33:42', NULL, '2023-03-22 16:33:45', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for keep_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `keep_role_permission`;
CREATE TABLE `keep_role_permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `permission_id` int NOT NULL COMMENT '菜单ID或者操作项ID',
  `permission_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '权限类型 menu, operation',
  `role_id` int NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_id` (`permission_id`,`role_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='角色关联权限表';

-- ----------------------------
-- Records of keep_role_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for keep_role_user
-- ----------------------------
DROP TABLE IF EXISTS `keep_role_user`;
CREATE TABLE `keep_role_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `role_id` int NOT NULL COMMENT '角色ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `deleted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_id` (`role_id`,`user_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户角色关联用户表';

-- ----------------------------
-- Records of keep_role_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for keep_tgt_token
-- ----------------------------
DROP TABLE IF EXISTS `keep_tgt_token`;
CREATE TABLE `keep_tgt_token` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `device_type` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设备类型',
  `last_time_used` datetime DEFAULT NULL COMMENT '上次使用时间',
  `time_to_live` int DEFAULT NULL COMMENT '存活时间（有效期），单位s：\ncreate_time + time_to_live  <  当前时间  有效',
  `time_to_die` int DEFAULT NULL COMMENT '死亡时间，单位s：\nlast_time_used + time_to_live  <  当前时间',
  `descendant_tickets` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '后代ticket（AT，RT）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `service_id` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '服务ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户tgt存储表';

-- ----------------------------
-- Records of keep_tgt_token
-- ----------------------------
BEGIN;
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTA5IDA4OjMyOjM4LTQtMA==', 'yangt1', 'MAC', '2023-03-09 08:32:38', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMTUgMDk6MTU6NDctMy0w\",\"RT-UlQtLVYxLTIwMjMtMDMtMTUgMDk6MTU6NDctMy0x\"]', '2023-03-09 08:32:38', '12345dwef-APP');
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTE1IDA5OjIyOjEyLTQtMg==', 'yangt1', NULL, '2023-03-15 09:22:13', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMTUgMDk6MjI6MTItMy0z\",\"RT-UlQtLVYxLTIwMjMtMDMtMTUgMDk6MjI6MTItMy00\"]', '2023-03-15 09:22:13', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTE1IDExOjUzOjA5LTQtMA==', 'yangt1', NULL, '2023-03-15 11:53:09', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMTUgMTE6NTM6MDktMy0x\",\"RT-UlQtLVYxLTIwMjMtMDMtMTUgMTE6NTM6MDktMy0y\"]', '2023-03-15 11:53:09', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjE5OjA4LTQtMA==', 'yangt1', NULL, '2023-03-16 08:19:08', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6MTk6MDgtMy0x\",\"RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6MTk6MDgtMy0y\"]', '2023-03-16 08:19:08', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjI1OjUxLTQtNg==', 'yangt1', NULL, '2023-03-16 08:25:51', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6MjU6NTEtMy03\",\"RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6MjU6NTEtMy04\"]', '2023-03-16 08:25:51', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjI4OjM4LTQtOQ==', 'yangt1', NULL, '2023-03-16 08:28:38', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6Mjg6MzgtMy0xMA==\",\"RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6Mjg6MzgtMy0xMQ==\"]', '2023-03-16 08:28:38', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTE2IDA4OjIyOjA2LTQtMw==', 'yangt1', NULL, '2023-03-16 08:22:06', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMTYgMDg6MjI6MDYtMy00\",\"RT-UlQtLVYxLTIwMjMtMDMtMTYgMDg6MjI6MDYtMy01\"]', '2023-03-16 08:22:06', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTIwIDA4OjQ4OjIyLTQtMA==', 'yangt1', NULL, '2023-03-20 08:48:22', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMjAgMDg6NDg6MjItMy0x\",\"RT-UlQtLVYxLTIwMjMtMDMtMjAgMDg6NDg6MjItMy0y\"]', '2023-03-20 08:48:22', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTIxIDE0OjQyOjAxLTQtMA==', 'yangt1', NULL, '2023-03-21 14:42:02', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMjEgMTQ6NDI6MDItMy0x\",\"RT-UlQtLVYxLTIwMjMtMDMtMjEgMTQ6NDI6MDItMy0y\"]', '2023-03-21 14:42:02', NULL);
INSERT INTO `keep_tgt_token` VALUES ('TGT-VEdULS1WMS0yMDIzLTAzLTIxIDE2OjA1OjE4LTQtMA==', 'yangt1', NULL, '2023-03-21 16:05:18', 2592000, NULL, '[\"AT-QVQtLVYxLTIwMjMtMDMtMjEgMTY6MDU6MTgtMy0x\",\"RT-UlQtLVYxLTIwMjMtMDMtMjEgMTY6MDU6MTgtMy0y\"]', '2023-03-21 16:05:18', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
