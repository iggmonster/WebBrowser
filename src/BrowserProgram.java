// IMPORTS
// These are some classes that may be useful for completing the project.
// You may have to add others.

//--module-path
//C:\Users\keiff\Downloads\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib
//--add-modules=javafx.controls,javafx.fxml,javafx.web,javafx.media
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebHistory.Entry;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;
import javafx.concurrent.Worker;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * The main class for BrowserProgram. BrowserProgram constructs the JavaFX window and
 * handles interactions with the dynamic components contained therein.
 */
public class BrowserProgram extends Application {
	// INSTANCE VARIABLES
	// These variables are included to get you started.
	private Stage stage = null;
	private BorderPane borderPane = null;
    private WebView view = null;
	private WebEngine webEngine = null;
	private TextField statusbar = null;

	// HELPER METHODS
	/**
	 * Retrieves the value of a command line argument specified by the index.
	 *
	 * @param index - position of the argument in the args list.
	 * @return The value of the command line argument.
	 */
	private String getParameter( int index ) {
		Parameters params = getParameters();
		List<String> parameters = params.getRaw();
		return !parameters.isEmpty() ? parameters.get(index) : "";
	}

	/**
	 * Creates a WebView which handles mouse and some keyboard events, and
	 * manages scrolling automatically, so there's no need to put it into a ScrollPane.
	 * The associated WebEngine is created automatically at construction time.
	 *
	 * @return browser - a WebView container for the WebEngine.
	 */
	private WebView makeHtmlView( ) {
		view = new WebView();
		webEngine = view.getEngine();
		return view;
	}

	/**
	 * Generates the status bar layout and text field.
	 *
	 * @return statusbarPane - the HBox layout that contains the statusbar.
	 */
	private HBox makeStatusBar( ) {
		HBox statusbarPane = new HBox();
		statusbarPane.setPadding(new Insets(5, 4, 5, 4));
		statusbarPane.setSpacing(10);
		statusbarPane.setStyle("-fx-background-color: #336699;");
		statusbar = new TextField();
		HBox.setHgrow(statusbar, Priority.ALWAYS);
		statusbarPane.getChildren().addAll(statusbar);
		return statusbarPane;
	}
/*
	private WebView webView{
		WebView webViewPane = new WebView();

		VBox root = new VBox();
		root.getChildren().addAll(webViewPane);
	}

 */

	// REQUIRED METHODS
	/**
	 * The main entry point for all JavaFX applications. The start method is
	 * called after the init method has returned, and after the system is ready
	 * for the application to begin running.
	 *
	 * NOTE: This method is called on the JavaFX Application Thread.
	 *
	 * @param primaryStage - the primary stage for this application, onto which
	 * the application scene can be set.
	 */
	@Override
	public void start(Stage primaryStage) {


		//WebView myWebView = new WebView();
		//WebEngine engine = myWebView.getEngine();
		//engine.load("https://www.google.com");

		//VBox root = new VBox();
		//root.getChildren().addAll(myWebView);



		BorderPane bp = new BorderPane();
		try {

			Scene scene = new Scene(bp, 800, 800);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();


		} catch (Exception e){
			e.printStackTrace();
		}

		Button refresh = new Button("⟳");//b1
		Button search = new Button("Search");//b2
		Button backButton = new Button("<");
		Button fowardButton = new Button(">");
		Button helpButton = new Button("?");
		TextField urlBar = new TextField();//tf1
		urlBar.setPromptText("URL Address");
		HBox topBar = new HBox(6);//tb
		HBox.setHgrow(urlBar, Priority.ALWAYS);
		topBar.getChildren().addAll(backButton, fowardButton, refresh, urlBar, search, helpButton);
		bp.setTop(topBar);
		bp.setCenter(makeHtmlView());
		webEngine.load("https://www.google.com");

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, ev ->{
			if (ev.getCode() == KeyCode.ENTER && urlBar.isFocused()) {
				webEngine.load((urlBar.getText()));
				ev.consume();
			}
		});

		backButton.setOnAction(actionEvent -> {
				if (!webEngine.getHistory().getEntries().isEmpty()) {
					webEngine.getHistory().go(-1);
					actionEvent.consume();
				}
			});


		fowardButton.setOnAction(actionEvent -> {
			webEngine.getHistory().go(+1);
			actionEvent.consume();
		});



		//WebView browser = new WebView();
		//WebEngine webEngine = makeHtmlView().getEngine();
		//webEngine.load("http://google.com");


		//String s=urlBar.getText();
		//webEngine.load("http://"+s);
		//tab1


	}

	/**
	 * The main( ) method is ignored in JavaFX applications.
	 * main( ) serves only as fallback in case the application is launched
	 * as a regular Java application, e.g., in IDEs with limited FX
	 * support.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
