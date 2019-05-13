package cardGame.game;

import cardGame.models.Card;

public class Turn {


    public void Turn(Card.Face chosenCardPlayer, Card.Face chosenCardDealer){
        if(chosenCardPlayer == chosenCardDealer){
            System.out.println("You've guessed the right face, I have to drink.");
        } else if(chosenCardPlayer.ordinal() < chosenCardPlayer.ordinal()){
            System.out.println("The card in my hand is higher than your guess.");
        } else{
            System.out.println("The card in my hand is lower than your guess.");
        }

        Card.Face guessFace2 = Card.Face.ACE ; //get new guess

        if(guessFace2 != chosenCardDealer){
            System.out.println("You've guesses the wrong card again, you have to drink...");
        } else{
            System.out.println("Buggers, you've guessed the right card, I have to drink...");
        }

    }


}
