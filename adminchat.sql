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

 Date: 01/10/2019 03:21:05
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
INSERT INTO `user` VALUES (1, 22276035118628864, 'admin', b'0', NULL, 58962844460253185, b'0', b'0', '2018-07-20 11:17:01', '2018-10-16 20:57:39');
INSERT INTO `user` VALUES (2, 22276035118628865, 'admin01', b'0', NULL, 58962844460253185, b'0', b'0', '2018-07-20 11:17:01', '2018-08-29 09:50:18');
INSERT INTO `user` VALUES (3, 31400757496381440, '科技部景欣', b'0', NULL, 58962844460253185, b'0', b'0', '2018-08-14 15:35:24', '2018-10-26 11:16:34');
INSERT INTO `user` VALUES (4, 38953549064830976, '科技部任勇红', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-04 11:47:30', '2018-10-26 11:14:17');
INSERT INTO `user` VALUES (5, 39379929728548864, '计划部罗泾力', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-05 16:01:47', '2018-10-29 08:43:56');
INSERT INTO `user` VALUES (6, 39388200514355200, '采购部谢霜', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-05 16:34:39', '2018-09-05 16:43:54');
INSERT INTO `user` VALUES (7, 39388724554891264, '计划部徐科', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-05 16:36:44', '2018-10-22 16:58:51');
INSERT INTO `user` VALUES (8, 39388838837092352, '计划部张胜迪', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-05 16:37:11', '2018-10-22 16:58:58');
INSERT INTO `user` VALUES (9, 39390973570056192, '计划部邹欣芳', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-05 16:45:40', '2018-10-22 16:59:03');
INSERT INTO `user` VALUES (10, 39391057665851392, '计划部段妞', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-05 16:46:00', '2018-10-22 16:59:10');
INSERT INTO `user` VALUES (11, 39642023417872384, '泉涌指挥部1', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-06 09:23:15', '2018-09-27 15:44:59');
INSERT INTO `user` VALUES (12, 39642080644956160, '泉涌指挥部2', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-06 09:23:29', '2018-09-27 15:45:03');
INSERT INTO `user` VALUES (13, 39642174366679040, '泉涌指挥部3', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-06 09:23:51', '2018-09-27 15:45:08');
INSERT INTO `user` VALUES (14, 39642835514818560, '王慧军', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-06 09:26:29', '2018-09-14 16:29:57');
INSERT INTO `user` VALUES (15, 39653055678906368, '财务部杨光燕', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-06 10:07:05', '2018-10-31 09:13:52');
INSERT INTO `user` VALUES (16, 41088524899844096, '财务部查仕标', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-10 09:11:08', '2018-09-10 09:47:02');
INSERT INTO `user` VALUES (17, 41088612258807808, '财务部纪兴英', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-10 09:11:29', '2018-09-10 09:47:05');
INSERT INTO `user` VALUES (18, 41190603500814336, '财务部黎维飞', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-10 15:56:45', '2018-09-14 09:58:14');
INSERT INTO `user` VALUES (19, 41494296313987072, '财务部王彭', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-11 12:03:31', '2018-11-01 16:21:20');
INSERT INTO `user` VALUES (20, 41845492597915648, '鲜直达', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-12 11:19:03', '2018-09-12 11:19:25');
INSERT INTO `user` VALUES (21, 42246627947184128, '客服陈鹏', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-13 13:53:01', '2018-09-13 14:11:29');
INSERT INTO `user` VALUES (22, 42593093660180480, '配送组胡重阳', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-14 12:49:45', '2018-09-14 12:49:56');
INSERT INTO `user` VALUES (23, 42662006376890368, '采购部余鹏', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-14 17:23:35', '2018-09-14 17:23:43');
INSERT INTO `user` VALUES (24, 43290681178849280, '分拣部卢凤', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-16 11:01:43', '2018-09-16 11:03:47');
INSERT INTO `user` VALUES (25, 43291418352943104, '分拣部吴定奎', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-16 11:04:38', '2018-09-16 11:05:44');
INSERT INTO `user` VALUES (26, 43743249134780416, '李宇', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-17 17:00:03', '2018-09-17 20:56:43');
INSERT INTO `user` VALUES (27, 44373663847284736, '采购部邹林', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-19 10:45:06', '2018-09-20 11:30:19');
INSERT INTO `user` VALUES (28, 44805126161956864, '财务部黄明凤', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-20 15:19:35', '2018-10-31 09:15:51');
INSERT INTO `user` VALUES (29, 46557796438638592, '何鲨', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-25 11:24:04', '2018-09-25 11:24:16');
INSERT INTO `user` VALUES (30, 47699257896992768, '蔬菜刘桂梅', b'0', NULL, 58962844460253185, b'0', b'0', '2018-09-28 14:59:49', '2018-09-28 14:59:57');
INSERT INTO `user` VALUES (31, 51674940860006400, '客服陈欢', b'0', NULL, 58962844460253185, b'0', b'0', '2018-10-09 14:17:46', '2018-10-09 14:18:21');
INSERT INTO `user` VALUES (32, 51675030127378432, '客服罗英', b'0', NULL, 58962844460253185, b'0', b'0', '2018-10-09 14:18:07', '2018-10-09 14:18:26');
INSERT INTO `user` VALUES (33, 58847778444410880, '财务部张燕琴', b'0', NULL, 58962844460253185, b'0', b'0', '2018-10-29 09:20:04', '2018-10-29 14:52:54');
INSERT INTO `user` VALUES (34, 58962844460253184, 'abc', b'0', NULL, 58962844460253185, b'0', b'0', '2018-10-29 16:57:18', '2018-10-29 16:57:25');

SET FOREIGN_KEY_CHECKS = 1;
