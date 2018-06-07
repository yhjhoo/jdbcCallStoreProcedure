create table person
(
	id int auto_increment
		primary key,
	name varchar(45) default 'NULL' null
);

INSERT INTO test.person (id, name) VALUES (1, 'hello');
INSERT INTO test.person (id, name) VALUES (2, 'hi');


DELIMITER $$

DROP PROCEDURE IF EXISTS my_sqrt$$
CREATE PROCEDURE my_sqrt(input_number INT, OUT out_number FLOAT)
  BEGIN
    select * from person;
    SET out_number=SQRT(input_number);
  END$$

DELIMITER ;


call my_sqrt(12, @test)
