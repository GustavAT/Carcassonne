package distudios.at.carcassonne.engine.logic;

public class ExtendedCard {
    private int id;
    private CardSide top;
    private CardSide left;
    private CardSide right;
    private CardSide down;

    public ExtendedCard(int id, CardSide top, CardSide left, CardSide right, CardSide down) {
        this.id = id;
        this.top = top;
        this.left = left;
        this.right = right;
        this.down = down;
    }

    public int getId() {
        return id;
    }

    public CardSide getTop() {
        return top;
    }

    public CardSide getLeft() {
        return left;
    }

    public CardSide getRight() {
        return right;
    }

    public CardSide getDown() {
        return down;
    }

    public void setTop(CardSide top) {
        this.top = top;
    }

    public void setLeft(CardSide left) {
        this.left = left;
    }

    public void setRight(CardSide right) {
        this.right = right;
    }

    public void setDown(CardSide down) {
        this.down = down;
    }
}
