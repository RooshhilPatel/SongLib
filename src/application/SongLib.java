/* Rooshhil Patel and Akhila Narayan */

package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
	public static void insertToList(String song, String artist, String album,int year){
		Song newSong = new Song(song, artist, album, year);

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
		if(song.isEmpty()){
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
	//new class specially made to sort song objects by name
	static class SongComparator implements Comparator<Song>
	 {
		public int compare(Song s1, Song s2)
	    {
	        return s1.getSongName().compareTo(s2.getSongName());
	    }
	 }
	
	//sorts the array list of songs by comparing names
	public static void sortList(ArrayList<Song> songs){
		Collections.sort(songs, new SongComparator());
	}
	
	//sorts the array list of songNames
	public static void sortNameList(ArrayList<String> s){
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
	
	public static void save(String fileName) throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
	    for (Song song : songList){
	        pw.println(song.getSongName());
	    	pw.println(song.getSongArtist());
	    	pw.println(song.getSongAlbum());
	    	pw.println(song.getSongYear());
	    }
	    pw.close();
	}
	
	public static boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}
	
	public static int numOfLines(String FileName) throws IOException{
		int lines  = 0;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(FileName));
			lines = 0;
			while (reader.readLine() != null) lines++;
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	public static void readFile(String FileName) throws FileNotFoundException{
		try (BufferedReader br = new BufferedReader(new FileReader(FileName)))
        {
            String sSongLine;
            String sArtistLine;
            String sAlbumLine;
            String sYearLine;
            int newYear = 0;
            int lines = numOfLines(FileName) -3;
            int i = 0;
            
            while (i != lines){
            		sSongLine = br.readLine();
            		sArtistLine = br.readLine();
            		sAlbumLine = br.readLine();
            		sYearLine = br.readLine();
            		
            		//set year if a number is entered
        		   	if(sYearLine == ""){
        		   		if(isNumeric(sYearLine) == true){
        		   			newYear = Integer.valueOf(sYearLine);
        		   		}
        		   	}
            		
            		insertToList(sSongLine, sArtistLine, sAlbumLine, newYear);
            		i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
	}

	public static void main(String[] args){
		songList.clear();
		try {
			readFile("currentSongs.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		launch(args);
	}
}
