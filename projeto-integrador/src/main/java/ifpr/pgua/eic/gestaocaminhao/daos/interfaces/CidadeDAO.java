package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import java.util.ArrayList;

import ifpr.pgua.eic.gestaocaminhao.models.Cidade;

public interface CidadeDAO {
    
    ArrayList<Cidade> listar() throws Exception;

    Cidade buscar(int id) throws Exception;

}
