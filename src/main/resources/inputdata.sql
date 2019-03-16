use buber;
INSERT INTO role
  (name)
VALUES ('admin'),
       ('driver'),
       ('client');
INSERT INTO bonus
  (name, factor)
VALUES ('light', 0.05),
       ('medium', 0.10),
       ('hard', 0.15),
       ('free', 1);
INSERT INTO user_account
(login, password, first_name, email, phone, status, registration_date, role_id)
VALUES ('admin', '8df79deb606c9a5955d4d7fbd93037c29f736659b06aef73a377626dcd404b41', 'Admin', 'admin@admin.ru',
        '+375251111111', 'off-line', '0', '1'),
       ('driver', 'AE788F9D08032173D63F16A059685C9B0890F2FA1128C84859D508AAE58E9D02', 'Driver', 'driver@driver.ru',
        '+375252222222', 'off-line', '0', '2'),
       ('client', 'A3FA61A1980CDDB31E03B803B03B43C7D1638ECE2620ABACEF41CD7BF75AB216', 'Client', 'client@client.ru',
        '+375253333333', 'off-line', '0', '3');