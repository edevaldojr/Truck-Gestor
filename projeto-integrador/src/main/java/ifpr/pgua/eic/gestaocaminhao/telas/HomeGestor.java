package ifpr.pgua.eic.gestaocaminhao.telas;

import javafx.fxml.FXML;

public class HomeGestor {

    private Home homeController;

    public HomeGestor(Home homeController) {
        System.out.println("Home Gestor");
        this.homeController = homeController;
    }

    @FXML
    public void logout() {
        homeController.logout();
    }

}
