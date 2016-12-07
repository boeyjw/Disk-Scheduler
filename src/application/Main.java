package application;
	
import javafx.application.Application;
import javafx.fxml.FXML;
//import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.control.Tooltip;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;


public class Main extends Application{
		
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("DiskScheduling_view.fxml"));
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Disk Scheduling Simulator"); 					
			primaryStage.show();
			primaryStage.setResizable(true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
