DROP DATABASE IF EXISTS `mygenerator`;

CREATE DATABASE `mygenerator` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `mygenerator`;

DROP TABLE IF EXISTS `people`;

SET NAMES 'utf8mb4';

CREATE TABLE `people` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lfp` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `age` int(11) NOT NULL,
  `gender` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `dob` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `inn` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `zip` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `country` varchar(45) COLLATE utf8mb4_general_ci DEFAULT 'N/A',
  `region` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `city` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `street` varchar(45) COLLATE utf8mb4_general_ci NOT NULL,
  `house` int(11) NOT NULL,
  `flat` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
