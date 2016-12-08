package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * The main class to load FXML interface and the application icon.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public class Main extends Application{
		
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("DiskScheduling_view.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/DiskScheduling_style.css").toExternalForm());
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Hard-Disk.png"))); 
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
