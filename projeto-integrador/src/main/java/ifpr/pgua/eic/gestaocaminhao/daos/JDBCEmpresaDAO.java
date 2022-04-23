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

    public JDBCEmpresaDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrar(Empresa e) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_empresa(nome, tipo, endereco_id) VALUES (?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, e.getNome());
        pstmt.setInt(2, e.getTipo().getCod());
        pstmt.setInt(3, e.getEndereco().getId());

        pstmt.execute();
        pstmt.close();
        con.close();

        return true;
    }

    @Override
    public boolean atualizar(int id, Empresa e) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_empresa SET nome=?, tipo=?, endereco_id=? WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, e.getNome());
        pstmt.setInt(2, e.getTipo().getCod());
        pstmt.setInt(3, e.getEndereco().getId());

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean remover(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_empresa SET ativo=0 WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        con.close();
        pstmt.close();
        return ret == 1;
    }

    @Override
    public ArrayList<Empresa> listarEmpresasOrigem() throws Exception {
        ArrayList<Empresa> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_empresa WHERE ativo=1 and tipo='ORIGEM'";

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

    @Override
    public ArrayList<Empresa> listarEmpresasDestino() throws Exception {
        ArrayList<Empresa> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_empresa WHERE ativo=1 and tipo='DESTINO'";

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
        TipoEmpresa tipo = TipoEmpresa.valueOf(rs.getString("tipo"));

        Empresa empresa = new Empresa(id, nome, null, tipo);

        return empresa;
    }

    @Override
    public Empresa buscar(int id) throws Exception {
        Empresa e = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_empresa WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            e = montarEmpresa(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return e;
    }

    @Override
    public Empresa buscarPorNome(String nome) throws Exception {
        Empresa e = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_empresa WHERE nome=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, nome);

        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            e = montarEmpresa(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return e;
    }

    @Override
    public int buscarEnderecoId(int id) throws Exception {
        int enderecoid = 0;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT endereco_id FROM projeto_empresa WHERE id=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();

        rs.next();

        enderecoid = rs.getInt("endereco_id");

        rs.close();
        pstmt.close();
        con.close();

        return enderecoid;
    }

}
