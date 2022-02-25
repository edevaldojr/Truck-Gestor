package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public interface AutenticacaoDAO {

    Usuario login(String loginCPF, String senha) throws Exception;

    boolean cadastrar(String loginCPF, String senha) throws Exception;
}
