package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Empresa;

public interface EmpresaDAO {

    boolean cadastrar(Empresa e) throws Exception;

    boolean atualizar(int id, Empresa e) throws Exception;

    boolean remover(int id) throws Exception;

    ArrayList<String> listarEmpresasOrigem() throws Exception;

    ArrayList<String> listarEmpresasDestino() throws Exception;

    Empresa buscar(int id) throws Exception;

    Empresa buscarPorNome(String nome) throws Exception;

}
