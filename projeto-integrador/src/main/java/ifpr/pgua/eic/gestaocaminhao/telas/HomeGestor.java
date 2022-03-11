package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class HomeGestor {

    private AutenticacaoServico autenticacaoServico;
    private RepositorioCaminhao repositorioCaminhao;
    private Login login;

    @FXML
    private Button btCadastrarCaminhao;

    @FXML
    private Button btRelatorios;

    @FXML
    private Button btEntradas;

    @FXML
    private AnchorPane root;

    public HomeGestor(Login login, AutenticacaoServico autenticacaoServico, RepositorioCaminhao repositorioCaminhao) {
        System.out.println("Home Gestor");
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioCaminhao = repositorioCaminhao;
        this.login = login;
    }

    public HomeGestor(Login login, AutenticacaoServico autenticacaoServico) {
        this.autenticacaoServico = autenticacaoServico;
        this.login = login;
    }

    @FXML
    public void abrirCadastrarCaminhao() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/cadastro_caminhao.fxml",
                        a -> new CadastroCaminhao(this.login, this.autenticacaoServico, this.repositorioCaminhao)));
    }

    @FXML
    public void abrirRelatorios() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/relatorios.fxml",
                        a -> new Relatorios()));
    }

    @FXML
    public void abrirEntradasViagens() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/entradas_viagens.fxml",
                        a -> new Entradas_viagens()));
    }

    @FXML
    public void logout() {
        autenticacaoServico.logout();
        login.atualizaTela();
    }

}
