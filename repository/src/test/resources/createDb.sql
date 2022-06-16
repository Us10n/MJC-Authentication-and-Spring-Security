create schema IF NOT EXISTS module;

CREATE TABLE IF NOT EXISTS module.gift_certificates
(
    `id`               BIGINT         NOT NULL AUTO_INCREMENT UNIQUE,
    `name`             TEXT           NOT NULL,
    `description`      TEXT           NOT NULL,
    `price`            DECIMAL(10, 2) NOT NULL,
    `duration`         INT            NOT NULL,
    `create_date`      DATETIME       NOT NULL,
    `last_update_date` DATETIME       NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS module.tags
(
    `id`   BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    `name` TEXT   NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS module.gift_certificate_tags
(
    `id`                  BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
    `gift_certificate_id` BIGINT NOT NULL,
    `tag_id`              BIGINT NOT NULL,
    FOREIGN KEY (`gift_certificate_id`) REFERENCES gift_certificates,
    FOREIGN KEY (`tag_id`) REFERENCES tags,
    PRIMARY KEY (`id`)
    );

INSERT INTO module.tags
values ('1', 'cinema');
INSERT INTO module.tags
values ('2', 'food');
INSERT INTO module.tags
values ('3', 'cash');
INSERT INTO module.tags
values ('4', 'drink');
INSERT INTO module.tags
values ('5', 'city');
INSERT INTO module.tags
values ('6', 'building');
INSERT INTO module.tags
values ('7', 'alcohol');
INSERT INTO module.tags
values ('8', 'park');
INSERT INTO module.tags
values ('9', 'for family');
INSERT INTO module.tags
values ('10', 'fun');
INSERT INTO module.tags
values ('11', 'cheap');
INSERT INTO module.tags
values ('12', 'summer');
INSERT INTO module.tags
values ('13', 'anime');
INSERT INTO module.tags
values ('14', 'cosplay');
INSERT INTO module.tags
values ('15', 'festival');
INSERT INTO module.tags
values ('16', 'health');
INSERT INTO module.tags
values ('20', 'hello');
INSERT INTO module.tags
values ('21', 'wow');
INSERT INTO module.tags
values ('22', 'test');
INSERT INTO module.tags
values ('23', 'patched');

INSERT INTO module.gift_certificates
values ('1', 'Movie Enjoyer', 'Watch movies whole day for free', '20.00', '1', '2022-04-11 10:00:11',
        '2022-04-11 18:37:17');
INSERT INTO module.gift_certificates
values ('2', 'Alco day', 'Have any drinks up to the cost 50$', '49.99', '1', '2022-04-05 00:00:00',
        '2022-04-05 00:00:00');
INSERT INTO module.gift_certificates
values ('3', 'Amusement park ticket', 'Park pass', '20.00', '3', '2022-04-03 00:00:00', '2022-04-05 00:00:00');
INSERT INTO module.gift_certificates
values ('4', 'Park pass', 'Hero park pass. ', '100.00', '10', '2022-04-05 00:00:00', '2022-04-06 00:00:00');
INSERT INTO module.gift_certificates
values ('5', 'Comic-Con', 'Festival 4-day pass', '111.11', '4', '2022-04-01 00:00:00', '2022-04-20 00:00:00');

INSERT INTO module.gift_certificate_tags
values ('6', '2', '2');
INSERT INTO module.gift_certificate_tags
values ('7', '2', '3');
INSERT INTO module.gift_certificate_tags
values ('8', '2', '10');
INSERT INTO module.gift_certificate_tags
values ('9', '2', '7');
INSERT INTO module.gift_certificate_tags
values ('10', '2', '4');
INSERT INTO module.gift_certificate_tags
values ('11', '3', '2');
INSERT INTO module.gift_certificate_tags
values ('12', '3', '8');
INSERT INTO module.gift_certificate_tags
values ('13', '3', '9');
INSERT INTO module.gift_certificate_tags
values ('14', '3', '10');
INSERT INTO module.gift_certificate_tags
values ('15', '3', '12');
INSERT INTO module.gift_certificate_tags
values ('16', '4', '16');
INSERT INTO module.gift_certificate_tags
values ('17', '4', '9');
INSERT INTO module.gift_certificate_tags
values ('18', '4', '6');
INSERT INTO module.gift_certificate_tags
values ('19', '5', '2');
INSERT INTO module.gift_certificate_tags
values ('20', '5', '8');
INSERT INTO module.gift_certificate_tags
values ('21', '5', '11');
INSERT INTO module.gift_certificate_tags
values ('22', '5', '13');
INSERT INTO module.gift_certificate_tags
values ('23', '5', '14');
INSERT INTO module.gift_certificate_tags
values ('24', '5', '15');
INSERT INTO module.gift_certificate_tags
values ('25', '1', '23');


SELECT module.gift_certificate_tags.id, count(tag_id) GROUP  BY gift_certificate-tags

