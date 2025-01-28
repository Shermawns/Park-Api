ALTER TABLE tb_client
ADD created_date DATE NOT NULL,
ADD modification_date DATE,
ADD created_by VARCHAR(60) NOT NULL,
ADD update_by VARCHAR(60)