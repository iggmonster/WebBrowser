// IMPORTS
// These are some classes that may be useful for completing the project.
// You may have to add others.

//--module-path
//C:\Users\keiff\Downloads\openjfx-11.0.2_windows-x64_bin-sdk\javafx-sdk-11.0.2\lib
//--add-modules=javafx.controls,javafx.fxml,javafx.web,javafx.media

/**
 * @author Keiffer Button
 * Lab Section: L01
 * Class: CS1131 Accelerated Intro to Programming
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import javafx.stage.Modality;
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




//make scene
		BorderPane bp = new BorderPane();
		try {

			Scene scene = new Scene(bp, 800, 800);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();


		} catch (Exception e){
			e.printStackTrace();
		}
//initializing stuff
		Button refresh = new Button("⟳");//b1
		Button search = new Button("Search");//b2
		Button backButton = new Button("<");
		Button fowardButton = new Button(">");
		Button helpButton = new Button("?");
		TextField urlBar = new TextField();//tf1
		urlBar.setPromptText("URL Address");
		TextField statusBar = new TextField();
		statusBar.setEditable(false);
		HBox bottomBar = new HBox(4);
		HBox.setHgrow(statusBar, Priority.ALWAYS);
		bottomBar.getChildren().addAll(statusBar);
		HBox topBar = new HBox(6);//tb
		HBox.setHgrow(urlBar, Priority.ALWAYS);
		topBar.getChildren().addAll(backButton, fowardButton, refresh, urlBar, search, helpButton);
		bp.setTop(topBar);
		bp.setCenter(makeHtmlView());
		bp.setBottom(bottomBar);
		webEngine.load("https://www.google.com");

//popup window
		Stage popupwindow=new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Help");
		Label label1= new Label("Author: Keiffer Button\nLab Section: L01\nCourse: CS1131 Accelerated Intro to Programming\n\n" +
				"Type a URL in the top bar and hit Enter or the search\nbutton to go to that web page.\nThe back arrow goes back, forward\ngoes forward, " +
				"and reload reloads the page.\nClick the ? button in the top right corner\nto bring up this page anytime.");
		Button button1= new Button("Ok");
		button1.setOnAction(e -> popupwindow.close());
		VBox layout= new VBox(10);
		layout.getChildren().addAll(label1, button1);
		layout.setAlignment(Pos.CENTER);
		Scene scene1= new Scene(layout, 300, 250);
		popupwindow.setScene(scene1);
		popupwindow.showAndWait();
//enter to search
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, ev ->{
			if (ev.getCode() == KeyCode.ENTER && urlBar.isFocused()) {
				webEngine.load((urlBar.getText()));
				ev.consume();
			}
		});
//push search button
		search.setOnAction(actionEvent -> {
			try {
				webEngine.load((urlBar.getText()));
				actionEvent.consume();
			} catch (Exception e){

			}
		});
//push back button
		backButton.setOnAction(actionEvent -> {
			try {
				if (!webEngine.getHistory().getEntries().isEmpty()) {
					webEngine.getHistory().go(-1);
					actionEvent.consume();
				}
			} catch (Exception e){

			}
			});
//push forward button
		fowardButton.setOnAction(actionEvent -> {
			try {
				webEngine.getHistory().go(+1);
				actionEvent.consume();
			} catch (Exception e){

			}
		});
//push refresh button
		refresh.setOnAction(actionEvent -> {
			webEngine.getHistory().go(0);
			webEngine.reload();
			actionEvent.consume();
		});
//push help button
		helpButton.setOnAction(actionEvent -> {
			popupwindow.showAndWait();
			actionEvent.consume();
		});
//status bar
		webEngine.setOnStatusChanged(event -> {
			statusBar.setText(event.getData());
		});



//url bar and title bar rename
stage = primaryStage;
		webEngine.getLoadWorker().stateProperty().addListener(
				(ov, oldState, newState) -> {
					if (newState == State.SUCCEEDED){
						urlBar.setText(webEngine.getLocation());
						if (webEngine.titleProperty() == null) {
							stage.setTitle(webEngine.getLocation());
						}
						else stage.setTitle(webEngine.titleProperty().getValue());
				}

		}
		);






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
