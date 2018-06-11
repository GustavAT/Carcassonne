package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.List;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import distudios.at.carcassonne.networking.connection.OrientationTypeConverter;

@JsonObject
public class Card {

    @JsonField
    private int id;
    @JsonField(typeConverter = OrientationTypeConverter.class)
    private Orientation orientation;
    @JsonField
    private int xCoordinate;
    @JsonField
    private int yCoordinate;
    @JsonField
    private ArrayList<PeepPosition> marks;

    public Card() {}

    public Card(int id,  int xCoordinate, int yCoordinate, Orientation orientation) {
        this.id = id;
        this.orientation = orientation;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.marks = new ArrayList<PeepPosition>();
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

    public ArrayList<PeepPosition> getMarks() { return marks; }

    public void setMarks(ArrayList<PeepPosition> marks){ this.marks = marks;}

    public void setMark(PeepPosition mark) {
        if(!(this.marks.contains(mark))) {
            this.marks.add(mark);
        }
    }

    @Override
    public String toString(){
        return "Id: "+ id + " Coordinates: " + xCoordinate + " | " + yCoordinate;
    }



    public static Orientation getAbsoluteOrientation(Orientation card, Orientation offset){
        int rotation=0;
        if(offset==Orientation.NORTH){
            rotation=0;
        }
        else if(offset==Orientation.EAST){
            rotation=1;
        }
        else if(offset==Orientation.SOUTH){
            rotation=2;
        }
        else{
            rotation=3;
        }


        for(int i=0;i<rotation;i++){
            if(card==Orientation.NORTH){
                card=Orientation.WEST;
            }
            else if(card==Orientation.EAST){
                card=Orientation.NORTH;
            }
            else if(card==Orientation.SOUTH){
                card=Orientation.EAST;
            }
            else{
                card=Orientation.SOUTH;
            }
        }

        return card;
    }
}
