-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u2
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Lun 08 Mai 2017 à 10:54
-- Version du serveur :  5.5.53-0+deb8u1
-- Version de PHP :  5.6.29-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `wakemeup`
--

-- --------------------------------------------------------

--
-- Structure de la table `alarm`
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
-- Contenu de la table `alarm`
--

INSERT INTO `alarm` (`id`, `idUser`, `enabled`, `idVoter`, `ytlink`, `msg`, `chosen`, `date`) VALUES
(00000000000000000003, 00000000000000000004, 'false', 00000000000000000005, 'AFP5tYKjdTc', 'Fromage de biche.', 'true', '2016-09-29 12:29:30');

-- --------------------------------------------------------

--
-- Structure de la table `friends`
--

CREATE TABLE IF NOT EXISTS `friends` (
`id` bigint(20) unsigned zerofill NOT NULL,
  `idUser` bigint(20) unsigned zerofill NOT NULL,
  `idAmi` bigint(20) unsigned zerofill NOT NULL,
  `pending` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  `hasAccepted` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT 'false'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Contenu de la table `friends`
--

INSERT INTO `friends` (`id`, `idUser`, `idAmi`, `pending`, `hasAccepted`) VALUES
(00000000000000000001, 00000000000000000004, 00000000000000000005, 'false', 'true'),
(00000000000000000002, 00000000000000000005, 00000000000000000004, 'true', 'true');

-- --------------------------------------------------------

--
-- Structure de la table `members`
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
-- Contenu de la table `members`
--

INSERT INTO `members` (`id`, `username`, `password`, `cookie`, `mode`, `pseudonyme`, `date_creation`, `image`) VALUES
(00000000000000000004, 'Theo', '$2y$10$85EEE87bRJ1HAQMWgB7.ROx6N.4mq/s2X8NgrqKNDkUgejOE7pBWW', 'ecb1d015c9781e497b45297266808bd12f0407a1bdaad4cf7fa802bb1247e907', 'world', 'Theo', '2016-09-22 13:32:03', NULL),
(00000000000000000005, 'valou', '$2y$10$4tZ.Q2lqbYIzu2YirAnth.iS4b3qJedAEUfhH/GkpmZ7mpBwWWDka', 'f61d69c1c79b3a8851ace6d2e58dfcbb564846d60a054b9a396b1ec0eace9150', 'private', 'valou', '2016-09-22 13:36:31', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

CREATE TABLE IF NOT EXISTS `message` (
`id` bigint(20) unsigned zerofill NOT NULL,
  `idSender` bigint(20) unsigned zerofill NOT NULL,
  `idReceiver` bigint(20) unsigned zerofill NOT NULL,
  `msg` varchar(140) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Contenu de la table `message`
--

INSERT INTO `message` (`id`, `idSender`, `idReceiver`, `msg`) VALUES
(00000000000000000001, 00000000000000000004, 00000000000000000005, 'Celui qui lit ce message est un con.');

-- --------------------------------------------------------

--
-- Structure de la table `messenger`
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
-- Index pour les tables exportées
--

--
-- Index pour la table `alarm`
--
ALTER TABLE `alarm`
 ADD PRIMARY KEY (`id`), ADD KEY `idUser` (`idUser`), ADD KEY `idVoter` (`idVoter`);

--
-- Index pour la table `friends`
--
ALTER TABLE `friends`
 ADD PRIMARY KEY (`id`), ADD KEY `idUser` (`idUser`), ADD KEY `idAmi` (`idAmi`);

--
-- Index pour la table `members`
--
ALTER TABLE `members`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `username` (`username`), ADD UNIQUE KEY `pseudonyme` (`pseudonyme`);

--
-- Index pour la table `message`
--
ALTER TABLE `message`
 ADD PRIMARY KEY (`id`), ADD KEY `idSender` (`idSender`), ADD KEY `idReceiver` (`idReceiver`);

--
-- Index pour la table `messenger`
--
ALTER TABLE `messenger`
 ADD PRIMARY KEY (`id`), ADD KEY `idUser` (`idUser`,`idOther`), ADD KEY `idOther` (`idOther`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `alarm`
--
ALTER TABLE `alarm`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT pour la table `friends`
--
ALTER TABLE `friends`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT pour la table `members`
--
ALTER TABLE `members`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `message`
--
ALTER TABLE `message`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT pour la table `messenger`
--
ALTER TABLE `messenger`
MODIFY `id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `alarm`
--
ALTER TABLE `alarm`
ADD CONSTRAINT `alarm_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
ADD CONSTRAINT `alarm_ibfk_2` FOREIGN KEY (`idVoter`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `friends`
--
ALTER TABLE `friends`
ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE,
ADD CONSTRAINT `friends_ibfk_3` FOREIGN KEY (`idAmi`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`idSender`) REFERENCES `members` (`id`) ON DELETE CASCADE,
ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`idReceiver`) REFERENCES `members` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `messenger`
--
ALTER TABLE `messenger`
ADD CONSTRAINT `messenger_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `messenger_ibfk_2` FOREIGN KEY (`idOther`) REFERENCES `members` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
