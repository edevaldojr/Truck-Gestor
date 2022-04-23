package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Endereco;

public interface EnderecoDAO {

    boolean cadastrar(Endereco e) throws Exception;

    boolean atualizar(int id, Endereco e) throws Exception;

    boolean remover(int id) throws Exception;

    ArrayList<Endereco> listar() throws Exception;

    Endereco buscar(int id) throws Exception;

    Endereco buscarPorEnd(String bairro, String rua, String numero) throws Exception;

    int buscarCidadeId(int id) throws Exception;

}
