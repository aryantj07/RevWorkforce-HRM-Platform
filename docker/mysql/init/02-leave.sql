-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: revworkforce_leave
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
-- Current Database: `revworkforce_leave`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `revworkforce_leave` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `revworkforce_leave`;

--
-- Table structure for table `holidays`
--

DROP TABLE IF EXISTS `holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holidays` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `holiday_date` date NOT NULL,
  `is_recurring` bit(1) NOT NULL,
  `name` varchar(100) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnlp33xi3gy9njxmakcj5dq6l3` (`holiday_date`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holidays`
--

LOCK TABLES `holidays` WRITE;
/*!40000 ALTER TABLE `holidays` DISABLE KEYS */;
INSERT INTO `holidays` VALUES (2,'2026-09-30 14:52:43.966449','Gandhi Jayanti - National Holiday on 2nd October','2026-10-02',_binary '','Gandhi Jayanti','2026-09-30 14:52:43.966449');
/*!40000 ALTER TABLE `holidays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_balances`
--

DROP TABLE IF EXISTS `leave_balances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_balances` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `employee_id` bigint NOT NULL,
  `pending_days` int NOT NULL,
  `total_days` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `used_days` int NOT NULL,
  `year` int NOT NULL,
  `leave_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbt0kj5tlqfwi0vny4dalj5b66` (`employee_id`,`leave_type_id`,`year`),
  KEY `FK86791wotycqa54js45s9396wy` (`leave_type_id`),
  CONSTRAINT `FK86791wotycqa54js45s9396wy` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_balances`
--

LOCK TABLES `leave_balances` WRITE;
/*!40000 ALTER TABLE `leave_balances` DISABLE KEYS */;
INSERT INTO `leave_balances` VALUES (1,'2026-09-28 13:54:35.430533',3,0,12,'2026-09-28 14:15:51.001659',4,2026,1),(3,'2026-09-29 05:42:02.911397',7,0,12,'2026-09-29 07:57:07.227427',2,2026,1),(4,'2026-09-30 14:34:50.274087',8,0,12,'2026-09-30 14:34:50.274087',0,2026,1),(5,'2026-09-30 14:37:43.630137',8,0,7,'2026-09-30 14:38:23.759687',3,2026,2),(6,'2026-09-30 15:04:37.815738',9,0,12,'2026-09-30 15:04:37.815738',0,2026,1),(7,'2026-09-30 15:04:37.822739',9,0,7,'2026-09-30 15:04:37.822739',0,2026,2);
/*!40000 ALTER TABLE `leave_balances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_quotas`
--

DROP TABLE IF EXISTS `leave_quotas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_quotas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `total_days` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `year` int NOT NULL,
  `leave_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKisxnsme6l050yv8bgymqy4pec` (`leave_type_id`,`year`),
  CONSTRAINT `FKln0cfsvjd4q7htq44wuf64bc0` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_quotas`
--

LOCK TABLES `leave_quotas` WRITE;
/*!40000 ALTER TABLE `leave_quotas` DISABLE KEYS */;
INSERT INTO `leave_quotas` VALUES (1,'2026-09-30 14:37:03.042573',7,'2026-09-30 14:37:03.042573',2026,2);
/*!40000 ALTER TABLE `leave_quotas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_requests`
--

DROP TABLE IF EXISTS `leave_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_requests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `applied_at` datetime(6) NOT NULL,
  `approver_comments` varchar(500) DEFAULT NULL,
  `approver_id` bigint DEFAULT NULL,
  `employee_id` bigint NOT NULL,
  `end_date` date NOT NULL,
  `reason` varchar(500) NOT NULL,
  `reviewed_at` datetime(6) DEFAULT NULL,
  `start_date` date NOT NULL,
  `status` enum('APPROVED','CANCELLED','PENDING','REJECTED') NOT NULL,
  `total_days` int NOT NULL,
  `leave_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_status` (`status`),
  KEY `idx_start_date` (`start_date`),
  KEY `FK26il0qrl79p6etqwn0ae6l43b` (`leave_type_id`),
  CONSTRAINT `FK26il0qrl79p6etqwn0ae6l43b` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_requests`
--

LOCK TABLES `leave_requests` WRITE;
/*!40000 ALTER TABLE `leave_requests` DISABLE KEYS */;
INSERT INTO `leave_requests` VALUES (1,'2026-09-28 13:54:35.438557','Approved for integration testing.',1,3,'2026-10-06','Integration testing','2026-09-28 14:00:27.134513','2026-10-05','APPROVED',2,1),(2,'2026-09-28 14:02:53.237132','Approved for notification integration testing.',1,3,'2026-10-13','Approval notification test','2026-09-28 14:05:07.799484','2026-10-12','APPROVED',2,1),(3,'2026-09-28 14:14:56.869008','Rejected for integration testing.',1,3,'2026-10-21','Testing rejected leave notification','2026-09-28 14:15:50.978084','2026-10-20','REJECTED',2,1),(5,'2026-09-29 05:42:02.916381','Enjoy',5,7,'2026-10-01','Test Leave','2026-09-29 07:57:07.106437','2026-09-30','APPROVED',2,1),(6,'2026-09-30 14:37:43.638647','Apporved and Take care!',5,8,'2026-10-07','Medical Emergency, Admitted in the hospital.','2026-09-30 14:38:23.735136','2026-10-05','APPROVED',3,2);
/*!40000 ALTER TABLE `leave_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_types`
--

DROP TABLE IF EXISTS `leave_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `is_paid` bit(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc7knnvg6a1wkmt3f2gciae83e` (`code`),
  UNIQUE KEY `UKjk0ragqnw78kwwdopm49iea60` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_types`
--

LOCK TABLES `leave_types` WRITE;
/*!40000 ALTER TABLE `leave_types` DISABLE KEYS */;
INSERT INTO `leave_types` VALUES (1,'CASUAL','2026-09-28 13:53:46.725145','Casual leave for personal requirements',_binary '',_binary '','Casual Leave','2026-09-28 13:53:46.725145'),(2,'M-1','2026-09-30 14:36:55.209290','Medical Leave for medical emergencies.',_binary '',_binary '','Medical Leave','2026-09-30 14:36:55.209290');
/*!40000 ALTER TABLE `leave_types` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-01  0:16:08
