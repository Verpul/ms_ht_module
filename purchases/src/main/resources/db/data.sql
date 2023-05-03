DELETE FROM purchases;
INSERT IGNORE INTO purchases VALUES
    (1, 'Покупка №1', 'https://www.google.com', 1, null, null, true);
INSERT IGNORE INTO weight VALUES
    (2, 'Покупка №2', null, 5, null, null, true);
INSERT IGNORE INTO weight VALUES
    (3, 'Покупка №3', 'https://www.microsoft.com', 1, null, null, false);
INSERT IGNORE INTO weight VALUES
    (4, 'Покупка №4', null, 1, '2023-01-01', '2024-01-01', false);
INSERT IGNORE INTO weight VALUES
    (5, 'Покупка №5', 'https://www.buy.com', 1, '2023-02-02', '2023-07-02' false);