package cave;

public class Treasure {
	//variables
	private int xpos=0;
	private int ypos=0;
	
	//No arg Constructor
	public Treasure(){
	}
	//Constructor
	public Treasure(int xpos,int ypos){
		this.xpos = xpos;
		this.ypos = ypos;
	}
	
	//Methods. Used to generate the random treasure (weapon/armor) and value of upgrade (higher chance of low upgrade vs high)
	public int getValue(){
		int value = (int)Math.ceil(Math.pow(Math.random()*2.641588,3));
		if (value <=2){
			value +=Math.round(Math.random());
		}
		return value;
	}
	public String getType(){
		if(Math.random()<.4){
			return "armor";
		}
		else{
			return "weapon";
		}
	}
	public int getX(){
		return xpos;
	}
	public int getY(){
		return ypos;
	}
	
}
