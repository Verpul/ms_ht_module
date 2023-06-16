DELETE FROM purchases;
INSERT IGNORE INTO purchases VALUES
    (1, 'Покупка №1', 'https://www.google.com', null, 1, null, null, true, null, null);
INSERT IGNORE INTO purchases VALUES
    (2, 'Покупка №2', null , null, 5, null, null, true, null, null);
INSERT IGNORE INTO purchases VALUES
    (3, 'Покупка №3', 'https://www.microsoft.com', null, 1, null, null, false, null, null);
INSERT IGNORE INTO purchases VALUES
    (4, 'Покупка №4', null, null, 1, '2023-01-01', '2024-01-01', false, 'День', 30);
INSERT IGNORE INTO purchases VALUES
    (5, 'Покупка №5', 'https://www.buy.com', null, 1, '2023-02-02', '2023-07-02', false, 'Месяц', 6);
INSERT IGNORE INTO purchases VALUES
    (6, 'Покупка №6', null, 'Mall', 1, '2023-02-02', null, false, null, null);
INSERT IGNORE INTO purchases VALUES
    (7, 'Покупка №4', null, null, 1, '2022-01-01', '2023-01-01', false, 'Год', 1);