CREATE TABLE User_user
(
    Id INT(11) NOT NULL AUTO_INCREMENT,
    Name VARCHAR(1000) NOT NULL,
    Password varchar(16) not NULL,
    Email VARCHAR(1000) NOT NULL,
    Status INT NOT NULL DEFAULT 1,
    CreateTime DateTime,
    PRIMARY KEY(Id)
)
