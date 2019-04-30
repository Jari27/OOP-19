package nl.rug.oop.introduction.helpers;

public class SaveLoadQuitHelper {

  public static void handleQuit() {
    System.out.println("Are you sure you want to exit?");
    String result = InputHelper.getString("Enter 'Y' to exit.");
    if (result.toLowerCase().equals("y")) {
      System.out.println("Thank you for playing. Bye bye...");
      System.exit(69);
    } else {
      System.out.println("One. More. Turn.");
    }
  }

}
