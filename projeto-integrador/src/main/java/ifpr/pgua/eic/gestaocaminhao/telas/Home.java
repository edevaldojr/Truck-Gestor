package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Home {

    private AutenticacaoServico autenticacaoServico;

    @FXML
    private StackPane painelCentral;

    @FXML
    private BorderPane root;

    @FXML
    private Button btCadastrar;

    private RepositorioUsuarios repositorioUsuarios;

    public Home(RepositorioUsuarios repositorioUsuarios, AutenticacaoServico autenticacaoServico) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.autenticacaoServico = autenticacaoServico;
    }

    public void initialize() {
        atualizaTela();
    }

    @FXML
    public void logout() {
        autenticacaoServico.logout();
        atualizaTela();
    }

    @FXML
    public void atualizaTela() {
        if (!autenticacaoServico.estaLogado()) {
            root.getLeft().setVisible(false);
            painelCentral.getChildren().clear();
            painelCentral.getChildren()
                    .add(App.loadTela("fxml/login.fxml", a -> new Login(autenticacaoServico, this)));
        } else {
            /*
             * root.getLeft().setVisible(true);
             * if(autenticacaoServico.getLogado().isAdmin()){
             * btCadastrar.setDisable(false);
             * }else{
             * btCadastrar.setDisable(true);
             * }
             */

            // criando homes diferentes para cada tipo de usuario

            if (autenticacaoServico.getLogado().getGestor()) {
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/home_gestor.fxml", a -> new HomeGestor(this)));

            } else {
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/home_moto.fxml", a -> new HomeMoto(this)));

            }

        }

    }

    @FXML
    public void carregaTela(String tela) {
        if (tela.equals("cadastro")) {
            painelCentral.getChildren().clear();
            painelCentral.getChildren().add(
                    App.loadTela("fxml/cadastro_users.fxml", a -> new CadastroUsuario(autenticacaoServico, this)));

        }
    }

}
