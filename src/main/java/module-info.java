module com.scissor.hand {
    requires javafx.controls;
    requires javafx.fxml;
    requires uk.co.caprica.vlcj;
    requires uk.co.caprica.vlcj.javafx;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires fastjson;
    requires java.sql;

    opens com.scissor.hand to javafx.fxml, fastjson;
    exports com.scissor.hand;
}