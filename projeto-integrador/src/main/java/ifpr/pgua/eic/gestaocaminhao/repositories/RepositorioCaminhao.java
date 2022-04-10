package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.enums.TipoCaminhao;

public class RepositorioCaminhao {

    private ArrayList<Caminhao> caminhoes;

    private CaminhaoDAO caminhaoDAO;

    public RepositorioCaminhao(CaminhaoDAO caminhaoDAO) {
        this.caminhaoDAO = caminhaoDAO;
        caminhoes = new ArrayList<>();
    }

    public boolean cadastrarCaminhao(String placa, String cor, int ano, String marca, String modelo, TipoCaminhao tipo)
            throws SQLException {
        Caminhao u = new Caminhao(placa, cor, ano, marca, modelo, tipo);

        try {
            caminhaoDAO.cadastrar(u);
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
            return caminhaoDAO.atualizar(id, Caminhao);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerCaminhoes(int id) throws SQLException {
        try {
            return caminhaoDAO.remover(id);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Caminhao> listarCaminhoes() throws Exception {
        return caminhaoDAO.listar();
    }

    public ArrayList<String> listarCaminhoesToString() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        for (Caminhao caminhao : caminhaoDAO.listar()) {
            String nomeCaminhao = caminhao.getModelo();
            lista.add(nomeCaminhao);
        }
        return lista;
    }
    
    public ArrayList<String> listarCaminhoesToStringComTipo() throws Exception {
        ArrayList<String> lista = new ArrayList<>();
        for (Caminhao caminhao : caminhaoDAO.listar()) {
            String nomeCaminhao = caminhao.getModelo() + " (" +  caminhao.getTipo().toString() + ")";
            lista.add(nomeCaminhao);
        }
        return lista;
    }

    public Caminhao buscarPorModelo(String modelo) throws Exception {
        return caminhaoDAO.buscarPorModelo(modelo);
    }
}
