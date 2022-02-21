module ifpr.pgua.eic.gestaocaminhao {
    requires javafx.controls;
    requires javafx.fxml;

    opens ifpr.pgua.eic.gestaocaminhao to javafx.fxml;
    exports ifpr.pgua.eic.gestaocaminhao;
}
