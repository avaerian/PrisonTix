package org.avaeriandev.prisontix.quests;

import java.util.ArrayList;

public class Quest {

	private String name;
	private String identifier;
	private int moneyReward;
	private ArrayList<Object> requirements;
	private ArrayList<String> lore;
	
	public Quest(String name, String identifier, int moneyReward, ArrayList<Object> requirements, ArrayList<String> lore) {
		this.name = name;
		this.identifier = identifier;
		this.moneyReward = moneyReward;
		this.requirements = requirements;
		this.lore = lore;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public int getMoneyReward() {
		return moneyReward;
	}
	
	public ArrayList<Object> getRequirements(){
		return requirements;
	}
	
	public ArrayList<String> getLore(){
		return lore;
	}
	
}
