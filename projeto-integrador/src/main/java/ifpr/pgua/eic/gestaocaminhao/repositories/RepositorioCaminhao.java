package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoCaminhao;

public class RepositorioCaminhao {

    private ArrayList<Caminhao> caminhoes;

    private CaminhaoDAO CaminhaoDAO;

    public RepositorioCaminhao(CaminhaoDAO caminhaoDAO) {
        this.CaminhaoDAO = caminhaoDAO;
        caminhoes = new ArrayList<>();
    }

    public boolean cadastrarCaminhao(String placa, String cor, int ano, String marca, String modelo, TipoCaminhao tipo)
            throws SQLException {
        Caminhao u = new Caminhao(placa, cor, ano, marca, modelo, tipo);

        try {
            CaminhaoDAO.cadastrar(u);
            this.caminhoes.add(u);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

    }

    public boolean atualizarCaminhao(int id, String placa, String cor, int ano, String marca, String modelo,
            TipoCaminhao tipo) throws SQLException {

        Caminhao Caminhao = new Caminhao(id, placa, cor, ano, marca, modelo, tipo);

        try {
            return CaminhaoDAO.atualizar(id, Caminhao);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerCaminhoes(int id) throws SQLException {
        try {
            return CaminhaoDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Caminhao> listarCaminhoes() throws Exception {
        return CaminhaoDAO.listar();
    }

}
