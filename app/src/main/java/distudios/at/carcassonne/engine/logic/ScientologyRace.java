package distudios.at.carcassonne.engine.logic;

public class ScientologyRace extends Player {
    public ScientologyRace(int playerID) {
        super(playerID);
        this.peeps = 15;
        this.scoreMultiplierTotal = 0.8f;
        this.canSetOnExistingPeeps = true;
        this.canOnlySetOnExisitingPeeps = true;
        this.peepStrength = 0.8f;
        this.canConvertPeeps = true;
        this.convertsPeepsOnMultipleOf = 2;

    }

}
