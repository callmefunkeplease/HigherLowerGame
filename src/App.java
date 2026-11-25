import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.io.File;
import java.io.IOException;

public class App {
    //This makes all the 6 buttons that represent the current hand
        static JButton[] playerHand=new JButton[6];
        static JLabel[] failedHand=new JLabel[6];
        static AISmart ai;
        static AIRandom ai2;
        static boolean canAiPlay=true;
        //1 is for the smart AI.
        static int aiType=0;
        static boolean winnerExists=false;
        static boolean playerHasWon=false;
        static boolean aiHasWon=false;
        static Scoreboard scoreBoard;



    public static void main(String[] args) throws Exception {

        JFrame playerGame = new JFrame("Player Game");
        playerGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playerGame.setSize(800, 700);
        scoreBoard = new Scoreboard();
        
        JPanel game = new JPanel();
        game.setBackground(Color.BLUE);
        
        Deck deck = new Deck("Deck");

        JButton easyDifficultyButton=new JButton("Easy Mode?");
        JButton hardDifficultyButton=new JButton("Hard Mode?");
        easyDifficultyButton.setBackground(Color.GREEN);
        easyDifficultyButton.setForeground(Color.BLACK);
        easyDifficultyButton.setFont(new Font("Arial", Font.BOLD, 20));
        easyDifficultyButton.addActionListener(e -> {
            aiType=2;
            ai2 = new AIRandom(deck.hand);
            canAiPlay=true;
            scoreBoard.AIColor=Color.GREEN;
            scoreBoard.typeOfAI=2;
            try {
                scoreBoard.updateScoreboard(aiType);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            easyDifficultyButton.setVisible(false);
            hardDifficultyButton.setVisible(false);
        });
        game.add(easyDifficultyButton);
        easyDifficultyButton.setVisible(true);

        
        hardDifficultyButton.setBackground(Color.RED);
        hardDifficultyButton.setForeground(Color.BLACK);
        hardDifficultyButton.setFont(new Font("Arial", Font.BOLD, 20));
        hardDifficultyButton.addActionListener(e -> {
            aiType=1;
            ai = new AISmart(deck.hand);
            canAiPlay=true;
            hardDifficultyButton.setVisible(false);
            try {
                scoreBoard.updateScoreboard(aiType);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            easyDifficultyButton.setVisible(false);
        });
        game.add(hardDifficultyButton);
        hardDifficultyButton.setVisible(true);
        //Pretty much the "System.out.println()" equiivalent for the GUI
        game.add(Box.createRigidArea(new Dimension(800, 1))); 


        JLabel instructions0=new JLabel("Your goal is to guess what the next card is.");
        instructions0.setForeground(Color.WHITE);
        instructions0.setFont(new Font("Arial", Font.BOLD, 20));
        game.add(instructions0);
        game.add(Box.createRigidArea(new Dimension(800, 1))); 
        JLabel instructions=new JLabel("Left Click to guess lower, Right Click to guess higher");
        instructions.setForeground(Color.WHITE);
        instructions.setFont(new Font("Arial", Font.BOLD, 20));
        game.add(instructions);

        //Pretty much the "System.out.println()" equiivalent for the GUI
        game.add(Box.createRigidArea(new Dimension(800, 1))); 

        playerHasWon=false;
        aiHasWon=false;


        File folder = new File("src/Cards");
        File[] Cards = folder.listFiles();
        for(int i=0; i<Cards.length; i++){
            System.out.println(Cards[i]);
        }
        File[][] cardImages = new File[Cards.length][];
        for(int i=0; i<Cards.length; i++){
            cardImages[i] = Cards[i].listFiles();
        }


        

        //Add these later
        //JButton decreaseAiWins=new JButton("-");
        
        //JButton resetWins=new JButton("Reset Wins");


        for(int i=0;i<9;i++){
            for(int j=0;j<4;j++){
                System.out.println(cardImages[i][j]);
            }
        }
        

        
        //This is the loop that makes the buttons work.
        for(int i=0;i<playerHand.length;i++){
            ImageIcon typeOfCard=new ImageIcon("src/Cards/"+Cards[deck.getCard(i)].getName()+"/"+cardImages[deck.getCard(i)][4-deck.numCards[deck.getCard(i)]].getName());
            deck.numCards[deck.getCard(i)]--;


            Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
            typeOfCard = new ImageIcon(typeOfCard2);


            final int index=i;
            playerHand[i]=new JButton();
            playerHand[i].setIcon(typeOfCard);
            playerHand[i].addMouseListener(new MouseAdapter() {
                //This is the mouse listener that listens for right or left click, then checks higher or lower accordingly.
                @Override
                public void mousePressed(MouseEvent e){
                    //Left Click
                    if (e.getButton()==MouseEvent.BUTTON1){
                        System.out.print("Left Click");
                        
                        int temp=deck.flip(index);
                        
                        if(!playerHasWon){
                            scoreBoard.playerCardsLeft.setText(Integer.parseInt(scoreBoard.playerCardsLeft.getText())-1+"");
                        }
                        if(!aiHasWon&&aiType!=0){
                            scoreBoard.AICardsLeft.setText(Integer.parseInt(scoreBoard.AICardsLeft.getText())-1+"");
                        }else if(aiType==0){
                            scoreBoard.AICardsLeft.setText("0");
                        }

                        if(temp>=deck.getCard(index)){
                            System.out.println("Yes, it was Lower");
                            
                            ImageIcon typeOfCard=new ImageIcon("src/Cards/"+Cards[deck.getCard(index)].getName()+"/"+cardImages[deck.getCard(index)][deck.numCards[deck.getCard(index)]].getName());
                            
                            Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                            typeOfCard = new ImageIcon(typeOfCard2);
                            playerHand[index].setIcon(typeOfCard);
                            
                        }else{
                            System.out.println("No, it was Higher");
                            
                            ImageIcon failCard=new ImageIcon("src/Fail.png");
                            
                            Image typeOfCard2 = failCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                            
                            failCard = new ImageIcon(typeOfCard2);
                            

                            
                            
                            playerHand[index].setVisible(false);
                            
                            failedHand[index]=new JLabel(failCard);
                            game.add(failedHand[index]);
                            failedHand[index].setVisible(true);

                            for(int i=index+1;i<playerHand.length;i++){
                                if(playerHand[i].isVisible()){
                                game.remove(playerHand[i]);
                                game.add(playerHand[i]);
                                }
                                if(failedHand[i]!=null){
                                    game.remove(failedHand[i]);
                                    game.add(failedHand[i]);
                                }
                            }
                            int hasThePlayerLost=0;
                            for(int i=0;i<failedHand.length;i++){
                                if(failedHand[i]!=null&&failedHand[i].isVisible()){
                                    hasThePlayerLost++;
                                }
                            }
                            if(hasThePlayerLost==6){
                                System.out.println("Player has lost");
                                if(!winnerExists){
                                    scoreBoard.winsPlusOne(aiType*2);
                                    winnerExists=true;
                                    aiJustWon();
                                }
                            }



                        }
                        if(aiType==1){
                            if(canAiPlay){
                                canAiPlay=ai.AIPlay(deck.getCard(index)+1);
                            }else{
                                System.out.println("AI cannot play anymore");
                                if(!winnerExists){
                                    scoreBoard.winsPlusOne(1);
                                    winnerExists=true;
                                    playerJustWon();
                                }
                                //If time permits, add the bonus features like keeping track of the win rate
                                //time permitted. It is done.
                            }
                        }
                        if(aiType==2){
                            if(canAiPlay){
                                canAiPlay=ai2.AIPlay(deck.getCard(index)+1);
                            }else{
                                System.out.println("AI cannot play anymore");
                                if(!winnerExists){
                                    scoreBoard.winsPlusOne(3);
                                    winnerExists=true;
                                    playerJustWon();
                                }
                                //If time permits, add the bonus features like keeping track of the win rate
                                //time permitted. It is done.
                            }
                        }
                    }
                    //Right Click
                    else if (e.getButton()==MouseEvent.BUTTON3){
                        System.out.print("Right Click");

                        int temp=deck.flip(index);
                        
                        if(!playerHasWon){
                            scoreBoard.playerCardsLeft.setText(Integer.parseInt(scoreBoard.playerCardsLeft.getText())-1+"");
                        }
                        
                        if(!aiHasWon&&aiType!=0){
                            scoreBoard.AICardsLeft.setText(Integer.parseInt(scoreBoard.AICardsLeft.getText())-1+"");
                        }else if(aiType==0){
                            scoreBoard.AICardsLeft.setText("0");
                        }


                        if(temp<=deck.getCard(index)){
                            System.out.println("Yes, it was Higher");
                            
                            ImageIcon typeOfCard=new ImageIcon("src/Cards/"+Cards[deck.getCard(index)].getName()+"/"+cardImages[deck.getCard(index)][deck.numCards[deck.getCard(index)]].getName());
                            
                            Image typeOfCard2 = typeOfCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                            typeOfCard = new ImageIcon(typeOfCard2);
                            playerHand[index].setIcon(typeOfCard);
                        }else{
                            System.out.println("No, it was Lower");
                            
                            ImageIcon failCard=new ImageIcon("src/Fail.png");
                            
                            Image typeOfCard2 = failCard.getImage().getScaledInstance(200, 290, java.awt.Image.SCALE_SMOOTH);
                            
                            failCard = new ImageIcon(typeOfCard2);
                            playerHand[index].setVisible(false);
                            
                            failedHand[index]=new JLabel(failCard);
                            game.add(failedHand[index]);
                            failedHand[index].setVisible(true);

                            for(int i=index+1;i<playerHand.length;i++){
                                if(playerHand[i].isVisible()){
                                game.remove(playerHand[i]);
                                game.add(playerHand[i]);
                                }
                                if(failedHand[i]!=null){
                                    game.remove(failedHand[i]);
                                    game.add(failedHand[i]);
                                }
                            }
                            int hasThePlayerLost=0;
                            for(int i=0;i<failedHand.length;i++){
                                if(failedHand[i]!=null&&failedHand[i].isVisible()){
                                    hasThePlayerLost++;
                                }
                            }
                            if(hasThePlayerLost==6){
                                System.out.println("Player has lost");
                                if(!winnerExists){
                                    scoreBoard.winsPlusOne(aiType*2);
                                    winnerExists=true;
                                    aiJustWon();
                                }
                            }
                        }
                        if(aiType==1){
                            //playerCardsLeft.setText(deck.add()+"");
                            if(canAiPlay){
                                canAiPlay=ai.AIPlay(deck.getCard(index)+1);
                            }else{
                                System.out.println("AI cannot play anymore");
                                if(!winnerExists){
                                    scoreBoard.winsPlusOne(aiType*2-1);
                                    winnerExists=true;
                                    playerJustWon();
                                }
                                //If time permits, add the bonus features like keeping track of the win rate in a seperate file
                            }
                        }
                        if(aiType==2){
                            if(canAiPlay){
                                int imHavingAWeirdErrorAndIHopeThisFixesItForSomeReason=deck.getCard(index)+1;
                                canAiPlay=ai2.AIPlay(imHavingAWeirdErrorAndIHopeThisFixesItForSomeReason);
                                System.out.println(deck.getCard(index)+1);
                                System.out.println(deck.getCard(index)+1);
                                System.out.println(deck.getCard(index)+1);
                                System.out.println(deck.getCard(index)+1);
                            }else{
                                System.out.println("AI cannot play anymore");
                                if(!winnerExists){
                                    scoreBoard.winsPlusOne(3);
                                    winnerExists=true;
                                    playerJustWon();
                                }
                                //If time permits, add the bonus features like keeping track of the win rate
                                //time permitted. It is done.
                            }
                        }
                    }
                }
            });


            
            game.add(playerHand[i]);

            
        }
        
        
        

        deck.printNumCards();
        playerGame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if(!winnerExists){
                    scoreBoard.winsPlusOne(aiType * 2); 
                }
            }
        });

        playerGame.add(game);
        playerGame.setVisible(true);
    }

    public static void playerJustWon() {
        playerHasWon=true;
    }
    public static void aiJustWon() {
        aiHasWon=true;
    }
    
}
