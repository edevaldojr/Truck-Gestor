package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;

public interface CaminhaoDAO {

    boolean cadastrar(Caminhao c) throws Exception;

    boolean atualizar(int id, Caminhao u) throws Exception;

    boolean remover(int id) throws Exception;

    ArrayList<Caminhao> listar() throws Exception;

    Caminhao buscar(int id) throws Exception;

    Caminhao buscarPorModelo(String modelo) throws Exception;

}
