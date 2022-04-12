package ifpr.pgua.eic.gestaocaminhao.telas;

import java.util.List;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.models.Caminhao;
import ifpr.pgua.eic.gestaocaminhao.models.Empresa;
import ifpr.pgua.eic.gestaocaminhao.models.Usuario;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCidade;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioDespesas;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEmpresa;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEndereco;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEstado;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioViagens;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class Relatorios {

    @FXML
    private ListView<Caminhao> lstCaminhoes;

    @FXML
    private ListView<Usuario> lstMotoristas;

    @FXML
    private ListView<Usuario> lstGestores;

    @FXML
    private ListView<Empresa> lstEmpresasOrigem;

    @FXML
    private ListView<Empresa> lstEmpresasDestino;

    @FXML
    private Button btVoltar;

    @FXML
    private Button btRemover;

    @FXML
    private Button btAtualizar;

    @FXML
    private ProgressIndicator piListarRelatorio;

    @FXML
    private AnchorPane root;

    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioCaminhao repositorioCaminhao;
    private RepositorioEndereco repositorioEndereco;
    private RepositorioCidade repositorioCidade;
    private RepositorioEstado repositorioEstado;
    private RepositorioEmpresa repositorioEmpresa;
    private RepositorioViagens repositorioViagens;
    private RepositorioDespesas repositorioDespesas;
    private AutenticacaoServico autenticacaoServico;
    private Login login;

    public Relatorios(Login login, AutenticacaoServico autenticacaoServico,
            RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco,
            RepositorioEstado repositorioEstado,
            RepositorioCidade repositorioCidade, RepositorioEmpresa repositorioEmpresa,
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

    public void initialize() {

        lstCaminhoes.setCellFactory(lista -> new ListCell<>() {
            protected void updateItem(Caminhao caminhao, boolean alterou) {
                super.updateItem(caminhao, alterou);
                if (caminhao != null) {
                    setText("(" + caminhao.getId() + ")" + caminhao.getMarca() + " " + caminhao.getModelo());
                } else {
                    setText(null);
                }
            };
        });
        lstMotoristas.setCellFactory(lista -> new ListCell<>() {
            protected void updateItem(Usuario motorista, boolean alterou) {
                super.updateItem(motorista, alterou);
                if (motorista != null && !motorista.isGestor()) {
                    setText("(" + motorista.getCpf() + ")" + motorista.getNome());
                }
            };
        });

        lstGestores.setCellFactory(lista -> new ListCell<>() {
            protected void updateItem(Usuario gestor, boolean alterou) {
                super.updateItem(gestor, alterou);
                if (gestor != null && gestor.isGestor()) {
                    setText("(" + gestor.getCpf() + ")" + gestor.getNome());
                }
            };
        });

        lstEmpresasOrigem.setCellFactory(lista -> new ListCell<>() {
            protected void updateItem(Empresa empresa, boolean alterou) {
                super.updateItem(empresa, alterou);
                if (empresa != null) {
                    setText("(" + empresa.getId() + ")" + empresa.getNome());
                } else {
                    setText(null);
                }
            };
        });

        lstEmpresasDestino.setCellFactory(lista -> new ListCell<>() {
            protected void updateItem(Empresa empresa, boolean alterou) {
                super.updateItem(empresa, alterou);
                if (empresa != null) {
                    setText("(" + empresa.getId() + ")" + empresa.getNome());
                } else {
                    setText(null);
                }
            };
        });

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
            lstCaminhoes.getItems().addAll(repositorioCaminhao.listarCaminhoes());
            lstMotoristas.getItems().addAll(repositorioUsuarios.listarMotoristas());
            lstGestores.getItems().addAll(repositorioUsuarios.listarGestores());
            lstEmpresasOrigem.getItems().addAll(repositorioEmpresa.listarEmpresasOrigem());
            lstEmpresasDestino.getItems().addAll(repositorioEmpresa.listarEmpresasDestino());
            piListarRelatorio.setVisible(false);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }

    });

    @FXML
    private void voltar() {
        root.getChildren().clear();
        root.getChildren()
                .add(App.loadTela("fxml/home_gestor.fxml",
                        a -> new HomeGestor(this.login, autenticacaoServico, repositorioUsuarios, repositorioCaminhao,
                                repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa,
                                repositorioViagens, repositorioDespesas)));

    }

    @FXML
    private void removerSelecionado() {
        Caminhao caminhaoSelecionado = lstCaminhoes.getSelectionModel().getSelectedItem();
        Usuario gestorSelecionado = lstGestores.getSelectionModel().getSelectedItem();
        Usuario motoristaSelecionado = lstMotoristas.getSelectionModel().getSelectedItem();
        Empresa empresaDestinoSelecionada = lstEmpresasDestino.getSelectionModel().getSelectedItem();
        Empresa empresaOrigemSelecionada = lstEmpresasOrigem.getSelectionModel().getSelectedItem();
        if (caminhaoSelecionado != null) {
            try {
                repositorioCaminhao.removerCaminhoes((caminhaoSelecionado.getId()));
                lstCaminhoes.getItems().clear();
                lstCaminhoes.getItems().addAll(repositorioCaminhao.listarCaminhoes());
                Alert alert = new Alert(AlertType.CONFIRMATION, "Caminhão removido com sucesso!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        } else if (gestorSelecionado != null) {
            try {
                repositorioUsuarios.removerUsuarios(gestorSelecionado.getCpf());
                lstGestores.getItems().clear();
                lstGestores.getItems().addAll(repositorioUsuarios.listarGestores());
                Alert alert = new Alert(AlertType.CONFIRMATION, "Gestor removido com sucesso!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        } else if (motoristaSelecionado != null) {
            try {
                repositorioUsuarios.removerUsuarios(motoristaSelecionado.getCpf());
                lstMotoristas.getItems().clear();
                lstMotoristas.getItems().addAll(repositorioUsuarios.listarMotoristas());
                Alert alert = new Alert(AlertType.CONFIRMATION, "Motorista removido com sucesso!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        } else if (empresaDestinoSelecionada != null) {
            try {
                repositorioEmpresa.removerEmpresas(empresaDestinoSelecionada.getId());
                lstEmpresasDestino.getItems().clear();
                lstEmpresasDestino.getItems().addAll(repositorioEmpresa.listarEmpresasDestino());
                Alert alert = new Alert(AlertType.CONFIRMATION, "Empresa removida com sucesso!");
                alert.showAndWait();
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        } else if (empresaOrigemSelecionada != null) {
            try {
                repositorioEmpresa.removerEmpresas(empresaOrigemSelecionada.getId());
                lstEmpresasOrigem.getItems().clear();
                lstEmpresasOrigem.getItems().addAll(repositorioEmpresa.listarEmpresasOrigem());
                Alert alert = new Alert(AlertType.CONFIRMATION, "Empresa removida com sucesso!");
                alert.showAndWait();
                } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void atualizarSelecionado() {
        Caminhao caminhaoSelecionado = lstCaminhoes.getSelectionModel().getSelectedItem();
        Usuario gestorSelecionado = lstGestores.getSelectionModel().getSelectedItem();
        Usuario motoristaSelecionado = lstMotoristas.getSelectionModel().getSelectedItem();
        Empresa empresaDestinoSelecionada = lstEmpresasDestino.getSelectionModel().getSelectedItem();
        Empresa empresaOrigemSelecionada = lstEmpresasOrigem.getSelectionModel().getSelectedItem();
        if (caminhaoSelecionado != null) {
            root.getChildren().clear();
            root.getChildren()
                .add(App.loadTela("fxml/cadastro_caminhao.fxml",
                    o -> new CadastroCaminhao(caminhaoSelecionado, autenticacaoServico,
                            repositorioUsuarios,
                            repositorioCaminhao,
                            repositorioEndereco,
                            repositorioEstado,
                            repositorioCidade,
                            repositorioEmpresa,
                            repositorioViagens, repositorioDespesas)));
        } else if (gestorSelecionado != null) {
            root.getChildren().clear();
            root.getChildren()
                .add(App.loadTela("fxml/cadastro_Users.fxml",
                    o -> new CadastroUsuario(login, gestorSelecionado, autenticacaoServico,
                            repositorioUsuarios,
                            repositorioCaminhao,
                            repositorioEndereco, repositorioEstado,
                            repositorioCidade, repositorioEmpresa,
                            repositorioViagens, repositorioDespesas)));
        } else if (motoristaSelecionado != null) {
            root.getChildren().clear();
            root.getChildren()
                .add(App.loadTela("fxml/cadastro_Users.fxml",
                    o -> new CadastroUsuario(login, motoristaSelecionado, autenticacaoServico,
                            repositorioUsuarios,
                            repositorioCaminhao,
                            repositorioEndereco, repositorioEstado,
                            repositorioCidade, repositorioEmpresa,
                            repositorioViagens, repositorioDespesas)));
        } else if (empresaDestinoSelecionada != null) {
            root.getChildren().clear();
            root.getChildren()
                .add(App.loadTela("fxml/cadastro_empresa.fxml",
                    o -> new CadastroEmpresa(login, empresaDestinoSelecionada, autenticacaoServico,
                            repositorioUsuarios,
                            repositorioCaminhao,
                            repositorioEndereco,
                            repositorioEstado,
                            repositorioCidade, repositorioEmpresa,
                            repositorioViagens, repositorioDespesas)));
        } else if (empresaOrigemSelecionada != null) {
            root.getChildren().clear();
            root.getChildren()
                .add(App.loadTela("fxml/cadastro_empresa.fxml",
                    o -> new CadastroEmpresa(login, empresaOrigemSelecionada, autenticacaoServico,
                            repositorioUsuarios,
                            repositorioCaminhao,
                            repositorioEndereco,
                            repositorioEstado,
                            repositorioCidade, repositorioEmpresa,
                            repositorioViagens, repositorioDespesas)));
        }
    }

}
