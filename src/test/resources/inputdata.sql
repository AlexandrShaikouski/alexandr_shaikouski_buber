use buber;
INSERT INTO role
(name)
VALUES ('admin'),
       ('driver'),
       ('client');
INSERT INTO bonus
(name,factor)
VALUES ('light',0.05),
       ('medium',0.10),
       ('hard', 0.15),
       ('free', 1);
