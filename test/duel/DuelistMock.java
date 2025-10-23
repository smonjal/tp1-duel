package duel;

import duel.exception.CapitulationOfAttackerException;
import duel.exception.CapitulationOfDefenderException;

public class DuelistMock implements Duelist {
	
	public boolean hasRewardMethodBeenCalled = false;
    public int rewardMethodeCallCounter; 
	public Skill rewardSkill = null;
	public int rewardPoints = 0;
	
	public boolean hasPenalizeMethodBeenCalled = false;
    public int penalizeMethodeCallCounter; 
    public int healthPointsLost = 0;
	public int penaltyPoints = 0;
	
	public boolean isAlive = true;
	
	private int effect = 0;
	private boolean hasDuelistCapitulated = false;
	
	public DuelistMock() {}
	
	public DuelistMock(int effect) {
		this.effect = effect;
	}
		
	public DuelistMock(boolean capitulation) {
		this.hasDuelistCapitulated = capitulation;
	}
	
	@Override
	public boolean isAlive() {
		return this.isAlive;
	}

	@Override
	public int attack() {
		if(this.hasDuelistCapitulated) {
			throw new CapitulationOfAttackerException();
		}
		return this.effect;
	}

	@Override
	public int counterAttack() {
		if(this.hasDuelistCapitulated) {
			throw new CapitulationOfDefenderException();
		}
		return this.effect;
	}

	@Override
	public void reward(int rewardPoints, Skill rewardSkill) {
		this.hasRewardMethodBeenCalled = true;
		this.rewardMethodeCallCounter++;
		this.rewardPoints = rewardPoints;
		this.rewardSkill = rewardSkill;

	}

	@Override
	public void penalize(int penaltyPoints, int healthPointsLost) {
		this.hasPenalizeMethodBeenCalled = true;
		this.penalizeMethodeCallCounter++;
		this.penaltyPoints = penaltyPoints;	
		this.healthPointsLost = healthPointsLost;
	}
}
