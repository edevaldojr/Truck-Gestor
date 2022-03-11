DROP TABLE IF EXISTS projeto_Usuario;
CREATE TABLE `tads20_edevaldo`.`projeto_Usuario` (
  `cpf` VARCHAR(14) NOT NULL,
  `nome` VARCHAR(150) NOT NULL,
  `cidade` VARCHAR(45) NOT NULL,
  `endereco` VARCHAR(150) NOT NULL,
  `telefone` VARCHAR(45) NOT NULL,
  `email` VARCHAR(150) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  `gestor` TINYINT NULL,
  `cnh` VARCHAR(14),
  `ativo` TINYINT NOT NULL default 1,
  PRIMARY KEY (`cpf`));

create table projeto_Caminhao(
	id int not null AUTO_INCREMENT,
    placa varchar(7) not null,
    cor varchar(45) not null,
    ano int not null,
    marca varchar(45) not null,
    modelo varchar(45) not null,
    tipo ENUM('GRANELEIRO', 'CONTAINER') not null,
    ativo TINYINT not null default 1,
    PRIMARY KEY(id)
);
