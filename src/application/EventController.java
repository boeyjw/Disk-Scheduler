package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;


public class EventController {

	public EventController() {}	

	//buttons
	@FXML 
	Button reset;
	@FXML
	Button simulate;
		
	//parameters
	@FXML
	TextField cylinderamount;
	@FXML
	TextField currenthead;
	@FXML
	TextField jobreq;
		
	//to display text when mouse hovers on item
	public void hovertext() {
		cylinderamount.setTooltip(new Tooltip("Total number of cylinder on a disk."));
		currenthead.setTooltip(new Tooltip("The cylinder where the head is currently."));
		jobreq.setTooltip(new Tooltip("The job request queue in the cylinder."));
		reset.setTooltip(new Tooltip("Clear all values."));
		simulate.setTooltip(new Tooltip("Run simulation to display graph(s)."));
	}


}
