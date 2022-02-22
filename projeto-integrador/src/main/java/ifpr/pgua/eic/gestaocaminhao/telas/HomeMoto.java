package ifpr.pgua.eic.gestaocaminhao.telas;

import javafx.fxml.FXML;

public class HomeMoto {

    private Home homeController;

    public HomeMoto(Home homeController) {
        System.out.println("Home Motorista");
        this.homeController = homeController;
    }

    @FXML
    public void logout() {
        homeController.logout();
    }

}
