package ifpr.pgua.eic.gestaocaminhao.services;

import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;

public class AutenticacaoServico {

    private Usuario logado;
    private AutenticacaoDAO autenticacaoDAO;
    private EnderecoDAO enderecoDAO;
    private UsuarioDAO usuarioDAO;
    private CidadeDAO cidadeDAO;

    public AutenticacaoServico(AutenticacaoDAO autenticacaoDAO, EnderecoDAO enderecoDAO, UsuarioDAO usuarioDAO,  CidadeDAO cidadeDAO) {
        this.autenticacaoDAO = autenticacaoDAO;
        this.enderecoDAO = enderecoDAO;
        this.usuarioDAO = usuarioDAO;
        this.cidadeDAO = cidadeDAO;
    }

    public Usuario logar(String loginCpf, String senha) throws Exception {
        logado =  autenticacaoDAO.login(loginCpf, senha);
        Endereco endereco = enderecoDAO.buscar(usuarioDAO.buscarEnderecoId(logado.getCpf()));
        Cidade cidade = cidadeDAO.buscarPorId(endereco.getId());
        endereco.setCidade(cidade);
        logado.setEndereco(endereco);
        return logado;
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
