create table register_category
(
    catid          char(6)        not null
        primary key,
    name           char(12)       not null,
    py             char(4)        not null,
    depid          char(6)        not null,
    speciallist    tinyint(1)     not null,
    max_reg_number int            not null,
    reg_fee        decimal(10, 2) not null,
    constraint register_category_ibfk_1
        foreign key (depid) references department (depid)
)
    charset = utf8;

create index depid
    on register_category (depid);

INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('1', '内科专家号', 'NKZJH', '1', 1, 10, 200.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('6', '内科普通号', 'NKPTH', '1', 0, 20, 20.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('2', '外科专家号', 'WKZJH', '2', 1, 5, 150.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('7', '外科普通号', 'WKPTH', '2', 0, 30, 15.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('8', '妇科普通号', 'FKPTH', '3', 0, 40, 10.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('3', '妇科专家号', 'FKZJH', '3', 1, 8, 100.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('9', '皮肤科普通号', 'PFKPTH', '4', 0, 30, 5.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('4', '皮肤科专家号', 'PFKZJH', '4', 1, 10, 50.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('10', '骨科普通号', 'GKPTH', '5', 0, 25, 10.00);
INSERT INTO lab2.register_category (catid, name, py, depid, speciallist, max_reg_number, reg_fee) VALUES ('5', '骨科专家号', 'GKZJH', '5', 1, 7, 100.00);