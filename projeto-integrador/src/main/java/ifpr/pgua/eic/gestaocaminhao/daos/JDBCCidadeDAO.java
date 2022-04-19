package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Estado;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEstado;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCCidadeDAO implements CidadeDAO {

    FabricaConexoes fabricaConexoes;
    RepositorioEstado repositorioEstado;

    public JDBCCidadeDAO(FabricaConexoes fabricaConexoes, RepositorioEstado repositorioEstado) {
        this.fabricaConexoes = fabricaConexoes;
        this.repositorioEstado = repositorioEstado;
    }

    public Cidade montarCidade(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        int id_estado = rs.getInt("id_estado");

        Estado estado = repositorioEstado.buscarEstado(id_estado);

        Cidade c = new Cidade(id, nome, estado);

        return c;

    }

    @Override
    public ArrayList<Cidade> listar() throws Exception {
        ArrayList<Cidade> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_cidade WHERE ativo=1";

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
    public Cidade buscarPorNome(String nome) throws Exception {
        Cidade c = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_cidade WHERE nome=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, nome);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            c = montarCidade(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return c;
    }

    @Override
    public Cidade buscarPorId(int id) throws Exception {
        Cidade c = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_cidade WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            c = montarCidade(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return c;
    }

}
