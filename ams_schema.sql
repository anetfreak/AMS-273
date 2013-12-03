-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	6.0.11-alpha-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema ams_schema
--

CREATE DATABASE IF NOT EXISTS ams_schema;
USE ams_schema;

--
-- Definition of table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customerId` int(7) NOT NULL,
  `personId` int(11) NOT NULL,
  `passportNumber` varchar(8) NOT NULL,
  `nationality` varchar(50) NOT NULL,
  PRIMARY KEY (`customerId`),
  KEY `fk_customer_person_id_idx` (`personId`),
  CONSTRAINT `fk_customer_person_id` FOREIGN KEY (`personId`) REFERENCES `person` (`personId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` (`customerId`,`personId`,`passportNumber`,`nationality`) VALUES 
 (1061830924,123477,'AB1234JP','Indian'),
 (2047378985,123475,'AB1234JP','Indian');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employeeId` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) NOT NULL,
  `workDesc` varchar(150) DEFAULT NULL,
  `position` enum('PILOT','CREW','GROUNDSTAFF','MANAGER') NOT NULL,
  `hireDate` varchar(45) NOT NULL,
  PRIMARY KEY (`employeeId`),
  KEY `fk_employee_person_id_idx` (`personId`),
  CONSTRAINT `fk_employee_person_id` FOREIGN KEY (`personId`) REFERENCES `person` (`personId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`employeeId`,`personId`,`workDesc`,`position`,`hireDate`) VALUES 
 (-1423015938,123476,'Manager','CREW','10102010'),
 (-747222585,123478,'Manager','CREW','10102010');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;


--
-- Definition of table `flight`
--

DROP TABLE IF EXISTS `flight`;
CREATE TABLE `flight` (
  `flight_id` int(11) NOT NULL AUTO_INCREMENT,
  `flightNo` varchar(45) NOT NULL,
  `flightSource` varchar(100) NOT NULL,
  `flightDestination` varchar(100) NOT NULL,
  `flightNoOfSeats` int(11) DEFAULT NULL,
  `airlineName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`flight_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `flight`
--

/*!40000 ALTER TABLE `flight` DISABLE KEYS */;
/*!40000 ALTER TABLE `flight` ENABLE KEYS */;


--
-- Definition of table `flight_time`
--

DROP TABLE IF EXISTS `flight_time`;
CREATE TABLE `flight_time` (
  `flight_id` int(11) NOT NULL,
  `day` varchar(10) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`flight_id`),
  CONSTRAINT `fk_flight_time_id` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`flight_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `flight_time`
--

/*!40000 ALTER TABLE `flight_time` DISABLE KEYS */;
/*!40000 ALTER TABLE `flight_time` ENABLE KEYS */;


--
-- Definition of table `journey`
--

DROP TABLE IF EXISTS `journey`;
CREATE TABLE `journey` (
  `flightId` int(11) NOT NULL,
  `boarding` varchar(50) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `reservationId` int(11) NOT NULL,
  `dateTime` varchar(50) NOT NULL,
  UNIQUE KEY `uk_flight_reservation` (`flightId`,`reservationId`),
  KEY `fk_journey_reservation_id_idx` (`reservationId`),
  KEY `fk_journey_flight_id_idx` (`flightId`),
  CONSTRAINT `fk_journey_flight_id` FOREIGN KEY (`flightId`) REFERENCES `flight` (`flight_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_journey_reservation_id` FOREIGN KEY (`reservationId`) REFERENCES `reservation` (`reservationId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `journey`
--

/*!40000 ALTER TABLE `journey` DISABLE KEYS */;
/*!40000 ALTER TABLE `journey` ENABLE KEYS */;


--
-- Definition of table `locations`
--

DROP TABLE IF EXISTS `locations`;
CREATE TABLE `locations` (
  `locationId` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(50) NOT NULL,
  `stateCode` varchar(3) NOT NULL,
  `airportCode` varchar(3) NOT NULL,
  PRIMARY KEY (`locationId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `locations`
--

/*!40000 ALTER TABLE `locations` DISABLE KEYS */;
/*!40000 ALTER TABLE `locations` ENABLE KEYS */;


--
-- Definition of table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `personId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `zip` int(11) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `personType` enum('EMPLOYEE','CUSTOMER','CREW') NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`personId`)
) ENGINE=InnoDB AUTO_INCREMENT=123479 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `person`
--

/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` (`personId`,`firstName`,`lastName`,`address`,`state`,`city`,`zip`,`dob`,`personType`,`username`,`password`) VALUES 
 (123475,'Amit','Agrawal','Metro Station','CA','San Jose',95112,'2010-10-10','EMPLOYEE','username','password'),
 (123476,'Amit','Agrawal','Metro Station','CA','San Jose',95112,'2010-10-10','EMPLOYEE','username','password'),
 (123477,'Amit','Agrawal','Metro Station','CA','San Jose',95112,'2010-10-10','EMPLOYEE','username','password'),
 (123478,'Amit','Agrawal','Metro Station','CA','San Jose',95112,'2010-10-10','EMPLOYEE','username','password');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;


--
-- Definition of table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation` (
  `reservationId` int(11) NOT NULL AUTO_INCREMENT,
  `reservationNo` varchar(60) NOT NULL,
  `customerId` int(7) NOT NULL,
  `reservationStatus` enum('ACTIVE','WAITING','CANCELED') NOT NULL,
  `seatsBooked` int(11) DEFAULT NULL,
  PRIMARY KEY (`reservationId`),
  KEY `fk_reservation_customer_id_idx` (`customerId`),
  CONSTRAINT `fk_reservation_customer_id` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservation`
--

/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` (`reservationId`,`reservationNo`,`customerId`,`reservationStatus`,`seatsBooked`) VALUES 
 (3,'123456789',1061830924,'ACTIVE',3);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;


--
-- Definition of table `traveller`
--

DROP TABLE IF EXISTS `traveller`;
CREATE TABLE `traveller` (
  `travellerId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `sex` char(1) NOT NULL,
  `reservationId` int(11) NOT NULL,
  PRIMARY KEY (`travellerId`),
  KEY `fk_traveller_reservation_id_idx` (`reservationId`),
  CONSTRAINT `fk_traveller_reservation_id` FOREIGN KEY (`reservationId`) REFERENCES `reservation` (`reservationId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `traveller`
--

/*!40000 ALTER TABLE `traveller` DISABLE KEYS */;
INSERT INTO `traveller` (`travellerId`,`firstName`,`lastName`,`age`,`sex`,`reservationId`) VALUES 
 (5,'Amit','Agrawal',26,'M',3),
 (6,'Amit','Agrawal',26,'M',3);
/*!40000 ALTER TABLE `traveller` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
