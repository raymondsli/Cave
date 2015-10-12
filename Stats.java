package cave;

import java.awt.Color;

import javax.swing.JLabel;

public class Stats {
	private JLabel stats;
	private int playerHealth;
	private int monsterHealth=0;
	private int numMonsters;
	private int playerAttack;
	private int playerArmor = 0;
	private int moves = 0;
	public Stats(JLabel stats){
		this.stats = stats;
	}
	public Stats(int playerHealth, int playerAttack,int playerArmor, int monsterHealth,int numMonsters){
		this.playerHealth = playerHealth;
		this.monsterHealth = monsterHealth;
		this.numMonsters=numMonsters;
		this.playerAttack = playerAttack;
		this.playerArmor = playerArmor;
	}
	public void updateStats(int playerHealth,int playerAttack,int playerArmor, int monsterHealth,int numMonsters,int moves){
		this.playerHealth = playerHealth;
		this.monsterHealth = monsterHealth;
		this.numMonsters = numMonsters;
		this.playerAttack = playerAttack;
		this.playerArmor = playerArmor;
		this.moves = moves;
	}
	public void updatePlayerHealth(int playerHealth){
		this.playerHealth = playerHealth;
	}
	public void updateMonster(int monsterHealth){
		this.monsterHealth = monsterHealth;
	}
	public void updateMonsterNum(int numMonsters){
		this.numMonsters = numMonsters;
	}
	public void updatePlayerAttack(int playerAttack){
		this.playerAttack = playerAttack;
	}
	public void updatePlayerArmor(int playerArmor){
		this.playerArmor = playerArmor;
	}
	public void setMoves(int moves){
		this.moves = moves;
	}
	public JLabel getLabel(){
		stats = new JLabel("Health: "+playerHealth+" Attack: "+playerAttack+" Armor: "+playerArmor+" Monster Health: "+monsterHealth+" Enemies Left: "+numMonsters+" Moves: "+moves);
		stats.setForeground(Color.WHITE);
		return stats;
	}
	public String getText(){
		return "Health: "+playerHealth+" Attack: "+playerAttack+" Armor: "+playerArmor+" Monster Health: "+monsterHealth+" Enemies Left: "+numMonsters+" Moves: "+moves;
	}
}
