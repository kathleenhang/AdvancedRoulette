/*
 * [ Super Vip Player]
 * - Gains same cashback bonus as a generic vip player but with an added bonus dictated by their number of bets.
 * - Super Vip Bonus Amounts:
 * - $10 for at least 5 bets and up to 10 bets
 * - $25 for at least 11 bets and up to 20 bets
 * - $50 for more than 20 bets
 * 
 */
public class SuperVipPlayer extends VipPlayer {

	int superBonusAmount;
	// Create a super vip player object
	public SuperVipPlayer(int playerBalance, int playerID, String firstName, String lastName) {
		super(playerBalance, playerID, firstName, lastName);
	}
	
	// TODO: calculate super vip cash back
	private void calculateSuperVipBonus()
	{
		if(numberOfBets >= 5 && numberOfBets <= 10)
		{
			superBonusAmount = 10;
		}
		else if(numberOfBets >= 11 && numberOfBets <= 20)
		{
			superBonusAmount = 25;
		}
		else if(numberOfBets > 20)
		{
			superBonusAmount = 50;
		}
	}
	
	public String getPlayerType() { return "Super Vip Player"; }
	public String toString()
    {
    	return "Super Vip Player - Starting balance: " + playerBalance + " - " 
    				+ getName() + " - Vip ID: " + getVipID();
    }
	

}
