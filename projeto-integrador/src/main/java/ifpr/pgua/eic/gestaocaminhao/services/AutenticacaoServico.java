package ifpr.pgua.eic.gestaocaminhao.services;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public class AutenticacaoServico {

    private Usuario logado;
    private AutenticacaoDAO autenticacaoDAO;

    public AutenticacaoServico(AutenticacaoDAO autenticacaoDAO) {
        this.autenticacaoDAO = autenticacaoDAO;
    }

    public Usuario logar(int login, String senha) throws Exception {
        this.logado = autenticacaoDAO.login(login, senha);
        return this.logado;
    }

    public void cadastrar(int login, String senha) throws Exception {
        autenticacaoDAO.cadastrar(login, senha);
    }

    public Usuario getLogado() {
        return this.logado;
    }

    public void logout() {
        this.logado = null;
    }

    public boolean estaLogado() {
        return this.logado != null;
    }

}
