package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public class RepositorioUsuarios {

    private ArrayList<Usuario> usuarios;

    private UsuarioDAO usuarioDAO;
    private EnderecoDAO enderecoDAO;
    private CidadeDAO cidadeDAO;

    public RepositorioUsuarios(UsuarioDAO usuarioDAO, EnderecoDAO enderecoDAO, CidadeDAO cidadeDAO) {
        this.usuarioDAO = usuarioDAO;
        this.enderecoDAO = enderecoDAO;
        this.cidadeDAO = cidadeDAO;
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
        usuarios = usuarioDAO.listar();
        for (Usuario usuario : usuarios) {
            Endereco endereco = enderecoDAO.buscar(usuarioDAO.buscarEnderecoId(usuario.getCpf()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            usuario.setEndereco(endereco);
        }
        return usuarios;
    }

    public ArrayList<Usuario> listarMotoristas() throws Exception {
        usuarios = usuarioDAO.listarMotorista();
        for (Usuario usuario : usuarios) {
            Endereco endereco = enderecoDAO.buscar(usuarioDAO.buscarEnderecoId(usuario.getCpf()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            usuario.setEndereco(endereco);
        }
        return usuarios;
    }

    public ArrayList<Usuario> listarGestores() throws Exception {
        usuarios = usuarioDAO.listarGestor();
        for (Usuario usuario : usuarios) {
            Endereco endereco = enderecoDAO.buscar(usuarioDAO.buscarEnderecoId(usuario.getCpf()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            usuario.setEndereco(endereco);
        }
        return usuarios;
    }

    public Usuario buscar(String cpf) throws SQLException {
        try {
            Usuario usuario = usuarioDAO.buscar(cpf);
            Endereco endereco = enderecoDAO.buscar(usuarioDAO.buscarEnderecoId(usuario.getCpf()));
            Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
            endereco.setCidade(cidade);
            usuario.setEndereco(endereco);
            return usuario;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
}
