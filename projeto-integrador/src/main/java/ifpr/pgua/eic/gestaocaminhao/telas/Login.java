package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class Login {

    @FXML
    private TextField tfCpf;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Button btLogin;

    @FXML
    private Button btCadastrar;

    @FXML
    private AnchorPane root;

    private AutenticacaoServico autenticacaoServico;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioCaminhao repositorioCaminhao;

    public Login(AutenticacaoServico autenticacaoServico, RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao) {
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioCaminhao = repositorioCaminhao;
    }

    @FXML
    public void logar() {
        String cpf = tfCpf.getText();
        String senha = pfSenha.getText();
        boolean temErro = false;
        try {
            autenticacaoServico.logar(cpf, senha);
            if (autenticacaoServico.estaLogado()) {
                Alert alerta = new Alert(temErro ? AlertType.CONFIRMATION : AlertType.INFORMATION,
                        "Login realizado com sucesso!");
                alerta.showAndWait();
                atualizaTela();
            }
        } catch (Exception e) {
            temErro = true;
            Alert alerta = new Alert(temErro ? AlertType.ERROR : AlertType.INFORMATION,
                    "Cpf ou senha incorretos!");
            alerta.showAndWait();
            e.getCause();
        }
    }

    @FXML
    public void cadastrar() {
        root.getChildren().clear();
        root.getChildren().add(
                App.loadTela("fxml/cadastro_users.fxml",
                        a -> new CadastroUsuario(autenticacaoServico, repositorioUsuarios, repositorioCaminhao)));
    }

    @FXML
    public void atualizaTela() {
        if (!autenticacaoServico.estaLogado()) {
            root.getChildren().clear();
            root.getChildren()
                    .add(App.loadTela("fxml/login.fxml",
                            a -> new Login(autenticacaoServico, repositorioUsuarios, repositorioCaminhao)));
        } else {

            if (autenticacaoServico.getLogado().isGestor()) {
                root.getChildren().clear();
                root.getChildren().add(App.loadTela("fxml/home_gestor.fxml",
                        a -> new HomeGestor(this, autenticacaoServico, repositorioCaminhao)));

            } else {
                root.getChildren().clear();
                root.getChildren().add(App.loadTela("fxml/home_moto.fxml",
                        a -> new HomeMoto(this, autenticacaoServico)));

            }

        }

    }

}
