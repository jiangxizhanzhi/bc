-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 10.100.1.26    Database: ojj
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.7-MariaDB-1:10.4.7+maria~bionic

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attachment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `file_name` varchar(128) NOT NULL,
  `file_path` varchar(256) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `public` int(1) NOT NULL,
  `business_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=314 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_business_template`
--

DROP TABLE IF EXISTS `cr_business_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_business_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `local_scene_id` varchar(40) DEFAULT NULL,
  `topo_id` varchar(40) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `business_type` int(11) NOT NULL,
  `difficulty` int(11) NOT NULL,
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user_id` varchar(40) NOT NULL,
  `operation_auth_type` int(11) DEFAULT NULL,
  `operation_auth_ids` text DEFAULT NULL,
  `read_auth_type` int(11) DEFAULT NULL,
  `read_auth_ids` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=630 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_mission`
--

DROP TABLE IF EXISTS `cr_mission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_mission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(32) NOT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `score` smallint(6) DEFAULT NULL,
  `skill_points` varchar(40) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `option` varchar(512) DEFAULT NULL,
  `type` tinyint(40) DEFAULT NULL,
  `category_id` varchar(32) DEFAULT NULL,
  `write_up` text DEFAULT NULL,
  `mission_stage` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `show_writeup` tinyint(1) DEFAULT NULL,
  `room_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1234 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_mission_category`
--

DROP TABLE IF EXISTS `cr_mission_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_mission_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_mission_scheme_template`
--

DROP TABLE IF EXISTS `cr_mission_scheme_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_mission_scheme_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(32) NOT NULL,
  `create_user_id` varchar(32) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `template_data` text DEFAULT NULL,
  `template_config` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_mission_template`
--

DROP TABLE IF EXISTS `cr_mission_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_mission_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `score` smallint(6) DEFAULT NULL,
  `skill_points` varchar(40) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `option` varchar(512) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `category_id` varchar(64) DEFAULT NULL,
  `write_up` text DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `template_id` varchar(64) DEFAULT NULL,
  `attachment` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1993 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_rb_device`
--

DROP TABLE IF EXISTS `cr_rb_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_rb_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) DEFAULT NULL,
  `room_id` varchar(64) DEFAULT NULL,
  `device_id` varchar(64) DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=225 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_rb_room`
--

DROP TABLE IF EXISTS `cr_rb_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_rb_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `template_id` varchar(40) NOT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `description` varchar(512) NOT NULL,
  `sub_type` int(11) NOT NULL,
  `pre_skill` text NOT NULL,
  `train_skill` text NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `difficulty` int(11) NOT NULL,
  `training_status` int(11) DEFAULT NULL,
  `scene_file` text DEFAULT NULL,
  `create_user_id` varchar(40) DEFAULT NULL,
  `modify_user_id` varchar(40) DEFAULT NULL,
  `local_scene_id` varchar(40) DEFAULT NULL,
  `copyed_topo_id` varchar(40) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `enable_recording` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `team_template_id` (`template_id`) USING BTREE,
  KEY `training_status` (`training_status`),
  KEY `create_user_id` (`create_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=316 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_rb_template`
--

DROP TABLE IF EXISTS `cr_rb_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_rb_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `business_template_id` varchar(40) NOT NULL,
  `pre_skill` varchar(40) NOT NULL,
  `train_skill` varchar(40) NOT NULL,
  `role_config` varchar(4000) NOT NULL,
  `sub_type` varchar(40) NOT NULL,
  `duration_time` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `business_template_id` (`business_template_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_rb_time_management`
--

DROP TABLE IF EXISTS `cr_rb_time_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_rb_time_management` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `room_id` varchar(40) NOT NULL,
  `user_id` varchar(40) DEFAULT NULL,
  `astronomical_time` datetime NOT NULL,
  `combat_time` datetime NOT NULL,
  `rate` smallint(5) unsigned NOT NULL,
  `status` smallint(5) unsigned NOT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `create_time` datetime(6) NOT NULL,
  `update_user_id` varchar(64) DEFAULT NULL,
  `update_time` datetime(6) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_role`
--

DROP TABLE IF EXISTS `cr_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(200) NOT NULL,
  `room_id` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `role_group` int(11) NOT NULL,
  `nodes` varchar(4000) DEFAULT NULL,
  `user_ids` text NOT NULL,
  `exercise_paper` varchar(4000) DEFAULT NULL,
  `parent_resource_id` varchar(40) DEFAULT NULL,
  `mission_scheme` varchar(4000) DEFAULT NULL,
  `business_type` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `business_instance_id` (`room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1272 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_score`
--

DROP TABLE IF EXISTS `cr_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `room_id` varchar(40) NOT NULL,
  `user_id` varchar(40) NOT NULL,
  `training_score` varchar(4000) DEFAULT NULL,
  `training_percentage` varchar(40) NOT NULL,
  `skill_percentage` varchar(40) NOT NULL,
  `skill_score` varchar(40) NOT NULL,
  `comment` varchar(40) NOT NULL,
  `final_score` varchar(40) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_single_room`
--

DROP TABLE IF EXISTS `cr_single_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_single_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `business_template_id` varchar(64) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `training_record` longtext DEFAULT NULL,
  `user_id` varchar(64) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `local_scene_id` char(32) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  UNIQUE KEY `business_template_id__user_id__uniq` (`business_template_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_single_template`
--

DROP TABLE IF EXISTS `cr_single_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_single_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `business_template_id` varchar(64) NOT NULL,
  `pre_skills` varchar(256) NOT NULL,
  `train_skills` varchar(256) NOT NULL,
  `sub_type` varchar(40) NOT NULL,
  `duration_time` int(10) unsigned NOT NULL,
  `missions_scheme` longtext NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `business_template_id` (`business_template_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_task`
--

DROP TABLE IF EXISTS `cr_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `mission_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `user_answer` varchar(255) DEFAULT NULL,
  `release_time` datetime DEFAULT NULL,
  `last_submit_time` datetime DEFAULT NULL,
  `task_start_time` datetime DEFAULT NULL,
  `room_id` varchar(64) DEFAULT NULL,
  `role_id` varchar(64) DEFAULT NULL,
  `business_type` int(11) DEFAULT NULL,
  `score` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`),
  KEY `mission_id` (`mission_id`),
  KEY `user_id` (`user_id`),
  KEY `room_id` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=400 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_task_log`
--

DROP TABLE IF EXISTS `cr_task_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_task_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `room_id` varchar(64) DEFAULT NULL,
  `mission_id` varchar(64) DEFAULT NULL,
  `action_code` tinyint(4) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `task_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `mission_id` (`mission_id`) USING BTREE,
  KEY `room_id` (`room_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `action_code` (`action_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1049 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_task_submit`
--

DROP TABLE IF EXISTS `cr_task_submit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_task_submit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `room_id` varchar(64) DEFAULT NULL,
  `task_id` varchar(64) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `answer_extension` varchar(128) DEFAULT NULL,
  `evaluator_id` varchar(64) DEFAULT NULL,
  `evaluate_time` datetime DEFAULT NULL,
  `evaluate_status` tinyint(4) DEFAULT NULL,
  `reason` tinyint(4) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `score` smallint(6) DEFAULT NULL,
  `mission_score` smallint(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `task_id` (`task_id`) USING BTREE,
  KEY `room_id` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_team_room`
--

DROP TABLE IF EXISTS `cr_team_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_team_room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `team_template_id` varchar(40) NOT NULL,
  `team_template_name` varchar(255) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `description` varchar(512) NOT NULL,
  `sub_type` int(11) NOT NULL,
  `pre_skills` varchar(4000) NOT NULL,
  `train_skills` varchar(4000) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `difficulty` int(11) NOT NULL,
  `training_status` int(11) DEFAULT NULL,
  `time_line_config` varchar(4000) NOT NULL,
  `create_user_id` varchar(40) DEFAULT NULL,
  `modify_user_id` varchar(40) DEFAULT NULL,
  `local_scene_id` varchar(40) DEFAULT NULL,
  `copyed_topo_id` varchar(40) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `enable_recording` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `team_template_id` (`team_template_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_team_template`
--

DROP TABLE IF EXISTS `cr_team_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_team_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `business_template_id` varchar(40) NOT NULL,
  `pre_skills` varchar(40) NOT NULL,
  `train_skills` varchar(40) NOT NULL,
  `role_config` varchar(4000) NOT NULL,
  `sub_type` varchar(40) NOT NULL,
  `time_line_config` varchar(4000) NOT NULL,
  `people_number` int(10) NOT NULL,
  `duration_time` int(10) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE,
  KEY `business_template_id` (`business_template_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_tool_exploit`
--

DROP TABLE IF EXISTS `cr_tool_exploit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_tool_exploit` (
  `id` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT current_timestamp(),
  `title` varchar(255) DEFAULT NULL,
  `edb_id` int(11) DEFAULT NULL,
  `cve` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `verified` tinyint(1) DEFAULT NULL,
  `exploit` varchar(255) DEFAULT NULL,
  `suffix` varchar(255) DEFAULT '',
  `content` longtext DEFAULT NULL,
  `vulnerable_app` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `cr_tool_exploit_id_uindex` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_tool_ghdb`
--

DROP TABLE IF EXISTS `cr_tool_ghdb`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_tool_ghdb` (
  `id` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `ghdb_id` int(11) DEFAULT NULL,
  `google_search` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `published` varchar(255) DEFAULT NULL,
  `google_dork_description` varchar(255) DEFAULT NULL,
  `content` longtext DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_tool_shellcode`
--

DROP TABLE IF EXISTS `cr_tool_shellcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_tool_shellcode` (
  `id` int(11) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT current_timestamp(),
  `title` varchar(255) DEFAULT '',
  `edb_id` int(11) DEFAULT NULL,
  `size` varchar(255) DEFAULT '',
  `author` varchar(255) DEFAULT '',
  `type` varchar(255) DEFAULT NULL,
  `platform` varchar(64) DEFAULT NULL,
  `published` varchar(255) DEFAULT NULL,
  `edb_verified` tinyint(1) DEFAULT NULL,
  `shellcode` varchar(255) DEFAULT NULL,
  `suffix` varchar(255) DEFAULT '',
  `content` longtext DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_tool_tool`
--

DROP TABLE IF EXISTS `cr_tool_tool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_tool_tool` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `cover` varchar(64) DEFAULT NULL,
  `homepage` varchar(1024) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `manual` varchar(256) DEFAULT NULL,
  `image_resource_id` varchar(64) DEFAULT NULL,
  `visible` tinyint(4) DEFAULT NULL,
  `tool_type` smallint(6) DEFAULT NULL,
  `platforms` varchar(128) DEFAULT NULL,
  `language` varchar(64) DEFAULT NULL,
  `introduction` varchar(10000) DEFAULT NULL,
  `public` varchar(64) DEFAULT NULL,
  `cracked` tinyint(4) DEFAULT NULL,
  `tag` varchar(256) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL,
  `scene` varchar(128) DEFAULT NULL,
  `version` varchar(512) DEFAULT NULL,
  `files` varchar(128) DEFAULT NULL,
  `cover_url` varchar(128) DEFAULT NULL,
  `serial_version_uid` longtext DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `cr_tool_tool_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7013 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_tool_toolcategory`
--

DROP TABLE IF EXISTS `cr_tool_toolcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_tool_toolcategory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) DEFAULT NULL,
  `cn_name` varchar(64) DEFAULT NULL,
  `en_name` varchar(64) DEFAULT NULL,
  `serial_version_uid` longtext DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_tool_toolfile`
--

DROP TABLE IF EXISTS `cr_tool_toolfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_tool_toolfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(256) DEFAULT NULL,
  `save_path` varchar(256) DEFAULT NULL,
  `file_url` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11529 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cr_train_record`
--

DROP TABLE IF EXISTS `cr_train_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cr_train_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `business_type` int(10) unsigned NOT NULL,
  `business_template_id` varchar(64) NOT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `room_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `extra` longtext DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `business_template_id` (`business_template_id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`) USING BTREE,
  KEY `flyway_schema_history_s_idx` (`success`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `init_rtks_course_related`
--

DROP TABLE IF EXISTS `init_rtks_course_related`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `init_rtks_course_related` (
  `resource_id` varchar(64) NOT NULL COMMENT '课程id',
  `related_id` varchar(64) NOT NULL COMMENT '知识点id',
  `type` int(11) NOT NULL COMMENT '1：知识点;2：技能点',
  `weight` int(11) NOT NULL COMMENT '权重',
  KEY `course_id` (`resource_id`) USING BTREE,
  KEY `related_id` (`related_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `init_rtks_knowledge`
--

DROP TABLE IF EXISTS `init_rtks_knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `init_rtks_knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `category_id` varchar(40) NOT NULL,
  `content` varchar(512) NOT NULL,
  `courses` text NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=363 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `init_rtks_knowledge_category`
--

DROP TABLE IF EXISTS `init_rtks_knowledge_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `init_rtks_knowledge_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `init_rtks_level`
--

DROP TABLE IF EXISTS `init_rtks_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `init_rtks_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `frame` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `init_rtks_role`
--

DROP TABLE IF EXISTS `init_rtks_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `init_rtks_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` longtext DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `level_id` varchar(64) NOT NULL,
  `category` varchar(64) NOT NULL,
  `task_ids` text NOT NULL,
  `knowledge_id_weights` text NOT NULL,
  `knowledge_ids` text NOT NULL,
  `skill_id_weights` text NOT NULL,
  `skill_ids` text NOT NULL,
  `ability_ids` text NOT NULL,
  `skill_courses` text DEFAULT NULL,
  `knowledge_courses` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `init_rtks_task`
--

DROP TABLE IF EXISTS `init_rtks_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `init_rtks_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `local_scene`
--

DROP TABLE IF EXISTS `local_scene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `local_scene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(32) NOT NULL,
  `mp_scene_id` varchar(32) DEFAULT NULL,
  `business_code` varchar(11) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `destroy_time` datetime DEFAULT NULL,
  `destroy_mode` int(11) DEFAULT NULL,
  `business_instance_id` varchar(65) NOT NULL,
  `scene_status` int(11) DEFAULT NULL,
  `pause_time` datetime DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `topo_id` varchar(64) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `scene_ttl` int(11) DEFAULT NULL,
  `node_count` int(11) DEFAULT NULL,
  `meta` longtext DEFAULT NULL,
  `scene_name` varchar(256) DEFAULT NULL,
  `scene_attr` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resourceIdUnique` (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1175 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_classroom`
--

DROP TABLE IF EXISTS `oj_classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_classroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `course_id` varchar(64) NOT NULL,
  `lesson_id` varchar(64) NOT NULL,
  `organization_id` varchar(64) NOT NULL,
  `organization_ids` varchar(1024) NOT NULL,
  `create_user_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_classroom_group_archive`
--

DROP TABLE IF EXISTS `oj_classroom_group_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_classroom_group_archive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL,
  `groups` longtext NOT NULL,
  `classroom_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_classroom_group_info`
--

DROP TABLE IF EXISTS `oj_classroom_group_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_classroom_group_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `classroom_id` varchar(64) NOT NULL,
  `group_no` int(64) NOT NULL,
  `group_user_ids` longtext NOT NULL,
  `destroy_delay` int(11) DEFAULT NULL,
  `scene_share_mode` int(11) DEFAULT NULL,
  `lesson_id` varchar(64) NOT NULL,
  `topo_id` varchar(64) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4190 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_classroom_sign`
--

DROP TABLE IF EXISTS `oj_classroom_sign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_classroom_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `classroom_id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `sign_in` int(1) NOT NULL,
  `sign_in_time` datetime NOT NULL,
  `need_help` int(1) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_user_uniq` (`classroom_id`,`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_comment`
--

DROP TABLE IF EXISTS `oj_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `reply_id` varchar(64) DEFAULT NULL,
  `business_id` varchar(64) NOT NULL,
  `theme_name` varchar(128) NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `content` varchar(1024) NOT NULL,
  `business_type` int(11) NOT NULL,
  `root_id` varchar(64) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_comment_like`
--

DROP TABLE IF EXISTS `oj_comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_comment_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `comment_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_comment_uniq` (`user_id`,`comment_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_course`
--

DROP TABLE IF EXISTS `oj_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `builtin` int(1) NOT NULL,
  `operation_auth_type` int(1) NOT NULL,
  `operation_auth_ids` longtext NOT NULL,
  `read_auth_type` int(1) NOT NULL,
  `read_auth_ids` varchar(4096) NOT NULL,
  `name` varchar(256) NOT NULL,
  `description` longtext DEFAULT NULL,
  `logo` varchar(512) DEFAULT NULL,
  `outline` longtext DEFAULT NULL,
  `difficulty` int(1) NOT NULL,
  `create_user_id` varchar(64) NOT NULL,
  `modify_user_id` varchar(64) NOT NULL,
  `public` int(1) NOT NULL,
  `course_type` int(1) DEFAULT NULL,
  `heat` int(11) NOT NULL,
  `lecturer` varchar(64) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `course_num` varchar(64) DEFAULT NULL,
  `category_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_unq` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_course_category`
--

DROP TABLE IF EXISTS `oj_course_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_course_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL,
  `course_type` int(2) NOT NULL COMMENT '课程类型0=基础 2=攻击 3=防御',
  `code` varchar(2096) NOT NULL COMMENT '节点编码  父级节点code + '','' + 当前节点编号',
  `update_time` datetime NOT NULL,
  `create_time` datetime NOT NULL,
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级节点编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_course_lesson`
--

DROP TABLE IF EXISTS `oj_course_lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_course_lesson` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `course_id` varchar(64) NOT NULL,
  `builtin` int(1) NOT NULL,
  `name` varchar(256) NOT NULL,
  `type` int(1) DEFAULT NULL,
  `handout_type` int(1) DEFAULT NULL,
  `handout_url` longtext DEFAULT NULL,
  `video_url` varchar(512) DEFAULT NULL,
  `difficulty` int(1) NOT NULL,
  `public` int(1) NOT NULL,
  `attachment_ids` varchar(1024) DEFAULT NULL,
  `compulsory` int(1) NOT NULL,
  `duration` int(11) DEFAULT NULL,
  `destroy_delay` int(11) DEFAULT NULL,
  `topo_id` varchar(64) DEFAULT NULL,
  `knowledge` varchar(2048) DEFAULT NULL,
  `scene_share_mode` int(1) DEFAULT NULL,
  `origin_markdown_file` longtext DEFAULT NULL,
  `create_user_id` varchar(64) DEFAULT NULL,
  `heat` int(11) NOT NULL,
  `paper_id` varchar(64) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `paper_public` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resourse_id_uniq` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_lesson_config`
--

DROP TABLE IF EXISTS `oj_lesson_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_lesson_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `index` int(11) NOT NULL,
  `parent_id` varchar(64) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `node_type` int(1) NOT NULL,
  `course_id` varchar(64) NOT NULL,
  `lesson_id` varchar(64) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=675 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_lesson_paper_evaluate`
--

DROP TABLE IF EXISTS `oj_lesson_paper_evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_lesson_paper_evaluate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `lesson_id` varchar(64) NOT NULL,
  `score` double(11,1) NOT NULL,
  `record` longtext NOT NULL,
  `submit_user_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `total_score` double(11,1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_lesson_record`
--

DROP TABLE IF EXISTS `oj_lesson_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_lesson_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `lesson_id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `course_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_lesson_uniq` (`user_id`,`lesson_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1668 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_local_scene`
--

DROP TABLE IF EXISTS `oj_local_scene`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_local_scene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `shared` int(1) NOT NULL,
  `from_web` int(1) DEFAULT NULL,
  `flags` varchar(256) DEFAULT NULL,
  `business_id` varchar(128) NOT NULL,
  `business_type` int(1) NOT NULL,
  `local_scene_id` varchar(64) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=453 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_note`
--

DROP TABLE IF EXISTS `oj_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `business_type` int(11) NOT NULL COMMENT '1=课程 2=课时 3=练习 4=课后练习',
  `content` longtext NOT NULL COMMENT '笔记内容',
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `business_id` varchar(64) NOT NULL COMMENT '资源编号',
  `note_type` int(11) NOT NULL COMMENT '1=笔记 2=实验报告',
  `operation_auth_type` int(1) NOT NULL COMMENT '授权操作类类型 1=共享 2=自定义',
  `operation_auth_ids` varchar(2048) DEFAULT NULL,
  `read_auth_type` int(1) NOT NULL COMMENT '同上',
  `read_auth_ids` varchar(2048) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `public` int(1) NOT NULL COMMENT '是否公开',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_index` (`resource_id`) USING BTREE,
  UNIQUE KEY `user_bt_bi_uniq` (`user_id`,`note_type`,`business_type`,`business_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=285 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_note_evaluate`
--

DROP TABLE IF EXISTS `oj_note_evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_note_evaluate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `score` int(11) NOT NULL,
  `passed` int(1) NOT NULL,
  `evaluate_comment` varchar(2048) DEFAULT NULL,
  `evaluator_id` varchar(64) NOT NULL,
  `evaluate_time` datetime NOT NULL,
  `note_id` varchar(64) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `note_id_uniq` (`note_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_paper`
--

DROP TABLE IF EXISTS `oj_paper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_paper` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `logo` varchar(512) DEFAULT NULL,
  `practice_count` int(11) NOT NULL,
  `total_score` int(11) NOT NULL,
  `public` int(1) NOT NULL,
  `type` int(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `operation_auth_type` int(1) DEFAULT NULL,
  `operation_auth_ids` varchar(1024) DEFAULT NULL,
  `read_auth_type` int(1) DEFAULT NULL,
  `read_auth_ids` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id_uniq` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_practice_category`
--

DROP TABLE IF EXISTS `oj_practice_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_practice_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(128) NOT NULL COMMENT '分类名称',
  `practice_type` int(1) NOT NULL COMMENT '习题类型',
  `create_user_id` varchar(64) NOT NULL COMMENT '创建人',
  `code` varchar(1024) NOT NULL COMMENT '节点编号',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '编号',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_practice_operation`
--

DROP TABLE IF EXISTS `oj_practice_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_practice_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `category_id` varchar(64) NOT NULL,
  `builtin` int(1) NOT NULL,
  `practice_set_id` varchar(64) NOT NULL,
  `title` varchar(1024) NOT NULL,
  `content` longtext NOT NULL,
  `answer` varchar(1024) DEFAULT NULL,
  `difficulty` int(1) NOT NULL,
  `knowledge` varchar(1024) DEFAULT NULL,
  `public` int(1) NOT NULL,
  `score` int(11) NOT NULL,
  `attachment_id` varchar(1024) DEFAULT NULL,
  `writeup` longtext DEFAULT NULL,
  `writeup_public` int(1) DEFAULT NULL,
  `create_user_id` varchar(64) NOT NULL,
  `dynamic_env` int(1) DEFAULT NULL,
  `topo_id` varchar(64) DEFAULT NULL,
  `scene_share_mode` int(1) DEFAULT NULL,
  `dynamic_flag` int(1) DEFAULT NULL,
  `destroy_delay` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_practice_operation_ref`
--

DROP TABLE IF EXISTS `oj_practice_operation_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_practice_operation_ref` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `snapshot` longtext NOT NULL,
  `origin_id` varchar(64) NOT NULL,
  `paper_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_practice_set`
--

DROP TABLE IF EXISTS `oj_practice_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_practice_set` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `builtin` int(1) NOT NULL,
  `practice_type` int(1) NOT NULL,
  `name` varchar(128) NOT NULL,
  `logo` varchar(512) DEFAULT NULL,
  `public` int(1) NOT NULL,
  `create_user_id` varchar(64) NOT NULL,
  `operation_auth_type` int(1) NOT NULL,
  `operation_auth_ids` varchar(1024) DEFAULT NULL,
  `read_auth_type` int(1) NOT NULL,
  `read_auth_ids` varchar(1024) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `practice_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_idx` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_practice_solved`
--

DROP TABLE IF EXISTS `oj_practice_solved`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_practice_solved` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `submit_user_id` varchar(64) NOT NULL COMMENT '提交人',
  `submit_answer` longtext NOT NULL COMMENT '提交的答案',
  `score` double(11,0) NOT NULL COMMENT '得分',
  `total_score` double(11,0) NOT NULL COMMENT '习题分值',
  `solved` int(1) NOT NULL COMMENT '是否解出',
  `practice_id` varchar(64) NOT NULL COMMENT '习题编号',
  `practice_type` int(1) NOT NULL COMMENT '习题类型',
  `collection` int(1) NOT NULL COMMENT '是否收藏',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_type_practice_uniq` (`submit_user_id`,`practice_type`,`practice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_practice_theory`
--

DROP TABLE IF EXISTS `oj_practice_theory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_practice_theory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `builtin` int(1) NOT NULL,
  `category_id` varchar(64) NOT NULL,
  `title` varchar(512) NOT NULL,
  `content` longtext NOT NULL,
  `answer` varchar(1024) NOT NULL,
  `difficulty` int(1) NOT NULL,
  `knowledge` varchar(1024) DEFAULT NULL,
  `public` int(1) NOT NULL,
  `score` int(11) NOT NULL,
  `create_user_id` varchar(64) NOT NULL,
  `theory_type` int(1) NOT NULL,
  `option` longtext DEFAULT NULL,
  `blank` varchar(1024) DEFAULT NULL,
  `keyword` varchar(1024) DEFAULT NULL,
  `practice_set_id` varchar(64) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_uniq` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3356 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_practice_theory_ref`
--

DROP TABLE IF EXISTS `oj_practice_theory_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_practice_theory_ref` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `snapshot` longtext NOT NULL,
  `origin_id` varchar(64) NOT NULL,
  `option_random` int(1) DEFAULT NULL,
  `paper_id` varchar(64) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=406 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oj_resource_collection`
--

DROP TABLE IF EXISTS `oj_resource_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oj_resource_collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL,
  `business_id` varchar(64) NOT NULL,
  `business_type` int(1) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_type_id_uniq` (`user_id`,`business_type`,`business_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `radar_label`
--

DROP TABLE IF EXISTS `radar_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `radar_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_ability`
--

DROP TABLE IF EXISTS `rtks_ability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_ability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_course_name`
--

DROP TABLE IF EXISTS `rtks_course_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_course_name` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(256) DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `difficulty` int(11) DEFAULT NULL,
  `lesson_count` int(11) DEFAULT NULL,
  `e_book` int(11) DEFAULT NULL,
  `course_num` varchar(64) DEFAULT NULL,
  `exercise_count` int(11) DEFAULT NULL,
  `theory_count` int(11) DEFAULT NULL,
  `direction` varchar(64) DEFAULT NULL,
  `direction_name` varchar(64) DEFAULT NULL,
  `interview` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=281 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_course_related`
--

DROP TABLE IF EXISTS `rtks_course_related`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_course_related` (
  `resource_id` varchar(64) NOT NULL COMMENT '课程id',
  `related_id` varchar(64) NOT NULL COMMENT '知识点id',
  `type` int(11) NOT NULL COMMENT '1：知识点;2：技能点',
  `weight` int(11) NOT NULL COMMENT '权重',
  KEY `course_id` (`resource_id`) USING BTREE,
  KEY `related_id` (`related_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_knowledge`
--

DROP TABLE IF EXISTS `rtks_knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_knowledge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `category_id` varchar(40) NOT NULL,
  `content` varchar(512) NOT NULL,
  `courses` text NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=365 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_knowledge_category`
--

DROP TABLE IF EXISTS `rtks_knowledge_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_knowledge_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_level`
--

DROP TABLE IF EXISTS `rtks_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `parent` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `frame` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_role`
--

DROP TABLE IF EXISTS `rtks_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `level_id` varchar(64) NOT NULL,
  `category` varchar(64) NOT NULL,
  `task_ids` text NOT NULL,
  `knowledge_id_weights` text NOT NULL,
  `knowledge_ids` text NOT NULL,
  `skill_id_weights` text NOT NULL,
  `skill_ids` text NOT NULL,
  `ability_ids` text NOT NULL,
  `skill_courses` text DEFAULT NULL,
  `knowledge_courses` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_role_related`
--

DROP TABLE IF EXISTS `rtks_role_related`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_role_related` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(255) NOT NULL,
  `related_id` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_skill`
--

DROP TABLE IF EXISTS `rtks_skill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_skill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `system_id` varchar(40) NOT NULL,
  `category_id` varchar(40) NOT NULL,
  `content` varchar(512) NOT NULL,
  `courses` text NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_skill_category`
--

DROP TABLE IF EXISTS `rtks_skill_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_skill_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id_UNIQUE` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_skill_system`
--

DROP TABLE IF EXISTS `rtks_skill_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_skill_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `scene_id` int(11) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_task`
--

DROP TABLE IF EXISTS `rtks_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` varchar(64) NOT NULL,
  `content` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `resource_id` (`resource_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rtks_user_role`
--

DROP TABLE IF EXISTS `rtks_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rtks_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) NOT NULL,
  `role_id` varchar(64) NOT NULL,
  `role_name` varchar(512) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `user_name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_uni` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-13 18:08:07
