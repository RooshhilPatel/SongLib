/* Rooshhil Patel and Akhila Narayan */

package view;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.Song;
import application.SongLib;
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
	   @FXML private TextField editViewSong;
	   @FXML private TextField editViewAlbum;
	   @FXML private TextField editViewArtist;
	   @FXML private TextField editViewYear;
	   @FXML private VBox editView1;
	   @FXML private VBox editView2;
	   @FXML private Button editButton;
	   @FXML private Text songInfo;
	   private int edit = 0;													//0 is edit, 1 is save1

	   private ObservableList<String> obsList;              					//private observable list
	   
	   public void start(Stage mainStage) {                
	      // create an ObservableList 
	      // from an ArrayList 
		   SongLib.songToStringList(SongLib.songList);
		   obsList = FXCollections.observableArrayList(SongLib.songNameList);
		   
		   editView2.setVisible(false);
		    
	      
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
		   if(index < 0){
			   index = 0;
		   }
		   
		   String album = SongLib.songList.get(index).getSongAlbum();
		   String artist = SongLib.songList.get(index).getSongArtist();
		   int year = SongLib.songList.get(index).getSongYear();

		   String content = "Song: " + item + 
				   "\nArtist: " + artist + "\nAlbum: " + album + "\nYear: " + year;
		   songInfo.setText(content);
	   }
	   
	   public boolean isNumeric(String s) {  
		    return s.matches("[-+]?\\d*\\.?\\d+");  
		}
	   
	   public static boolean hasDuplicates(ArrayList<Song> songs, String songName, String artistName) {
		   if(songs.size() == 1){
			  if(songs.get(0).getSongName().compareToIgnoreCase(songName) == 0){
				  if(songs.get(0).getSongArtist().compareToIgnoreCase(artistName) == 0){
					  return true;
				  }
			  }
		   }
		   
		    final List<String> usedSongNames = new ArrayList<String>();
		    for (int i = 0; i < songs.size(); i++) {
		    	final String name = songs.get(i).getSongName();

		        if (usedSongNames.contains(songName)) {
		        	int index = SongLib.songList.indexOf(songName);
			   		if(index < 0){
			   			index = 0;
					}
		        	if(songs.get(index).getSongArtist().compareToIgnoreCase(artistName) == 0){
		        		return true;
		        	}
		        }
		        
		        usedSongNames.add(name);
		    }

		    return false;
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
		   	int newYear = 0; 													//default year to -1 if not entered
		   	
		   	//error if song and/or artist fields are empty
		   	if(songField.getText().isEmpty() == true || artistField.getText().isEmpty() == true){
		   		error("Invalid Song");
		   		return;
		   	}
		   	
		   	//if song already exists
		   	if(hasDuplicates(SongLib.songList, newSong, newArtist) == true) {
		   		int index = SongLib.songList.indexOf(newSong);
		   		if(index < 0){
		   			index = 0;
				}
		   		if(SongLib.songList.get(index).getSongArtist().compareToIgnoreCase(newArtist) == 0){
			   		error("Duplicate Add");
			   		return;
		   		}
		   	}
		   	
		   	//set year if a number is entered
		   	if(!yearField.getText().isEmpty()){
		   		if(isNumeric(year) == true){
		   			newYear = Integer.valueOf(yearField.getText());
		   		}else{
		   			error("Invalid Year");
			   		return;
		   		}
		   	}
		   	
		   	//add song with properties
		   	Optional<ButtonType> result = alert("Confirm Add");
	   		if (result.get() == ButtonType.OK){
	   			SongLib.insertToList(newSong, newArtist, newAlbum, newYear);
				obsList.add(newSong);
				listView.getSelectionModel().select(obsList.lastIndexOf(newSong));
	   		}
			
			//sort both lists
			SongLib.sortList(SongLib.songList);
			FXCollections.sort(obsList);
			if(obsList.size() == 1){
				// select the first item
			    listView.getSelectionModel().select(0);
			}
	   }
	   
	   @FXML private void deleteButtonAction(ActionEvent event) {
		   
		 //delete from empty list
		   if(SongLib.songList.isEmpty() == true){ 
			   error("Empty List Delete");
			   return;
		   }
		   
		   //if the size of the list is 1, remove song
		   if(obsList.size() == 1){
			   	Optional<ButtonType> result = alert("Confirm Delete");
		   		if (result.get() == ButtonType.OK){
				   obsList.clear();
				   SongLib.songList.clear();
				   clearItems();
				   return;
		   		}
		   }
		   
		   	//delete selected item from listview and songList ArrayList
		   	Optional<ButtonType> result = alert("Confirm Delete");
	   		if (result.get() == ButtonType.OK){
			   String item = listView.getSelectionModel().getSelectedItem();
			   SongLib.deleteFromList(item);
		       obsList.remove(item);
	   		}
		       
		   if(obsList.size() >=2){
		       //sort lists after deleting
		       SongLib.sortList(SongLib.songList);
		       FXCollections.sort(obsList);
		   }
		       
	    }
	   
	   @FXML private void quitButtonAction(ActionEvent event) {
		   	Optional<ButtonType> result = alert("Confirm Exit");
	   		if (result.get() == ButtonType.OK){
	   			try {
	   				SongLib.save("currentSongs.txt");
	   			} catch (FileNotFoundException e) {
	   				e.printStackTrace();
	   			}
			   obsList.clear();
			   SongLib.songList.clear();
			   System.exit(0);
	   		}
	   }
	   
	   
	   @FXML private void editButtonAction(ActionEvent event) {
		   
		   //edit an empty list
		   if(SongLib.songList.isEmpty() == true){ 
			   error("Empty List Edit");
			   return;
		   }
		   
		   String item = listView.getSelectionModel().getSelectedItem();
		   int index = listView.getSelectionModel().getSelectedIndex();
		   if(index < 0){
			   index = 0;
		   }
		   String album = SongLib.songList.get(index).getSongAlbum();
		   String artist = SongLib.songList.get(index).getSongArtist();
		   String year = String.valueOf(SongLib.songList.get(index).getSongYear());
		   
		   if(edit == 0){
			   editView1.setVisible(false);
				   editViewSong.setText(item);
				   editViewArtist.setText(artist);
				   editViewAlbum.setText(album);
				   editViewYear.setText(year);
			   editView2.setVisible(true);
			   editButton.setText("Save");
			   edit = 1;
		   }else{		   		
		   		Optional<ButtonType> result = alert("Confirm Edit");
		   		if (result.get() == ButtonType.OK){
		   			//song gets edited
		   			item = editViewSong.getText();
		   			artist = editViewArtist.getText();
		   			album = editViewAlbum.getText();
		   			year = editViewYear.getText();
				   	int newYear = 0; 													//default year to -1 if not entered
				  //set year if a number is entered
				   	if(!editViewYear.getText().isEmpty()){
				   		if(isNumeric(year) == true){
				   			newYear = Integer.valueOf(editViewYear.getText());
				   		}else{
				   			error("Invalid Year");
					   		return;
				   		}
				   	}
		   			
				   	//if song already exists
				   	if(hasDuplicates(SongLib.songList, item, artist) == true) {
				   		index = SongLib.songList.indexOf(item);
				   		if(index < 0){
				   			index = 0;
						}
				   		if(SongLib.songList.get(index).getSongArtist().compareToIgnoreCase(artist) == 0){
					   		error("Duplicate Add");
					   		return;
				   		}
				   	}

				   	editView2.setVisible(false);
				   		SongLib.songList.get(index).setSongName(item);
				   		SongLib.songList.get(index).setArtistName(artist);
				   		SongLib.songList.get(index).setAlbumName(album);
				   		SongLib.songList.get(index).setYear(newYear);
				   		obsList.set(index, item);
				   		if(obsList.size() >=2){
					       //sort lists after editing
					       SongLib.sortList(SongLib.songList);
					       FXCollections.sort(obsList);
					       listView.getSelectionModel().select(index);
						}
				   		if(obsList.size() == 1){
							// select the first item
						    listView.getSelectionModel().select(0);
						}
				   	editView1.setVisible(true);
				   	editButton.setText("Edit");
				   	edit = 0;
		   		}
		   		else {
		   			editView2.setVisible(false);
		   			editView1.setVisible(true);
		   			editButton.setText("Edit");
		   			edit = 0;
		   		}
		   }

	   }
	   
	   public void error(String type){
		   Alert alert = new Alert(AlertType.WARNING);
		   
		   switch(type){
		   case "Duplicate Add":
			   	alert.setTitle("Adding Error!");
		   		alert.setHeaderText("This song already exists");
		   		alert.setContentText(null);
		   		alert.showAndWait();
		   		return;
		   case "Invalid Song":
			   	alert.setTitle("Adding Error!");
		   		alert.setHeaderText("Must enter a song and artist");
		   		alert.setContentText(null);
		   		alert.showAndWait();
		   		return;
		   case "Invalid Year":
			   	alert.setTitle("Adding Error!");
		   		alert.setHeaderText("Must Enter a numeric Year");
		   		alert.setContentText(null);
		   		alert.showAndWait();
			   return;
		   case "Empty List Delete":
			   alert.setTitle("Deleting Error!");
			   alert.setHeaderText("You are trying to delete from an empty list");
			   alert.setContentText(null);
			   alert.showAndWait();
			   return;
		   case "Empty List Edit":
			   alert.setTitle("EditError!");
			   alert.setHeaderText("You are trying to edit an empty list");
			   alert.setContentText(null);
			   alert.showAndWait();
			   return;	   
		   default:
			   return;
		   }
	   }
	   
	   public Optional<ButtonType> alert(String type){
		   Alert alert = new Alert(AlertType.CONFIRMATION);
		   Optional<ButtonType> result = null;
		   switch(type){
		   	case "Confirm Add":
			   	alert.setTitle("Adding Confirmation");
		   		alert.setHeaderText("Are you sure you want to add this song?");
		   		alert.setContentText(null);
		   		result = alert.showAndWait();
		   		return result;
		   	case "Confirm Delete":
		   		alert.setTitle("Delete Confirmation");
		   		alert.setHeaderText("Are you sure you want to delete this song?");
		   		alert.setContentText(null);
		   		result = alert.showAndWait();
		   		return result;
		   	case "Confirm Edit":
		   		alert.setTitle("Edit Confirmation");
		   	  	alert.setHeaderText("Are you sure you want to edit this song?");
		   		alert.setContentText(null);
		   		result = alert.showAndWait();
		   		return result;
		   	case "Confirm Exit":
		   		alert.setTitle("Exit Confirmation");
		   	  	alert.setHeaderText("Are you sure you want to exit?");
		   		alert.setContentText(null);
		   		result = alert.showAndWait();
		   		return result;
		   	default:
		   		return result;
		   }
	   }
	   
	   

	}

/*
 * THINGS LEFT TO DO:
 * - check if year is 4 digits long && not past 2017 && >= 0000
 * - make it so that all entered songs are in textfile so that you can startup the app with songs already listed from last session
 * 			- the first song should be selected by default
 * - look through point deductions part
 */
