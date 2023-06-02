CREATE TABLE IF NOT EXISTS purchases
(
    id                    INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title                 VARCHAR(250) NOT NULL,
    link                  VARCHAR(500),
    purchase_place        VARCHAR(100),
    amount                INT(3)       NOT NULL,
    purchase_date         DATE,
    guarantee_expire_date DATE,
    active                BOOLEAN      NOT NULL,
    guarantee_interval    VARCHAR(50),
    guarantee_duration    INT(3)
) engine = InnoDB;