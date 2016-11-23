package fil;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * http://stackoverflow.com/questions/17522343/custom-javafx-webview-protocol-handler
 * http://stackoverflow.com/questions/16215844/javafx-webview-disable-same-origin-policy-allow-cross-domain-requests
 *
 *
 * -Dhttp.proxyHost=10.0.0.100 -Dhttp.proxyPort=8800 -Dhttp.nonProxyHosts="localhost|127.0.0.1|10.*.*.*|*.foo.com|etc"
 *
 * TODO:
 *  Actions non fournies par webkit:
 *   base:
 * - back/next button bind sur alt<- alt->
 * - refresh bind sur F5
 * - bookmark bind sur ^D
 * - menu des bookmarks
 *   utile:
 * - tabs  : ^T
 * - menus : Classement bookmarks, Histo arborescent, Parametres
 * - customisation : gestion de plugin avec une API sur le chargement, l'histo le contenu (filtrage..)
 *
 * @author pdemanget
 *
 */
public class Web extends Application {

  private static final String LOGO = "/style/logo.png";
  private ResourceBundle bundle = ResourceBundle.getBundle ("i18n/app", Locale.getDefault ());


	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws IOException{
//    Injector.registerExistingAndInject (this);
//    Injector.setModelOrService(this.getClass (),this);
	  stage.setTitle ("Blue browser");

    stage.getIcons().add(new Image(LOGO));
    Parent root = FXMLLoader.load(getClass().getResource("App.fxml"),bundle);
    Scene scene = new Scene(root);
    stage.setMaximized(true);
    stage.setScene(scene);
    stage.show ();



	}

	 @Override
	  public void stop () {
	   System.out.println("exiting");
	 }
}
