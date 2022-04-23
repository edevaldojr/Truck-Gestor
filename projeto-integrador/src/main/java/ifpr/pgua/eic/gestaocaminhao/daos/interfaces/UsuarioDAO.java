package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public interface UsuarioDAO {

    boolean cadastrar(Usuario u) throws Exception;

    boolean atualizar(String cpf, Usuario u) throws Exception;

    boolean remover(String cpf) throws Exception;

    ArrayList<Usuario> listar() throws Exception;

    ArrayList<Usuario> listarMotorista() throws Exception;

    ArrayList<Usuario> listarGestor() throws Exception;

    Usuario buscar(String cpf) throws Exception;

    int buscarEnderecoId(String cpf) throws Exception;
}
