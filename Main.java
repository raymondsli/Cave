package cave;

import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import cave.Cave;

public class Main implements KeyListener{

	//Variables
	private static JLabel title;
	private static JLabel title3;
	private static Cave c;
	private static JFrame titleSc = new JFrame();
	private java.util.Timer timer;
	private static JLabel score = new JLabel();

	//Constructor
	public Main(){
		titleSc.addKeyListener(this);
	}
	//Main method that runs on start
	public static void main(String[] args){
		//creates title frame
		titleSc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		titleSc.setTitle("Cave");
		titleSc.setSize(1000, 1000);
		titleSc.getContentPane().setBackground(new Color(120, 100, 82));
		titleSc.setLayout(null);

		//creates the text and images that go on the frame.
		title = new JLabel("CAVE GAME");
		title.setBounds(0,0,1000,300);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 100));
		JLabel title2 = new JLabel("[press space to begin]");
		title2.setBounds(0,400,1000,1000);
		title2.setHorizontalAlignment(JLabel.CENTER);
		title2.setFont(new Font("Serif", Font.PLAIN, 25));
		ImageIcon titlePic = new ImageIcon(Main.class.getResource("titlepic.png"));
		title3 = new JLabel("by Bradley Qu, and Raymond Li",titlePic,JLabel.CENTER);
		title3.setFont(new Font("Serif", Font.PLAIN, 20));
		title3.setVerticalTextPosition(JLabel.TOP);
		title3.setHorizontalTextPosition(JLabel.CENTER);
		title3.setBounds(0,200,1000,400);
		JLabel info = new JLabel("wsad to move, n for new map, space for attack/interact, b to bomb");
		info.setFont(new Font("Serif", Font.PLAIN, 20));
		info.setBounds(0,300,1000,700);
		info.setHorizontalAlignment(JLabel.CENTER);
		JLabel info2 = new JLabel("kill all enemies (rats) to defeat the game. Do it in least moves possible!");
		info2.setFont(new Font("Serif", Font.PLAIN, 20));
		info2.setBounds(0,300,1000,750);
		info2.setHorizontalAlignment(JLabel.CENTER);
		JLabel info3 = new JLabel("collect treasure to get attack and armor upgrades! You have infinite bombs, but they cost 50 moves.");
		info3.setFont(new Font("Serif", Font.PLAIN, 20));
		info3.setBounds(0,300,1000,800);
		info3.setHorizontalAlignment(JLabel.CENTER);
		score.setFont(new Font("Serif", Font.PLAIN, 50));
		score.setBounds(0,500,1000,600);
		score.setSize(1000,100);
		score.setHorizontalAlignment(JLabel.CENTER);

		//display these texts and images.
		titleSc.add(title);
		titleSc.add(title2);
		titleSc.add(title3);
		titleSc.add(info);
		titleSc.add(info2);
		titleSc.add(info3);
		titleSc.add(score);

		//Set visibility
		titleSc.setVisible(true); 

		//execute the constructor that has some non-static stuff that needs to be done.
		Main main = new Main();
	}
	public static void win(){
		title.setText("You Win");
		score.setText("You Used "+c.getMoves()+" Moves");
		title3.setText("");
		titleSc.setVisible(true);
	}
	public static void lose(){
		title.setText("You Lose");
		score.setText("You Used "+c.getMoves()+" Moves");
		title3.setText("");
		titleSc.setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Begin the game and hide the start menu.
		if (e.getKeyChar() == ' ') {
			titleSc.setVisible(false);
			startGameLoop();
		}
		if (e.getKeyChar()=='q'){
			titleSc.dispose();
			timer.cancel();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void startGameLoop(){
		c = new Cave(25, 25,1000,1000,100);
		timer = new Timer();
		timer.schedule(new testStatus(), 0, 500); //new timer at 60 fps, the timing mechanism
	}
	public void stopGameLoop(){
		timer.cancel();
	}


	private class testStatus extends java.util.TimerTask{
		public void run(){
			if(c.getStatus()==1){
				win();
				stopGameLoop();
			}else if (c.getStatus()==-1){
				lose();
				stopGameLoop();
			}
		}
	}
}
