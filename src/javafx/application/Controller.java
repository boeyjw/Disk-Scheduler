package javafx.application;

import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

import osc.diskscheduling.algorithm.ControllerBroker;


/**
 * @author CHIA
 *
 */
public class Controller implements Initializable {// implements EventHandler<ActionEvent>{

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
		
		
		//text 
		@FXML
		private TextArea description_text;
		@FXML
		private Label customgraph_label;
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
		
		//pane
		@FXML
		private Pane custom_pane;
		@FXML
		private Pane fcfs_pane;
		@FXML
		private Pane sstf_pane;
		@FXML
		private Pane scan_pane;
		@FXML
		private Pane cscan_pane;
		@FXML
		private Pane look_pane;
		@FXML
		private Pane clook_pane;
		@FXML
		private Pane load_pane;

		@FXML
		private Pane customgraph_pane;
		
		//parameters
		private static String message="";
		private static String message2="";
		private LinkedList<Integer> queue = new LinkedList<Integer>();
		private int cylinder;
		private int head; 
		private static int rflag=1;
		private static int sflag=-1;
		
		//graph
        @FXML
	    private CategoryAxis xAxis;
	    @FXML
	    private NumberAxis yAxis;
	    @FXML
	    private LineChart<String, Number> custom_linechart;
	    
	    //progress bar
	    @FXML
	    private ProgressBar load_bar;
	    
	    private static int seriesCounter = 0;
	    private static int requestCount = 0;	    

        private ControllerBroker cbr = new ControllerBroker();
        private static LinkedList<LinkedList<XYChart.Series<String,Number>>> lsAlgorithm = new LinkedList<LinkedList<XYChart.Series<String,Number>>>(); 
	    private LinkedList<XYChart.Series<String,Number>> lsSeries = new LinkedList<XYChart.Series<String,Number>>();
		
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			load_combo.setItems(FXCollections.observableArrayList("Light","Medium","Heavy","Custom"));
			load_combo.setValue("Pick a load type");			
		}
		
		@FXML
		private void load(){
			String loadType = load_combo.getValue();
			if(loadType.compareTo("Light") == 0){
				cylinderamount.setText("199");
	    		currenthead.setText("50");
	    		jobreq.setText("42,36,76,98,80,72,52,40,32,12");
			}else if(loadType.compareTo("Medium") == 0){
				cylinderamount.setText("999");
	    		currenthead.setText("500");
	    		jobreq.setText("421,125,624,524,125,32,125,324,698,203,421,698,825,421");
			}else if(loadType.compareTo("Heavy") == 0){
				cylinderamount.setText("999");
	    		currenthead.setText("500");
	    		jobreq.setText("987,123,896,214,987,123,998,2,875,365,993,2,785,12,987,56,865,12,998,123");
			}else if(loadType.compareTo("Custom") == 0){
				cylinderamount.clear();
				currenthead.clear();
				jobreq.clear();
			}else{
				message="Kindly select a load type";
				errorMessage();
			}
		}

	    
	    //handle input algorithm option
	    private void algorithmOptions(LineChart<String, Number> linechart){
	    	LinkedList<Integer> temp = new LinkedList<Integer>();
	    	if(fcfs.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.FCFS);
	    		graphingData(temp, "First Come First Serve");
	    		addAlgorithmSeries(linechart);
	    		fcfs_label.setText(temp.toString());
	    		fcfs_pane.setVisible(true);
	    	}
	    	if(sstf.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.SSTF);
	    		graphingData(temp, "Shortest Seek Time First");
	    		addAlgorithmSeries(linechart);
	    		sstf_label.setText(temp.toString());
				sstf_pane.setVisible(true);
	    	}
	    	if(scan.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.SCAN);
	    		graphingData(temp, "Scan");
	    		addAlgorithmSeries(linechart);
	    		scan_label.setText(temp.toString());
				scan_pane.setVisible(true);
	    	}
	    	if(cscan.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.CSCAN);
	    		graphingData(temp, "CScan");
	    		addAlgorithmSeries(linechart);
	    		cscan_label.setText(temp.toString());
				cscan_pane.setVisible(true);
	    	}
	    	if(look.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.LOOK);
	    		graphingData(temp, "Look");
	    		addAlgorithmSeries(linechart);
	    		look_label.setText(temp.toString());
				look_pane.setVisible(true);
	    	}
	    	if(clook.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.CLOOK);
	    		graphingData(temp, "CLook");
	    		addAlgorithmSeries(linechart);
	    		clook_label.setText(temp.toString());
				clook_pane.setVisible(true);
	    	}	  
	    }
	   
	    
	    //check if any algorithm is selected, display error message if none selected
	    private void algorithmOptionCheck(){
	    	if(fcfs.isSelected()==false && sstf.isSelected()==false && scan.isSelected()==false && cscan.isSelected()==false && look.isSelected()==false && clook.isSelected()==false){
	    		message="Kindly select at least 1 algorithm to run simulation."; 
	    		errorMessage();
	    	}
	    }
	    
	    
	    //display queue which is sorted by algorithm
	    private LinkedList<Integer> algorithmQueue(LinkedList<Integer> temp){
	    	return temp;
	    }
	    
	    
	    //to put the data nodes into a series 
	    private void graphingData(LinkedList<Integer> tmp, String operation) {
	    	char i = '0';
	    	ListIterator<Integer> itTmp = tmp.listIterator();
	    	itTmp.next();
	    	itTmp.next();
	    	
	    	lsSeries.add(new XYChart.Series<String,Number>());
	    	lsSeries.get(seriesCounter).setName(operation);
	    	while(itTmp.hasNext()) {
	    		lsSeries.get(seriesCounter).getData().add(new Data<String, Number>(String.valueOf(i), itTmp.next()));
	    		i += 1;
	    	}
	    	seriesCounter++;
	    }
	    
	    
	    //remove all nodes on graph
	    private void clearGraph(){
	    	custom_linechart.getData().clear();
	    }
	    
	    
	    //put resulting series for algorithm into a list and add data series to load linechart 
	    private void addAlgorithmSeries(LineChart<String, Number> linechart){
	    	lsAlgorithm.add(lsSeries);
	    	for(int i = 0; i < seriesCounter; i++)
	    		linechart.getData().add(lsSeries.get(i));
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
			reset();
		}
	    
	    
		//to clear all values to default (null)
	    @FXML
		public void reset() {
			if(rflag==sflag){
				rflag=-rflag;

				lsSeries.clear();
				lsAlgorithm.clear();
				clearGraph();
								
				cylinderamount.clear();
				currenthead.clear();
				jobreq.clear();
				queue.clear();
				seriesCounter = 0;
				requestCount = 0;
				
				//customgraph_pane.setVisible(false);
				
				fcfs.setSelected(false);
				sstf.setSelected(false);
				scan.setSelected(false);
				cscan.setSelected(false);
				look.setSelected(false);
				clook.setSelected(false);
				
				fcfs_pane.setVisible(false);
				sstf_pane.setVisible(false);
				scan_pane.setVisible(false);
				cscan_pane.setVisible(false);
				look_pane.setVisible(false);
				clook_pane.setVisible(false);
								
                message="";
                message2="";
                
				printText("");			
			}	
		}
	    
	    
		//to pass parameters and run simulation
	    @FXML
		public void simulate(){
	    	try{
				if(rflag!=sflag){
					sflag = -sflag;
					algorithmOptionCheck();	
	        		cylinder = Integer.parseInt(cylinderamount.getText());
					head = Integer.parseInt(currenthead.getText());
					queue.add(head);
	        		for(String temp:jobreq.getText().replaceAll("\\s+", "").split(",")){
	        			int foo = Integer.parseInt(temp);
	        			if(foo>cylinder || foo<0){
	        				message="Job request not in range of cylinder";
	        				errorMessage();
	        			}
	        			else
	        				queue.add(Integer.parseInt(temp));
	        			}
	        		queue.add(cylinder);
	        		algorithmOptions(custom_linechart);	
					customgraph_pane.setVisible(true);
					printText("Kindly reset before new simulation.");
					customgraph_label.setText("Custom Queue: "+" [ "+jobreq.getText()+" ] ");	
				}					
        	}catch(NumberFormatException e){
        		message="Please make sure all text input is in integer.\nJob request must be filled and must only contain integers separated by comma.";
        		errorMessage();
        	}
		}
	    
		
}