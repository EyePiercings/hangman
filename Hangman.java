
/**
 * Hangman
 * 
 * Main program for the Hangman Game.
 */

import java.util.Scanner;
import java.util.Stack;
import java.util.Random;
import java.io.*;


public class Hangman {

  public int guessesLeft = 8;

  /**
   * This method returns a hidden string. It is composed of '-' characters.
   * 
   * @param len
   * @return hidden
   */
  public String genHiddenString(int len) {
    String hidden = "";

    for (int i = 0; i < len; i++) {
      hidden += "-";
    }
    return hidden;
  }

  /**
   * This method modifies the hidden string to reveal a character of playWord among the series of
   * '-' characters.
   * 
   * @param playWord
   * @param guess
   * @param hidden
   */
  public String revealLetter(String playWord, String guess, String hidden) {
    int gIndex = playWord.indexOf(guess);
    int hiddenLen = hidden.length();
    int pwLen = playWord.length();
    
    Stack<Integer> indexStack = new Stack<Integer>();
    for(int i=0;i<pwLen;i++) {
      if(playWord.substring(i,i+1).equals(guess)) {
        indexStack.push(i);
      }
    }

    while(!indexStack.isEmpty()) {
      int index = indexStack.pop();
      hidden = hidden.substring(0,index) + guess + hidden.substring(index+1);
    }
    return hidden;
  }

  public void run(Scanner in) {
    HangmanLexicon HL = new HangmanLexicon();
    Random r = new Random();
    String playWord = HL.getWord(r.nextInt(10));
    String hidden = genHiddenString(playWord.length());

    System.out.println("Welcome to Hangman!");

    // Game loop
    while (true) {
      System.out.println("The word now looks like this: " + hidden);
      System.out.println("You have " + guessesLeft + " guesses left." );

      System.out.print("Your guess: ");
      String guess = in.next().toUpperCase();
      
      if(hidden.contains(guess)) {
        System.out.println("That letter has already been found, try again.");
      } else if(!playWord.contains(guess)) {
        System.out.println("There are no " + guess + "'s in the word.");
        guessesLeft--;
      } else {
        hidden = revealLetter(playWord, guess, hidden);
        System.out.println("That guess is correct.");
      }
      

      // Win condition
      if(playWord.equals(hidden)) {
        System.out.println("You guessed the word: " + playWord);
        System.out.println("You win.");
        break;
      } 
      // Lose condition
      else if(guessesLeft == 0) {
        System.out.println("You're completely hung.");
        System.out.println("The word was: " + playWord);
        System.out.println("You lose.");
        break;
      }
    }

  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    Hangman h = new Hangman();
    
    // Test Methods
    /* 
    String res = h.genHiddenString(5);
    String boi = h.revealLetter("Hello", "l", res);
    String goy = h.revealLetter("Hello", "o", boi);
    System.out.println(res);
    System.out.println(boi);
    System.out.println(goy);
    */

    h.run(in);
  }

}