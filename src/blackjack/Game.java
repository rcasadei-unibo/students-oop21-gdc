package blackjack;

import java.util.List;

public interface Game {
    
    void askCard();
    
    void stay();
    
    void split();
    
    void askDouble();
    
    void startGame();
    
    void newTurn();

    boolean checkWin();
    
    void dealerDraw();
    
    void dealerStay();
    
    List<Card> getPlayerHand();
    
    List<Card> getDealerHand();
    
    int calculatePoints(List<Card> cards);
    
    int getPlayerPoints();
    
    int getDealerPoints();
    
    
    void nextDealerMove();

}
