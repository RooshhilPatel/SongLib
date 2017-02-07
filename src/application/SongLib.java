/* Rooshhil Patel and Akhila Narayan */

package application;

import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import view.ListController;
import view.ListController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SongLib extends Application {
	
	static Stage listStage;
	static Button addButton;
	static Object getClass;
	
	public void start(Stage primaryStage) {
		try {
			listStage = primaryStage;
			
			 SongLib.listStage.setResizable(false);
			
			FXMLLoader loader = new FXMLLoader();   
		      loader.setLocation(getClass().getResource("/view/List.fxml"));
		      AnchorPane root = (AnchorPane)loader.load();
		      ListController listController = loader.getController();
		      listController.start(primaryStage);
		      Scene scene = new Scene(root, 670, 300);
			
			listStage.setScene(scene);
			listStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Song> songList = new ArrayList<Song>();
	
	public static void insertToList(ArrayList<Song> s, String song){
		Song newSong = new Song(song);
		if(s == null){
			s = new ArrayList<Song>();
			s.add(newSong); 
			System.out.println("in if statement" + s.get(0));
			return; 
		}
		
		s.add(newSong);
	}
	
	public static void deleteFromList(ArrayList<Song> s, String song){
		
		if(s == null){
			return; 
		}
		
		int i = 0;
		while(s.get(i).name.compareTo(song) != 0){
			i++;
		}
		s.remove(i);
	}

	public static void displayList(ArrayList<Song> s){
		int j = 0;
		int size = s.size();
		while(j != size){
			System.out.print(s.get(j) + " -> ");
			j++;
		}
	}
	
	public static ArrayList<String> sortByName(ArrayList<Song> s){
		ArrayList<String> nameList = new ArrayList<String>();
		int size = s.size();
		for(int i = 0; i <size; i++){
			nameList.add(s.get(i).name);
		}
		return nameList;
	}
	
	public static void sortList(ArrayList<String> s){
		//s.sort(String::compareToIgnoreCase);
		Collections.sort(s, String.CASE_INSENSITIVE_ORDER);
	}
	
	private static GridPane makeGridPane(){
		Text sText = new Text("Song");
		Text alText = new Text("Album");
		Text arText = new Text("Artist");
		Text yText = new Text("Year");
		TextField songbox = new TextField();
		TextField albumbox = new TextField(); 
		TextField artistbox = new TextField(); 
		TextField yearbox = new TextField(); 
		
		TextField random1 = new TextField();
		
		Button add = new Button("Add Song");
		
		Button delete = new Button("Delete Song");
		Button edit = new Button("Edit Song");
		Button quit = new Button("Quit");
		quit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.exit(0);
			}
		});
		
		GridPane gridPane = new GridPane();
		gridPane.add(sText, 0, 0);
		gridPane.add(alText, 1, 0);
		gridPane.add(arText, 2, 0);
		gridPane.add(yText, 3, 0);
		gridPane.add(add, 4, 0);
		gridPane.add(songbox, 0, 1);
		gridPane.add(albumbox, 1, 1);
		gridPane.add(artistbox, 2, 1);
		gridPane.add(yearbox, 3, 1);
		gridPane.add(delete, 4, 1);
		gridPane.add(edit, 4, 2);
		gridPane.add(quit, 4, 3);
		
		gridPane.add(random1, 0, 2);
		
		songbox.setPrefColumnCount(10);
		songbox.setPromptText("Enter Song");
		albumbox.setPrefColumnCount(10);
		albumbox.setPromptText("Enter Album");
		artistbox.setPrefColumnCount(10);
		artistbox.setPromptText("Enter Artist");
		yearbox.setPrefColumnCount(10);
		yearbox.setPromptText("Enter Year");
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		//gridPane.setPadding(new Insets(10,10,10,10));
		//GridPane.setValignment(sText, VPos.BOTTOM);
		//GridPane.setValignment(alText, VPos.BOTTOM);
		
		add.setOnAction(new EventHandler<ActionEvent>() {
			//Song song = new Song();
			int i;
			 public void handle(ActionEvent e) {
				 String sval = String.valueOf(songbox.getText());
				 String alval = String.valueOf(albumbox.getText());
				 
				 if (sval.compareTo("") != 0 && alval.compareTo("")!= 0){
					 insertToList(songList, songbox.getText());
					 random1.setText(songList.get(i).name);
					 i++;
					 //song.Song(songbox.getText(), albumbox.getText());
				 }
				 else {
					 //IS THIS OKAY FOR THE DIALOG BOX THAT POPS UP?????
					 Alert alert = new Alert(AlertType.INFORMATION);
					 alert.setTitle("Error");
					 alert.setHeaderText("Error Message");
					 alert.setContentText("Must enter at least song and album name.");
					 alert.showAndWait();
				 }
			 }
			});
		
		return gridPane; 
	}
	
	/*public class ListController { 	
	 @FXML
	 ListView<String> listView;
	 private ObservableList<String> obsList;
	 public void start() {
	 // create an ObservableList
	 // from an ArrayList
	 obsList = FXCollections.observableArrayList("Giants", "Patriots", "Jaguars");
	 listView.setItems(obsList);
	 }
	} */
	
	public static void main(String[] args) {
		songList.clear();
		launch(args);
	}
}
