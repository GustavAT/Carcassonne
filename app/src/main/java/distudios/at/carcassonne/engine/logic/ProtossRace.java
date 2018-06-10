package distudios.at.carcassonne.engine.logic;

public class ProtossRace extends Player {
    // Strong and very interested in Cathedrals
    public ProtossRace(int playerID) {
        super(playerID);
        this.peeps = 7;
        this.scoreMultiplierTotal = 2;
        this.scoreMultiplierCathedral = 3;
    }
}
