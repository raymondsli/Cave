package cave;

public class Monster extends Entity{
	public Monster(){
	}
	public Monster(int xpos, int ypos, int health, int attack){
		super( xpos, ypos, health, attack);
	}
	//prevent monster from dealing a lot of damage at low health.
	@Override
	public int getRandomAttack(){
		int randattack = attack+ (int)Math.round(6*Math.random())-3;
		if (randattack > health){
			randattack = health;
		}
		return randattack;
	}
	
}
