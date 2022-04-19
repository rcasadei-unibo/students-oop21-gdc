package view.menu;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import account.AccountManager;
import roulette.Roulette;
import view.gui.MenuManager;
import view.menu.games.GamesPanel;
import view.menu.games.roulette.DisplayWinningNumbers;
import view.menu.games.roulette.Table;

import java.awt.BorderLayout;
import java.awt.Color;

public class RouletteMenu extends JPanel implements Menu {
    
    public RouletteMenu(final MenuManager frame, final AccountManager account){
        this.setLayout(new BorderLayout());
//        this.setBackground(new Color(0, 118, 58));    //VERDE COME IL TAVOLO DELLA ROULETTE
//        this.setPreferredSize(frame.getSizeMenu());
        final DisplayWinningNumbers l = new DisplayWinningNumbers();
        final Roulette roulette = new Roulette(l);
        this.add(new Table());
        
        
        this.add(l, BorderLayout.NORTH);
        this.add(new GamesPanel(), BorderLayout.SOUTH);
        roulette.start();
    }

    @Override
    public JPanel getMenu() {
        return this;
    }

}
