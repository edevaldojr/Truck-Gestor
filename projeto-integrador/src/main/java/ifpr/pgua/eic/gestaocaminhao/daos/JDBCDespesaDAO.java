package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.DespesaDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Despesa;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoDespesa;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCDespesaDAO implements DespesaDAO {

    FabricaConexoes fabricaConexoes;

    @Override
    public boolean cadastrar(Despesa d) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_Despesa(tipoDespesa, nome, valorDespesaAutopeça, valorDespesaCombustivel) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, d.getTipoDespesa().getCod());
        pstmt.setString(2, d.getNome());
        pstmt.setDouble(3, d.getValorDespesaAutopeca());
        pstmt.setDouble(4, d.getValorDespesaCombustivel());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Despesa d) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Despesa SET tipoDespesa=?, nome=?, valorDespesaAutopeça=?, valorDespesaCombustivel=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, d.getTipoDespesa().getCod());
        pstmt.setString(2, d.getNome());
        pstmt.setDouble(3, d.getValorDespesaAutopeca());
        pstmt.setDouble(4, d.getValorDespesaCombustivel());
        pstmt.setInt(5, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Despesa SET ativo=0 WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    @Override
    public ArrayList<Despesa> listar() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public Despesa montarEmpresa(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        TipoDespesa tipoDespesa = TipoDespesa.valueOf(rs.getString("tipoDespesa"));
        String nome = rs.getString("nome");
        double valorDespesaAutopeca = rs.getDouble("valorDespesaAutopeca");
        double valorDespesaCombustivel = rs.getDouble("valorDespesaCombustivel");

        Despesa despesa = new Despesa(id, tipoDespesa, nome, valorDespesaAutopeca, valorDespesaCombustivel);

        return despesa;
    }

}
