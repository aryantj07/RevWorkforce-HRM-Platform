-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: revworkforce_notification
-- ------------------------------------------------------
-- Server version	26.7.0

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
-- Current Database: `revworkforce_notification`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `revworkforce_notification` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `revworkforce_notification`;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `message` varchar(1000) NOT NULL,
  `title` varchar(150) NOT NULL,
  `type` enum('ANNOUNCEMENT','GENERAL','LEAVE_APPROVED','LEAVE_REJECTED','LEAVE_SUBMITTED','LOW_LEAVE_BALANCE','PERFORMANCE_FEEDBACK','PERFORMANCE_REVIEW') NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_is_read` (`is_read`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (2,'2026-09-25 07:56:51.933864',_binary '','Notification service integration test','Test Notification','GENERAL',100),(3,'2026-09-28 09:44:07.471676',_binary '\0','The system will undergo scheduled maintenance tonight.','System Maintenance','ANNOUNCEMENT',1),(4,'2026-09-28 09:44:07.554705',_binary '\0','The system will undergo scheduled maintenance tonight.','System Maintenance','ANNOUNCEMENT',2),(5,'2026-09-28 09:44:07.567726',_binary '\0','The system will undergo scheduled maintenance tonight.','System Maintenance','ANNOUNCEMENT',3),(6,'2026-09-28 09:47:26.122900',_binary '\0','This announcement was created through the API Gateway.','Gateway Integration Test','ANNOUNCEMENT',1),(7,'2026-09-28 09:47:26.134660',_binary '\0','This announcement was created through the API Gateway.','Gateway Integration Test','ANNOUNCEMENT',2),(8,'2026-09-28 09:47:26.147659',_binary '','This announcement was created through the API Gateway.','Gateway Integration Test','ANNOUNCEMENT',3),(9,'2026-09-28 13:54:36.041427',_binary '\0','Your leave request from 2026-10-05 to 2026-10-06 has been submitted successfully.','Leave Request Submitted','LEAVE_SUBMITTED',1),(10,'2026-09-28 14:02:53.343522',_binary '\0','Your leave request from 2026-10-12 to 2026-10-13 has been submitted successfully.','Leave Request Submitted','LEAVE_SUBMITTED',1),(11,'2026-09-28 14:05:07.824068',_binary '\0','Your leave request from 2026-10-12 to 2026-10-13 has been approved.','Leave Request Approved','LEAVE_APPROVED',1),(12,'2026-09-28 14:14:57.025880',_binary '\0','Your leave request from 2026-10-20 to 2026-10-21 has been submitted successfully.','Leave Request Submitted','LEAVE_SUBMITTED',1),(13,'2026-09-28 14:15:50.992146',_binary '\0','Your leave request from 2026-10-20 to 2026-10-21 has been rejected.','Leave Request Rejected','LEAVE_REJECTED',1),(14,'2026-09-28 17:42:51.868896',_binary '\0','Your manager has submitted feedback on your performance review: Good progress. Keep improving your technical skills and continue contributing to the team.','Performance Feedback Received','PERFORMANCE_FEEDBACK',1),(16,'2026-09-29 07:57:07.209901',_binary '','Your leave request from 2026-09-30 to 2026-10-01 has been approved.','Leave Request Approved','LEAVE_APPROVED',4),(17,'2026-09-29 08:08:30.203380',_binary '','Your manager has submitted feedback on your performance review: Good progress during the review period. Continue improving backend development skills and contribute actively to team projects.','Performance Feedback Received','PERFORMANCE_FEEDBACK',4),(18,'2026-09-30 13:18:24.893986',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening.','System Maintenance Notice','ANNOUNCEMENT',1),(19,'2026-09-30 13:18:25.046932',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening.','System Maintenance Notice','ANNOUNCEMENT',2),(20,'2026-09-30 13:18:25.065308',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening.','System Maintenance Notice','ANNOUNCEMENT',3),(22,'2026-09-30 13:18:25.104252',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening.','System Maintenance Notice','ANNOUNCEMENT',5),(23,'2026-09-30 13:19:30.588984',_binary '\0','Public holiday declared on 2nd October!','Holiday Notice for 2nd October','ANNOUNCEMENT',1),(24,'2026-09-30 13:19:30.609500',_binary '\0','Public holiday declared on 2nd October!','Holiday Notice for 2nd October','ANNOUNCEMENT',2),(25,'2026-09-30 13:19:30.626370',_binary '\0','Public holiday declared on 2nd October!','Holiday Notice for 2nd October','ANNOUNCEMENT',3),(27,'2026-09-30 13:19:30.661287',_binary '\0','Public holiday declared on 2nd October!','Holiday Notice for 2nd October','ANNOUNCEMENT',5),(28,'2026-09-30 13:20:21.794802',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening from 8 PM to 9 PM.','System Maintenance Notice','ANNOUNCEMENT',1),(29,'2026-09-30 13:20:21.819026',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening from 8 PM to 9 PM.','System Maintenance Notice','ANNOUNCEMENT',2),(30,'2026-09-30 13:20:21.836552',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening from 8 PM to 9 PM.','System Maintenance Notice','ANNOUNCEMENT',3),(32,'2026-09-30 13:20:21.870953',_binary '\0','The HRM system will undergo scheduled maintenance on Friday evening from 8 PM to 9 PM.','System Maintenance Notice','ANNOUNCEMENT',5),(33,'2026-09-30 13:23:41.974416',_binary '\0','The annual company holiday schedule has been published. Please review the updated holiday calendar in the HR portal.','Annual Company Holiday Schedule','ANNOUNCEMENT',1),(34,'2026-09-30 13:23:42.001414',_binary '\0','The annual company holiday schedule has been published. Please review the updated holiday calendar in the HR portal.','Annual Company Holiday Schedule','ANNOUNCEMENT',2),(35,'2026-09-30 13:23:42.022474',_binary '\0','The annual company holiday schedule has been published. Please review the updated holiday calendar in the HR portal.','Annual Company Holiday Schedule','ANNOUNCEMENT',3),(36,'2026-09-30 13:23:42.041474',_binary '','The annual company holiday schedule has been published. Please review the updated holiday calendar in the HR portal.','Annual Company Holiday Schedule','ANNOUNCEMENT',4),(37,'2026-09-30 13:23:42.057091',_binary '\0','The annual company holiday schedule has been published. Please review the updated holiday calendar in the HR portal.','Annual Company Holiday Schedule','ANNOUNCEMENT',5),(38,'2026-09-30 14:34:45.700375',_binary '\0','Testinggg','Test','ANNOUNCEMENT',1),(39,'2026-09-30 14:34:45.735534',_binary '\0','Testinggg','Test','ANNOUNCEMENT',2),(40,'2026-09-30 14:34:45.746128',_binary '\0','Testinggg','Test','ANNOUNCEMENT',3),(41,'2026-09-30 14:34:45.754129',_binary '\0','Testinggg','Test','ANNOUNCEMENT',4),(42,'2026-09-30 14:34:45.763431',_binary '','Testinggg','Test','ANNOUNCEMENT',5),(43,'2026-09-30 14:34:45.774445',_binary '','Testinggg','Test','ANNOUNCEMENT',6),(44,'2026-09-30 14:37:43.757207',_binary '','Your leave request from 2026-10-05 to 2026-10-07 has been submitted successfully.','Leave Request Submitted','LEAVE_SUBMITTED',6),(45,'2026-09-30 14:38:23.750156',_binary '','Your leave request from 2026-10-05 to 2026-10-07 has been approved.','Leave Request Approved','LEAVE_APPROVED',6);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-01  0:16:40
