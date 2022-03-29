package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.ViagemDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;

public class RepositorioViagens {

    private ArrayList<Viagem> viagens;

    private ViagemDAO viagemDAO;

    public RepositorioViagens(ViagemDAO viagemDAO) {
        this.viagemDAO = viagemDAO;
        viagens = new ArrayList<>();
    }

    public boolean cadastrarViagens(double peso, LocalDate data_da_baixa, double valor, Empresa empresa_origem,
            Empresa empresa_destino, String carga, Usuario caminhoneiro) throws SQLException {
        Viagem v = new Viagem(peso, data_da_baixa, valor, empresa_origem, empresa_destino, carga, caminhoneiro);

        try {
            viagemDAO.cadastrar(v);
            this.viagens.add(v);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarViagens(int id, double peso, LocalDate data_da_baixa, double valor, Empresa empresa_origem,
            Empresa empresa_destino, String carga, Usuario caminhoneiro, double valor_total) throws SQLException {

        Viagem viagem = new Viagem(id, peso, data_da_baixa, valor, empresa_origem, empresa_destino, carga,
                caminhoneiro, valor_total);

        try {
            return viagemDAO.atualizar(id, viagem);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerViagem(int id) throws SQLException {
        try {
            return viagemDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Viagem> listarViagens() throws Exception {
        return viagemDAO.listar();
    }

    public ArrayList<Viagem> listarViagens7dias() throws Exception {
        return viagemDAO.listar7dias();
    }

    public ArrayList<Viagem> listarViagens14dias() throws Exception {
        return viagemDAO.listar14dias();
    }

    public ArrayList<Viagem> listarViagens30dias() throws Exception {
        return viagemDAO.listar30dias();
    }

}
