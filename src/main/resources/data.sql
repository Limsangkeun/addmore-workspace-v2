insert into authority (id, name)
values ('a948f0ae-83e1-4e78-8a03-beaa504b6513', 'ROLE_ADMIN'),
       ('00e23db5-cdbd-482a-b019-189b93ecfb34', 'ROLE_USER')
;

insert into dept (id, name, created_by, created_at)
values ('a948f0ae-83e1-4e78-8a03-beaa504b6513', '영업1팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb34', '영업2팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb35', '영업3팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb36', '영업4팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb37', '영업5팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb38', '영업6팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb39', '영업7팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb40', '영업8팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb41', '영업9팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb42', '영업10팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb43', '영업11팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb44', '영업12팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb45', '영업13팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb46', '영업14팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb47', '영업15팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb48', '영업16팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb49', '영업17팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb50', '영업18팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb51', '영업19팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb52', '영업20팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb53', '영업21팀', 'admin', now()),
       ('00e23db5-cdbd-482a-b019-189b93ecfb54', '영업22팀', 'admin', now())
;

INSERT INTO users (id, created_at, created_by, updated_at, updated_by, birth, enabled, join_date, name, password, username, dept_id)
VALUES ('a948f0ae-83e1-4e78-8a03-beaa504b6520', '2023-03-24 16:31:16.000000', 'ADMIN', '2023-03-24 16:31:28.000000',
        'ADMIN', '2023-03-24', b'1', '2023-03-24 16:40:13.000000', '임상근', '$2a$10$/S9eH/9t/k4FgX21cZ7yq.xOAp7eOLV.T4kKTJ1rKDpxhbvWv9HYS', 'admin', 'a948f0ae-83e1-4e78-8a03-beaa504b6513');


INSERT INTO user_authorities(id, created_at, created_by, updated_at, updated_by, authority_id, user_id)
values ('a948f0ae-83e1-4e78-8a03-beaa504b6580', '2023-03-24 16:31:16.000000', 'ADMIN', '2023-03-24 16:31:28.000000','ADMIN',
    'a948f0ae-83e1-4e78-8a03-beaa504b6513', 'a948f0ae-83e1-4e78-8a03-beaa504b6520');