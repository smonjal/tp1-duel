package duel;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import duel.exception.DeadDuelistException;

class DuelTest1 {

	/* Duel Constructor Tests */
	/* ====================== */
	@Test
	void whenCreatingDuel_ifDuelistsAreAlive_thenDuelIsCreated() {
		Assertions.assertDoesNotThrow(() -> new Duel(new DuelistMock(), new DuelistMock())); 
	}
	
	private static Stream<Arguments> provideDeadDuelists_toValidateDuel() {
		DuelistMock deadDuelist = new DuelistMock();  // J'utilise le même mock. Donc tests dépendants mais Stub
		deadDuelist.isAlive = false;
		
		return Stream.of(
						Arguments.of(deadDuelist, new DuelistMock(), Duel.ATTACKER_DEAD_EXCEPTION_MESSAGE),
						Arguments.of(new DuelistMock(), deadDuelist, Duel.DEFENDER_DEAD_EXCEPTION_MESSAGE),
						Arguments.of(deadDuelist, deadDuelist, Duel.ATTACKER_DEAD_EXCEPTION_MESSAGE)
						);
	}		
	@ParameterizedTest
	@MethodSource("provideDeadDuelists_toValidateDuel")
	void whenCreatingDuel_ifAtLeastOneDuelistIsDead_thenDeadDuelistExceptionIsThrown(Duelist attacker, Duelist defender, String exceptionMessage) {
		DeadDuelistException ex = Assertions.assertThrows(DeadDuelistException.class, () -> new Duel(attacker, defender));
		Assertions.assertEquals(exceptionMessage, ex.getMessage());
	}
	
	/* Fight Method Test */
	/* ================= */
	@Test
	void whenFightingDuel_ifAttackerIsStronger_thenAttackerIsWinner() {
		DuelistMock attacker = new DuelistMock(1000);
		DuelistMock defender = new DuelistMock(200);	
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(attacker.hasRewardMethodBeenCalled);
		Assertions.assertEquals(1,  attacker.rewardMethodeCallCounter);
		Assertions.assertEquals(Duel.REWARD_POINTS, attacker.rewardPoints);
		Assertions.assertSame(prizeSkill, attacker.rewardSkill);
	
		Assertions.assertFalse(attacker.hasPenalizeMethodBeenCalled);
	}
	
	@Test
	void whenFightingDuel_ifAttackerIsStronger_thenDefenderIsLoser() {
		DuelistMock attacker = new DuelistMock(1000);
		DuelistMock defender = new DuelistMock(200);	
		int duelEffect = 1000 - 200;
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(defender.hasPenalizeMethodBeenCalled);
		Assertions.assertEquals(1,  defender.penalizeMethodeCallCounter);
		Assertions.assertEquals(Duel.PENALTY_POINTS, defender.penaltyPoints);
		Assertions.assertEquals(duelEffect, defender.healthPointsLost);	
		
		Assertions.assertFalse(defender.hasRewardMethodBeenCalled);
	}

	@Test
	void whenFightingDuel_ifDefenderIsStronger_thenDefenderIsWinner() {
		DuelistMock attacker = new DuelistMock(300);
		DuelistMock defender = new DuelistMock(1550);	
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(defender.hasRewardMethodBeenCalled);
		Assertions.assertEquals(1,  defender.rewardMethodeCallCounter);
		Assertions.assertEquals(Duel.REWARD_POINTS, defender.rewardPoints);
		Assertions.assertSame(prizeSkill, defender.rewardSkill);
		
		Assertions.assertFalse(defender.hasPenalizeMethodBeenCalled);
	}
	
	@Test
	void whenFightingDuel_ifDefenderIsStronger_thenAttackerIsLoser() {
		DuelistMock attacker = new DuelistMock(300);
		DuelistMock defender = new DuelistMock(1550);	
		int duelEffect = 1550 - 300;
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(attacker.hasPenalizeMethodBeenCalled);
		Assertions.assertEquals(1,  attacker.penalizeMethodeCallCounter);
		Assertions.assertEquals(Duel.PENALTY_POINTS, attacker.penaltyPoints);
		Assertions.assertEquals(duelEffect, attacker.healthPointsLost);	
		
		Assertions.assertFalse(attacker.hasRewardMethodBeenCalled);		
	}
	
	@Test
	void whenFightingDuel_ifDefenderIsSameStrongThanAttacker_thenDefenderIsWinner() {
		DuelistMock attacker = new DuelistMock(888);
		DuelistMock defender = new DuelistMock(888);
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock(); 
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(defender.hasRewardMethodBeenCalled);
		Assertions.assertEquals(1,  defender.rewardMethodeCallCounter);
		Assertions.assertEquals(Duel.REWARD_POINTS, defender.rewardPoints);
		Assertions.assertSame(prizeSkill, defender.rewardSkill);
		
		Assertions.assertFalse(defender.hasPenalizeMethodBeenCalled);		
	}
	
	@Test
	void whenFightingDuel_ifDefenderIsSameStrongThanAttacker_thenAttackerIsLoser() {
		DuelistMock attacker = new DuelistMock(888);
		DuelistMock defender = new DuelistMock(888);
		int duelEffect = 888 - 888;
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock(); 
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(attacker.hasPenalizeMethodBeenCalled);
		Assertions.assertEquals(1,  attacker.penalizeMethodeCallCounter);
		Assertions.assertEquals(Duel.PENALTY_POINTS, attacker.penaltyPoints);
		Assertions.assertEquals(duelEffect, attacker.healthPointsLost);	
		
		Assertions.assertFalse(attacker.hasRewardMethodBeenCalled);			
	}	
	
	private static Stream<Arguments> provideDuelist_toMakeAttackerCapitulate() {
		return Stream.of(
				Arguments.of(new DuelistMock(true), new DuelistMock()),
				Arguments.of(new DuelistMock(true), new DuelistMock(true))
						);		
	}
	
	@ParameterizedTest
	@MethodSource("provideDuelist_toMakeAttackerCapitulate")
	void whenFightingDuel_ifAttackerCapitulates_thenDefenderReceivesRewards(DuelistMock attacker, DuelistMock defender) {
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(defender.hasRewardMethodBeenCalled);
		Assertions.assertEquals(1,  defender.rewardMethodeCallCounter);
		Assertions.assertEquals(Duel.REWARD_POINTS, defender.rewardPoints);
		Assertions.assertSame(prizeSkill, defender.rewardSkill);	
		
		Assertions.assertFalse(defender.hasPenalizeMethodBeenCalled);	
	}
	
	@ParameterizedTest
	@MethodSource("provideDuelist_toMakeAttackerCapitulate")
	void whenFightingDuel_ifAttackerCapitulates_thenAttackerIsNotPenalized(DuelistMock attacker, DuelistMock defender) {
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertFalse(attacker.hasPenalizeMethodBeenCalled);	
		Assertions.assertFalse(attacker.hasRewardMethodBeenCalled);			
	}
	
	@Test
	void whenFightingDuel_ifDefenderCapitulates_thenAttackerReceivesOnlyTheRewardSkill() {
		DuelistMock attacker = new DuelistMock();
		DuelistMock defender = new DuelistMock(true);
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertTrue(attacker.hasRewardMethodBeenCalled);
		Assertions.assertEquals(1,  attacker.rewardMethodeCallCounter);
		Assertions.assertEquals(Duel.NO_REWARD_POINTS, attacker.rewardPoints);
		Assertions.assertSame(prizeSkill, attacker.rewardSkill);	
		
		Assertions.assertFalse(attacker.hasPenalizeMethodBeenCalled);
	}
	
	@Test
	void whenFightingDuel_ifDefenderCapitulates_thenDefenderIsNotPenalized() {
		DuelistMock attacker = new DuelistMock();
		DuelistMock defender = new DuelistMock(true);
		Duel duel = new Duel(attacker, defender);
		Skill prizeSkill = new SkillMock();
		
		duel.fight(prizeSkill); 
		
		Assertions.assertFalse(defender.hasPenalizeMethodBeenCalled);	
		Assertions.assertFalse(defender.hasRewardMethodBeenCalled);	
	}

}
