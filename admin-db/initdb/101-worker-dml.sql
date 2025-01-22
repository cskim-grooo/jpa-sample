use jpa;

# 유저 정보 insert
INSERT INTO worker (id,login_id,password,name,email,expiry_date,is_active,is_deleted,date_created,last_updated)
VALUES (1, 'id4grooo', '$2a$10$C1A1PuDSejtz2t42njgiYOoxlTEXGTyiJ6c1SbxDdzUkXOnXO7GCC', 'id4grooo', 'admin@admin.net', '2030-02-22 10:01:17', 1, 0, '2020-01-21 10:01:17', '2020-01-21 10:01:17');

# 권한 정보 insert
INSERT INTO role (id, name, description, is_active, creator_id, date_created, last_updator_id, last_updated)
VALUES (1, '관리자', '管理者', 1, 1, '2020-01-21 10:07:27', 1, '2020-01-21 10:07:27');
INSERT INTO role (id, name, description, is_active, creator_id, date_created, last_updator_id, last_updated)
VALUES (2, '작업자', '作業者', 1, 1, '2020-01-21 10:07:27', 1, '2020-01-21 10:07:27');
INSERT INTO role (id, name, description, is_active, creator_id, date_created, last_updator_id, last_updated)
VALUES (3, '직원', 'スタッフ', 1, 1, '2020-01-21 10:07:27', 1, '2020-01-21 10:07:27');

# 유저 권한 insert
INSERT INTO worker_role (worker_id, role_id, creator_id, date_created) VALUES (1, 1, 1, '2020-01-22 09:16:29');

## /workers
INSERT INTO function (id, code, name, url, http_method) VALUES (1, 'CREATE_BOARDS', '게시판등록', '/board', 'POST');
INSERT INTO function (id, code, name, url, http_method) VALUES (2, 'READ_BOARDS',   '게시판조회', '/board', 'GET');
INSERT INTO function (id, code, name, url, http_method) VALUES (3, 'UPDATE_BOARDS', '게시판수정', '/board', 'PUT');
INSERT INTO function (id, code, name, url, http_method) VALUES (4, 'DELETE_BOARDS', '게시판삭제', '/board', 'DELETE');
INSERT INTO function (id, code, name, url, http_method) VALUES (5, 'READ_PARTNERS', '파트너조회', '/partner', 'GET');

### 관리자권한
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (1, 1, 1, '2020-01-21 10:07:27'); # C
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (1, 2, 1, '2020-01-21 10:07:27'); # R
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (1, 3, 1, '2020-01-21 10:07:27'); # U
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (1, 4, 1, '2020-01-21 10:07:27'); # D
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (1, 5, 1, '2020-01-21 10:07:27'); # D

### 작업자권한
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (2, 1, 1, '2020-01-21 10:07:27'); # C
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (2, 2, 1, '2020-01-21 10:07:27'); # R
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (2, 3, 1, '2020-01-21 10:07:27'); # U

## 직원
INSERT INTO role_function(role_id, function_id, creator_id, date_created) values (3, 2, 1, '2020-01-21 10:07:27'); # R
