-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `activity_type` tinyint(1) NOT NULL COMMENT '活动类型：1-借阅，2-审核，3-逾期，4-系统，5-损坏',
  `content` varchar(255) NOT NULL COMMENT '活动内容',
  `time` datetime NOT NULL COMMENT '活动时间',
  `user_id` int DEFAULT NULL COMMENT '相关用户ID',
  `book_id` int DEFAULT NULL COMMENT '相关图书ID',
  `admin_id` int DEFAULT NULL COMMENT '相关管理员ID',
  PRIMARY KEY (`id`),
  KEY `idx_time` (`time`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_book_id` (`book_id`),
  KEY `idx_admin_id` (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='活动记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,2,'用户13反馈问题你有问题吧？','2025-03-08 17:49:53',13,NULL,NULL),(2,2,'管理员9处理用户的问题5','2025-03-08 18:05:58',9,NULL,NULL),(3,1,'用户14归还了图书11','2025-03-08 19:50:50',14,NULL,NULL),(4,2,'用户13反馈问题你好','2025-03-09 18:46:04',13,NULL,NULL),(5,2,'审核评论10','2025-03-12 13:13:25',9,NULL,NULL);
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_log`
--

DROP TABLE IF EXISTS `audit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_log` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL DEFAULT '0' COMMENT '操作人id',
  `operation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `method_name` varchar(50) DEFAULT NULL COMMENT '操作方法名',
  `method_params` varchar(1000) DEFAULT NULL COMMENT '方法参数',
  `cost_time` bigint DEFAULT NULL COMMENT '耗时',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `class_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `idx_time` (`operation_time`)
) ENGINE=InnoDB AUTO_INCREMENT=999 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_log`
--

LOCK TABLES `audit_log` WRITE;
/*!40000 ALTER TABLE `audit_log` DISABLE KEYS */;
INSERT INTO `audit_log` VALUES (815,9,'2025-03-08 16:10:17','getPendingComments','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(816,9,'2025-03-08 16:10:17','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(817,9,'2025-03-08 16:12:46','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(818,9,'2025-03-08 16:12:46','getAdminStatistics','[]',5,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(819,9,'2025-03-08 16:12:46','getPendingComments','[1, 5]',4,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(820,9,'2025-03-08 16:12:46','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(821,9,'2025-03-08 16:12:46','getBookCategoryStats','[]',12,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(822,9,'2025-03-08 16:14:43','getAdminInfo','[]',2,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(823,9,'2025-03-08 16:14:43','getAdminStatistics','[]',4,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(824,9,'2025-03-08 16:14:43','getPendingComments','[1, 5]',2,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(825,9,'2025-03-08 16:14:43','getPendingFeedbacks','[1, 5]',2,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(826,9,'2025-03-08 16:14:43','getBookCategoryStats','[]',16,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(827,9,'2025-03-08 16:15:08','getAdminInfo','[]',2,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(828,9,'2025-03-08 16:15:08','getAdminStatistics','[]',6,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(829,9,'2025-03-08 16:15:08','getPendingComments','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(830,9,'2025-03-08 16:15:08','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(831,9,'2025-03-08 16:15:08','getBookCategoryStats','[]',9,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(832,9,'2025-03-08 16:15:11','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(833,9,'2025-03-08 16:15:12','getAdminStatistics','[]',5,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(834,9,'2025-03-08 16:15:12','getPendingComments','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(835,9,'2025-03-08 16:15:12','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(836,9,'2025-03-08 16:15:12','getBookCategoryStats','[]',9,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(837,9,'2025-03-08 16:15:14','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(838,9,'2025-03-08 16:15:14','getAdminStatistics','[]',4,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(839,9,'2025-03-08 16:15:14','getPendingComments','[1, 5]',2,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(840,9,'2025-03-08 16:15:14','getPendingFeedbacks','[1, 5]',2,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(841,9,'2025-03-08 16:15:14','getBookCategoryStats','[]',13,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(842,9,'2025-03-08 16:15:16','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(843,9,'2025-03-08 16:15:16','getAdminStatistics','[]',6,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(844,9,'2025-03-08 16:15:16','getPendingComments','[1, 5]',5,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(845,9,'2025-03-08 16:15:16','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(846,9,'2025-03-08 16:15:17','getBookCategoryStats','[]',10,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(847,9,'2025-03-08 16:15:25','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(848,9,'2025-03-08 16:15:26','getAdminStatistics','[]',4,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(849,9,'2025-03-08 16:15:26','getPendingComments','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(850,9,'2025-03-08 16:15:26','getPendingFeedbacks','[1, 5]',2,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(851,9,'2025-03-08 16:15:26','getBookCategoryStats','[]',10,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(852,9,'2025-03-08 16:16:56','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(853,9,'2025-03-08 16:16:57','getAdminStatistics','[]',5,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(854,9,'2025-03-08 16:16:57','getPendingComments','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(855,9,'2025-03-08 16:16:57','getPendingFeedbacks','[1, 5]',2,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(856,9,'2025-03-08 16:16:57','getBookCategoryStats','[]',11,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(857,9,'2025-03-08 16:17:25','getLogByPage','[LogDTO(pageNum=1, pageSize=10, userId=null)]',8,NULL,'zhijianhu.libraryserver.controller.AuditLogController'),(858,9,'2025-03-08 16:17:25','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(859,9,'2025-03-08 16:17:26','getAdminStatistics','[]',5,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(860,9,'2025-03-08 16:17:26','getPendingComments','[1, 5]',5,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(861,9,'2025-03-08 16:17:26','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(862,9,'2025-03-08 16:17:26','getBookCategoryStats','[]',12,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(863,9,'2025-03-08 16:18:35','login','[UserLoginDTO(username=小胡, password=123456)]',187,NULL,'zhijianhu.libraryserver.controller.UserController'),(864,9,'2025-03-08 16:18:36','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',166,NULL,'zhijianhu.libraryserver.controller.BookController'),(875,9,'2025-03-08 16:25:09','getAdminStatistics','[]',28,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(876,9,'2025-03-08 16:25:10','getPendingComments','[1, 5]',16,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(877,9,'2025-03-08 16:25:10','getPendingFeedbacks','[1, 5]',11,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(878,9,'2025-03-08 16:25:10','getBorrowingTrends','[6]',24,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(879,9,'2025-03-08 16:25:10','getBookCategoryStats','[]',24,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(880,9,'2025-03-08 16:36:39','login','[UserLoginDTO(username=小胡, password=123456)]',447,NULL,'zhijianhu.libraryserver.controller.UserController'),(881,9,'2025-03-08 16:36:39','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',142,NULL,'zhijianhu.libraryserver.controller.BookController'),(882,9,'2025-03-08 16:36:40','getAdminInfo','[]',3,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(883,9,'2025-03-08 16:36:40','getAdminStatistics','[]',22,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(884,9,'2025-03-08 16:36:41','getPendingComments','[1, 5]',14,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(885,9,'2025-03-08 16:36:41','getPendingFeedbacks','[1, 5]',8,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(886,9,'2025-03-08 16:36:41','getBorrowingTrends','[6]',21,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(887,9,'2025-03-08 16:36:41','getBookCategoryStats','[]',14,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(888,9,'2025-03-08 16:38:10','list','[UserPageQueryDTO(page=1, pageSize=10, username=, role=null, status=null)]',19,NULL,'zhijianhu.libraryserver.controller.UserController'),(889,9,'2025-03-08 16:38:12','getUserQuestionList','[UserQuestionPageDTO(pageNum=1, pageSize=10, status=null, type=null)]',5,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(890,9,'2025-03-08 16:38:15','getAdminInfo','[]',2,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(891,9,'2025-03-08 16:38:15','getAdminStatistics','[]',9,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(892,9,'2025-03-08 16:38:15','getPendingComments','[1, 5]',8,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(893,9,'2025-03-08 16:38:15','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(894,9,'2025-03-08 16:38:15','getBorrowingTrends','[6]',11,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(895,9,'2025-03-08 16:38:15','getBookCategoryStats','[]',7,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(896,9,'2025-03-08 16:39:43','getUserQuestionList','[UserQuestionPageDTO(pageNum=1, pageSize=10, status=null, type=null)]',5,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(897,9,'2025-03-08 16:39:45','getAdminInfo','[]',2,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(898,9,'2025-03-08 16:39:45','getAdminStatistics','[]',10,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(899,9,'2025-03-08 16:39:45','getPendingComments','[1, 5]',6,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(900,9,'2025-03-08 16:39:45','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(901,9,'2025-03-08 16:39:46','getBorrowingTrends','[6]',10,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(902,9,'2025-03-08 16:39:46','getBookCategoryStats','[]',7,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(903,9,'2025-03-08 17:24:30','login','[UserLoginDTO(username=小胡, password=123456)]',449,NULL,'zhijianhu.libraryserver.controller.UserController'),(904,9,'2025-03-08 17:24:30','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',144,NULL,'zhijianhu.libraryserver.controller.BookController'),(905,9,'2025-03-08 17:35:27','login','[UserLoginDTO(username=小胡, password=123456)]',552,NULL,'zhijianhu.libraryserver.controller.UserController'),(906,9,'2025-03-08 17:35:27','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',145,NULL,'zhijianhu.libraryserver.controller.BookController'),(907,9,'2025-03-08 17:39:10','login','[UserLoginDTO(username=小胡, password=123456)]',408,NULL,'zhijianhu.libraryserver.controller.UserController'),(908,9,'2025-03-08 17:39:10','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',144,NULL,'zhijianhu.libraryserver.controller.BookController'),(910,9,'2025-03-08 17:42:18','getAdminInfo','[]',17,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(911,9,'2025-03-08 17:42:18','getAdminStatistics','[]',21,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(912,9,'2025-03-08 17:42:18','getPendingComments','[1, 5]',12,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(913,9,'2025-03-08 17:42:18','getPendingFeedbacks','[1, 5]',8,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(914,9,'2025-03-08 17:42:19','getBorrowingTrends','[6]',23,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(915,9,'2025-03-08 17:42:19','getBookCategoryStats','[]',14,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(916,9,'2025-03-08 17:44:53','login','[UserLoginDTO(username=小胡, password=123456)]',400,NULL,'zhijianhu.libraryserver.controller.UserController'),(917,9,'2025-03-08 17:44:53','login','[UserLoginDTO(username=小胡, password=123456)]',76,NULL,'zhijianhu.libraryserver.controller.UserController'),(918,9,'2025-03-08 17:44:53','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',140,NULL,'zhijianhu.libraryserver.controller.BookController'),(919,0,'2025-03-08 17:44:57','exit','[9]',0,NULL,'zhijianhu.libraryserver.controller.UserController'),(920,9,'2025-03-08 17:45:17','login','[UserLoginDTO(username=小胡, password=123456)]',78,NULL,'zhijianhu.libraryserver.controller.UserController'),(921,9,'2025-03-08 17:45:17','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',31,NULL,'zhijianhu.libraryserver.controller.BookController'),(922,9,'2025-03-08 17:45:49','upload','[org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@4c9aa886]',345,NULL,'zhijianhu.libraryserver.controller.UploadController'),(923,9,'2025-03-08 17:47:43','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',32,NULL,'zhijianhu.libraryserver.controller.BookController'),(924,0,'2025-03-08 17:48:50','exit','[9]',0,NULL,'zhijianhu.libraryserver.controller.UserController'),(925,13,'2025-03-08 17:48:56','login','[UserLoginDTO(username=喜喜, password=xxxxxx)]',76,NULL,'zhijianhu.libraryserver.controller.UserController'),(926,13,'2025-03-08 17:48:56','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=20, categoryId=null)]',40,NULL,'zhijianhu.libraryserver.controller.BookController'),(927,13,'2025-03-08 17:48:57','getCount','[13]',16,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(928,13,'2025-03-08 17:48:58','getUserBorrowCount','[13]',3,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(929,13,'2025-03-08 17:48:58','getById','[13]',3,NULL,'zhijianhu.libraryserver.controller.UserController'),(930,13,'2025-03-08 17:48:59','getBorrowCount','[13]',3,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(931,13,'2025-03-08 17:48:59','borrow','[BorrowPageDTO(pageNum=1, pageSize=10, userId=13, status=null)]',21,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(932,13,'2025-03-08 17:49:02','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=20, categoryId=null)]',21,NULL,'zhijianhu.libraryserver.controller.BookController'),(933,13,'2025-03-08 17:49:03','getBookById','[22]',5,NULL,'zhijianhu.libraryserver.controller.BookController'),(934,13,'2025-03-08 17:49:03','getReviewByBookId','[22, 13]',4,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(935,13,'2025-03-08 17:49:04','changeBookStatus','[ChangeBookStatusDTO(bookId=22, userId=13, status=0)]',20,NULL,'zhijianhu.libraryserver.controller.BookController'),(936,13,'2025-03-08 17:49:04','getBookById','[22]',4,NULL,'zhijianhu.libraryserver.controller.BookController'),(937,13,'2025-03-08 17:49:05','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=20, categoryId=null)]',37,NULL,'zhijianhu.libraryserver.controller.BookController'),(938,0,'2025-03-08 17:49:07','exit','[13]',0,NULL,'zhijianhu.libraryserver.controller.UserController'),(939,9,'2025-03-08 17:49:14','login','[UserLoginDTO(username=小胡, password=123456)]',73,NULL,'zhijianhu.libraryserver.controller.UserController'),(940,9,'2025-03-08 17:49:14','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',21,NULL,'zhijianhu.libraryserver.controller.BookController'),(941,9,'2025-03-08 17:49:17','borrow','[BorrowPageDTO(pageNum=1, pageSize=10, userId=null, status=null)]',9,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(942,9,'2025-03-08 17:49:31','updateStatus','[BorrowDTO(id=24, status=3, violationReason=测试, penaltyAmount=0.2, note=测试)]',22,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(943,9,'2025-03-08 17:49:31','borrow','[BorrowPageDTO(pageNum=1, pageSize=10, userId=null, status=null)]',9,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(944,0,'2025-03-08 17:49:34','exit','[9]',1,NULL,'zhijianhu.libraryserver.controller.UserController'),(945,13,'2025-03-08 17:49:38','login','[UserLoginDTO(username=喜喜, password=xxxxxx)]',76,NULL,'zhijianhu.libraryserver.controller.UserController'),(946,13,'2025-03-08 17:49:38','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=20, categoryId=null)]',18,NULL,'zhijianhu.libraryserver.controller.BookController'),(947,13,'2025-03-08 17:49:38','getCount','[13]',4,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(948,13,'2025-03-08 17:49:39','getById','[13]',1,NULL,'zhijianhu.libraryserver.controller.UserController'),(949,13,'2025-03-08 17:49:40','getBorrowCount','[13]',2,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(950,13,'2025-03-08 17:49:40','getUserBorrowCount','[13]',2,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(951,13,'2025-03-08 17:49:40','borrow','[BorrowPageDTO(pageNum=1, pageSize=10, userId=13, status=null)]',7,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(952,13,'2025-03-08 17:49:53','addUserQuestion','[AddUserQuestionDTO(userId=13, borrowRecordId=24, questionType=0, note=你有问题吧？)]',12,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(953,13,'2025-03-08 17:52:48','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=20, categoryId=null)]',21,NULL,'zhijianhu.libraryserver.controller.BookController'),(954,13,'2025-03-08 17:52:48','getById','[13]',1,NULL,'zhijianhu.libraryserver.controller.UserController'),(955,13,'2025-03-08 17:52:49','getBorrowCount','[13]',2,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(956,13,'2025-03-08 17:52:49','getUserBorrowCount','[13]',2,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(957,13,'2025-03-08 17:52:49','borrow','[BorrowPageDTO(pageNum=1, pageSize=10, userId=13, status=null)]',8,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(958,13,'2025-03-08 17:54:32','getById','[13]',2,NULL,'zhijianhu.libraryserver.controller.UserController'),(959,13,'2025-03-08 17:54:32','getUserBorrowCount','[13]',4,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(960,13,'2025-03-08 17:54:32','getBorrowCount','[13]',2,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(961,13,'2025-03-08 17:54:32','getCount','[13]',2,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(962,13,'2025-03-08 17:54:32','borrow','[BorrowPageDTO(pageNum=1, pageSize=10, userId=13, status=null)]',66,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(963,13,'2025-03-08 17:55:02','getUserBorrowCount','[13]',1,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(964,13,'2025-03-08 17:55:02','getById','[13]',1,NULL,'zhijianhu.libraryserver.controller.UserController'),(965,13,'2025-03-08 17:55:03','getBorrowCount','[13]',1,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(966,13,'2025-03-08 17:55:03','getCount','[13]',1,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(967,13,'2025-03-08 17:55:03','borrow','[BorrowPageDTO(pageNum=1, pageSize=10, userId=13, status=null)]',12,NULL,'zhijianhu.libraryserver.controller.BorrowController'),(968,0,'2025-03-08 17:55:21','exit','[13]',0,NULL,'zhijianhu.libraryserver.controller.UserController'),(969,9,'2025-03-08 17:55:28','login','[UserLoginDTO(username=小胡, password=123456)]',78,NULL,'zhijianhu.libraryserver.controller.UserController'),(970,9,'2025-03-08 17:55:28','getBooksByPage','[BookPageDTO(name=null, status=null, page=1, pageSize=10, categoryId=null)]',24,NULL,'zhijianhu.libraryserver.controller.BookController'),(971,9,'2025-03-08 17:55:30','getAdminInfo','[]',4,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(972,9,'2025-03-08 17:55:30','getAdminStatistics','[]',18,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(973,9,'2025-03-08 17:55:30','getPendingComments','[1, 5]',9,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(974,9,'2025-03-08 17:55:30','getPendingFeedbacks','[1, 5]',7,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(975,9,'2025-03-08 17:55:30','getRecentActivities','[5]',3,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(976,9,'2025-03-08 17:55:31','getBorrowingTrends','[6]',23,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(977,9,'2025-03-08 17:55:31','getBookCategoryStats','[]',10,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(978,9,'2025-03-08 17:55:40','getLogByPage','[LogDTO(pageNum=1, pageSize=10, userId=null)]',7,NULL,'zhijianhu.libraryserver.controller.AuditLogController'),(979,9,'2025-03-08 17:55:41','getAdminInfo','[]',1,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(980,9,'2025-03-08 17:55:41','getAdminStatistics','[]',7,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(981,9,'2025-03-08 17:55:41','getPendingComments','[1, 5]',4,NULL,'zhijianhu.libraryserver.controller.ReviewController'),(982,9,'2025-03-08 17:55:41','getPendingFeedbacks','[1, 5]',3,NULL,'zhijianhu.libraryserver.controller.UserQuestionController'),(983,9,'2025-03-08 17:55:41','getRecentActivities','[5]',2,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(984,9,'2025-03-08 17:55:42','getBorrowingTrends','[6]',9,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(985,9,'2025-03-08 17:55:42','getBookCategoryStats','[]',6,NULL,'zhijianhu.libraryserver.controller.ManagerController'),(986,9,'2025-03-08 17:56:00','getLogByPage','[LogDTO(pageNum=1, pageSize=10, userId=null)]',5,NULL,'zhijianhu.libraryserver.controller.AuditLogController'),(987,9,'2025-03-08 17:56:04','getLogByPage','[LogDTO(pageNum=1, pageSize=100, userId=null)]',11,NULL,'zhijianhu.libraryserver.controller.AuditLogController'),(988,9,'2025-03-08 19:38:00','delete','[13]',7,NULL,'zhijianhu.libraryserver.controller.PublishController'),(989,9,'2025-03-10 14:33:30','deleteReview','[3]',5,NULL,'zhijianhu.libraryserver.controller.user.ReviewController'),(990,9,'2025-03-10 14:33:33','deleteReview','[5]',6,NULL,'zhijianhu.libraryserver.controller.user.ReviewController'),(991,9,'2025-03-12 13:09:30','updateConfine','[8, 9]',5,NULL,'zhijianhu.libraryserver.controller.user.UserController'),(992,9,'2025-03-12 13:10:52','updateStatus','[1, 11, ]',7,NULL,'zhijianhu.libraryserver.controller.user.UserController'),(993,9,'2025-03-12 13:11:16','deleteAddress','[2]',8,NULL,'zhijianhu.libraryserver.controller.admin.AddressController'),(994,9,'2025-03-12 13:13:13','updateConfine','[7, 9]',6,NULL,'zhijianhu.libraryserver.controller.user.UserController'),(995,9,'2025-03-12 13:14:41','deleteLog','[[865, 866, 867, 868, 869, 870, 871, 872, 873, 874]]',21,NULL,'zhijianhu.libraryserver.controller.admin.AuditLogController'),(996,9,'2025-03-12 13:35:14','updatePassword','[UserChangePasswordDTO(id=9, oldPassword=123456, newPassword=654321)]',139,NULL,'zhijianhu.libraryserver.controller.user.UserController'),(997,9,'2025-03-12 13:37:00','deleteAddress','[5]',5,NULL,'zhijianhu.libraryserver.controller.admin.AddressController'),(998,9,'2025-03-12 13:37:15','delete','[2]',5,NULL,'zhijianhu.libraryserver.controller.admin.PublishController');
/*!40000 ALTER TABLE `audit_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_classes`
--

DROP TABLE IF EXISTS `book_classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_classes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '分类名称',
  `parent_id` int DEFAULT NULL COMMENT '父分类ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_classes`
--

LOCK TABLES `book_classes` WRITE;
/*!40000 ALTER TABLE `book_classes` DISABLE KEYS */;
INSERT INTO `book_classes` VALUES (1,'文学',NULL),(2,'小说',1),(3,'科幻小说',2),(4,'历史',NULL),(5,'科技',NULL),(6,'计算机科学',5),(7,'编程语言',6),(11,'古典小说',1),(33,'散文',1),(34,'随笔',33),(35,'传记',1),(36,'自传',35),(37,'回忆录',35),(38,'美国历史',4),(39,'亚洲历史',4),(40,'中世纪史',4),(41,'文艺复兴',4),(50,'艺术',NULL),(51,'绘画',50),(52,'油画',51),(53,'雕塑',50),(54,'音乐',NULL),(55,'古典音乐',54),(56,'流行音乐',54),(57,'漫画',NULL),(58,'成长',35);
/*!40000 ALTER TABLE `book_classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '书名',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `clazz_id` int DEFAULT NULL COMMENT '分类ID',
  `address_id` int DEFAULT NULL COMMENT '存放地址ID',
  `publish_id` int DEFAULT NULL COMMENT '出版社',
  `ISBN` char(13) DEFAULT NULL COMMENT '国际标准书号',
  `profile` text COMMENT '图书简介',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '1 可借 0不可借',
  `publish_date` date DEFAULT NULL COMMENT '出版日期',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `image` varchar(255) NOT NULL DEFAULT 'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ISBN` (`ISBN`),
  KEY `idx_author` (`author`(20)),
  KEY `idx_status` (`status`),
  KEY `idx_book` (`name`),
  KEY `books_ibfk_1` (`clazz_id`),
  KEY `books_ibfk_2` (`address_id`),
  KEY `books_publish_id_fk` (`publish_id`),
  CONSTRAINT `books_ibfk_1` FOREIGN KEY (`clazz_id`) REFERENCES `book_classes` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `books_ibfk_2` FOREIGN KEY (`address_id`) REFERENCES `storage_address` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `books_publish_id_fk` FOREIGN KEY (`publish_id`) REFERENCES `publish` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图书信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (6,'活着','余华',1,1,1,'9787506365437','《活着(新版)》讲述了农村人福贵悲惨的人生遭遇。福贵本是个阔少爷，可他嗜赌如命，终于赌光了家业，一贫如洗。他的父亲被他活活气死，母亲则在穷困中患了重病，福贵前去求药，却在途中被国民党抓去当壮丁。经过几番波折回到家里，才知道母亲早已去世，妻子家珍含辛茹苦地养大两个儿女。此后更加悲惨的命运一次又一次降临到福贵身上，他的妻子、儿女和孙子相继死去，最后只剩福贵和一头老牛相依为命，但老人依旧活着，仿佛比往日更加洒脱与坚强。\n\n《活着(新版)》荣获意大利格林扎纳•卡佛文学奖最高奖项（1998年）、台湾《中国时报》10本好书奖（1994年）、香港“博益”15本好书奖（1994年）、第三届世界华文“冰心文学奖”（2002年），入选香港《亚洲周刊》评选的“20世纪中文小说百年百强”、中国百位批评家和文学编辑评选的“20世纪90年代最有影响的10部作品”。',1,'1993-01-01','2023-01-01 10:00:00','2025-03-04 18:04:38','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/028ed548-5d25-4387-acb6-02e3cefc3352.jpg'),(7,'三体','刘慈欣',2,NULL,NULL,'9787536692930','文化大革命如火如荼进行的同时。军方探寻外星文明的绝秘计划“红岸工程”取得了突破性进展。但在按下发射键的那一刻，历经劫难的叶文洁没有意识到，她彻底改变了人类的命运。地球文明向宇宙发出的第一声啼鸣，以太阳为中心，以光速向宇宙深处飞驰……\n\n四光年外，“三体文明”正苦苦挣扎——三颗无规则运行的太阳主导下的百余次毁灭与重生逼迫他们逃离母星。而恰在此时。他们接收到了地球发来的信息。在运用超技术锁死地球人的基础科学之后。三体人庞大的宇宙舰队开始向地球进发……\n\n人类的末日悄然来临。',1,'2008-01-01','2023-02-15 14:30:00','2025-03-08 19:38:31','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/f73483d9-567f-49b9-9c1a-50816eba9017.jpg'),(9,'百年孤独','加西亚·马尔克斯',1,3,NULL,'9787544253994','魔幻现实主义文学代表作',1,'2011-06-01','2023-04-10 16:20:00','2025-03-09 13:09:49','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg'),(11,'《挪威的森林》','村上春树',1,7,21,'9787532776771','《挪威的森林》是日本作家村上春树所著的一部长篇爱情小说，影响了几代读者的青春名作。故事讲述主角渡边纠缠在情绪不稳定且患有精神疾病的直子和开朗活泼的小林绿子之间，苦闷彷徨，最终展开了自我救赎和成长的旅程。彻头彻尾的现实笔法，描绘了逝去的青春风景，小说中弥散着特有的感伤和孤独气氛。自1987年在日本问世后，该小说在年轻人中引起共鸣，风靡不息。上海译文出版社于2018年2月，推出该书的全新纪念版。',1,'2018-02-15','2025-02-25 16:52:57','2025-03-12 13:04:29','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg'),(15,'地久天长','王小波',2,6,1,'90-102-102','纯洁的感情地久天长，王小波早期感人作品~~',1,'2016-02-17','2025-02-28 20:30:59','2025-03-09 13:09:50','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/c90b5aa3-7ecc-4151-b48a-55d05039edb1.jpg'),(21,'射雕英雄传','金庸',2,1,1,'wx-901023','《射雕英雄传》是金庸的代表作之一，作于一九五七年到一九五九年，在《香港商报》连载。《射雕》中的人物个性单纯，郭靖诚朴厚重、黄蓉机智狡狯，读者容易印象深刻。这是中国传统小说和戏剧的特征，但不免缺乏人物内心世界的复杂性。由于人物性格单纯而情节热闹，所以《射雕》比较得到欢迎，被拍成各种语种的电影和电视剧在全球众多国家和地区热播。',1,'1999-03-24','2025-03-01 12:10:24','2025-03-03 17:23:04','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/36f4e5c4-dc46-4bf4-80e5-5aebb3636db3.jpg'),(22,'情书','岩井俊二',1,NULL,1,'9787201048161','日本神户，渡边博子在未婚夫藤井树的三周年祭日上又一次陷入到悲痛和思念之中。博子在藤井树的中学同学录里找到了他在小樽市读书时的地址。由于抑制不住对爱人的怀念，博子按着这个地址给远在天国的藤井树寄去了一封充满问候和思念的书信。\n\n不可思议的是，不久博子竟然收到了署名为“藤井树”的回信。经过进一步了解，这个藤井树是一个年轻的女子，而且她还曾经是男性藤井树的同班同学，原来是博子从同学录中误抄了她的地址。为了多了解一些男友在中学时代的情况，博子继续与女性藤井树保持书信来往。而藤井树在不断的回忆中，竟逐渐发现中学时代那个和自己同名同姓的少男曾经对自己产生过一段真挚的感情……\n\n《情书》由一个同名同姓的误会开始，通过两个女子书信的交流，以含情脉脉的笔触舒缓地展现了两段可贵的爱情。女主角博子对藤井树的眷恋，两个藤井树之间朦胧的情感，都没有由于藤井树的意外死亡而枯萎，而通过细腻感人的影象深深地印在每一个观众的心里，永远不变。\n\n在精心描绘爱情的同时，岩井俊二还着意表现了对逝去岁月的怀念和追忆。《情书》正象普鲁斯特那本小说的名字，追忆着似水的年华。过往的爱情和青春也正是在主人公的回忆中才逐渐清晰、复活。与现实相比，影片中的过去更为明快优美。在那一幅幅唯美的画面中，漫天飞舞的片片樱花，暗生情愫的少男少女，都唤起我们的无限遐想。而《情书》中所构筑的那个美好的中学时代，可能也正是岩井俊二和很多人最为温馨纯洁的回忆。\n\n《情书》中对过去的追忆和有关生死的描绘都极具东方气质，含蓄优美、感而不伤地表达了故事的主题——珍惜有限的生命和宝贵的爱情。',1,'2025-03-17','2025-03-08 17:46:16','2025-03-12 13:04:48','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/91486831-8784-4889-a235-310925123207.jpg'),(23,'《人类简史》','尤瓦尔·赫拉利',3,1,3,'9787508647353','以全新角度阐述人类从原始到现代的发展历程',1,NULL,'2025-03-10 14:30:31','2025-03-10 14:30:31','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg'),(24,'《小王子》','安托万·德·圣-埃克苏佩里',4,NULL,1,'9787020107928','讲述了来自B-612星球的小王子的宇宙冒险故事',1,NULL,'2025-03-10 14:30:31','2025-03-10 14:30:31','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg'),(25,'《呐喊》','鲁迅',2,3,7,'9787020017943','收录了鲁迅从1918年至1922年所作的14部短篇小说',1,NULL,'2025-03-10 14:30:31','2025-03-10 14:30:31','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg'),(26,'《红楼梦》','曹雪芹',1,NULL,1,'9787020002208','中国古代四大名著之一，讲述了贾府的兴衰',1,'2006-03-22','2025-03-10 14:30:31','2025-03-12 13:07:20','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/0c50a439-dc76-403d-be78-e640f07bcdfa.jpg'),(27,'《资本论》','卡尔·马克思',6,NULL,NULL,'9787010006631','政治经济学的经典著作，分析了资本主义经济体系',1,NULL,'2025-03-10 14:30:31','2025-03-10 14:30:31','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg'),(28,'《平凡的世界》','路遥',2,6,8,'9787530216786','讲述了普通人在大时代背景下的奋斗与成长',1,NULL,'2025-03-10 14:30:31','2025-03-10 14:30:31','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg'),(29,'《追风筝的人》','卡勒德·胡赛尼',1,6,3,'9787208061644','一部关于友谊、背叛与救赎的小说',1,NULL,'2025-03-10 14:30:31','2025-03-10 14:30:31','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow_records`
--

DROP TABLE IF EXISTS `borrow_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL COMMENT '借书人ID',
  `book_id` int DEFAULT NULL COMMENT '图书ID',
  `image` varchar(255) DEFAULT NULL COMMENT '图书图片url',
  `status` tinyint DEFAULT '1' COMMENT '借阅状态（0已还1在借2违规未还3丢失4损坏）',
  `lend_time` date DEFAULT (curdate()),
  `must_return_time` date DEFAULT NULL COMMENT '应还时间',
  `return_time` date DEFAULT NULL COMMENT '实际还时间',
  `overdue_days` smallint DEFAULT NULL COMMENT '逾期天数',
  `violation_reason` varchar(50) DEFAULT NULL COMMENT '违规原因',
  `penalty_amount` decimal(8,2) DEFAULT NULL COMMENT '罚款金额',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  KEY `idx_book` (`book_id`),
  KEY `idx_status` (`status`),
  CONSTRAINT `borrow_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `borrow_records_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='借阅记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_records`
--

LOCK TABLES `borrow_records` WRITE;
/*!40000 ALTER TABLE `borrow_records` DISABLE KEYS */;
INSERT INTO `borrow_records` VALUES (11,13,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-03','2025-04-02','2025-03-03',NULL,NULL,NULL,NULL),(12,13,9,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg',0,'2025-03-03','2025-04-02','2025-03-03',NULL,NULL,NULL,NULL),(13,13,7,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/f73483d9-567f-49b9-9c1a-50816eba9017.jpg',0,'2025-03-03','2025-04-02','2025-03-05',NULL,'',NULL,''),(14,13,21,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/36f4e5c4-dc46-4bf4-80e5-5aebb3636db3.jpg',0,'2025-03-03','2025-04-02','2025-03-05',NULL,'',NULL,''),(15,13,6,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/028ed548-5d25-4387-acb6-02e3cefc3352.jpg',0,'2025-03-03','2025-04-02','2025-03-04',NULL,'测试',0.40,'测试'),(16,13,7,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/f73483d9-567f-49b9-9c1a-50816eba9017.jpg',0,'2025-03-05','2025-04-04',NULL,NULL,'测试',0.30,'测试'),(17,13,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-05','2025-04-04','2025-03-07',NULL,'测试',0.30,'测试'),(18,13,9,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg',0,'2025-03-06','2025-04-05','2025-03-06',NULL,NULL,NULL,NULL),(19,13,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-07','2025-04-06','2025-03-07',NULL,'违规',0.30,'等待'),(20,14,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-07','2025-04-06','2025-03-07',NULL,NULL,NULL,NULL),(21,14,15,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/c90b5aa3-7ecc-4151-b48a-55d05039edb1.jpg',0,'2025-03-07','2025-04-06','2025-03-07',NULL,NULL,NULL,NULL),(22,14,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-07','2025-04-06','2025-03-08',NULL,NULL,NULL,NULL),(23,14,7,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/f73483d9-567f-49b9-9c1a-50816eba9017.jpg',0,'2025-03-07','2025-04-06','2025-03-08',NULL,NULL,NULL,NULL),(24,13,22,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/91486831-8784-4889-a235-310925123207.jpg',0,'2025-03-08','2025-04-07',NULL,NULL,'测试',0.20,'测试'),(25,14,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-08','2025-04-07','2025-03-08',NULL,NULL,NULL,NULL),(26,14,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-08','2025-04-07','2025-03-08',NULL,NULL,NULL,NULL),(27,14,9,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/book/default.jpg',2,'2025-03-09','2025-04-23','2025-03-09',NULL,'测试',0.30,''),(28,14,15,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/c90b5aa3-7ecc-4151-b48a-55d05039edb1.jpg',0,'2025-03-09','2025-04-23','2025-03-09',NULL,NULL,NULL,NULL),(29,13,11,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/adb514bd-7071-4118-8a2d-ccb71c22f37b.jpg',0,'2025-03-09','2025-03-09','2025-03-12',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `borrow_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` tinyint(1) DEFAULT NULL COMMENT '1.点赞类型 2.评论类型',
  `sender_id` int DEFAULT NULL COMMENT '发送消息的人id',
  `receiver_id` int DEFAULT NULL COMMENT '接收方id',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未读 1:已读',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
  `post_id` int DEFAULT NULL,
  `review_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`receiver_id`),
  KEY `sender_id` (`sender_id`),
  KEY `idx_review` (`review_id`),
  KEY `idx_post` (`post_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`),
  CONSTRAINT `sender_id` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (7,1,14,13,1,'2025-03-10 19:18:44',NULL,14),(8,1,14,13,1,'2025-03-10 19:18:55',6,NULL),(9,2,14,13,1,'2025-03-10 19:19:43',6,25),(10,1,14,13,1,'2025-03-11 11:44:50',7,NULL),(11,1,13,14,1,'2025-03-11 16:46:27',8,NULL),(12,2,13,14,1,'2025-03-11 16:46:42',8,26);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `penalty_records`
--

DROP TABLE IF EXISTS `penalty_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `penalty_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `borrow_record_id` int DEFAULT NULL COMMENT '借阅记录id',
  `penalty_amount` decimal(8,2) DEFAULT NULL COMMENT '罚款金额',
  `status` tinyint DEFAULT '0' COMMENT '0未缴纳1已缴纳',
  `note` varchar(200) DEFAULT NULL COMMENT '备注',
  `book_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `penalty_records___book` (`book_id`),
  KEY `penalty_records_ibfk_1` (`user_id`),
  KEY `penalty_records_ibfk_2` (`borrow_record_id`),
  CONSTRAINT `penalty_records___book` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penalty_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `penalty_records_ibfk_2` FOREIGN KEY (`borrow_record_id`) REFERENCES `borrow_records` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户违规缴纳罚款记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `penalty_records`
--

LOCK TABLES `penalty_records` WRITE;
/*!40000 ALTER TABLE `penalty_records` DISABLE KEYS */;
INSERT INTO `penalty_records` VALUES (7,13,16,0.30,1,'测试',7),(8,13,16,0.30,1,NULL,7),(9,13,16,0.30,1,'',7),(10,13,17,0.30,0,NULL,11),(11,13,19,0.30,0,NULL,11),(12,13,24,0.20,1,NULL,22),(13,14,27,0.30,0,NULL,9);
/*!40000 ALTER TABLE `penalty_records` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_like` (
  `post_id` int unsigned DEFAULT NULL COMMENT '帖子id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  KEY `idx_post_id` (`post_id`),
  KEY `idx_user_id` (`user_id`),
  CONSTRAINT `post_like_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_like_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子点赞';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_like`
--

LOCK TABLES `post_like` WRITE;
/*!40000 ALTER TABLE `post_like` DISABLE KEYS */;
INSERT INTO `post_like` VALUES (6,14),(7,14),(8,13),(8,14);
/*!40000 ALTER TABLE `post_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_tags`
--

DROP TABLE IF EXISTS `post_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_tags` (
  `post_id` int unsigned NOT NULL,
  `tag_id` int unsigned NOT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_id`,`tag_id`),
  KEY `post_tags_ibfk_2` (`tag_id`),
  CONSTRAINT `post_tags_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `post_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_tags`
--

LOCK TABLES `post_tags` WRITE;
/*!40000 ALTER TABLE `post_tags` DISABLE KEYS */;
INSERT INTO `post_tags` VALUES (6,3,'2025-03-10 11:33:24'),(7,6,'2025-03-10 13:57:00'),(8,7,'2025-03-11 16:37:04');
/*!40000 ALTER TABLE `post_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `content` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `like_count` int unsigned NOT NULL DEFAULT '0',
  `review_count` int unsigned NOT NULL DEFAULT '0',
  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user` (`user_id`),
  FULLTEXT KEY `idx_title` (`title`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (6,13,' 事先说明，本人日语能力有限，所以对片子内容如果有理解错误的地方，请大家原谅。\n\n       进电影院之前，我并不知道这是个怎样的故事。甚至连主人公是谁，我也不知道。我想或许这样可以避免先入为主的印象吧？\n\n       然后故事开始。大正的日本，就像民初的北京一样夹杂着熟悉的传统与陌生的现代，或者说东方的落后和西方的先进。在这样的环境里出生的少年，怀着对蓝天的渴望，吃力地翻着借来的辞典，透过从遥远的欧洲传来的书努力触摸新时代。这样的少年对我们来说并不陌生，就像少年的詹天佑和袁隆平，包括鲁迅和郭沫若。\n\n       随后少年长成了青年，他学了更多造飞机的知识，忘我地沉浸在造飞机的快乐中。他是天才，他够努力，他的梦想没有变，可是时代已经变了。就像片中一直反复说的：“起风了，要好好活下去。”战争的狂风已经吹到亚洲，面对强势的欧洲，积贫积弱的日本要崛起，要不惜一切代价的崛起，甚至让自己国家的儿童挨饿也要拿钱送技师去德国学习技术。一面是国内的饿殍遍地，另一面是自己的国家要花二十年才能赶得上的先进科技，德国的飞机仓库打开的瞬间，全金属现代化的巨大飞机带给主人公的不仅是震撼，更是屈辱和恨——这种屈辱和恨，近代的中国人已经尝过够多了。坐在德国旅馆里的青年望着灰蒙蒙的夜，抽着烟说：“德国花20年得到的技术，我们要花五年达到。”这句话是不是听着特别耳熟？没错，在这个时间点上，面对强大的西方，我们和日本的立场是一样的。\n\n       然后像每一个时代的青年人一样，青年遇到了心上人，可是爱情的甜蜜在这样的时空下也变得艰难而弥足珍贵。她身染重病，要到山里的医院去接受治疗，可是他身负造飞机的重任，还要被跟踪和监视，甚至可能有生命危险。为了和心爱的人在一起，少女选择忍耐孤独和寒冷，一个人去山里治疗，青年也只能躲在黒川先生的家里，透过明信片和电报默默地与爱人联系。\n\n       或许，少女意识到自己的病是不可能治好的了吧。她从山里走出来，来到心爱的人身边，在黑川先生夫妇的见证下结婚，用最后的生命换取一段短暂而幸福的婚姻生活。然而即使这样，青年仍不能把全部的时间和精力都留给生命像沙漏一样逝去的妻子。至今记得当他听说少女病发呕血，不顾一切地穿上衣服狂奔出去，一边流着眼泪一边还在电车上计算飞机制造的数据。他的泪水打湿了铅笔的痕迹，但是他的笔没有停，他还在工作，还在争分夺秒地抓住五年的时光！在我眼前，青年的身影不觉和另一些身影重合了：他们二十出头，背井离乡，来到北京或上海，为了国家和民族到处宣传、演讲、游行、甚至战斗。谁无父母？谁无弟兄？谁无爱人？谁无家庭？他们中有的人一辈子都没有结婚，有的人含泪写下“我大概要做一个不孝子了”。但是他们的脚步也没有停，因为时代不允许他们停下。\n\n       终于，天才的青年造出了最符合军队要求的飞机，但是这却不是他要的。他说过，他只是想追求人类飞行的技术。在他的梦里，搭载飞机在天空翱翔的人们才是最快乐的。他的飞机是给人，不是给炸弹制造的。他甚至为此失去了陪伴妻子最后的机会——少女在他终于设计完成之后，默默地离开了他，回到山里一个人走向生命的最终。但是，树欲静而风不止。他造的零式飞向了天空，携带炸弹飞向海峡对岸的中国。而最后留给他的，是满地飞机的残骸，是他亲手设计的像自己的孩子一样的飞机的残骸。每一片铁，每一根弦，都是他亲手画出来的，却只剩下粉碎的残骸，和残骸下埋葬的生命。\n\n       到这里，中国和日本分道扬镳了。我的感情也在一瞬间复杂了起来。在那个时代，追求军事的强大是每个国家的梦想，即使现在也是一样。因为大家都知道，落后就要挨打，弱小的国家会被欺负。但是强大了之后呢？军事强大的尽头真的是幸福和和平吗？战后的日本人，或许最适合回答这个问题。\n\n       过去的一百年，究竟发生了什么？这是我走出电影院之后，一直盘桓在心中的问题。基本的史实大家都知道，但是压在史实背后的心情，又要怎么去讲述？站在一个正义的立场批判另一方的邪恶是这个世界上最容易的事情，就像父亲骂儿子不该砸人家玻璃一样。但是凡事皆有因，不反省的过错不会停止。就像片中所说：起风了，要好好活下去。但是风停之后，活下来的人总要为下一场大风做点什么。','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/3faa5da0-3f0e-4a4e-a1e2-98c5524dd973.png','2025-03-10 11:33:24','2025-03-10 11:33:24',7,5,'站在时间·空间的交叉点：中国和日本【有剧透】'),(7,13,'导言\n\n我知道你的行为，你也不冷也不热；我巴不得你或冷或热。你既如温水，也不冷也不热，所以我必从我口中把你吐出去。（启3：16）\n1984年7月18日，安德烈·塔可夫斯基于伦敦圣詹姆士教堂发表演说，或许也可说是一次布道，关于启示与希望、时间与历史，以及信仰的缺失。“爱人如己”的教诲遭遇严峻的危机，\n\n“人们晓得去赞美高山的顶，大海的浪，江河的洪流，浩浩无垠的海滩，千万星辰的运行，却独独遗弃了自己”（奥古斯丁语）\n“活的眼”（不妨想想卢梭笔下的沃勒玛先生）仅是个反讽的理想。同时，由于经济原则压抑了牺牲原则，对自我的爱不可避免地被歪曲为自负或自私，由此形成的封闭主体幻想着自足（autarky）与自我主权（autarchy）。\n\n稀薄的自我意识和占有统治地位的自私态度并不相矛盾，毋宁说源于同一种不可能性，即自私的不可能。我们总是欲望着别人的欲望，而逃避这一结构的对孤独的欲望根本上居于否定性的一面，空洞的自足（autarky）与自我主权（autarchy）是对纯粹的孤独（不可触及而近乎于死亡）的补偿（德里达语）。社会中原子化的个体心灵总是被充填着的，自私假定的封闭状态自始便不存在。与此同时，似乎独属于我们的记忆也根本不满足自足与自我主权的条件——我们对自我进行日常确认用到的材料远少于我们实际拥有的那些过去。\n\n马克思致卢格的信中（1843年9月）写道：\n\n只有当世界意识到它早已拥有它所梦想的事物，它才可能真正驾驭它。（dass die Welt längst den Traum von einer Sache besitzt, von der sie nur das Bewusstsein besitzen muss, um sie wirklich zu besitzen）\n过去乃是有待认领的财产，并不存在自发的过去与当下的有机关系，\n\n过去随身带着一份时间的清单，它通过这份时间 的清单而被托付给赎救（本雅明语）\n当下应该夺取属于自己的过去，应该在过去的意象中认出自身的“史前史”废墟，由此从褪色的业已败坏的“历史”中浮出水面。\n\n以上的鹦鹉学舌，是为了引出两个基本主题，即封闭主体的破坏与对过去的认领，而这正是我在《败犬女主太多了》（下文统称“败犬”）中看到的主线。我主要对目前的小说文本进行分析。正文分成三个基本部分，第一部分厘清作品中的不同“时间”，第二部分从时间体验的角度入手，分析人物，主要是主人公温水和彦的变化，第三部分对未来整体剧情走向做可能的预测。全文所引文本来源于台版中译本。','','2025-03-10 13:57:00','2025-03-10 13:57:00',4,1,'时间与欲望——一个主题化尝试'),(8,14,'一直想写一篇关于《挪威的森林》的心得，10年了，也该动笔了。\n\n我大概是中国第一代读村上春树书的人。\n\n91年，一本书在南京的大学校园里悄悄流行，那是《挪威的森林》，不是林少华的版本，也不是赖明珠。我也赶时髦，在南京大学门口的书摊上买了一本，然后这本书10年没有搁下，它现在正静静地躺在我的枕头边。\n\n这本书不知看了多少次，书上密密麻麻地记载了从20岁到30岁不同阶段我的心得体会，每次随人生阅历地增长，都能有新的感悟。\n\n当年我第一次打开《挪威的森林》，一下子就被它领入略带感伤的青春情韵之中。小说情节是平平的，笔调是缓缓的，语气是淡淡的，然而字里行间却鼓涌着一股无可抑制的冲击波，激起我强烈的心灵震颤与共鸣。看《挪威的森林》可以从任何地方开始，每次翻开都会给我带来心灵的休息和艺术的感染。十年来，我始终沉醉于其中，屡屡不能自拔。多少次，我问自己，这是为什么？我想在写作此文的梳理过程中，给自己一个早该有的答案。\n\n10年之痒，该瓜熟蒂落了。\n\n一、 说明\n《挪威的森林》自1987年在日本问世以来，在日本已销出760余万册（1996年统计），这在只有一亿多人口的日本是一个奇迹，平均每十五个日本人就有一人有这本书。在中国的统计数字不一，但常见说法是三百多万。最近常在上海，有时也去北京出差，看到《挪威的森林》在北京风入松、上海书城等著名书店排行榜上，屹立前十名近一年时间，而这股购书热潮还在如火如荼地高涨着。\n\n《挪威的森林》有多个版本，很多人知道林少华版本，有些人从网路上知道台湾赖明珠的版本，很少有人知道还有一个老版本。这个老版本的译者是谁，我早已忘却了，这两年问过多人，至今我还不知道答案。关于版本优劣，历来是喜欢村上春树的朋友们争论的话题，和BBS上板砖大战一样，我从来不参与这种争论，仁者乐山，智者乐水，不必强求。\n\n这里只简单说一下，我所感知的这三个版本的区别。老版本在我印象里是意译，更为传神地表达日本文学地细腻和委婉，该版本与林、赖版本的最大区别是心理地翻译更出色，特别该版本很多性行为和性心理的描写相当到位，在以后的林、赖版本里找不到了，都有大量删节，恐怕和时代有关系。林少华版本是最常见的，他的版本影响力最大，一版再版。赖明珠的版本常见于网路，作为台湾译者，她似乎有和席娟同样的文风，这让很多新新人类拍手欢迎，但遗憾地是她的版本翻译不全。\n\n我老版本《挪威的森林》于1994年被一个好朋友借走了，然后辗转流落于民间，最后不知所终。我很心痛，不仅是该版本发行较少，关键是书里记载了当年20—23岁的我许多感慨与领悟，这是我从青涩走向成熟的一段不可或缺的真实记录。\n\n曾在一个BBS上看到有人愿以原书10倍价格购买老版本，这肯定是一个老读者。后来又买过两次林少华版本，都被朋友借走，借走了我不再心疼，因为书店里还能买到，只为多了一个喜爱《挪威的森林》朋友而高兴。为以下行文方便和看过《挪威的森林》朋友不至误解，说明一下，我现在手中的书版本是1996年7月漓江出版社林少华版本。\n\n在下文某个章节，会写一下记载在《挪威的森林》里空白处我的一些感悟，现在提上来一段写在书中最后一页的后记，真实记录。\n\n“1997年10月1日23：38　　苏州竹辉小筑\n这几年每年3月中旬我都要变更一次工作，今年也不例外。又一次在异乡，在被网站热火朝天宣扬的今天，我还是喜欢这本书。什么都在变，唯有真爱不变，本性不变。\n\n每一次阅读都会有一种新的体会，人生的阅历使我越来越明白书中的男女，从某种程度讲，我何尝不是和他们一样。\n\n几个小时后，太阳照常升起，我又要开始漂泊。”','','2025-03-11 16:37:04','2025-03-11 16:37:04',2,1,'十年之痒（一）');
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publish`
--

DROP TABLE IF EXISTS `publish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `publish` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '出版社名称',
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `publish_storage_address_id_fk` (`address`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publish`
--

LOCK TABLES `publish` WRITE;
/*!40000 ALTER TABLE `publish` DISABLE KEYS */;
INSERT INTO `publish` VALUES (1,'人民出版社','北京市东城区安定门东大街5号！'),(3,'高等教育出版社','北京市朝阳区管庄南街1号'),(4,'人民教育出版社','北京市海淀区中关村大街18号'),(5,'商务印书馆','北京市东城区王府井大街36号'),(6,'清华大学出版社','北京市海淀区清华园1号'),(7,'北京大学出版社','北京市海淀区成府路205号'),(8,'机械工业出版社','北京市西城区百万庄大街22号'),(9,'电子工业出版社','北京市海淀区万寿路173号'),(11,'人民邮电出版社','北京市东城区夕照寺街14号'),(12,'外语教学与研究出版社','北京市西城区三里河路21号'),(14,'中国电力出版社','北京市西城区三里河路6号'),(15,'中国建筑工业出版社','测试'),(16,'中国轻工业出版社','北京市东城区广渠门内大街12号'),(17,'北京译文出版社','北京市朝阳区光华路15号'),(18,'清华科技出版社','北京市海淀区中关村东路1号'),(19,'中国财政经济出版社','北京市西城区月坛南街14号'),(20,'中国农业出版社','北京市朝阳区农展馆北路2号'),(21,'上海译文出版社','上海市黄浦区福建中路193号世纪出版大厦15~17层');
/*!40000 ALTER TABLE `publish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `content` varchar(200) DEFAULT NULL COMMENT '评论内容',
  `book_id` int DEFAULT NULL COMMENT '图书id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  `is_audit` tinyint(1) NOT NULL DEFAULT '1',
  `like_count` int unsigned NOT NULL DEFAULT '0',
  `image` varchar(255) NOT NULL DEFAULT 'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg',
  `description` varchar(50) DEFAULT NULL COMMENT '说明',
  `post_id` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `idx_book` (`book_id`),
  KEY `idx_post` (`post_id`),
  CONSTRAINT `review___post` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='图书评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (2,13,'这本书真的太好看了！',9,'2025-03-06 12:54:57',1,4,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg','没问题',NULL),(4,13,'顶顶顶',9,'2025-03-06 17:22:43',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg',NULL,NULL),(6,13,'笑死我了，这本书真的有意思😉',9,'2025-03-06 17:34:16',1,4,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg',NULL,NULL),(7,14,'有意思哈，我在自娱自乐，哈哈哈',9,'2025-03-06 17:51:22',1,1,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/65044477-aecc-4abf-af54-2a7b5245eb88.jpg',NULL,NULL),(8,14,'特别喜欢这本书，这是我目前看过最感动的小说了🥰',15,'2025-03-06 17:52:08',1,3,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/65044477-aecc-4abf-af54-2a7b5245eb88.jpg',NULL,NULL),(9,14,'为什么没有展示我发的评论？',11,'2025-03-06 17:57:35',1,4,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/65044477-aecc-4abf-af54-2a7b5245eb88.jpg',NULL,NULL),(10,13,'有问题！',7,'2025-03-07 14:03:08',1,4,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','没有意义的评论',NULL),(11,13,'你好啊',11,'2025-03-07 14:58:57',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',NULL),(12,13,'你要好好的活着！',6,'2025-03-07 15:00:39',1,0,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',NULL),(14,13,'这本书给我的印象很深，经常在不安的时候读😮',11,'2025-03-07 16:11:18',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',NULL),(18,13,'没有评论吗？',NULL,'2025-03-10 11:53:41',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',6),(19,13,'你好',11,'2025-03-10 13:39:09',1,1,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',NULL),(20,13,'作者写的真好！',NULL,'2025-03-10 13:51:14',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',6),(21,13,'原评论在豆瓣哦：https://movie.douban.com/review/16124742/',NULL,'2025-03-10 13:57:58',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',7),(22,12,'\n作者写的真好！😀',NULL,'2025-03-10 14:03:50',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg','未检测出风险',6),(23,13,'一口气读完但却没有能力评价。',9,'2025-03-10 17:17:19',1,2,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',NULL),(24,14,'宫崎骏是最爱，但从未曾到电影院看过，因为不想从腰包里掏出钱让日本人赚了。这种喜欢不是强烈的追星跟风，只是心里一种慢慢的感觉，让人觉得暖，觉得安心。宫崎骏所要引发的深思是全人类的，出发立意，可大可小。起风了还没看，但很喜欢楼主的这篇评论。',NULL,'2025-03-10 17:23:04',1,1,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/65044477-aecc-4abf-af54-2a7b5245eb88.jpg','未检测出风险',6),(25,14,'写的真的蛮不错的！不过宫崎骏的电影我最喜欢《幽灵公主》',NULL,'2025-03-10 19:19:43',1,1,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/65044477-aecc-4abf-af54-2a7b5245eb88.jpg','未检测出风险',6),(26,13,'写的不错哦😉',NULL,'2025-03-11 16:46:42',1,0,'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg','未检测出风险',8);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review_like`
--

DROP TABLE IF EXISTS `review_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_like` (
  `review_id` int DEFAULT NULL COMMENT '评论id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  KEY `review_like_ibfk_1` (`review_id`),
  KEY `review_like_ibfk_2` (`user_id`),
  CONSTRAINT `review_like_ibfk_1` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `review_like_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_like`
--

LOCK TABLES `review_like` WRITE;
/*!40000 ALTER TABLE `review_like` DISABLE KEYS */;
INSERT INTO `review_like` VALUES (6,13),(9,13),(9,14),(2,13),(11,14),(14,13),(10,14),(2,14),(4,14),(11,13),(19,13),(21,13),(21,12),(18,12),(20,12),(22,12),(20,13),(22,13),(23,13),(23,14),(8,14),(24,14),(18,13),(8,13),(14,14),(25,14);
/*!40000 ALTER TABLE `review_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_address`
--

DROP TABLE IF EXISTS `storage_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storage_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `area` varchar(20) NOT NULL COMMENT '地区',
  `shelf` varchar(10) DEFAULT NULL COMMENT '书架编号',
  `floor` tinyint DEFAULT NULL COMMENT '楼层',
  `rack_layer` tinyint DEFAULT NULL COMMENT '书架楼层',
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_address`
--

LOCK TABLES `storage_address` WRITE;
/*!40000 ALTER TABLE `storage_address` DISABLE KEYS */;
INSERT INTO `storage_address` VALUES (1,'广州市图书馆','A-101-A面',3,2,'广州市图书馆 3楼 A-101-A面架 2层'),(3,'深圳图书馆','C-303-A面',2,1,'深圳图书馆 2楼 C-303-A面架 1层'),(6,'国家图书馆','F-606-A面',5,2,'国家图书馆 5楼 F-606-A面架 2层'),(7,'上海图书馆','G-707-A面',4,4,'上海图书馆 4楼 G-707-A面架 4层'),(8,'上海图书馆','H-808-B面',4,1,'上海图书馆 4楼 H-808-B面架 1层'),(10,'广职图书馆','东面4-10B面',4,2,'广职图书馆 4楼 东面4-10B面架 2层'),(11,'广东职业技术学院图书馆','1',4,2,'广东职业技术学院图书馆 4楼 1架 2层');
/*!40000 ALTER TABLE `storage_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `theme_color` char(7) NOT NULL DEFAULT '#12cae2',
  `created_by` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `idx_name` (`name`),
  KEY `tags_ibfk_1` (`created_by`),
  CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'读后感','#12cae2',12),(2,'读书笔记','#12cae2',12),(3,'影评','#12cae2',12),(4,'王小波的精神世界','#409EFF',NULL),(5,'大学教育','#DCE7F3',NULL),(6,'动漫评价','#C2D5E9',NULL),(7,'书评','#C7F4DC',NULL);
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_question`
--

DROP TABLE IF EXISTS `user_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户ID',
  `borrow_record_id` int DEFAULT NULL COMMENT '关联借阅记录ID',
  `appeal_type` tinyint(1) NOT NULL DEFAULT '3' COMMENT '0=罚款申诉 1=借阅申诉 2=评论申诉 3=其他',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0=待处理 1=已解决',
  `manager_id` int DEFAULT NULL COMMENT '处理人ID',
  `note` varchar(200) NOT NULL COMMENT '用户提交的简要描述',
  `process_note` varchar(200) DEFAULT NULL COMMENT '处理意见',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `end_time` datetime DEFAULT NULL COMMENT '处理完成时间',
  `is_read_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1:未读 0:已读',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`),
  KEY `idx_user` (`user_id`),
  KEY `manager_id` (`manager_id`),
  CONSTRAINT `user_question_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_question_ibfk_2` FOREIGN KEY (`manager_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_question`
--

LOCK TABLES `user_question` WRITE;
/*!40000 ALTER TABLE `user_question` DISABLE KEYS */;
INSERT INTO `user_question` VALUES (2,13,14,3,1,9,'???为啥啊','测试而已，我会帮你处理的！','2025-03-04 16:35:29','2025-03-04 18:01:52',0),(3,13,16,3,1,9,'知道','哦哦哦','2025-03-05 15:10:30','2025-03-05 15:11:30',0),(4,13,17,3,1,9,'知道','杀杀杀','2025-03-05 15:50:19','2025-03-05 15:51:16',0),(5,13,24,3,1,9,'你有问题吧？','收到了','2025-03-08 17:49:52','2025-03-08 18:05:58',0);
/*!40000 ALTER TABLE `user_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `name` varchar(30) NOT NULL COMMENT '真实姓名',
  `password` varchar(60) NOT NULL COMMENT '密码（bcrypt加密）',
  `card_id` char(18) NOT NULL COMMENT '身份证号',
  `phone` char(11) NOT NULL COMMENT '手机号',
  `image` varchar(255) NOT NULL DEFAULT 'https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1正常 0封禁）',
  `confine` tinyint unsigned NOT NULL DEFAULT '5' COMMENT '最大借书数限制',
  `gender` tinyint DEFAULT '0' COMMENT '性别（0未知 1男 2女）',
  `role` tinyint NOT NULL DEFAULT '0' COMMENT '角色（0用户 1管理员）',
  `reg_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `address` varchar(100) DEFAULT NULL COMMENT '用户地址',
  `violation_reason` varchar(50) DEFAULT NULL COMMENT '封禁原因',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `card_id` (`card_id`),
  UNIQUE KEY `phone` (`phone`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (9,'小胡','胡','$2a$10$qETxuwUzB.x.Vj.rp/1Blegtnt3HajiM.kNdqH6UCqYoPyIUHbnR6','44142499000','14754319998','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg',1,7,1,1,'2025-02-24 17:02:23','2025-04-09 17:49:15','梅州五华红星',NULL),(11,'小','胡','$2a$10$0NmPHY50KP/vQZnoYRe34upbGMHWf9JRR0Yet5RDJxZk2M2pUCZzm','44142499001','14754319991','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg',1,5,1,1,'2025-02-24 17:37:31',NULL,'梅州市五华111','密码忘记了！'),(12,'小红','邢红','$2a$10$Ii9Z/9W1B2lFKiri0ByK6.7y5mmnWEEQlWx3tLvcB78iGJcmxTM1m','4414190009901111','1475439999','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/user/default.jpg',1,5,2,0,'2025-02-27 17:40:14','2025-04-09 17:46:26','北京市',NULL),(13,'喜喜','张喜','$2a$10$W2mw4wV2h1vUKZann73nauNavukHyF4v3F.yGyxaoBpFxlsywcWdu','441424567890933','14754319907','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/4387c981-e53a-485a-927f-5136aa85fdbb.jpg',1,10,2,0,'2025-02-27 20:36:03','2025-03-21 17:34:44','云南省玉溪市',NULL),(14,'菜鸟','小小','$2a$10$.gZ7gbtdzXVlK/OCuud/QuaOBQXomcOFwpHYJbkt8BjpBU1iEtQ2O','441433551111334411','14799909009','https://huzhijian-springboot-csse.oss-cn-guangzhou.aliyuncs.com/library/65044477-aecc-4abf-af54-2a7b5245eb88.jpg',1,2,2,0,'2025-03-06 17:50:35','2025-03-21 17:12:37','北京市朝阳区',NULL);
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

-- Dump completed on 2025-04-28  8:42:28
