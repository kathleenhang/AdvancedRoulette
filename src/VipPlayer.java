import java.util.Scanner;
/*
 * [ Vip Player ]
 * - Contains 4 digit unique identifier
 * - Tracks first & last name
 * - Awarded 5% cashback bonus on all bets when vip player leaves the game
 */
public class VipPlayer extends RegularPlayer {
	int vipID; // 4 digit id
	String firstName;
	String lastName;
	double CASH_BACK_PERCENTAGE = 0.05;
	protected int cashBackAmount = 0;		// for vip and super vip players
	 
	// Create vip player object
	public VipPlayer(int playerBalance, int vipID, String firstName, String lastName)
	{
		super(playerBalance);
		this.vipID = vipID;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	// TODO: Calculate vip cash back
	public void calculateVipCashBack()
	{
		
	}
	public String getName()
	{
		return firstName + " " + lastName;
	}
	public int getVipID()
	{
		return vipID;
	}
	
    public boolean playAgain(Scanner scan)
    {
      	String answer;

      	System.out.print (getName() + "\tPlay again [y/n]? ");
      	answer = scan.next();
      	if(answer.equals("y"))
      	{
      		numberOfBets++;
      		if(playerBalance == 0)
      		{
      			playerBalance = 50;
          		System.out.println(getName() + "\t$" + playerBalance + " has been added.");
      		}
      	}
      	else
      	{
      		System.out.println(getName() + "\tAfter " + numberOfBets + " bet(s), " + getName() 
				+ " has won $" + totalWinnings + ".");
      	}
      		
      	return (answer.equals("y") || answer.equals("Y"));
    }  // method playAgain
	

    public String getPlayerType() { return "VIP Player"; }
    
    public String toString()
    {
    	return "Vip Player - Starting balance: " + playerBalance + " - " + getName() + " - Vip ID: " + getVipID();
    }
}
