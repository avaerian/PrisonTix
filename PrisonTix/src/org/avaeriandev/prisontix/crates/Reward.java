package org.avaeriandev.prisontix.crates;

enum Rarity {
	COMMON, UNCOMMON, RARE, LEGENDARY, MYTHICAL
}

public class Reward {

	private String displayName;
	private Rarity rarityLevel;
	private float rarityPercent;
	private Object reward; // itemstack or string
	
	public Reward(String displayName, Rarity rarityLevel, float rarityPercent, Object reward) {
		this.displayName = displayName;
		this.rarityLevel = rarityLevel;
		this.rarityPercent = rarityPercent;
		this.reward = reward;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public Rarity getRarityLevel() {
		return rarityLevel;
	}
	
	public float getRarityPercent() {
		return rarityPercent;
	}
	
	public Object getReward() {
		return reward;
	}
	
}
