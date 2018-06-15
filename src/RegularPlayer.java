import java.util.Scanner;
/*
 * [ Regular Player ]
 * - Don't care about their name and doesn't have a id code
 */
public class RegularPlayer extends AbstractPlayer {
	
	// create regular player object
	public RegularPlayer(int playerBalance)
	{
		this.playerBalance = playerBalance;
	}

	// allows the player to quit the game or play again
	// TODO: fix playAgain
    public boolean playAgain(Scanner scan)
    {
      	String answer;

      	System.out.print ("\tPlay again [y/n]? ");
      	answer = scan.next();
      	if(answer.equals("y"))
      	{
      		numberOfBets++;
      		if(playerBalance == 0)
      		{
      			playerBalance = 50;
          		System.out.println("\t$" + playerBalance + " has been added.");
      		}
      	}
      	else
      	{
      		// once player quits, their data goes to text file
      		// player is able to see their win amount when they leave the game. include cash back and bonus
      		
      	}
      		
      	return (answer.equals("y") || answer.equals("Y"));
    }  // method playAgain
	
    public String getPlayerType() { return "Regular Player"; }
    
    public String printPlayerStats()
    {
    	return ("\nTotal winnings: " + totalWinnings);
    }
    public String toString()
    {
    	return "Regular Player - Starting balance: " + playerBalance;
    }

}
