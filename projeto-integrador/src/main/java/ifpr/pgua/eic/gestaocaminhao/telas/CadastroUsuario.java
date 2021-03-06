package ifpr.pgua.eic.gestaocaminhao.telas;

import java.sql.SQLException;

import ifpr.pgua.eic.gestaocaminhao.App;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.models.Cidade;
import ifpr.pgua.eic.gestaocaminhao.models.Endereco;
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
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class CadastroUsuario {

    private RepositorioUsuarios repositorioUsuarios;
    private RepositorioCaminhao repositorioCaminhao;
    private RepositorioEndereco repositorioEndereco;
    private RepositorioCidade repositorioCidade;
    private RepositorioEstado repositorioEstado;
    private RepositorioEmpresa repositorioEmpresa;
    private RepositorioViagens repositorioViagens;
    private RepositorioDespesas repositorioDespesas;
    private Login login;

    private Usuario usuarioExistente = null;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfTelefone;

    @FXML
    private TextField pfSenha;

    @FXML
    private TextField tfCnh;

    @FXML
    private TextField tfCidade;

    @FXML
    private TextField tfBairro;

    @FXML
    private TextField tfRua;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfCep;

    @FXML
    private TextField tfComplemento;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    @FXML
    private CheckBox cbGestor;

    @FXML
    private AnchorPane root;

    private AutenticacaoServico autenticacaoServico;

    public CadastroUsuario(Login login, AutenticacaoServico autenticacaoServico,
            RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco, RepositorioEstado repositorioEstado,
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

    public CadastroUsuario(AutenticacaoServico autenticacaoServico, RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco, RepositorioEstado repositorioEstado,
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
    }

    public CadastroUsuario(Login login, Usuario usuarioExiste, AutenticacaoServico autenticacaoServico,
            RepositorioUsuarios repositorioUsuarios,
            RepositorioCaminhao repositorioCaminhao,
            RepositorioEndereco repositorioEndereco, RepositorioEstado repositorioEstado,
            RepositorioCidade repositorioCidade, RepositorioEmpresa repositorioEmpresa,
            RepositorioViagens repositorioViagens, RepositorioDespesas repositorioDespesas) {
        this.login = login;
        this.autenticacaoServico = autenticacaoServico;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioEndereco = repositorioEndereco;
        this.repositorioCaminhao = repositorioCaminhao;
        this.repositorioCidade = repositorioCidade;
        this.repositorioEstado = repositorioEstado;
        this.repositorioEmpresa = repositorioEmpresa;
        this.repositorioViagens = repositorioViagens;
        this.usuarioExistente = usuarioExiste;
        this.repositorioDespesas = repositorioDespesas;
    }

    public void initialize() {
        if (usuarioExistente != null) {
            tfCpf.setText(usuarioExistente.getCpf());
            tfCpf.setEditable(false);
            tfNome.setText(usuarioExistente.getNome());
            tfEmail.setText(usuarioExistente.getEmail());
            tfTelefone.setText(usuarioExistente.getTelefone());
            tfCnh.setText(usuarioExistente.getCnh());
            pfSenha.setText(usuarioExistente.getSenha());
            tfCidade.setText(usuarioExistente.getEndereco().getCidade().getNome());
            tfBairro.setText(usuarioExistente.getEndereco().getBairro());
            tfRua.setText(usuarioExistente.getEndereco().getRua());
            tfNumero.setText(usuarioExistente.getEndereco().getNumero() + "");
            tfCep.setText(usuarioExistente.getEndereco().getCep());
            tfComplemento.setText(usuarioExistente.getEndereco().getComplemento());
            tfCnh.setText(usuarioExistente.getCnh());
            if (usuarioExistente.isGestor()) {
                cbGestor.setSelected(true);
            }
            btCadastrar.setText("Atualizar");
        }
        if (!autenticacaoServico.estaLogado()) {
            cbGestor.setVisible(false);
            cbGestor.disarm();
        }
    }

    @FXML
    private void voltar() {
        if (autenticacaoServico.estaLogado()) {
            root.getChildren().clear();
            root.getChildren()
                    .add(App.loadTela("fxml/home_gestor.fxml",
                            a -> new HomeGestor(login, autenticacaoServico, repositorioUsuarios,
                                    repositorioCaminhao,
                                    repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa,
                                    repositorioViagens, repositorioDespesas)));
        } else {
            root.getChildren().clear();
            root.getChildren()
                    .add(App.loadTela("fxml/login.fxml",
                            a -> new Login(autenticacaoServico, repositorioUsuarios, repositorioCaminhao,
                                    repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa,
                                    repositorioViagens, repositorioDespesas)));
        }

    }

    @FXML
    private void cadastrar() throws Exception {
        String cpf = tfCpf.getText();
        String nome = tfNome.getText();
        String telefone = tfTelefone.getText();
        String email = tfEmail.getText();
        String senha = pfSenha.getText();
        String cnh = tfCnh.getText();
        String cidade = tfCidade.getText();
        String rua = tfRua.getText();
        String bairro = tfBairro.getText();
        String numero = tfNumero.getText();
        String cep = tfCep.getText();
        String complemento = tfComplemento.getText();

        boolean gestor = cbGestor.isSelected();

        boolean temErro = false;
        String msg = "";

        if (cpf.isEmpty() || cpf.isBlank()) {
            temErro = true;
            msg += "CPF n??o pode ser vazio!\n";
        }

        if (nome.isEmpty() || nome.isBlank()) {
            temErro = true;
            msg += "Nome n??o pode ser vazio!\n";
        }

        if (cidade.isEmpty() || cidade.isBlank()) {
            temErro = true;
            msg += "Cidade n??o pode ser vazio!\n";
        }

        if (bairro.isEmpty() || bairro.isBlank()) {
            temErro = true;
            msg += "Bairro n??o pode ser vazio!\n";
        }
        if (numero.isEmpty() || numero.isBlank()) {
            temErro = true;
            msg += "N??mero n??o pode ser vazio!\n";
        }

        if (rua.isEmpty() || rua.isBlank()) {
            temErro = true;
            msg += "Rua n??o pode ser vazio!\n";
        }

        if (cep.isEmpty() || cep.isBlank()) {
            temErro = true;
            msg += "CEP n??o pode ser vazio!\n";
        }

        if (email.isEmpty() || email.isBlank()) {
            temErro = true;
            msg += "Email n??o pode ser vazio!\n";
        }

        if (senha.isEmpty() || senha.isBlank()) {
            temErro = true;
            msg += "Senha n??o pode ser vazio!\n";
        }

        if (telefone.isEmpty() || telefone.isBlank()) {
            temErro = true;
            msg += "Telefone n??o pode ser vazio!\n";
        }

        if (cnh.isEmpty() || cnh.isBlank()) {
            if (!gestor) {
                temErro = true;
                msg += "CNH n??o pode ser vazio!\n";
            }
        }

        if (!temErro) {
            try {
                boolean ret;
                int existente = 0;
                if (usuarioExistente != null) {
                    Cidade cidadeObj = repositorioCidade.buscarCidadePorNome(cidade);
                    if (repositorioEndereco.buscar(bairro, rua, numero) == null) {
                        repositorioEndereco.cadastrarEndereco(numero, complemento, bairro, rua, cep, cidadeObj);
                    }
                    Endereco endereco = repositorioEndereco.buscar(bairro, rua, numero);
                    ret = repositorioUsuarios.atualizarUsuarios(cpf, nome, endereco, telefone, email, senha, cnh,
                            gestor);
                    existente = 1;
                } else {
                    if (repositorioUsuarios.buscar(cpf) == null) {
                        Cidade cidadeObj = repositorioCidade.buscarCidadePorNome(cidade);
                        if (repositorioEndereco.buscar(bairro, rua, numero) == null) {
                            repositorioEndereco.cadastrarEndereco(numero, complemento, bairro, rua, cep, cidadeObj);
                        }
                        Endereco endereco = repositorioEndereco.buscar(bairro, rua, numero);
                        ret = repositorioUsuarios.cadastrarUsuario(cpf, nome, endereco, telefone, email, senha, cnh,
                                gestor);
                        existente = 2;
                    } else {
                        Alert alert = new Alert(AlertType.INFORMATION, "CPF de usu??rio j?? cadastrado");
                        alert.showAndWait();
                        ret = false;
                    }

                }

                if (ret && existente == 2) {
                    msg = "Usu??rio cadastrado com sucesso!";
                    limpar();
                } else if (ret && existente == 1) {
                    msg = "Usu??rio atualizado com sucesso!";
                    limpar();
                } else {
                    msg = "Erro ao cadastrar Usu??rio!";
                }

            } catch (SQLException e) {
                temErro = true;
                msg = e.getMessage();
            }
        }

        Alert alert = new Alert(temErro ? AlertType.ERROR : AlertType.INFORMATION, msg);
        alert.showAndWait();

    }

    @FXML
    private void limpar() {
        tfCpf.clear();
        tfCidade.clear();
        pfSenha.clear();
        tfEmail.clear();
        tfNome.clear();
        tfTelefone.clear();
        tfCnh.clear();
        tfBairro.clear();
        tfRua.clear();
        tfNumero.clear();
        tfCep.clear();
        tfComplemento.clear();
        cbGestor.setSelected(false);
    }

}
