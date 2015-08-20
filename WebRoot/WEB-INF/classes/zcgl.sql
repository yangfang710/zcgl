/*
Navicat MySQL Data Transfer

Source Server         : 200
Source Server Version : 50528
Source Host           : 172.23.8.200:3306
Source Database       : zcgl

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2014-12-15 17:51:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ads
-- ----------------------------
DROP TABLE IF EXISTS `ads`;
CREATE TABLE `ads` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `information` varchar(255) DEFAULT NULL,
  `oper_user` varchar(255) DEFAULT NULL,
  `oper_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ads
-- ----------------------------
INSERT INTO `ads` VALUES ('14', '管理应用程序执行控制器，负责与所有网络交换机进行交互，配置数据转发路径，从而提高带宽利用率。这个应用程序与云管理软件进行交互，保证有足够的带宽支持负载的创建和变化。', 'admin', '2004-01-03 06:35:28');
INSERT INTO `ads` VALUES ('15', '万事如意，一切顺利！', 'admin', '2004-01-03 06:37:04');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_detail` varchar(255) DEFAULT NULL,
  `department_id` varchar(255) DEFAULT NULL,
  `department_name` varchar(255) DEFAULT NULL,
  `product_code` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `class_code` varchar(255) DEFAULT NULL,
  `product_version` varchar(255) DEFAULT NULL,
  `specification` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `manufacturers` varchar(255) DEFAULT NULL,
  `product_use` varchar(255) DEFAULT NULL,
  `receive_user` varchar(255) DEFAULT NULL,
  `in_time` date DEFAULT NULL,
  `product_status` varchar(255) DEFAULT NULL,
  `manage_user` varchar(255) DEFAULT NULL,
  `use_user` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `last_out_time` date DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `oper_user` varchar(45) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '入库的操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '1', '01', '通信与信息工程学院', '1', '1', '1', '1', '1', '100', '1', '科研', '1', '2014-11-10', '报废', '1', '1', '信科', '2014-11-17', '1', null, null);
INSERT INTO `product` VALUES ('22', 'rtwer', '0101', '通信学院办公室', 'wer', 'wer', 'wer', 'wre', 'wer', '0', 'wer', '科研', 'wer', '2014-02-04', '库存', '123', '', '二教', '2014-11-17', '', null, null);
INSERT INTO `product` VALUES ('24', 'aa', '0102', '通信学院学生办公室', 'aaaa', 'aa', 'aa', 'aaa', 'aa', '0', 'aa', '科研', 'aa', '2014-02-04', '库存', '123', '', '三教', '2014-11-17', '', null, null);
INSERT INTO `product` VALUES ('25', 'aa', '010J', '通信学院教研中心', 'a22', 'aa', 'aa', 'aa', 'aa', '0', 'aa', '教学', 'aa', '2014-03-04', '库存', '123', '', '四教', '2014-11-17', '', null, null);
INSERT INTO `product` VALUES ('26', 'aa', '010J01', '电路基础教研中心', 'a23', '123456', 'aa', 'aa ', 'aa', '0', 'aa', '教学', 'aa', '2014-03-04', '在用', '123', 'aaaaa213', '二教', '2014-12-05', '', null, null);
INSERT INTO `product` VALUES ('27', 'erw', '01', '通信与信息工程学院', 'werqqq', 'we', 'wre', 'wer', 'wr', '0', 'wre', '教学', 'wr', '2014-04-09', '库存', '123', '', '三教', '2014-11-17', '', 'null', '2014-12-05 17:37:12');
INSERT INTO `product` VALUES ('28', '2342', '0101', '通信学院办公室', '2342', '2342', '234', '234', '234', '0', '243', '行政', '234', '2014-04-09', '库存', '123', '234', '四教', '2014-12-15', '', 'admin', '2014-12-08 15:42:50');
INSERT INTO `product` VALUES ('29', '24', '0102', '通信学院学生办公室', '32454', '54', '45', '45', '45', '0', '45', '行政', 'ert', '2014-05-08', '库存', '123', 'er', '信科', '2014-12-09', '', 'admin', '2014-12-08 15:48:44');
INSERT INTO `product` VALUES ('30', 'asd', '010J', '通信学院教研中心', 'wer232', 'wer', 'we', 'sfd', 'sdf', '0', 'sdf', '科研', 'sdf', '2014-05-07', '库存', '123', '', '三教', '2014-12-09', '', 'admin', '2014-12-08 15:51:17');
INSERT INTO `product` VALUES ('31', 'rtwer', '010J01', '电路基础教研中心', 's1', 'wer', 'wer', 'wre', 'wer', '0', 'wer', '科研', 'wer', '2014-06-04', '库存', '123', '', '四教', '2014-12-05', '无', 'admin', '2014-12-08 17:44:10');
INSERT INTO `product` VALUES ('32', 'aa', '01', '通信与信息工程学院', '11', 'aa', 'aa', 'aa', 'aa', '0', 'aa', '科研', 'aa', '2014-06-04', '库存', '123', '', '二教', '2014-12-05', '无', 'admin', '2014-12-08 17:44:10');
INSERT INTO `product` VALUES ('33', 'aa', '0101', '通信学院办公室', '111', 'aa', 'aa', 'aaa', 'aa', '0', 'aa', '教学', 'aa', '2014-07-04', '库存', '123', '', '三教', '2014-12-05', '无', 'admin', '2014-12-08 17:44:10');
INSERT INTO `product` VALUES ('34', 'aa', '0102', '通信学院学生办公室', '1111', 'aa', 'aa', 'aa', 'aa', '0', 'aa', '教学', 'aa', '2014-07-04', '库存', '123', '', '四教', '2014-12-05', '无', 'admin', '2014-12-08 17:44:11');
INSERT INTO `product` VALUES ('35', 'aa', '010J', '通信学院教研中心', '11111', '123456', 'aa', 'aa', 'aa', '0', 'aa', '教学', 'aa', '2014-08-04', '在用', '123', 'aaaaa213', '逸夫楼', '2014-12-05', '无', 'admin', '2014-12-08 17:44:11');
INSERT INTO `product` VALUES ('36', 'rtwer', '010J01', '电路基础教研中心', 'Ss1', 'wer', 'wer', 'wre', 'wer', '0', 'wer', '行政', 'wer', '2014-08-04', '库存', '123', '', '逸夫楼', '2014-12-05', '1', 'admin', '2014-12-08 17:51:16');
INSERT INTO `product` VALUES ('37', 'aa', '01', '通信与信息工程学院', 's11', 'aa', 'aa', 'aa', 'aa', '0', 'aa', '行政', 'aa', '2014-09-04', '库存', '123', '', '行政楼', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product` VALUES ('38', 'aa', '0101', '通信学院办公室', 's111', 'aa', 'aa', 'aaa', 'aa', '0', 'aa', '科研', 'aa', '2014-09-04', '库存', '123', '', '逸夫楼', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product` VALUES ('39', 'aa', '0102', '通信学院学生办公室', 's1111', 'aa', 'aa', 'aa', 'aa', '0', 'aa', '科研', 'aa', '2014-10-04', '报废', '123', '', '行政楼', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product` VALUES ('40', 'aa', '010J', '通信学院教研中心', 's11111', '123456', 'aa', 'aa', 'aa', '0', 'aa', '科研', 'aa', '2014-10-04', '在用', '123', 'aaaaa213', '图书馆', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product` VALUES ('41', 'rtwer', '010J01', '电路基础教研中心', '22', 'wer', 'wer', 'wre', 'wer', '0', 'wer', '教学', 'wer', '2014-11-04', '库存', '123', '', '图书馆', '2014-12-05', '11', 'admin', '2014-12-08 19:39:14');
INSERT INTO `product` VALUES ('42', 'aa', '01', '通信与信息工程学院', '222', 'aa', 'aa', 'aa', 'aa', '0', 'aa', '教学', 'aa', '2014-11-04', '库存', '123', '', '逸夫楼', '2014-12-05', '11', 'admin', '2014-12-08 19:39:14');
INSERT INTO `product` VALUES ('43', 'aa', '0101', '通信学院办公室', '2222', 'aa', 'aa', 'aaa', 'aa', '0', 'aa', '教学', 'aa', '2014-12-04', '库存', '123', '', '行政楼', '2014-12-05', '11', 'admin', '2014-12-08 19:39:14');
INSERT INTO `product` VALUES ('44', 'aa', '0102', '通信学院学生办公室', '22222', 'aa', 'aa', 'aa', 'aa', '0', 'aa', '行政', 'aa', '2014-12-04', '库存', '123', '', '二教', '2014-12-05', '11', 'admin', '2014-12-08 19:39:14');
INSERT INTO `product` VALUES ('45', 'aa', '010J', '通信学院教研中心', '222222', '123456', 'aa', 'aa', 'aa', '0', 'aa', '行政', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '信科', '2014-12-05', '11', 'admin', '2014-12-08 19:39:14');
INSERT INTO `product` VALUES ('46', 'dg', '010J01', '电路基础教研中心', 'dgfd', 'sdfgs', 'sgfd', 'sg', 'sgdf', '0', 'sdfg', '科研', 'sdfs', '2014-12-08', '报废', '123', 'asfwe', '四教', '2014-12-09', '', 'admin', '2004-01-01 17:06:40');
INSERT INTO `product` VALUES ('47', 'rtwer', '01', '通信与信息工程学院', 'a18', 'wer', 'wer', 'wre', 'wer', '100', 'wer', '教学', 'wer', '2014-12-04', '库存', '123', '', '二教', '2014-12-05', '无', 'admin', '2004-01-02 15:23:26');
INSERT INTO `product` VALUES ('48', 'aa', '0101', '通信学院办公室', 'a19', 'aa', 'aa', 'aa', 'aa', '100', 'aa', '教学', 'aa', '2014-12-04', '库存', '123', '', '三教', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('49', 'aa', '0102', '通信学院学生办公室', 'a20', 'aa', 'aa', 'aaa', 'aa', '100', 'aa', '教学', 'aa', '2014-12-04', '库存', '123', '', '四教', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('50', 'aa', '010J', '通信学院教研中心', 'a21', 'aa', 'aa', 'aa', 'aa', '100', 'aa', '行政', 'aa', '2014-12-04', '库存', '123', '', '逸夫楼', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('51', 'aa', '010J01', '电路基础教研中心', 'a233', '123456', 'aa', 'aa', 'aa', '100', 'aa', '行政', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '行政楼', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('52', 'aa', '01', '通信与信息工程学院', 'a24', '123457', 'aa', 'aa', 'aa', '100', 'aa', '科研', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '逸夫楼', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('53', 'aa', '0101', '通信学院办公室', 'a25', '123458', 'aa', 'aa', 'aa', '100', 'aa', '科研', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '行政楼', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('54', 'aa', '0102', '通信学院学生办公室', 'a26', '123459', 'aa', 'aa', 'aa', '100', 'aa', '教学', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '逸夫楼', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('55', 'aa', '010J', '通信学院教研中心', 'a27', '123460', 'aa', 'aa', 'aa', '100', 'aa', '教学', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '行政楼', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('56', 'aa', '010J01', '电路基础教研中心', 'a28', '123461', 'aa', 'aa', 'aa', '100', 'aa', '教学', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '二教', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('57', 'aa', '0102', '通信学院学生办公室', 'a29', '123462', 'aa', 'aa', 'aa', '100', 'aa', '行政', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '三教', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('58', 'aa', '010J', '通信学院教研中心', 'a30', '123463', 'aa', 'aa', 'aa', '100', 'aa', '行政', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '四教', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('59', 'aa', '010J01', '电路基础教研中心', 'a31', '123464', 'aa', 'aa', 'aa', '100', 'aa', '科研', 'aa', '2014-12-04', '在用', '123', 'aaaaa213', '二教', '2014-12-05', '无', 'admin', '2004-01-02 15:23:27');
INSERT INTO `product` VALUES ('60', null, '010S', '信号处理实验中心', 'a32', '123464', '123464', '123464', '123464', '500', 'aa', '教学', 'aa', '2014-12-16', '在用', '123', '在现场咨询', '四教', '2014-12-18', '无', 'admin', '2014-12-22 14:52:12');

-- ----------------------------
-- Table structure for product_edit
-- ----------------------------
DROP TABLE IF EXISTS `product_edit`;
CREATE TABLE `product_edit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL COMMENT '资产的记录ID',
  `department_id` varchar(45) DEFAULT NULL,
  `department_name` varchar(45) DEFAULT NULL,
  `manage_user` varchar(45) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `product_status` varchar(45) DEFAULT NULL,
  `product_use` varchar(45) DEFAULT NULL,
  `use_user` varchar(45) DEFAULT NULL,
  `last_out_time` date DEFAULT NULL,
  `remark` varchar(45) DEFAULT NULL,
  `oper_user` varchar(45) DEFAULT NULL COMMENT '操作人',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='资产状态变更历史记录表';

-- ----------------------------
-- Records of product_edit
-- ----------------------------
INSERT INTO `product_edit` VALUES ('1', '26', '010J', '通信学院教研中心', '123', 'aa', '在用', '教学', 'aaaaaa', '2014-12-05', '', 'admin', null);
INSERT INTO `product_edit` VALUES ('2', '26', '010J', '通信学院教研中心', '123', 'aa', '在用', '教学', 'aaa', '2014-12-05', '', 'admin', null);
INSERT INTO `product_edit` VALUES ('3', '26', '010J', '通信学院教研中心', '123', 'aa', '库存', '教学', 'aaa', '2014-12-05', '', 'admin', null);
INSERT INTO `product_edit` VALUES ('4', '26', '010J', '通信学院教研中心', '123', 'aa', '在用', '教学', 'aaaaa213', '2014-12-05', '', 'admin', '2014-12-05 15:06:01');
INSERT INTO `product_edit` VALUES ('5', '26', '010J', '通信学院教研中心', '123', 'aa', '在用', '教学', 'aaaaa213', '2014-12-05', '', 'admin', '2014-12-05 15:06:04');
INSERT INTO `product_edit` VALUES ('6', '26', '010J', '通信学院教研中心', '123', 'aa', '在用', '教学', 'aaaaa213', '2014-12-05', '', 'admin', '2014-12-05 15:06:07');
INSERT INTO `product_edit` VALUES ('7', '26', '010J', '通信学院教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '', 'admin', '2014-12-05 16:21:49');
INSERT INTO `product_edit` VALUES ('9', '28', '010J01', '电路基础教研中心', '123', '234', '库存', '科研', '234', '2014-12-15', '', 'admin', '2014-12-08 15:44:07');
INSERT INTO `product_edit` VALUES ('11', '29', '010S', '通信学院实验中心', '123', 'derte', '库存', '科研', 'er', '2014-12-09', '', 'admin', '2014-12-08 15:49:07');
INSERT INTO `product_edit` VALUES ('12', '30', '010S', '通信学院实验中心', '123', 'sdf', '库存', '科研', '', '2014-12-09', '', 'admin', '2014-12-08 15:51:17');
INSERT INTO `product_edit` VALUES ('13', '35', '010J', '通信学院教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2014-12-08 17:44:11');
INSERT INTO `product_edit` VALUES ('14', '35', '010J', '通信学院教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2014-12-08 17:44:11');
INSERT INTO `product_edit` VALUES ('15', '35', '010J', '通信学院教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2014-12-08 17:44:11');
INSERT INTO `product_edit` VALUES ('16', '35', '010J', '通信学院教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2014-12-08 17:44:11');
INSERT INTO `product_edit` VALUES ('17', '35', '010J', '通信学院教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2014-12-08 17:44:11');
INSERT INTO `product_edit` VALUES ('18', '36', '010J', '通信学院教研中心', '123', 'wer', '库存', '科研', '', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product_edit` VALUES ('19', '37', '010J', '通信学院教研中心', '123', 'aa', '库存', '科研', '', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product_edit` VALUES ('20', '38', '010J', '通信学院教研中心', '123', 'aa', '库存', '科研', '', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product_edit` VALUES ('21', '39', '010J', '通信学院教研中心', '123', '', '库存', '科研', '', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product_edit` VALUES ('22', '40', '010J', '通信学院教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '1', 'admin', '2014-12-08 17:51:17');
INSERT INTO `product_edit` VALUES ('23', '41', '010J', '通信学院教研中心', '123', 'wer', '库存', '教学', '', '2014-12-05', '11', 'admin', '2014-12-08 19:39:14');
INSERT INTO `product_edit` VALUES ('24', '42', '010J', '通信学院教研中心', '123', 'aa', '库存', '教学', '', '2014-12-05', '11', 'admin', '2014-12-08 19:39:14');
INSERT INTO `product_edit` VALUES ('25', '43', '010S03', '信号处理实验中心', '123', 'aa', '库存', '教学', '', '2014-12-05', '11', 'admin', '2014-12-08 19:39:15');
INSERT INTO `product_edit` VALUES ('26', '44', '010S03', '信号处理实验中心', '123', '', '库存', '行政', '', '2014-12-05', '11', 'admin', '2014-12-08 19:39:15');
INSERT INTO `product_edit` VALUES ('27', '45', '010S03', '信号处理实验中心', '123', 'aa', '在用', '行政', 'aaaaa213', '2014-12-05', '11', 'admin', '2014-12-08 19:39:15');
INSERT INTO `product_edit` VALUES ('28', '46', '010J', '通信学院教研中心', '123', 'sdfg', '库存', '科研', '', '2014-12-09', '', 'admin', '2004-01-01 17:06:41');
INSERT INTO `product_edit` VALUES ('29', '46', '010J', '通信学院教研中心', '123', 'sdfgdfgedfg', '报废', '科研', 'asfwe', '2014-12-09', '', 'admin', '2004-01-01 17:07:09');
INSERT INTO `product_edit` VALUES ('30', '47', '1', '通信与信息工程学院', '123', 'wer', '库存', '科研', '', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('31', '48', '101', '通信学院办公室', '123', 'aa', '库存', '科研', '', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('32', '49', '102', '通信学院学生办公室', '123', 'aa', '库存', '科研', '', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('33', '50', '010J', '通信学院教研中心', '123', '', '库存', '科研', '', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('34', '51', '010J01', '电路基础教研中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('35', '52', '010J02', '信号处理教学研究中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('36', '53', '010J03', '信息工程教学研究中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('37', '54', '010J04', '通信网络教学研究中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('38', '55', '010J05', '通信理论教学研究中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('39', '56', '010S', '通信学院实验中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('40', '57', '010S01', '通信学院原电路实验中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:28');
INSERT INTO `product_edit` VALUES ('41', '58', '010S02', '通信技术与网络实验中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:29');
INSERT INTO `product_edit` VALUES ('42', '59', '010S03', '信号处理实验中心', '123', 'aa', '在用', '科研', 'aaaaa213', '2014-12-05', '无', 'admin', '2004-01-02 15:23:29');

-- ----------------------------
-- Table structure for select_item
-- ----------------------------
DROP TABLE IF EXISTS `select_item`;
CREATE TABLE `select_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `select_name` varchar(255) NOT NULL,
  `select_val` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of select_item
-- ----------------------------
INSERT INTO `select_item` VALUES ('1', '使用方向', '科研');
INSERT INTO `select_item` VALUES ('2', '使用方向', '教学');
INSERT INTO `select_item` VALUES ('3', '使用方向', '行政');
INSERT INTO `select_item` VALUES ('4', '季度', '1');
INSERT INTO `select_item` VALUES ('5', '季度', '2');
INSERT INTO `select_item` VALUES ('6', '季度', '3');
INSERT INTO `select_item` VALUES ('7', '季度', '4');
INSERT INTO `select_item` VALUES ('8', '当前状态', '库存');
INSERT INTO `select_item` VALUES ('9', '当前状态', '在用');
INSERT INTO `select_item` VALUES ('10', '当前状态', '故障');
INSERT INTO `select_item` VALUES ('11', '当前状态', '报废');
INSERT INTO `select_item` VALUES ('12', '当前状态', '校内转入');

-- ----------------------------
-- Table structure for sys_data_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_auth`;
CREATE TABLE `sys_data_auth` (
  `data_auth_id` int(10) unsigned NOT NULL COMMENT 'ID',
  `dept_type` varchar(40) DEFAULT NULL COMMENT '部门类型（管理部、代理商、网点）',
  `group_id` varchar(10) DEFAULT NULL COMMENT '角色组ID',
  `group_name` varchar(50) DEFAULT NULL COMMENT '角色组名称',
  `user_id` varchar(20) DEFAULT NULL COMMENT '用户ＩＤ（可以为空）',
  `data_type` varchar(50) DEFAULT NULL COMMENT '相应的数据权限',
  PRIMARY KEY (`data_auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限表';

-- ----------------------------
-- Records of sys_data_auth
-- ----------------------------
INSERT INTO `sys_data_auth` VALUES ('1', '市公司', '1101', '企信部管理员', null, 'allDeptId');
INSERT INTO `sys_data_auth` VALUES ('2', '代理商', '1401', '代理商经理', null, 'selfShops');
INSERT INTO `sys_data_auth` VALUES ('3', '营业厅', '1402', '营业厅经理', null, 'selfDept');
INSERT INTO `sys_data_auth` VALUES ('4', '地包商', '1201', '地包商经理', null, 'selfDept');
INSERT INTO `sys_data_auth` VALUES ('5', '维修商', '1301', '维修商经理', null, 'selfDept');
INSERT INTO `sys_data_auth` VALUES ('6', '省公司', '1', '超级管理员', null, 'allData');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dept_id` varchar(20) NOT NULL COMMENT '部门ID',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `parent_dept_id` varchar(45) DEFAULT NULL,
  `dept_address` varchar(100) DEFAULT NULL COMMENT '部门地址',
  `phone_num` varchar(15) DEFAULT NULL COMMENT '联系电话（固定电话）',
  `email` varchar(20) DEFAULT NULL COMMENT 'Email',
  `dept_state` varchar(5) DEFAULT NULL COMMENT '部门状态（1可用；2禁用）',
  `in_date` datetime DEFAULT NULL COMMENT '部门创建时间',
  `oper_user_name` varchar(45) DEFAULT NULL COMMENT '操作人',
  `remark` varchar(100) DEFAULT NULL COMMENT '部门备注',
  `level` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`,`dept_id`),
  KEY `idx_sd_dept_name` (`dept_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='部门表，parent_dept_id来表示部门所属的级别，不在代码级用dept_id做判断，便与变更';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '01', '通信与信息工程学院', '00', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('2', '0101', '通信学院办公室', '01', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('3', '0102', '通信学院学生办公室', '01', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('4', '010J', '通信学院教研中心', '01', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('5', '010J01', '电路基础教研中心', '010J', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('6', '010J02', '信号处理教学研究中心', '010J', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('7', '010J03', '信息工程教学研究中心', '010J', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('8', '010J04', '通信网络教学研究中心', '010J', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('9', '010J05', '通信理论教学研究中心', '010J', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('10', '010S', '通信学院实验中心', '01', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('11', '010S01', '通信学院原电路实验中心', '010S', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('12', '010S02', '通信技术与网络实验中心', '010S', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('13', '010S03', '信号处理实验中心', '010S', '', '', '', '可用', null, '', '', '');
INSERT INTO `sys_dept` VALUES ('15', '010101', 'aaaaa', '0101', '', '', null, '可用', '2004-01-01 12:02:30', 'admin', '', null);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menuid` varchar(20) NOT NULL COMMENT '菜单ID',
  `menuname` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(50) DEFAULT NULL COMMENT 'ICON图片',
  `url` varchar(50) DEFAULT NULL COMMENT '路径（URL）',
  `menulevel` tinyint(2) DEFAULT NULL COMMENT '菜单级别',
  `p_menuid` varchar(20) DEFAULT NULL COMMENT '上级菜单ID',
  `menu_title` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `menu_desc` varchar(20) DEFAULT NULL COMMENT '菜单描述',
  `menu_order` tinyint(2) DEFAULT NULL COMMENT '同级菜单顺序',
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('01', '资产管理', 'organizationManage', '#', '1', '01', '业务管理', '业务管理', '1');
INSERT INTO `sys_menu` VALUES ('0101', '资产入库', 'repair', 'productListAction', '2', '01', '资产入库', '资产入库', '1');
INSERT INTO `sys_menu` VALUES ('010101', '添加资产', 'add', 'addProduct', '3', '0101', '添加资产', '添加资产务', '1');
INSERT INTO `sys_menu` VALUES ('010102', '修改资产', 'modify', 'modifyProduct', '3', '0101', '修改资产', '修改资产', '2');
INSERT INTO `sys_menu` VALUES ('010103', '删除资产', 'delete', 'deleteProduct', '3', '0101', '删除资产', '删除资产', '3');
INSERT INTO `sys_menu` VALUES ('010104', '导入资产', 'up', 'excelInProduct', '3', '0101', '导入资产', '导入资产', '4');
INSERT INTO `sys_menu` VALUES ('010105', '导出资产', 'save', 'excelOutProduct', '3', '0101', '导出资产', '导出资产', '5');
INSERT INTO `sys_menu` VALUES ('010106', '资产状态变更', 'modify', 'editProduct', '3', '0101', '资产状态变更', '资产状态变更', '6');
INSERT INTO `sys_menu` VALUES ('010107', '资产历史记录', 'search', 'detailProduct', '3', '0101', '资产历史记录', '资产历史记录', '7');
INSERT INTO `sys_menu` VALUES ('02', '统计报表', 'search', '#', '1', '02', '统计报表', '统计报表', '2');
INSERT INTO `sys_menu` VALUES ('0201', '资产统计', 'search', 'chartAction', '2', '02', '资产统计', '资产统计', '1');
INSERT INTO `sys_menu` VALUES ('04', '系统管理', 'sysmanage', '#', '1', '04', '系统管理', '系统管理', '4');
INSERT INTO `sys_menu` VALUES ('0401', '在线用户监控', 'userManage', 'onLineUserManagerAction', '2', '04', '在线用户监控', '在线用户监控', '1');
INSERT INTO `sys_menu` VALUES ('0402', '部门管理', 'organization', 'organizationManageAction', '2', '04', '部门管理', '部门管理', '2');
INSERT INTO `sys_menu` VALUES ('040201', '添加部门', 'add', 'f_add', '3', '0402', '新增部门', '新增部门', '1');
INSERT INTO `sys_menu` VALUES ('040202', '修改部门', 'modify', 'f_modify', '3', '0402', '修改部门', '修改部门', '2');
INSERT INTO `sys_menu` VALUES ('040203', '删除部门', 'delete', 'f_delete', '3', '0402', '删除部门', '删除部门', '3');
INSERT INTO `sys_menu` VALUES ('040204', '导出EXCEL', 'up', 'f_down', '3', '0402', '导出EXCEL', '导出EXCEL', '4');
INSERT INTO `sys_menu` VALUES ('0403', '用户管理', 'userManage', 'userManageAction', '2', '04', '用户管理', '用户管理', '3');
INSERT INTO `sys_menu` VALUES ('040301', '添加用户', 'add', 'f_add', '3', '0403', '添加用户', '添加用户', '1');
INSERT INTO `sys_menu` VALUES ('040302', '修改用户', 'modify', 'f_modify', '3', '0403', '修改用户', '修改用户', '2');
INSERT INTO `sys_menu` VALUES ('040303', '删除用户', 'delete', 'f_delete', '3', '0403', '删除用户', '删除用户', '3');
INSERT INTO `sys_menu` VALUES ('040304', '导出EXCEL', 'up', 'f_down', '3', '0403', '导出excel', '导出excel', '4');
INSERT INTO `sys_menu` VALUES ('0404', '公告管理', 'search', 'f_gonggao', '2', '04', '公告管理', '公告管理', '4');
INSERT INTO `sys_menu` VALUES ('040401', '添加公告', 'add', 'f_add', '3', '0404', '添加公告', '添加公告', '1');
INSERT INTO `sys_menu` VALUES ('040402', '修改公告', 'modify', 'f_modify', '3', '0404', '修改公告', '修改公告', '2');
INSERT INTO `sys_menu` VALUES ('040403', '删除公告', 'delete', 'f_delete', '3', '0404', '删除公告', '删除公告', '3');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户姓名',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `dept_id` varchar(20) DEFAULT NULL COMMENT '部门ID',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `group_id` varchar(20) DEFAULT NULL COMMENT '所属组ID',
  `group_name` varchar(50) DEFAULT NULL COMMENT '用户所属哪个组名称，在sys_user_group表中查看',
  `user_state` varchar(20) DEFAULT NULL COMMENT '用户可用状态',
  `user_email` varchar(50) DEFAULT NULL COMMENT 'Email',
  `phone_num` varchar(20) DEFAULT NULL COMMENT '用户联系电话',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `in_date` datetime DEFAULT NULL COMMENT '用户创建时间',
  `oper_user_name` varchar(45) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`user_id`),
  KEY `idx_su_user_name` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('123', '123', '96E79218965EB72C92A549DD5A330112', '010J', '通信学院教研中心', '2', '部门管理员', '可用', '', '', '', '2014-12-04 15:58:45', 'null');
INSERT INTO `sys_user` VALUES ('admin', 'admin', '21232F297A57A5A743894A0E4A801FC3', '01', '通信与信息工程学院', '1', '超级管理员', '可用', null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('sss25243', 'ssssderer', '96E79218965EB72C92A549DD5A330112', '0101', '通信学院办公室', '', '部门管理员', '1', '', '', '', '2004-01-01 14:23:56', 'admin');

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group` (
  `group_id` varchar(10) NOT NULL COMMENT '组ID',
  `group_name` varchar(50) DEFAULT NULL COMMENT '组名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色组表';

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
INSERT INTO `sys_user_group` VALUES ('1', '超级管理员', null);
INSERT INTO `sys_user_group` VALUES ('2', '部门管理员', null);
INSERT INTO `sys_user_group` VALUES ('3', '普通用户', null);

-- ----------------------------
-- Table structure for sys_user_group_oper_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group_oper_auth`;
CREATE TABLE `sys_user_group_oper_auth` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` varchar(20) DEFAULT NULL COMMENT '组ID',
  `menuid` varchar(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='操作权限表';

-- ----------------------------
-- Records of sys_user_group_oper_auth
-- ----------------------------
INSERT INTO `sys_user_group_oper_auth` VALUES ('1', '1', '01');
INSERT INTO `sys_user_group_oper_auth` VALUES ('2', '1', '0101');
INSERT INTO `sys_user_group_oper_auth` VALUES ('3', '1', '010101');
INSERT INTO `sys_user_group_oper_auth` VALUES ('4', '1', '010102');
INSERT INTO `sys_user_group_oper_auth` VALUES ('5', '1', '010103');
INSERT INTO `sys_user_group_oper_auth` VALUES ('6', '1', '02');
INSERT INTO `sys_user_group_oper_auth` VALUES ('7', '1', '0201');
INSERT INTO `sys_user_group_oper_auth` VALUES ('8', '1', '04');
INSERT INTO `sys_user_group_oper_auth` VALUES ('9', '1', '0401');
INSERT INTO `sys_user_group_oper_auth` VALUES ('10', '1', '0402');
INSERT INTO `sys_user_group_oper_auth` VALUES ('11', '1', '040201');
INSERT INTO `sys_user_group_oper_auth` VALUES ('12', '1', '040202');
INSERT INTO `sys_user_group_oper_auth` VALUES ('13', '1', '040203');
INSERT INTO `sys_user_group_oper_auth` VALUES ('14', '1', '040204');
INSERT INTO `sys_user_group_oper_auth` VALUES ('16', '1', '0403');
INSERT INTO `sys_user_group_oper_auth` VALUES ('17', '1', '040301');
INSERT INTO `sys_user_group_oper_auth` VALUES ('18', '1', '040302');
INSERT INTO `sys_user_group_oper_auth` VALUES ('19', '1', '040303');
INSERT INTO `sys_user_group_oper_auth` VALUES ('20', '1', '040304');
INSERT INTO `sys_user_group_oper_auth` VALUES ('21', '1', '010104');
INSERT INTO `sys_user_group_oper_auth` VALUES ('22', '1', '010105');
INSERT INTO `sys_user_group_oper_auth` VALUES ('23', '1', '010106');
INSERT INTO `sys_user_group_oper_auth` VALUES ('24', '1', '010107');
INSERT INTO `sys_user_group_oper_auth` VALUES ('25', '1', '0404');
INSERT INTO `sys_user_group_oper_auth` VALUES ('26', '1', '040401');
INSERT INTO `sys_user_group_oper_auth` VALUES ('27', '1', '040402');
INSERT INTO `sys_user_group_oper_auth` VALUES ('28', '1', '040403');
