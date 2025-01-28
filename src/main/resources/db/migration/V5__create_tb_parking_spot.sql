CREATE TABLE tb_parking_spot(
    id BIGSERIAL PRIMARY KEY,
    cod VARCHAR(4) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    created_date DATE NOT NULL,
    modification_date DATE,
    created_by VARCHAR(60) NOT NULL,
    update_by VARCHAR(60)
)