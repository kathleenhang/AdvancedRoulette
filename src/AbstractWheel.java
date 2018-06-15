import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// Class AbstractWheel represents a roulette wheel and its operations
public abstract class AbstractWheel implements Queue {
	
	// public name variables -- accessible to others
	// default values for the game types
    public int black     =  0;			// even numbers
    public int red       =  1;			// odd numbers
    public int green     =  2;			// 00 OR 0
    public static int number    =  3;			// number bet // removed static ***
    public int minNum   =  1;			// smallest number to bet // removed static ***
    public int maxNum   = 14;			// largest number to bet // removed static ***
    public int maxBet   = 10;			// largest amount to bet
    public int minBet   = 1;			// smallest number to bet
    protected int max_positions = 16;	// number of positions on wheel
    protected int number_payoff = 14;	// payoff for number bet
    protected int color_payoff  = 2;		// payoff for color bet
   
    protected String gameID = ""; // track unique game servers
    int houseBalance; // total cash of house player
    int totalHouseLoss = 0;
    int totalHouseWin = 0;
    int houseStartBalance;
    int houseEndBalance;
    int houseNetBalance;
    int roundCount = 0;
   
    protected static int ballPosition;				// 00, 0, 1 .. 14a
    protected static int color;						// GREEN, RED, OR BLACK
    
	public abstract void gameMenuOptions();
	public abstract void playerMenuOptions();
    public abstract void spin();
    public abstract String getGameID();
    public abstract int payoff(int betAmount, int betType, int betNumber);
    public int getHouseBalance() { return houseBalance; }
    public int getStartBalance() { return houseStartBalance; }
    public int getEndBalance() { return houseEndBalance; }
    public int getNetBalance() { return houseNetBalance; }
    public int getRoundCount() { return roundCount; }
    public int getBallPosition() { return ballPosition; }
    public String getColor()
    {
    	if(color == 0)
    		return "Black";
    	else if(color == 1)
    		return "Red";
    	else 
    		return "Green";
    }
    
}
