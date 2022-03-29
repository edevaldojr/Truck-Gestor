module ifpr.pgua.eic.gestaocaminhao {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens ifpr.pgua.eic.gestaocaminhao.telas to javafx.fxml;

    exports ifpr.pgua.eic.gestaocaminhao;
}
