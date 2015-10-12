package cave;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cave.Monster;
import cave.Treasure;
import cave.Stats;
import cave.Player;

public class Cave extends JFrame implements KeyListener{

	//Don't know what this is but eclipse complained about it so here it is.
	private static final long serialVersionUID = -6468850669352291855L;

	//Constants (final)
	protected static final int S = 0;
	protected static final int W = 1;
	protected static final int N = 2;
	protected static final int E = 3;

	private int NUMOFMON=randomInt(13,15);
	private JFrame frame = new JFrame(); JLabel[][] grid;
	private Player PlayerEntity = new Player(0,0,100,5,"Miner Joe");
	private Monster[] MonsterEntity = new Monster[NUMOFMON];
	private int prevplayerX;
	private int prevplayerY;
	private int length;
	private int width;
	private boolean wpressed = false;
	private boolean spressed = false;
	private boolean apressed = false;
	private boolean dpressed = false;
	private JLabel stats;
	private JPanel board = new JPanel();
	private Stats statObj;
	private int status =0;
	private int moves=0;

	//obtain the path for the images we use
	private java.net.URL[] playerPath = {this.getClass().getResource("player0.png"), this.getClass().getResource("player1.png"), this.getClass().getResource("player2.png"), this.getClass().getResource("player3.png")};
	private java.net.URL[] monsterPath = {this.getClass().getResource("monster0.png"),this.getClass().getResource("monster1.png"),this.getClass().getResource("monster2.png"),this.getClass().getResource("monster3.png")};
	private java.net.URL rockPath = this.getClass().getResource("rock.png");
	private java.net.URL treasurePath = this.getClass().getResource("chest.png");
	//create ImageIcons for the Paths
	private ImageIcon[] player = {new ImageIcon(playerPath[0]),new ImageIcon(playerPath[1]),new ImageIcon(playerPath[2]),new ImageIcon(playerPath[3])};
	private ImageIcon rock = new ImageIcon(rockPath);
	private ImageIcon[] monster = {new ImageIcon(monsterPath[0]),new ImageIcon(monsterPath[1]),new ImageIcon(monsterPath[2]),new ImageIcon(monsterPath[3])};
	private ImageIcon treasure = new ImageIcon(treasurePath);

	//constructor
	public Cave(int width, int length, int pixwidth, int pixlength, int NUMOFROCK) {
		this.length = length;
		this.width = width;
		//create the main frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Rat Cave");
			//this is needed because frame.setSize(pixwidth,pixlength) creates window of that size, not content pane.
		frame.getContentPane().setPreferredSize(new Dimension(pixwidth,pixlength));
		frame.pack();
		
		frame.getContentPane().setBackground(Color.GRAY);
		frame.addKeyListener(this);
		frame.setResizable(false);

		//set layout to grid so we can correctly arrange the game-board
		board = new JPanel(new GridLayout(width,length));

		//correctly format the grid.
		board.setOpaque(false);
		board.setBackground(Color.GRAY);
		frame.setLayout(null);
		board.setBounds(0,0,pixwidth, pixlength);
		board.setSize(new Dimension(pixwidth,pixlength));

		//setup the stats at bottom of screen.
		Stats statObj = new Stats(PlayerEntity.getHealth(),PlayerEntity.getAttack(),PlayerEntity.getArmor(),0,NUMOFMON);
		this.stats = statObj.getLabel();
		this.statObj = statObj;
		stats.setLocation(5,pixlength-45);
		stats.setSize(1000,50);
		stats.setFont(new Font("Serif", Font.PLAIN, 20));

		//creates width*length amount of empty JLabels
		grid = new JLabel[width][length];
		for (int y = 0; y < length; y++) {
			for (int x = 0; x < width; x++) {
				grid[x][y] = new JLabel("");
				if (x == 0 || y == 0) {
					grid[x][y].setIcon(rock);
				}
				if (x == (length - 1) || y == (length - 1)) {
					grid[x][y].setIcon(rock);
				}
				board.add(grid[x][y]);
			}
		}

		// choose random location for player and display the player.
		PlayerEntity.setX(randomInt(1, width-2));
		PlayerEntity.setY(randomInt(1, length-2));
		grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[0]);

		// find location for rocks and display them.
		for (int i = 0; i < NUMOFROCK; i++) {
			int ranX = randomInt(1, width-2);
			int ranY = randomInt(1, length-2);
			while (isOccupied(ranX, ranY)) {
				ranX = randomInt(1, width-2);
				ranY = randomInt(1, length-2);
			}
			grid[ranX][ranY].setIcon(rock);
		}
		if (PlayerEntity.getX()+1 < width-1){
			grid[PlayerEntity.getX()+1][PlayerEntity.getY()].setIcon(null);
		}
		if (PlayerEntity.getX()-1 >0){
			grid[PlayerEntity.getX()-1][PlayerEntity.getY()].setIcon(null);
		}
		if (PlayerEntity.getY()+1<length-1){
			grid[PlayerEntity.getX()][PlayerEntity.getY()+1].setIcon(null);
		}
		if (PlayerEntity.getY()-1 >0){
			grid[PlayerEntity.getX()][PlayerEntity.getY()-1].setIcon(null);
		}

		//Generates locations for 13-15 monsters(rats)
		for (int i = 0; i < NUMOFMON; i++) {
			int ranX = randomInt(1, width-2);
			int ranY = randomInt(1, length-2);

			//Generates a new location if the previous one is already occupied
			while (isOccupied(ranX, ranY)) {
				ranX = randomInt(1, width-2);
				ranY = randomInt(1, length-2);
			}
			grid[ranX][ranY].setIcon(monster[randomInt(0,3)]);
			MonsterEntity[i]=new Monster(ranX,ranY,50,5);
		}
		//Generates locations for 0-6 chests. (4-6 but does not recalculate if a spot is occupied)
		for (int i = 0; i < randomInt(4,6 ); i++) {
			int ranX = randomInt(1, width-2);
			int ranY = randomInt(1, length-2);
			grid[ranX][ranY].setIcon(treasure);
		}

		//Sets the frame to be visible.
		frame.add(stats);
		frame.add(board);
		frame.setVisible(true);
	}
	//method to return an int within (min,max)
	public static int randomInt(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
	//method to check if the specified square is occupied by another ImageIcon
	public boolean isOccupied(int x, int y) {
		if (grid[x][y].getIcon() == null) {
			return false;
		} else {
			return true;
		}
	}

	//updates the location of the player (movement only)
	private void movePlayer() {
		grid[prevplayerX][prevplayerY].setIcon(null);
		if (PlayerEntity.getDirection()==0){
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[0]);
		}else if(PlayerEntity.getDirection()==1){
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[1]);
		}else if(PlayerEntity.getDirection()==2){
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[2]);
		}else{
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[3]);
		}
	}

	//method that returns a boolean letting us know if the player can move to the direction the player wants to
	private boolean canMove(String key) {
		if (key.equals("w")) {
			if (isOccupied(PlayerEntity.getX(), PlayerEntity.getY() - 1)) {
				return false;
			}
		}
		if (key.equals("a")) {
			if (isOccupied(PlayerEntity.getX() - 1, PlayerEntity.getY())) {
				return false;
			}
		}
		if (key.equals("d")) {
			if (isOccupied(PlayerEntity.getX() + 1, PlayerEntity.getY())) {
				return false;
			}
		}
		if (key.equals("s")) {
			if (isOccupied(PlayerEntity.getX(), PlayerEntity.getY() + 1)) {
				return false;
			}
		}
		return true;
	}

	//checks to see if you won (no monsters on game-board)
	public int winCheck(){
		int win = 1;
		for (int i =0;i<MonsterEntity.length;i++){
			if (0 < MonsterEntity[i].getHealth()){
				win = 0;
			}
		}
		if (win==1 && (PlayerEntity.getHealth()>0)) {
			frame.dispose();
			return 1;
		} else if (PlayerEntity.getHealth()>0){
			return 0;
		}else{
			frame.dispose();
			return -1;
		}
	}
	//The method to kill a monster and spawn a chest in the position with a probability.
	private void killMonster(){
		if (PlayerEntity.getDirection()==0){
			if(Math.random()<0.5){  
				grid[PlayerEntity.getX()][PlayerEntity.getY()+1].setIcon(null);
			}else{
				grid[PlayerEntity.getX()][PlayerEntity.getY()+1].setIcon(treasure);
			}
		}else if(PlayerEntity.getDirection()==1){
			if(Math.random()<0.5){
				grid[PlayerEntity.getX()-1][PlayerEntity.getY()].setIcon(null);
			}else{
				grid[PlayerEntity.getX()-1][PlayerEntity.getY()].setIcon(treasure);
			}
		}else if(PlayerEntity.getDirection()==2){
			if (Math.random()<0.5){
				grid[PlayerEntity.getX()][PlayerEntity.getY()-1].setIcon(null);
			}else{
				grid[PlayerEntity.getX()][PlayerEntity.getY()-1].setIcon(treasure);
			}

		}else if(PlayerEntity.getDirection()==3){
			if (Math.random()<0.3){
				grid[PlayerEntity.getX()+1][PlayerEntity.getY()].setIcon(null);
			}else{
				grid[PlayerEntity.getX()+1][PlayerEntity.getY()].setIcon(treasure);
			}
		}
	}
	//part of keylistener class, checks for key press
	@Override
	public void keyPressed(KeyEvent e) {
		moves++;
		
		// b
		//This if for the bombing!
		if (e.getKeyChar() == 'b') {
			moves +=49;
			if(PlayerEntity.getX()+1!=width-1){
				Icon test =grid[PlayerEntity.getX()+1][PlayerEntity.getY()].getIcon();
				if(test==monster[0]||test==monster[1]||test==monster[2]||test==monster[3]){
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()+1&&MonsterEntity[i].getY() == PlayerEntity.getY()){
							target = i;
							break;
						}
					}
					MonsterEntity[target].setHealth(0);
					NUMOFMON--;
				}
				grid[PlayerEntity.getX()+1][PlayerEntity.getY()].setIcon(null);
			}
			if(PlayerEntity.getX()-1!=0){
				Icon test =grid[PlayerEntity.getX()-1][PlayerEntity.getY()].getIcon();
				if(test==monster[0]||test==monster[1]||test==monster[2]||test==monster[3]){
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()-1&&MonsterEntity[i].getY() == PlayerEntity.getY()){
							target = i;
							break;
						}
					}
					MonsterEntity[target].setHealth(0);
					NUMOFMON--;
				}
				grid[PlayerEntity.getX()-1][PlayerEntity.getY()].setIcon(null);
			}
			if(PlayerEntity.getY()+1!=length-1){
				Icon test =grid[PlayerEntity.getX()][PlayerEntity.getY()+1].getIcon();
				if(test==monster[0]||test==monster[1]||test==monster[2]||test==monster[3]){
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()&&MonsterEntity[i].getY() == PlayerEntity.getY()+1){
							target = i;
							break;
						}
					}
					MonsterEntity[target].setHealth(0);
					NUMOFMON--;
				}
				grid[PlayerEntity.getX()][PlayerEntity.getY()+1].setIcon(null);
			}
			if(PlayerEntity.getY()-1!=0){
				Icon test =grid[PlayerEntity.getX()][PlayerEntity.getY()-1].getIcon();
				if(test==monster[0]||test==monster[1]||test==monster[2]||test==monster[3]){
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()&&MonsterEntity[i].getY() == PlayerEntity.getY()-1){
							target = i;
							break;
						}
					}
					MonsterEntity[target].setHealth(0);
					NUMOFMON--;
				}
				grid[PlayerEntity.getX()][PlayerEntity.getY()-1].setIcon(null);
			}
			statObj.setMoves(moves);
			statObj.updateMonsterNum(NUMOFMON);
			this.stats.setText(statObj.getText());
			status = this.winCheck();
		}
		// w key
		if (e.getKeyChar()=='w'&&wpressed == false) {
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[2]);
			PlayerEntity.setDirection(2);
			if (canMove("w")) {
				prevplayerX = PlayerEntity.getX();
				prevplayerY = PlayerEntity.getY();
				PlayerEntity.moveUp();
				movePlayer();
				statObj.setMoves(moves);
				this.stats.setText(statObj.getText());
			} else{
				moves --;
			}
			wpressed = true;
		}
		// a key
		if (e.getKeyChar() == 'a'&&apressed == false) {
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[1]);
			PlayerEntity.setDirection(1);
			if (canMove("a")) {
				prevplayerX = PlayerEntity.getX();
				prevplayerY = PlayerEntity.getY();
				PlayerEntity.moveLeft();
				movePlayer();
				statObj.setMoves(moves);
				this.stats.setText(statObj.getText());
			} else{
				moves --;
			}
			apressed = true;
		}
		// s key
		if (e.getKeyChar() == 's'&&spressed == false) {
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[0]);
			PlayerEntity.setDirection(0);
			if (canMove("s")) {
				prevplayerX = PlayerEntity.getX();
				prevplayerY = PlayerEntity.getY();
				PlayerEntity.moveDown();
				movePlayer();
				statObj.setMoves(moves);
				this.stats.setText(statObj.getText());
			} else{
				moves --;
			}
			spressed = true;
		}
		// d key
		if (e.getKeyChar() == 'd'&&dpressed == false) {
			grid[PlayerEntity.getX()][PlayerEntity.getY()].setIcon(player[3]);
			PlayerEntity.setDirection(3);
			if (canMove("d")) {
				prevplayerX = PlayerEntity.getX();
				prevplayerY = PlayerEntity.getY();
				PlayerEntity.moveRight();
				movePlayer();
				statObj.setMoves(moves);
				this.stats.setText(statObj.getText());
			} else{
				moves --;
			}
			dpressed = true;
		}
		//For the interactions
		if (e.getKeyChar() == ' ') {
			if(PlayerEntity.getDirection() == 0){
				//chest interactions
				if(grid[PlayerEntity.getX()][PlayerEntity.getY()+1].getIcon()==treasure){
					int upgrade = (new Treasure()).getValue();
					if((new Treasure()).getType()=="armor"){
						PlayerEntity.addArmor(2*upgrade);
						statObj.updatePlayerArmor(PlayerEntity.getArmor());
					}else{
						PlayerEntity.addAttack(upgrade);
						statObj.updatePlayerAttack(PlayerEntity.getAttack());
					}
					this.stats.setText(statObj.getText());
					grid[PlayerEntity.getX()][PlayerEntity.getY()+1].setIcon(null);

					//attack interactions
				}else if(grid[PlayerEntity.getX()][PlayerEntity.getY() + 1].getIcon() == monster[0] ||
						grid[PlayerEntity.getX()][PlayerEntity.getY() + 1].getIcon() == monster[1] ||
						grid[PlayerEntity.getX()][PlayerEntity.getY() + 1].getIcon() == monster[2] ||
						grid[PlayerEntity.getX()][PlayerEntity.getY() + 1].getIcon() == monster[3] ){
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()&&MonsterEntity[i].getY() == PlayerEntity.getY()+1){
							target = i;
							break;
						}
					}
					MonsterEntity[target].damage(PlayerEntity.getRandomAttack());
					PlayerEntity.damage(MonsterEntity[target].getRandomAttack());
					grid[PlayerEntity.getX()][PlayerEntity.getY() + 1].setIcon(monster[2]);
					if(MonsterEntity[target].getHealth()<=0){
						killMonster();
						NUMOFMON--;
					}
					statObj.updateStats(PlayerEntity.getHealth(),PlayerEntity.getAttack(),PlayerEntity.getArmor(),MonsterEntity[target].getHealth(),NUMOFMON,moves);
					this.stats.setText(statObj.getText());
					status =this.winCheck();
				}
			}

			if(PlayerEntity.getDirection() == 1){
				//chest interactions
				if(grid[PlayerEntity.getX()-1][PlayerEntity.getY()].getIcon()==treasure){
					int upgrade = (new Treasure()).getValue();
					if((new Treasure()).getType()=="armor"){
						PlayerEntity.addArmor(2*upgrade);
						statObj.updatePlayerArmor(PlayerEntity.getArmor());
					}else{
						PlayerEntity.addAttack(upgrade);
						statObj.updatePlayerAttack(PlayerEntity.getAttack());
					}
					this.stats.setText(statObj.getText());
					grid[PlayerEntity.getX()-1][PlayerEntity.getY()].setIcon(null);

					//attack interactions
				}else if(grid[PlayerEntity.getX() - 1][PlayerEntity.getY()].getIcon() == monster[0] ||
						grid[PlayerEntity.getX() - 1][PlayerEntity.getY()].getIcon() == monster[1] ||
						grid[PlayerEntity.getX() - 1][PlayerEntity.getY()].getIcon() == monster[2] ||
						grid[PlayerEntity.getX() - 1][PlayerEntity.getY()].getIcon() == monster[3]){
					PlayerEntity.getRandomAttack();
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()-1&&MonsterEntity[i].getY() == PlayerEntity.getY()){
							target = i;
							break;
						}
					}
					MonsterEntity[target].damage(PlayerEntity.getRandomAttack());
					PlayerEntity.damage(MonsterEntity[target].getRandomAttack());
					grid[PlayerEntity.getX() - 1][PlayerEntity.getY()].setIcon(monster[3]);
					if(MonsterEntity[target].getHealth()<=0){
						killMonster();
						NUMOFMON--;
					}
					statObj.updateStats(PlayerEntity.getHealth(),PlayerEntity.getAttack(),PlayerEntity.getArmor(),MonsterEntity[target].getHealth(),NUMOFMON,moves);
					this.stats.setText(statObj.getText());
					status =this.winCheck();
				}
			}

			if(PlayerEntity.getDirection() == 2){
				//chest interactions
				if(grid[PlayerEntity.getX()][PlayerEntity.getY()-1].getIcon()==treasure){
					int upgrade = (new Treasure()).getValue();
					if((new Treasure()).getType()=="armor"){
						PlayerEntity.addArmor(2*upgrade);
						statObj.updatePlayerArmor(PlayerEntity.getArmor());
					}else{
						PlayerEntity.addAttack(upgrade);
						statObj.updatePlayerAttack(PlayerEntity.getAttack());
					}
					this.stats.setText(statObj.getText());
					grid[PlayerEntity.getX()][PlayerEntity.getY()-1].setIcon(null);

					//attack interactions
				}else if((grid[PlayerEntity.getX()][PlayerEntity.getY() - 1].getIcon() == monster[0] ||
						grid[PlayerEntity.getX()][PlayerEntity.getY() - 1].getIcon() == monster[1] ||
						grid[PlayerEntity.getX()][PlayerEntity.getY() - 1].getIcon() == monster[2] ||
						grid[PlayerEntity.getX()][PlayerEntity.getY() - 1].getIcon() == monster[3])) {
					PlayerEntity.getRandomAttack();	
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()&&MonsterEntity[i].getY() == PlayerEntity.getY()-1){
							target = i;
							break;
						}
					}
					MonsterEntity[target].damage(PlayerEntity.getRandomAttack());
					PlayerEntity.damage(MonsterEntity[target].getRandomAttack());
					grid[PlayerEntity.getX()][PlayerEntity.getY() - 1].setIcon(monster[0]);
					if(MonsterEntity[target].getHealth()<=0){
						killMonster();
						NUMOFMON--;
					}
					statObj.updateStats(PlayerEntity.getHealth(),PlayerEntity.getAttack(),PlayerEntity.getArmor(),MonsterEntity[target].getHealth(),NUMOFMON,moves);
					this.stats.setText(statObj.getText());
					status = this.winCheck();
				}
			}

			if(PlayerEntity.getDirection() == 3){
				//chest interactions
				if(grid[PlayerEntity.getX()+1][PlayerEntity.getY()].getIcon()==treasure){
					int upgrade = (new Treasure()).getValue();
					if((new Treasure()).getType()=="armor"){
						PlayerEntity.addArmor(2*upgrade);
						statObj.updatePlayerArmor(PlayerEntity.getArmor());
					}else{
						PlayerEntity.addAttack(upgrade);
						statObj.updatePlayerAttack(PlayerEntity.getAttack());
					}
					this.stats.setText(statObj.getText());
					grid[PlayerEntity.getX()+1][PlayerEntity.getY()].setIcon(null);

					//attack interactions
				}else if((grid[PlayerEntity.getX() + 1][PlayerEntity.getY()].getIcon() == monster[0] ||
						grid[PlayerEntity.getX() + 1][PlayerEntity.getY()].getIcon() == monster[1] ||
						grid[PlayerEntity.getX() + 1][PlayerEntity.getY()].getIcon() == monster[2] ||
						grid[PlayerEntity.getX() + 1][PlayerEntity.getY()].getIcon() == monster[3])) {
					int target=-1;
					for (int i =0; i<MonsterEntity.length;i++){
						if(MonsterEntity[i].getX()==PlayerEntity.getX()+1&&MonsterEntity[i].getY() == PlayerEntity.getY()){
							target = i;
							break;
						}
					}
					MonsterEntity[target].damage(PlayerEntity.getRandomAttack());
					PlayerEntity.damage(MonsterEntity[target].getRandomAttack());
					grid[PlayerEntity.getX() + 1][PlayerEntity.getY()].setIcon(monster[1]);
					if(MonsterEntity[target].getHealth()<=0){
						killMonster();
						NUMOFMON--;
					}
					statObj.updateStats(PlayerEntity.getHealth(),PlayerEntity.getAttack(),PlayerEntity.getArmor(),MonsterEntity[target].getHealth(),NUMOFMON,moves);
					this.stats.setText(statObj.getText());
					status = this.winCheck();
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	//For the main class to access the status of the game once it is finished.
	public int getStatus(){
		return status;
	}
	//returns number of moves.
	public int getMoves(){
		return moves;
	}
	//Checking for when the wsad keys are released, avoiding repeating keys.
	@Override
	public void keyReleased(KeyEvent e){
		if (e.getKeyChar() == 'w') {
			wpressed = false;
		}
		if(e.getKeyChar() == 's'){
			spressed = false;
		}
		if(e.getKeyChar() == 'a'){
			apressed = false;
		}
		if(e.getKeyChar()=='d'){
			dpressed = false;
		}
	}
}