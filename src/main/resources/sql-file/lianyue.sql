/*
Navicat MySQL Data Transfer

Source Server         : 101.200.38.242
Source Server Version : 50623
Source Host           : 101.200.38.242:33006
Source Database       : lianyue

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2019-03-26 10:03:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activation_record
-- ----------------------------
DROP TABLE IF EXISTS `activation_record`;
CREATE TABLE `activation_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `click_id` int(11) DEFAULT NULL,
  `req_url` varchar(500) DEFAULT NULL,
  `req_param` varchar(500) DEFAULT NULL,
  `result` varchar(100) DEFAULT NULL,
  `is_notice` tinyint(4) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='激活记录表';

-- ----------------------------
-- Records of activation_record
-- ----------------------------
INSERT INTO `activation_record` VALUES ('8', '1', 'http://101.200.38.242:8080/api/activationNotice', 'idfa=3BA5ADEE-6467-4FB7-9424-6D873071A73D&click_id=1', '', '0', '2018-06-28 13:28:49');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `realName` varchar(45) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `headPicture` varchar(45) DEFAULT NULL,
  `addDate` date DEFAULT NULL,
  `updateDate` date DEFAULT NULL,
  `state` int(11) DEFAULT '0' COMMENT '1：正常\n2：冻结\n3：删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', 'admin', '超级管理员', '28', '17788886666', '', '2017-06-15', '2017-06-15', '1');

-- ----------------------------
-- Table structure for app_channel
-- ----------------------------
DROP TABLE IF EXISTS `app_channel`;
CREATE TABLE `app_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_info_id` int(11) DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of app_channel
-- ----------------------------

-- ----------------------------
-- Table structure for application
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) DEFAULT NULL,
  `app_id` int(11) DEFAULT NULL,
  `app_name` varchar(100) DEFAULT NULL,
  `callback_ratio` varchar(50) DEFAULT NULL,
  `req_type` varchar(50) DEFAULT NULL,
  `req_url` varchar(500) DEFAULT NULL,
  `req_param` varchar(500) DEFAULT NULL,
  `req_ratio` varchar(50) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='app应用信息表';

-- ----------------------------
-- Records of application
-- ----------------------------
INSERT INTO `application` VALUES ('1', '1', '45845', '京东', '100', 'get', 'http://xxx.com', '?bta={}', '80', '1', '2018-06-23 13:46:00');
INSERT INTO `application` VALUES ('2', '1', '45846', '唯品会', '100', 'get', 'http://xxx.com', '?bta={}', '80', '1', '2018-06-24 21:29:42');
INSERT INTO `application` VALUES ('3', '2', '48562', '网易严选', '100', 'get', 'http://xxx.com', '?bta={}', '100', '1', '2018-06-24 21:30:38');
INSERT INTO `application` VALUES ('4', '2', '4585865', '极喵生活', null, 'get', 'https://www.aaa.com', '?name={}&id={}', null, '-1', null);

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `describe` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='渠道信息表';

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('1', '渠道1', 'qd_1', '-1', '联粤', null, '2018-06-23 13:46:35');
INSERT INTO `channel` VALUES ('2', '渠道2', 'qd_2', '1', '联粤', '', '2018-06-24 21:30:58');
INSERT INTO `channel` VALUES ('3', '极喵贷超', 'qd_3', '1', '联粤', '联粤', null);

-- ----------------------------
-- Table structure for click_record
-- ----------------------------
DROP TABLE IF EXISTS `click_record`;
CREATE TABLE `click_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `req_url` varchar(255) DEFAULT NULL,
  `req_param` varchar(255) DEFAULT NULL,
  `app_id` int(11) DEFAULT NULL,
  `channel_id` int(11) DEFAULT NULL,
  `idfa` varchar(255) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `callback_address` varchar(255) DEFAULT NULL,
  `is_activation` tinyint(4) DEFAULT '0' COMMENT '是否激活',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='点击记录表';

-- ----------------------------
-- Records of click_record
-- ----------------------------
INSERT INTO `click_record` VALUES ('1', 'https://', null, '45845', '1', '3BA5ADEE-6467-4FB7-9424-6D873071A73D', '', 'http://127.0.0.1:8080/api/jhNotice', '0', '2018-06-24 20:31:06');
INSERT INTO `click_record` VALUES ('2', 'https://', null, '45845', '1', 'abc-1', null, null, '0', '2018-06-24 20:40:17');
INSERT INTO `click_record` VALUES ('3', 'https://', null, '45845', '1', 'abc-2', null, null, '1', '2018-06-24 20:40:50');

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  `content` mediumtext,
  `addDate` datetime DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '1，正常\n2，删除',
  `commendState` int(11) DEFAULT NULL COMMENT '1，正常\n2，推荐',
  `browses` int(11) DEFAULT NULL COMMENT '浏览数',
  `likes` int(11) DEFAULT NULL COMMENT '喜欢数',
  `comments` int(11) DEFAULT NULL COMMENT '回复数',
  `score` int(11) DEFAULT NULL COMMENT '分数，用于排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES ('1', '科技是第一生产力1', '科技是第一生产力', '2', null, '<p>科技是第一生产力<br></p>', '2017-06-15 19:15:04', '2017-06-15 19:15:04', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('2', '科技是第一生产力2', '科技是第一生产力2', '2', '', '科技是第一生产力2', '2017-06-15 19:17:10', '2017-06-15 19:17:10', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('3', '科技是第一生产力3', '科技是第一生产力3', '2', '', '科技是第一生产力3', '2017-06-15 19:17:31', '2017-06-15 19:17:31', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('4', '科技是第一生产力4', '科技是第一生产力4', '2', '', '科技是第一生产力4', '2017-06-15 19:17:50', '2017-06-15 19:17:50', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('5', '科技是第一生产力5', '科技是第一生产力5', '2', '', '科技是第一生产力5', '2017-06-15 19:17:57', '2017-06-15 19:17:57', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('6', '科技是第一生产力6', '科技是第一生产力6', '2', '', '科技是第一生产力6', '2017-06-15 19:18:02', '2017-06-15 19:18:02', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('7', '科技是第一生产力7', '科技是第一生产力7', '2', '', '科技是第一生产力7', '2017-06-15 19:18:06', '2017-06-15 19:18:06', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('8', '科技是第一生产力8', '科技是第一生产力8', '2', '', '科技是第一生产力8', '2017-06-15 19:18:10', '2017-06-15 19:18:10', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('9', '科技是第一生产力9', '科技是第一生产力9', '2', '', '科技是第一生产力9', '2017-06-15 19:18:21', '2017-06-15 19:18:21', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('10', '科技是第一生产力10', '科技是第一生产力10', '2', '', '科技是第一生产力10', '2017-06-15 19:18:30', '2017-06-15 19:18:30', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('11', '财经天下1', '财经天下1', '1', '', '财经天下1', '2017-06-15 19:19:13', '2017-06-15 19:19:13', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('12', '财经天下2', '财经天下2', '1', '', '财经天下2', '2017-06-15 19:19:19', '2017-06-15 19:19:19', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('13', '财经天下3', '财经天下3', '1', '', '财经天下3', '2017-06-15 19:19:23', '2017-06-15 19:19:23', '1', '2', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('14', '财经天下4', '财经天下4', '1', '', '财经天下4', '2017-06-15 19:19:27', '2017-06-15 19:19:27', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('15', '财经天下5', '财经天下5', '1', '', '财经天下5', '2017-06-15 19:19:31', '2017-06-15 19:19:31', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('16', '财经天下6', '财经天下6', '1', '', '财经天下6', '2017-06-15 19:19:34', '2017-06-15 19:19:34', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('17', '财经天下7', '财经天下7', '1', '', '财经天下7', '2017-06-15 19:19:38', '2017-06-15 19:19:38', '2', '2', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('18', '财经天下8', '财经天下8', '1', '', '财经天下8', '2017-06-15 19:19:42', '2017-06-15 19:19:42', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('19', '财经天下9', '财经天下9', '1', '', '财经天下9', '2017-06-15 19:19:45', '2017-06-15 19:19:45', '1', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('20', '财经天下10', '财经天下10', '1', '', '财经天下10', '2017-06-15 19:19:53', '2017-06-15 19:19:53', '1', '2', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('21', '体育人生1', '体育人生1', '3', '', '体育人生1', '2017-06-15 19:20:55', '2017-06-15 19:20:55', '1', '2', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('22', '体育人生2', '体育人生2', '3', '', '体育人生2', '2017-06-15 19:21:03', '2017-06-15 19:21:03', '2', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('23', '体育人生3', '体育人生3', '3', '', '体育人生3', '2017-06-15 19:21:07', '2017-06-15 19:21:07', '2', '2', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('24', '体育人生4', '体育人生4', '3', '', '体育人生4', '2017-06-15 19:21:11', '2017-06-15 19:21:11', '2', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('25', '体育人生5', '体育人生5', '3', '', '体育人生5', '2017-06-15 19:21:14', '2017-06-15 19:21:14', '2', '2', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('26', '特泰尼克号update', '一个美好的故事update', '3', '/userfiles/20170704160534_351.png', '<p>一个美好的故事update<br></p>', '2017-07-04 11:11:18', '2017-07-04 16:05:34', '2', '2', '11', '12', '15', '111');
INSERT INTO `news` VALUES ('27', '张学友芜湖演唱会', '张学友芜湖演唱会一票难求', '5', '/userfiles/20170704143344_119.png', '<div class=\"container content\">    <h3 class=\"text-center\">云堆微软文分发介绍</h3>    <p class=\"text-center\">自媒体真实流量分发 · 品效合一</p>    <img src=\"http://www.yunduimedia.com/imgs/weixin/weixin01_89df947.png\" width=\"992\" height=\"369\">  </div>            <h3>云堆投放优势</h3>            <img src=\"http://www.yunduimedia.com/imgs/weixin/weixin02_ed2c3a3.png\" width=\"992\" height=\"340\">', '2017-07-04 14:33:44', '2017-07-04 14:33:44', '2', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('28', '', '', '0', '', '', '2017-07-05 11:35:19', '2017-07-05 11:35:19', '2', '1', '0', '0', '0', '0');
INSERT INTO `news` VALUES ('29', '标题', '', '1', '', '<p><br></p>', '2017-07-05 11:46:14', '2017-07-05 14:03:27', '1', '1', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for news_category
-- ----------------------------
DROP TABLE IF EXISTS `news_category`;
CREATE TABLE `news_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  `addDate` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '1，正常\n2，删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news_category
-- ----------------------------
INSERT INTO `news_category` VALUES ('1', '财经', '财经', '', '2017-06-15 18:59:37', '1');
INSERT INTO `news_category` VALUES ('2', '科技', '科技', '', '2017-06-15 18:59:46', '1');
INSERT INTO `news_category` VALUES ('3', '体育', '体育', '', '2017-06-15 18:59:55', '1');
INSERT INTO `news_category` VALUES ('4', '人文', '人文', '', '2017-06-15 19:00:06', '2');
INSERT INTO `news_category` VALUES ('5', '娱乐', '娱乐八卦', '/userfiles/20170704110957.png', '2017-07-04 11:09:57', '2');
INSERT INTO `news_category` VALUES ('6', '历史', '历史文学', '/userfiles/20170705113505.png', '2017-07-05 11:34:31', '1');
INSERT INTO `news_category` VALUES ('7', '地理', '地理', '', '2018-06-16 12:52:22', '1');
