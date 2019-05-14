package cardGame.game;

import java.util.Observable;

public class Turn extends Observable {

    private boolean hasSelectedFirstCard = false;
    private String statusString = "Loading...";

    public String getStatusString() {
        return statusString;
    }


//    public Turn() {
//
//    }


//    public void Turn(Card.Face chosenCardPlayer, Card.Face chosenCardDealer){
//        if(chosenCardPlayer == chosenCardDealer){
//            System.out.println("You've guessed the right face");
//        } else if(chosenCardPlayer.ordinal() < chosenCardPlayer.ordinal()){
//            System.out.println("The card in my hand is higher than your guess.");
//        } else{
//            System.out.println("The card in my hand is lower than your guess.");
//        }
//
//        Card.Face guessFace2 = Card.Face.ACE ; //get new guess
//
//        if(guessFace2 != chosenCardDealer){
//            System.out.println("You've guesses the wrong face again, you have to drink...");
//            numWrongGuesses++;
//            if(numWrongGuesses==3){
//                System.out.println("Three persons have guessed wrong, so you can give the cards to the person next to you");
//                // TODO: person next to you has to be given
//                numWrongGuesses = 0;
//            }
//        } else{
//            System.out.println("Buggers, you've guessed the right face, I have to drink...");
//        }
//    }


}
