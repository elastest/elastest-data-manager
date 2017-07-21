CREATE TABLE if not exists `elastest`.`test` (
  `test_id` INT NOT NULL,
  `testcol1` VARCHAR(45) NULL,
  `testcol2` VARCHAR(45) NULL,
  PRIMARY KEY (`test_id`));

DELETE FROM `elastest`.`test`;
INSERT INTO `elastest`.`test` VALUES (1,'test1','test1');
INSERT INTO `elastest`.`test` VALUES (2,'test2','test2');
INSERT INTO `elastest`.`test` VALUES (3,'test3','test3');

SELECT 'SQL script completed !!!' as ''
