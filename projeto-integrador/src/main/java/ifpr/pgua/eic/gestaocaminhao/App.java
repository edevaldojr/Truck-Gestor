package ifpr.pgua.eic.gestaocaminhao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

import ifpr.pgua.eic.gestaocaminhao.daos.JDBCAutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCCaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.JDBCUsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.AutenticacaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.CaminhaoDAO;
import ifpr.pgua.eic.gestaocaminhao.daos.interfaces.UsuarioDAO;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioCaminhao;
import ifpr.pgua.eic.gestaocaminhao.repositories.RepositorioUsuarios;
import ifpr.pgua.eic.gestaocaminhao.services.AutenticacaoServico;
import ifpr.pgua.eic.gestaocaminhao.telas.Login;
import ifpr.pgua.eic.gestaocaminhao.utils.FabricaConexoes;

/**
 * JavaFX App
 */
public class App extends Application {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

    UsuarioDAO usuarioDAO = new JDBCUsuarioDAO(fabricaConexoes);
    CaminhaoDAO caminhaoDAO = new JDBCCaminhaoDAO(fabricaConexoes);
    private AutenticacaoDAO autenticacaoDAO = new JDBCAutenticacaoDAO(fabricaConexoes);

    private RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(usuarioDAO);
    private RepositorioCaminhao repositorioCaminhao = new RepositorioCaminhao(caminhaoDAO);
    private AutenticacaoServico autenticacaoServico = new AutenticacaoServico(autenticacaoDAO);

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(
                loadTela("fxml/login.fxml",
                        o -> new Login(autenticacaoServico, repositorioUsuarios, repositorioCaminhao)),
                864, 515);
        // stage.setMaximized(true);
        stage.setTitle("Truck");
        // stage.getIcons().add(new Image(""));
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