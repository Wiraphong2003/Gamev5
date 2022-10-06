import java.awt.image.ImageObserver;
import java.util.Random;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.RepaintManager;

class LeftAttackThread extends Thread{
	boolean attack;
	Panel panelCenter;
	int positionX = 0;
	int positionY = 0;
	JLabel character1;
	int positionHit;
	
	Info info;
	
	Maps mark;
	Sounds soundAttack;
	LeftAttackThread(boolean attack ,Panel panelCenter,Info info ,Maps mark){
		if(attack) {
			this.mark = mark;
			this.info = info;
			character1 = panelCenter.character1Label;
			positionX = panelCenter.character1Label.getX();
			positionY = panelCenter.character1Label.getY();
			this.attack = attack;
			this.panelCenter = panelCenter;
		}
	}
	
	public void run() {
		if(true) {
			while(attack) {
				positionX += 1;
				character1.setLocation(positionX,positionY);
				if(positionX + 100 == panelCenter.character2Label.getX()) {
					attack = !attack;
				}
				if(positionX + 100 == panelCenter.character2Label.getX() - 50) {
					panelCenter.character1Label.setIcon(mark.player1.character.attack);
				}
				try {
					Thread.sleep(6);
				}catch(InterruptedException e){} 
		
			}
			positionX += 50;
			positionHit = positionX + 100;
			character1.setLocation(positionX,positionY);
			
			if(positionHit == positionX + panelCenter.character1Label.getWidth()) {
				mark.player2.hp -= mark.player1.damage;
				try {
					soundAttack = new Sounds("Attack");
				} catch (Exception e1) {}
				if(mark.player2.hp <= 0) {
					mark.player2.hp = 0;
				}
				panelCenter.countHp2 = mark.player2.hp;
			}
			while(positionX + 100 != panelCenter.character2Label.getX()) {	
				positionX -= 1;
				character1.setLocation(positionX,positionY);
				try {
					Thread.sleep(20);
				}catch(InterruptedException e) {}
			}
			while(positionX != panelCenter.positionX1) {
				positionX += -1;
				character1.setLocation(positionX,positionY);
				character1.setIcon(mark.player1.character.run);
				try {
					Thread.sleep(6);
				}catch(InterruptedException e) {}
			}
			character1.setIcon(mark.player1.character.stand);
			soundAttack.clip.stop();
		}
	}
	public static boolean checkHit(JLabel character1Label, JLabel character2Label) {
		if(character1Label.getX() + character1Label.getWidth() >  character2Label.getX()) {
				return true;
		}
		return false;
	}
	public static String checkLeftOrRight(JLabel character) {
		if(character.getX() == 125) {
			return "left";
		}else if(character.getX() == 475){
			return "right";
		}
		return "";
	}
}

class LeftEscapeThread extends Thread{
	int positionX , positionY , positionStand;
	
	Panel panelCenter;
	Info info;
	
	boolean escape = false;
	
	JLabel escapeLabel;
	JFrame frame;
	
	Maps mark;
	LeftEscapeThread(boolean escape,Panel panelCenter,Info info,JFrame frame,OptionFrame optionFrame , Maps mark){
		this.escape = escape;
		this.panelCenter = panelCenter;
		this.info = info;
		this.frame = frame;
		this.mark = mark;
		positionX = this.panelCenter.character1Label.getX();
		positionY = this.panelCenter.character1Label.getY();
		positionStand = 225;
	}
	
	public void run() {
		while(positionX+100 != 0) {
			positionX -= 1;
			panelCenter.character1Label.setIcon(mark.player1.character.run);
			panelCenter.character1Label.setLocation(positionX , positionY);
			if(positionX + 100 != 225) {
				escapeLabel = new JLabel("Player 1 Escape !!!");
				escapeLabel.setFont(new Font("",1,50));
				escapeLabel.setForeground(Color.red);
				escapeLabel.setBounds(125,125,450,100);
				panelCenter.add(escapeLabel);
				panelCenter.repaint();
			}
			if(positionX + 100 == 0) {
				frame.setContentPane(new Panel("Maps",frame,info,null,null));
				frame.pack();
				
				break;
			}
			try{
				Thread.sleep(10);
			}catch(InterruptedException e) {}
		}
	}
	
}

class RightAttackThread extends Thread{
	boolean attack;
	Panel panelCenter;
	int positionX = 0;
	int positionY = 0;
	JLabel character2;
	int positionHit;
	
	Info info;
	
	Maps mark;
	
	Sounds soundAttack;
	RightAttackThread(boolean attack ,Panel panelCenter,Info info,Maps mark){
		if(attack) {
			this.mark = mark;
			this.info = info;
			character2 = panelCenter.character2Label;
			positionX = character2.getX();
			positionY = character2.getY();
			this.attack = attack;
			this.panelCenter = panelCenter;
		}
	}
	
	public void run() {
		if(true) {
			while(attack) {
				positionX -= 1;
				character2.setLocation(positionX,positionY);
				if(positionX == panelCenter.character1Label.getX() + 100) {
					attack = !attack;
				}
				if(positionX  == panelCenter.character1Label.getX() + panelCenter.character1Label.getWidth() + 100) {
					panelCenter.character2Label.setIcon(mark.player2.character.attack);
				}
				try {
					Thread.sleep(6);
				}catch(InterruptedException e){} 
			}
				positionX -= 50;
				positionHit = positionX;
				character2.setLocation(positionX,positionY);
				
			if(positionHit  == positionX) {
				mark.player1.hp -= mark.player2.damage;
				try {
					soundAttack = new Sounds("Attack");
				} catch (Exception e1) {}
				if(mark.player1.hp <= 0) {
					mark.player1.hp = 0;
				}
				panelCenter.countHp1 = mark.player1.hp;
			}
			
			while(positionX != panelCenter.character1Label.getX() + 100) {	
				positionX += 1;
				character2.setLocation(positionX,positionY);
				try {
					Thread.sleep(20);
				}catch(InterruptedException e) {}
			}
			while(positionX != panelCenter.positionX2) {
				positionX += 1;
				character2.setLocation(positionX,positionY);
				character2.setIcon(mark.player2.character.run);
				try {
					Thread.sleep(6);
				}catch(InterruptedException e) {}
			}
			character2.setIcon(mark.player2.character.stand);
			soundAttack.clip.stop();
		}
	}
	public static boolean checkHit(JLabel character1Label, JLabel character2Label) {
		if(character1Label.getX() < character2Label.getX() + 100) {
				return true;
		}
		return false;
	}
	public static String checkLeftOrRight(JLabel character) {
		if(character.getX() == 125) {
			return "left";
		}else if(character.getX() == 475){
			return "right";
		}
		return "";
	}
}

class RightEscapeThread extends Thread{
	int positionX , positionY , positionStand;
	
	Panel panelCenter;
	Info info;
	
	boolean escape = false;
	
	JLabel escapeLabel;
	JFrame frame;
	
	Maps mark;
	RightEscapeThread(boolean escape,Panel panelCenter,Info info,JFrame frame,OptionFrame optionFrame,Maps mark){
		this.escape = escape;
		this.panelCenter = panelCenter;
		this.info = info;
		this.frame = frame;
		this.mark = mark;
		positionX = this.panelCenter.character2Label.getX();
		positionY = this.panelCenter.character2Label.getY();
		positionStand = 225;
	}
	
	public void run() {
		while(positionX+100 != 0) {
			positionX += 1;
			panelCenter.character2Label.setIcon(mark.player2.character.run);
			panelCenter.character2Label.setLocation(positionX , positionY);
			if(positionX != 475) {
				escapeLabel = new JLabel("Player 2 Escape !!!");
				escapeLabel.setFont(new Font("",1,50));
				escapeLabel.setForeground(Color.red);
				escapeLabel.setBounds(125,125,450,100);
				panelCenter.add(escapeLabel);
				panelCenter.repaint();
			}
			if(positionX == 700) {
				frame.setContentPane(new Panel("Maps",frame,info,null,null));
				frame.pack();
				break;
			}
			try{
				Thread.sleep(10);
			}catch(InterruptedException e) {}
		}
	}
	
}
class thrads extends Thread{
	JLabel box = new JLabel();
	int x = 0,y = 0,open = 0;
	int index;
	boolean run = true,isrun = true;
	Random ran = new Random();
	int poiter;
	
	JFrame frame;
	Info info;
	Player player;
	Maps[] mark;
	public thrads(JLabel box,int PositionX ,int PositionY,JFrame frame ,Info info ,Player player,Maps[] mark) {
		this.box = box;
		this.x = PositionX;
		this.y = PositionY;
		this.frame = frame;
		this.info = info;
		this.player = player;
		this.mark = mark;
	}
		
	public void run() {
		while(isrun) {
			if(run) {
				if(box.getY()!=y) {
					if(box.getY()<=y) {
						box.setLocation(box.getX(), box.getY()+1);
					}
					else if(box.getY()>=y) {
						box.setLocation(box.getX(), box.getY()-1);
					}
				}
				if(box.getY() == y) {
					run = false;
				}
			}
			if(run == false) {
				if(box.getX()!=x) {
					if(box.getX()<=x) {
						box.setLocation(box.getX()+1, box.getY());
					}
					else if(box.getX()>=x) {
						box.setLocation(box.getX()-1, box.getY());
					}
				}
				if(box.getX() == x) {
					run = true;
					isrun = false;
				}
			}
			if(box.getX()>55 && box.getX()<75 && box.getY()>180 && box.getY()<200) {
				if(box.getX()==x && box.getY()==y) {
					poiter = 1;
					if(mark[poiter].player1 == null) {
						
						mark[poiter].player1 = player;
					}else {
						
						mark[poiter].player2 = player;
					}
					System.out.println(mark[poiter].player1 +":"+ mark[poiter].player2);
					if(mark[poiter].player1 != null && mark[poiter].player2 != null) {
						
						frame.setContentPane(new Panel("Battle",frame,info,mark[poiter],null));
						frame.pack();
					}
					System.out.println("Mark 1");
				}
			}
			else if(box.getX()>55 && box.getX()<75 && box.getY()>350 && box.getY()<375) {
				if(box.getX()==x && box.getY()==y) {
					poiter = 2;
					if(mark[poiter].player1 == null) {
						mark[poiter].player1 = player;
					}else {					
						mark[poiter].player2 = player;
					}
					System.out.println(mark[poiter].player1 +":"+ mark[poiter].player2);
					if(mark[poiter].player1 != null && mark[poiter].player2 != null) {
						
						frame.setContentPane(new Panel("Battle",frame,info,mark[poiter],null));
						frame.pack();
					}
					System.out.println("Mark 2");
				}
			}
			else if(box.getX()>195 && box.getX()<225 && box.getY()>80 && box.getY()<100) {
				if(box.getX()==x && box.getY()==y) {
					poiter = 3;
					System.out.println("Mark 3");
					if(mark[poiter].player1 == null) {
						mark[poiter].player1 = player;
					}else {
						mark[poiter].player2 = player;
					}
					System.out.println(mark[poiter].player1 +":"+ mark[poiter].player2);
					if(mark[poiter].player1 != null && mark[poiter].player2 != null) {
						frame.setContentPane(new Panel("Battle",frame,info,mark[poiter],null));
						frame.pack();
					}
				}
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {}
		}
		
	}	
}