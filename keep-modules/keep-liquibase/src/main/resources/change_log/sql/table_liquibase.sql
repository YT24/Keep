SET NAMES utf8mb4;


DROP TABLE IF EXISTS `table_liquibase`;
CREATE TABLE `test_liquibase`
(
    `id`     int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`   varchar(255) DEFAULT NULL COMMENT '名称',
    `length` int(11) DEFAULT NULL COMMENT '长度',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



