/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : adminchat

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 02/10/2019 02:21:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chat_uuid` bigint(20) NOT NULL COMMENT '会话Uuid',
  `chat_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会话名字64',
  `chat_csuse_uuid` bigint(20) NOT NULL COMMENT '会话创建者Uuid',
  `chat_public` bit(1) NOT NULL DEFAULT b'0' COMMENT '会话是否公开 0：公开 1：不公开',
  `chat_type` bit(2) NOT NULL COMMENT '会话类型0：系统会话 1：一对一会话 2：多人会话',
  `chat_count` bit(8) NOT NULL DEFAULT b'0' COMMENT '会话成员数zui多256',
  `chat_last_time` datetime(0) NOT NULL COMMENT '最后一条消息时间',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat
-- ----------------------------
INSERT INTO `chat` VALUES (1, 1, '1', 38953549064830976, b'0', b'01', b'00000000', '2019-06-13 16:41:25', '2019-06-13 16:41:28');

-- ----------------------------
-- Table structure for chat_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cgro_uuid` bigint(20) NOT NULL COMMENT '群Uuid',
  `cgro_chat_uuid` bigint(20) NULL DEFAULT NULL COMMENT '群会话Uuid',
  `cgro_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群名字64',
  `cgro_csuse_uuid` bigint(20) NOT NULL COMMENT '群创建者Uuid',
  `cgro_public` bit(1) NOT NULL DEFAULT b'0' COMMENT '群是否公开 0：公开 1：不公开',
  `cgro_type` bit(2) NOT NULL COMMENT '群类型0：系统会话 1：一对一会话 2：多人会话',
  `cgro_count` bit(8) NOT NULL DEFAULT b'0' COMMENT '群成员数最多256',
  `cgro_data_status` bit(1) NOT NULL COMMENT '数据是否正常0：正常1：删除',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat_group_user
-- ----------------------------
DROP TABLE IF EXISTS `chat_group_user`;
CREATE TABLE `chat_group_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cgus_uuid` bigint(20) NOT NULL COMMENT '群成员Uuid',
  `cgus_suse_uuid` bigint(20) NOT NULL COMMENT '群参与用户Uuid',
  `cgus_cgro_uuid` bigint(20) NOT NULL COMMENT '群Uuid',
  `cgus_order` int(11) NOT NULL DEFAULT 0 COMMENT '加入顺序',
  `cgus_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会话成员名片',
  `cgus_data_stutus` bit(1) NOT NULL DEFAULT b'0' COMMENT '数据状态0：正常 1：删除',
  `create_time` datetime(0) NOT NULL COMMENT '会话成员加入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat_user
-- ----------------------------
DROP TABLE IF EXISTS `chat_user`;
CREATE TABLE `chat_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cuse_uuid` bigint(20) NOT NULL COMMENT '会话成员Uuid',
  `cuse_suse_uuid` bigint(20) NOT NULL COMMENT '会话参与用户Uuid',
  `cuse_chat_uuid` bigint(20) NOT NULL COMMENT '会话Uuid',
  `cuse_order` int(11) NOT NULL DEFAULT 0 COMMENT '加入顺序',
  `cuse_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会话成员名片',
  `cuse_data_stutus` bit(1) NOT NULL DEFAULT b'0' COMMENT '数据状态0：正常 1：删除',
  `cuse_chat_status` bit(2) NOT NULL DEFAULT b'0' COMMENT '最近会话状态0：正常 1：退出',
  `cuse_line` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否在线0：在线 1：没有在线',
  `create_time` datetime(0) NOT NULL COMMENT '会话成员加入时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat_user
-- ----------------------------
INSERT INTO `chat_user` VALUES (1, 1, 38953549064830976, 1, 0, NULL, b'0', b'00', b'1', '2019-06-16 16:45:58');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mess_uuid` bigint(20) NOT NULL COMMENT '消息Uuid',
  `mess_chat_uuid` bigint(20) NOT NULL COMMENT '会话Uuid',
  `mess_suse_uuid` bigint(20) NOT NULL COMMENT '创建消息用户Uuid',
  `mess_date` bigint(20) NOT NULL COMMENT '消息时间戳',
  `mess_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息',
  `mess_content_type` bit(3) NOT NULL COMMENT '消息类型0：普通消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 1, 1, 38953549064830976, 1, '1', b'001');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_uuid` bigint(20) NOT NULL COMMENT '用户uuid',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_type` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否是后端操作员 0：是 1：否',
  `user_head` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `user_ente_uuid` bigint(20) NOT NULL COMMENT '公司/企业Uuid',
  `user_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '数据状态0：正常 1：禁用',
  `user_data_status` bit(1) NOT NULL DEFAULT b'0' COMMENT '数据状态0：正常 1：删除',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (34, 58962844460253184, 'abc', b'0', NULL, 58962844460253185, b'0', b'0', '2018-10-29 16:57:18', '2018-10-29 16:57:25');

SET FOREIGN_KEY_CHECKS = 1;
