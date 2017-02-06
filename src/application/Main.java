package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = makeGridPane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Song> songList = new ArrayList<Song>();
	
	public static void insertToList(ArrayList<Song> s, String song){
		Song newSong = new Song(song);
		if(s == null){
			s = new ArrayList<Song>();
			s.add(newSong); return; 
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
		Text fText = new Text("Fahrenheit");
		
		Button add = new Button();
		add.setOnAction(new EventHandler<ActionEvent>() {
			 public void handle(ActionEvent e) {
				 insertToList(songList, "test");
				 fText.setText(songList.get(0).name);
			 }
		}); 
		
		
		GridPane gridPane = new GridPane();
		gridPane.add(fText, 0, 0);
		gridPane.add(add, 1, 0);
		return gridPane; 
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
