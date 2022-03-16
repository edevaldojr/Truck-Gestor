package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoCaminhao;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCCaminhaoDAO implements CaminhaoDAO {

    FabricaConexoes fabricaConexoes;

    public JDBCCaminhaoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrar(Caminhao c) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_Caminhao(placa, cor, ano, marca, modelo, tipo) VALUES (?,?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, c.getPlaca());
        pstmt.setString(2, c.getCor());
        pstmt.setInt(3, c.getAno());
        pstmt.setString(4, c.getMarca());
        pstmt.setString(5, c.getModelo());
        pstmt.setInt(6, c.getTipo().getCod());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Caminhao c) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Caminhao SET placa=?, cor=?, ano=?, marca=?, modelo=?,  tipo=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, c.getPlaca());
        pstmt.setString(2, c.getCor());
        pstmt.setInt(3, c.getAno());
        pstmt.setString(4, c.getMarca());
        pstmt.setString(5, c.getModelo());
        pstmt.setString(6, c.getTipo().toString());
        pstmt.setInt(7, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Caminhao SET ativo=0 WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    public Caminhao montarCaminhao(ResultSet rs) throws Exception {

        int id = rs.getInt("id");
        String placa = rs.getString("placa");
        String cor = rs.getString("cor");
        int ano = rs.getInt("ano");
        String marca = rs.getString("marca");
        String modelo = rs.getString("modelo");
        TipoCaminhao tipo = TipoCaminhao.valueOf(rs.getString("tipo"));

        Caminhao u = new Caminhao(id, placa, cor, ano, marca, modelo, tipo);

        return u;

    }

    @Override
    public ArrayList<Caminhao> listar() throws Exception {
        ArrayList<Caminhao> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Caminhao WHERE ativo=1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Caminhao u = montarCaminhao(rs);
            lista.add(u);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

}