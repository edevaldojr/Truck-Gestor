package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.Despesa;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoDespesa;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.DespesaDAO;

public class RepositorioDespesas {

    private ArrayList<Despesa> despesas;

    private DespesaDAO despesaDAO;
    private CaminhaoDAO caminhaoDAO;

    public RepositorioDespesas(DespesaDAO despesaDAO, CaminhaoDAO caminhaoDAO) {
        this.despesaDAO = despesaDAO;
        this.caminhaoDAO = caminhaoDAO;
        despesas = new ArrayList<>();
    }

    public boolean cadastrarDespesa(TipoDespesa tipoDespesa, String nome, double valorDespesa,
            LocalDate dataDespesa, Caminhao caminhao) throws SQLException {
        Despesa d = new Despesa(tipoDespesa, nome, valorDespesa, dataDespesa, caminhao);

        try {
            despesaDAO.cadastrar(d);
            this.despesas.add(d);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarDespesa(int id, TipoDespesa tipoDespesa, String nome, double valorDespesa,
            LocalDate dataDespesa, Caminhao caminhao) throws SQLException {

        Despesa Despesa = new Despesa(tipoDespesa, nome, valorDespesa, dataDespesa, caminhao);

        try {
            return despesaDAO.atualizar(id, Despesa);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerDespesas(int id) throws SQLException {
        try {
            return despesaDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Despesa> listarDespesas() throws Exception {
        despesas = despesaDAO.listar();
        for (Despesa despesa : despesas) {
            despesa.setCaminhaoDespesa(caminhaoDAO.buscar(despesaDAO.buscarCaminhaoId(despesa.getId())));
        }
        return despesas;
    }

    public ArrayList<Despesa> listarDespesasDias(int dias) throws Exception {
        despesas = despesaDAO.listarDias(dias);
        for (Despesa despesa : despesas) {
            despesa.setCaminhaoDespesa(caminhaoDAO.buscar(despesaDAO.buscarCaminhaoId(despesa.getId())));
        }
        return despesas;
    }


}
