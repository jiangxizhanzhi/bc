ALTER TABLE `iov_dut`
MODIFY COLUMN `model` varchar(36) NOT NULL COMMENT '车型';

UPDATE `iov_dut` SET `model`='9526946147e2fdd1f63380ff3118328e' where id > 0;