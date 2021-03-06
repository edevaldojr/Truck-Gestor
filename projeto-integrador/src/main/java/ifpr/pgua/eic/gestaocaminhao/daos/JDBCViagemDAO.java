package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.ViagemDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCViagemDAO implements ViagemDAO {

    FabricaConexoes fabricaConexoes;

    public JDBCViagemDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrar(Viagem v) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_viagem(peso, data_da_Baixa, valor, carga, empresa_origem_id, empresa_destino_id, motorista, caminhao_id) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setDouble(1, v.getPeso());
        pstmt.setDate(2, Date.valueOf(v.getData_da_baixa()));
        pstmt.setDouble(3, v.getValor());
        pstmt.setString(4, v.getcarga());
        pstmt.setInt(5, v.getEmpresa_origem().getId());
        pstmt.setInt(6, v.getEmpresa_destino().getId());
        pstmt.setString(7, v.getCaminhoneiro().getCpf());
        pstmt.setInt(8, v.getCaminhao().getId());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Viagem v) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_viagem SET peso=?, data_da_Baixa=?, valor=?, carga=? empresa_origem_id=?, empresa_destino_id=?, motorista=?, caminao_id=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setDouble(1, v.getPeso());
        pstmt.setDate(2, Date.valueOf(v.getData_da_baixa()));
        pstmt.setDouble(3, v.getValor());
        pstmt.setString(4, v.getcarga());
        pstmt.setInt(5, v.getEmpresa_origem().getId());
        pstmt.setInt(6, v.getEmpresa_destino().getId());
        pstmt.setString(7, v.getCaminhoneiro().getCpf());
        pstmt.setInt(8, v.getCaminhao().getId());
        pstmt.setInt(9, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "DELETE FROM projeto_viagem WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    @Override
    public ArrayList<Viagem> listar() throws Exception {
        ArrayList<Viagem> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL projeto_RetornarViagem()";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Viagem v = montarViagem(rs);
            lista.add(v);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    public Viagem montarViagem(ResultSet rs) throws Exception {
        DecimalFormat df = new DecimalFormat("###.00");
        int id = rs.getInt("id");
        double peso = rs.getDouble("peso");
        Date data = rs.getDate("data_da_Baixa");
        double valor = rs.getDouble("valor");
        String carga = rs.getString("carga");
        double valor_total = rs.getDouble("total");

        LocalDate data_da_baixa = data.toLocalDate();
        df.format(valor);
        df.format(peso);
        df.format(valor_total);

        Viagem u = new Viagem(id, peso, data_da_baixa, valor, null, null, carga, null, null, valor_total);

        return u;
    }

    @Override
    public Viagem buscar(int id) throws Exception {
        Viagem v = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_viagem WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            v = montarViagem(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return v;
    }

    @Override
    public ArrayList<Viagem> listarDias(int dias) throws Exception {
        ArrayList<Viagem> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL projeto_RetornarViagemDias(?)";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, dias);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Viagem v = montarViagem(rs);
            lista.add(v);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public ArrayList<Viagem> listarPorMoto(String cpf) throws Exception {
        ArrayList<Viagem> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL projeto_RetornarViagemPorMotorista(?);";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cpf);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Viagem v = montarViagem(rs);
            lista.add(v);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public ArrayList<Viagem> listarPorMotoEmDias(int dias, String cpf) throws Exception {
        ArrayList<Viagem> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "CALL projeto_RetornarViagemPorDiaEMotorista(?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, cpf);
        pstmt.setInt(2, dias);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Viagem v = montarViagem(rs);
            lista.add(v);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public int buscarEmpresaOrigemId(int id) throws Exception {
        int empresaId = 0;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT empresa_origem_id FROM projeto_viagem WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        rs.next();

        empresaId = rs.getInt("empresa_origem_id");

        rs.close();
        pstmt.close();
        con.close();

        return empresaId;
    }

    @Override
    public int buscarEmpresaDestinoId(int id) throws Exception {
        int empresaId = 0;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT empresa_destino_id FROM projeto_viagem WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();
        
        rs.next();

        empresaId = rs.getInt("empresa_destino_id");

        rs.close();
        pstmt.close();
        con.close();

        return empresaId;
    }

    @Override
    public int buscarCaminhaoId(int id) throws Exception {
        int caminhaoId = 0;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT caminhao_id FROM projeto_viagem WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        rs.next();

        caminhaoId = rs.getInt("caminhao_id");

        rs.close();
        pstmt.close();
        con.close();

        return caminhaoId;
    }

    @Override
    public String buscarMotoristaCpf(int id) throws Exception {
        String motoristaCpf;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT motorista FROM projeto_viagem WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        rs.next();

        motoristaCpf = rs.getString("motorista");

        rs.close();
        pstmt.close();
        con.close();

        return motoristaCpf;
    }

}
