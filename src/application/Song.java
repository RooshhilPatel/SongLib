/* Rooshhil Patel and Akhila Narayan */

package application;

public class Song {
	
	int year = 0;
	String name = "";
	String artist = "";
	String album = "";
	
	public Song(){
		this.name = "noName";
		this.artist = "noArtist";
	}
	public Song(String name){
		this.name = name;
	}
	public Song(String name, String artist){
		this.name = name;
		this.artist = artist;
	}
	public Song(String name, String artist, int year){
		this.name = name;
		this.artist = artist;
		this.year = year;
	}
	public Song(String name, String artist, String album){
		this.name = name;
		this.artist = artist;
		this.album = album;
	}
	public Song(String name, String artist, String album, int year){
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	public String getSongName(){
		return this.name;
	}
	public String getSongArtist(){
		return this.artist;
	}
	public String getSongAlbum(){
		return this.album;
	}
	public int getSongYear(){
		return this.year;
	}
	
}
