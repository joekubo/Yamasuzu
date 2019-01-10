-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 09, 2019 at 10:06 AM
-- Server version: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `yamasuzudb`
--

-- --------------------------------------------------------

--
-- Table structure for table `categorytable`
--

CREATE TABLE `categorytable` (
  `categoryid` int(11) NOT NULL,
  `category` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categorytable`
--

INSERT INTO `categorytable` (`categoryid`, `category`, `type`, `status`) VALUES
(10, 'LAPTOP', 'INDIVIDUAL', 1);

-- --------------------------------------------------------

--
-- Table structure for table `clientstable`
--

CREATE TABLE `clientstable` (
  `clientid` int(11) NOT NULL,
  `clientname` varchar(255) NOT NULL,
  `clientaddress` varchar(255) NOT NULL,
  `clientcity` varchar(255) NOT NULL,
  `clientphone` varchar(255) NOT NULL,
  `clientemail` varchar(255) NOT NULL,
  `clientpin` varchar(255) NOT NULL,
  `balance` double NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `clientstable`
--

INSERT INTO `clientstable` (`clientid`, `clientname`, `clientaddress`, `clientcity`, `clientphone`, `clientemail`, `clientpin`, `balance`, `status`) VALUES
(1, '-', '-', '-', '-', '-', '-', 0, 1),
(2, 'JOE JOE', 'SDFAD', '34323432', '23423323', 'joe.tola@gmail.com', '34344523', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `companytable`
--

CREATE TABLE `companytable` (
  `companyid` int(11) NOT NULL,
  `companyname` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `phoneno` varchar(255) NOT NULL,
  `fax` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `pin` varchar(255) NOT NULL,
  `dealerin` varchar(255) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `companytable`
--

INSERT INTO `companytable` (`companyid`, `companyname`, `location`, `address`, `city`, `phoneno`, `fax`, `email`, `pin`, `dealerin`, `status`) VALUES
(1, 'Yamasuzu Elite Agencies', 'KFA Mwembe Tayari', '345234-80100', 'MOMBASA', '+0722234344', '-', 'info@yamasuzu.co.ke', 'P0097809099T', 'M-pesa, Mobile Phones, Accessories and Electricals', 1);

-- --------------------------------------------------------

--
-- Table structure for table `invoicetable`
--

CREATE TABLE `invoicetable` (
  `invoiceno` int(11) NOT NULL,
  `stockingid` int(11) NOT NULL,
  `clientid` int(11) NOT NULL,
  `totalamount` double NOT NULL,
  `balancebf` double NOT NULL,
  `paidamount` double NOT NULL,
  `balance` double NOT NULL,
  `invoicedate` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `invoice_info`
--

CREATE TABLE `invoice_info` (
  `id` int(11) NOT NULL,
  `quotation_no` varchar(255) NOT NULL,
  `service_id` varchar(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `price` double NOT NULL,
  `s` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `invoice_table`
--

CREATE TABLE `invoice_table` (
  `quotation_no` int(11) NOT NULL,
  `client_name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `phone_no` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `quotation_date` date NOT NULL,
  `total` double NOT NULL,
  `user_id` int(11) NOT NULL,
  `s` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `logstable`
--

CREATE TABLE `logstable` (
  `id` int(11) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `date` varchar(255) NOT NULL,
  `time` varchar(255) NOT NULL,
  `operation` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `logstable`
--

INSERT INTO `logstable` (`id`, `userid`, `name`, `date`, `time`, `operation`) VALUES
(1439, '7', 'Raphael Kunga', '2018-11-29', '11:33:21', 'Successfully logged in'),
(1440, '7', 'Raphael Kunga', '2018-11-30', '09:25:12', 'Successfully logged in'),
(1441, '7', 'Raphael Kunga', '2018-11-30', '09:31:33', 'Successfully logged in'),
(1442, '7', 'Raphael Kunga', '2018-11-30', '09:42:27', 'Successfully logged in'),
(1443, '7', 'Raphael Kunga', '2018-11-30', '09:43:36', 'Successfully logged in'),
(1444, '7', 'Raphael Kunga', '2018-11-30', '09:45:27', 'Successfully logged in'),
(1445, '7', 'Raphael Kunga', '2018-11-30', '09:45:53', 'Successfully logged in'),
(1446, '7', 'Raphael Kunga', '2018-11-30', '09:47:46', 'Successfully logged in'),
(1447, '7', 'Raphael Kunga', '2018-11-30', '09:48:39', 'Successfully logged in'),
(1448, '7', 'Raphael Kunga', '2018-11-30', '09:49:56', 'Successfully logged in'),
(1449, '7', 'Raphael Kunga', '2018-11-30', '09:51:35', 'Successfully logged in'),
(1450, '7', 'Raphael Kunga', '2018-11-30', '09:52:24', 'Successfully logged in'),
(1451, '7', 'Raphael Kunga', '2018-11-30', '10:02:15', 'Successfully logged in'),
(1452, '7', 'Raphael Kunga', '2018-11-30', '10:03:22', 'Successfully logged in'),
(1453, '7', 'Raphael Kunga', '2018-11-30', '10:05:05', 'Successfully logged in'),
(1454, '7', 'Raphael Kunga', '2018-11-30', '10:05:48', 'Successfully logged in'),
(1455, '7', 'Raphael Kunga', '2018-11-30', '10:06:36', 'Successfully logged in'),
(1456, '7', 'Raphael Kunga', '2018-11-30', '10:07:46', 'Successfully logged in'),
(1457, '7', 'Raphael Kunga', '2018-11-30', '10:08:15', 'Successfully logged in'),
(1458, '7', 'Raphael Kunga', '2018-11-30', '10:13:44', 'Successfully logged in'),
(1459, '7', 'Raphael Kunga', '2018-11-30', '10:14:44', 'Successfully logged in'),
(1460, '7', 'Raphael Kunga', '2018-11-30', '10:15:38', 'Successfully logged in'),
(1461, '7', 'Raphael Kunga', '2018-11-30', '10:16:53', 'Successfully logged in'),
(1462, '7', 'Raphael Kunga', '2018-11-30', '10:17:08', 'Successfully logged in'),
(1463, '7', 'Raphael Kunga', '2018-11-30', '10:34:36', 'Successfully logged in'),
(1464, '7', 'Raphael Kunga', '2018-11-30', '10:36:06', 'Successfully logged in'),
(1465, '7', 'Raphael Kunga', '2018-11-30', '10:36:13', 'Delete details for \'-\' (Client)'),
(1466, '7', 'Raphael Kunga', '2018-11-30', '10:37:01', 'Successfully logged in'),
(1467, '7', 'Raphael Kunga', '2018-11-30', '10:38:46', 'Successfully logged in'),
(1468, '7', 'Raphael Kunga', '2018-11-30', '11:03:53', 'Successfully logged in'),
(1469, '7', 'Raphael Kunga', '2018-11-30', '11:17:20', 'Successfully logged in'),
(1470, '7', 'Raphael Kunga', '2018-11-30', '11:18:39', 'Successfully logged in'),
(1471, '7', 'Raphael Kunga', '2018-11-30', '11:30:52', 'Successfully logged in'),
(1472, '7', 'Raphael Kunga', '2018-11-30', '11:37:32', 'Successfully logged in'),
(1473, '7', 'Raphael Kunga', '2018-11-30', '11:39:04', 'Successfully logged in'),
(1474, '7', 'Raphael Kunga', '2018-11-30', '11:40:49', 'Successfully logged in'),
(1475, '7', 'Raphael Kunga', '2018-11-30', '11:40:50', 'Successfully logged in'),
(1476, '7', 'Raphael Kunga', '2018-11-30', '11:45:49', 'Successfully logged in'),
(1477, '7', 'Raphael Kunga', '2018-11-30', '12:55:55', 'Successfully logged in'),
(1478, '7', 'Raphael Kunga', '2018-11-30', '12:58:21', 'Successfully logged in'),
(1479, '7', 'Raphael Kunga', '2018-11-30', '14:01:44', 'Successfully logged in'),
(1480, '7', 'Raphael Kunga', '2018-11-30', '14:16:26', 'Successfully logged in'),
(1481, '7', 'Raphael Kunga', '2018-11-30', '14:17:37', 'Successfully logged in'),
(1482, '7', 'Raphael Kunga', '2018-11-30', '14:30:43', 'Successfully logged in'),
(1483, '7', 'Raphael Kunga', '2018-11-30', '14:33:12', 'Successfully logged in'),
(1484, '7', 'Raphael Kunga', '2018-11-30', '14:34:10', 'Successfully logged in'),
(1485, '7', 'Raphael Kunga', '2018-11-30', '15:17:46', 'Successfully logged in'),
(1486, '7', 'Raphael Kunga', '2018-11-30', '15:21:35', 'Successfully logged in'),
(1487, '7', 'Raphael Kunga', '2018-11-30', '15:26:49', 'Successfully logged in'),
(1488, '7', 'Raphael Kunga', '2018-11-30', '15:28:31', 'Successfully logged in'),
(1489, '7', 'Raphael Kunga', '2018-11-30', '15:30:05', 'Successfully logged in'),
(1490, '7', 'Raphael Kunga', '2018-11-30', '15:31:54', 'Successfully logged in'),
(1491, '7', 'Raphael Kunga', '2018-11-30', '15:41:00', 'Successfully logged in'),
(1492, '7', 'Raphael Kunga', '2018-11-30', '15:44:27', 'Successfully logged in'),
(1493, '7', 'Raphael Kunga', '2018-11-30', '15:49:53', 'Successfully logged in'),
(1494, '7', 'Raphael Kunga', '2018-11-30', '15:51:43', 'Successfully logged in'),
(1495, '7', 'Raphael Kunga', '2018-11-30', '15:52:56', 'Successfully logged in'),
(1496, '7', 'Raphael Kunga', '2018-11-30', '15:53:46', 'Successfully logged in'),
(1497, '7', 'Raphael Kunga', '2018-11-30', '16:09:59', 'Successfully logged in'),
(1498, '7', 'Raphael Kunga', '2018-11-30', '16:12:30', 'Successfully logged in'),
(1499, '7', 'Raphael Kunga', '2018-11-30', '16:56:46', 'Successfully logged in'),
(1500, '7', 'Raphael Kunga', '2018-11-30', '16:58:52', 'Successfully logged in'),
(1501, '7', 'Raphael Kunga', '2018-11-30', '16:59:50', 'Successfully logged in'),
(1502, '7', 'Raphael Kunga', '2018-11-30', '17:01:51', 'Successfully logged in'),
(1503, '7', 'Raphael Kunga', '2018-11-30', '17:04:35', 'Successfully logged in'),
(1504, '7', 'Raphael Kunga', '2018-11-30', '22:22:38', 'Successfully logged in'),
(1505, '7', 'Raphael Kunga', '2018-11-30', '22:23:41', 'Successfully logged in'),
(1506, '7', 'Raphael Kunga', '2018-11-30', '22:25:20', 'Successfully logged in'),
(1507, '7', 'Raphael Kunga', '2018-11-30', '22:28:18', 'Successfully logged in'),
(1508, '7', 'Raphael Kunga', '2018-11-30', '22:29:34', 'Successfully logged in'),
(1509, '7', 'Raphael Kunga', '2018-11-30', '22:33:24', 'Successfully logged in'),
(1510, '7', 'Raphael Kunga', '2018-11-30', '22:35:06', 'Successfully logged in'),
(1511, '7', 'Raphael Kunga', '2018-11-30', '22:38:06', 'Successfully logged in'),
(1512, '7', 'Raphael Kunga', '2018-11-30', '22:48:08', 'Successfully logged in'),
(1513, '7', 'Raphael Kunga', '2018-11-30', '22:50:27', 'Successfully logged in'),
(1514, '7', 'Raphael Kunga', '2018-11-30', '22:50:36', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1515, '7', 'Raphael Kunga', '2018-11-30', '22:53:01', 'Successfully logged in'),
(1516, '7', 'Raphael Kunga', '2018-11-30', '22:58:11', 'Successfully logged in'),
(1517, '7', 'Raphael Kunga', '2018-11-30', '22:59:52', 'Successfully logged in'),
(1518, '7', 'Raphael Kunga', '2018-11-30', '23:10:25', 'Successfully logged in'),
(1519, '7', 'Raphael Kunga', '2018-11-30', '23:15:37', 'Successfully logged in'),
(1520, '7', 'Raphael Kunga', '2018-11-30', '23:17:03', 'Successfully logged in'),
(1521, '7', 'Raphael Kunga', '2018-11-30', '23:18:44', 'Successfully logged in'),
(1522, '7', 'Raphael Kunga', '2018-11-30', '23:19:46', 'Successfully logged in'),
(1523, '7', 'Raphael Kunga', '2018-11-30', '23:21:06', 'Successfully logged in'),
(1524, '7', 'Raphael Kunga', '2018-11-30', '23:21:19', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1525, '7', 'Raphael Kunga', '2018-11-30', '23:21:23', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1526, '7', 'Raphael Kunga', '2018-11-30', '23:22:07', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1527, '7', 'Raphael Kunga', '2018-11-30', '23:22:12', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1528, '7', 'Raphael Kunga', '2018-11-30', '23:22:34', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1529, '7', 'Raphael Kunga', '2018-11-30', '23:22:44', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1530, '7', 'Raphael Kunga', '2018-11-30', '23:22:49', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1531, '7', 'Raphael Kunga', '2018-11-30', '23:23:02', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1532, '7', 'Raphael Kunga', '2018-11-30', '23:23:06', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1533, '7', 'Raphael Kunga', '2018-11-30', '23:28:19', 'Successfully logged in'),
(1534, '7', 'Raphael Kunga', '2018-11-30', '23:34:44', 'Successfully logged in'),
(1535, '7', 'Raphael Kunga', '2018-11-30', '23:37:10', 'Successfully logged in'),
(1536, '7', 'Raphael Kunga', '2018-11-30', '23:41:11', 'Successfully logged in'),
(1537, '7', 'Raphael Kunga', '2018-11-30', '23:44:45', 'Successfully logged in'),
(1538, '7', 'Raphael Kunga', '2018-11-30', '23:50:53', 'Successfully logged in'),
(1539, '7', 'Raphael Kunga', '2018-12-01', '00:03:03', 'Successfully logged in'),
(1540, '7', 'Raphael Kunga', '2018-12-01', '00:06:55', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1541, '7', 'Raphael Kunga', '2018-12-01', '00:06:59', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1542, '7', 'Raphael Kunga', '2018-12-01', '00:07:55', 'Successfully logged in'),
(1543, '7', 'Raphael Kunga', '2018-12-01', '00:16:16', 'Successfully logged in'),
(1544, '7', 'Raphael Kunga', '2018-12-01', '00:17:05', 'Successfully logged in'),
(1545, '7', 'Raphael Kunga', '2018-12-01', '00:19:33', 'Successfully logged in'),
(1546, '7', 'Raphael Kunga', '2018-12-01', '00:21:41', 'Successfully logged in'),
(1547, '7', 'Raphael Kunga', '2018-12-01', '00:23:04', 'Successfully logged in'),
(1548, '7', 'Raphael Kunga', '2018-12-01', '00:23:31', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1549, '7', 'Raphael Kunga', '2018-12-01', '00:23:35', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1550, '7', 'Raphael Kunga', '2018-12-01', '00:23:39', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1551, '7', 'Raphael Kunga', '2018-12-01', '00:41:05', 'Successfully logged in'),
(1552, '7', 'Raphael Kunga', '2018-12-01', '00:42:44', 'Successfully logged in'),
(1553, '7', 'Raphael Kunga', '2018-12-01', '00:43:32', 'Successfully logged in'),
(1554, '7', 'Raphael Kunga', '2018-12-01', '00:47:24', 'Successfully logged in'),
(1555, '7', 'Raphael Kunga', '2018-12-01', '00:49:16', 'Successfully logged in'),
(1556, '7', 'Raphael Kunga', '2018-12-01', '00:49:58', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1557, '7', 'Raphael Kunga', '2018-12-01', '00:50:02', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1558, '7', 'Raphael Kunga', '2018-12-01', '00:50:06', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1559, '7', 'Raphael Kunga', '2018-12-01', '00:50:10', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1560, '7', 'Raphael Kunga', '2018-12-01', '00:50:13', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1561, '7', 'Raphael Kunga', '2018-12-01', '00:52:14', 'Successfully logged in'),
(1562, '7', 'Raphael Kunga', '2018-12-01', '00:57:28', 'Successfully logged in'),
(1563, '7', 'Raphael Kunga', '2018-12-01', '01:03:59', 'Successfully logged in'),
(1564, '7', 'Raphael Kunga', '2018-12-01', '01:04:22', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1565, '7', 'Raphael Kunga', '2018-12-01', '01:04:37', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1566, '7', 'Raphael Kunga', '2018-12-01', '01:05:28', 'Successfully logged in'),
(1567, '7', 'Raphael Kunga', '2018-12-01', '01:11:51', 'Successfully logged in'),
(1568, '7', 'Raphael Kunga', '2018-12-01', '01:38:13', 'Successfully logged in'),
(1569, '7', 'Raphael Kunga', '2018-12-01', '05:48:48', 'Successfully logged in'),
(1570, '7', 'Raphael Kunga', '2018-12-01', '05:55:24', 'Successfully logged in'),
(1571, '7', 'Raphael Kunga', '2018-12-01', '05:57:16', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1572, '7', 'Raphael Kunga', '2018-12-01', '05:57:29', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1573, '7', 'Raphael Kunga', '2018-12-01', '05:57:34', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1574, '7', 'Raphael Kunga', '2018-12-01', '05:57:39', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1575, '7', 'Raphael Kunga', '2018-12-01', '05:58:37', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1576, '7', 'Raphael Kunga', '2018-12-01', '06:26:50', 'Successfully logged in'),
(1577, '7', 'Raphael Kunga', '2018-12-01', '06:30:41', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1578, '7', 'Raphael Kunga', '2018-12-01', '06:34:27', 'Successfully logged in'),
(1579, '7', 'Raphael Kunga', '2018-12-01', '06:35:48', 'Successfully logged in'),
(1580, '7', 'Raphael Kunga', '2018-12-01', '07:06:53', 'Successfully logged in'),
(1581, '7', 'Raphael Kunga', '2018-12-01', '07:11:36', 'Successfully logged in'),
(1582, '7', 'Raphael Kunga', '2018-12-01', '07:34:39', 'Successfully logged in'),
(1583, '7', 'Raphael Kunga', '2018-12-01', '07:44:32', 'Successfully logged in'),
(1584, '7', 'Raphael Kunga', '2018-12-01', '07:53:36', 'Successfully logged in'),
(1585, '7', 'Raphael Kunga', '2018-12-01', '07:56:18', 'Successfully logged in'),
(1586, '7', 'Raphael Kunga', '2018-12-01', '08:03:29', 'Successfully logged in'),
(1587, '7', 'Raphael Kunga', '2018-12-01', '08:05:47', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1588, '7', 'Raphael Kunga', '2018-12-01', '08:07:11', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1589, '7', 'Raphael Kunga', '2018-12-01', '08:07:55', 'Successfully logged in'),
(1590, '7', 'Raphael Kunga', '2018-12-01', '08:13:30', 'Successfully logged in'),
(1591, '7', 'Raphael Kunga', '2018-12-01', '08:17:23', 'Successfully logged in'),
(1592, '7', 'Raphael Kunga', '2018-12-01', '08:20:13', 'Successfully logged in'),
(1593, '7', 'Raphael Kunga', '2018-12-01', '08:23:33', 'Successfully logged in'),
(1594, '7', 'Raphael Kunga', '2018-12-01', '08:28:53', 'Successfully logged in'),
(1595, '7', 'Raphael Kunga', '2018-12-01', '08:34:25', 'Successfully logged in'),
(1596, '7', 'Raphael Kunga', '2018-12-01', '08:41:19', 'Successfully logged in'),
(1597, '7', 'Raphael Kunga', '2018-12-01', '08:43:31', 'Successfully logged in'),
(1598, '7', 'Raphael Kunga', '2018-12-01', '08:46:39', 'Successfully logged in'),
(1599, '7', 'Raphael Kunga', '2018-12-01', '08:47:11', 'Successfully logged in'),
(1600, '7', 'Raphael Kunga', '2018-12-01', '08:49:29', 'Successfully logged in'),
(1601, '7', 'Raphael Kunga', '2018-12-01', '08:50:33', 'Successfully logged in'),
(1602, '7', 'Raphael Kunga', '2018-12-01', '08:52:15', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1603, '7', 'Raphael Kunga', '2018-12-01', '08:52:19', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1604, '7', 'Raphael Kunga', '2018-12-01', '08:57:51', 'Successfully logged in'),
(1605, '7', 'Raphael Kunga', '2018-12-01', '09:08:17', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1606, '7', 'Raphael Kunga', '2018-12-01', '09:09:08', 'Successfully logged in'),
(1607, '7', 'Raphael Kunga', '2018-12-01', '09:09:49', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1608, '7', 'Raphael Kunga', '2018-12-01', '09:10:16', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1609, '7', 'Raphael Kunga', '2018-12-01', '09:12:26', 'Successfully logged in'),
(1610, '7', 'Raphael Kunga', '2018-12-01', '09:13:00', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1611, '7', 'Raphael Kunga', '2018-12-01', '09:14:30', 'Successfully logged in'),
(1612, '7', 'Raphael Kunga', '2018-12-01', '09:27:40', 'Successfully logged in'),
(1613, '7', 'Raphael Kunga', '2018-12-01', '09:28:05', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1614, '7', 'Raphael Kunga', '2018-12-01', '09:28:58', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1615, '7', 'Raphael Kunga', '2018-12-01', '09:31:17', 'Successfully logged in'),
(1616, '7', 'Raphael Kunga', '2018-12-01', '09:32:05', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1617, '7', 'Raphael Kunga', '2018-12-01', '09:33:56', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1618, '7', 'Raphael Kunga', '2018-12-01', '09:36:15', 'Successfully logged in'),
(1619, '7', 'Raphael Kunga', '2018-12-01', '09:37:24', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1620, '7', 'Raphael Kunga', '2018-12-01', '09:42:24', 'Successfully logged in'),
(1621, '7', 'Raphael Kunga', '2018-12-01', '09:43:33', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1622, '7', 'Raphael Kunga', '2018-12-01', '09:49:47', 'Delete Sales details for Rcpt#/Invoice#\'null\''),
(1623, '7', 'Raphael Kunga', '2018-12-01', '09:52:23', 'Successfully logged in'),
(1624, '7', 'Raphael Kunga', '2018-12-01', '09:53:20', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1625, '7', 'Raphael Kunga', '2018-12-01', '09:53:49', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1626, '7', 'Raphael Kunga', '2018-12-01', '09:58:15', 'Successfully logged in'),
(1627, '7', 'Raphael Kunga', '2018-12-01', '09:59:22', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1628, '7', 'Raphael Kunga', '2018-12-01', '09:59:43', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1629, '7', 'Raphael Kunga', '2018-12-01', '10:00:58', 'Successfully logged in'),
(1630, '7', 'Raphael Kunga', '2018-12-01', '10:01:41', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1631, '7', 'Raphael Kunga', '2018-12-01', '10:03:53', 'Successfully logged in'),
(1632, '7', 'Raphael Kunga', '2018-12-01', '10:04:16', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1633, '7', 'Raphael Kunga', '2018-12-01', '10:07:08', 'Successfully logged in'),
(1634, '7', 'Raphael Kunga', '2018-12-01', '10:07:46', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1635, '7', 'Raphael Kunga', '2018-12-01', '10:09:00', 'Successfully logged in'),
(1636, '7', 'Raphael Kunga', '2018-12-01', '10:09:30', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1637, '7', 'Raphael Kunga', '2018-12-01', '10:10:49', 'Successfully logged in'),
(1638, '7', 'Raphael Kunga', '2018-12-01', '10:12:24', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1639, '7', 'Raphael Kunga', '2018-12-01', '10:12:39', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1640, '7', 'Raphael Kunga', '2018-12-01', '10:14:06', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1641, '7', 'Raphael Kunga', '2018-12-01', '10:14:11', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1642, '7', 'Raphael Kunga', '2018-12-01', '10:14:18', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1643, '7', 'Raphael Kunga', '2018-12-01', '10:57:12', 'Successfully logged in'),
(1644, '7', 'Raphael Kunga', '2018-12-01', '10:59:31', 'Successfully logged in'),
(1645, '7', 'Raphael Kunga', '2018-12-01', '11:00:37', 'Successfully logged in'),
(1646, '7', 'Raphael Kunga', '2018-12-01', '11:08:02', 'Successfully logged in'),
(1647, '7', 'Raphael Kunga', '2018-12-01', '14:05:16', 'Successfully logged in'),
(1648, '7', 'Raphael Kunga', '2018-12-01', '21:21:59', 'Successfully logged in'),
(1649, '7', 'Raphael Kunga', '2018-12-02', '16:42:09', 'Successfully logged in'),
(1650, '7', 'Raphael Kunga', '2018-12-02', '16:45:59', 'Successfully logged in'),
(1651, '7', 'Raphael Kunga', '2018-12-02', '16:50:52', 'Successfully logged in'),
(1652, '7', 'Raphael Kunga', '2018-12-02', '16:56:24', 'Successfully logged in'),
(1653, '7', 'Raphael Kunga', '2018-12-02', '17:03:22', 'Successfully logged in'),
(1654, '7', 'Raphael Kunga', '2018-12-02', '17:11:58', 'Successfully logged in'),
(1655, '7', 'Raphael Kunga', '2018-12-02', '17:13:09', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1656, '7', 'Raphael Kunga', '2018-12-02', '17:14:05', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1657, '7', 'Raphael Kunga', '2018-12-02', '17:14:21', 'Successfully logged in'),
(1658, '7', 'Raphael Kunga', '2018-12-02', '17:15:12', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1659, '7', 'Raphael Kunga', '2018-12-02', '17:16:07', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1660, '7', 'Raphael Kunga', '2018-12-02', '17:19:17', 'Successfully logged in'),
(1661, '7', 'Raphael Kunga', '2018-12-02', '17:26:10', 'Delete Sales details for Rcpt#/Invoice#\'\''),
(1662, '7', 'Raphael Kunga', '2018-12-04', '09:14:45', 'Successfully logged in'),
(1663, '7', 'Raphael Kunga', '2018-12-04', '14:54:54', 'Successfully logged in'),
(1664, '7', 'Raphael Kunga', '2018-12-04', '16:32:50', 'Successfully logged in'),
(1665, '7', 'Raphael Kunga', '2018-12-05', '09:58:26', 'Successfully logged in'),
(1666, '7', 'Raphael Kunga', '2018-12-18', '13:22:16', 'Successfully logged in'),
(1667, '7', 'Raphael Kunga', '2018-12-27', '12:43:30', 'Successfully logged in'),
(1668, '7', 'Raphael Kunga', '2018-12-27', '12:54:33', 'Successfully logged in'),
(1669, '7', 'Raphael Kunga', '2018-12-27', '12:55:16', 'Successfully logged in'),
(1670, '7', 'Raphael Kunga', '2018-12-31', '15:38:02', 'Successfully logged in');

-- --------------------------------------------------------

--
-- Table structure for table `paymenttable`
--

CREATE TABLE `paymenttable` (
  `paymentid` int(11) NOT NULL,
  `details` varchar(255) NOT NULL,
  `supplierid` int(11) NOT NULL,
  `totalamount` double NOT NULL,
  `paidamount` double NOT NULL,
  `datepay` varchar(255) NOT NULL,
  `paymentmethod` varchar(255) NOT NULL,
  `transactionid` varchar(255) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `paymenttable`
--

INSERT INTO `paymenttable` (`paymentid`, `details`, `supplierid`, `totalamount`, `paidamount`, `datepay`, `paymentmethod`, `transactionid`, `status`) VALUES
(145, 'INV001', 15, 270000, 0, '2018-12-02', 'CASH', '---', 1),
(146, '34534', 15, 18000, 0, '2018-12-18', 'CASH', '---', 1);

-- --------------------------------------------------------

--
-- Table structure for table `quotation_info`
--

CREATE TABLE `quotation_info` (
  `id` int(11) NOT NULL,
  `quotation_no` varchar(255) NOT NULL,
  `service_id` varchar(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `vat` double NOT NULL,
  `price` double NOT NULL,
  `s` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quotation_info`
--

INSERT INTO `quotation_info` (`id`, `quotation_no`, `service_id`, `qty`, `unit_price`, `vat`, `price`, `s`) VALUES
(3, '1', 'P001', 10, 28000, 44800, 324800, 1),
(4, '1', 'P002', 9, 29000, 40320, 292320, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quotation_table`
--

CREATE TABLE `quotation_table` (
  `quotation_no` int(11) NOT NULL,
  `client_name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `phone_no` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `quotation_date` date NOT NULL,
  `total` double NOT NULL,
  `total_vat` double NOT NULL,
  `user_id` int(11) NOT NULL,
  `s` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `quotation_table`
--

INSERT INTO `quotation_table` (`quotation_no`, `client_name`, `address`, `city`, `phone_no`, `email`, `quotation_date`, `total`, `total_vat`, `user_id`, `s`) VALUES
(1, 'ABC CO', '23423', 'MOMBASA', '2340234', 'abc@gmail.com', '2018-11-30', 617120, 44800, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `receipt_table`
--

CREATE TABLE `receipt_table` (
  `receipt_no` varchar(255) NOT NULL,
  `clientid` int(11) NOT NULL,
  `lpo_no` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `total` double NOT NULL,
  `total_vat` double NOT NULL,
  `user_id` int(11) NOT NULL,
  `s` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `receipt_table`
--

INSERT INTO `receipt_table` (`receipt_no`, `clientid`, `lpo_no`, `date`, `total`, `total_vat`, `user_id`, `s`) VALUES
('3', 1, '-', '2018-12-02', 38280, 5280, 7, 1),
('4', 1, '-', '2018-12-02', 29000, 4000, 7, 1),
('5', 1, '-', '2018-12-02', 29000, 4000, 7, 1),
('6', 2, '-', '2018-12-02', 38280, 5280, 7, 1),
('7', 1, '-', '2018-12-27', 29000, 4000, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `removetable`
--

CREATE TABLE `removetable` (
  `removeid` int(11) NOT NULL,
  `productcode` varchar(255) NOT NULL,
  `qty` int(11) NOT NULL,
  `imei` varchar(255) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `removedate` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `userid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `removetable`
--

INSERT INTO `removetable` (`removeid`, `productcode`, `qty`, `imei`, `reason`, `removedate`, `status`, `userid`) VALUES
(6, 'P002', 1, '434534453453453443', 'broke', '2018-12-02', 1, 7);

-- --------------------------------------------------------

--
-- Table structure for table `renewtable`
--

CREATE TABLE `renewtable` (
  `id` int(11) NOT NULL,
  `final_date` date NOT NULL,
  `today_date` date NOT NULL,
  `code` varchar(255) NOT NULL,
  `s` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `salestable`
--

CREATE TABLE `salestable` (
  `salesid` int(11) NOT NULL,
  `details` int(11) NOT NULL,
  `date` varchar(255) NOT NULL,
  `productcode` varchar(255) NOT NULL,
  `imei` varchar(255) NOT NULL,
  `qty` int(11) NOT NULL,
  `unitprice` double NOT NULL,
  `vat` int(11) NOT NULL,
  `totalprice` double NOT NULL,
  `profit` double NOT NULL,
  `means_of_payment` varchar(255) NOT NULL,
  `transactionid` varchar(255) NOT NULL,
  `sold` varchar(255) NOT NULL,
  `status` int(11) NOT NULL,
  `userid` varchar(255) NOT NULL,
  `companyid` int(11) NOT NULL,
  `default_` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `salestable`
--

INSERT INTO `salestable` (`salesid`, `details`, `date`, `productcode`, `imei`, `qty`, `unitprice`, `vat`, `totalprice`, `profit`, `means_of_payment`, `transactionid`, `sold`, `status`, `userid`, `companyid`, `default_`) VALUES
(153, 3, '2018-12-02', 'P002', '34534534534534', 1, 33000, 5280, 38280, 13000, 'CASH', '---', 'yes', 1, '7', 1, 1),
(155, 5, '2018-12-02', 'P001', '4535345453', 1, 25000, 4000, 29000, 7000, 'CASH', '---', 'yes', 1, '7', 1, 1),
(156, 6, '2018-12-02', 'P002', '3453445435', 1, 33000, 5280, 38280, 13000, 'CASH', '---', 'yes', 1, '7', 1, 1),
(157, 7, '2018-12-27', 'P001', '345354344334345345', 1, 25000, 4000, 29000, 7000, 'CASH', '---', 'yes', 1, '7', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `stockingtable`
--

CREATE TABLE `stockingtable` (
  `stockingId` int(11) NOT NULL,
  `invoiceno` varchar(255) NOT NULL,
  `productcode` varchar(255) NOT NULL,
  `qty` int(11) NOT NULL,
  `imei` varchar(255) NOT NULL,
  `unitprice` double NOT NULL,
  `total` double NOT NULL,
  `invoicedate` varchar(255) NOT NULL,
  `supplierid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stockingtable`
--

INSERT INTO `stockingtable` (`stockingId`, `invoiceno`, `productcode`, `qty`, `imei`, `unitprice`, `total`, `invoicedate`, `supplierid`, `userid`, `status`) VALUES
(639, 'INV001', 'P001', 1, '345354344334345345', 18000, 18000, '2018-12-02', 15, 7, 0),
(640, 'INV001', 'P001', 1, '4535345453', 18000, 18000, '2018-12-02', 15, 7, 0),
(641, 'INV001', 'P001', 1, '3453454335345', 18000, 18000, '2018-12-02', 15, 7, 1),
(642, 'INV001', 'P001', 1, '34534543345345', 18000, 18000, '2018-12-02', 15, 7, 1),
(643, 'INV001', 'P001', 1, '345345435344334', 18000, 18000, '2018-12-02', 15, 7, 1),
(644, 'INV001', 'P002', 1, '434534453453453443', 20000, 20000, '2018-12-02', 15, 7, 0),
(645, 'INV001', 'P002', 1, '34534534534534', 20000, 20000, '2018-12-02', 15, 7, 0),
(646, 'INV001', 'P002', 1, '3452342342432', 20000, 20000, '2018-12-02', 15, 7, 1),
(647, 'INV001', 'P002', 1, '3453445435', 20000, 20000, '2018-12-02', 15, 7, 0),
(648, 'INV001', 'P002', 1, '3453453454353', 20000, 20000, '2018-12-02', 15, 7, 1),
(649, 'INV001', 'P003', 1, '345345453454343534', 20000, 20000, '2018-12-02', 15, 7, 1),
(650, 'INV001', 'P003', 1, '3453454343453443', 20000, 20000, '2018-12-02', 15, 7, 1),
(651, 'INV001', 'P003', 1, '34545464564545', 20000, 20000, '2018-12-02', 15, 7, 1),
(652, 'INV001', 'P003', 1, '6756764563545433454', 20000, 20000, '2018-12-02', 15, 7, 1),
(653, '34534', 'P001', 1, '4534543345534345', 18000, 18000, '2018-12-18', 15, 7, 1);

-- --------------------------------------------------------

--
-- Table structure for table `stocktable`
--

CREATE TABLE `stocktable` (
  `productcode` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `categoryid` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `currentunitprice` double NOT NULL,
  `lowestsellingprice` double NOT NULL,
  `highestsellingprice` double NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stocktable`
--

INSERT INTO `stocktable` (`productcode`, `type`, `description`, `categoryid`, `qty`, `currentunitprice`, `lowestsellingprice`, `highestsellingprice`, `status`) VALUES
('P001', 'HP COMPAQ', 'MODEL 2000', 10, 3, 18000, 23000, 25000, 1),
('P002', 'HP ELITEBOOK', 'MODEL 8690P', 10, 2, 20000, 25000, 33000, 1),
('P003', 'HP PROBOOK', 'MODEL 6560B', 10, 4, 20000, 26000, 29000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `supplierstable`
--

CREATE TABLE `supplierstable` (
  `id` int(11) NOT NULL,
  `suppliername` varchar(255) NOT NULL,
  `phoneno` varchar(255) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `supplierstable`
--

INSERT INTO `supplierstable` (`id`, `suppliername`, `phoneno`, `status`) VALUES
(1, 'ON SHELF', '-', 1),
(15, 'PRIMUSTECH', '0723561274', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usertable`
--

CREATE TABLE `usertable` (
  `id` int(11) NOT NULL,
  `idnumber` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phoneno` varchar(255) NOT NULL,
  `usertype` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(225) NOT NULL,
  `companyid` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `logged` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `usertable`
--

INSERT INTO `usertable` (`id`, `idnumber`, `name`, `phoneno`, `usertype`, `username`, `password`, `companyid`, `status`, `logged`) VALUES
(7, 934434543, 'Raphael Kunga', '0723561274', 'Admin', 'admin', '$2a$10$sUK3GcftJeE/xgcnMRXJFOum2ICZvgGpcT2vE4X53u.oWbcJXhoLS', 1, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categorytable`
--
ALTER TABLE `categorytable`
  ADD PRIMARY KEY (`categoryid`);

--
-- Indexes for table `clientstable`
--
ALTER TABLE `clientstable`
  ADD PRIMARY KEY (`clientid`);

--
-- Indexes for table `companytable`
--
ALTER TABLE `companytable`
  ADD PRIMARY KEY (`companyid`),
  ADD UNIQUE KEY `companyname` (`companyname`);

--
-- Indexes for table `invoicetable`
--
ALTER TABLE `invoicetable`
  ADD PRIMARY KEY (`invoiceno`);

--
-- Indexes for table `invoice_info`
--
ALTER TABLE `invoice_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `logstable`
--
ALTER TABLE `logstable`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `paymenttable`
--
ALTER TABLE `paymenttable`
  ADD PRIMARY KEY (`paymentid`),
  ADD KEY `supplierid` (`supplierid`),
  ADD KEY `supplierid_2` (`supplierid`);

--
-- Indexes for table `quotation_info`
--
ALTER TABLE `quotation_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quotation_table`
--
ALTER TABLE `quotation_table`
  ADD PRIMARY KEY (`quotation_no`);

--
-- Indexes for table `receipt_table`
--
ALTER TABLE `receipt_table`
  ADD PRIMARY KEY (`receipt_no`);

--
-- Indexes for table `removetable`
--
ALTER TABLE `removetable`
  ADD PRIMARY KEY (`removeid`);

--
-- Indexes for table `renewtable`
--
ALTER TABLE `renewtable`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salestable`
--
ALTER TABLE `salestable`
  ADD PRIMARY KEY (`salesid`),
  ADD KEY `userid` (`userid`),
  ADD KEY `productcode` (`productcode`),
  ADD KEY `companyid` (`companyid`),
  ADD KEY `userid_2` (`userid`),
  ADD KEY `details` (`details`);

--
-- Indexes for table `stockingtable`
--
ALTER TABLE `stockingtable`
  ADD PRIMARY KEY (`stockingId`),
  ADD KEY `supplierid` (`supplierid`),
  ADD KEY `userid` (`userid`),
  ADD KEY `productcode` (`productcode`),
  ADD KEY `userid_2` (`userid`);

--
-- Indexes for table `stocktable`
--
ALTER TABLE `stocktable`
  ADD PRIMARY KEY (`productcode`),
  ADD KEY `categoryid` (`categoryid`),
  ADD KEY `categoryid_2` (`categoryid`);

--
-- Indexes for table `supplierstable`
--
ALTER TABLE `supplierstable`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phoneno` (`phoneno`),
  ADD UNIQUE KEY `suppliername` (`suppliername`);

--
-- Indexes for table `usertable`
--
ALTER TABLE `usertable`
  ADD PRIMARY KEY (`id`),
  ADD KEY `companyid` (`companyid`),
  ADD KEY `companyid_2` (`companyid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categorytable`
--
ALTER TABLE `categorytable`
  MODIFY `categoryid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `clientstable`
--
ALTER TABLE `clientstable`
  MODIFY `clientid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `companytable`
--
ALTER TABLE `companytable`
  MODIFY `companyid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `invoice_info`
--
ALTER TABLE `invoice_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `logstable`
--
ALTER TABLE `logstable`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1671;

--
-- AUTO_INCREMENT for table `paymenttable`
--
ALTER TABLE `paymenttable`
  MODIFY `paymentid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=147;

--
-- AUTO_INCREMENT for table `quotation_info`
--
ALTER TABLE `quotation_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `removetable`
--
ALTER TABLE `removetable`
  MODIFY `removeid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `renewtable`
--
ALTER TABLE `renewtable`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salestable`
--
ALTER TABLE `salestable`
  MODIFY `salesid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=158;

--
-- AUTO_INCREMENT for table `stockingtable`
--
ALTER TABLE `stockingtable`
  MODIFY `stockingId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=654;

--
-- AUTO_INCREMENT for table `supplierstable`
--
ALTER TABLE `supplierstable`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `usertable`
--
ALTER TABLE `usertable`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
