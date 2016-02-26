package fil;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
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
@SuppressWarnings("restriction")
public class Web extends Application {

	private static final String INDEX = "http://www.google.com";
	private static final String INDEX2 = "myapp://resource/index.html";
  private static final String LOGO = "/style/logo.png";
  private ResourceBundle bundle = ResourceBundle.getBundle ("i18n/app", Locale.getDefault ());

	public void start1(Stage stage) {
		URL.setURLStreamHandlerFactory(new MyURLStreamHandlerFactory());
		stage.setTitle("HTML");
		//stage.setFullScreen(true);
		
		stage.setMaximized(true);
//		stage.setWidth(500);
//		stage.setHeight(500);
		Scene scene = new Scene(new Group(),Color.TRANSPARENT);
		VBox root = new VBox();
		if(false){
		    root.setStyle("-fx-background-color: transparent;"); 
		    stage.initStyle(StageStyle.TRANSPARENT);
		    stage.setMaximized(true);
		}else{
			stage.setMaximized(true);	
		}
		
		final WebView webView = new WebView();
		webView.setPrefHeight(1600);

		@SuppressWarnings("restriction")
		final WebEngine webEngine = webView.getEngine();
		Hyperlink hpl = new Hyperlink("www.google.com");
		hpl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				webEngine.load("http://www.google.com");
			}
		});
		
		MenuButton menuButton = new MenuButton("go", null,getMenuItem(webEngine,"google","www.google.com")
				,getMenuItem(webEngine,"local","http://localhost:8001"));
		menuButton.setOnAction((e)->{webEngine.load("http://www.google.com");});
		
		TextField text = new TextField();
		text.textProperty().addListener((obs,old,value)->{
//			System.out.println(value);
//			webEngine.load(value);
		});
		Button button = new Button("go");
		button.setOnAction((e)->{
		  webEngine.load(text.getText());
		  System.out.println(text.getText());
		});
		
		HBox hbox = new HBox(menuButton, text, button);
		
		webView.getEngine().locationProperty().addListener((obs,old,value) ->{
		  text.setText(value);
		});
		root.getChildren().addAll(hbox, webView);
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		//webEngine.load("http://localhost:8001/index.html");
		//webEngine.loadContent("<html><head><title>IWH</title></head><body><h1>test</h1><script>alert('toto');</script></body></html>");
		webEngine.load(INDEX);
		//webEngine.on
		scene.setRoot(root);

		stage.setScene(scene);
		stage.show();
		JSObject window = (JSObject) webEngine.executeScript("window");
		window.setMember("app", this);

	}

	private MenuItem getMenuItem(final WebEngine webEngine, String label, String url) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.setOnAction((e)->{webEngine.load(url);});
		return menuItem;
	}
	
	public void log(String msg){
		System.out.println(msg);
	}

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
