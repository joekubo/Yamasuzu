-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 30, 2018 at 03:58 PM
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
(1503, '7', 'Raphael Kunga', '2018-11-30', '17:04:35', 'Successfully logged in');

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
(140, 'INV001', 15, 168300, 0, '2018-11-30', 'CASH', '---', 1);

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
('1', 2, '-', '2018-11-30', 64960, 8960, 7, 1),
('2', 2, '-', '2018-11-30', 32480, 4480, 7, 1),
('3', 2, '-', '2018-11-30', 32480, 4480, 7, 1),
('4', 1, '-', '2018-11-30', 33640, 4640, 7, 1);

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
(69, 1, '2018-11-30', 'P001', '34534534543354', 1, 28000, 4480, 32480, 10000, 'CASH', '---', 'yes', 1, '7', 1, 1),
(70, 1, '2018-11-30', 'P001', '2342342323', 1, 28000, 4480, 32480, 10000, 'CASH', '---', 'yes', 1, '7', 1, 1),
(71, 2, '2018-11-30', 'P001', '345342334534534', 1, 28000, 4480, 32480, 10000, 'CASH', '---', 'yes', 1, '7', 1, 1),
(72, 3, '2018-11-30', 'P001', '3453456456456', 1, 28000, 4480, 32480, 10000, 'CASH', '---', 'yes', 1, '7', 1, 1),
(73, 4, '2018-11-30', 'P002', '4564564564564', 1, 29000, 4640, 33640, 8900, 'CASH', '---', 'yes', 1, '7', 1, 0);

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
(599, 'INV001', 'P001', 1, '3423423343454534', 18000, 18000, '2018-11-30', 15, 7, 1),
(600, 'INV001', 'P001', 1, '34534534543354', 18000, 18000, '2018-11-30', 15, 7, 1),
(601, 'INV001', 'P001', 1, '3453456456456', 18000, 18000, '2018-11-30', 15, 7, 1),
(602, 'INV001', 'P001', 1, '3453453454567565', 18000, 18000, '2018-11-30', 15, 7, 1),
(603, 'INV001', 'P001', 1, '345342334534534', 18000, 18000, '2018-11-30', 15, 7, 1),
(604, 'INV001', 'P001', 1, '2342342323', 18000, 18000, '2018-11-30', 15, 7, 1),
(605, 'INV001', 'P002', 1, '342343432423', 20100, 20100, '2018-11-30', 15, 7, 1),
(606, 'INV001', 'P002', 1, '2423423423', 20100, 20100, '2018-11-30', 15, 7, 1),
(607, 'INV001', 'P002', 1, '4564564564564', 20100, 20100, '2018-11-30', 15, 7, 1);

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
('P001', 'HP COMPAQ', 'MODEL 2000', 10, 2, 18000, 23000, 28000, 1),
('P002', 'HP ELITE BOOK', '23534R', 10, 2, 20100, 25700, 29000, 1);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1504;

--
-- AUTO_INCREMENT for table `paymenttable`
--
ALTER TABLE `paymenttable`
  MODIFY `paymentid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;

--
-- AUTO_INCREMENT for table `quotation_info`
--
ALTER TABLE `quotation_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `removetable`
--
ALTER TABLE `removetable`
  MODIFY `removeid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `renewtable`
--
ALTER TABLE `renewtable`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salestable`
--
ALTER TABLE `salestable`
  MODIFY `salesid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT for table `stockingtable`
--
ALTER TABLE `stockingtable`
  MODIFY `stockingId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=608;

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
