package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCAutenticacaoDAO implements AutenticacaoDAO {

    FabricaConexoes fabricaConexoes;

    public JDBCAutenticacaoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public Usuario login(String loginCPF, String senha) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * from projeto_Usuario WHERE cpf=? AND senha=? AND ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, loginCPF);
        pstmt.setString(2, senha);

        ResultSet rs = pstmt.executeQuery(sql);

        Usuario u = montarUsuario(rs);
        String login = u.getCpf();
        String password = u.getSenha();

        pstmt.close();
        con.close();
        rs.close();

        if (loginCPF.equals(login) && senha.equals(password)) {
            return u;
        } else {
            return null;
        }
    }

    public Usuario montarUsuario(ResultSet rs) throws Exception {
        String cpf = rs.getString("cpf");
        String nome = rs.getString("nome");
        String cidade = rs.getString("cidade");
        String endereco = rs.getString("endereco");
        String email = rs.getString("email");
        String senha = rs.getString("senha");
        String telefone = rs.getString("telefone");
        String cnh = rs.getString("cnh");
        boolean gestor = rs.getBoolean("gestor");

        Usuario u = new Usuario(cpf, nome, cidade, endereco, email, senha, telefone, cnh, gestor);

        return u;

    }

    @Override
    public boolean cadastrar(String loginCPF, String senha) throws Exception {
        // TODO Auto-generated method stub
        return false;
    }

}
