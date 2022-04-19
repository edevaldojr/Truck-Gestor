package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EstadoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Estado;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCEstadoDAO implements EstadoDAO {

    FabricaConexoes fabricaConexoes;

    public JDBCEstadoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    public Estado montarEstado(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String uf = rs.getString("uf");

        Estado u = new Estado(id, nome, uf);

        return u;

    }

    @Override
    public ArrayList<Estado> listar() throws Exception {
        ArrayList<Estado> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_estado WHERE ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Estado e = montarEstado(rs);
            lista.add(e);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Estado buscar(int id) throws Exception {
        Estado e = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_estado WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            e = montarEstado(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return e;
    }

}
