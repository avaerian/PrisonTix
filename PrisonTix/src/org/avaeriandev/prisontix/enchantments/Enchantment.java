package org.avaeriandev.prisontix.enchantments;

import java.util.ArrayList;

enum ToolType {
	SWORD, PICKAXE, AXE, SHOVEL, ARMOR
}

public class Enchantment {

	private String name;
	private Enchant identifier;
	private int maxLevel;
	private ArrayList<String> lore;
	private ToolType toolType;
	
	public Enchantment(String name, Enchant identifier, int maxLevel, ArrayList<String> lore, ToolType toolType) {
		this.name = name;
		this.identifier = identifier;
		this.maxLevel = maxLevel;
		this.lore = lore;
		this.toolType = toolType;
	}
	
	public String getName() {
		return name;
	}
	
	public Enchant getIdentifier() {
		return identifier;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public ArrayList<String> getLore() {
		return lore;
	}
	
	public ToolType getToolType() {
		return toolType;
	}
	
}
