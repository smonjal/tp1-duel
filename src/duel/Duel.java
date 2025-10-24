package duel;

import duel.exception.CapitulationOfAttackerException;
import duel.exception.CapitulationOfDefenderException;
import duel.exception.DeadDuelistException;

public class Duel {

	public static final int REWARD_POINTS = 1;
	public static final int NO_REWARD_POINTS = 0;
	public static final int PENALTY_POINTS = 1;

	public static final String ATTACKER_DEAD_EXCEPTION_MESSAGE = "The attacker is already dead!";
	public static final String DEFENDER_DEAD_EXCEPTION_MESSAGE = "The defender is already dead!";

	private Duelist attacker;
	private Duelist defender;

	public Duel(Duelist attacker, Duelist defender) {
		this.attacker = attacker;
		this.defender = defender;
		validateDuelistsAlive();
	}

	private void validateDuelistsAlive() {
		if(!this.attacker.isAlive()) {
			throw new DeadDuelistException(ATTACKER_DEAD_EXCEPTION_MESSAGE);
		}
		if(!this.defender.isAlive()) {
			throw new DeadDuelistException(DEFENDER_DEAD_EXCEPTION_MESSAGE);
		}		
	}

	public void fight(Skill prizeForWinner) {		
		try {
			resolveDuel(prizeForWinner);	
		}
		catch(CapitulationOfAttackerException ex) {
			defender.reward(REWARD_POINTS, prizeForWinner);
		}
		catch(CapitulationOfDefenderException ex) {
			attacker.reward(NO_REWARD_POINTS, prizeForWinner);		
		}			
	}

	private void resolveDuel(Skill prizeForWinner) {
		int attackEffect = this.attacker.attack();
		int defenseEffect = this.defender.counterAttack();

		Duelist winner = this.attacker;
		Duelist loser = this.defender;

		if ( attackEffect <= defenseEffect) {
			winner = this.defender;
			loser = this.attacker;
		}

		int duelEffect = Math.abs(attackEffect - defenseEffect);
		winner.reward(REWARD_POINTS, prizeForWinner);
		loser.penalize(PENALTY_POINTS, duelEffect);
	}

	private void resolveDuel2(Skill prizeForWinner) {
		int attackEffect = this.attacker.attack();
		int defenseEffect = this.defender.counterAttack();

		if (attackEffect > defenseEffect) {
			this.attacker.reward(REWARD_POINTS, prizeForWinner);
			this.defender.penalize(PENALTY_POINTS, attackEffect - defenseEffect);
		}
		else {
			this.defender.reward(REWARD_POINTS, prizeForWinner);
			this.attacker.penalize(PENALTY_POINTS, defenseEffect - attackEffect);

		}
	}
}
