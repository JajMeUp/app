-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 01, 2016 at 05:32 PM
-- Server version: 5.5.52-0+deb8u1
-- PHP Version: 5.6.27-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `wakemeup`
--

-- --------------------------------------------------------

--
-- Table structure for table `alarm`
--

CREATE TABLE IF NOT EXISTS `alarm` (
`id` bigint(20) unsigned zerofill NOT NULL,
  `idUser` bigint(20) unsigned zerofill NOT NULL,
  `enabled` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `idVoter` bigint(20) unsigned zerofill NOT NULL,
  `ytlink` varchar(20) CHARACTER SET utf8 NOT NULL,
  `msg` varchar(140) COLLATE latin1_general_ci NOT NULL,
  `chosen` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `alarm`
--

INSERT INTO `alarm` (`id`, `idUser`, `enabled`, `idVoter`, `ytlink`, `msg`, `chosen`, `date`) VALUES
(00000000000000000003, 00000000000000000004, 'false', 00000000000000000005, 'AFP5tYKjdTc', 'Fromage de biche.', 'true', '2016-09-29 12:29:30');

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE IF NOT EXISTS `friends` (
`id` bigint(20) unsigned zerofill NOT NULL,
  `idUser` bigint(20) unsigned zerofill NOT NULL,
  `idAmi` bigint(20) unsigned zerofill NOT NULL,
  `pending` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `hasAccepted` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`id`, `idUser`, `idAmi`, `pending`, `hasAccepted`) VALUES
(00000000000000000001, 00000000000000000004, 00000000000000000005, 'false', 'true'),
(00000000000000000002, 00000000000000000005, 00000000000000000004, 'true', 'true');

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE IF NOT EXISTS `members` (
`id` bigint(20) unsigned zerofill NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `cookie` varchar(255) CHARACTER SET utf8 NOT NULL,
  `mode` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'world',
  `pseudonyme` varchar(16) CHARACTER SET utf8 NOT NULL,
  `date_creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image` varchar(50) COLLATE latin1_general_ci DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`id`, `username`, `password`, `cookie`, `mode`, `pseudonyme`, `date_creation`, `image`) VALUES
(00000000000000000004, 'Theo', '$2y$10$85EEE87bRJ1HAQMWgB7.ROx6N.4mq/s2X8NgrqKNDkUgejOE7pBWW', 'ecb1d015c9781e497b45297266808bd12f0407a1bdaad4cf7fa802bb1247e907', 'world', 'Theo', '2016-09-22 13:32:03', NULL),
(00000000000000000005, 'valou', '$2y$10$4tZ.Q2lqbYIzu2YirAnth.iS4b3qJedAEUfhH/GkpmZ7mpBwWWDka', '01502ee8db8a3109674eb5f8e8dade127385a48beb661d8bc74ea2475f151c2d', 'private', 'valou', '2016-09-22 13:36:31', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
`id` bigint(20) unsigned zerofill NOT NULL,
  `idSender` bigint(20) unsigned zerofill NOT NULL,
  `idReceiver` bigint(20) unsigned zerofill NOT NULL,
  `msg` varchar(140) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `idSender`, `idReceiver`, `msg`) VALUES
(00000000000000000001, 00000000000000000004, 00000000000000000005, 'Celui qui lit ce message est un con.');

-- --------------------------------------------------------

--
-- Table structure for table `messenger`
--

CREATE TABLE IF NOT EXISTS `messenger` (
`id` bigint(20) unsigned zerofill NOT NULL,
  `idUser` bigint(20) unsigned zerofill NOT NULL,
  `idOther` bigint(20) unsigned zerofill NOT NULL,
  `message` varchar(140) NOT NULL,
  `read` varchar(10) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alarm`
--
ALTER TABLE `alarm`
 ADD PRIMARY KEY (`id`), ADD KEY `idUser` (`idUser`), ADD KEY `idVoter` (`idVoter`);

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
 ADD PRIMARY KEY (`id`), ADD KEY `idUser` (`idUser`), ADD KEY `idAmi` (`idAmi`);

--
-- Indexes for table `members`
--
ALTER TABLE `members`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `username` (`username`), ADD UNIQUE KEY `pseudonyme` (`pseudonyme`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
 ADD PRIMARY KEY (`id`), ADD KEY `idSender` (`idSender`), ADD KEY `idReceiver` (`idReceiver`);

--
-- Indexes for table `messenger`
--
ALTER TABLE `messenger`
 ADD PRIMARY KEY (`id`), ADD KEY `idUser` (`idUser`,`idOther`), ADD KEY `idOther` (`idOther`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alarm`
--
ALTER TABLE `alarm`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `friends`
--
ALTER TABLE `friends`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `members`
--
ALTER TABLE `members`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `messenger`
--
ALTER TABLE `messenger`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `alarm`
--
ALTER TABLE `alarm`
ADD CONSTRAINT `alarm_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
ADD CONSTRAINT `alarm_ibfk_2` FOREIGN KEY (`idVoter`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
ADD CONSTRAINT `friends_ibfk_3` FOREIGN KEY (`idAmi`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `message`
--
ALTER TABLE `message`
ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`idSender`) REFERENCES `members` (`id`) ON DELETE CASCADE,
ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`idReceiver`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `messenger`
--
ALTER TABLE `messenger`
ADD CONSTRAINT `messenger_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `messenger_ibfk_2` FOREIGN KEY (`idOther`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
