package cave;

public class Entity {
	//Variables
	protected int xpos;
	protected int ypos;
	protected int health;
	protected int attack;
	protected int direction;
	
	//Constructor
	public Entity(int xpos, int ypos, int health, int attack){
		this.xpos = xpos;
		this.ypos = ypos;
		this.health = health;
		this.attack = attack;
	}
	//No args constructor
	public Entity(){
	}
	
	//All the methods!
	public void setDirection(int Dir){
		this.direction = Dir;
	}
	public int getDirection(){
		return this.direction;
	}
	public int getHealth(){
		return health;
	}
	public int getAttack(){
		return attack;
	}
	public int getRandomAttack(){
		int randattack = attack+ (int)Math.round(6*Math.random())-3;
		return randattack;
	}
	public int getX(){
		return xpos;
	}
	public int getY(){
		return ypos;
	}
	public boolean isDead(){
		return health <= 0;
	}
	protected void damage(int damage){
		this.health -= damage; 
	}
	public void setAttack(int attack){
		this.attack = attack;
	}
	public void attack(Entity entity){
		entity.damage(this.attack);
	}
	public void setPos(int xpos,int ypos){
		this.xpos = xpos;
		this.ypos = ypos;
	}
	public void setX(int x){
		this.xpos = x;
	}
	public void setY(int y){
		this.ypos = y;
	}
	public void heal(int health){
		this.health += health;
	}
	public void setHealth(int health){
		this.health = health;
	}
	public boolean checkDeath(){
		if (this.health<= 0){
			return true;
		}
		else{
			return false;
		}
	}
}
