package blackjack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.*;

import account.AccountManager;

import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import ex.ExImageModifier;
import view.gui.MainGui;


public class Gui extends JFrame{

    private static final long serialVersionUID = 1L;
    
    private int puntata;
    private int chipvalue = 1;
    
    List<JLabel> dCards;
    List<JLabel> pCards;
    private final Game g;
    
    public Gui(final Dimension dim, AccountManager account) {
        
        g = new GameImpl(account);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //JPanel con immagine di sfondo
        final BackgroundPanel bgpanel = new BackgroundPanel(
                new ImageIcon("res/img/backgrounds/blackjacktableHD.png").getImage(),
                BackgroundPanel.SCALED, 0.0f, 0.0f);  
        //Area Pulsanti in fondo SUD
        final JPanel buttonsArea = new JPanel(new GridBagLayout());
        buttonsArea.setPreferredSize(new Dimension(150, 150));

        //codice ripetuto
        final JButton draw = new JButton("Hit"); 
        final JButton stay = new JButton("Stand");
        final JButton Double = new JButton("Double");
        final JButton reset = new JButton("Reset");      
        
        final JButton chip1 = new JButton("1"); 
        final JButton chip2 = new JButton("5");
        final JButton chip3 = new JButton("25");
        final JButton chip4 = new JButton("100");    
        final JButton chip5 = new JButton("500");
        
        
        final List<JButton> l = new ArrayList<>();
        l.add(draw);
        l.add(stay);
        l.add(Double);
        l.add(reset);   
        
        l.add(chip1);
        l.add(chip2);
        l.add(chip3);
        l.add(chip4);
        l.add(chip5);
        
        
        int i = 0;
        for (final JButton jb : l) { 
            jb.setPreferredSize(new Dimension(130, 100));
            jb.setForeground(Color.BLACK);
            jb.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
            buttonsArea.add(jb, setDimensionObj(i, 0, 0, 10, 10));
            i++;
        }
        
        //aggiungo il jpanel dei pulsanti al jpanel generale
        bgpanel.add(buttonsArea, BorderLayout.SOUTH);

        
        

        //JPanel a layer che mostra le carte del giocatore CENTER
        final JLayeredPane playerCardsPanel = new JLayeredPane();
        //playerCardsPanel.setLayout(null); //da rivedere
        pCards = new LinkedList<>(); //lista di JLabel, ciascuna sarà una carta del giocatore
        
        //Punteggio player
        final JLabel playerpoints = new JLabel();
        playerpoints.setForeground(Color.WHITE);
        playerpoints.setBounds(530, 20, 150, 150);
        playerpoints.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        playerCardsPanel.add(playerpoints, 0);
        
        final JLabel amountbet = new JLabel("0 €");
        amountbet.setForeground(Color.WHITE);
        amountbet.setBounds(350, 20, 150, 150);
        amountbet.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
        playerCardsPanel.add(amountbet, 0);
        
        
        final JButton bet = new JButton("Punta");
        bet.setBounds(350, 120, 90, 30);
        playerCardsPanel.add(bet, 0);
        
        final JButton resetta = new JButton("Resetta");
        resetta.setBounds(350, 155, 90, 30);
        playerCardsPanel.add(resetta, 0);
        
        final JButton conferma = new JButton("Conferma");
        conferma.setBounds(350, 190, 90, 30);
        playerCardsPanel.add(conferma, 0);
        
        
        //aggiunto il pannello con tutte le carte del player al pannello generale
        bgpanel.add(playerCardsPanel, BorderLayout.CENTER);
        
        
        
        
        
 
        //JPanel a layer che mostra le carte del dealer NORTH
        final JLayeredPane dealerCardsPanel = new JLayeredPane();
        //dealerCardsPanel.setLayout(null); //da rivedere
        dealerCardsPanel.setPreferredSize(new Dimension(300, 300));
        //Lista delle carte del dealer
        dCards = new LinkedList<>();
        final JLabel dpoints = new JLabel();
        dpoints.setForeground(Color.WHITE);
        dpoints.setBounds(530, 20, 150, 150);
        dpoints.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        
        dealerCardsPanel.add(dpoints, 0);
        
        
        final JLabel saldo = new JLabel("Saldo: " + account.getBalance());
        final JLabel puntata = new JLabel("Puntata: " + this.puntata);
        saldo.setForeground(Color.WHITE);
        saldo.setBounds(100, 80, 150, 150);
        saldo.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        
        puntata.setForeground(Color.WHITE);
        puntata.setBounds(100, 110, 150, 150);
        puntata.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 20));
        
        dealerCardsPanel.add(saldo);
        dealerCardsPanel.add(puntata);
        
        //aggiungo le carte del dealer al pannello generale NORTH
        bgpanel.add(dealerCardsPanel, BorderLayout.NORTH);
        
        
        dpoints.setText("");
        playerpoints.setText("");
        
        
        draw.setEnabled(false);
        stay.setEnabled(false);
        reset.setEnabled(false);
        Double.setEnabled(false);
  
        setPreferredSize(dim); 
        add(bgpanel);    
        pack();                                 
        setLocationRelativeTo(null); 
        setVisible(true); 
       

        //da fare refactoring
        draw.addActionListener(e -> {   
            if (g.getPlayerPoints() < 21) {
                g.askCard();
                addCard(pCards, g.getPlayerHand().get(g.getPlayerHand().size() - 1), playerCardsPanel);
                playerpoints.setText(String.valueOf(g.calculatePoints(g.getPlayerHand())));
            } 
      
            if (g.getPlayerPoints() >= 21) {
                stay.doClick();
            }
        });
        
        //codice ripetuto
        stay.addActionListener(e -> {
            //g.nextDealerMove();
            dCards.get(1).setIcon(new ImageIcon(new CardImpl(g.getDealerHand().get(1).getSuit(), 
                    g.getDealerHand().get(1).getValue()).getImg().getScaledInstance(100, 150, Image.SCALE_SMOOTH))); 
            dpoints.setText(String.valueOf(g.calculatePoints(g.getDealerHand())));
            
            System.out.println("ciao");
            while (g.getDealerPoints() < 17) {
                //aggiunge altre carte nel caso non basti la prima
                g.nextDealerMove();
                addCardDealer(dCards, g.getDealerHand().get(g.getDealerHand().size() - 1), dealerCardsPanel);
                dpoints.setText(String.valueOf(g.calculatePoints(g.getDealerHand())));
            }
            
            //dice chi ha vinto e i punteggi
            //System.out.println(checkWin(this.playerValue, this.dealerValue));
            
            if (g.checkWin()) {
                account.deposit(this.puntata); 
            } else {
                account.withdraw(this.puntata); 
            }
            
            saldo.setText("Saldo: " + account.getBalance());
            
            //disattiva i pulsanti
            draw.setEnabled(false);
            stay.setEnabled(false);
            reset.setEnabled(true);
            Double.setEnabled(false);
            
            
        });
        
        
        
        chip1.addActionListener(e -> {  
            this.chipvalue = 1;
        });
        
        chip2.addActionListener(e -> {  
            this.chipvalue = 5;
        });
        
        chip3.addActionListener(e -> {  
            this.chipvalue = 25;
        });
        
        chip4.addActionListener(e -> {  
            this.chipvalue = 100;
        });
        
        chip5.addActionListener(e -> {  
            this.chipvalue = 500;
        });
        
        bet.addActionListener(e -> {  
            this.puntata += this.chipvalue;
            bet.setText("");
            
            bet.removeAll();
            
            final JPanel jp = new JPanel(new BorderLayout());
            
            //jp.setPreferredSize(new Dimension(100, 100));
            
            
            
            final JLabel punt = new JLabel(String.valueOf(this.puntata), SwingConstants.CENTER);
            punt.setForeground(Color.WHITE);
            
            jp.setOpaque(false);
            
            //jp.setBackground(Color.BLACK);
            
            jp.add(punt, BorderLayout.CENTER);
            
            
            bet.add(jp, BorderLayout.CENTER);
            bet.setIcon(chooseChip(this.puntata));
            
            
            
            amountbet.setText(this.puntata + " €");
        });
        
        resetta.addActionListener(e -> {  
            this.puntata = 0;
            amountbet.setText(this.puntata + " €");
        });
       
        reset.addActionListener(e -> {  
            this.puntata = 0;
            amountbet.setText(this.puntata + " €");
            puntata.setText("Puntata: " + this.puntata);
            resetta.setEnabled(true);
            bet.setEnabled(true);
            conferma.setEnabled(true);
            
            draw.setEnabled(false);
            stay.setEnabled(false);
            reset.setEnabled(false);
            Double.setEnabled(false);
            
            dpoints.setText("");
            playerpoints.setText("");
            
            
            for(final JLabel j : pCards) {
                playerCardsPanel.remove(j);
            }
            
            for(final JLabel j : dCards) {
                dealerCardsPanel.remove(j);
            }

            dCards = new LinkedList<>();
            pCards = new LinkedList<>();     
            playerCardsPanel.revalidate();
            playerCardsPanel.repaint();
            dealerCardsPanel.revalidate();
            dealerCardsPanel.repaint();
            g.newTurn();
        });
        
        conferma.addActionListener(e -> {  
            resetta.setEnabled(false);
            bet.setEnabled(false);
            conferma.setEnabled(false);
            
            draw.setEnabled(true);
            stay.setEnabled(true);
            reset.setEnabled(true);
            Double.setEnabled(true);
            
            
            //prima carta scoperta, seconda coperta
            addCardDealer(dCards, g.getDealerHand().get(0), dealerCardsPanel);
            addCardDealer(dCards, g.getDealerHand().get(1), dealerCardsPanel);

            addCard(pCards, g.getPlayerHand().get(0), playerCardsPanel);
            addCard(pCards, g.getPlayerHand().get(1), playerCardsPanel);
            
            puntata.setText("Puntata: " + this.puntata);
            dpoints.setText(String.valueOf(g.getDealerHand().get(0).getValue()));
            playerpoints.setText(String.valueOf(g.calculatePoints(g.getPlayerHand())));
        });
        
        
        //NOTE NICO: FARE UNA FUNZIONE PRIVATA PER AGGIUNGERE CARTE A UNA LISTA DI JPANEL
        
        
    }
    
    
    private ImageIcon chooseChip(final int puntata) {
        
        if (puntata <  5) {
            final Image img = new ImageIcon("res/img/fiches/empty/1.png").getImage();
            return new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }
        
        if (puntata < 25) {
            final Image img = new ImageIcon("res/img/fiches/empty/5.png").getImage();
            return new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }
        
        if (puntata < 100) {
            final Image img = new ImageIcon("res/img/fiches/empty/25.png").getImage();
            return new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }
        
        if (puntata < 500) {
            final Image img = new ImageIcon("res/img/fiches/empty/100.png").getImage();
            return new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        }
        
        final Image img = new ImageIcon("res/img/fiches/empty/500.png").getImage();
        return new ImageIcon(img.getScaledInstance(40, 40, Image.SCALE_SMOOTH)); 
    }


    private void addCard(final List<JLabel> cards, final Card c, final JLayeredPane p) {
        cards.add(new JLabel());
        cards.get((cards.size() - 1)).setBounds(575 + ((cards.size() - 1) * 20),
                80 - ((cards.size() - 1) * 15), 150, 150);  //setbounds temporaneo?
        //setta al JLabel di index i l'immagine della nuova carta.
        cards.get((cards.size() - 1)).setIcon(new ImageIcon(c.getImg()
                .getScaledInstance(103, 150, Image.SCALE_SMOOTH)));
        //aggiunge la carta al pannello al layer 1
        p.add(cards.get((cards.size() - 1)), 0);

    }
    
    //da eliminare
    private void addCardDealer(final List<JLabel> cards, final Card c, final JLayeredPane p) {
        cards.add(new JLabel());
        cards.get((cards.size() - 1)).setBounds(575 + ((cards.size() - 1) * 35),
                80 + ((cards.size() - 1) * 10), 150, 150);  //setbounds temporaneo?
        //setta al JLabel di index i l'immagine della nuova carta.
        cards.get((cards.size() - 1)).setIcon(new ImageIcon(c.getImg()
                .getScaledInstance(103, 150, Image.SCALE_SMOOTH)));
        //aggiunge la carta al pannello al layer 1
        p.add(cards.get((cards.size() - 1)), 0);
    }
    
    
    
    private String checkWin(final int uservalue, final int dealervalue) {
        
        System.out.println("finegioco");
        
        System.out.println("Il tuo punteggio: " + uservalue);
        System.out.println("Il punteggio del dealer: " + dealervalue);
   
        if (uservalue > 21) {
            return "hai perso";
        }
        
        if (dealervalue > 21) {
            return "hai vinto";
        }
        
        if (uservalue == dealervalue) {
            return "patta";
        }
        if (uservalue > dealervalue) {
            return "hai vinto";
        } else {
            return "hai perso";
        }  
    }
    
    private GridBagConstraints setDimensionObj(final int gridx, final int gridy,
            final int spacedown, final int spaceright, final int spaceleft) {
        final GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(0, spaceleft, spacedown, spaceright); // terzo parametro definisce la distanza verticale
        //(verso il basso) tra i vari oggetti della gui
        c.gridx = gridx;
        c.gridy = gridy;
        return c;
    }
    
    /**
     * Testing. Da eliminare in seguito.
     */
    public static void main(final String[] args) {      
        //new Gui(new Dimension(1280, 720));
        new MainGui();
    }
    
}
