package distudios.at.carcassonne.engine.logic;

public enum Orientation {
    NORTH, SOUTH, EAST, WEST;

    public static Orientation getIdOrientation(int id){
        if(id==0){
            return Orientation.NORTH;
        }else if(id==1) {
            return Orientation.EAST;
        }else if(id==2){
            return Orientation.SOUTH;
        }else if(id==3){
            return Orientation.WEST;
        }else{
            return null;
        }
    }
}