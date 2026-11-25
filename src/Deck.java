public class Deck {
    int[] numCards={4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
    String name;
    int[] hand={-1,-1,-1,-1,-1,-1};

    public Deck(String n) {
        name=n;
        
        for(int i=0;i<6;i++) {
            int card=(int)(Math.random()*13);
            
            while(numCards[card]==0) {
                card=(int)(Math.random()*13);
            }
            hand[i]=card;
            System.out.print(card+", ");
        }
        System.out.println();
        System.out.println();
    }
    
    public int getCard(int i) {
        return hand[i];
    }

// returns true if the new card is higher than the old card
    public int flip(int i) {
        int compare=hand[i];
        hand[i]=(int)(Math.random()*13);
        while(numCards[hand[i]]==0) {
            hand[i]=(int)(Math.random()*13);
        }
        numCards[hand[i]]--;
        return compare;
    }

    public int add(){
        int temp=0;
        for(int i=0;i<13;i++) {
            temp+=numCards[i];
        }
        return temp;
    }

    public String print() {
        String s=name+" : ";

        for(int i=0;i<6;i++) {
            s+=hand[i]+", ";
        }
        return s;
    }

    public void printNumCards() {
        for(int i=0;i<13;i++) {
            System.out.print(i+1+": "+ numCards[i]+", ");
        }
        System.out.println();
    }
}
