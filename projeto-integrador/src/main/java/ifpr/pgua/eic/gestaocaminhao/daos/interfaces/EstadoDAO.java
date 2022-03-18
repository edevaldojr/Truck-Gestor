package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Estado;

public interface EstadoDAO {

    ArrayList<Estado> listar() throws Exception;

    Estado buscar(int id) throws Exception;
}
