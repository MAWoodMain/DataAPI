CREATE TABLE `devices` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `parentId` INT,
  `name` varchar(25) NOT NULL,
  `tag` varchar(25) NOT NULL UNIQUE,
  `description` VARCHAR(255),
  PRIMARY KEY (`id`)
);

CREATE TABLE `datatypes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `tag` varchar(25) NOT NULL UNIQUE,
  `symbol` varchar(5) NOT NULL,
  `description` TEXT,
  PRIMARY KEY (`id`)
);

CREATE TABLE `readings` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `deviceId` INT NOT NULL,
  `dataTypeId` INT NOT NULL,
  `reading` FLOAT NOT NULL,
  `timestamp` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `devices` ADD CONSTRAINT `devices_fk0` FOREIGN KEY (`parentId`) REFERENCES `devices`(`id`);

ALTER TABLE `readings` ADD CONSTRAINT `readings_fk0` FOREIGN KEY (`deviceId`) REFERENCES `devices`(`id`);

ALTER TABLE `readings` ADD CONSTRAINT `readings_fk1` FOREIGN KEY (`dataTypeId`) REFERENCES `datatypes`(`id`);
