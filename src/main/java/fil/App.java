package fil;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;

/**
 *
 * <br><b>© Copyright 2016 - IN-CORE Systèmes - Tous droits réservés</b>
 *
 * @author pdemanget
 * @version 26 févr. 2016
 */
public class App {

  @FXML TextField text;
  @FXML WebView webView;

  public App(){
    System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
    //TODO
    //java.net.CookieHandler.setDefault(new com.sun.webkit.network.CookieManager());
  }


  @FXML
  public void initialize(){
    webView.getEngine().locationProperty().addListener((obs,old,value) ->{
      text.setText(value);
    });

    JSObject window = (JSObject) webView.getEngine().executeScript("window");
    window.setMember("app", this);

    webView.getEngine().setOnAlert((e)-> {
        Stage popup = new Stage();
        popup.initOwner(webView.getScene().getWindow());
        popup.initStyle(StageStyle.UTILITY);
        popup.initModality(Modality.WINDOW_MODAL);

        StackPane content = new StackPane();
        content.getChildren().setAll(
          new Label(e.getData())
        );
        content.setPrefSize(200, 100);

        popup.setScene(new Scene(content));
        popup.showAndWait();
      }
    );

    //webView.getEngine().setPromptHandler();

  }


  public void go(){
    webView.getEngine().load(text.getText());
  }
  public void back(){
    webView.getEngine().getHistory().go(-1);
  }
  public void next(){
    webView.getEngine().getHistory().go(1);
  }
}
