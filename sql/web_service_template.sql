/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.7.17-log : Database - my_project
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`my_project` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `my_project`;

/*Table structure for table `acl_permission` */

DROP TABLE IF EXISTS `acl_permission`;

CREATE TABLE `acl_permission` (
  `id` char(19) NOT NULL COMMENT 'id',
  `pid` char(19) NOT NULL COMMENT '父级id',
  `name` varchar(20) NOT NULL COMMENT '权限名称',
  `type` tinyint(1) NOT NULL COMMENT '类型(1:菜单，2：按钮)',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `path` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态(0:禁止，1：正常)，默认为1',
  `weight` tinyint(1) NOT NULL DEFAULT '5' COMMENT '排序权重，默认值为5，值越大排序越靠前',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0为未删除，1为已删除，默认为0',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `acl_permission` */

insert  into `acl_permission`(`id`,`pid`,`name`,`type`,`permission_value`,`path`,`component`,`icon`,`status`,`weight`,`is_deleted`,`gmt_created`,`gmt_modified`) values ('1','0','全部数据',0,NULL,NULL,NULL,NULL,1,5,0,'2023-10-03 16:10:14','2023-10-03 16:10:14'),('1709129676787609601','1','权限管理',1,NULL,'/acl','Layout','password',1,5,0,'2023-10-03 16:54:04','2023-10-03 16:54:04'),('1709131171264262146','1709129676787609601','用户管理',1,NULL,'user','','',1,5,0,'2023-10-03 17:00:00','2023-10-13 13:29:54'),('1709137426884128769','1709129676787609601','角色管理',1,NULL,'role','','',1,5,0,'2023-10-03 17:24:51','2023-10-13 21:05:46'),('1709137833274437634','1709129676787609601','菜单管理',1,NULL,'menu','/acl/menu/list','',1,4,0,'2023-10-03 17:26:28','2023-10-23 14:16:36'),('1709138071506710530','1709131171264262146','查看',2,'user.list','list','/acl/user/list',NULL,1,5,1,'2023-10-03 17:27:25','2023-10-13 14:06:35'),('1709142047807565826','1709131171264262146','添加',2,'user.add','add','/acl/user/form',NULL,1,5,0,'2023-10-03 17:43:13','2023-10-13 21:21:22'),('1709146225808306178','1709131171264262146','修改',2,'user.update','update/:id','/acl/user/form',NULL,1,5,0,'2023-10-03 17:59:49','2023-10-12 18:23:08'),('1709149718191337473','1709137426884128769','查看',2,'role.list','','',NULL,1,5,1,'2023-10-03 18:13:42','2023-10-03 18:13:42'),('1709463001679126529','1709131171264262146','删除',2,'user.remove','','',NULL,1,5,0,'2023-10-04 14:58:35','2023-10-04 16:10:11'),('1709463749561278465','1709131171264262146','用户角色',2,'user.viewRole','role/:id','/acl/user/roleForm',NULL,1,5,0,'2023-10-04 15:01:33','2023-10-13 20:55:20'),('1709464171340488705','1709137426884128769','添加',2,'role.add','add','/acl/role/form',NULL,1,5,0,'2023-10-04 15:03:13','2023-10-12 19:00:42'),('1709464608860921857','1709137426884128769','修改',2,'role.update','update/:id','/acl/role/form',NULL,1,5,0,'2023-10-04 15:04:58','2023-10-12 19:24:06'),('1709465844674846722','1709137426884128769','删除',2,'role.remove','','',NULL,1,5,0,'2023-10-04 15:09:52','2023-10-04 15:09:52'),('1709469782828507137','1','讲师管理',1,NULL,'/teacher','Layout','peoples',1,5,0,'2023-10-04 15:25:31','2023-10-23 16:06:26'),('1709470180863762434','1','课程管理',1,NULL,'/course','Layout','excel',1,5,0,'2023-10-04 15:27:06','2023-10-23 16:06:36'),('1709471069422870530','1709469782828507137','讲师列表',1,NULL,'list','/teacher/list','',1,5,0,'2023-10-04 15:30:38','2023-10-04 15:30:38'),('1709471342128128002','1709470180863762434','课程列表',1,NULL,'list','/course/list','',1,5,0,'2023-10-04 15:31:43','2023-10-04 15:31:43'),('1709472319820390401','1709137833274437634','查看',2,'permission.list','','',NULL,1,5,0,'2023-10-04 15:35:36','2023-10-04 15:35:36'),('1709472619864121345','1709137833274437634','添加',2,'permission.add','','',NULL,1,5,0,'2023-10-04 15:36:48','2023-10-04 15:36:48'),('1709472698301800449','1709137833274437634','修改',2,'permission.update','','',NULL,1,5,0,'2023-10-04 15:37:06','2023-10-04 15:37:06'),('1709472805894086658','1709137833274437634','删除',2,'permission.remove','','',NULL,1,5,0,'2023-10-04 15:37:32','2023-10-04 15:37:32'),('1709883186730876930','1709471069422870530','查看',2,'teacher.list','','',NULL,1,5,0,'2023-10-05 18:48:14','2023-10-23 15:22:37'),('1710494487291125762','1709131171264262146','分配角色',2,'user.assignRole','','',NULL,1,5,0,'2023-10-07 11:17:20','2023-10-13 21:04:34'),('1710494858591887361','1709137426884128769','角色权限',2,'role.viewPermission','permission/:id','/acl/role/authorityForm',NULL,1,5,0,'2023-10-07 11:18:48','2023-10-12 19:24:18'),('1710495602128740354','1709137426884128769','分配权限',2,'role.assignPermission','','',NULL,1,5,0,'2023-10-07 11:21:46','2023-10-07 11:21:46'),('1711216032682606593','1709471069422870530','test',2,'test','','',NULL,1,5,1,'2023-10-09 11:04:30','2023-10-09 11:04:30'),('1712421386879823874','1','test',1,NULL,'/aa','/aa','eye-open',1,5,1,'2023-10-12 18:54:09','2023-10-12 18:54:09'),('1712682859526885378','1709131171264262146','用户列表',1,NULL,'list','/acl/user/list','',1,8,0,'2023-10-13 12:13:08','2023-10-13 12:13:08'),('1712723254243762177','1709137426884128769','角色列表',1,NULL,'list','/acl/role/list','',1,7,0,'2023-10-13 14:53:39','2023-10-13 14:53:39'),('1712819526430629889','1709131171264262146','用户添加',1,NULL,'add','/acl/user/form','',1,6,1,'2023-10-13 21:16:12','2023-10-13 21:16:12'),('1716318197000482818','1709131171264262146','查看',2,'user.list','','',NULL,1,6,0,'2023-10-23 12:58:40','2023-10-23 12:58:53'),('1716324880212865026','1709137426884128769','查看',2,'role.list','','',NULL,1,6,0,'2023-10-23 13:25:14','2023-10-23 13:25:14');

/*Table structure for table `acl_role` */

DROP TABLE IF EXISTS `acl_role`;

CREATE TABLE `acl_role` (
  `id` char(19) NOT NULL COMMENT 'id',
  `name` varchar(20) NOT NULL COMMENT '角色名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0为未删除，1为已删除，默认为0',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `acl_role` */

insert  into `acl_role`(`id`,`name`,`is_deleted`,`gmt_created`,`gmt_modified`) values ('1705819166965018625','课程管理员',0,'2023-09-24 13:39:17','2023-09-24 13:39:17'),('1707330185570861058','test',0,'2023-09-28 17:43:32','2023-09-28 17:43:32'),('1707330229493612546','test1',1,'2023-09-28 17:43:42','2023-09-28 17:43:42'),('1707348824286142466','普通管理员',0,'2023-09-28 18:57:35','2023-09-28 18:57:35'),('1712730197125505025','超级管理员',0,'2023-10-13 15:21:15','2023-10-13 15:21:15');

/*Table structure for table `acl_role_permission` */

DROP TABLE IF EXISTS `acl_role_permission`;

CREATE TABLE `acl_role_permission` (
  `id` char(19) NOT NULL COMMENT '主键id',
  `role_id` char(19) NOT NULL COMMENT '角色id',
  `permission_id` char(19) NOT NULL COMMENT '权限id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0为未删除，1为已删除，默认为0',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `acl_role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `acl_role` (`id`),
  CONSTRAINT `acl_role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `acl_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限';

/*Data for the table `acl_role_permission` */

insert  into `acl_role_permission`(`id`,`role_id`,`permission_id`,`is_deleted`,`gmt_created`,`gmt_modified`) values ('1709898615998119938','1707330185570861058','1709470180863762434',1,'2023-10-05 19:49:33','2023-10-05 19:49:33'),('1709898615998119939','1707330185570861058','1709471342128128002',1,'2023-10-05 19:49:33','2023-10-05 19:49:33'),('1709898615998119940','1707330185570861058','1',0,'2023-10-05 19:49:33','2023-10-05 19:49:33'),('1709899553131466754','1707348824286142466','1709131171264262146',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553202769922','1707348824286142466','1709138071506710530',1,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553202769923','1707348824286142466','1709142047807565826',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553202769924','1707348824286142466','1709146225808306178',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553269878785','1707348824286142466','1709463001679126529',1,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553278267393','1707348824286142466','1709463749561278465',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553278267394','1707348824286142466','1709472319820390401',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553278267395','1707348824286142466','1',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553278267396','1707348824286142466','1709129676787609601',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899553345376257','1707348824286142466','1709137833274437634',0,'2023-10-05 19:53:17','2023-10-05 19:53:17'),('1709899650636451842','1707330185570861058','1709137426884128769',0,'2023-10-05 19:53:40','2023-10-05 19:53:40'),('1709899650636451843','1707330185570861058','1709149718191337473',1,'2023-10-05 19:53:40','2023-10-05 19:53:40'),('1709899650703560706','1707330185570861058','1709464171340488705',0,'2023-10-05 19:53:40','2023-10-05 19:53:40'),('1709899650703560707','1707330185570861058','1709464608860921857',0,'2023-10-05 19:53:40','2023-10-05 19:53:40'),('1709899650770669570','1707330185570861058','1709465844674846722',1,'2023-10-05 19:53:40','2023-10-05 19:53:40'),('1709899650770669571','1707330185570861058','1709129676787609601',0,'2023-10-05 19:53:40','2023-10-05 19:53:40'),('1709899987929796610','1705819166965018625','1709149718191337473',1,'2023-10-05 19:55:00','2023-10-05 19:55:00'),('1709899987929796611','1705819166965018625','1',0,'2023-10-05 19:55:00','2023-10-05 19:55:00'),('1709899987980128258','1705819166965018625','1709129676787609601',1,'2023-10-05 19:55:00','2023-10-05 19:55:00'),('1709899987980128259','1705819166965018625','1709137426884128769',1,'2023-10-05 19:55:00','2023-10-05 19:55:00'),('1710486462211842050','1705819166965018625','1709470180863762434',0,'2023-10-07 10:45:27','2023-10-07 10:45:27'),('1710486462278950914','1705819166965018625','1709471342128128002',0,'2023-10-07 10:45:27','2023-10-07 10:45:27'),('1710487683391840258','1707330185570861058','1709138071506710530',1,'2023-10-07 10:50:18','2023-10-07 10:50:18'),('1710487683391840259','1707330185570861058','1709131171264262146',1,'2023-10-07 10:50:18','2023-10-07 10:50:18'),('1710488268493053954','1707348824286142466','1709149718191337473',1,'2023-10-07 10:52:37','2023-10-07 10:52:37'),('1710488268493053955','1707348824286142466','1709464171340488705',0,'2023-10-07 10:52:37','2023-10-07 10:52:37'),('1710488268493053956','1707348824286142466','1709464608860921857',0,'2023-10-07 10:52:37','2023-10-07 10:52:37'),('1710488268493053957','1707348824286142466','1709472619864121345',0,'2023-10-07 10:52:37','2023-10-07 10:52:37'),('1710488268564357122','1707348824286142466','1709472698301800449',0,'2023-10-07 10:52:37','2023-10-07 10:52:37'),('1710488268564357123','1707348824286142466','1709137426884128769',0,'2023-10-07 10:52:37','2023-10-07 10:52:37'),('1710488643363168258','1707330229493612546','1709470180863762434',0,'2023-10-07 10:54:07','2023-10-07 10:54:07'),('1710488643363168259','1707330229493612546','1709471342128128002',0,'2023-10-07 10:54:07','2023-10-07 10:54:07'),('1710488643363168260','1707330229493612546','1',0,'2023-10-07 10:54:07','2023-10-07 10:54:07'),('1711221413718786049','1707330185570861058','1711216032682606593',1,'2023-10-09 11:25:53','2023-10-09 11:25:53'),('1711221413718786050','1707330185570861058','1709469782828507137',1,'2023-10-09 11:25:53','2023-10-09 11:25:53'),('1711221413785894913','1707330185570861058','1709471069422870530',1,'2023-10-09 11:25:53','2023-10-09 11:25:53'),('1712712325150138369','1707348824286142466','1712682859526885378',0,'2023-10-13 14:10:14','2023-10-13 14:10:14'),('1712723436901507073','1707348824286142466','1712723254243762177',0,'2023-10-13 14:54:23','2023-10-13 14:54:23'),('1712733368161935362','1712730197125505025','1',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368161935363','1712730197125505025','1709129676787609601',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368161935364','1712730197125505025','1709131171264262146',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368161935365','1712730197125505025','1709138071506710530',1,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368161935366','1712730197125505025','1709142047807565826',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368229044226','1712730197125505025','1709146225808306178',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368233238530','1712730197125505025','1709463001679126529',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368233238531','1712730197125505025','1709463749561278465',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368233238532','1712730197125505025','1710494487291125762',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368233238533','1712730197125505025','1712682859526885378',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368233238534','1712730197125505025','1709137426884128769',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368233238535','1712730197125505025','1709149718191337473',1,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368300347394','1712730197125505025','1709464171340488705',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368300347395','1712730197125505025','1709464608860921857',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368300347396','1712730197125505025','1709465844674846722',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368300347397','1712730197125505025','1710494858591887361',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368300347398','1712730197125505025','1710495602128740354',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368300347399','1712730197125505025','1712723254243762177',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368367456258','1712730197125505025','1709137833274437634',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368367456259','1712730197125505025','1709472319820390401',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368367456260','1712730197125505025','1709472619864121345',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368367456261','1712730197125505025','1709472698301800449',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368367456262','1712730197125505025','1709472805894086658',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368367456263','1712730197125505025','1709469782828507137',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368367456264','1712730197125505025','1709471069422870530',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368442953730','1712730197125505025','1709883186730876930',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368442953731','1712730197125505025','1709470180863762434',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712733368442953732','1712730197125505025','1709471342128128002',0,'2023-10-13 15:33:51','2023-10-13 15:33:51'),('1712734368121757697','1712730197125505025','1709138071506710530',1,'2023-10-13 15:37:49','2023-10-13 15:37:49'),('1712735734437257218','1707348824286142466','1709138071506710530',1,'2023-10-13 15:43:15','2023-10-13 15:43:15'),('1712797584923303938','1712730197125505025','1709138071506710530',1,'2023-10-13 19:49:01','2023-10-13 19:49:01'),('1712797628325961730','1707348824286142466','1709138071506710530',1,'2023-10-13 19:49:12','2023-10-13 19:49:12'),('1712820276380573698','1712730197125505025','1712819526430629889',1,'2023-10-13 21:19:11','2023-10-13 21:19:11'),('1713851351399718914','1707348824286142466','1710494858591887361',0,'2023-10-16 17:36:19','2023-10-16 17:36:19'),('1716318580598943746','1712730197125505025','1716318197000482818',0,'2023-10-23 13:00:12','2023-10-23 13:00:12'),('1716325706763382785','1712730197125505025','1716324880212865026',0,'2023-10-23 13:28:31','2023-10-23 13:28:31'),('1716326315566608385','1707348824286142466','1716318197000482818',0,'2023-10-23 13:30:56','2023-10-23 13:30:56'),('1716326315566608386','1707348824286142466','1716324880212865026',0,'2023-10-23 13:30:56','2023-10-23 13:30:56'),('1716326315566608387','1707348824286142466','1709469782828507137',0,'2023-10-23 13:30:56','2023-10-23 13:30:56'),('1716326315717603330','1707348824286142466','1709471069422870530',0,'2023-10-23 13:30:56','2023-10-23 13:30:56'),('1716326315738574850','1707348824286142466','1709883186730876930',0,'2023-10-23 13:30:56','2023-10-23 13:30:56'),('1716326315738574851','1707348824286142466','1709470180863762434',0,'2023-10-23 13:30:56','2023-10-23 13:30:56'),('1716326315738574852','1707348824286142466','1709471342128128002',0,'2023-10-23 13:30:56','2023-10-23 13:30:56');

/*Table structure for table `acl_user` */

DROP TABLE IF EXISTS `acl_user`;

CREATE TABLE `acl_user` (
  `id` char(19) NOT NULL COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0为未删除，1为已删除，默认为0',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

/*Data for the table `acl_user` */

insert  into `acl_user`(`id`,`username`,`password`,`role_name`,`is_deleted`,`gmt_created`,`gmt_modified`) values ('1707348019730554881','courseadmin','75b91b499e317a752b863694ab16ce84','课程管理员',0,'2023-09-28 18:54:24','2023-10-05 19:42:47'),('1709058753682214913','admin','75b91b499e317a752b863694ab16ce84','普通管理员',0,'2023-10-03 12:12:14','2023-10-16 17:35:33'),('1709058881730121729','superadmin','3b0d671a8e7caa1b76cae7787b377dec','超级管理员',0,'2023-10-03 12:12:45','2024-02-23 18:03:01'),('1712727198604992513','test','75b91b499e317a752b863694ab16ce84',NULL,0,'2023-10-13 15:09:20','2023-10-13 15:09:20');

/*Table structure for table `acl_user_role` */

DROP TABLE IF EXISTS `acl_user_role`;

CREATE TABLE `acl_user_role` (
  `id` char(19) NOT NULL COMMENT '主键id',
  `user_id` char(19) NOT NULL COMMENT '用户id',
  `role_id` char(19) NOT NULL COMMENT '角色id',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0为未删除，1为已删除，默认为0',
  `gmt_created` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `acl_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `acl_user` (`id`),
  CONSTRAINT `acl_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `acl_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

/*Data for the table `acl_user_role` */

insert  into `acl_user_role`(`id`,`user_id`,`role_id`,`is_deleted`,`gmt_created`,`gmt_modified`) values ('1709058811039322114','1709058753682214913','1707348824286142466',1,'2023-10-03 12:12:28','2023-10-03 12:12:28'),('1709058811039322115','1709058753682214913','1705819166965018625',1,'2023-10-03 12:12:28','2023-10-03 12:12:28'),('1709058923429892097','1709058881730121729','1705819166965018625',1,'2023-10-03 12:12:55','2023-10-03 12:12:55'),('1709058923429892098','1709058881730121729','1707330185570861058',1,'2023-10-03 12:12:55','2023-10-03 12:12:55'),('1709058923429892099','1709058881730121729','1707330229493612546',1,'2023-10-03 12:12:55','2023-10-03 12:12:55'),('1709058923492806657','1709058881730121729','1707348824286142466',1,'2023-10-03 12:12:55','2023-10-03 12:12:55'),('1709896911982428161','1707348019730554881','1705819166965018625',0,'2023-10-05 19:42:47','2023-10-05 19:42:47'),('1712730472330567682','1709058881730121729','1712730197125505025',0,'2023-10-13 15:22:20','2023-10-13 15:22:20'),('1713851158499483650','1709058753682214913','1707348824286142466',0,'2023-10-16 17:35:33','2023-10-16 17:35:33');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
