package application;
	
import javafx.application.Application;
//import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.control.Tooltip;


public class Main extends Application{
	
	/*@FXML
	private TextField cylinderamount;
	@FXML
	private TextField currenthead;
	@FXML
	private TextField jobreq;
	@FXML 
	private Button reset;
	@FXML
	private Button simulate;*/
	
	
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
			
			/*cylinderamount.setTooltip(new Tooltip text = "Total number of cylinder on a disk."));
			currenthead.setTooltip(new Tooltip("The cylinder where the head is currently."));
			jobreq.setTooltip(new Tooltip("The job request queue in the cylinder."));
			reset.setTooltip(new Tooltip("Clear all values."));
			simulate.setTooltip(new Tooltip("Run simulation to display graph(s)."));*/
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
