-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 29, 2020 at 06:25 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `reegan`
--

-- --------------------------------------------------------

--
-- Table structure for table `reegan`
--

CREATE TABLE `reegan` (
  `indexe` int(10) NOT NULL,
  `id` varchar(10) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `gender` varchar(7) NOT NULL,
  `pob` varchar(50) NOT NULL,
  `contact` varchar(15) NOT NULL,
  `age` varchar(15) NOT NULL,
  `date` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reegan`
--

INSERT INTO `reegan` (`indexe`, `id`, `name`, `gender`, `pob`, `contact`, `age`, `date`) VALUES
(10, '12345678', 'ronny stono', 'male', 'meru', '0712345678', '12/12/1990', '29/07/2020'),
(11, '87654321', 'test name', 'female', 'meru', '0786754321', '34/12/1990', '29/07/2020'),
(12, '12345678', 'qwerrt qwert', 'female', 'nairobi', '0987654456', '32/10/1990', '29/07/2020');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `reegan`
--
ALTER TABLE `reegan`
  ADD PRIMARY KEY (`indexe`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `reegan`
--
ALTER TABLE `reegan`
  MODIFY `indexe` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
