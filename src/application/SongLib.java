/* Rooshhil Patel and Akhila Narayan */

package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.ListController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class SongLib extends Application {
	
	static Stage listStage;														//So you can reference the stage globally
	
	public void start(Stage primaryStage) {
		//try catch to catch any stack exceptions
		try {
			listStage = primaryStage;											//So you can reference the stage globally
			
			FXMLLoader loader = new FXMLLoader();   							//Loader to call the .fxml file
			loader.setLocation(getClass().getResource("/view/List.fxml"));		//set .fxml file 
			AnchorPane root = (AnchorPane)loader.load();						//sets dimensions of loader
			ListController listController = loader.getController();				//makes a list controller
			listController.start(listStage);									//sets list controller's stage
			Scene scene = new Scene(root, 670, 300);							//new scene with window size specifications
			
			listStage.setResizable(false);										//does not allow resize-ability
			listStage.setScene(scene);											//sets the scene for the stage
			listStage.show();													//shows the stage
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Song> songList = new ArrayList<Song>();				//static array list of song objects
	public static ArrayList<String> songNameList = new ArrayList<String>();		//list of songs by name with type string
	
	//insert method array list
	public static void insertToList(String song){
		Song newSong = new Song(song);
		if(songList == null){
			songList = new ArrayList<Song>();
			songList.add(newSong); 
			System.out.println("in if statement" + songList.get(0));
			return; 
		}
		
		songList.add(newSong);
	}
	
	//insert method for name array list
	public static void insertToNameList(String song){
		if(songNameList == null){
			songNameList = new ArrayList<String>();
			songNameList.add(song); 
			System.out.println("in if statement" + songNameList.get(0));
			return; 
		}
		
		songNameList.add(song);
	}
	
	//delete method for array list
	public static void deleteFromList(String song){
		
		if(songList == null){
			return; 
		}
		
		int i = 0;
		while(songList.get(i).name.compareTo(song) != 0){
			i++;
		}
		songList.remove(i);
	}

	//displays array list
	public static void displayList(){
		int j = 0;
		int size = songList.size();
		while(j != size){
			System.out.print(songList.get(j).name + " -> ");
			j++;
		}
	}
	
	//displays name array list
	public static void displayNameList(ArrayList<String> s){
		int j = 0;
		int size = s.size();
		while(j != size){
			System.out.print(s.get(j) + " -> ");
			j++;
		}
	}
	
	//sorts the array list of songs
	public static void sortList(ArrayList<String> s){
		Collections.sort(s, String.CASE_INSENSITIVE_ORDER);
	}
	
	public static void songToStringList(ArrayList<Song> s){
		int j = 0;
		int size = s.size();
		while(j != size){
			insertToNameList(s.get(j).name);
			j++;
		}
	}

	public static void main(String[] args) {
		songList.clear();
		launch(args);
	}
}
