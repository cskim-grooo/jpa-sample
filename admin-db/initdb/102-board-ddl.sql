# 스키마 사용
use jpa;

#--------------------------------------------------------------------------------------------------------

CREATE TABLE board (
                       id                       INT         UNSIGNED    NOT NULL   AUTO_INCREMENT,
                       title                    VARCHAR(255)            NOT NULL,
                       content                  TEXT                    NOT NULL,
                       view_count               INT                     NOT NULL   DEFAULT 0,
                       is_deleted               BOOLEAN                 NOT NULL   DEFAULT 0,
                       creator_id               INT         UNSIGNED    NOT NULL,
                       date_created             DATETIME                NOT NULL   DEFAULT CURRENT_TIMESTAMP,
                       last_updator_id          INT         UNSIGNED    NOT NULL,
                       last_updated             DATETIME                NOT NULL   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT fk_board_creator FOREIGN KEY (creator_id) REFERENCES worker(id),
                       CONSTRAINT fk_board_updator FOREIGN KEY (last_updator_id) REFERENCES worker(id),
                       PRIMARY KEY (id)
);

CREATE TABLE board_comment (
                       id                       INT         UNSIGNED    NOT NULL   AUTO_INCREMENT,
                       board_id                 INT         UNSIGNED    NOT NULL,
                       content                  TEXT                    NOT NULL,
                       creator_id               INT         UNSIGNED    NOT NULL,
                       date_created             DATETIME                NOT NULL   DEFAULT CURRENT_TIMESTAMP,
                       last_updator_id          INT         UNSIGNED    NOT NULL,
                       last_updated             DATETIME                NOT NULL   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT fk_board_comment FOREIGN KEY (board_id) REFERENCES board(id),
                       CONSTRAINT fk_board_comment_creator FOREIGN KEY (creator_id) REFERENCES worker(id),
                       CONSTRAINT fk_board_comment_updator FOREIGN KEY (last_updator_id) REFERENCES worker(id),
                       PRIMARY KEY (id)
);
