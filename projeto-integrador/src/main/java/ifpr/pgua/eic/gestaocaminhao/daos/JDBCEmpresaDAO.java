package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EmpresaDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoEmpresa;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCEmpresaDAO implements EmpresaDAO {

    FabricaConexoes fabricaConexoes;

    @Override
    public boolean cadastrar(Empresa e) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_Empresa(nome, tipo, endereco_id, ativo) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, e.getNome());
        pstmt.setInt(2, e.getTipo().getCod());
        pstmt.setInt(3, e.getEndereco());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Empresa e) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Caminhao SET nome=?, tipo=?, endereco_id=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, e.getNome());
        pstmt.setInt(2, e.getTipo().getCod());
        pstmt.setInt(3, e.getEndereco());

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Empresa SET ativo=0 WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    @Override
    public ArrayList<Empresa> listar() throws Exception {
        ArrayList<Empresa> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Empresa WHERE ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Empresa u = montarEmpresa(rs);
            lista.add(u);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    public Empresa montarEmpresa(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        int endereco = rs.getInt("endereco_id");
        TipoEmpresa tipo = TipoEmpresa.valueOf(rs.getString("tipo"));

        Empresa empresa = new Empresa(id, nome, endereco, tipo);

        return empresa;
    }

}
