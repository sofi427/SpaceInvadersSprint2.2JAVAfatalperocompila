package model.player;

public class PlayerFactory {
	private static PlayerFactory myPlayerFactory;
	
	private PlayerFactory() {}
	
	public static PlayerFactory getPlayerFactory(){
		if (myPlayerFactory == null)
		    {myPlayerFactory = new PlayerFactory();}
		return myPlayerFactory;
	    }
	
	public AbstractPlayer generatePlayer(String type, int posX, int posY) {
		AbstractPlayer myPlayer = null;
		if (type.equals("Green")) {
			myPlayer = new GreenPlayer(posX,posY);
		}
		else if (type.equals("Blue")) {
			myPlayer = new BluePlayer(posX,posY);
		}
		else if (type.equals("Red")) {
			myPlayer = new RedPlayer(posX,posY);
		}
		return myPlayer;
	}
}
