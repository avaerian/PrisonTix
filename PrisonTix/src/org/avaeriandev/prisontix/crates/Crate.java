package org.avaeriandev.prisontix.crates;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class Crate {

	private String displayName;
	private String identifier;
	private ItemStack crateItem;
	private ArrayList<Reward> rewards;
	
	public Crate(String displayName, String identifier, ItemStack crateItem, ArrayList<Reward> rewards) {
		this.displayName = displayName;
		this.identifier = identifier;
		this.crateItem = crateItem;
		this.rewards = rewards;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ItemStack getCrateItem() {
		return crateItem;
	}
	
	public ArrayList<Reward> getRewards() {
		return rewards;
	}
	
}
