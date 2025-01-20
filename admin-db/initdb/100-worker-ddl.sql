# 스키마 생성
CREATE DATABASE jpa;

# 유저 생성
create user 'grooo-sample'@'%' identified by 'grooo~!';

# 유저에 스키마 권한 설정
grant all privileges on jpa.* to 'grooo-sample'@'%';

# reflush
flush privileges;

# 스키마 사용
use jpa;

#--------------------------------------------------------------------------------------------------------

CREATE TABLE worker (
                        id              INT     UNSIGNED    NOT NULL    AUTO_INCREMENT,
                        login_id        VARCHAR(30)         NOT NULL,
                        password        VARCHAR(500)        NOT NULL,
                        name            VARCHAR(500)        NOT NULL,
                        email           VARCHAR(255)        NOT NULL,
                        expiry_date     DATETIME            NOT NULL,
                        is_active       BOOLEAN             NOT NULL    DEFAULT 1 ,
                        is_deleted      BOOLEAN             NOT NULL    DEFAULT 0 ,
                        date_created    DATETIME            NOT NULL    DEFAULT CURRENT_TIMESTAMP,
                        last_updated    DATETIME            NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (id)
);
CREATE UNIQUE INDEX uix_worker_01 ON worker (login_id);

CREATE TABLE worker_login_log (
    id              INT     UNSIGNED    NOT NULL AUTO_INCREMENT,
    worker_id       INT     UNSIGNED    NOT NULL,
    worker_ip       VARCHAR(50)         NOT NULL,
    date_created    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE function (
                          id          INT         UNSIGNED    NOT NULL AUTO_INCREMENT,
                          code        VARCHAR(50)             NOT NULL,
                          name        VARCHAR(100)            NOT NULL,
                          url         VARCHAR(500)            NOT NULL,
                          http_method VARCHAR(50)             NOT NULL,
                          PRIMARY KEY (id)
);
CREATE UNIQUE INDEX uix_function_01 ON function (code);

CREATE TABLE role (
                      id              INT     UNSIGNED    NOT NULL AUTO_INCREMENT,
                      name            VARCHAR(100)        NOT NULL,
                      description     VARCHAR(255),
                      is_active       BOOLEAN             NOT NULL DEFAULT 1 ,
                      creator_id      INT     UNSIGNED    NOT NULL,
                      date_created    DATETIME            NOT NULL  DEFAULT CURRENT_TIMESTAMP,
                      last_updator_id INT     UNSIGNED    NOT NULL,
                      last_updated    DATETIME            NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      PRIMARY KEY (id)
);

CREATE TABLE role_function (
                               role_id        INT     UNSIGNED    NOT NULL,
                               function_id    INT     UNSIGNED    NOT NULL,
                               creator_id     INT     UNSIGNED    NOT NULL,
                               date_created   DATETIME            NOT NULL  DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE role_function ADD CONSTRAINT PK_role_function PRIMARY KEY (role_id, function_id);
ALTER TABLE role_function ADD CONSTRAINT FK_role_function_0 FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE role_function ADD CONSTRAINT FK_role_function_1 FOREIGN KEY (function_id) REFERENCES function (id);

CREATE TABLE worker_role (
                             worker_id       INT     UNSIGNED    NOT NULL,
                             role_id         INT     UNSIGNED    NOT NULL,
                             creator_id      INT     UNSIGNED    NOT NULL,
                             date_created    DATETIME            NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE worker_role ADD CONSTRAINT PK_worker_role PRIMARY KEY (worker_id, role_id);
ALTER TABLE worker_role ADD CONSTRAINT FK_worker_role FOREIGN KEY (role_id) REFERENCES role (id);
