package org.avaeriandev.prisontix.ticketshop;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class TicketReward {

	String name;
	int cost;
	ArrayList<String> lore;
	ItemStack icon;
	String command;
	
	public TicketReward(String name, int cost, ArrayList<String> lore, ItemStack icon, String command) {
		this.name = name;
		this.cost = cost;
		this.lore = lore;
		this.icon = icon;
		this.command = command;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public ArrayList<String> getLore() {
		return lore;
	}
	
	public ItemStack getIcon() {
		return icon;
	}
	
	public String getCommand() {
		return command;
	}
	
}
