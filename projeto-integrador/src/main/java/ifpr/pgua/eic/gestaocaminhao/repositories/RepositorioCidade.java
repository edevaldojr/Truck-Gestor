package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EstadoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;

public class RepositorioCidade {

    private ArrayList<Cidade> cidades;
    private CidadeDAO cidadeDAO;
    private EstadoDAO estadoDAO;

    public RepositorioCidade(CidadeDAO cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
        cidades = new ArrayList<>();
    }

    public ArrayList<Cidade> listarCidades() throws Exception {
        cidades = cidadeDAO.listar();
        for (Cidade cidade : cidades) {
            cidade.setEstado(estadoDAO.buscar(cidadeDAO.buscarEstadoId(cidade.getId())));
        }
        return cidades;
    }

    public Cidade buscarCidadePorId(int id) throws SQLException {
        try{
            Cidade cidade = cidadeDAO.buscarPorId(id);
            cidade.setEstado(estadoDAO.buscar(cidadeDAO.buscarEstadoId(cidade.getId())));
            return cidade;
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    public Cidade buscarCidadePorNome(String nome) throws SQLException {
        try{
            Cidade cidade = cidadeDAO.buscarPorNome(nome);
            cidade.setEstado(estadoDAO.buscar(cidadeDAO.buscarEstadoId(cidade.getId())));
            return cidade;
        }catch(Exception e){
            throw new SQLException(e.getMessage());
        }
    }


    

}
