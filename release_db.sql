CREATE DATABASE finflow;

CREATE TABLE finflow.user (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `fname` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE finflow.transactionType (
  id INT NOT NULL AUTO_INCREMENT,
  type VARCHAR(255) NOT NULL,
  category ENUM('Income', 'Expense') NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE finflow.transaction (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `amount` double NOT NULL,
  `title` varchar(255) NOT NULL,
  `type` int NOT NULL,
  `date` date NOT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
   FOREIGN KEY (`type`) REFERENCES `transactionType` (`id`)
);


INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Salary', 'Income');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Investment', 'Income');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Gifts', 'Income');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Rental Income', 'Income');

INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Education', 'Expense');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Grocery', 'Expense');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Rent', 'Expense');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Travel', 'Expense');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Food', 'Expense');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Entertainment', 'Expense');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Clothes', 'Expense');
INSERT INTO finflow.transactionType (`type`, `category`) VALUES ('Utilities', 'Expense');
