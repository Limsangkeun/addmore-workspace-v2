insert into authority (id, name)
values ('a948f0ae-83e1-4e78-8a03-beaa504b6513', 'ROLE_ADMIN'),
       ('00e23db5-cdbd-482a-b019-189b93ecfb34', 'ROLE_USER')
;

insert into dept (id, name)
values ('a948f0ae-83e1-4e78-8a03-beaa504b6513', '영업1팀'),
       ('00e23db5-cdbd-482a-b019-189b93ecfb34', '영업2팀')
;

INSERT INTO users (id, created_at, created_by, updated_at, updated_by, birth, enabled, join_date, name, password, username, dept_id)
VALUES ('a948f0ae-83e1-4e78-8a03-beaa504b6520', '2023-03-24 16:31:16.000000', 'ADMIN', '2023-03-24 16:31:28.000000',
        'ADMIN', '2023-03-24', b'1', '2023-03-24 16:40:13.000000', '임상근', '$2a$10$/S9eH/9t/k4FgX21cZ7yq.xOAp7eOLV.T4kKTJ1rKDpxhbvWv9HYS', 'admin', 'a948f0ae-83e1-4e78-8a03-beaa504b6513');


INSERT INTO user_authorities(id, created_at, created_by, updated_at, updated_by, authority_id, user_id)
values ('a948f0ae-83e1-4e78-8a03-beaa504b6580', '2023-03-24 16:31:16.000000', 'ADMIN', '2023-03-24 16:31:28.000000','ADMIN',
    'a948f0ae-83e1-4e78-8a03-beaa504b6513', 'a948f0ae-83e1-4e78-8a03-beaa504b6520');