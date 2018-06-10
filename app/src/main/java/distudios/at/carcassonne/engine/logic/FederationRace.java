package distudios.at.carcassonne.engine.logic;

public class FederationRace extends Player {
    //UNENDLICHE WEITEN
    public FederationRace(int playerID) {
        super(playerID);
        this.peeps = 15;
        this.peepStrength = 0.8f;
        this.canSetMultiplePeeps = true;
        this.scoreMultiplierStreets = 3;

    }

}
