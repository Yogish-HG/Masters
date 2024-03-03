CREATE DATABASE  IF NOT EXISTS `parkdatabase` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `parkdatabase`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: parkdatabase
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activities`
--

DROP TABLE IF EXISTS `activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activities` (
  `Activity_ID` int NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Description` text,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`Activity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activities`
--

LOCK TABLES `activities` WRITE;
/*!40000 ALTER TABLE `activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `Customer_ID` int NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `PhoneNo` varchar(255) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Customer_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `features`
--

DROP TABLE IF EXISTS `features`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `features` (
  `Feature_ID` int NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Description` text,
  PRIMARY KEY (`Feature_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `features`
--

LOCK TABLES `features` WRITE;
/*!40000 ALTER TABLE `features` DISABLE KEYS */;
/*!40000 ALTER TABLE `features` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `News_ID` int NOT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `Description` text,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`News_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park`
--

DROP TABLE IF EXISTS `park`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park` (
  `Park_ID` int NOT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `Location` varchar(255) DEFAULT NULL,
  `Description` text,
  `ContactInfo` varchar(255) DEFAULT NULL,
  `Timings` varchar(255) DEFAULT NULL,
  `RegionID` int DEFAULT NULL,
  PRIMARY KEY (`Park_ID`),
  KEY `RegionID` (`RegionID`),
  CONSTRAINT `park_ibfk_1` FOREIGN KEY (`RegionID`) REFERENCES `region` (`Region_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park`
--

LOCK TABLES `park` WRITE;
/*!40000 ALTER TABLE `park` DISABLE KEYS */;
/*!40000 ALTER TABLE `park` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park_has_activities`
--

DROP TABLE IF EXISTS `park_has_activities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park_has_activities` (
  `Park_ID` int DEFAULT NULL,
  `Activity_ID` int DEFAULT NULL,
  KEY `Park_ID` (`Park_ID`),
  KEY `Activity_ID` (`Activity_ID`),
  CONSTRAINT `park_has_activities_ibfk_1` FOREIGN KEY (`Park_ID`) REFERENCES `park` (`Park_ID`),
  CONSTRAINT `park_has_activities_ibfk_2` FOREIGN KEY (`Activity_ID`) REFERENCES `activities` (`Activity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park_has_activities`
--

LOCK TABLES `park_has_activities` WRITE;
/*!40000 ALTER TABLE `park_has_activities` DISABLE KEYS */;
/*!40000 ALTER TABLE `park_has_activities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park_has_features`
--

DROP TABLE IF EXISTS `park_has_features`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park_has_features` (
  `Park_ID` int DEFAULT NULL,
  `Feature_ID` int DEFAULT NULL,
  KEY `Park_ID` (`Park_ID`),
  KEY `Feature_ID` (`Feature_ID`),
  CONSTRAINT `park_has_features_ibfk_1` FOREIGN KEY (`Park_ID`) REFERENCES `park` (`Park_ID`),
  CONSTRAINT `park_has_features_ibfk_2` FOREIGN KEY (`Feature_ID`) REFERENCES `features` (`Feature_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park_has_features`
--

LOCK TABLES `park_has_features` WRITE;
/*!40000 ALTER TABLE `park_has_features` DISABLE KEYS */;
/*!40000 ALTER TABLE `park_has_features` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park_has_news`
--

DROP TABLE IF EXISTS `park_has_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park_has_news` (
  `Park_ID` int DEFAULT NULL,
  `News_ID` int DEFAULT NULL,
  KEY `Park_ID` (`Park_ID`),
  KEY `News_ID` (`News_ID`),
  CONSTRAINT `park_has_news_ibfk_1` FOREIGN KEY (`Park_ID`) REFERENCES `park` (`Park_ID`),
  CONSTRAINT `park_has_news_ibfk_2` FOREIGN KEY (`News_ID`) REFERENCES `news` (`News_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park_has_news`
--

LOCK TABLES `park_has_news` WRITE;
/*!40000 ALTER TABLE `park_has_news` DISABLE KEYS */;
/*!40000 ALTER TABLE `park_has_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `park_has_rules`
--

DROP TABLE IF EXISTS `park_has_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `park_has_rules` (
  `Park_ID` int DEFAULT NULL,
  `Rule_ID` int DEFAULT NULL,
  KEY `Park_ID` (`Park_ID`),
  KEY `Rule_ID` (`Rule_ID`),
  CONSTRAINT `park_has_rules_ibfk_1` FOREIGN KEY (`Park_ID`) REFERENCES `park` (`Park_ID`),
  CONSTRAINT `park_has_rules_ibfk_2` FOREIGN KEY (`Rule_ID`) REFERENCES `rules` (`Rule_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `park_has_rules`
--

LOCK TABLES `park_has_rules` WRITE;
/*!40000 ALTER TABLE `park_has_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `park_has_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `Region_ID` int NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Region_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `Reservation_ID` int NOT NULL,
  `Customer_ID` int DEFAULT NULL,
  `Activity_ID` int DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `Duration` int DEFAULT NULL,
  `Participants` int DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`Reservation_ID`),
  KEY `Customer_ID` (`Customer_ID`),
  KEY `Activity_ID` (`Activity_ID`),
  CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`Customer_ID`) REFERENCES `customers` (`Customer_ID`),
  CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`Activity_ID`) REFERENCES `activities` (`Activity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rules`
--

DROP TABLE IF EXISTS `rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rules` (
  `Rule_ID` int NOT NULL,
  `Description` text,
  PRIMARY KEY (`Rule_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rules`
--

LOCK TABLES `rules` WRITE;
/*!40000 ALTER TABLE `rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `rules` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-05 17:40:56
