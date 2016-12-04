package javafx.application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;

import osc.diskscheduling.algorithm.ControllerBroker;


public class Controller implements EventHandler<ActionEvent>{

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
		private CheckBox light;
		@FXML
		private CheckBox medium;
		@FXML
		private CheckBox heavy;
		@FXML
		private CheckBox custom;
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
		
		//label 
		@FXML
		private Label description;
		@FXML
		private Label customgraph_label;
		
		//pane
		@FXML
		private Pane custom_pane;
		@FXML
		private Pane lightgraph_pane;
		@FXML
		private Pane mediumgraph_pane;
		@FXML
		private Pane heavygraph_pane;
		@FXML
		private Pane customgraph_pane;
		
		//parameters
		private static String message="";
		private static String message2="";
		private static String des="";
		private static int cylinder;
		private static int head;
		private LinkedList<Integer> queue = new LinkedList<Integer>();
		
		//graph
        @FXML
	    private CategoryAxis xAxis;
	    @FXML
	    private NumberAxis yAxis;
	    @FXML
	    private LineChart<String, Number> light_linechart;
	    @FXML
	    private LineChart<String, Number> medium_linechart;
	    @FXML
	    private LineChart<String, Number> heavy_linechart;
	    @FXML
	    private LineChart<String, Number> custom_linechart;
	    private static int seriesCounter = 0;

        private ControllerBroker cbr = new ControllerBroker();
        private ArrayList<LinkedList<LinkedList<XYChart.Series<String,Number>>>> lsLoad = new ArrayList<LinkedList<LinkedList<XYChart.Series<String,Number>>>>(); 
        private LinkedList<LinkedList<XYChart.Series<String,Number>>> lsAlgorithm = new LinkedList<LinkedList<XYChart.Series<String,Number>>>(); 
	    private LinkedList<XYChart.Series<String,Number>> lsSeries = new LinkedList<XYChart.Series<String,Number>>(); 
  
	    
		
		//handle input of load options
	    private void loadOptions(){

	    	if(fcfs.isSelected()==false && sstf.isSelected()==false && scan.isSelected()==false && cscan.isSelected()==false && look.isSelected()==false && clook.isSelected()==false)
	    		message2="Kindly select at least 1 algorithm to run simulation."; 
	    	
	    	if(light.isSelected()==true){
	        	int[] q = {50, 61, 74, 79, 62, 78, 60, 51, 48, 40, 199}; 
	        	helper(q,light_linechart);
				lightgraph_pane.setVisible(true);
				algorithmOptionCheck();
	        } 
	        if(medium.isSelected()==true){
	        	int[] q = {50, 63, 77, 89, 132, 103, 89, 85, 46, 35, 199};
	        	helper(q,medium_linechart);
				mediumgraph_pane.setVisible(true);
				algorithmOptionCheck();	
	        }
	        if(heavy.isSelected()==true){
	        	int[] q = {50, 154, 32, 101, 189, 23, 101, 32, 89, 154, 32, 199};
	        	helper(q,heavy_linechart); 
				heavygraph_pane.setVisible(true);
				algorithmOptionCheck();	
	        }
	        if(custom.isSelected()==true){
	        	try{
	        		cylinder = Integer.parseInt(cylinderamount.getText());
					head = Integer.parseInt(currenthead.getText());
	        		for(String temp:jobreq.getText().replaceAll("\\s+", "").split(","))
		        		queue.add(Integer.parseInt(temp));	
	        		algorithmOptions(custom_linechart);	
	        		customgraph_label.setText("Custom Load"+queue);
					customgraph_pane.setVisible(true);
					algorithmOptionCheck();	
	        	}catch(NumberFormatException|NullPointerException e){
	        		message="Please make sure all text input is in integer.\nJob request must be filled and must only contain integers separated by comma.";
	        		errorMessage();
	        	}	
	        }
	        if(light.isSelected()==false && medium.isSelected()==false && heavy.isSelected()==false && custom.isSelected()==false){
	        	message="Kindly select at least 1 Load Type to run simulation.";
	        	errorMessage();
			}
	    }
	    
	    
	    //get head and no. of cylinder for preset load
	    private void helper(int[] q, LineChart<String, Number> linechart){
	    	queue.clear();
	    	head=q[0];
        	cylinder=200;
        	for(int i=0; i<q.length; i++) 
        		queue.add(q[i]); 
        	queue.addFirst(head);
			queue.addLast(cylinder);
			algorithmOptions(linechart);
			addLoadSeries();
	    }
	    
	    
	    //check if any algorithm is selected, display error message if none selected
	    private void algorithmOptionCheck(){
	    	if(fcfs.isSelected()==false && sstf.isSelected()==false && scan.isSelected()==false && cscan.isSelected()==false && look.isSelected()==false && clook.isSelected()==false){
	    		message="Kindly select at least 1 algorithm to run simulation."; 
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
	    	}
	    	if(sstf.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.SSTF);
	    		graphingData(temp, "Shortest Seek Time First");
	    		addAlgorithmSeries(linechart);
	    	}
	    	if(scan.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.SCAN);
	    		graphingData(temp, "Scan");
	    		addAlgorithmSeries(linechart);
	    	}
	    	if(cscan.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.CSCAN);
	    		graphingData(temp, "CScan");
	    		addAlgorithmSeries(linechart);
	    	}
	    	if(look.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.LOOK);
	    		graphingData(temp, "Look");
	    		addAlgorithmSeries(linechart);
	    	}
	    	if(clook.isSelected()==true){
	    		temp = cbr.algorithmSelector(queue, ControllerBroker.CLOOK);
	    		graphingData(temp, "CLook");
	    		addAlgorithmSeries(linechart);
	    	}	  
	    }
	      
	    
	    //to put the data nodes into a series 
	    private void graphingData(LinkedList<Integer> tmp, String operation) {
	    	int totalSeek = tmp.pop();
	    	int tail = tmp.pop();
	    	char i = '0';
	    	ListIterator<Integer> itTmp = tmp.listIterator();
	    	
	    	lsSeries.add(new XYChart.Series<String,Number>());
	    	lsSeries.get(seriesCounter).setName(operation);
	    	while(itTmp.hasNext()) {
	    		lsSeries.get(seriesCounter).getData().add(new Data<String, Number>(String.valueOf(i), itTmp.next()));
	    		i += 1;
	    	}
	    	seriesCounter++;
	    }
	    
	    
	    //put resulting series for algorithm into a list and add data series to load linechart 
	    private void addAlgorithmSeries(LineChart linechart){
	    	lsAlgorithm.add(lsSeries);
	    	for(int i = 0; i < seriesCounter; i++)
	    		linechart.getData().add(lsSeries.get(i));
	    }

	    
	    //to put multiple algorithms results into a list
	    private void addLoadSeries(){
	    	lsLoad.add(lsAlgorithm);
	    }

	    
		//to clear all values to default (null)
	    @FXML
		public void reset(ActionEvent event) {
			
			if(event.getSource() == reset){
				int i = 0;

				lsLoad.clear();
				
				cylinderamount.clear();
				currenthead.clear();
				jobreq.clear();
				queue.clear();
				
				custom_pane.setVisible(false);
				lightgraph_pane.setVisible(false);
				mediumgraph_pane.setVisible(false);
				heavygraph_pane.setVisible(false);
				customgraph_pane.setVisible(false);
				
				light.setSelected(false);
				medium.setSelected(false);
				heavy.setSelected(false);
				custom.setSelected(false);
				fcfs.setSelected(false);
				sstf.setSelected(false);
				scan.setSelected(false);
				cscan.setSelected(false);
				look.setSelected(false);
				clook.setSelected(false);
				
                message="";
                message2="";
                
				description.setText(
						"This is a simple program to simulate and visualise disk scheduling.\n"
						+"Select a load type and algorithm to run.\n"
						+"Multiple selection for comparison is supported.\n\n"
						+des
						);				
			}	
		}
	    
	    
		//to pass parameters to backend
		//run simulation
	    @FXML
		public void simulate(ActionEvent event){
				loadOptions();
		}
		
		//display error dialogue	
		public void errorMessage(){
	        Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setContentText(message+"\n"+message2);
			alert.showAndWait();

			lightgraph_pane.setVisible(false);
			mediumgraph_pane.setVisible(false);
			heavygraph_pane.setVisible(false);
			customgraph_pane.setVisible(false);
			
			//flush message
			message="";
			message2="";
		}
		
		
		//make set no. of cylinder,current head and job request queue visible
		@Override
		public void handle(ActionEvent event) {
    		custom_pane.setVisible(true);	
		}
		
		
}
