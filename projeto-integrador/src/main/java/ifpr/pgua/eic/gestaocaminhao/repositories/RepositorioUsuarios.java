package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public class RepositorioUsuarios {

    private ArrayList<Usuario> usuarios;

    private UsuarioDAO usuarioDAO;

    public RepositorioUsuarios(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        usuarios = new ArrayList<>();
    }

    public boolean cadastrarUsuario(String cpf, String nome, Endereco endereco, String telefone, String email,
            String senha, String cnh, boolean gestor) throws SQLException {
        Usuario u = new Usuario(cpf, nome, endereco, telefone, email, senha, cnh, gestor);

        try {
            usuarioDAO.cadastrar(u);
            this.usuarios.add(u);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarUsuarios(String cpf, String nome, Endereco endereco, String email, String senha,
            String telefone, String cnh, boolean gestor) throws SQLException {

        Usuario usuario = new Usuario(cpf, nome, endereco, email, senha, telefone, cnh, gestor);

        try {
            return usuarioDAO.atualizar(cpf, usuario);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerUsuarios(String cpf) throws SQLException {
        try {
            return usuarioDAO.remover(cpf);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Usuario> listarUsuarios() throws Exception {
        return usuarioDAO.listar();
    }

    public Usuario buscar(String cpf) throws SQLException {
        try {
            return usuarioDAO.buscar(cpf);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

}
