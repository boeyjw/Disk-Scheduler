package javafx.application;

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import osc.diskscheduling.algorithm.ControllerBroker;


/**
 * This is the class that hooks the interface to the Model logic.
 * Any functions in the interface are executed here.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public class Controller implements Initializable {

	//buttons
	@FXML 
	private Button reset;
	@FXML
	private Button simulate;

	//parameters input
	@FXML
	private TextField cylinderamount;
	@FXML
	private TextField currenthead;
	@FXML
	private TextField jobreq;
	@FXML
	private CheckBox fcfs;
	@FXML
	private CheckBox sstf;
	@FXML
	private CheckBox scan;
	@FXML
	private CheckBox cscan;
	@FXML
	private CheckBox look;
	@FXML
	private CheckBox clook;
	@FXML
	private ComboBox<String> load_combo;


	//description
	@FXML
	private Label desc_label;
	@FXML
	private TextArea description_text;

	//Input queue display
	@FXML
	private Label input_queuelabel;
	@FXML
	private TextArea input_queuetext;
	
	//modified queue show text area
	@FXML
	private GridPane grid_pane;
	//label grid, column 0
	@FXML
	private Label fcfs_label;
	@FXML
	private Label sstf_label;
	@FXML
	private Label scan_label;
	@FXML
	private Label cscan_label;
	@FXML
	private Label look_label;
	@FXML
	private Label clook_label;
	//text area grid, column 1
	@FXML
	private TextArea fcfs_textqueue;
	@FXML
	private TextArea sstf_textqueue;
	@FXML
	private TextArea scan_textqueue;
	@FXML
	private TextArea cscan_textqueue;
	@FXML
	private TextArea look_textqueue;
	@FXML
	private TextArea clook_textqueue;
	@FXML
	private Label column_header_algol;
	@FXML
	private Label column_header_queue;

	//graph
	@FXML
	private Pane customgraph_pane;
	@FXML
	private NumberAxis xaxis;
	@FXML
	private NumberAxis yaxis;
	@FXML
	private LineChart<Integer, Integer> custom_linechart;
	private static int seriesCounter = 0; 

	//parameters
	private String message="";
	private String message2="";
	private static final String load_message="Pick a load type (Default: Custom)";
	private LinkedList<Integer> queue = new LinkedList<Integer>();
	private int cylinder;
	private int head;
	
	//Program local field
	private ControllerBroker cbr = new ControllerBroker();
	private ArrayList<XYChart.Series<Integer, Integer>> lsSeries = new ArrayList<XYChart.Series<Integer, Integer>>(6);

	
	/**
	 * Interface factory default view.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		load_combo.setItems(FXCollections.observableArrayList("Light","Medium","Heavy","Custom"));
		load_combo.setValue(load_message);
		
		//The description TextArea is not for use
		description_text.setEditable(false);
		
		//Set the graph x-axis and y-axis labels
		xaxis.setLabel("Request");
		yaxis.setLabel("Cylinder");
		
		//The processed queue information is not visible because there is no input at launch
		grid_pane.setVisible(false);
		
		fcfs_label.setVisible(false);
		sstf_label.setVisible(false);
		scan_label.setVisible(false);
		cscan_label.setVisible(false);
		look_label.setVisible(false);
		clook_label.setVisible(false);

		fcfs_textqueue.setVisible(false);
		sstf_textqueue.setVisible(false);
		scan_textqueue.setVisible(false);
		cscan_textqueue.setVisible(false);
		look_textqueue.setVisible(false);
		clook_textqueue.setVisible(false);
		
		//The processed queue information is not for use
		fcfs_textqueue.setEditable(false);
		sstf_textqueue.setEditable(false);
		scan_textqueue.setEditable(false);
		cscan_textqueue.setEditable(false);
		look_textqueue.setEditable(false);
		clook_textqueue.setEditable(false);
		
		//The input queue display is not for use and is not visible until a valid queue is entered
		input_queuelabel.setVisible(false);
		input_queuetext.setVisible(false);
		input_queuetext.setEditable(false);
		input_queuetext.setWrapText(true);
	}
	
	/**
	 * Correspond to the load ComboBox. Executes every time a load is changed. 
	 */
	@FXML
	private void load(){
		String loadType = load_combo.getValue();
		if(loadType.compareTo("Light") == 0){
			cylinderamount.setEditable(false);
			currenthead.setEditable(false);
			jobreq.setEditable(false);
			cylinderamount.setText("999");
			currenthead.setText("500");
			jobreq.setText("524,468,560,532,442,490,564,580,602");
		}else if(loadType.compareTo("Medium") == 0){
			cylinderamount.setEditable(false);
			currenthead.setEditable(false);
			jobreq.setEditable(false);
			cylinderamount.setText("999");
			currenthead.setText("500");
			jobreq.setText("421,125,624,524,125,32,125,324,698,203,421,698,825,421");
		}else if(loadType.compareTo("Heavy") == 0){
			cylinderamount.setEditable(false);
			currenthead.setEditable(false);
			jobreq.setEditable(false);
			cylinderamount.setText("999");
			currenthead.setText("500");
			jobreq.setText("987,123,896,214,987,123,998,2,875,365,993,2,785,12,987,56,865,12,998,123");
		}else if(loadType.compareTo("Custom") == 0){
			cylinderamount.setEditable(true);
			currenthead.setEditable(true);
			jobreq.setEditable(true);
			cylinderamount.clear();
			currenthead.clear();
			jobreq.clear();
		}
	}


	/**
	 * Handles input algorithm methods. Sequential access in the order of FCFS, SSTF, Scan, CScan, Look, CLook
	 */
	private void algorithmOptions(){
		LinkedList<Integer> temp = new LinkedList<Integer>();
		int[] totalSeekTime = new int[6];
		String[] descShowAlgolSeek = new String[] {"FCFS", "SSTF", "Scan", "CScan", "Look", "CLook"};
		Arrays.fill(totalSeekTime, -1);
		boolean anySelected = false;
		
		if(fcfs.isSelected()){
			temp = cbr.algorithmSelector(queue, ControllerBroker.FCFS);
			graphingData(temp, "First Come First Serve");
			totalSeekTime[0] = temp.pop().intValue();
			temp.pop();
			fcfs_label.setVisible(true);
			fcfs_textqueue.setVisible(true);
			fcfs_textqueue.setText(temp.toString());
			anySelected = true;
		}
		if(sstf.isSelected()){
			temp = cbr.algorithmSelector(queue, ControllerBroker.SSTF);
			graphingData(temp, "Shortest Seek Time First");
			totalSeekTime[1] = temp.pop().intValue();
			temp.pop();
			sstf_label.setVisible(true);
			sstf_textqueue.setVisible(true);
			sstf_textqueue.setText(temp.toString());
			anySelected = true;
		}
		if(scan.isSelected()){
			temp = cbr.algorithmSelector(queue, ControllerBroker.SCAN);
			graphingData(temp, "Scan");
			totalSeekTime[2] = temp.pop().intValue();
			temp.pop();
			scan_label.setVisible(true);
			scan_textqueue.setVisible(true);
			scan_textqueue.setText(temp.toString());
			anySelected = true;
		}
		if(cscan.isSelected()){
			temp = cbr.algorithmSelector(queue, ControllerBroker.CSCAN);
			graphingData(temp, "CScan");
			totalSeekTime[3] = temp.pop().intValue();
			temp.pop();
			cscan_label.setVisible(true);
			cscan_textqueue.setVisible(true);
			cscan_textqueue.setText(temp.toString());
			anySelected = true;
		}
		if(look.isSelected()){
			temp = cbr.algorithmSelector(queue, ControllerBroker.LOOK);
			graphingData(temp, "Look");
			totalSeekTime[4] = temp.pop().intValue();
			temp.pop();
			look_label.setVisible(true);
			look_textqueue.setVisible(true);
			look_textqueue.setText(temp.toString());
			anySelected = true;
		}
		if(clook.isSelected()){
			temp = cbr.algorithmSelector(queue, ControllerBroker.CLOOK);
			graphingData(temp, "CLook");
			totalSeekTime[5] = temp.pop().intValue();
			temp.pop();
			clook_label.setVisible(true);
			clook_textqueue.setVisible(true);
			clook_textqueue.setText(temp.toString());
			anySelected = true;
		}
		if(anySelected){
			addAlgorithmSeries();
			String desc_algolSeekText = "";
			for(int i = 0; i < totalSeekTime.length; i++) {
				if(totalSeekTime[i] != -1) {
					desc_algolSeekText += descShowAlgolSeek[i] + ":\n" + "Total seek time: " + totalSeekTime[i] + "\n" + "Disk load: " + diskLoad((float) (totalSeekTime[i])) + "%\n\n";
				}
			}
			desc_label.setText("Algorithm information");
			description_text.setText(desc_algolSeekText);
			column_header_algol.setVisible(true);
			column_header_queue.setVisible(true);
		}
	}
	
	/**
	 * Computes the disk load
	 * @param tst The total seek time of the processed queue
	 * @return The disk load in maximum of 4 significant figures.
	 */
	private String diskLoad(float tst) {
		DecimalFormat df = new DecimalFormat("##.##");
		df.setRoundingMode(RoundingMode.CEILING);
		return df.format((float) (((tst / (float) (queue.size() - 1)) / (float) (cylinder + 1)) * 100.00));
	}

	/**
	 * Checks if any one algorithm is selected, else throws an alert
	 */
	private void algorithmOptionCheck(){
		if(fcfs.isSelected()==false && sstf.isSelected()==false && scan.isSelected()==false && cscan.isSelected()==false && look.isSelected()==false && clook.isSelected()==false){
			message="Kindly select at least 1 algorithm to run simulation."; 
			errorMessage();
		}
	}	    

	/**
	 * Adds a series of data nodes into a LinkedList as temporary storage because of multi algorithm execution.
	 * @see javafx.application.Controller#algorithmOptions()
	 * @param tmp The input queue
	 * @param operation The label for the series corresponding to the computing algorithm
	 */
	private void graphingData(LinkedList<Integer> tmp, String operation) {
		int i = 0;
		ListIterator<Integer> itTmp = tmp.listIterator();
		itTmp.next(); //Ignore total seek time here
		itTmp.next(); //Ignore tail value

		lsSeries.add(new XYChart.Series<Integer,Integer>());
		lsSeries.get(seriesCounter).setName(operation);
		while(itTmp.hasNext()) {
			lsSeries.get(seriesCounter).getData().add(new Data<Integer,Integer>(i++, itTmp.next()));
		}
		seriesCounter++;
	}

	/**
	 * Adds the LinkedList with line chart series into the LineChart object.
	 */
	private void addAlgorithmSeries(){
		for(int i = 0; i < seriesCounter; i++)
			custom_linechart.getData().add(lsSeries.get(i));
	}


	/**
	 * For printing extra text in the Description TextArea
	 * @param des The extra string
	 */
	private void printText(String des){
		description_text.setText(
				"This is a simple disk scheduling simulator to\n" 
						+ "compare the efficiency of disk scheduling algorithms.\n\n" 
						+ "Multiple selection for comparison is supported.\n\n"
						+ "Reset All to clear all parameters.\n"
						+ "Simulate to run simulation.\n\n"
						+ "Protip: You can click Simulate multiple times to generate\n" 
						+ "different outputs.\n\n"
						+des
				);	
	}


	/**
	 * Displays alert box along with the error message	
	 */
	public void errorMessage(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setContentText(message+"\n"+message2);
		printText("");
		alert.showAndWait();
	}


	/**
	 * Resets the interface to factory default interface
	 */
	@FXML
	public void reset() {
		//Resets everything including the class values as well
		resetInternal();
		
		//Flush the user input TextArea
		cylinderamount.clear();
		currenthead.clear();
		jobreq.clear();
		
		//Deselects all algorithm selection
		fcfs.setSelected(false);
		sstf.setSelected(false);
		scan.setSelected(false);
		cscan.setSelected(false);
		look.setSelected(false);
		clook.setSelected(false);
		
		//Resets to custom
		cylinderamount.setEditable(true);
		currenthead.setEditable(true);
		jobreq.setEditable(true);
		
		//Hide the processed queue information
		grid_pane.setVisible(false);
		
		//Flush all error messages
		message="";
		message2="";
		
		//Reset the ComboBox to default message
		load_combo.setValue(load_message);
		
		//Hide input queue TextArea
		input_queuelabel.setVisible(false);
		input_queuetext.setVisible(false);
		input_queuetext.clear();
		
		//Reset description
		desc_label.setText("Description");
		printText("");
	}
	
	/**
	 * Resets a portion of the interface and Controller fields
	 */
	private void resetInternal(){
		//Clear the graph data
		lsSeries.clear();
		custom_linechart.getData().clear();
		//Clear the user input queue
		queue.clear();
		seriesCounter = 0;
		
		//Hide the processed queue information
		fcfs_label.setVisible(false);
		sstf_label.setVisible(false);
		scan_label.setVisible(false);
		cscan_label.setVisible(false);
		look_label.setVisible(false);
		clook_label.setVisible(false);

		fcfs_textqueue.setVisible(false);
		sstf_textqueue.setVisible(false);
		scan_textqueue.setVisible(false);
		cscan_textqueue.setVisible(false);
		look_textqueue.setVisible(false);
		clook_textqueue.setVisible(false);

		column_header_algol.setVisible(false);
		column_header_queue.setVisible(false);
	}

	/**
	 * Executes the simulation and does input validation
	 */
	@FXML
	public void simulate(){
		try{
			//Readies the interface for simulation
			grid_pane.setVisible(true);
			resetInternal();
			algorithmOptionCheck();
			
			//The input queue
			cylinder = Integer.parseInt(cylinderamount.getText().trim());
			head = Integer.parseInt(currenthead.getText().trim());
			if(head > cylinder || cylinder < 0 || head < 0) {
				message="Total number of cylinder must be non-zero and current head must be less than total number of cylinder and non-zero";
				errorMessage();
			}
			else {
				boolean isInvalid = false;
				queue.add(head);
				for(String temp:jobreq.getText().replaceAll("\\s+", "").split(",")){
					int foo = Integer.parseInt(temp);
					if(foo>cylinder || foo<0){
						message="Job request not in range of cylinder or current head is more than total number of cylinder";
						message2="You have to separate each request with a comma";
						errorMessage();
						isInvalid = true;
						break;
					}
					else
						queue.add(Integer.parseInt(temp));
				}
				if(!isInvalid) {
					queue.add(cylinder);
					algorithmOptions();	
					customgraph_pane.setVisible(true);
					input_queuelabel.setVisible(true);
					input_queuetext.setVisible(true);
					input_queuetext.setText(queue.toString() + "\n" + "Initial disk head position: " + queue.getFirst() + "\n" + "Total number of cylinders: " + (queue.getLast() + 1));
					input_queuetext.setTooltip(new Tooltip("The last element in the queue is the total number of cylinders. This is how the program read your input."));
				}
				else {
					input_queuelabel.setVisible(true);
					input_queuetext.setVisible(true);
					input_queuetext.setText("Invalid queue input!");
				}
			}
		}catch(NumberFormatException e){
			message="Please make sure all text input is in integer.\nJob request must be filled and must only contain integers separated by comma.";
			errorMessage();
		}
	}
}
