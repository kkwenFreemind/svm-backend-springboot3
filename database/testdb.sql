-- MySQL dump 10.13  Distrib 8.0.35, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: testdb
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `api_events`
--

DROP TABLE IF EXISTS `api_events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `api_events` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `event` varchar(255) DEFAULT NULL,
  `event_cost` bigint DEFAULT NULL,
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `log_type` int DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  `request_method` varchar(255) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1132 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `api_events`
--

LOCK TABLES `api_events` WRITE;
/*!40000 ALTER TABLE `api_events` DISABLE KEYS */;
INSERT INTO `api_events` VALUES (235,'2023-10-12 13:44:35.340000','http://localhost:8081/admin/update/4',82,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,4,'kkwen'),(237,'2023-10-12 14:30:21.813000','http://localhost:8081/admin/update/4',27,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,4,'kkwen'),(239,'2023-10-12 14:35:24.260000','http://localhost:8081/admin/update/4',71,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,4,'kkwen'),(247,'2023-10-12 15:21:20.644000','http://localhost:8081/admin/update/1',48,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,1,'kevinchang'),(249,'2023-10-12 15:22:05.586000','http://localhost:8081/admin/update/3',27,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,3,'admin'),(254,'2023-10-12 15:44:06.489000','http://localhost:8081/admin/role/update',27,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,1,'kevinchang'),(256,'2023-10-12 15:44:30.673000','http://localhost:8081/admin/role/update',20,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,1,'kevinchang'),(258,'2023-10-12 15:44:36.707000','http://localhost:8081/admin/role/update',38,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,1,'kevinchang'),(260,'2023-10-12 15:44:41.519000','http://localhost:8081/admin/role/update',32,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,1,'kevinchang'),(263,'2023-10-12 15:44:48.130000','http://localhost:8081/admin/role/update',19,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,1,'kevinchang'),(265,'2023-10-12 15:44:51.869000','http://localhost:8081/admin/role/update',16,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/118.0','POST','success',1,1,'kevinchang'),(596,'2023-10-28 05:57:02.461000','http://localhost:8080/admin/role/update',15,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(599,'2023-10-28 05:57:13.345000','http://localhost:8080/admin/role/update',13,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(606,'2023-10-28 05:58:06.695000','http://localhost:8080/admin/update/12',58,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,12,'test7'),(608,'2023-10-28 05:58:29.926000','http://localhost:8080/admin/update/12',16,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,12,'test7'),(662,'2023-10-28 21:32:04.227000','http://localhost:8080/admin/update/11',34,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,11,'test6'),(733,'2023-10-30 10:10:27.209000','http://localhost:8080/admin/update/9',45,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,9,'test5'),(735,'2023-10-30 10:10:41.693000','http://localhost:8080/admin/update/10',18,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,10,'test4'),(737,'2023-10-30 10:10:50.621000','http://localhost:8080/admin/update/11',15,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,11,'test6'),(740,'2023-10-30 10:11:14.113000','http://localhost:8080/admin/update/3',20,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,3,'admin'),(742,'2023-10-30 10:11:21.227000','http://localhost:8080/admin/update/1',18,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(744,'2023-10-30 10:11:30.897000','http://localhost:8080/admin/update/6',17,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,6,'test1'),(746,'2023-10-30 10:11:40.060000','http://localhost:8080/admin/update/7',19,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(748,'2023-10-30 10:11:47.916000','http://localhost:8080/admin/update/8',15,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,8,'test3'),(754,'2023-10-30 10:12:51.286000','http://localhost:8080/admin/update/6',18,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,6,'test1'),(756,'2023-10-30 10:12:58.340000','http://localhost:8080/admin/update/8',16,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,8,'test3'),(760,'2023-10-30 10:13:11.347000','http://localhost:8080/admin/update/9',17,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,9,'test5'),(762,'2023-10-30 10:13:22.400000','http://localhost:8080/admin/update/6',16,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,6,'test1'),(764,'2023-10-30 10:13:28.129000','http://localhost:8080/admin/update/7',15,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(782,'2023-10-30 10:37:33.266000','http://localhost:8080/admin/update/7',41,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(788,'2023-10-30 10:47:11.108000','http://localhost:8080/admin/update/7',16,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(813,'2023-10-30 11:16:43.598000','http://localhost:8080/admin/update/1',20,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(815,'2023-10-30 11:16:51.404000','http://localhost:8080/admin/update/6',22,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,6,'test1'),(828,'2023-10-30 13:33:27.567000','http://localhost:8080/admin/update/6',19,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,6,'test1'),(836,'2023-10-30 13:54:57.948000','http://localhost:8080/admin/update/14',26,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,14,'test11'),(839,'2023-10-30 13:55:11.195000','http://localhost:8080/admin/update/6',22,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,6,'test1'),(841,'2023-10-30 13:55:53.697000','http://localhost:8080/admin/update/9',19,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,9,'test5'),(843,'2023-10-30 16:01:13.147000','http://localhost:8080/admin/update/1',43,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(845,'2023-10-30 16:01:37.762000','http://localhost:8080/admin/update/6',27,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,6,'test1'),(847,'2023-10-30 16:01:44.939000','http://localhost:8080/admin/update/7',18,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(849,'2023-10-30 16:01:54.396000','http://localhost:8080/admin/update/9',18,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,9,'test5'),(852,'2023-10-30 16:03:47.028000','http://localhost:8080/admin/update/15',20,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,15,'test12'),(854,'2023-10-30 16:05:41.467000','http://localhost:8080/admin/update/15',21,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,15,'test12'),(872,'2023-10-31 08:37:32.590000','http://localhost:8080/admin/update/9',44,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,9,'test5'),(874,'2023-10-31 08:37:41.682000','http://localhost:8080/admin/update/7',19,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(876,'2023-10-31 08:39:12.966000','http://localhost:8080/admin/update/7',57,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(882,'2023-10-31 08:41:52.655000','http://localhost:8080/admin/update/1',40,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(935,'2023-10-31 13:40:40.252000','http://localhost:8080/admin/update/7',24,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(937,'2023-10-31 13:40:47.246000','http://localhost:8080/admin/update/9',16,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,9,'test5'),(940,'2023-10-31 13:40:55.198000','http://localhost:8080/admin/update/10',17,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,10,'test4'),(942,'2023-10-31 13:41:00.939000','http://localhost:8080/admin/update/14',17,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,14,'test11'),(944,'2023-10-31 13:41:06.521000','http://localhost:8080/admin/update/15',22,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,15,'test12'),(949,'2023-10-31 14:16:57.864000','http://localhost:8080/admin/update/7',44,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,7,'test2'),(951,'2023-10-31 14:17:04.485000','http://localhost:8080/admin/update/9',23,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,9,'test5'),(985,'2023-10-31 17:06:53.455000','http://localhost:8080/admin/update/14',47,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,14,'test11'),(989,'2023-10-31 17:07:12.525000','http://localhost:8080/admin/update/15',20,'127.0.1.1',0,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,15,'test12'),(1129,'2023-11-02 11:00:29.314000','http://localhost:8080/admin/logout',38,'127.0.1.1',1,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(1130,'2023-11-02 11:00:52.809000','http://localhost:8080/admin/login',134,'127.0.1.1',1,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang'),(1131,'2023-11-02 11:02:37.362000','http://localhost:8080/admin/login',218,'127.0.1.1',1,'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/119.0','POST','success',1,1,'kevinchang');
/*!40000 ALTER TABLE `api_events` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menus` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `level` int DEFAULT NULL COMMENT '菜单级数',
  `sort` int DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `hidden` int DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus`
--

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` VALUES (1,0,'2020-02-07 16:29:13.000000','權限',0,0,'ums','ums',0),(2,1,'2020-02-07 16:29:51.000000','用户列表',1,0,'admin','user',0),(3,1,'2020-02-07 16:30:13.000000','角色列表',1,0,'role','ums-role',0),(4,1,'2020-02-07 16:30:53.000000','選單列表',1,0,'menu','ums-menu',0),(5,1,'2020-02-07 16:31:13.000000','資源列表',1,0,'resource','list',0),(9,0,'2022-02-12 20:55:44.000000','組織',0,0,'oms','organization',0),(10,9,'2022-02-12 21:04:18.000000','organization',1,0,'organization','organization',0),(28,9,'2022-11-09 09:08:45.000000','公司列表',1,0,'company','organization',0),(29,0,'2022-11-28 14:37:02.000000','個人',0,0,'pms','user',0),(30,29,'2022-11-28 14:37:58.000000','事件列表',1,0,'operate','icon',0),(31,29,'2022-11-28 14:40:09.000000','變更密碼',1,0,'pwd','password',0),(39,0,'2023-01-16 10:02:23.000000','儀表板',0,0,'dashboard','dashboard',0),(40,29,'2023-02-08 14:01:07.000000','變更語系',1,0,'lang','language',0),(44,0,'2023-05-26 08:45:04.000000','日誌',0,0,'log','log',0),(45,44,'2023-05-26 08:46:36.000000','登入日誌',1,0,'login_log','log',0),(46,44,'2023-05-26 08:47:46.000000','操作日誌',1,0,'operate_log','log',0);
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL,
  `name_sn` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `org_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `level` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `sort` int DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (10,0,'97179430','總公司',0,1,0,'2023-10-30 09:06:19.516000'),(11,10,'97179430011','台北分公司',1,1,0,'2023-10-30 09:06:19.516000'),(12,10,'97179430012','基隆分公司',1,1,0,'2023-10-30 09:06:19.516000'),(16,12,'97179430012016','愛六店',2,1,0,'2023-10-30 09:06:19.516000'),(17,10,'97179430017','高雄分公司',1,1,0,'2023-10-30 09:06:19.516000'),(18,17,'97179430017018','左營店',2,1,0,'2023-10-30 09:06:19.516000'),(19,11,'97179430011001','南港店',2,1,0,'2023-10-30 09:06:19.516000'),(23,11,'97179430011002','松仁店',1,1,0,'2023-10-30 09:15:39.695000');
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_category`
--

DROP TABLE IF EXISTS `resource_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sort` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_category`
--

LOCK TABLES `resource_category` WRITE;
/*!40000 ALTER TABLE `resource_category` DISABLE KEYS */;
INSERT INTO `resource_category` VALUES (1,'2020-02-05 10:23:04','權限模組',0),(2,'2020-02-05 10:23:04','組織模組',0),(3,'2020-02-05 10:23:04','個人模組',0),(4,'2020-02-05 10:23:04','設備模組',0),(5,'2020-02-05 10:23:04','統計模組',0);
/*!40000 ALTER TABLE `resource_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resources` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL COMMENT '资源分类ID',
  PRIMARY KEY (`id`),
  KEY `resources_FK` (`category_id`),
  CONSTRAINT `resources_FK` FOREIGN KEY (`category_id`) REFERENCES `resource_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resources`
--

LOCK TABLES `resources` WRITE;
/*!40000 ALTER TABLE `resources` DISABLE KEYS */;
INSERT INTO `resources` VALUES (1,'2020-02-07 16:47:34.000000','帳號管理','/admin/**','後台用户管理(DML&Query)',1),(2,'2020-02-07 16:48:24.000000','角色管理','/role/**','後台用户角色管理(DML&Query)',1),(3,'2020-02-07 16:48:48.000000','選單管理','/menu/**','功能表單管理(DML&Query)',1),(4,'2020-02-07 16:49:18.000000','資源分類管理','/resourceCategory/**','API分類管理',1),(5,'2020-02-07 16:49:45.000000','資源管理','/resource/**','API管理',1),(8,'2022-02-12 21:14:29.000000','組織管理','/org/treeList','單位組織API',2),(13,'2022-03-15 09:43:51.000000','登入管理','/admin/info','登入 API',3),(17,'2022-11-28 16:16:09.000000','事件管理','/operate','',3);
/*!40000 ALTER TABLE `resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menus`
--

DROP TABLE IF EXISTS `role_menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menus` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `FKo7blfgcetl84km46ayoybmkvm` (`menu_id`),
  KEY `FK8w16n9supii3exa5gnfdey3vu` (`role_id`),
  CONSTRAINT `FK8w16n9supii3exa5gnfdey3vu` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKo7blfgcetl84km46ayoybmkvm` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1198 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menus`
--

LOCK TABLES `role_menus` WRITE;
/*!40000 ALTER TABLE `role_menus` DISABLE KEYS */;
INSERT INTO `role_menus` VALUES (1,1,1),(1151,1,2),(1152,1,3),(1153,1,4),(1154,1,5),(1155,1,9),(1156,1,10),(1158,1,29),(1159,1,30),(1167,1,40),(1168,1,31),(1169,1,39),(1173,1,44),(1174,1,45),(1175,1,46);
/*!40000 ALTER TABLE `role_menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_resources`
--

DROP TABLE IF EXISTS `role_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_resources` (
  `role_id` int NOT NULL,
  `resource_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`resource_id`),
  KEY `FK8k22y1jmevnedy4v80tl1yop7` (`resource_id`),
  CONSTRAINT `FK8k22y1jmevnedy4v80tl1yop7` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`),
  CONSTRAINT `FKn73hm4dq4ddnj6sc0k1phqeda` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_resources`
--

LOCK TABLES `role_resources` WRITE;
/*!40000 ALTER TABLE `role_resources` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `admin_count` int DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime(6) DEFAULT NULL,
  `status` int DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'超級管理员','擁有所有查看和操作功能',NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,3,1),(7,6,1),(8,7,1),(9,10,1),(12,1,1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_by` bigint DEFAULT NULL,
  `create_name` varchar(255) DEFAULT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `login_time` datetime(6) DEFAULT NULL,
  `logout_time` datetime(6) DEFAULT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `nick_name` varchar(20) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `org_id` bigint DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `status` int DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_name` varchar(255) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FK2h5ste2cf69o51tcfvwx503ge` (`org_id`),
  CONSTRAINT `FK2h5ste2cf69o51tcfvwx503ge` FOREIGN KEY (`org_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'kevin','2023-09-13 09:17:29.127000','kevinchang@gmail.com','2023-11-02 11:02:37.332000','2023-11-02 11:00:29.282000','0985555666','Kevin Chang','init account',10,'$2a$10$n6E77UWGZP6gVTwuEsQ8A.2wh974CEj4Ld8TG6D8Nb.RcJV5NZ3uC',1,3,'admin','2023-11-01 02:49:30.000000','kevinchang'),(3,NULL,NULL,'2023-10-03 09:34:47.185000','kevinchang1@gmail.com','2023-11-01 10:47:40.824000',NULL,'0985555666','System Account','init account',10,'$2a$10$n6E77UWGZP6gVTwuEsQ8A.2wh974CEj4Ld8TG6D8Nb.RcJV5NZ3uC',1,3,'admin','2023-10-30 10:11:14.098000','admin'),(6,NULL,NULL,'2023-10-12 11:21:59.675000','test1@gmail.com',NULL,NULL,'0985666999','test1','test account',11,'$2a$10$50FlFWmfpRVwjP5fYx3.MeMxWONp.jAOXB3LKUQBPep.0P8iQwY2i',1,1,'kevin','2023-10-30 16:01:37.750000','test1'),(7,NULL,NULL,'2023-10-12 11:27:37.918000','test2@gmail.com',NULL,NULL,'0955888777','test2','test account',19,'$2a$10$uhs0ogQFR4aDhVC44ZZYyO5cr2vlo79DRdNGDO6q8T1GJjwshQ1pS',1,1,'kevin','2023-10-31 14:16:57.837000','test2'),(9,NULL,NULL,'2023-10-12 11:37:16.418000','test5@gmail.com',NULL,NULL,'0982158008','test5','test account',23,'$2a$10$ZnMlx82mWS6m3ZwXFTjnfuJ4vcdCa/szNS8liznWHMf7Q.JNaX/nG',1,1,'kevin','2023-10-31 14:17:04.474000','test5'),(10,1,'kevin','2023-10-12 11:45:01.578000','test4@gmail.com',NULL,NULL,'0985444666','test4','test account',12,'$2a$10$h72o0/2CjT0EE94F0ZFs3OkFmPujGV3.6.TjBIibFU2TxNSZIMj1a',1,1,'kevin','2023-10-31 13:40:55.188000','test4'),(14,3,'admin','2023-10-30 10:51:57.477000','test11@gmail.com',NULL,NULL,'0985488666','test11','test account',16,'$2a$10$ogyzCj5Sd/IZyBMvZ.HrZOEhBP.5Z8KHjMTR47DTiEuY00HY0ja0e',1,1,'kevin','2023-10-31 17:06:53.426000','test11'),(15,1,'kevin','2023-10-30 13:54:40.493000','test12@gmail.com',NULL,NULL,'0985666999','test12','test account',16,'$2a$10$M2PFUJVGgWEde9Rhgz7nsugSXJBDgC5LWMHdId/rD66oBLLeyn9lO',1,1,'kevin','2023-10-31 17:07:12.516000','test12');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-02 11:32:01
