package duel;

public interface Duelist {
	
	/**
	 * Informe sur la vie ou la mort du duelliste
	 * @return true si le dueliste est en vie, false s'il est mort
	 */
	boolean isAlive();
	

	/**
	 * Met en oeuvre une puissance d'attaque lorsque le duelliste est l'attaquant
	 * @return 		la puissance d'attaque
	 * @exception 	lève une RunTimeException (CapitulationOfAttackerException) si le duelliste ne peut pas attaquer
	 */
	int attack();	
	

	/**
	 * Met en oeuvre une puissance de riposte lorsque le duelliste est le contre-attaquant
	 * @return 		la puissance de riposte
	 * @exception 	lève une RunTimeException (CapitulationOfDefenderException) si le duelliste ne peut pas riposter
	 */
	int counterAttack();
	

	/**
	 * Pour récompenser le dueliste s'il a gagné un duel 
	 * @param rewardPoints 	points boni reçus en récompense
	 * @param rewardSkill  	nouvelle capacité gagnée 
	 */
	void reward(int rewardPoints, Skill rewardSkill);
	
	
	/**
	 * Pour pénaliser le dueliste s'il a perdu un duel 
	 * @param penaltyPoints		points de pénalité reçus
	 * @param healthPointsLost	points de vie perdus
	 */
	void penalize(int penaltyPoints, int healthPointsLost);
	
}
