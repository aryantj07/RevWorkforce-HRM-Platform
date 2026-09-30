-- MySQL dump 10.13  Distrib 8.0.37, for Win64 (x86_64)
--
-- Host: localhost    Database: revworkforce_user
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
-- Current Database: `revworkforce_user`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `revworkforce_user` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `revworkforce_user`;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `email` varchar(150) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `role` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '','john.updated@example.com','John','Smith','$2a$10$liSqn.FPxaojG7Z8BQq4jOLUGHfC5WSPdCT7XUeST3IDwVD12AxYa','9999999999','MANAGER','john123'),(2,_binary '','admin@revworkforce.com','System','Admin','$2a$10$8VCSXijUk1SJQA9FsTWLwuNVB84pK4OS0UkWUmzbK8zkMPtIEKSey','9999999999','ADMIN','admin1'),(3,_binary '','admin2@revworkforce.com','System','Admin','$2a$10$8DpI.XwkUsYz5owdQQEXKuTnO2fs0LPnZeuDZtTCwf0sZSrgjzBX.','9999999999','ADMIN','admin2'),(4,_binary '','aryan@example.com','Aryan','Johnson','$2a$10$kl7Rex4iiD0zK/nEGw0PGeCQImHxiYfSsCSXQF01NKH2gC1.uvAQa','928374678','EMPLOYEE','aryan7'),(5,_binary '','manager@example.com','Manager','One','$2a$10$UDULLGwYz8ug5aSbfqH39OtVsgTnruZTvjvXJpDEDwfNWLCDrHGDS','123456781','MANAGER','manager1'),(6,_binary '','ronaldo@example.com','Cristiano','Ronaldo','$2a$10$nXes2y0Vi/or1e1QxLbJqOQdV5slLIkYfX7UsNDC5K1NkQT1pcdiS','','EMPLOYEE','ronaldo7'),(7,_binary '','manager2@example.com','Manager','Two','$2a$10$PlsS9MzFZDDL2zsxRrLLHuql/zKTZwl.yZ/jtTIB0jzaFoFjED0j6','123456789','MANAGER','manager2'),(8,_binary '','engmanager@revworkforce.com','Arjun','Mehta','$2a$10$.55VpgOh87sO90pDRoYgCOrCj.eUH0.tPgI4ffMUm6wvC812vegfO','9876500101','MANAGER','engmanager'),(9,_binary '','hrmanager@revworkforce.com','Neha','Sharma','$2a$10$JyKQ2M3SlziWNlNp5PHLL.a6FEz8tHp0Pwa0PuQm0yOViIxzymMcu','9876500102','MANAGER','hrmanager'),(10,_binary '','salesmanager@revworkforce.com','Vikram','Patel','$2a$10$Oasl0TU/NJQqDioYFfrEiOC888zVN9W8HrEDI7wu6LGiaWO6s.7F.','9876500103','MANAGER','salesmanager'),(11,_binary '','marketingmanager@revworkforce.com','Kavya','Nair','$2a$10$tLaHJCAhk93E9ABVPJh4ouF4LanDwxhbA6dCZpYwUDlrhHdnghvsG','9876500104','MANAGER','marketingmanager'),(12,_binary '','productmanager@revworkforce.com','Rohan','Kapoor','$2a$10$VlT94q7J5v4pVQSeSImoE.OedKop2FunsroFfAwZqVjJ8BnzKhsce','9876500105','MANAGER','productmanager'),(13,_binary '','operationsmanager@revworkforce.com','Meera','Iyer','$2a$10$4XehICySOGWpLy1CNPUQwu5RaEDurAYp9Ty.lkZrLaahHndgY4HmW','9876500106','MANAGER','operationsmanager'),(14,_binary '','itmanager@revworkforce.com','Siddharth','Rao','$2a$10$AsWkTFRS2vRP7T8AmyA30u/wtWjPJ5NkNpyqQHhQyGHlAZZeumthK','9876500107','MANAGER','itmanager');
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

-- Dump completed on 2026-10-01  0:16:01
