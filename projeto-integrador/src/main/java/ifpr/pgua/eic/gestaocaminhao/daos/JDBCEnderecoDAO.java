package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCEnderecoDAO implements EnderecoDAO {

    FabricaConexoes fabricaConexoes;
    CidadeDAO cidadeDAO;

    public JDBCEnderecoDAO(FabricaConexoes fabricaConexoes, CidadeDAO cidadeDAO) {
        this.fabricaConexoes = fabricaConexoes;
        this.cidadeDAO = cidadeDAO;
    }

    @Override
    public boolean cadastrar(Endereco e) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_Endereco(numero, rua, complemento, bairro, cep, cidade_id) VALUES (?,?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, e.getNumero());
        pstmt.setString(2, e.getRua());
        pstmt.setString(3, e.getComplemento());
        pstmt.setString(4, e.getBairro());
        pstmt.setString(5, e.getCep());
        pstmt.setInt(6, e.getCidade().getId());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Endereco e) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Endereco SET numero=?, rua=? , complemento=?, bairro=?, cep=?, cidade_id=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, e.getNumero());
        pstmt.setString(2, e.getRua());
        pstmt.setString(3, e.getComplemento());
        pstmt.setString(4, e.getBairro());
        pstmt.setString(5, e.getCep());
        pstmt.setInt(6, e.getCidade().getId());
        pstmt.setInt(7, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Endereco SET ativo=0 WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    public Endereco montarEndereco(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String numero = rs.getString("numero");
        String complemento = rs.getString("complemento");
        String bairro = rs.getString("bairro");
        String rua = rs.getString("rua");
        String cep = rs.getString("cep");
        int cidade_id = rs.getInt("cidade_id");

        Cidade cidade = cidadeDAO.buscarPorId(cidade_id);

        Endereco u = new Endereco(id, numero, complemento, bairro, rua, cep, cidade);

        return u;

    }

    @Override
    public ArrayList<Endereco> listar() throws Exception {
        ArrayList<Endereco> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Endereco WHERE ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Endereco u = montarEndereco(rs);
            lista.add(u);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Endereco buscar(int id) throws Exception {
        Endereco e = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Endereco WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            e = montarEndereco(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return e;
    }

    @Override
    public Endereco buscarPorEnd(String bairro, String rua, String numero) throws Exception {
        Endereco e = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Endereco WHERE bairro=? and rua=? and numero=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, bairro);
        pstmt.setString(2, rua);
        pstmt.setString(3, numero);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            e = montarEndereco(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return e;
    }

}
