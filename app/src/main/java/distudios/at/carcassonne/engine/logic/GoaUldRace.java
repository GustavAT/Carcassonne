package distudios.at.carcassonne.engine.logic;

public class GoaUldRace extends Player {
    public GoaUldRace(int playerID) {
        super(playerID);
        this.peeps = 10;
        this.scoreMultiplierTotal = 1;
        this.canSetOnExistingPeeps = true;
        this.canOnlySetOnExisitingPeeps = true;
        this.peepStrength = 1.2f;

    }

}
