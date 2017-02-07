package view;

import java.util.Collections;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class ListController {
	      
	   @FXML         
	   ListView<String> listView;                

	   private ObservableList<String> obsList;              
	  
	   public void start(Stage mainStage) {                
	      // create an ObservableList 
	      // from an ArrayList 
			
	      obsList = FXCollections.observableArrayList(                               
	                 "Giants",                               
	                 "Patriots",
                     "49ers",
                     "Rams",
                     "Packers",
                     "Colts",
                     "Cowboys",
                     "Broncos",
                     "Vikings",
                     "Dolphins",
                     "Titans",
                     "Seahawks",
                     "Steelers",
	                 "Jaguars");  
	      Collections.sort(obsList, String.CASE_INSENSITIVE_ORDER);
	      listView.setItems(obsList);  
	      
	      // select the first item
	      listView.getSelectionModel().select(0);

	      // set listener for the items
	      listView
	        .getSelectionModel()
	        .selectedIndexProperty()
	        .addListener(
	           (obs, oldVal, newVal) -> 
	               showItemInputDialog(mainStage));
	   }
	   
	   private void showItem(Stage mainStage) {                
		   Alert alert = 
				   new Alert(AlertType.INFORMATION);
		   alert.initOwner(mainStage);
		   alert.setTitle("List Item");
		   alert.setHeaderText(
				   "Selected list item properties");

		   String content = "Index: " + 
				   listView.getSelectionModel()
		   .getSelectedIndex() + 
		   "\nValue: " + 
		   listView.getSelectionModel()
		   .getSelectedItem();

		   alert.setContentText(content);
		   alert.showAndWait();
	   }
	   
	   @FXML protected void handleButtonAction(ActionEvent event) {
	        obsList.set(0, "WORD");
	    }
	   
	   private void showItemInputDialog(Stage mainStage) {                
		   String item = listView.getSelectionModel().getSelectedItem();
		   int index = listView.getSelectionModel().getSelectedIndex();

		   TextInputDialog dialog = new TextInputDialog(item);
		   dialog.initOwner(mainStage); dialog.setTitle("List Item");
		   dialog.setHeaderText("Selected Item (Index: " + index + ")");
		   dialog.setContentText("Enter name: ");

		   Optional<String> result = dialog.showAndWait();
		   if (result.isPresent()) { 
			   obsList.set(index, result.get());
		   }
	   }


	}


