/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : books

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2018-03-21 08:51:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET gbk COLLATE gbk_bin NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `status` int(2) DEFAULT '1',
  `lend_num` int(11) DEFAULT '0',
  `max_num` int(11) DEFAULT '5',
  PRIMARY KEY (`aid`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'root', '管理员', 'admin', '3231141543@qq.com', '134223674241', 2, 0, 5);
-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(205) NOT NULL,
  `card` varchar(205) CHARACTER SET utf8 NOT NULL,
  `autho` varchar(205) DEFAULT NULL,
  `num` int(11) NOT NULL,
  `press` varchar(205) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  UNIQUE KEY `ISBN` (`card`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;

-- book表添加属性
alter table book add borrow_user varchar(20) DEFAULT "无";
alter table book add borrow_time char(255) DEFAULT NULL;

-- 修改book表某一列属性
alter table book MODIFY borrow_time char(255) DEFAULT "无";

-- ----------------------------
-- Records of book
-- ----------------------------
-- INSERT INTO `book` VALUES ('4', '12', '12', '1', '1', '1', '3234');
-- INSERT INTO `book` VALUES ('2', '5', '5', '5', '5', '5', '323');
-- INSERT INTO `book` VALUES ('3', '6', '6', '4', '4', '4', '53');
-- INSERT INTO `book` VALUES ('5', '9', '9', '9', '9', '9', '3234');

-- ----------------------------
-- Table structure for `booktype`
-- ----------------------------
DROP TABLE IF EXISTS `booktype`;
CREATE TABLE `booktype` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=gbk;
-- ----------------------------
-- Records of booktype
-- ----------------------------
-- INSERT INTO `booktype` VALUES ('1', '3234');
-- INSERT INTO `booktype` VALUES ('2', '53');
-- INSERT INTO `booktype` VALUES ('4', '6');

-- ----------------------------
-- Table structure for `history`
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `hid` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) DEFAULT NULL,
  `bid` int(11) DEFAULT NULL,
  `card` char(255) DEFAULT NULL,
  `bookname` char(255) DEFAULT NULL,
  `adminname` char(255) DEFAULT NULL,
  `username` char(255) DEFAULT NULL,
  `begintime` char(255) DEFAULT NULL,
  `endtime` char(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`hid`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of history
-- ----------------------------
-- INSERT INTO `history` VALUES ('1', '1', '2', '5', '5', '123', '123', '2018-2-10', '2018-3-10', '0');
-- INSERT INTO `history` VALUES ('2', '1', '3', '6', '6', '123', '123', '2018-2-10', '2018-2-10', '0');
-- INSERT INTO `history` VALUES ('3', '1', '2', '5', '5', '123', '1235567', '2018-2-11', '2018-2-11', '0');
-- INSERT INTO `history` VALUES ('4', '4', '3', '6', '6', '456', '456', '2018-2-11', '2018-2-12', '0');
-- INSERT INTO `history` VALUES ('5', '1', '4', '1', '1', '123', '1235567', '2018-2-12', '2018-2-12', '0');

-- ----------------------------
-- Table structure for `review`
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) DEFAULT NULL,
  `bid` int(11) DEFAULT NULL,
  `card` char(255) DEFAULT NULL,
  `bookname` varchar(255) DEFAULT NULL,
  `adminname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `application_time` char(255) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`rid`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;