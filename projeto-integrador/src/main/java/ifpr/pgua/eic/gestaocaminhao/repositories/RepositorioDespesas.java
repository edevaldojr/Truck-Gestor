package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Despesa;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoDespesa;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.DespesaDAO;

public class RepositorioDespesas {

    private ArrayList<Despesa> despesas;

    private DespesaDAO despesaDAO;

    public RepositorioDespesas(DespesaDAO despesaDAO) {
        this.despesaDAO = despesaDAO;
        despesas = new ArrayList<>();
    }

    public boolean cadastrarDespesa(TipoDespesa tipoDespesa, String nome, double valorDespesa,
            LocalDate dataDespesa) throws SQLException {
        Despesa d = new Despesa(tipoDespesa, nome, valorDespesa, dataDespesa);

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
            LocalDate dataDespesa) throws SQLException {

        Despesa Despesa = new Despesa(tipoDespesa, nome, valorDespesa, dataDespesa);

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
        return despesaDAO.listar();
    }

    public ArrayList<Despesa> listarDespesas7dias() throws Exception {
        return despesaDAO.listarDias(7);
    }

    public ArrayList<Despesa> listarDespesas14dias() throws Exception {
        return despesaDAO.listarDias(14);
    }

    public ArrayList<Despesa> listarDespesas30dias() throws Exception {
        return despesaDAO.listarDias(30);
    }


}
