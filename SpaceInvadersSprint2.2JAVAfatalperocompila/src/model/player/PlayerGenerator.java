package model.player;

public class PlayerGenerator {
	private static PlayerGenerator myPlayerGenerator;
	
	private PlayerGenerator() {}
	
	public static PlayerGenerator getPlayerGenerator() {
		if (myPlayerGenerator == null) {
			myPlayerGenerator = new PlayerGenerator();
		}
		return myPlayerGenerator;
	}
	
	public AbstractPlayer generatePlayer (String type) {
		AbstractPlayer myPlayer = PlayerFactory.getPlayerFactory().generatePlayer(type);
		return myPlayer;
	}
}
