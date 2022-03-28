package ifpr.pgua.eic.gestaocaminhao.telas;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Despesa;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCidade;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioDespesas;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEmpresa;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEndereco;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEstado;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioViagens;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class HomeGestor {

    @FXML
    private TableView<Viagem> tbListaEntradas;

    @FXML
    private TableColumn<Viagem, String> tbcDataViagem;

    @FXML
    private TableColumn<Viagem, Double> tbcValorViagem;

    @FXML
    private TableView<Despesa> tbcListaDespesa;

    @FXML
    private TableColumn<Despesa, String> tbcDataDespesa;

    @FXML
    private TableColumn<Despesa, String> tbcTipoDespesa;

    @FXML
    private TableColumn<Despesa, Double> tbcValorDespesa;

    @FXML
    private Button btCadastrarCaminhao;

    @FXML
    private Button btRelatorios;

    @FXML
    private Button btEntradas;

    @FXML
    private Label lbLucro;

    @FXML
    private ProgressIndicator piListarHome;

    @FXML
    private AnchorPane root;

    private boolean tarefaPB=false;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private AutenticacaoServico autenticacaoServico;
    private RepositorioCaminhao repositorioCaminhao;
    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioEndereco repositorioEndereco;
    private RepositorioCidade repositorioCidade;
    private RepositorioEstado repositorioEstado;
    private RepositorioViagens repositorioViagens;
    private RepositorioEmpresa repositorioEmpresa;
    private RepositorioDespesas repositorioDespesas;
    private Login login;

    public HomeGestor(Login login, AutenticacaoServico autenticacaoServico, RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco,
            RepositorioEstado repositorioEstado,
            RepositorioCidade repositorioCidade,
            RepositorioEmpresa repositorioEmpresa,
            RepositorioViagens repositorioViagens, RepositorioDespesas repositorioDespesas) {
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioEndereco = repositorioEndereco;
        this.repositorioCaminhao = repositorioCaminhao;
        this.repositorioCidade = repositorioCidade;
        this.repositorioEstado = repositorioEstado;
        this.repositorioEmpresa = repositorioEmpresa;
        this.repositorioViagens = repositorioViagens;
        this.repositorioDespesas = repositorioDespesas;
        this.login = login;
    }

    public HomeGestor(Login login, AutenticacaoServico autenticacaoServico) {
        this.autenticacaoServico = autenticacaoServico;
        this.login = login;
    }

    public void initialize() {

        tbcDataViagem.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getData_da_baixa().format(formatter)));
        tbcValorViagem
                .setCellValueFactory(valor -> new SimpleDoubleProperty(valor.getValue().getValor_total()).asObject());

        tbcDataDespesa.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getDataDespesa().format(formatter)));
        tbcTipoDespesa
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoDespesa().getDescricao()));
        tbcValorDespesa
                .setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorDespesa()).asObject());
        ;

        try {
            threadListar.setDaemon(true);
            threadListar.start();
            lbLucro.setText("Lucro: ");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    }

    Thread threadListar = new Thread(() -> {
        try {
            tbListaEntradas.getItems().addAll(repositorioViagens.listarViagens());
            tbcListaDespesa.getItems().addAll(repositorioDespesas.listarDespesas());
            piListarHome.setVisible(false);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    });

    @FXML
    public void abrirCadastrarCaminhao() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/cadastro_caminhao.fxml",
                        a -> new CadastroCaminhao(this.login, autenticacaoServico, repositorioUsuarios,
                                repositorioCaminhao, repositorioEndereco, repositorioEstado,
                                repositorioCidade, repositorioEmpresa, repositorioViagens, repositorioDespesas)));
    }

    @FXML
    public void abrirCadastrarEmpresa() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/cadastro_empresa.fxml",
                        a -> new CadastroEmpresa(this.login, autenticacaoServico, repositorioUsuarios,
                                repositorioCaminhao, repositorioEndereco, repositorioEstado,
                                repositorioCidade, repositorioEmpresa, repositorioViagens, repositorioDespesas)));
    }

    @FXML
    public void abrirCadastrarDespesas() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/cadastro_despesas.fxml",
                        a -> new CadastroDespesa(this.login, autenticacaoServico, repositorioUsuarios,
                                repositorioCaminhao, repositorioEndereco, repositorioEstado,
                                repositorioCidade, repositorioEmpresa, repositorioViagens, repositorioDespesas)));
    }

    @FXML
    public void abrirRelatorios() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/relatorios.fxml",
                        a -> new Relatorios(this.login, autenticacaoServico, repositorioUsuarios,
                                repositorioCaminhao, repositorioEndereco, repositorioEstado,
                                repositorioCidade, repositorioEmpresa, repositorioViagens, repositorioDespesas)));
    }

    @FXML
    public void abrirEntradasViagens() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/entradas_viagens.fxml",
                        a -> new EntradasViagens(this.login, autenticacaoServico, repositorioUsuarios,
                                repositorioCaminhao, repositorioEndereco, repositorioEstado,
                                repositorioCidade, repositorioEmpresa, repositorioViagens, repositorioDespesas)));
    }

    @FXML
    public void logout() {
        autenticacaoServico.logout();
        login.atualizaTela();
    }

}
