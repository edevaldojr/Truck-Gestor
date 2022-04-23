package ifpr.pgua.eic.gestaocaminhao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import ifpr.pgua.eic.gestaocaminhao.daos.JDBCAutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCCaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCCidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCDespesaDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCEmpresaDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCEnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCEstadoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCUsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCViagemDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CidadeDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.DespesaDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EmpresaDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EnderecoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.ViagemDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.EstadoDAO;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCidade;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioDespesas;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEmpresa;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEndereco;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioEstado;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioViagens;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import ifpr.pgua.eic.gestaocaminhao.telas.Login;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

/**
 * JavaFX App
 */
public class App extends Application {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

    private CaminhaoDAO caminhaoDAO = new JDBCCaminhaoDAO(fabricaConexoes);
    private EstadoDAO estadoDAO = new JDBCEstadoDAO(fabricaConexoes);
    private CidadeDAO cidadeDAO = new JDBCCidadeDAO(fabricaConexoes);
    private EnderecoDAO enderecoDAO = new JDBCEnderecoDAO(fabricaConexoes);
    private EmpresaDAO empresaDAO = new JDBCEmpresaDAO(fabricaConexoes);
    private UsuarioDAO usuarioDAO = new JDBCUsuarioDAO(fabricaConexoes);
    private ViagemDAO viagemDAO = new JDBCViagemDAO(fabricaConexoes);
    private DespesaDAO despesaDAO = new JDBCDespesaDAO(fabricaConexoes);
    private AutenticacaoDAO autenticacaoDAO = new JDBCAutenticacaoDAO(fabricaConexoes);

    private RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(usuarioDAO, enderecoDAO, cidadeDAO);
    private RepositorioCaminhao repositorioCaminhao = new RepositorioCaminhao(caminhaoDAO);
    private AutenticacaoServico autenticacaoServico = new AutenticacaoServico(autenticacaoDAO, enderecoDAO, usuarioDAO,
            cidadeDAO);
    private RepositorioEndereco repositorioEndereco = new RepositorioEndereco(enderecoDAO, cidadeDAO);
    private RepositorioEstado repositorioEstado = new RepositorioEstado(estadoDAO);
    private RepositorioCidade repositorioCidade = new RepositorioCidade(cidadeDAO);
    private RepositorioEmpresa repositorioEmpresa = new RepositorioEmpresa(empresaDAO, enderecoDAO, cidadeDAO);
    private RepositorioViagens repositorioViagens = new RepositorioViagens(viagemDAO, caminhaoDAO, empresaDAO,
            usuarioDAO);
    private RepositorioDespesas repositorioDespesas = new RepositorioDespesas(despesaDAO, caminhaoDAO);

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(
                loadTela("fxml/login.fxml",
                        o -> new Login(autenticacaoServico, repositorioUsuarios, repositorioCaminhao,
                                repositorioEndereco, repositorioEstado, repositorioCidade, repositorioEmpresa,
                                repositorioViagens, repositorioDespesas)),
                1233, 597);
        stage.setResizable(false);
        // stage.setMaximized(true);
        stage.setTitle("Gestão de Caminhão");
        stage.getIcons().add(new Image(this.getClass().getResource("img/caminhaoIcon.png").toString()));
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadTela(String fxml, Callback controller) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml));
            loader.setControllerFactory(controller);

            root = loader.load();

        } catch (Exception e) {
            System.out.println("Problema no arquivo fxml. Está correto?? " + fxml);
            e.printStackTrace();
            System.exit(0);
        }
        return root;
    }

    public static void main(String[] args) {
        launch();
    }

}