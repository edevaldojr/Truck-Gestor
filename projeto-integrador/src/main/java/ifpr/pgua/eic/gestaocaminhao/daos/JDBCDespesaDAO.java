package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.DespesaDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Despesa;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoDespesa;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCDespesaDAO implements DespesaDAO {

    FabricaConexoes fabricaConexoes;

    public JDBCDespesaDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrar(Despesa d) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_despesa(tipoDespesa, nome, valorDespesa, dataDespesa, caminhao_id) VALUES (?,?,?,?, ?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, d.getTipoDespesa().getCod());
        pstmt.setString(2, d.getNome());
        pstmt.setDouble(3, d.getValorDespesa());
        pstmt.setDate(4, Date.valueOf(d.getDataDespesa()));
        pstmt.setInt(5, d.getCaminhaoDespesa().getId());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Despesa d) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_despesa SET tipoDespesa=?, nome=?, valorDespesa=?, dataDespesa=?, caminhao_id=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, d.getTipoDespesa().getCod());
        pstmt.setString(2, d.getNome());
        pstmt.setDouble(3, d.getValorDespesa());
        pstmt.setDate(4, Date.valueOf(d.getDataDespesa()));
        pstmt.setInt(5, d.getCaminhaoDespesa().getId());
        pstmt.setInt(6, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "DELETE projeto_despesa WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    @Override
    public ArrayList<Despesa> listar() throws Exception {
        ArrayList<Despesa> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_despesa ORDER BY dataDespesa DESC";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Despesa u = montarDespesa(rs);
            lista.add(u);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    public Despesa montarDespesa(ResultSet rs) throws Exception {
        DecimalFormat df = new DecimalFormat("###.00");
        int id = rs.getInt("id");
        TipoDespesa tipoDespesa = TipoDespesa.valueOf(rs.getString("tipoDespesa"));
        String nome = rs.getString("nome");
        double valorDespesa = rs.getDouble("valorDespesa");
        LocalDate dataDespesa = rs.getDate("dataDespesa").toLocalDate();
        df.format(valorDespesa);

        Despesa despesa = new Despesa(id, tipoDespesa, nome, valorDespesa, dataDespesa, null);

        return despesa;
    }

    @Override
    public int buscarCaminhaoId(int id) throws Exception {
        int caminhao_id = 0;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT caminhao_id FROM projeto_despesa WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        rs.next();

        caminhao_id = rs.getInt("caminhao_id");

        rs.close();
        pstmt.close();
        con.close();

        return caminhao_id;
    }


    @Override
    public ArrayList<Despesa> listarDias(int dias) throws Exception {
        ArrayList<Despesa> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_despesa WHERE dataDespesa >= curdate() - INTERVAL ? DAY ORDER BY dataDespesa DESC";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, dias);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Despesa v = montarDespesa(rs);
            lista.add(v);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

}
