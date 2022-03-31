package ifpr.pgua.eic.gestaocaminhao.telas;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private TableColumn<Viagem, String> tbcValorViagem;

    @FXML
    private TableView<Despesa> tbListaDespesa;

    @FXML
    private TableColumn<Despesa, String> tbcDataDespesa;

    @FXML
    private TableColumn<Viagem, String> tbcMotorista;

    @FXML
    private TableColumn<Despesa, String> tbcTipoDespesa;

    @FXML
    private TableColumn<Despesa, String> tbcValorDespesa;

    ObservableList<String> options = FXCollections.observableArrayList(
            "Todas",
            "7 dias",
            "14 dias",
            "30 dias");
    @FXML
    private ComboBox<String> cbDataRelatorios = new ComboBox<String>(options);

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
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValor_total_ToString()));

        tbcMotorista.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCaminhoneiro().getNome()));

        tbcDataDespesa.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getDataDespesa().format(formatter)));
        tbcTipoDespesa
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTipoDespesa().getDescricao()));
        tbcValorDespesa
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValorDespesaToString()));
        
        cbDataRelatorios.getItems().clear();
        cbDataRelatorios.getItems().addAll(options);

        try {
            threadListar.setDaemon(true);
            threadListar.start();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    }

    Thread threadListar = new Thread(() -> {
        try {
            piListarHome.setVisible(true);
            tbListaEntradas.getItems().clear();
            tbListaEntradas.getItems().addAll(repositorioViagens.listarViagens());
            tbListaDespesa.getItems().clear();
            tbListaDespesa.getItems().addAll(repositorioDespesas.listarDespesas());
            Platform.runLater(() -> {
                piListarHome.setVisible(false);
                lbLucro.setText("Lucro: " + calculoLucro());
            });
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    });

    Thread threadListar7dias = new Thread(() -> {
        try {
            piListarHome.setVisible(true);
            tbListaEntradas.getItems().clear();
            tbListaEntradas.getItems().addAll(repositorioViagens.listarViagensDias(7));
            tbListaDespesa.getItems().clear();
            tbListaDespesa.getItems().addAll(repositorioDespesas.listarDespesasDias(7));
            Platform.runLater(() -> {
                piListarHome.setVisible(false);
                lbLucro.setText("Lucro: " + calculoLucro());
            });
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    });

    Thread threadListar14dias = new Thread(() -> {
        try {
            piListarHome.setVisible(true);
            tbListaEntradas.getItems().clear();
            tbListaEntradas.getItems().addAll(repositorioViagens.listarViagensDias(14));
            tbListaDespesa.getItems().clear();
            tbListaDespesa.getItems().addAll(repositorioDespesas.listarDespesasDias(14));
            Platform.runLater(() -> {
                piListarHome.setVisible(false);
                lbLucro.setText("Lucro: " + calculoLucro());
            });
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    });

    Thread threadListar30dias = new Thread(() -> {
        try {
            piListarHome.setVisible(true);
            tbListaEntradas.getItems().clear();
            tbListaEntradas.getItems().addAll(repositorioViagens.listarViagensDias(30));
            tbListaDespesa.getItems().clear();
            tbListaDespesa.getItems().addAll(repositorioDespesas.listarDespesasDias(30));
            Platform.runLater(() -> {
                piListarHome.setVisible(false);
                lbLucro.setText("Lucro: " + calculoLucro());
            });
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    });

    private String calculoLucro() {
        Double soma = 0.0;
        Double resultadoEntrada = 0.0;
        Double resultadoSaida = 0.0;
        Double resultado = 0.0;
        List<Viagem> listaEntrada = tbListaEntradas.getItems();
        for (Viagem v : listaEntrada) {
            Double valorTabela = v.getValor_total();
            soma += valorTabela;
            resultadoEntrada = soma;
        }
        soma = 0.0;
        List<Despesa> listaSaida = tbListaDespesa.getItems();
        for (Despesa d : listaSaida) {
            Double valorTabela = d.getValorDespesa();
            soma += valorTabela;
            resultadoSaida = soma;
        }
        
        resultado = resultadoEntrada - resultadoSaida - (resultadoEntrada * 0.2);
        System.out.println("a soma é: " + resultado);
        String lucro = String.format("R$%.2f", resultado);
        System.out.println("a soma é: " + lucro);
        return lucro;
    }

    @FXML
    public void buscar() {
        String opcao0 = options.get(0);
        String opcao1 = options.get(1);
        String opcao2 = options.get(2);
        String opcao3 = options.get(3);
        String dataSelecionada = cbDataRelatorios.getSelectionModel().getSelectedItem();
        if (dataSelecionada != null && dataSelecionada == opcao0) {
            threadListar.setDaemon(true);
            threadListar.start();
        } else if (dataSelecionada != null && dataSelecionada == opcao1) {
            threadListar7dias.setDaemon(true);
            threadListar7dias.start();
        } else if (dataSelecionada != null && dataSelecionada == opcao2) {
            threadListar14dias.setDaemon(true);
            threadListar14dias.start();
        } else if (dataSelecionada != null && dataSelecionada == opcao3) {
            threadListar30dias.setDaemon(true);
            threadListar30dias.start();
        }

    }

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
