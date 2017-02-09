/* Rooshhil Patel and Akhila Narayan */

package view;

import java.util.Optional;

//import application.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ListController {
	      
	   @FXML ListView<String> listView;											//strings to be displayed in the listView
	   
	   //initializes the fx:id from List.fxml to be used by button
	   @FXML private TextField songField;
	   @FXML private TextField albumField;
	   @FXML private TextField artistField;
	   @FXML private TextField yearField;
	   @FXML private VBox editView;
	   @FXML private Button editButton;
	   @FXML private Text songInfo;
	   private int edit = 0;													//0 is edit, 1 is save

	   private ObservableList<String> obsList;              					//private observable list
	   private int numOfSongs = 0;
	   
	   public void start(Stage mainStage) {                
	      // create an ObservableList 
	      // from an ArrayList 
		   application.SongLib.songToStringList(application.SongLib.songList);
		   obsList = FXCollections.observableArrayList(application.SongLib.songNameList);
		   
		   editView.setVisible(false);
		   String cssLayout = "-fx-border-color: red;\n" +
                   "-fx-border-insets: 5;\n" +
                   "-fx-border-width: 3;\n" +
                   "-fx-border-style: dashed;\n"; 
		   editView.setStyle(cssLayout);
		    
	      
	      listView.setItems(obsList);  
	      
	      // select the first item
	      listView.getSelectionModel().select(0);

	      // set listener for the items
	      listView
	        .getSelectionModel()
	        .selectedIndexProperty()
	        .addListener((obs, oldVal, newVal) -> showItem());
	   }
	   
	   @FXML private void showItem() {   
	   	   String item = listView.getSelectionModel().getSelectedItem();
		   int index = listView.getSelectionModel().getSelectedIndex();
		   
		   String album = application.SongLib.songList.get(0).getSongAlbum();
		   String artist = application.SongLib.songList.get(0).getSongArtist();
		   int year = application.SongLib.songList.get(0).getSongYear();

		   String content = "Index: " + index + "\nSong: " + item + 
				   "\nArtist: " + artist + "\nAlbum: " + album + "\nYear: " + year;
		   songInfo.setText(content);
	   }
	   
	   public boolean isNumeric(String s) {  
		    return s.matches("[-+]?\\d*\\.?\\d+");  
		}
	   
	   @FXML private void clearItems() {   
		   String content = "Index: " + "\nSong: " +
				   "\nArtist: " + "\nAlbum: " + "\nYear: ";
		   songInfo.setText(content);
	   }
	   
	   @FXML private void addButtonAction(ActionEvent event) {
		   //initiate everything
		   	String newSong = songField.getText();
		   	String newArtist = artistField.getText();
		   	String newAlbum = albumField.getText();
		   	String year = yearField.getText();
		   	int newYear = -1; 													//default year to -1 if not entered
		   	
		   	//if adding same 2 songs
		   	int i;
		   	for(i = 0; i < obsList.size(); i++) {
			   	if(songField.getText().compareToIgnoreCase(obsList.get(i)) == 0 && artistField.getText().compareToIgnoreCase(obsList.get(i)) == 0){
			   		Alert alert = new Alert(AlertType.WARNING);
			   		alert.setTitle("Adding Error!");
			   		alert.setHeaderText("This song already exists");
			   		alert.setContentText(null);
			   		alert.showAndWait();
			   		return;
			   	}
		   	}
		   	
		   	//error if song and/or artist fields are empty
		   	if(songField.getText().isEmpty() == true || artistField.getText().isEmpty() == true){
		   		Alert alert = new Alert(AlertType.WARNING);
		   		alert.setTitle("Adding Error!");
		   		alert.setHeaderText("Must enter a song and artist");
		   		alert.setContentText(null);
		   		alert.showAndWait();
		   		return;
		   	}
		   	
		   	//if song already exists
		   	/*
		   	if() {
		   		Alert alert = new Alert(AlertType.WARNING);
		   		alert.setTitle("Adding Error!");
		   		alert.setHeaderText("This exact song already exists");
		   		alert.setContentText(null);
		   		alert.showAndWait();
		   		return;
		   	}
		   	 */
		   	
		   	//set year if a number is entered
		   	if(year != null && year != "" && isNumeric(year) == true){
		   		newYear = Integer.valueOf(yearField.getText());
		   	}
		   	
		   	//add song with properties
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	   		alert.setTitle("Adding Confirmation");
	   		alert.setHeaderText("Are you sure you want to add this song?");
	   		alert.setContentText(null);
	   		
	   		Optional<ButtonType> result = alert.showAndWait();
	   		if (result.get() == ButtonType.OK){
	   			application.SongLib.insertToList(newSong, newArtist, newAlbum, newYear);
				obsList.add(newSong);
				listView.getSelectionModel().select(numOfSongs);
				numOfSongs++;
	   		}
			
			//sort both lists
			application.SongLib.sortList(application.SongLib.songList);
			FXCollections.sort(obsList);
			if(obsList.size() == 1){
				// select the first item
			    listView.getSelectionModel().select(0);
			}
	   }
	   
	   @FXML private void deleteButtonAction(ActionEvent event) {
		   if(obsList.size() == 1){
			   	Alert alert = new Alert(AlertType.CONFIRMATION);
		   		alert.setTitle("Delete Confirmation");
		   		alert.setHeaderText("Are you sure you want to delete this song?");
		   		alert.setContentText(null);
		   		
		   		Optional<ButtonType> result = alert.showAndWait();
		   		if (result.get() == ButtonType.OK){
				   obsList.clear();
				   application.SongLib.songList.clear();
				   clearItems();
				   numOfSongs--;
				   return;
		   		}
		   }
		   
		   //delete from empty list
		   if(application.SongLib.songList.isEmpty() == true){ 
			   Alert alert = new Alert(AlertType.WARNING);
			   alert.setTitle("Deleting Error!");
			   alert.setHeaderText("You are trying to delete from an empty list");
			   alert.setContentText(null);
			   alert.showAndWait();
			   return;
		   }
		   
		   //delete selected item from listview and songList ArrayList
		   	Alert alert = new Alert(AlertType.CONFIRMATION);
	   		alert.setTitle("Delete Confirmation");
	   		alert.setHeaderText("Are you sure you want to delete this song?");
	   		alert.setContentText(null);
	   		
	   		Optional<ButtonType> result = alert.showAndWait();
	   		if (result.get() == ButtonType.OK){
			   String item = listView.getSelectionModel().getSelectedItem();
			   application.SongLib.deleteFromList(item);
		       obsList.remove(item);
		       numOfSongs--;
	   		}
		       
		   if(obsList.size() >=2){
		       //sort lists after deleting
		       application.SongLib.sortList(application.SongLib.songList);
		       FXCollections.sort(obsList);
		   }
		       
	    }
	   
	   @FXML private void quitButtonAction(ActionEvent event) {
		   	Alert alert = new Alert(AlertType.CONFIRMATION);
		   	alert.setTitle("Exit Confirmation");
	   	  	alert.setHeaderText("Are you sure you want to exit?");
	   		alert.setContentText(null);
	   		
	   		Optional<ButtonType> result = alert.showAndWait();
	   		if (result.get() == ButtonType.OK){
			   obsList.clear();
			   application.SongLib.songList.clear();
			   System.exit(0);
	   		}
	   }
	   
	   
	   @FXML private void editButtonAction(ActionEvent event) {                
		   
		   if(edit == 0){
			   editView.setVisible(true);
			   editButton.setText("Save");
			   edit = 1;
		   }else{
			   	Alert alert = new Alert(AlertType.CONFIRMATION);
			   	alert.setTitle("Edit Confirmation");
		   	  	alert.setHeaderText("Are you sure you want to edit this song?");
		   		alert.setContentText(null);
		   		
		   		Optional<ButtonType> result = alert.showAndWait();
		   		if (result.get() == ButtonType.OK){
		   		   //song gets edited
		   			//insert check to see if the songs match! (did this for the adding 2 songs part)
				   editView.setVisible(false);
				   editButton.setText("Edit");
				   edit = 0;
		   		}
		   		else {
		   			editView.setVisible(false);
		   			editButton.setText("Edit");
		   			edit = 0;
		   		}
		   }

	   }


	}

/*
 * THINGS LEFT TO DO:
 * - not let edit with 2 same songs occur
 * - check edit/save screen stuff
 * - make it so that all entered songs are in textfile so that you can startup the app with songs already listed from last session
 * 			- the first song should be selected by default
 * - look through point deductions part
 */
