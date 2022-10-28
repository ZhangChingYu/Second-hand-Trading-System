/*
 Navicat Premium Data Transfer

 Source Server         : Mysql_connector
 Source Server Type    : MySQL
 Source Server Version : 50715 (5.7.15)
 Source Host           : localhost:3306
 Source Schema         : idlebugs

 Target Server Type    : MySQL
 Target Server Version : 50715 (5.7.15)
 File Encoding         : 65001

 Date: 28/10/2022 13:55:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用戶編號',
  `user_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '用戶名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手機號',
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '密碼',
  `authority` int(11) NOT NULL COMMENT '權限\n0: 已通過實名認證\n1: 未通過實名認證\n2: 違規用戶',
  `real_name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '真實姓名',
  `id_card` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '身分證號',
  `stu_num` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '學號',
  `register_date` datetime NOT NULL COMMENT '註冊時間',
  `violation_count` int(11) NULL DEFAULT NULL COMMENT '違規次數',
  `default_addr` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '默認地址',
  `addr_one` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '備用地址1',
  `addr_two` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '備用地址2',
  `addr_three` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '備用地址3',
  `email` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL COMMENT '郵箱帳號',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (3, 'Meng Kwok Wing', '12345678910', 'lKpJ5CnBry', 1, 'Meng Kwok Wing', '', 'fGsUVuXncI', '2020-09-10 05:09:56', 0, '295 Alameda Street', '295 Alameda Street', '295 Alameda Street', '295 Alameda Street', '12345678910@qq.com');
INSERT INTO `user_info` VALUES (4, 'Andrea Washington', '12345678910', 'ei3bpRMxWq', 1, 'Andrea Washington', '', '5A3gLrKW8L', '2006-11-02 17:41:18', 0, '715 Hinckley Rd', '715 Hinckley Rd', '715 Hinckley Rd', '715 Hinckley Rd', '12345678910@qq.com');
INSERT INTO `user_info` VALUES (5, 'Mao Jialun', '12345678910', '4DmkewSQq6', 1, 'Mao Jialun', '', '8A7gPjTpN4', '2022-07-23 21:22:36', 1, '276 Little Clarendon St', '276 Little Clarendon St', '276 Little Clarendon St', '276 Little Clarendon St', '12345678910@qq.com');
INSERT INTO `user_info` VALUES (6, 'Zou Xiuying', '12345678910', 'clYE3F1KSz', 1, 'Zou Xiuying', '', '8L3nfBophu', '2008-03-11 21:12:51', 0, '659 Shennan E Rd, Cai Wu Wei, Luohu District', '659 Shennan E Rd, Cai Wu Wei, Luohu District', '659 Shennan E Rd, Cai Wu Wei, Luohu District', '659 Shennan E Rd, Cai Wu Wei, Luohu District', '12345678910@qq.com');
INSERT INTO `user_info` VALUES (7, 'Jamie Sanders', '12345678910', 'VO8p848fwA', 1, 'Jamie Sanders', '', 'r38LEjbzee', '2016-01-18 17:04:45', 3, '200 Park End St', '200 Park End St', '200 Park End St', '200 Park End St', '12345678910@qq.com');
INSERT INTO `user_info` VALUES (8, NULL, '1635905050', '111', 1, NULL, NULL, NULL, '2022-10-27 16:47:21', NULL, NULL, NULL, NULL, NULL, '12345678910@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
