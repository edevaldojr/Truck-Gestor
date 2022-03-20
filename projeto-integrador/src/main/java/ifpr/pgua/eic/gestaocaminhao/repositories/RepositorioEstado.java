package ifpr.pgua.eic.gestaocaminhao.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EstadoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Estado;

public class RepositorioEstado {

    private ArrayList<Estado> estados;

    private EstadoDAO estadoDAO;

    public RepositorioEstado(EstadoDAO estadoDAO) {
        this.estadoDAO = estadoDAO;
        estados = new ArrayList<>();
    }

    public ArrayList<Estado> listarEstados() throws Exception {
        return estadoDAO.listar();
    }

    public Estado buscarEstado(int id) {
        return this.estados.stream().filter((estado) -> estado.getId() == id).findFirst().orElse(null);
    }

}
