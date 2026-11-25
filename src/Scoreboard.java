import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scoreboard {
    static JLabel playerWins;
    static JLabel AIWins;
    static JLabel playerWinRate;
    static JLabel AIWinRate;
    static JPanel scorePanel;
    static Color AIColor;
    static JLabel playerCardsLeft;
    static JLabel AICardsLeft;
    static int typeOfAI=1; //1 is Smart AI, 2 is Dumb AI
    static JButton subtractAIWinButton;

    public Scoreboard() throws IOException{
        AIColor = Color.WHITE;

        JFrame scoreBoard = new JFrame("Scoreboard");
        scoreBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scoreBoard.setSize(300,1000);
        scoreBoard.setLocation(800, 0);
        
        scoreBoard.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Increment AI wins when the window is closed
                if(!App.winnerExists){
                    winsPlusOne(typeOfAI * 2); // Increment AI's win count
                }
            }
        });


        scorePanel = new JPanel();
        scorePanel.setBackground(Color.WHITE);
        scorePanel.setLayout(null);


        JLabel playerTitle=new JLabel("Player");
        playerTitle.setBounds(120, 10, 100, 25);
        playerTitle.setForeground(Color.BLACK);
        playerTitle.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(playerTitle);

        JLabel AITitle=new JLabel("AI");
        AITitle.setBounds(230, 10, 100, 25);
        AITitle.setForeground(Color.BLACK);
        AITitle.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(AITitle);


        JLabel cardsLeftTitle=new JLabel("Cards Left");
        cardsLeftTitle.setBounds(5, 45, 100, 25);
        cardsLeftTitle.setForeground(Color.BLACK);
        cardsLeftTitle.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(cardsLeftTitle);

        playerCardsLeft=new JLabel("46");
        playerCardsLeft.setBounds(135, 45, 100, 25);
        playerCardsLeft.setForeground(Color.BLUE);
        playerCardsLeft.setFont(new Font("Arial", Font.PLAIN, 20));
        scorePanel.add(playerCardsLeft);

        AICardsLeft=new JLabel("46");
        AICardsLeft.setBounds(230, 45, 100, 25);
        AICardsLeft.setForeground(AIColor);
        AICardsLeft.setFont(new Font("Arial", Font.PLAIN, 20));
        scorePanel.add(AICardsLeft);
        
        
        JLabel winsTitle=new JLabel("Wins");
        winsTitle.setBounds(55, 80, 100, 25);
        winsTitle.setForeground(Color.BLACK);
        winsTitle.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(winsTitle);

        playerWins=new JLabel("");
        playerWins.setBounds(135, 80, 100, 25);
        playerWins.setForeground(Color.BLUE);
        playerWins.setFont(new Font("Arial", Font.PLAIN, 20));
        scorePanel.add(playerWins);

        AIWins=new JLabel(readWins(2)+"");
        AIWins.setBounds(230, 80, 100, 25);
        AIWins.setForeground(AIColor);
        AIWins.setFont(new Font("Arial", Font.PLAIN, 20));
        scorePanel.add(AIWins);


        JLabel winRateTitle=new JLabel("Win Rate");
        winRateTitle.setBounds(20, 115, 100, 25);
        winRateTitle.setForeground(Color.BLACK);
        winRateTitle.setFont(new Font("Arial", Font.BOLD, 20));
        scorePanel.add(winRateTitle);

        int gamesPlayed=(readWins(1)+readWins(2));
        if(gamesPlayed==0) {
            gamesPlayed=1;
        }
        playerWinRate=new JLabel("null%");
        playerWinRate.setBounds(135, 115, 100, 25);
        playerWinRate.setForeground(Color.BLUE);
        playerWinRate.setFont(new Font("Arial", Font.PLAIN, 20));
        scorePanel.add(playerWinRate);

        AIWinRate=new JLabel(Math.round(((double)readWins(typeOfAI*2)/(double)gamesPlayed)*100)+"%");
        AIWinRate.setBounds(230, 115, 100, 25);
        AIWinRate.setForeground(AIColor);
        AIWinRate.setFont(new Font("Arial", Font.PLAIN, 20));
        scorePanel.add(AIWinRate);


        

        JButton resetButton = new JButton("Reset Wins to 0");
        resetButton.setBounds(50, 150, 200, 25);
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));

        resetButton.addActionListener(e -> {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/Wins.txt"))) {
                if(typeOfAI==1) {
                    writer.write("0\n0");
                }else if(typeOfAI==2) {
                    writer.write("\n\n0\n0");
                }else if(typeOfAI==3) {
                    writer.write("\n\n\n\n0\n0");
                }else if(typeOfAI==4) {
                    //this one is unlikely to be used but just in case. 
                    writer.write("\n\n\n\n\n\n0\n0");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            playerWins.setText("0");
            AIWins.setText("0");
            playerWinRate.setText("0%");
            AIWinRate.setText("0%");
        });

        JButton subtractPlayerWinButton = new JButton("Player -1 Win?");
        subtractPlayerWinButton.setBounds(50, 200, 200, 25);
        subtractPlayerWinButton.setBackground(Color.BLUE);
        subtractPlayerWinButton.setForeground(Color.WHITE);
        subtractPlayerWinButton.setFont(new Font("Arial", Font.BOLD, 20));
        subtractPlayerWinButton.addActionListener(e -> {
            try {
                //prevents some dividing by 0 errors
                int currentWins = readWins(typeOfAI * 2 - 1);
                if (currentWins > 0) {try {
                            List<String> lines = Files.readAllLines(Paths.get("src/Wins.txt"));
                    
                            int currentValue = Integer.parseInt(lines.get(typeOfAI*2-2).trim());
                            lines.set(typeOfAI*2-2, String.valueOf(currentValue - 1));
                    
                            Files.write(Paths.get("src/Wins.txt"), lines);
                        } catch (IOException | NumberFormatException c) {
                            c.printStackTrace();
                        }
                        try {
                            playerWins.setText(readWins(typeOfAI * 2 - 1) + "");
                            AIWins.setText(readWins(typeOfAI * 2) + "");
                    
                            playerWinRate.setText(Math.round((double)(readWins(typeOfAI * 2 - 1) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");
                            AIWinRate.setText(Math.round((double)(readWins(typeOfAI * 2) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");
                    
                            playerWins.repaint();
                            AIWins.repaint();
                        } catch (IOException c) {
                            c.printStackTrace();
                        }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        scorePanel.add(subtractPlayerWinButton);

        subtractAIWinButton = new JButton("AI -1 Win?");
        subtractAIWinButton.setBounds(50, 250, 200, 25);
        subtractAIWinButton.setBackground(AIColor);
        subtractAIWinButton.setForeground(Color.WHITE);
        subtractAIWinButton.setFont(new Font("Arial", Font.BOLD, 20));
        subtractAIWinButton.addActionListener(e -> {
            try {
                //prevents some dividing by 0 errors
                int currentWins = readWins(typeOfAI * 2);
                if (currentWins > 0) {try {
                            List<String> lines = Files.readAllLines(Paths.get("src/Wins.txt"));
                    
                            int currentValue = Integer.parseInt(lines.get(typeOfAI*2-1).trim());
                            lines.set(typeOfAI*2-1, String.valueOf(currentValue - 1));
                    
                            Files.write(Paths.get("src/Wins.txt"), lines);
                        } catch (IOException | NumberFormatException c) {
                            c.printStackTrace();
                        }
                        try {
                            playerWins.setText(readWins(typeOfAI * 2 - 1) + "");
                            AIWins.setText(readWins(typeOfAI * 2) + "");
                    
                            playerWinRate.setText(Math.round((double)(readWins(typeOfAI * 2 - 1) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");
                            AIWinRate.setText(Math.round((double)(readWins(typeOfAI * 2) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");
                    
                            playerWins.repaint();
                            AIWins.repaint();
                        } catch (IOException c) {
                            c.printStackTrace();
                        }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        scorePanel.add(subtractAIWinButton);

        //scorePanel.add(resetButton);
        //I changed my mind about this button. I don't want to have it in the game.
        //You can just click -1 until you're happy.

        JButton closeButton = new JButton("Close Game");
        closeButton.setBounds(50, 300, 200, 25);
        closeButton.setBackground(Color.BLACK);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.addActionListener(e -> {
            JFrame.getFrames();
            if(!App.winnerExists){
                winsPlusOne(typeOfAI * 2); 
            }
            for (Window frame : JFrame.getOwnerlessWindows()) {
                    frame.dispose(); // Close the scoreboard window
            }

        });
        scorePanel.add(closeButton);


        scoreBoard.add(scorePanel);
        scoreBoard.setVisible(true);
    }

    
    public void updateScoreboard(int AIType) throws IOException {
        typeOfAI = AIType;
        if(AIType == 1) {
            AIColor = Color.RED;
        } else if(AIType == 2) {
            AIColor = Color.GREEN;
        }
        subtractAIWinButton.setBackground(AIColor);
        AIWinRate.setForeground(AIColor);


        AIWins.setForeground(AIColor);
        playerWins.setText(readWins(typeOfAI * 2 - 1) + "");
        AIWins.setText(readWins(typeOfAI * 2) + "");
        AICardsLeft.setForeground(AIColor);
        playerWinRate.setText(Math.round((double)(readWins(typeOfAI * 2 - 1) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");
        AIWinRate.setText(Math.round((double)(readWins(typeOfAI * 2) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");

        subtractAIWinButton.repaint();
        AIWinRate.repaint();
        AIWins.repaint();
        AICardsLeft.repaint();
    
        scorePanel.repaint();
        
    }
    //I don't know why I need the "try" but I do know that if I don't it doesn't work
    //and the resources I got this from didn't explain it. They just *had* it.
    public static int readWins(int lineNumber) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Wins.txt"))) {
            for (int i = 1; i < lineNumber; i++) {
                if (reader.readLine() == null) {
                    return -999; // Return -999 if the file has fewer lines than the target
                }
            }
            String line = reader.readLine(); // Read the target line
            if (line == null) {
                return -998; // Return -998 if the target line is missing
            }
            int temp=Integer.parseInt(line.trim());
            System.out.println(temp);
            System.out.println(temp);
            System.out.println(temp);
            System.out.println(temp);
            System.out.println(temp);
            System.out.println(temp);
            System.out.println(temp);
            return temp; // Parse and return the target line as an integer
        }
    }

    //the Smart AI is 1
    public static void winsPlusOne(int line) {
    try {
//Makes a list of the lines in the file and then writes that list on top of the old file
//It replaces a specific then by copying everything, editing what needs editing, then
//replacing everything.
        List<String> lines=Files.readAllLines(Paths.get("src/Wins.txt"));

/*  I shouldn't need this because I don't plan of having a "line" input greater than the file size, but if I do, I can remove the comment. 
        while (lines.size()<line) {
            lines.add("0"); 
        }*/

        int currentValue = Integer.parseInt(lines.get(line-1).trim());
        lines.set(line-1, String.valueOf(currentValue+1));

        Files.write(Paths.get("src/Wins.txt"), lines);
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }
    try {
        playerWins.setText(readWins(typeOfAI * 2 - 1) + "");
        AIWins.setText(readWins(typeOfAI * 2) + "");

        playerWinRate.setText(Math.round((double)(readWins(typeOfAI * 2 - 1) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");
        AIWinRate.setText(Math.round((double)(readWins(typeOfAI * 2) /(double) (readWins(typeOfAI * 2 - 1) +(double) readWins(typeOfAI * 2))) * 100) + "%");

        playerWins.repaint();
        AIWins.repaint();
    } catch (IOException e) {
        e.printStackTrace();
    }

    if(line%2==1){
        scorePanel.setBackground(Color.BLUE);
        playerWinRate.setForeground(Color.WHITE);
        playerWins.setForeground(Color.WHITE);
        playerCardsLeft.setForeground(Color.WHITE);
    }else{
        if(!App.winnerExists){
        scorePanel.setBackground(AIColor);
        AICardsLeft.setForeground(Color.WHITE);
        AIWinRate.setForeground(Color.WHITE);
        AIWins.setForeground(Color.WHITE);
        }
    }

}}
