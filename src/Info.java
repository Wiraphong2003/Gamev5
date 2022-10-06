import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

public class Info implements Serializable{
	Player player1, player2,player3,player4;
	long serialVersionUID = 5657987992401750799L;
	Info(){

	}
}
class Player{
	Items item ;
	int hp = 10;
	int maxHp = hp;
	int damage = 2;
	int crit;
	int level = 1;
	String name;
	Character character ;
	Player(int count ,Character character){
		if(count == 1) {
			hp = 10;
			name = "Player1";
			
			this.character = character;
			
		}else if(count == 2) {
			hp = 10;
			name = "Player2";
			item = new Items(this,"Sword");
			this.character = character;
		}else if(count == 3) {
			hp = 3;
			name = "Player3";
			this.character = character;
		}else if(count == 4) {
			hp = 4;
			name = "Player4";	
			this.character = character;
		}
		
	}
	
	public String toString() {
		return name;
	}
}

class Character{
	ImageIcon stand , attack , run , dying;
	Character(String character){
		if(character == "character1") {
			stand = getImage("ch1_default.GIF");
			attack = getImage("ch1_attack.GIF");
			run = getImage("ch1_run.GIF");
			dying = getImage("ch1_die.GIF");
		}else if(character == "character2") {
			stand = getImage("ch2_default.GIF");
			attack = getImage("ch2_attack.GIF");
			run = getImage("ch2_run.GIF");
			dying = getImage("ch2_die.GIF");
		}else if(character == "character3") {
			stand = getImage("ch3_default.GIF");
			attack = getImage("ch3_attack.GIF");
			run = getImage("ch3_run.GIF");
			dying = getImage("ch3_die.GIF");
		}else if(character == "character4") {
			stand = getImage("ch4_default.GIF");
			attack = getImage("ch4_attack.GIF");
			run = getImage("ch4_run.GIF");
			dying = getImage("ch4_die.GIF");
		}
	}
	
	ImageIcon getImage(String file) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + File.separator + file));
	}
	
}
class Maps {
	Player player1 = null;
	Player player2 = null;
	Character character;
	Maps(Player player1 , Player player2){
	}
}
class monster{
	int hp ;
	int damage;
	int maxHp;
	Character character;
	monster(){
		
	}
}
class Items{
	Items(Player player , String name){
		if(name =="Sword") {
			player.damage += 2;
		}
	}
}

class Sounds{
	File file;
	AudioInputStream audioStream;
	Clip clip;
	
	Sounds(String name) throws IOException, Exception{
		if(name == "StartGame") {
			
			clip = AudioSystem.getClip();
			clip.open(getAudio("Start.wav"));
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
		} 
		if(name == "Battle") {
			clip = AudioSystem.getClip();
			clip.open(getAudio("battle.wav"));
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		if(name == "Attack") {
			clip = AudioSystem.getClip();
			clip.open(getAudio("Hit.wav"));
			clip.start();
		}
		if(name == "Maps") {
			clip = AudioSystem.getClip();
			clip.open(getAudio("Maps.wav"));
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	AudioInputStream getAudio(String name) throws Exception{
		AudioInputStream audio;
		audio = AudioSystem.getAudioInputStream(new File(name));
		return audio;
	}
}