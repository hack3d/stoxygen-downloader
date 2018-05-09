-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: stockanalyses_prod
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `bond`
--

LOCK TABLES `bond` WRITE;
/*!40000 ALTER TABLE `bonds` DISABLE KEYS */;
INSERT INTO `bond` (`bonds_id`, `name`, `isin`, `state`, `insert_timestamp`, `insert_user`, `modify_timestamp`, `modify_user`, `crypto_pair`, `crypto_base`, `crypto_quote`) VALUES
(1,'Bitcoin/Euro','XFC000000001',0,'2017-12-09 18:55:05','admin','2018-03-30 20:00:17','admin','btceur','btc','eur'),
(2,'Bitcoin/US-Dollar','XFC000000002',0,'2017-12-10 15:23:07','admin','2018-03-30 20:00:17','admin','btcusd','btc','usd'),
(3,'Litecoin/US-Dollar','XFC000000003',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','ltcusd','ltc','usd'),
(4,'Litecoin/Bitcoin','XFC000000004',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','ltcbtc','ltc','btc'),
(5,'Ethereum/US-Dollar','XFC000000005',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','ethusd','eth','usd'),
(6,'Ethereum/Bitcoin','XFC000000006',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','ethbtc','eth','btc'),
(7,'Ethereum Classic/Bitcoin','XFC000000007',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','etcbtc','etc','btc'),
(8,'Ethereum Classic/US-Dollar','XFC000000008',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','etcusd','etc','usd'),
(9,'Recovery Right Token/US-Dollar','XFC000000009',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','rrtusd','rrt','usd'),
(10,'Recovery Right Token/Bitcoin','XFC000000010',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','rrtbtc','rrt','btc'),
(11,'Zcash/US-Dollar','XFC000000011',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','zecusd','zec ','usd'),
(12,'Zcash/Bitcoin','XFC000000012',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','zecbtc','zec','btc'),
(13,'Monero/US-Dollar','XFC000000013',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','xmrusd','xmr','usd'),
(14,'Moneroe/Bitcoin','XFC000000014',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','xmrbtc','xmr','btc'),
(15,'Dashcoin/US-Dollar','XFC000000015',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','dshusd','dsh','usd'),
(16,'Dashcoin/Bitcoin','XFC000000016',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','dshbtc','dsh','btc'),
(17,'Ripple/US-Dollar','XFC000000017',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','xrpusd','xrp','usd'),
(18,'Ripple/Bitcoin','XFC000000018',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','xrpbtc','xrp','btc'),
(19,'IOTA/US-Dollar','XFC000000019',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','iotusd','iot','usd'),
(20,'IOTA/Bitcoin','XFC000000020',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','iotbtc','iot','btc'),
(21,'IOTA,/Ethereum','XFC000000021',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','ioteth','iot','eth'),
(22,'EOS/US-Dollar','XFC000000022',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','eosusd','eos','usd'),
(23,'EOS/Bitcoin','XFC000000023',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','eosbtc','eos','btc'),
(24,'EOS/Ethereum','XFC000000024',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','eoseth','eos','eth'),
(25,'Santiment/US-Dollar','XFC000000025',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','sanusd','san','usd'),
(26,'Santiment/Bitoin','XFC000000026',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','sanbtc','san','btc'),
(27,'Santiment/Ethereum','XFC000000027',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','saneth','san','eth'),
(28,'OmiseGO/US-Dollar','XFC000000028',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','omgusd','omg','usd'),
(29,'OmiseGO/Bitcoin','XFC000000029',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','omgbtc','omg','btc'),
(30,'OmiseGO/Ethereum','XFC000000030',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','omgeth','omg','eth'),
(31,'Bitcoin Cash/US-Dollar','XFC000000031',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','bchusd','bch','usd'),
(32,'Bitcoin Cash/Bitcoin','XFC000000032',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','bchbtc','bch','btc'),
(33,'Bitcoin Cash/Ethereum','XFC000000033',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','bcheth','bch','eth'),
(34,'NEO/US-DOllar','XFC000000034',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','neousd','neo','usd'),
(35,'NEO/Bitcoin','XFC000000035',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','neobtc','neo','btc'),
(36,'NEO/Ethereum','XFC000000036',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','neoeth','neo','eth'),
(37,'Metaverse ETP/US-Dollar','XFC000000037',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','etpusd','etp','usd'),
(38,'Metaverse ETP/Bitcoin','XFC000000038',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','etpbtc','etp','btc'),
(39,'Metaverse ETP/Ethereum','XFC000000039',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','etpeth','etp','eth'),
(40,'Qtum/US-Dollar','XFC000000040',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','qtmusd','qtm','usd'),
(41,'Qtum/Bitcoin','XFC000000041',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','qtmbtc','qtm','btc'),
(42,'Qtum/Ethereum','XFC000000042',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','qtmeth','qtm','eth'),
(43,'AventCoin/US-Dollar','XFC000000043',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','avtusd','avt','usd'),
(44,'AventCoin/Bitcoin','XFC000000044',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','avtbtc','avt','btc'),
(45,'AventCoin/Ethereum','XFC000000045',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','avteth','avt','eth'),
(46,'Eidoo/US-Dollar','XFC000000046',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','edousd','edo','usd'),
(47,'Eidoo/Bitcoin','XFC000000047',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','edobtc','edo','btc'),
(48,'Eidoo/Ethereum','XFC000000048',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','edoeth','edo','eth'),
(49,'Bitcoin Gold/US-Dollar','XFC000000049',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','btgusd','btg','usd'),
(50,'Bitcoin Gold/Bitcoin','XFC000000050',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','btgbtc','btg','btc'),
(51,'Datum/US-DOllar','XFC000000051',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','datusd','dat','usd'),
(52,'Datum/Bitcoin','XFC000000052',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','datbtc','dat','btc'),
(53,'Datum/Ethereum','XFC000000053',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','dateth','dat','eth'),
(54,'Qash/US-Dollar','XFC000000054',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','qshusd','qsh','usd'),
(55,'Qash/Bitcoin','XFC000000055',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','qshbtc','qsh','btc'),
(56,'Qash/Ethereum','XFC000000056',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','qsheth','qsh','eth'),
(57,'Yoyow/US-Dollar','XFC000000057',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','yywusd','yyw','usd'),
(58,'Yoyow/Bitcoin','XFC000000058',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','yywbtc','yyw','btc'),
(59,'Yoyow/Ethereum','XFC000000059',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','yyweth','yyw','eth'),
(60,'Golem/US-Dollar','XFC000000060',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','gntusd','gnt','usd'),
(61,'Golem/Bitocin','XFC000000061',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','gntbtc','gnt','btc'),
(62,'Golem/Ethereum','XFC000000062',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','gnteth','gnt','eth'),
(63,'Status Network Token/US-Dollar','XFC000000063',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','sntusd','snt','usd'),
(64,'Status Network Token/Bitcoin','XFC000000064',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','sntbtc','snt','btc'),
(65,'Status Network Token/Ethereum','XFC000000065',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','snteth','snt','eth'),
(66,'IOTA/Euro','XFC000000066',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','ioteur','iot','eur'),
(67,'Basic Attention Token/US-Dollar','XFC000000067',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','batusd','bat','usd'),
(68,'Basic Attention Token/Bitcoin','XFC000000068',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','batbtc','bat','btc'),
(69,'Basic Attention Token/Ethereum','XFC000000069',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','bateth','bat','eth'),
(70,'Decentraland/US-Dollar','XFC000000070',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','mnausd','mna','usd'),
(71,'Decentraland/Bitcoin','XFC000000071',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','mnabtc','mna','btc'),
(72,'Decentraland/Ethereum','XFC000000072',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','mnaeth','mna','eth'),
(73,'FunFair/US-Dollar','XFC000000073',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','funusd','fun','usd'),
(74,'FunFair/Bitcoin','XFC000000074',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','funbtc','fun','btc'),
(75,'FunFair/Ethereum','XFC000000075',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','funeth','fun','eth'),
(76,'0x/US-Dollar','XFC000000076',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','zrxusd','zrx','usd'),
(77,'0x/Bitcoin','XFC000000077',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','zrxbtc','zrx','btc'),
(78,'0x/Ethereum','XFC000000078',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','zrxeth','zrx','eth'),
(79,'Time New Bank/US-Dollar','XFC000000079',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','tnbusd','tnb','usd'),
(80,'Time New Bank/Bitcoin','XFC000000080',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','tnbbtc','tnb','btc'),
(81,'Time New Bank/Ethereum','XFC000000081',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','tnbeth','tnb','eth'),
(82,'SpankChain/US-Dollar','XFC000000082',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','spkusd','spk','usd'),
(83,'SpankChain/Bitcoin','XFC000000083',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','spkbtc','spk','btc'),
(84,'SpankChain/Ethereum','XFC000000084',0,'2018-02-16 17:14:56','admin','2018-03-30 20:00:17','admin','spketh','spk','eth');
/*!40000 ALTER TABLE `bonds` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-19  7:40:14
