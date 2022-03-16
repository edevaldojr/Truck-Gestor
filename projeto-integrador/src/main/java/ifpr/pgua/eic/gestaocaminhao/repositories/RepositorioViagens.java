package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.ViagemDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;

public class RepositorioViagens {

    private ArrayList<Viagem> viagens;

    private ViagemDAO viagemDAO;

    public RepositorioViagens(ViagemDAO viagemDAO) {
        this.viagemDAO = viagemDAO;
        viagens = new ArrayList<>();
    }

    public boolean cadastrarViagens(double peso, LocalDate data_da_baixa, double valor, int empresa_origem,
            int empresa_destino, String carga) throws SQLException {
        Viagem v = new Viagem(peso, data_da_baixa, valor, empresa_origem, empresa_destino, carga);

        try {
            viagemDAO.cadastrar(v);
            this.viagens.add(v);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarViagens(int id, double peso, LocalDate data_da_baixa, double valor, int empresa_origem,
            int empresa_destino, String carga) throws SQLException {

        Viagem viagem = new Viagem(id, peso, data_da_baixa, valor, empresa_origem, empresa_destino, carga);

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

}