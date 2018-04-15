package distudios.at.carcassonne.engine.logic;

public class Card {

    private int id;
    private Orientation orientation;
    private int xCoordinate;
    private int yCoordinate;

    public Card(int id, Orientation orientation, int xCoordinate, int yCoordinate) {
        this.id = id;
        this.orientation = orientation;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString(){
        return "Id: "+ id + " Coordinates: " + xCoordinate + " | " + yCoordinate;
    }
}


