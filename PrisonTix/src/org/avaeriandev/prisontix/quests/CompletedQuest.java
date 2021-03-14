package org.avaeriandev.prisontix.quests;

public class CompletedQuest {

	private Quest quest;
	private long cooldownOffTimestamp;
	
	public CompletedQuest(Quest quest, long cooldownOffTimestamp) {
		this.quest = quest;
		this.cooldownOffTimestamp = cooldownOffTimestamp;
	}
	
	public Quest getQuest() {
		return quest;
	}
	
	public long getCooldownOffTimestamp() {
		return cooldownOffTimestamp;
	}
	
}