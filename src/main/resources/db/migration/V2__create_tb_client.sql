CREATE TABLE tb_client(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES tb_user(id)
)