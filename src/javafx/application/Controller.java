package javafx.application;

import java.net.URL;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import osc.diskscheduling.algorithm.ControllerBroker;


/**
 * @author CHIA
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
		
		//graph
		@FXML
		private Pane customgraph_pane;
		@FXML
		private NumberAxis xaxis;
		@FXML
		private NumberAxis yaxis;
		
		//parameters
		private static String message="";
		private static String message2="";
		private static final String load_message="Pick a load type (Default: Custom)";
		private LinkedList<Integer> queue = new LinkedList<Integer>();
		private int cylinder;
		private int head; 

	    @FXML
	    private LineChart<Integer, Integer> custom_linechart;
	    private static int seriesCounter = 0; 

        private ControllerBroker cbr = new ControllerBroker();
	    private LinkedList<XYChart.Series<Integer, Integer>> lsSeries = new LinkedList<XYChart.Series<Integer, Integer>>();
		
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			load_combo.setItems(FXCollections.observableArrayList("Light","Medium","Heavy","Custom"));
			load_combo.setValue(load_message);
			
			description_text.setEditable(false);
			
			xaxis.setLabel("Request");
			yaxis.setLabel("Cylinder");
			
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
			
			fcfs_textqueue.setEditable(false);
			sstf_textqueue.setEditable(false);
			scan_textqueue.setEditable(false);
			cscan_textqueue.setEditable(false);
			look_textqueue.setEditable(false);
			clook_textqueue.setEditable(false);
		}
		
		@FXML
		private void load(){
			String loadType = load_combo.getValue();
			if(loadType.compareTo("Light") == 0){
				cylinderamount.setEditable(false);
				currenthead.setEditable(false);
				jobreq.setEditable(false);
				cylinderamount.setText("199");
	    		currenthead.setText("50");
	    		jobreq.setText("42,36,76,98,80,72,52,40,32,12");
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

	    
		//handle input algorithm option
	    private void algorithmOptions(){
	    	LinkedList<Integer> temp = new LinkedList<Integer>();
	    	int[] totalSeekTime = new int[6];
	    	String[] descShowAlgolSeek = new String[] {"FCFS", "SSTF", "Scan", "CScan", "Look", "CLook"};
	    	Arrays.fill(totalSeekTime, -1);
	    	boolean anySelected = false;
	    	if(fcfs.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.FCFS);
	    		graphingData(temp, "First Come First Serve");
	    		totalSeekTime[0] = temp.pop().intValue();
	    		temp.pop();
	    		fcfs_label.setVisible(true);
	    		fcfs_textqueue.setVisible(true);
	    		fcfs_textqueue.setText(temp.toString());
	    		anySelected = true;
	    	}
	    	if(sstf.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.SSTF);
	    		graphingData(temp, "Shortest Seek Time First");
	    		totalSeekTime[1] = temp.pop().intValue();
	    		temp.pop();
	    		sstf_label.setVisible(true);
	    		sstf_textqueue.setVisible(true);
	    		sstf_textqueue.setText(temp.toString());
				anySelected = true;
	    	}
	    	if(scan.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.SCAN);
	    		graphingData(temp, "Scan");
	    		totalSeekTime[2] = temp.pop().intValue();
	    		temp.pop();
	    		scan_label.setVisible(true);
	    		scan_textqueue.setVisible(true);
	    		scan_textqueue.setText(temp.toString());
				anySelected = true;
	    	}
	    	if(cscan.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.CSCAN);
	    		graphingData(temp, "CScan");
	    		totalSeekTime[3] = temp.pop().intValue();
	    		temp.pop();
	    		cscan_label.setVisible(true);
	    		cscan_textqueue.setVisible(true);
	    		cscan_textqueue.setText(temp.toString());
				anySelected = true;
	    	}
	    	if(look.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.LOOK);
	    		graphingData(temp, "Look");
	    		totalSeekTime[4] = temp.pop().intValue();
	    		temp.pop();
	    		look_label.setVisible(true);
	    		look_textqueue.setVisible(true);
	    		look_textqueue.setText(temp.toString());
				anySelected = true;
	    	}
	    	if(clook.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.CLOOK);
	    		graphingData(temp, "CLook");
	    		totalSeekTime[5] = temp.pop().intValue();
	    		temp.pop();
	    		clook_label.setVisible(true);
	    		clook_textqueue.setVisible(true);
	    		clook_textqueue.setText(temp.toString());
				anySelected = true;
	    	}
	    	if(anySelected==true){
	    		addAlgorithmSeries();
	    		String desc_algolSeekText = "";
	    		for(int i = 0; i < totalSeekTime.length; i++) {
	    			if(totalSeekTime[i] != -1) {
	    				desc_algolSeekText += descShowAlgolSeek[i] + ":\n" + "Total seek time: " + totalSeekTime[i] + "\n\n";
	    			}
	    		}
	    		desc_label.setText("Algorithm information");
	    		description_text.setText(desc_algolSeekText);
	    	}
	    }
	    
	    //check if any algorithm is selected, display error message if none selected
	    private void algorithmOptionCheck(){
	    	if(fcfs.isSelected()==false && sstf.isSelected()==false && scan.isSelected()==false && cscan.isSelected()==false && look.isSelected()==false && clook.isSelected()==false){
	    		message="Kindly select at least 1 algorithm to run simulation."; 
	    		errorMessage();
	    	}
	    }	    
	    
	    //to put the data nodes into a series 
	    private void graphingData(LinkedList<Integer> tmp, String operation) {
	    	int i = 0;
	    	ListIterator<Integer> itTmp = tmp.listIterator();
	    	itTmp.next();
	    	itTmp.next();
	    	
	    	lsSeries.add(new XYChart.Series<Integer,Integer>());
	    	lsSeries.get(seriesCounter).setName(operation);
	    	while(itTmp.hasNext()) {
	    		lsSeries.get(seriesCounter).getData().add(new Data<Integer,Integer>(i++, itTmp.next()));
	    	}
	    	seriesCounter++;
	    }
	    
	    //put resulting series for algorithm into a list and add data series to load linechart 
	    private void addAlgorithmSeries(){
	    	for(int i = 0; i < seriesCounter; i++)
	    		custom_linechart.getData().add(lsSeries.get(i));
	    }

	    
	    //for printing description in text area
	    private void printText(Object des){
	    	description_text.setText(
					"This is a simple disk scheduling simulator to compare the efficiency\n"
					+ "of disk scheduling algorithms.\n\n" 
					+ "Multiple selection for comparison is supported.\n\n"
					+ "Reset All to clear all parameters.\n"
					+ "Simulate to run simulation.\n"
					+des
				);	
	    }
	    
	    
		//display error dialogue	
		public void errorMessage(){
	        Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText(message+"\n"+message2);
			alert.showAndWait();
		}
	    
	    
		//to clear all values to default (null)
	    @FXML
		public void reset() {
				resetInternal();
								
				cylinderamount.clear();
				currenthead.clear();
				jobreq.clear();
								
				//customgraph_pane.setVisible(false);
				
				fcfs.setSelected(false);
				sstf.setSelected(false);
				scan.setSelected(false);
				cscan.setSelected(false);
				look.setSelected(false);
				clook.setSelected(false);
				
				cylinderamount.setEditable(true);
				currenthead.setEditable(true);
				jobreq.setEditable(true);
				
				grid_pane.setVisible(false);
					
                message="";
                message2="";
                
                desc_label.setText("Description");
                load_combo.setValue(load_message);
				printText("");
		}
	    
	    private void resetInternal(){
	    	lsSeries.clear();
			custom_linechart.getData().clear();
			queue.clear();
			seriesCounter = 0;
			
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
	    }
	    
		//to pass parameters and run simulation
	    @FXML
		public void simulate(){
	    	boolean isInvalid = false;
	    	try{
	    			grid_pane.setVisible(true);
					resetInternal();
	    			algorithmOptionCheck();	
	        		cylinder = Integer.parseInt(cylinderamount.getText().trim());
					head = Integer.parseInt(currenthead.getText().trim());
					if(head > cylinder) {
						isInvalid = true;
					}
					else {
						queue.add(head);
					}
	        		for(String temp:jobreq.getText().replaceAll("\\s+", "").split(",")){
	        			int foo = Integer.parseInt(temp);
	        			if(foo>cylinder || foo<0 || isInvalid){
	        				message="Job request not in range of cylinder or current head is more than total number of cylinder";
	        				errorMessage();
	        				isInvalid = true;
	        				break;
	        			}
	        			else
	        				queue.add(Integer.parseInt(temp));
	        		}
	        		queue.add(cylinder);
	        		if(!isInvalid) {
	        			algorithmOptions();	
						customgraph_pane.setVisible(true);	
	        		}
        	}catch(NumberFormatException e){
        		message="Please make sure all text input is in integer.\nJob request must be filled and must only contain integers separated by comma.";
        		errorMessage();
        	}
		}
}
