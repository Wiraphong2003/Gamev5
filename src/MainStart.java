import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;

public class MainStart extends JFrame{
	Panel startGamePanel;
	Panel selectPanel;
	Info info = new Info();
	MainStart(){
		setContentPane(startGamePanel = new Panel("StartGame",this,info,null,null));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void main(String[]args) {
		new MainStart();
	}
}
class SelectCharacter extends JFrame{
	Panel selectPanel;
	SelectCharacter(){
		//add(selectPanel = new Panel("SelectCharacter",this,info));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}

class Panel extends JPanel implements ActionListener , MouseListener{
	int width = 0;
	int height = 0;

	Button buttonStartGame , buttonExitProgram , buttonAttack ,buttonSelectCh;
	JLabel title ,character1Label , character2Label;
	JFrame frame;
	
	Image imageStart , imageBackgroundCharacter , imageBackgroundBattle;
	Image imageCharacter1 , imageCharacter2 , imageCharacter3 , imageCharacter4;
	Image imageObject1 , imageObject2;
	
	String nameOfPanel;
	
	LeftAttackThread leftAttackThread;
	LeftEscapeThread leftEscapeThread;
	RightAttackThread rightAttackThread;
	RightEscapeThread rightEscapeThread;

	int positionX1 , positionY1 , positionX2 ,positionY2;
	
	boolean isCh1 , isCh2 , isCh3 , isCh4;

	int count = 0;
	Player player;
	Info info;
	
	Player player1 , player2 , player3 , player4;
	Character character;
	
	JProgressBar bar1 , bar2; 
	int countHp1 = 0 , countHp2 = 0;
	
	OptionFrame optionFrame ;
	
	Panel panelCenter , selectPanel;
	
	box box1,box2,box3,box4;
	thrads th;
	Image imageBackground;
	
	Sounds soundStart , soundBattle , soundMaps;
	
	int countMap = 0;
	
	Maps markSingle = new Maps(null,null);
	Maps[] mark = new Maps[4];
	
	Panel(String nameOfPanel,JFrame frame, Info info,Maps mark,ClientSideConnection csc){
		this.frame = frame;
		this.nameOfPanel = nameOfPanel;
		this.csc = csc;
		if( nameOfPanel == "StartGame") {
			try{
				soundStart = new Sounds("StartGame");
			}catch(Exception e) {}
			this.info = info;
			
			title = new JLabel("Stick Man");
			imageStart = getImage("BackgroundStartGame.GIF");
			
			setSize(700,500);
			
			buttonStartGame = new Button("StartGame",200,80,0,420,new Color(57,52,46));
			buttonExitProgram = new Button("ExitGame",200,80,500,420,new Color(57,52,46));
			
			title.setBounds(240,60,300,50);
			title.setFont(new Font("",1,50));
			
			add(buttonStartGame);
			add(buttonExitProgram);
			add(title);
			
			buttonStartGame.addActionListener(this);
			buttonExitProgram.addActionListener(this);
			
		
		}else if(nameOfPanel == "SelectCharacter") {
			this.info = info;
			
			imageBackgroundCharacter = getImage("BackgroundCharacter.JPG");
			
			imageCharacter1 = getImage("ch1_default.GIF");
			imageCharacter2 = getImage("ch2_default.GIF");
			imageCharacter3 = getImage("ch3_default.GIF");
			imageCharacter4 = getImage("ch4_default.GIF");
			
			setSize(700,500);                                                                                                                           
			setBackground(Color.white);
			
			buttonSelectCh = new Button("Select",200,80,250,400, new Color(57,52,46));
			add(buttonSelectCh);
			buttonSelectCh.addActionListener(this);
			
			
			
		}else if(nameOfPanel == "Battle") {
			this.info = info;
			this.markSingle = mark;
			
			try{
				soundBattle = new Sounds("Battle");
			}catch(Exception e) {}
			
			optionFrame = new OptionFrame(frame,this,info,mark);
			
			bar1 = new JProgressBar();
			bar2 = new JProgressBar();
			setBar(info.player1,bar1,0,0);
			setBar(info.player2,bar2,415,0);
			add(bar1);
			add(bar2);
			
			setSize(700,500);
			imageBackgroundBattle = getImage("BackgroundCharacter.JPG");
	
			character1Label = new JLabel(this.markSingle.player1.character.stand);
			character2Label = new JLabel(this.markSingle.player2.character.stand);
			
			character1Label.setBounds(125,260,100,100);
			character2Label.setBounds(475,260,100,100);
			
			add(character1Label);
			add(character2Label);
			
			positionX1 = character1Label.getX();
			positionY1 = character1Label.getY();
			
			positionX2 = character2Label.getX();
			positionY2 = character2Label.getY();
			
			imageCharacter1 = getImage("Character1.GIF");
			
		}else if(nameOfPanel == "Maps") {
			this.info = info;
			setPreferredSize(new Dimension(700,500));
			setLayout(null);
			
			imageBackground = Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + File.separator + "map.PNG");;
			try {
				soundMaps = new Sounds("Maps");
			} catch (Exception e) {}
				
			box1 = new box(20,20,60,10,new Color(255,0,0),true);
			box2 = new box(20,20,65,190,new Color(0,0,255),true);
			box3 = new box(20,20,65,365,new Color(0,255,0),true);
			box4 = new box(20,20,205,90,new Color(255,0,255),true);
			add(box1);
			add(box2);
			add(box3);
			add(box4);
			player1 = info.player1;
			player2 = info.player2;
			player3 = info.player3;
			player4 = info.player4;
			
			for(int i = 0 ; i < 4 ; i++) {
				this.mark[i] = new Maps(null,null);
			}
		}
		this.addMouseListener(this);
	}
	
	public void fill(Player player1,Player player2,JProgressBar bar1,JProgressBar bar2){
		if(countHp1 == player1.maxHp && countHp2 == player2.maxHp) {
			return;
		}
		if(countHp1 != player1.hp) {
			System.out.println(countHp1);
			countHp1 += 1 ;
			bar1.setValue(countHp1);
			bar1.setString(countHp1+"");
		}
		if(countHp2 != player2.hp) {
			countHp2 += 1 ;
			bar2.setValue(countHp2);
			bar2.setString(countHp2+"");
		}
	}
	public void setBar(Player player,JProgressBar bar ,int x ,int y) {
		bar.setValue(0);
		bar.setBounds(x,y,285,35);
		bar.setStringPainted(true);
		bar.setMaximum(player.maxHp);
		bar.setBackground(Color.black);
		bar.setForeground(Color.red);
	}
	Image getImage(String file) {
		return Toolkit.getDefaultToolkit().createImage(System.getProperty("user.dir") + File.separator + file);
		
	}
	public void setSize(int width ,int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(this.width,this.height));
		setLayout(null);
	}
	
	@Override
	public void	paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		if(nameOfPanel == "StartGame") {
			
			g.drawImage(imageStart ,0,0,700,500,0,0,700,500,this);
			
		}else if(nameOfPanel == "SelectCharacter") {
			g.drawImage(imageBackgroundCharacter ,0,0,700,500,0,0,600,360,this);
			g.drawImage(imageCharacter1 ,45,80,180,220,0,0,100,100,this);
			g.drawImage(imageCharacter2 ,205,80,340,220,0,0,100,100,this);
			g.drawImage(imageCharacter3 ,360,80,495,220,0,0,100,100,this);
			g.drawImage(imageCharacter4 ,520,80,655,220,0,0,100,100,this);
			g2D.setStroke(new BasicStroke(2));
			g2D.drawRect(45, 80, 135, 140);
			g2D.drawRect(205, 80, 135, 140);
			g2D.drawRect(360, 80, 135, 140);
			g2D.drawRect(520, 80, 135, 140);
			
		}else if(nameOfPanel == "Battle") {
			g.drawImage(imageBackgroundBattle ,0,0,700,500,0,0,600,360,this);
			if(countHp1-1 != markSingle.player1.hp && countHp2 - 1 != markSingle.player2.hp) {
				fill(markSingle.player1,markSingle.player2,bar1,bar2);
				
			}
			
			if(LeftAttackThread.checkHit(character1Label,character2Label)) {
				g2D.setStroke(new BasicStroke(10.0f));
				g2D.setColor(Color.red);
				g2D.drawRect(0,0,700,500);
				
				//คำนวณหาตำแหน่งตีเป็นตำแหน่งตี เพื่อ ทำการลดเลือดตามดาเมจของอีกฝั่งที่ตีได้
				if(markSingle.player2.hp <= 0) {
					
					markSingle.player2.hp = 0;
					character2Label.setIcon(markSingle.player2.character.dying);
				}
				if(markSingle.player1.hp <= 0) {
					
					markSingle.player1.hp = 0;
					character1Label.setIcon(markSingle.player1.character.dying);
				}
				//ทำการเซตเลือดในการต่อสู้ตามเลือดที่มีของ player
				bar1.setValue(markSingle.player1.hp);
				bar1.setString(markSingle.player1.hp+"");
				bar2.setValue(markSingle.player2.hp);
				bar2.setString(markSingle.player2.hp+"");
				repaint();
			}

		}else if(nameOfPanel == "Maps") {
			g.drawImage(imageBackground ,0,0,700,500,0,0,300,300,this);
		}
	}
	ClientSideConnection csc;
	byte[] serializedObject;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == buttonStartGame) {
			csc = new ClientSideConnection();
			setPanel(frame , selectPanel = new Panel("SelectCharacter",frame,info,null,csc));
			
			
			selectPanel.addMouseListener(this);
			soundStart.clip.stop();
		}else if(e.getSource() == buttonExitProgram) {
			frame.setVisible(false);
		}else if(e.getSource() == buttonSelectCh) {
			System.out.println("true");
			if(isCh1 == true && isCh2 == false && isCh3 == false && isCh4 == false) {
				System.out.println("Select Charatcter 1 "+isCh1);
				count++;	
				character = new Character("character1");
			}
			else if(isCh1 == false && isCh2 == true &&isCh3 == false && isCh4 == false) {
				System.out.println("Select Charatcter 2 "+isCh2);
				count++;
				character = new Character("character2");
			}
			else if(isCh1 == false && isCh2 == false && isCh3 == true && isCh4 == false) {
				System.out.println("Select Charatcter 3 "+isCh3);
				count++;
				character = new Character("character3");
			}
			else if(isCh1 == false && isCh2 == false && isCh3 == false &&isCh4 == true) {
				System.out.println("Select Charatcter 4 "+isCh4);
				count++;
				character = new Character("character4");
			}else {
				System.out.println("Nothing");
				System.out.println(isCh1 + " " + isCh2 + " " + isCh3 + " " + isCh4);
			}
			
			player = new Player(count,character);
			
			if(csc.playerID == 1) {
				info.player1 = player;
				try {
					csc.dataOut.writeInt(csc.playerID);
					System.out.println("write\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("IOException csc.writeInt");
				}
			}else if(info.player2 == null && csc.playerID == 2){
				info.player2 = player;
				try {
					csc.dataOut.writeInt(csc.playerID);
					System.out.println("write\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("IOException csc.writeInt");
				}
			}else if(info.player3 == null && csc.playerID == 3) {
				info.player3 = player;
				try {
					csc.dataOut.writeInt(csc.playerID);
					System.out.println("write\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("IOException csc.writeInt");
				}
			}else if(info.player4 == null && csc.playerID == 4) {
				info.player4 = player;
				try {
					csc.dataOut.writeInt(csc.playerID);
					System.out.println("write\n");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("IOException csc.writeInt");
				}
			}else {
				System.out.println("Can't not Create.");
			}
			try{
				count = csc.dataIn.readInt();
			}catch(IOException io){System.out.println("IOException from csc dataInput count.");}
			
			if(count == 4) {
				panelCenter = new Panel("Maps",frame,info,null,csc);
				panelCenter.addMouseListener(this);
				setPanel(frame ,panelCenter);
				
			}
		}
	}
	
	void setPanel(JFrame frame ,Panel panel) {
		frame.setContentPane(panel);
		frame.pack();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX() +" "+ e.getY());
		if(nameOfPanel == "SelectCharacter") {
			if(e.getX()>45 && e.getX()<180 ) {
				isCh1 = true;			
				isCh2 = false;			
				isCh3 = false;			
				isCh4 = false;
				System.out.println(isCh1);
			}
			if(e.getX()>206 && e.getX()<340 ) {
				
				isCh1 = false;			
				isCh2 = true;			
				isCh3 = false;			
				isCh4 = false;
				System.out.println(isCh2);
			}
			if(e.getX()>360 && e.getX()<490 ) {
				
				isCh1 = false;			
				isCh2 = false;			
				isCh3 = true;			
				isCh4 = false;
				System.out.println(isCh3);
				
			}
			if(e.getX()>520 && e.getX()<650 ) {
				isCh1 = false;			
				isCh2 = false;			
				isCh3 = false;			
				isCh4 = true;
				System.out.println(isCh4);
			}
		}else if(nameOfPanel == "Maps") {
			countMap ++;
			if(countMap == 1) {
				th = new thrads(box1,e.getX()-10,e.getY()-10,frame , info , player1,mark);
			}else if(countMap == 2) {
				th = new thrads(box2,e.getX()-10,e.getY()-10,frame , info , player2,mark);
			}
			else if(countMap == 3) {
				th = new thrads(box3,e.getX()-10,e.getY()-10,frame , info , player3,mark);
			}
			else if(countMap == 4) {
				th = new thrads(box4,e.getX()-10,e.getY()-10,frame , info , player4,mark);
				countMap = 0;
			}
			th.start();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

class Button extends JButton{
	Button(String name , int width , int height , int x , int y ,Color color){
		setSize(width,height);
		setLocation(x,y);
		setBackground(color);
		setText(name);
		setFocusable(false);
		setFont(new Font("MV Bovi",1,30));
		setForeground(Color.white);
	}
}

class box extends JLabel{
	box(int width ,int higth, int x ,int y , Color color,boolean open){
		setSize(width,higth);
		setLocation(x,y);
		setBackground(color);
		setOpaque(open);
		
	}
}