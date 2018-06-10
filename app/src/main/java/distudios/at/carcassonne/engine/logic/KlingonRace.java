package distudios.at.carcassonne.engine.logic;

public class KlingonRace extends Player {
    // Dominating and conquering race
    public KlingonRace(int playerID) {
        super(playerID);
        this.peeps = 7;
        this.scoreMultiplierTotal = 1.5f;
        this.scoreMultiplierCastles = 2.5f;
        this.peepStrength = 1.5f;
        this.canSetOnExistingPeeps = true;
    }
}
