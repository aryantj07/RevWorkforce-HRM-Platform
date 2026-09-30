-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: revworkforce_employee
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
-- Current Database: `revworkforce_employee`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `revworkforce_employee` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `revworkforce_employee`;

--
-- Table structure for table `announcements`
--

DROP TABLE IF EXISTS `announcements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcements` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `message` text,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcements`
--

LOCK TABLES `announcements` WRITE;
/*!40000 ALTER TABLE `announcements` DISABLE KEYS */;
INSERT INTO `announcements` VALUES (9,_binary '','2026-09-30 13:23:41.899237','The annual company holiday schedule has been published. Please review the updated holiday calendar in the HR portal.','Annual Company Holiday Schedule'),(10,_binary '','2026-09-30 14:34:45.513157','Testinggg','Test');
/*!40000 ALTER TABLE `announcements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES (2,'Software engineering, application development and technical delivery','Engineering'),(3,'Financial planning, accounting and corporate finance','Finance'),(4,'People operations, recruitment and employee relations','Human Resources'),(5,'Sales, business development and customer growth','Sales'),(6,'Brand, digital marketing and content strategy','Marketing'),(7,'Product strategy, analysis and business requirements','Product Management'),(8,'Business operations, process management and service delivery','Operations'),(15,'IT infrastructure, support and enterprise technology','Information Technology');
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `designations`
--

DROP TABLE IF EXISTS `designations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `designations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designations`
--

LOCK TABLES `designations` WRITE;
/*!40000 ALTER TABLE `designations` DISABLE KEYS */;
INSERT INTO `designations` VALUES (2,'Develops and maintains software applications','Software Engineer'),(3,'Leads engineering teams and technical delivery','Engineering Manager'),(4,'Designs and develops complex software solutions','Senior Software Engineer'),(5,'Leads financial planning and accounting operations','Finance Manager'),(6,'Analyzes financial data and business performance','Financial Analyst'),(7,'Manages accounting records and financial transactions','Accountant'),(8,'Leads human resources and people operations','HR Manager'),(9,'Handles employee relations and HR operations','HR Executive'),(10,'Manages recruitment and talent acquisition','Talent Acquisition Specialist'),(11,'Leads sales strategy and revenue operations','Sales Manager'),(12,'Manages customer acquisition and sales activities','Sales Executive'),(13,'Develops business opportunities and partnerships','Business Development Executive'),(14,'Leads marketing strategy and campaigns','Marketing Manager'),(15,'Executes digital and marketing initiatives','Marketing Specialist'),(16,'Creates and manages marketing content','Content Marketing Executive'),(17,'Owns product strategy, roadmap and delivery','Product Manager'),(18,'Analyzes product data and user requirements','Product Analyst'),(19,'Translates business requirements into solutions','Business Analyst'),(20,'Leads business operations and process improvement','Operations Manager'),(21,'Supports operational processes and service delivery','Operations Executive'),(22,'Leads enterprise IT operations and infrastructure','IT Manager'),(23,'Provides technical support and IT services','IT Support Engineer');
/*!40000 ALTER TABLE `designations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `date_of_joining` date DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `designation_id` bigint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (2,'Goa','2026-09-28',2,4,'rahul.sharma@revworkforce.com','Rahul','Sharma','9876500011','ACTIVE',NULL),(3,'Goa','2026-09-28',2,2,'priya.nair@revworkforce.com','Priya','Nair','9876500012','ACTIVE',1),(4,'Goa','2026-09-28',3,6,'ananya.mehta@revworkforce.com','Ananya','Mehta','9876500013','ACTIVE',NULL),(5,'Goa','2026-09-28',4,9,'sneha.patel@revworkforce.com','Sneha','Patel','9876500014','ACTIVE',NULL),(6,'Goa','2026-09-28',5,12,'rohan.kapoor@revworkforce.com','Rohan','Kapoor','9876500015','ACTIVE',NULL),(7,'Goa, India','2026-10-05',2,2,'aryan@example.com','Aryan','Johnson','928374678','ACTIVE',4),(8,'Madrid, Spain','2026-10-01',2,2,'ronaldo@example.com','Cristiano','Ronaldo','','ACTIVE',6),(9,'Near MES College, Sancoale','2026-10-01',3,5,'manager2@example.com','Manager','Two','123456789','ACTIVE',7),(10,'Panaji, Goa','2026-10-01',2,3,'engmanager@revworkforce.com','Arjun','Mehta','9876500101','ACTIVE',8),(11,'Panaji, Goa','2026-10-01',4,8,'hrmanager@revworkforce.com','Neha','Sharma','9876500102','ACTIVE',9),(12,'Panaji, Goa','2026-10-01',5,11,'salesmanager@revworkforce.com','Vikram','Patel','9876500103','ACTIVE',10),(13,'Panaji, Goa','2026-10-01',6,14,'marketingmanager@revworkforce.com','Kavya','Nair','9876500104','ACTIVE',11),(14,'Panaji, Goa','2026-10-01',7,17,'productmanager@revworkforce.com','Rohan','Kapoor','9876500105','ACTIVE',12),(15,'Panaji, Goa','2026-10-01',8,20,'operationsmanager@revworkforce.com','Meera','Iyer','9876500106','ACTIVE',13),(16,'Panaji, Goa','2026-10-01',15,22,'itmanager@revworkforce.com','Siddharth','Rao','9876500107','ACTIVE',14);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_configurations`
--

DROP TABLE IF EXISTS `system_configurations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `system_configurations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `config_key` varchar(255) NOT NULL,
  `config_value` text NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK867jsfttn43kaegq3c6c24b7r` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_configurations`
--

LOCK TABLES `system_configurations` WRITE;
/*!40000 ALTER TABLE `system_configurations` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_configurations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-01  0:16:30
