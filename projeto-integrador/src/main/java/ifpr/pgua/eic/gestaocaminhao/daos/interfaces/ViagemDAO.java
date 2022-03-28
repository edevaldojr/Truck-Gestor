package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Viagem;

public interface ViagemDAO {
    
    boolean cadastrar(Viagem u) throws Exception;

    boolean atualizar(int id, Viagem u) throws Exception;

    boolean remover(int id) throws Exception;

    ArrayList<Viagem> listar() throws Exception;

    ArrayList<Viagem> listar7dias() throws Exception;

    ArrayList<Viagem> listar14dias() throws Exception;

    ArrayList<Viagem> listar30dias() throws Exception;

    Viagem buscar(int id) throws Exception;

}
