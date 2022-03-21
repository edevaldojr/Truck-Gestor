package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCUsuarioDAO implements UsuarioDAO {

    FabricaConexoes fabricaConexoes;
    EnderecoDAO enderecoDAO;

    public JDBCUsuarioDAO(FabricaConexoes fabricaConexoes, EnderecoDAO enderecoDAO) {
        this.fabricaConexoes = fabricaConexoes;
        this.enderecoDAO = enderecoDAO;
    }

    @Override
    public boolean cadastrar(Usuario u) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_Usuario(cpf,nome,telefone,email,senha,gestor,cnh,endereco_id) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, u.getCpf());
        pstmt.setString(2, u.getNome());
        pstmt.setString(3, u.getTelefone());
        pstmt.setString(4, u.getEmail());
        pstmt.setString(5, u.getSenha());
        pstmt.setBoolean(6, u.isGestor());
        pstmt.setString(7, u.getCnh());
        pstmt.setInt(8, u.getEndereco().getId());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(String cpf, Usuario u) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Usuario SET nome=?, cidade=?, endereco=?, telefone=?, email=?,  senha=?, gestor=?, cnh=? WHERE cpf=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, u.getNome());
        pstmt.setInt(3, u.getEndereco().getId());
        pstmt.setString(4, u.getTelefone());
        pstmt.setString(5, u.getEmail());
        pstmt.setString(6, u.getSenha());
        pstmt.setBoolean(7, u.isGestor());
        pstmt.setString(8, u.getCnh());
        pstmt.setString(9, cpf);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(String cpf) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Usuario SET ativo=0 WHERE cpf=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cpf);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    public Usuario montarUsuario(ResultSet rs) throws Exception {
        String cpf = rs.getString("cpf");
        String nome = rs.getString("nome");
        String telefone = rs.getString("telefone");
        String email = rs.getString("email");
        String senha = rs.getString("senha");
        String cnh = rs.getString("cnh");
        boolean gestor = rs.getBoolean("gestor");
        int end = rs.getInt("endereco_id");

        Endereco endereco = enderecoDAO.buscar(end);

        Usuario u = new Usuario(cpf, nome, endereco, telefone, email, senha, cnh, gestor);

        return u;

    }

    @Override
    public ArrayList<Usuario> listar() throws Exception {
        ArrayList<Usuario> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Usuario WHERE ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Usuario u = montarUsuario(rs);
            lista.add(u);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Usuario buscar(String cpf) throws Exception {
        Usuario u = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Usuario WHERE cpf=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cpf);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            u = montarUsuario(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return u;
    }

}
