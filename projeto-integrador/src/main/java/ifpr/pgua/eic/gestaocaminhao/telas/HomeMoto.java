package ifpr.pgua.eic.gestaocaminhao.telas;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioDespesas;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioViagens;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

public class HomeMoto {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btEntradas;

    @FXML
    private Button btDespesas;

    @FXML
    private TableColumn<Viagem, String> tbcDataViagem;

    @FXML
    private TableColumn<Viagem, Double> tbcValorViagem;

    @FXML
    private TableColumn<Viagem, String> tbcMotorista;

    private RepositorioViagens repositorioViagens;
    private RepositorioDespesas repositorioDespesas;
    private AutenticacaoServico autenticacaoServico;
    private Login login;

    public HomeMoto(Login login, AutenticacaoServico autenticacaoServico, RepositorioViagens repositorioViagens,
            RepositorioDespesas repositoriodespesas) {
        System.out.println("Home Motorista");
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioViagens = repositorioViagens;
        this.repositorioDespesas = repositoriodespesas;
        this.login = login;
    }

    @FXML
    public void abrirEntradasViagens() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/entradas_viagens.fxml",
                        a -> new EntradasViagens(this.login, autenticacaoServico, repositorioViagens,
                                repositorioDespesas)));
    }

    @FXML
    public void abrirCadastrarDespesas() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/cadastro_despesas.fxml",
                        a -> new CadastroDespesa(this.login, autenticacaoServico, repositorioViagens,
                                repositorioDespesas)));
    }

    @FXML
    public void logout() {
        autenticacaoServico.logout();
        login.atualizaTela();
    }

}
