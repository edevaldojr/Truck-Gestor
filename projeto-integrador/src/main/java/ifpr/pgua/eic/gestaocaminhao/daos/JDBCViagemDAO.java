package ifpr.pgua.eic.gestaocaminhao.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.ViagemDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

public class JDBCViagemDAO implements ViagemDAO{

    FabricaConexoes fabricaConexoes;
    JDBCUsuarioDAO jdbcUsuarioDAO;
    JDBCEmpresaDAO jdbcEmpresaDAO;


    public JDBCViagemDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrar(Viagem v) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_Viagem(peso, data_da_baixa, valor, carga, empresa_origem, empresa_destino) VALUES (?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setDouble(1, v.getPeso());
        pstmt.setDate(2, Date.valueOf(v.getData_da_baixa()));
        pstmt.setDouble(3, v.getValor());
        pstmt.setString(4, v.getcarga());
        pstmt.setInt(5, v.getEmpresa_origem().getId());
        pstmt.setInt(6, v.getEmpresa_destino().getId());
        pstmt.setString(7, v.getCaminhoneiro().getCpf());
        pstmt.setInt(8, v.getId());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Viagem v) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Viagem SET peso=?, data_da_baixa=?, valor=?, carga=? empresa_origem=?, empresa_destino=?, motorista=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setDouble(1, v.getPeso());
        pstmt.setDate(2, Date.valueOf(v.getData_da_baixa()));
        pstmt.setDouble(3, v.getValor());
        pstmt.setString(4, v.getcarga());
        pstmt.setInt(5, v.getEmpresa_origem().getId());
        pstmt.setInt(6, v.getEmpresa_destino().getId());
        pstmt.setString(7, v.getCaminhoneiro().getCpf());
        pstmt.setInt(8, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_Viagem SET ativo=0 WHERE id=?";

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

        String sql = "SELECT * FROM projeto_Viagem WHERE ativo=1";

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
        int id = rs.getInt("id");
        double peso = rs.getDouble("peso");
        Date data = rs.getDate("data_da_baixa");
        double valor = rs.getDouble("valor");
        String carga = rs.getString("carga");
        int empresa_origem = rs.getInt("empresa_origem");
        int empresa_destino = rs.getInt("empresa_origem");
        String moto = rs.getString("motorista");

        Empresa origem = jdbcEmpresaDAO.buscar(empresa_origem);
        Empresa destino = jdbcEmpresaDAO.buscar(empresa_destino);
        Usuario motorista = jdbcUsuarioDAO.buscar(moto);
        LocalDate data_da_baixa = data.toLocalDate();


        Viagem u = new Viagem(id,peso, data_da_baixa, valor, origem, destino, carga, motorista);

        return u;
    }

    @Override
    public Viagem buscar(int id) throws Exception {
        Viagem v = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_Viagem WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            v = montarViagem(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return v;
    }
    
}
