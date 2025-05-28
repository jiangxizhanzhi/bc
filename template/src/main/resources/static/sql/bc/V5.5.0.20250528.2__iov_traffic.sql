ALTER TABLE `iov_traffic`
MODIFY COLUMN `model` varchar(36) NOT NULL COMMENT '车型';

UPDATE `iov_traffic` SET `model`='9526946147e2fdd1f63380ff3118328e' where id > 0;