import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

//THIS CLASS IS AI 2. THE TYPE OF AI IS 2.
public class AIRandom {
    static JFrame frame;
    static JPanel randomPanel= new JPanel();
    static JLabel[] randomHand = new JLabel[6];
    static File folder = new File("src/Cards");
    static File[] Cards = folder.listFiles();
    static File[][] cardImages = new File[Cards.length][4];
    ArrayList<Integer> hand = new ArrayList<Integer>();
    int[] cardCounter= {4,4,4,4,4,4,4,4,4,4,4,4,4};
    
    public AIRandom(int[] starter){

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

            randomHand[i]=new JLabel(typeOfCard);
            randomPanel.add(randomHand[i]);

        }
        frame = new JFrame("AI Easy");
        frame.setSize(800, 700);
        frame.setLocation(1100, 0);
        frame.add(randomPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if(!App.winnerExists){
                    App.scoreBoard.winsPlusOne(4); 
                }
            }
        });
        randomPanel.setBackground(Color.GREEN);
        randomPanel.setSize(800, 700);
        randomPanel.setLocation(1100, 0);
        randomPanel.setVisible(true);
    }

    public boolean AIPlay(int flippedCard){
        int index=(int)(Math.random()*6);
        int card=hand.get(index)+1;

        int thisNumberChecksIfTheAICanPlayAnyCard=0;
        for(int i=0;i<6;i++){
            if(hand.get(i)==-99){
                thisNumberChecksIfTheAICanPlayAnyCard++;
            }
        }
        if(thisNumberChecksIfTheAICanPlayAnyCard==6){
            return false;
        }
        while(hand.get(index)==-99){
            index=(int)(Math.random()*6);
            card=hand.get(index)+1;
        }
        System.out.println(card);

        if(Math.random()*13<card){
            System.out.println("guess lower");
            System.out.println("guess lower");
            System.out.println("guess lower");
            //this will choose to guess lower.
            if (flippedCard<=card){
                //it guessed correctly.
                hand.set(index,flippedCard);
                ImageIcon typeOfCard=new ImageIcon("src/Cards/"+
                Cards[flippedCard-1].getName()+"/"+
                cardImages[flippedCard-1]
                [cardCounter[flippedCard-1]-1].getName());
                Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                typeOfCard = new ImageIcon(typeOfCard2);
                randomHand[index].setIcon(typeOfCard);
                
            }else{
                //it guessed incorrectly.
                hand.set(index, -99);
                ImageIcon typeOfCard=new ImageIcon("src/Fail.png");
                Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                typeOfCard = new ImageIcon(typeOfCard2);
                randomHand[index].setIcon(typeOfCard);
                cardCounter[card-1]--;
                
            }
        }else{
            //this will choose to guess higher.
            System.out.println("guess higher");
            System.out.println("guess higher");
            System.out.println("guess higher");
            if (flippedCard>=card){
                //it guessed correctly.
                hand.set(index,flippedCard);
                ImageIcon typeOfCard=new ImageIcon("src/Cards/"+
                Cards[flippedCard-1].getName()+"/"
                +cardImages[flippedCard-1]
                [cardCounter[flippedCard-1]-1].getName());
                Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                typeOfCard = new ImageIcon(typeOfCard2);
                randomHand[index].setIcon(typeOfCard);
        }else{
                //it guessed incorrectly.
                hand.set(index, -99);
                ImageIcon typeOfCard=new ImageIcon("src/Fail.png");
                Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                typeOfCard = new ImageIcon(typeOfCard2);
                randomHand[index].setIcon(typeOfCard);
                cardCounter[card-1]--;
            }
        }
        

        return true;
    }

}
