package distudios.at.carcassonne.engine.logic;

public class ZergRace extends Player {
    // ZERG Race is weak but can overwhelm other races
    public ZergRace(int playerID) {
        super(playerID);
        this.peeps = 20;
        this.scoreMultiplierTotal = 0.5f;
        this.canSetMultiplePeeps = true;

    }
}
