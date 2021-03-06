package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Viagem;

public interface ViagemDAO {

    boolean cadastrar(Viagem u) throws Exception;

    boolean atualizar(int id, Viagem u) throws Exception;

    boolean remover(int id) throws Exception;

    ArrayList<Viagem> listar() throws Exception;

    ArrayList<Viagem> listarDias(int dias) throws Exception;

    ArrayList<Viagem> listarPorMoto(String cpf) throws Exception;

    ArrayList<Viagem> listarPorMotoEmDias(int dias, String cpf) throws Exception;

    Viagem buscar(int id) throws Exception;

    int buscarEmpresaDestinoId(int id) throws Exception;

    int buscarEmpresaOrigemId(int id) throws Exception;

    int buscarCaminhaoId(int id) throws Exception;

    String buscarMotoristaCpf(int id) throws Exception;

}
