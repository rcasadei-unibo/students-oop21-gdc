package view.menu.games.roulette;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import account.AccountManager;
import roulette.Roulette;
import view.gui.MenuManager;
import view.menu.GameMenu;

import java.awt.BorderLayout;
import java.awt.Color;

//RouletteGame
public class RouletteGame extends JPanel implements Game{
    
    final DisplayWinningNumbers winningNumbers;
    
    public RouletteGame(){
        this.setLayout(new BorderLayout());
        
        Roulette roulette = new Roulette();
        this.winningNumbers = new DisplayWinningNumbers(roulette);
        
        this.add(new Table());
        this.add(this.winningNumbers, BorderLayout.NORTH);
        
    }

    @Override
    public void endBetting() {
        winningNumbers.display();
    }

    @Override
    public JPanel getGame() {
        return this;
    }

}
