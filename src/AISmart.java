import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

public class AISmart {
    ArrayList<Integer> hand = new ArrayList<Integer>();
    ArrayList<Integer> sortedHand = new ArrayList<Integer>();
    JFrame AIGame=new JFrame("AI Hard");
    JPanel game = new JPanel();
    JLabel[] AIHand=new JLabel[6];
    JLabel[] failedHand=new JLabel[6];

    File folder = new File("src/Cards");    
    File[] Cards = folder.listFiles();
    File[][] cardImages = new File[Cards.length][4];

    //Counts how many of each card has been played
    int[] cardCounter={4,4,4,4,4,4,4,4,4,4,4,4,4};
    
    
    public AISmart(int[] starter){

        //Getting the images of the cards accessable
        for(int i=0; i<Cards.length; i++){
            cardImages[i] = Cards[i].listFiles();
        }
        
        
        for(int i=0;i<6;i++){
            hand.add(starter[i]);
            ImageIcon typeOfCard=new ImageIcon("src/Cards/"+Cards[hand.get(i)].getName()+"/"+cardImages[hand.get(i)][4-cardCounter[hand.get(i)]].getName());
            cardCounter[hand.get(i)]--;

            Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
            typeOfCard = new ImageIcon(typeOfCard2);

            AIHand[i]=new JLabel(typeOfCard);
            game.add(AIHand[i]);
        }
        for(int i=0;i<hand.size();i++){
            hand.set(i,hand.get(i)+1);  
        }
        sortedHand.addAll(hand);
        sortedHand.sort(null);

        AIGame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if(!App.winnerExists){
                    App.scoreBoard.winsPlusOne(2); 
                }
            }
        });

        game.setBackground(Color.RED);
        AIGame.add(game);
        AIGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        AIGame.setSize(800, 700);
        AIGame.setLocation(1100, 0);
        AIGame.setVisible(true);
    }



//returns true if the AI has a card to play
    //returns false if the AI has no cards to play
    public boolean AIPlay(int card){
        //counts the number of cards higher than the lowest card
        double cardsHigher=0;
        System.out.println();
        for(int i=sortedHand.get(0)-1;i<13;i++){
            cardsHigher+=cardCounter[i];
        }
        
        //counts the number of cards lower than the highest card
        double cardsLower=0;
        System.out.println();
        for(int i=0;i<sortedHand.get(sortedHand.size()-1);i++){
            cardsLower+=cardCounter[i];
        }

        //counts the number of cards left in the deck
        double numCards=0;
        for(int i=0;i<13;i++){
            numCards+=cardCounter[i];
        }

        //these are to get pecentages of higher and lower cards, but honestly I don't think they are needed.
        //I just know that they can't hurt.
            double higher=cardsHigher/numCards;            
            double lower=cardsLower/numCards;

            System.out.println("Card: "+card);
            cardCounter[card-1]--;
            System.out.print("Sorted Hand: ");
            for(int i=0;i<sortedHand.size();i++){
                System.out.print(sortedHand.get(i)+", ");  
            }
            System.out.println();

            System.out.print ("hand: ");
            for(int i=0;i<hand.size();i++){
                System.out.print(hand.get(i)+", ");  
            }


        if(higher>=lower){
            int index=hand.indexOf(sortedHand.get(0));


            System.out.println("TRYING TO REPLACE THE LOWEST CARD");
            System.out.println("INDEX: "+index);
            System.out.println("SMALLEST CARD"+sortedHand.get(0));

            System.out.println("Index "+index);
            if(card>=sortedHand.get(0)){
                hand.set(index,card);
                ImageIcon typeOfCard=new ImageIcon("src/Cards/"+
                Cards[card-1].getName()+"/"+cardImages[card-1][cardCounter[card-1]].getName());
                Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                typeOfCard = new ImageIcon(typeOfCard2);
                AIHand[index].setIcon(typeOfCard);

                sortedHand.set(0,card);
                sortedHand.sort(null);
            }else{
                hand.set(index,-999);
                ImageIcon failCard=new ImageIcon("src/Fail.png");
                Image typeOfCard2 = failCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                failCard = new ImageIcon(typeOfCard2);
                AIHand[index].setIcon(failCard);
                sortedHand.remove(0);
            }
        }else if(higher<=lower){
            int index=hand.indexOf(sortedHand.get(sortedHand.size()-1));


            System.out.println("TRYING TO REPLACE THE HIGHEST CARD");
            System.out.println("INDEX: "+index);
            System.out.println("BIGGEST CARD"+sortedHand.get(sortedHand.size()-1));


            System.out.println("Index "+index);
            if(card<=sortedHand.get(sortedHand.size()-1)){
                hand.set(index,card);
                ImageIcon typeOfCard=new ImageIcon("src/Cards/"+
                Cards[card-1]
                .getName()
                +"/"+
                
                cardImages[card-1]

                [cardCounter[card-1]]
                .getName()
                );

                Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                typeOfCard = new ImageIcon(typeOfCard2);
                AIHand[index].setIcon(typeOfCard);

                sortedHand.set(sortedHand.size()-1,card);
                sortedHand.sort(null);
            }else{
                hand.set(index,-999);
                ImageIcon failCard=new ImageIcon("src/Fail.png");
                Image typeOfCard2 = failCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                failCard = new ImageIcon(typeOfCard2);
                AIHand[index].setIcon(failCard);
                sortedHand.remove(sortedHand.size()-1);
            }
        }
        if(sortedHand.size()>0){
            return true;
        }
        return false;
    }
}
