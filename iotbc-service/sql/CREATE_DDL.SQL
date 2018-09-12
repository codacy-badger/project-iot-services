CREATE database  blockchain;
CREATE TABLE IF NOT EXISTS  M_Job (
record_ID   VARCHAR(64) NOT NULL,
transaction_ID varchar(100),
create_time  varchar(30),
temp1 varchar(100),
temp2 varchar(100),
temp3 varchar(100),
PRIMARY KEY(record_ID),
INDEX(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS  M_Doc (
hash_key   VARCHAR(64) NOT NULL,
doc_URL varchar(200),
record_ID varchar(100),
create_time  varchar(30),
temp1 varchar(100),
temp2 varchar(100),
temp3 varchar(100),
PRIMARY KEY(hash_key),
INDEX(record_ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;