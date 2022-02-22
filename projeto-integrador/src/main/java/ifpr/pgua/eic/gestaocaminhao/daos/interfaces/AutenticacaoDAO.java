package ifpr.pgua.eic.gestaocaminhao.daos.interfaces;

import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public interface AutenticacaoDAO {

    Usuario login(int loginCPF, String senha) throws Exception;

    boolean cadastrar(int loginCPF, String senha) throws Exception;
}
