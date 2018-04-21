package distudios.at.carcassonne.engine.logic;

public interface IGameController {

    void init();

    void action();

    void actionCardplacement(Card card);

    void dataReceived(Object data, int type);

}
