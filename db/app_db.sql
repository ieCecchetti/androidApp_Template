-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 18, 2018 at 02:46 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `app_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `cid` int(11) NOT NULL,
  `name` varchar(60) NOT NULL,
  `description` text NOT NULL,
  `date` date NOT NULL,
  `url` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`cid`, `name`, `description`, `date`, `url`) VALUES
(1, 'Ethereum ', 'Ethereum è una piattaforma decentralizzata del Web 3.0 per la creazione e pubblicazione peer-to-peer di contratti intelligenti (smart contracts) creati in un linguaggio di programmazione Turing-completo.\r\n\r\nEthereum è diverso da Bitcoin in quanto consente di creare contratti intelligenti (smart contracts[1]) che possono essere descritti come denaro digitale altamente programmabile.', '2018-09-09', 'https://i1.wp.com/www.kiroinvest.com/wp-content/uploads/2018/01/ETHEREUM-LOGO-2.png?fit=1920%2C1920&ssl=1'),
(2, 'Bitcoin ', 'Bitcoin (codice: BTC o XBT) è una criptovaluta e un sistema di pagamento mondiale creato nel 2009 da un anonimo inventore, noto con lo pseudonimo di Satoshi Nakamoto, che sviluppò un\'idea da lui stesso presentata su Internet a fine 2008.[1] Per convenzione se il termine Bitcoin è utilizzato con l\'iniziale maiuscola si riferisce alla tecnologia e alla rete, mentre se minuscola (bitcoin) si riferisce alla valuta in sé', '2018-09-21', 'http://pngimg.com/uploads/bitcoin/bitcoin_PNG48.png');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `uid` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `data` date NOT NULL,
  `permission` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`uid`, `username`, `email`, `password`, `data`, `permission`) VALUES
(1, 'richi', 'richi@gmail.com', '5f4dcc3b5aa765d61d8327deb882cf99', '2018-09-21', 'a'),
(2, 'federico', 'fede.gianno@gmail.com', '5f4dcc3b5aa765d61d8327deb882cf99', '2018-09-09', 'c'),
(18, 'giovanni', 'giovanni@gmail.it', '5f4dcc3b5aa765d61d8327deb882cf99', '2018-09-22', 'c'),
(20, 'fede', 'fede.giannogmail.com', '5f4dcc3b5aa765d61d8327deb882cf99', '2018-09-23', 'c'),
(21, 'Ciao', 'ciao@gmail.com', '6e6bc4e49dd477ebc98ef4046c067b5f', '2018-09-24', 'c'),
(22, 'User', 'user@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', '2018-09-24', 'c');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`cid`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`),
  ADD UNIQUE KEY `user` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
