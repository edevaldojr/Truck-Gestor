package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;

public class HomeGestor {

    private AutenticacaoServico autenticacaoServico;
    private Login login;

    public HomeGestor(Login login, AutenticacaoServico autenticacaoServico) {
        System.out.println("Home Gestor");
        this.autenticacaoServico = autenticacaoServico;
        this.login = login;
    }

    @FXML
    public void logout() {
        autenticacaoServico.logout();
        login.atualizaTela();
    }

}
