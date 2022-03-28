DROP TABLE IF EXISTS projeto_Usuario;
CREATE TABLE `tads20_edevaldo`.`projeto_Usuario` (
      cpf VARCHAR(14) NOT NULL,
      nome VARCHAR(150) NOT NULL,
      telefone VARCHAR(45) NOT NULL,
      email VARCHAR(150) NOT NULL,
      senha VARCHAR(45) NOT NULL,
      gestor TINYINT NULL,
      cnh VARCHAR(14),
      endereco_id int NOT NULL,
      ativo TINYINT NOT NULL default 1,
      PRIMARY KEY (cpf),
	  FOREIGN KEY (endereco_id) REFERENCES projeto_Endereco(id)
);

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

create table projeto_Estado(
	  id int not null AUTO_INCREMENT,
    nome varchar(45) not null,
    uf varchar(5) not null,
    PRIMARY KEY(id)
);

create table projeto_Cidade(
	  id int not null AUTO_INCREMENT,
    nome varchar(45) not null,
    id_estado int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(id_estado) REFERENCES projeto_Estado(id)
);

drop table if exists projeto_Empresa;
create table projeto_Empresa(
	  id int not null AUTO_INCREMENT,
    nome varchar(100) not null,
    tipo enum ('DESTINO','ORIGEM') not null,
    endereco_id int not null,
    ativo TINYINT not null default 1,
	  PRIMARY KEY(id),
    FOREIGN KEY(endereco_id) REFERENCES projeto_Endereco(id)    
);

drop table if exists projeto_Viagem;
create table projeto_Viagem(
	id int not null AUTO_INCREMENT,
    peso double not null,
    data_da_Baixa date not null,
    valor double not null,
    carga varchar(45) not null,
    empresa_origem_id int not null,
    empresa_destino_id int not null,
    motorista varchar(12) not null,
    total double not null,
    PRIMARY KEY(id),
    FOREIGN KEY(empresa_origem_id) REFERENCES projeto_Empresa(id),
    FOREIGN KEY(empresa_destino_id) REFERENCES projeto_Empresa(id),
    FOREIGN KEY(motorista) references projeto_Usuario(cpf)
);
--Link para povoar cidades e estados
--https://zerobugs.com.br/ver-post/codigo-sql-com-todas-as-cidades-e-estados-do-brasil-62/

drop table if exists projetoEndereco;
create table projeto_Endereco(
	  id int not null AUTO_INCREMENT,
    numero varchar(11) not null,
    rua varchar(45) not null,
    complemento varchar(45) not null,
    bairro varchar(45) not null,
    cep varchar(10) not null,
	  cidade_id int not null,
    PRIMARY KEY(id),
    FOREIGN KEY(cidade_id) REFERENCES projeto_Cidade(id)
);


drop table if exists projeto_Despesa;
create table projeto_Despesa(
	id int not null AUTO_INCREMENT,
    tipoDespesa ENUM('AUTOPECAS', 'COMBUSTIVEL') not null,
    nome varchar(120) not null,
    valorDespesa double,
    dataDespesa date not null,
    PRIMARY KEY(id)
);


-- trigger calcular valor viagem
delimiter $$
drop trigger if exists calcula_entrada$$
create trigger calcula_entrada before insert on projeto_Viagem for each
row
begin
 declare total_entrada double;
	set total_entrada = new.valor * (new.peso * 1000);
    set new.total = total_entrada;
end$$

drop table if exists projeto_EntradasSaídas;
create table projeto_EntradasSaídas(
			entradaTotal double not null,
    		saídaTotal double not null
);
