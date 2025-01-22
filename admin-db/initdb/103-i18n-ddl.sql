# 스키마 사용
use jpa;

#--------------------------------------------------------------------------------------------------------

# 언어
CREATE TABLE language (
                          id              INT     UNSIGNED    NOT NULL    AUTO_INCREMENT,
                          name            VARCHAR(100)        NOT NULL,
                          code            VARCHAR(50)         NOT NULL,
                          lcid            VARCHAR(50)         NOT NULL,
                          PRIMARY KEY (id)
);
CREATE UNIQUE INDEX uix_language_01 ON language (lcid);


# 거래처
CREATE TABLE partner (
                         id                      INT     UNSIGNED        NOT NULL    AUTO_INCREMENT,
                         phone_no                VARCHAR(20)             NOT NULL,
                         fax_no                  VARCHAR(20)             NOT NULL,
                         is_active               BOOLEAN                 NOT NULL    DEFAULT 1 ,
                         creator_id              INT     UNSIGNED        NOT NULL,
                         date_created            DATETIME                NOT NULL    DEFAULT CURRENT_TIMESTAMP,
                         last_updator_id         INT     UNSIGNED        NOT NULL,
                         last_updated            DATETIME                NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (id)
);

# 거래처 다국어
CREATE TABLE partner_i18n (
                              partner_id      INT     UNSIGNED        NOT NULL,
                              language_id     INT     UNSIGNED        NOT NULL,
                              name            VARCHAR(100)            NOT NULL,
                              address         VARCHAR(255)            NOT NULL,
                              PRIMARY KEY (partner_id,language_id)
);
