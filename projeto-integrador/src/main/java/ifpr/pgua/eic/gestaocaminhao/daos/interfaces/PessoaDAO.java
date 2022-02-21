package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Pessoa;

public interface PessoaDAO {

    boolean cadastrar(Pessoa p) throws Exception;

    boolean atualizar(int cpf, Pessoa p) throws Exception;

    boolean remover(int cpf) throws Exception;

    ArrayList<Pessoa> listar() throws Exception;

}