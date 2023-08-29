-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.8-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema sistema_academico
--

CREATE DATABASE IF NOT EXISTS sistema_academico;
USE sistema_academico;

--
-- Definition of table `a_escolar`
--

DROP TABLE IF EXISTS `a_escolar`;
CREATE TABLE `a_escolar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `a_escolar`
--

/*!40000 ALTER TABLE `a_escolar` DISABLE KEYS */;
INSERT INTO `a_escolar` (`id`,`denominacion`) VALUES 
 (1,'1'),
 (2,NULL);
/*!40000 ALTER TABLE `a_escolar` ENABLE KEYS */;


--
-- Definition of table `accesos`
--

DROP TABLE IF EXISTS `accesos`;
CREATE TABLE `accesos` (
  `id_rol` int(11) NOT NULL DEFAULT '0',
  `id_modulo` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_rol`,`id_modulo`),
  KEY `id_modulo` (`id_modulo`),
  CONSTRAINT `accesos_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `accesos_ibfk_2` FOREIGN KEY (`id_modulo`) REFERENCES `modulos` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accesos`
--

/*!40000 ALTER TABLE `accesos` DISABLE KEYS */;
INSERT INTO `accesos` (`id_rol`,`id_modulo`) VALUES 
 (1,1),
 (1,2),
 (1,3),
 (1,4),
 (1,5),
 (1,6),
 (1,7);
/*!40000 ALTER TABLE `accesos` ENABLE KEYS */;


--
-- Definition of table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
CREATE TABLE `alumnos` (
  `cedula_escolar` bigint(20) NOT NULL DEFAULT '0',
  `nombres` varchar(50) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `sexo` varchar(10) DEFAULT NULL,
  `lugar_nac` varchar(50) DEFAULT NULL,
  `fec_nac` date DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `cedula_representante` int(11) DEFAULT NULL,
  PRIMARY KEY (`cedula_escolar`),
  KEY `cedula_representante` (`cedula_representante`),
  CONSTRAINT `alumnos_ibfk_1` FOREIGN KEY (`cedula_representante`) REFERENCES `representantes` (`cedula`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `alumnos`
--

/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;


--
-- Definition of table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
CREATE TABLE `auditoria` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `modulo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`),
  KEY `modulo` (`modulo`),
  CONSTRAINT `auditoria_ibfk_2` FOREIGN KEY (`modulo`) REFERENCES `modulos` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `auditoria_ibfk_1` FOREIGN KEY (`user`) REFERENCES `usuarios` (`cedula`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `auditoria`
--

/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;


--
-- Definition of table `grados`
--

DROP TABLE IF EXISTS `grados`;
CREATE TABLE `grados` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grados`
--

/*!40000 ALTER TABLE `grados` DISABLE KEYS */;
/*!40000 ALTER TABLE `grados` ENABLE KEYS */;


--
-- Definition of table `inscripciones`
--

DROP TABLE IF EXISTS `inscripciones`;
CREATE TABLE `inscripciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cedula_escolar` bigint(11) DEFAULT NULL,
  `a_escolar` int(11) DEFAULT NULL,
  `grado` int(11) DEFAULT NULL,
  `seccion` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cedula_escolar` (`cedula_escolar`),
  KEY `a_escolar` (`a_escolar`),
  KEY `grado` (`grado`),
  CONSTRAINT `inscripciones_ibfk_3` FOREIGN KEY (`grado`) REFERENCES `grados` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `inscripciones_ibfk_1` FOREIGN KEY (`cedula_escolar`) REFERENCES `alumnos` (`cedula_escolar`) ON UPDATE CASCADE,
  CONSTRAINT `inscripciones_ibfk_2` FOREIGN KEY (`a_escolar`) REFERENCES `a_escolar` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inscripciones`
--

/*!40000 ALTER TABLE `inscripciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `inscripciones` ENABLE KEYS */;


--
-- Definition of table `materias`
--

DROP TABLE IF EXISTS `materias`;
CREATE TABLE `materias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(100) DEFAULT NULL,
  `id_grado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `materias`
--

/*!40000 ALTER TABLE `materias` DISABLE KEYS */;
/*!40000 ALTER TABLE `materias` ENABLE KEYS */;


--
-- Definition of table `modulos`
--

DROP TABLE IF EXISTS `modulos`;
CREATE TABLE `modulos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modulos` varchar(100) DEFAULT NULL,
  `clave` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `modulos`
--

/*!40000 ALTER TABLE `modulos` DISABLE KEYS */;
INSERT INTO `modulos` (`id`,`modulos`,`clave`) VALUES 
 (1,'Archivos',10000),
 (2,'Planteles',11000),
 (3,'Grados',12000),
 (4,'Mantenimiento',20000),
 (5,'Backup',21000),
 (6,'Accesos',22000),
 (7,'Roles',23000);
/*!40000 ALTER TABLE `modulos` ENABLE KEYS */;


--
-- Definition of table `planteles`
--

DROP TABLE IF EXISTS `planteles`;
CREATE TABLE `planteles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `plantel` varchar(100) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `planteles`
--

/*!40000 ALTER TABLE `planteles` DISABLE KEYS */;
/*!40000 ALTER TABLE `planteles` ENABLE KEYS */;


--
-- Definition of table `representantes`
--

DROP TABLE IF EXISTS `representantes`;
CREATE TABLE `representantes` (
  `cedula` int(11) NOT NULL DEFAULT '0',
  `nombres` varchar(50) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `nacionalidad` varchar(12) DEFAULT NULL,
  `fec_nac` date DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `ocupacion` varchar(50) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `telefono` varchar(12) DEFAULT NULL,
  `id_plantel` int(11) DEFAULT NULL,
  PRIMARY KEY (`cedula`),
  KEY `id_plantel` (`id_plantel`),
  CONSTRAINT `representantes_ibfk_1` FOREIGN KEY (`id_plantel`) REFERENCES `planteles` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `representantes`
--

/*!40000 ALTER TABLE `representantes` DISABLE KEYS */;
/*!40000 ALTER TABLE `representantes` ENABLE KEYS */;


--
-- Definition of table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`,`denominacion`) VALUES 
 (1,'Director(a)');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


--
-- Definition of table `secciones`
--

DROP TABLE IF EXISTS `secciones`;
CREATE TABLE `secciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(10) DEFAULT NULL,
  `id_grado` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `secciones`
--

/*!40000 ALTER TABLE `secciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `secciones` ENABLE KEYS */;


--
-- Definition of table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `cedula` int(11) NOT NULL DEFAULT '0',
  `nombres` varchar(50) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `clave` varchar(50) DEFAULT NULL,
  `estado` varchar(10) DEFAULT NULL,
  `id_rol` int(11) DEFAULT NULL,
  PRIMARY KEY (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuarios`
--

/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` (`cedula`,`nombres`,`apellidos`,`usuario`,`clave`,`estado`,`id_rol`) VALUES 
 (12345678,'Admin','Admin','admin','admin','Activo',1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
