package textfighter.casino;

import textfighter.main.Random;
import textfighter.main.Ui;
import textfighter.player.Achievements;
import textfighter.player.Coins;
import textfighter.player.Stats;

public class LotteryGame extends BasicCasinoGame{
	
	private static final double LOW_PRIZE_CHANCE = 0.25, MID_PRIZE_CHANCE = 0.1, HIGH_PRIZE_CHANCE = 0.05, JACKPOT_CHANCE = 0.001;
	private static final int LOW_PRIZE_MAX = 100, MID_PRIZE_MAX = 50, HIGH_PRIZE_MAX = 15, JACKPOT_MAX = 1;
	private static final int LOW_PRIZE_COINS = 20, MID_PRIZE_COINS = 50, HIGH_PRIZE_COINS = 100, JACKPOT_COINS = 1000;
	private int ticketsBought;
	private boolean unlockUnnaturalLuck;

	public LotteryGame() {
		super("------------------------------------------------------------------\n" +
                "                                Lottery                            \n" +
                "------------------------------------------------------------------", 
                "You will be able to buy lottery tickets.\n" + 
                "Each lottery tickets costs 10 coins ans increases\n" + 
                "your chance to win a prize. Each lottery is drawn\n" + 
                "the moment you pick the according option in the\n" + 
                "menu. Good luck!", 
                "1) Buy a tickets\n" + 
                "2) Draw lottery results\n" + 
                "3) Back to the casino menu",
                GameType.LOTTO);
		ticketsBought = 0;
		unlockUnnaturalLuck = false;
	}

	@Override
	public int play(int selection) {
		switch (selection) {
		case 1:
			buyTicket();
			return -1;
		case 2:
			return drawResult();
		default:
			return -1;
		}
	}
	
	private int drawResult() {
		int jackpotWon = 0;
		int highPrizeWon = 0;
		int midPrizeWon = 0;
		int lowPrizeWon = 0;
		
		for(int ticketNumber = 0; ticketNumber < ticketsBought; ticketNumber++) {
			if (Random.RInt(100) / 100D <= LOW_PRIZE_CHANCE && lowPrizeWon < LOW_PRIZE_MAX) {
				lowPrizeWon++;
			} else if (Random.RInt(100) / 100D <= MID_PRIZE_CHANCE && midPrizeWon < MID_PRIZE_MAX) {
				midPrizeWon++;
			} else if (Random.RInt(100) / 100D <= HIGH_PRIZE_CHANCE && highPrizeWon < HIGH_PRIZE_MAX) {
				highPrizeWon++;
			} else if (Random.RInt(1000) / 1000D <= JACKPOT_CHANCE && jackpotWon < JACKPOT_MAX) {
				jackpotWon++;
				unlockUnnaturalLuck = true;
			}
		}
		
		int coinsWon = jackpotWon * JACKPOT_COINS + 
					highPrizeWon * HIGH_PRIZE_COINS +
					midPrizeWon * MID_PRIZE_COINS + 
					lowPrizeWon * LOW_PRIZE_COINS;
		
		ticketsBought = 0;
		
		if(coinsWon > 0) {
			Ui.println("Congratulations!");
			Ui.println("Your tickets got the following results:");
			Ui.print((jackpotWon > 0 ? "Jackpot: " + jackpotWon + "x\n" : "") + 
					(highPrizeWon > 0 ? "High Prize: " + highPrizeWon + "x\n" : "") + 
					(midPrizeWon > 0 ? "Medium Prize: " + midPrizeWon + "x\n" : "") + 
					(lowPrizeWon > 0 ? "Low Prize: " + lowPrizeWon + "x\n" : ""));
			Ui.println("This rewards you with a total of " + coinsWon + " coins!");
			Achievements.check();
		} else {
			Ui.println("Sadly you won nothing this time.");
			Ui.println("Better luck next time!");
		}
		
		return coinsWon;
	}
	
	private void buyTicket() {
		Ui.cls();
		
		if (Coins.get() >= 10) {
			Coins.set(-10, true);
			ticketsBought++;
			Stats.lotteryTicketsBought++;
			Ui.println("Thank you for buying a ticket! Good luck winning the jackpot!");
		} else {
			Ui.println("You are not capable to pay for a ticket. Please come back one you have enough coins.");
		}
	}
	
	public int getTicketsBought() {
		return ticketsBought;
	}
	
	public void setTicketsBought(int tickets) {
		ticketsBought = tickets;
	}

	@Override
	protected int getExitEntry() {
		return 3;
	}
	
	public boolean getJackpotWon() {
		return unlockUnnaturalLuck;
	}

}
