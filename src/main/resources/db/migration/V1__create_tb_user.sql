CREATE TABLE tb_user(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(250) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_date DATE NOT NULL,
    modification_date DATE,
    created_by VARCHAR(255) NOT NULL,
    update_by VARCHAR(255)
)