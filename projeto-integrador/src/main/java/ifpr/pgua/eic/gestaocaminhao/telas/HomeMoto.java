package ifpr.pgua.eic.gestaocaminhao.telas;

import java.time.format.DateTimeFormatter;
import java.util.List;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Viagem;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioDespesas;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEmpresa;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioViagens;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class HomeMoto {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btEntradas;

    @FXML
    private Button btDespesas;

    @FXML
    private Button btBuscar;

    @FXML
    private Label lbValorAReceber;

    @FXML
    private TableColumn<Viagem, String> tbcDataViagem;

    @FXML
    private TableColumn<Viagem, String> tbcValorViagem;

    @FXML
    private TableColumn<Viagem, String> tbcMotorista;

    @FXML
    private TableView<Viagem> tbListaEntradas;

    ObservableList<String> options = FXCollections.observableArrayList(
            "Todas",
            "7 dias",
            "14 dias",
            "30 dias");
    @FXML
    private ComboBox<String> cbDataRelatorios = new ComboBox<String>(options);

    @FXML
    private ProgressIndicator piListarHomeMoto;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private RepositorioViagens repositorioViagens;
    private RepositorioDespesas repositorioDespesas;
    private RepositorioEmpresa repositorioEmpresa;
    private AutenticacaoServico autenticacaoServico;
    private RepositorioCaminhao repositorioCaminhao;
    private Login login;

    public HomeMoto(Login login, AutenticacaoServico autenticacaoServico, RepositorioViagens repositorioViagens,
            RepositorioDespesas repositoriodespesas, RepositorioEmpresa repositorioEmpresa, RepositorioCaminhao repositorioCaminhao) {
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioViagens = repositorioViagens;
        this.repositorioDespesas = repositoriodespesas;
        this.repositorioEmpresa = repositorioEmpresa;
        this.repositorioCaminhao = repositorioCaminhao;
        this.login = login;
    }

    public void initialize() {

        tbcDataViagem.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getData_da_baixa().format(formatter)));
        tbcValorViagem
                .setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValor_pagar_motoristaToString()));

        tbcMotorista.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCaminhoneiro().getNome()));

        cbDataRelatorios.getItems().clear();
        cbDataRelatorios.getItems().addAll(options);

        try {
            criaThreadListar().start();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    }

    private Thread criaThreadListar(){ 
        return new Thread(() -> {
            try {
                piListarHomeMoto.setVisible(true);
                tbListaEntradas.getItems().clear();
                tbListaEntradas.getItems()
                        .addAll(repositorioViagens.listarViagensMoto(autenticacaoServico.getLogado().getCpf()));
                Platform.runLater(() -> {
                    piListarHomeMoto.setVisible(false);
                    lbValorAReceber.setText("Valor a receber: " + calculoValorAReceber());
                });
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

    private Thread criaThreadListarDias(int dias){
        return new Thread(() -> {
            try {
                piListarHomeMoto.setVisible(true);
                tbListaEntradas.getItems().clear();
                tbListaEntradas.getItems()
                        .addAll(repositorioViagens.listarViagensMotoDias(dias, autenticacaoServico.getLogado().getCpf()));
                Platform.runLater(() -> {
                    piListarHomeMoto.setVisible(false);
                    lbValorAReceber.setText("Valor a receber: " + calculoValorAReceber());
                });
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
    }

    private String calculoValorAReceber() {
        Double soma = 0.0;
        Double resultadoEntrada = 0.0;
        Double resultado = 0.0;
        List<Viagem> listaEntrada = tbListaEntradas.getItems();
        for (Viagem v : listaEntrada) {
            Double valorTabela = v.getValor_total();
            soma += valorTabela;
            resultadoEntrada = soma;
        }
        resultado = resultadoEntrada * 0.20;
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
            criaThreadListar().start();;
        } else if (dataSelecionada != null && dataSelecionada == opcao1) {
            criaThreadListarDias(7).start();
        } else if (dataSelecionada != null && dataSelecionada == opcao2) {
            criaThreadListarDias(14).start();
        } else if (dataSelecionada != null && dataSelecionada == opcao3) {
            criaThreadListarDias(30).start();
        }

    }

    @FXML
    public void abrirEntradasViagens() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/entradas_viagens.fxml",
                        a -> new EntradasViagens(this.login, autenticacaoServico, repositorioViagens,
                                repositorioDespesas, repositorioEmpresa, repositorioCaminhao)));
    }

    @FXML
    public void abrirCadastrarDespesas() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/cadastro_despesas.fxml",
                        a -> new CadastroDespesa(this.login, autenticacaoServico, repositorioViagens,
                                repositorioDespesas, repositorioCaminhao)));
    }

    @FXML
    public void logout() {
        autenticacaoServico.logout();
        login.atualizaTela();
    }

}
