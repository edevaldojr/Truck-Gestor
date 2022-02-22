package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public interface UsuarioDAO {

    boolean cadastrar(Usuario u) throws Exception;

    boolean atualizar(int cpf, Usuario u) throws Exception;

    boolean remover(int cpf) throws Exception;

    ArrayList<Usuario> listar() throws Exception;

}
