create table Account(
	account_Id INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(1000) NOT NULL,
    Password varchar(16) not NULL,
    salt VARCHAR(1000) NOT NULL  default '',
    email VARCHAR(1000) NOT NULL default '',
    Status INT NOT NULL DEFAULT 1,
    PRIMARY KEY(account_Id)
);