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
