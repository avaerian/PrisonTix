package org.avaeriandev.prisontix.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestMaster {

	public static ArrayList<Quest> quests = new ArrayList<>();
	public static HashMap<UUID, ArrayList<Quest>> activeQuests = new HashMap<>();
	public static HashMap<UUID, ArrayList<CompletedQuest>> completedQuests = new HashMap<>();
	
	enum QuestStatus {
		INACTIVE, ACTIVE, COMPLETED
	}
	
	public static void defineQuests() {
		quests = new ArrayList<>(Arrays.asList(
			// E ward
			new Quest("Black Lung", "BlackLung", 1000, QuestUtils.requirements(new ItemStack(Material.IRON_PICKAXE, 1)), QuestUtils.lore("Hey friend,", "I need an iron pickaxe for a top secret project!", "Do me a solid and grab me one?")),
			new Quest("David Leatherman", "DavidLeatherman", 1500, QuestUtils.requirements(new ItemStack(Material.LEATHER_HELMET, 1), new ItemStack(Material.LEATHER_CHESTPLATE, 1), new ItemStack(Material.LEATHER_LEGGINGS, 1), new ItemStack(Material.LEATHER_BOOTS, 1)), QuestUtils.lore("Jay-walking Leno took my chestplate.", "Can you get me another?")),
			new Quest("Voodoo", "Voodoo", 2500, QuestUtils.requirements(new ItemStack(Material.SKULL_ITEM, 1, (byte) 3)), QuestUtils.lore("The guards took my last one,", "can you get me another head?")),
			new Quest("Tim Ber Land", "TimBerLand", 1000, QuestUtils.requirements(new ItemStack(Material.IRON_AXE, 1)), QuestUtils.lore("Let me axe you a question.", "Can you get me an axe?")),
			
			// D ward
			new Quest("Conductor", "Conductor", 5000, QuestUtils.requirements(new ItemStack(Material.RAILS, 32)), QuestUtils.lore("I need some help to finish my track.", "Can you get me some rails?")),
			new Quest("Mia Stoner", "MiaStoner", 4000, QuestUtils.requirements(new ItemStack(Material.LEAVES, 64)), QuestUtils.lore("Bring me a stack of dem leeaaaves yo...")),
			new Quest("Stanky James", "StankyJames", 7500, QuestUtils.requirements(new ItemStack(Material.RAW_FISH, 20)), QuestUtils.lore("Pert is hungry.", "Can you get him some fish?")),
			
			// C ward
			new Quest("Chef", "Chef", 11250, QuestUtils.requirements(new ItemStack(Material.RAW_BEEF, 20)), QuestUtils.lore("We are going to make you an omelette!", "Bring me 20 steak")),
			new Quest("Boy", "Boy", 11250, QuestUtils.requirements(new ItemStack(Material.PORK, 20)), QuestUtils.lore("We are going to make you an omelette!", "Bring me 20 porkchops!")),
			new Quest("Are", "Are", 11250, QuestUtils.requirements(new ItemStack(Material.MILK_BUCKET, 1)), QuestUtils.lore("We are going to make you an omelette!", "Bring me 1 milk bucket!")),
			new Quest("Dee", "Dee", 11250, QuestUtils.requirements(new ItemStack(Material.EGG, 10)), QuestUtils.lore("We are going to make you an omelette!", "Bring me 10 eggs!")),
			new Quest("Wile E", "WileE", 20000, QuestUtils.requirements(new ItemStack(Material.ANVIL, 1)), QuestUtils.lore("Dude I got a wicked headache...", "can you get me an anvil?")),
			new Quest("Kurt Throat", "KurtThroat", 40000, QuestUtils.requirements(new ItemStack(Material.SKULL_ITEM, 5, (byte) 3)), QuestUtils.lore()),
			new Quest("Johny ApplePoop", "JohnyApplePoop", 20000, QuestUtils.requirements(new ItemStack(Material.GOLDEN_APPLE, 5)), QuestUtils.lore("I need to make poopie and I need 5 gold apples...", "PLZ help!")),
			
			// B ward
			new Quest("Designer Dick", "DesignerDick", 140000, QuestUtils.requirements(new ItemStack(Material.LAPIS_BLOCK, 11)), QuestUtils.lore("Enter this hole to get to the mine!", "I need some lapis blocks to make this place fine!", "I can't pay a lot, but I can offer other", "compensation...")), 
			new Quest("Dirty Dan", "DirtyDan", 140000, QuestUtils.requirements(new ItemStack(Material.WATCH, 25)), QuestUtils.lore("I've been reselling clocks to people", "but i'm running low on stock", "Can you get me some more?")),
			new Quest("DJ Minor", "DjMinor", 14000, QuestUtils.requirements(new ItemStack(Material.JUKEBOX, 32)), QuestUtils.lore("We've got some tunes but no speakers", "Get me something to blast these tunes", "with.")),
			new Quest("Francis", "Francis", 60000, QuestUtils.requirements(new ItemStack(Material.LEATHER_CHESTPLATE, 1)), QuestUtils.lore("We are planning a breakout!", "We will need some disguises.", "Bring us a leather", "chest plate.")),
			new Quest("Benry", "Benry", 60000, QuestUtils.requirements(new ItemStack(Material.IRON_SWORD, 1)), QuestUtils.lore("We are almost ready to break", "out of this joint,", "but this wood sword won't do.", "Bring us an iron sword!")),
			new Quest("Gustov", "Gustov", 60000, QuestUtils.requirements(new ItemStack(Material.DIODE, 34)), QuestUtils.lore("We are so close!", "All we need is 34 redstone", "repeaters to setup our TNT!")),
			
			// A ward
			new Quest("Miner", "Miner", 300000, QuestUtils.requirements(new ItemStack(Material.DIAMOND_PICKAXE, 1), new ItemStack(Material.RAILS, 10), new ItemStack(Material.MINECART, 1)), QuestUtils.lore("Hey you! I'm trying to get out", "of this stinking prison and I need", "your help! Can you", "get me 1 Diamond pick", "10 Rails", "and 1 Minecart?")),
			new Quest("Dood", "Dood", 300000, QuestUtils.requirements(new ItemStack(Material.EMERALD_ORE, 30)), QuestUtils.lore("Hey Dood,", "I need 30 emerald ORE to buy my", "friend's auction house chest.", "Can you help me out?"))
			//new Quest("Tim Ber Land", "TimBerLand", 1000, QuestUtils.requirements(new ItemStack(Material.IRON_AXE, 1)), QuestUtils.lore("Let me axe you a question.", "Can you get me an axe?"))
		));
	}
	
	public static Quest getQuest(String questName) {
		for(Quest quest : quests) {
			if(quest.getName().equalsIgnoreCase(questName)) {
				return quest;
			}
		}
		return null;
	}
	
	public static Quest getQuestByIdentifier(String identifier) {
		for(Quest quest : quests) {
			if(quest.getIdentifier().equalsIgnoreCase(identifier)) {
				return quest;
			}
		}
		return null;
	}
	
	public static void assignQuest(Player plr, Quest quest) {
		
		ArrayList<Quest> newActiveQuests = new ArrayList<>();
		
		if(activeQuests.containsKey(plr.getUniqueId())) {
			newActiveQuests = activeQuests.get(plr.getUniqueId());
		}
		
		newActiveQuests.add(quest);
		activeQuests.put(plr.getUniqueId(), newActiveQuests);
		
		for(Object requirement : quest.getRequirements()) {
			
			if(requirement instanceof ItemStack) {
				ItemStack item = (ItemStack) requirement;
				String itemQuantity = String.valueOf(item.getAmount());
				String itemName = item.getType().name();
				itemName = itemName.replaceAll("_", " ");
				itemName = itemName.toLowerCase();
				
				plr.sendMessage(Utils.chat("&2Bring me " + itemQuantity + " " + itemName + "."));
			}
		}
		
		plr.playSound(plr.getLocation(), Sound.HORSE_ARMOR, 1, 1);
	}
	
	public static boolean meetsRequirements(Player plr, Quest quest) {
		for(Object requirement : quest.getRequirements()) {
			if(requirement instanceof ItemStack) {
				ItemStack item = (ItemStack) requirement;
				
				int checkAmount = 0;
				for(int i = 0; i < plr.getInventory().getSize(); i++) {
					try{
						if(plr.getInventory().getItem(i).isSimilar(item)) {
							checkAmount += plr.getInventory().getItem(i).getAmount();
						}
					} catch (Exception e) {						
					}
				}
				
				if(!(checkAmount >= item.getAmount())) {
					return false;
				}
				
			}
		}
		return true;
	}
	
	public static void rewardPlayer(Player plr, Quest quest) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + plr.getName() + " " + quest.getMoneyReward());
	}
	
	public static void markQuestAsComplete(Player plr, Quest quest) {
		try {
			ArrayList<Quest> newActiveQuests = activeQuests.get(plr.getUniqueId());
			ArrayList<CompletedQuest> newCompletedQuests = new ArrayList<>();
			
			if(completedQuests.containsKey(plr.getUniqueId())) {
				newCompletedQuests = completedQuests.get(plr.getUniqueId());
			}
			
			Calendar nextDay = Calendar.getInstance();
			nextDay.add(Calendar.DAY_OF_MONTH, 1);
			
			newActiveQuests.remove(quest);
			newCompletedQuests.add(new CompletedQuest(quest, nextDay.getTimeInMillis()));
			
			activeQuests.put(plr.getUniqueId(), newActiveQuests);
			completedQuests.put(plr.getUniqueId(), newCompletedQuests);
			
		} catch (Exception e) {
		}
	}
	
	public static QuestStatus getQuestStatus(Player plr, Quest quest) {
		if(activeQuests.containsKey(plr.getUniqueId())) {
			ArrayList<Quest> tempActiveQuests = activeQuests.get(plr.getUniqueId());
			if(tempActiveQuests.contains(quest)) {
				return QuestStatus.ACTIVE;
			}
		}
		
		if(completedQuests.containsKey(plr.getUniqueId())) {
			for(CompletedQuest completedQuest : completedQuests.get(plr.getUniqueId())) {
				if(completedQuest.getQuest().equals(quest)) {
					return QuestStatus.COMPLETED;
				}
			}
		}
		
		return QuestStatus.INACTIVE;
	}
	
	public static void removeCooldown(Player plr, Quest quest) {
		for(CompletedQuest completedQuest : completedQuests.get(plr.getUniqueId())) {
			if(completedQuest.getQuest().equals(quest)) {
				ArrayList<CompletedQuest> newCompletedQuests = completedQuests.get(plr.getUniqueId());
				newCompletedQuests.remove(completedQuest);
				if(newCompletedQuests.isEmpty()) {
					completedQuests.remove(plr.getUniqueId());
				} else {
					completedQuests.put(plr.getUniqueId(), newCompletedQuests);
				}
			}
		}
	}
	
	public static boolean isCooldownOver(Player plr, Quest quest) {
		for(CompletedQuest completedQuest : completedQuests.get(plr.getUniqueId())) {
			if(completedQuest.getQuest().equals(quest)) {
				if(System.currentTimeMillis() >= completedQuest.getCooldownOffTimestamp()) {
					return true;
				} else {
					continue;
				}
			}
		}
		return false;
	}
	
	public static void removeItem(Player plr, ItemStack item) {
		
		int quantity = item.getAmount();
		
		for(int i = 0; i < plr.getInventory().getSize(); i++) {
			
			ItemStack slotItem = plr.getInventory().getItem(i);
			
			if(slotItem != null) {
				
				if(slotItem.isSimilar(item)) {
										
					if(slotItem.getAmount() > quantity) {
						// subtract amount
						slotItem.setAmount(slotItem.getAmount() - quantity);
						plr.updateInventory();
						break;
					}else if(slotItem.getAmount() == quantity) {
						// set to air
						plr.getInventory().setItem(i, new ItemStack(Material.AIR));
						plr.updateInventory();
						break;
					} else {
						// subtract quantity var, set item to air
						quantity -= slotItem.getAmount();
						plr.getInventory().setItem(i, new ItemStack(Material.AIR));
						plr.updateInventory();
					}
				}
			}
		}
	}
	
	public static long getTimestamp(Player plr, Quest quest) {
		try {
			ArrayList<CompletedQuest> plrCompletedQuests = completedQuests.get(plr.getUniqueId());
			
			for(CompletedQuest completedQuest : plrCompletedQuests) {
				if(completedQuest.getQuest().equals(quest)) {
					return completedQuest.getCooldownOffTimestamp();
				}
			}
			
			return -1;
			
		} catch (Exception e) {
			System.out.println("An error occurred when getting the quest timestamp");
			return -1;
		}
	}
}
