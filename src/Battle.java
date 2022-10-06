import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class OptionFrame extends JFrame{
	OptionPanel optionPanel ;
	Panel panelCenter;
	Maps mark;
	OptionFrame(JFrame frame,Panel panelCenter ,Info info,Maps mark){
		this.mark = mark;
		this.panelCenter = panelCenter;
		setContentPane(optionPanel = new OptionPanel("Option",frame,this,info,this.mark));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocation(211,144);
		this.setVisible(true);
	}

	
}

class OptionPanel extends JPanel implements ActionListener{
	
	Button buttonAttack , buttonEscape , buttonItems;
	
	boolean isButtonAttack = false;
	boolean isButtonEscape = false;
	OptionFrame optionFrame;
	JFrame frame;
	
	LeftAttackThread leftAttackThread;
	LeftEscapeThread leftEscapeThread;
	RightAttackThread rightAttackThread;
	RightEscapeThread rightEscapeThread;
	
	Info info;
	
	long lastPress = 0;
	
	boolean labelLeftAttack = false, labelRightAttack = false;
	
	Maps mark;
	OptionPanel(String name, JFrame frame,OptionFrame optionFrame ,Info info,Maps mark){
		this.frame = frame;
		this.info = info;
		this.optionFrame = optionFrame;
		this.mark = mark;
		setPreferredSize(new Dimension(200,500));
		
		add(buttonAttack = new Button("Attack",200,80,0,420,new Color(57,52,46)));
		buttonAttack.addActionListener(this);
		
		add(buttonEscape = new Button("Escape",200,80,0,420,new Color(57,52,46)));
		buttonEscape.addActionListener(this);
		
		add(buttonItems = new Button("Items",200,80,0,420,new Color(57,52,46)));
		buttonItems.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(labelLeftAttack == false) {
			if(System.currentTimeMillis() - lastPress > 5500) {
				if(e.getSource() == buttonAttack) {
					isButtonAttack = true;
					optionFrame.panelCenter.character1Label.setIcon(mark.player1.character.run);
					leftAttackThread = new LeftAttackThread(isButtonAttack, optionFrame.panelCenter , info,mark);
					leftAttackThread.start();
				}else if(e.getSource() == buttonEscape) {
					isButtonEscape = true;
					leftEscapeThread = new LeftEscapeThread(isButtonEscape,optionFrame.panelCenter,info,frame,optionFrame,mark);
					leftEscapeThread.start();
					optionFrame.panelCenter.soundBattle.clip.stop();
				}
				lastPress = System.currentTimeMillis();
				labelLeftAttack = true;
				labelRightAttack = false;
			}
		}else if(labelRightAttack == false) {
			if(System.currentTimeMillis() - lastPress > 5500) {
				if(e.getSource() == buttonAttack) {
					isButtonAttack = true;
					optionFrame.panelCenter.character2Label.setIcon(mark.player2.character.run);
					rightAttackThread = new RightAttackThread(isButtonAttack, optionFrame.panelCenter , info,mark);
					rightAttackThread.start();
				}else if(e.getSource() == buttonEscape) {
					isButtonEscape = true;
					rightEscapeThread = new RightEscapeThread(isButtonEscape,optionFrame.panelCenter,info,frame,optionFrame,mark);
					rightEscapeThread.start();
					optionFrame.panelCenter.soundBattle.clip.stop();
				}
				lastPress = System.currentTimeMillis();
				labelRightAttack = true;
				labelLeftAttack = false;
				
			}
		}
	}
}