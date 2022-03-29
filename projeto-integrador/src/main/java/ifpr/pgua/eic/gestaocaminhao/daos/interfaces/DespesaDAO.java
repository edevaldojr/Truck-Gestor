package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Despesa;

public interface DespesaDAO {

    boolean cadastrar(Despesa d) throws Exception;

    boolean atualizar(int id, Despesa d) throws Exception;

    boolean remover(int id) throws Exception;

    ArrayList<Despesa> listar() throws Exception;

    ArrayList<Despesa> listar7dias() throws Exception;

    ArrayList<Despesa> listar14dias() throws Exception;

    ArrayList<Despesa> listar30dias() throws Exception;
}
