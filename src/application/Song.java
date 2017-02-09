/* Rooshhil Patel and Akhila Narayan */

package application;

public class Song {
	
	String year = "";
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
	
	public Song(String name, String artist, String album, String year){
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
	public String getSongYear(){
		return this.year;
	}
	public void setSongName(String name){
		this.name = name;
	}
	public void setArtistName(String artist){
		this.artist = artist;
	}
	public void setAlbumName(String album){
		this.album = album;
	}
	public void setYear(String year){
		this.year = year;
	}
	
}
