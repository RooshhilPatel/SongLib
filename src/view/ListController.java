package view;

import java.util.Optional;

//import application.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class ListController {
	      
	   @FXML ListView<String> listView;											//strings to be displayed in the listView
	   
	   //initializes the fx:id from List.fxml to be used by button
	   @FXML private TextField songField;
	   @FXML private TextField albumField;
	   @FXML private TextField artistField;
	   @FXML private TextField yearField;

	   private ObservableList<String> obsList;              					//private observable list
	  
	   public void start(Stage mainStage) {                
	      // create an ObservableList 
	      // from an ArrayList 
		   application.SongLib.songToStringList(application.SongLib.songList);
		   obsList = FXCollections.observableArrayList(application.SongLib.songNameList);
		    
	      
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
	   
	   //KEEEP THIS DO NOT DELETE
	    /*
	   @FXML private void showItem() {                

		   String content = "Index: " + listView.getSelectionModel().getSelectedIndex() + 
				   "\nValue: " + listView.getSelectionModel().getSelectedItem();
		   songInfo.setText(content);

	   }*/
	   @FXML private void addButtonAction(ActionEvent event) {
		   	String newSong = songField.getText();
	        application.SongLib.insertToList(newSong);
			obsList.add(newSong);
	   }
	   
	   @FXML private void deleteButtonAction(ActionEvent event) {
		   //idk y the delete error isn't coming
		   if(application.SongLib.songList == null){ 
			   Alert alert = new Alert(AlertType.WARNING);
			   alert.setTitle("Deleting Error!");
			   alert.setHeaderText("You are trying to delete from an empty list");
			   alert.setContentText(null);
			   alert.showAndWait();
		   }
	        obsList.remove(songField.getText());
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


