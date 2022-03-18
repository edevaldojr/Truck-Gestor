package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Estado;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCCidadeDAO implements CidadeDAO {

    FabricaConexoes fabricaConexoes;
    JDBCEstadoDAO jdbcEstadoDAO;

    public JDBCCidadeDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }


    public Cidade montarCidade(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        int id_estado = rs.getInt("id_estado");
 
        Estado estado = jdbcEstadoDAO.buscar(id_estado);

        Cidade u = new Cidade(id, nome, estado);

        return u;

    }

  

    @Override
    public ArrayList<Cidade> listar() throws Exception {
        ArrayList<Cidade> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Cidade WHERE ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Cidade u = montarCidade(rs);
            lista.add(u);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Cidade buscar(int id) throws Exception {
        Cidade u = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Cidade WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            u = montarCidade(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return u;
    }

    

    

}
