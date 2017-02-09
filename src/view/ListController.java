package view;

//import application.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

		   String content = "Index: " + index + "\nValue: " + item + 
				   "\nAlbum: " + album + "\nArtist: " + artist + "\nYear: " + year;
		   songInfo.setText(content);
	   }
	   
	   public boolean isNumeric(String s) {  
		    return s.matches("[-+]?\\d*\\.?\\d+");  
		}
	   
	   @FXML private void clearItems() {   
		   String content = "Index: " + "\nValue: " +
				   "\nAlbum: " + "\nArtist: " + "\nYear: ";
		   songInfo.setText(content);
	   }
	   
	   @FXML private void addButtonAction(ActionEvent event) {
		   //initiate everything
		   	String newSong = songField.getText();
		   	String newArtist = artistField.getText();
		   	String newAlbum = albumField.getText();
		   	String year = yearField.getText();
		   	int newYear = -1; 													//default year to -1 if not entered
		   	
		   	if(songField.getText().isEmpty() == true || artistField.getText().isEmpty() == true){
		   		System.out.println("You need song and artist to insert a song stupid");
		   		return;
		   	}
		   	
		   	//set year if a number is entered
		   	if(year != null && year != "" && isNumeric(year) == true){
		   		newYear = Integer.valueOf(yearField.getText());
		   	}
		   	
		   	//add song with properties
	        application.SongLib.insertToList(newSong, newArtist, newAlbum, newYear);
			obsList.add(newSong);
			
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
			   obsList.clear();
			   application.SongLib.songList.clear();
			   clearItems();
			   return;
		   }
		   //idk y the delete error isn't coming
		   if(application.SongLib.songList.isEmpty() == true){ 
			   Alert alert = new Alert(AlertType.WARNING);
			   alert.setTitle("Deleting Error!");
			   alert.setHeaderText("You are trying to delete from an empty list");
			   alert.setContentText(null);
			   alert.showAndWait();
			   return;
		   }
			   //delete selected item from listview and songList ArrayList
			   String item = listView.getSelectionModel().getSelectedItem();
			   application.SongLib.deleteFromList(item);
		       obsList.remove(item);
		       
		   if(obsList.size() >=2){
		       //sort lists after deleting
		       application.SongLib.sortList(application.SongLib.songList);
		       FXCollections.sort(obsList);
		   }
		       
	    }
	   
	   @FXML private void quitButtonAction(ActionEvent event) {
		   obsList.clear();
		   application.SongLib.songList.clear();
		   System.exit(0);
	   }
	   
	   
	   @FXML private void editButtonAction(ActionEvent event) {                
		   
		   if(edit == 0){
			   editView.setVisible(true);
			   editButton.setText("Save");
			   edit = 1;
		   }else{
			   editView.setVisible(false);
			   editButton.setText("Edit");
			   edit = 0;
		   }

	   }


	}


