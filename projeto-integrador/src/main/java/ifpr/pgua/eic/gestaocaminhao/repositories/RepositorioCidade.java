package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;

public class RepositorioCidade {

    private ArrayList<Cidade> cidades;

    private CidadeDAO cidadeDAO;

    public RepositorioCidade(CidadeDAO cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
        cidades = new ArrayList<>();
    }

    public ArrayList<Cidade> listarCidades() throws Exception {
        return cidadeDAO.listar();
    }

    public Cidade buscarCidadePorId(int id) throws SQLException {
        try{
            return cidadeDAO.buscarPorId(id);
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    public Cidade buscarCidadePorNome(String nome) throws SQLException {
        try{
            return cidadeDAO.buscarPorNome(nome);
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }

}
