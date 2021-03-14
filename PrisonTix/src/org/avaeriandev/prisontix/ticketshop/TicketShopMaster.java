package org.avaeriandev.prisontix.ticketshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.ConfigManager;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TicketShopMaster {

	private static long nextDayTimestamp;
	private static ArrayList<TicketReward> ticketRewards = new ArrayList<>();
	public static ArrayList<TicketReward> chosenRewards = new ArrayList<>(Arrays.asList(null, null, null));
	
	private static HashMap<UUID, Integer> slotOnePurchases = new HashMap<>();
	private static HashMap<UUID, Integer> slotTwoPurchases = new HashMap<>();
	private static HashMap<UUID, Integer> slotThreePurchases = new HashMap<>();
	
	public static void defineRewards() {
		ticketRewards = new ArrayList<>(Arrays.asList(
			new TicketReward("&bBountiful Pickaxe", 50000, TicketShopUtils.lore("&7Drops ores from other ores nearby!"), new ItemStack(Material.DIAMOND_PICKAXE, 1), "wpick give %player% BPick"),
			new TicketReward("&bSmelter's Pickaxe", 10000, TicketShopUtils.lore("&7Smelt your ores as you mine!"), new ItemStack(Material.DIAMOND_PICKAXE, 1), "wpick give %player% SPick"),
			new TicketReward("&bExplosive Pickaxe", 30000, TicketShopUtils.lore("&7Mines a 3x3 radius!"), new ItemStack(Material.DIAMOND_PICKAXE, 1), "wpick give %player% EPick"),
			new TicketReward("&bC1 Ticket", 100, TicketShopUtils.lore("&7Receive temporary access to the C1 Shop"), new ItemStack(Material.PAPER, 1), "commissary %player% c1"),
			new TicketReward("&bC2 Ticket", 200, TicketShopUtils.lore("&7Receive temporary access to the C2 Shop"), new ItemStack(Material.PAPER, 1), "commissary %player% c2"),
			new TicketReward("&bC3 Ticket", 350, TicketShopUtils.lore("&7Receive temporary access to the C3 Shop"), new ItemStack(Material.PAPER, 1), "commissary %player% c3"),
			new TicketReward("&a$100,000", 100, TicketShopUtils.lore(), new ItemStack(Material.EMERALD, 1), "eco give %player% 100000"),
			new TicketReward("&a$300,000", 300, TicketShopUtils.lore(), new ItemStack(Material.EMERALD, 1), "eco give %player% 300000"),
			new TicketReward("&a$500,000", 500, TicketShopUtils.lore(), new ItemStack(Material.EMERALD, 1), "eco give %player% 500000")
		));
	}
	
	public static void ticketResetTimer() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(System.currentTimeMillis() > nextDayTimestamp) {
					TicketShopMaster.selectDailyOptions();
				}
			}
			
		}.runTaskTimer(Main.PLUGIN, 0, 1 * 20);
	}
	
	public static TicketReward getTicketReward(String rewardName) {
		for(TicketReward reward : ticketRewards) {
			if(reward.getName().equalsIgnoreCase(rewardName)) {
				return reward;
			}
		}
		return null;
	}

	public static TicketReward getChosenTicketReward(int slot) {
		try {
			return chosenRewards.get(slot - 1);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static boolean hasEnoughTickets(Player plr, TicketReward ticketReward) {
		ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
		FileConfiguration f = cm.getConfig();
		
		int tickets = f.getInt("tickets");
		
		if(tickets >= ticketReward.getCost()) {
			return true;
		} else {
			return false;
		}
	}
	
	// Work on this
	public static void purchaseItem(Player plr, TicketReward ticketReward, int slot) {
		
		ConfigManager cm = new ConfigManager(Main.PLUGIN, plr);
		FileConfiguration f = cm.getConfig();
		int currentTickets = f.getInt("tickets");
		
		if(!(TicketShopMaster.hasEnoughTickets(plr, ticketReward))) {
			TicketShopUtils.error(plr, Error.NOT_ENOUGH_TICKETS);
			return;
		}
		
		if(TicketShopMaster.slotPurchases(slot).containsKey(plr.getUniqueId())) {
			if(TicketShopMaster.slotPurchases(slot).get(plr.getUniqueId()) >= 3) {
				TicketShopUtils.error(plr, Error.OUT_OF_STOCK);
				return;
			} else {
				TicketShopMaster.slotPurchases(slot).put(plr.getUniqueId(), TicketShopMaster.slotPurchases(slot).get(plr.getUniqueId()) + 1);
			}
		} else {
			TicketShopMaster.slotPurchases(slot).put(plr.getUniqueId(), 1);
		}
		
		f.set("tickets", currentTickets - ticketReward.getCost());
		cm.saveConfig();
		
		String command = ticketReward.getCommand();
		command = command.replaceAll("%player%", plr.getName());
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		
		plr.sendMessage(Utils.chat("&aPurchase successful!"));
		plr.playSound(plr.getLocation(), Sound.ORB_PICKUP, 1, 1);
		
	}
	
	private static HashMap<UUID, Integer> slotPurchases(int slot) {
		if(slot == 1) {
			return slotOnePurchases;
		} else if(slot == 2) {
			return slotTwoPurchases;
		} else if(slot == 3) {
			return slotThreePurchases;
		}
		
		return null;
	}
	
	public static ItemStack getIcon(Player plr, int slot) {
		if(slotPurchases(slot).containsKey(plr.getUniqueId())) {
			if(slotPurchases(slot).get(plr.getUniqueId()) >= 3) {
				return new ItemStack(Material.BARRIER);
			} else {
				return chosenRewards.get(slot - 1).getIcon();
			}
		} else {
			return chosenRewards.get(slot - 1).getIcon();
		}
	}
	
	public static String getStatus(Player plr, int slot) {
		if(slotPurchases(slot).containsKey(plr.getUniqueId())) {
			if(slotPurchases(slot).get(plr.getUniqueId()) >= 3) {
				return "&cOut of stock!";
			} else {
				return chosenRewards.get(slot - 1).getName();
			}
		} else {
			return chosenRewards.get(slot - 1).getName();
		}
	}
	
	public static ArrayList<String> getLore(Player plr, int slot) {
		if(slotPurchases(slot).containsKey(plr.getUniqueId())) {
			if(slotPurchases(slot).get(plr.getUniqueId()) >= 3) {
				return new ArrayList<>(Arrays.asList("&7This item is out of stock. Come back tomorrow!"));
			}
		}
		
		TicketReward ticketReward = chosenRewards.get(slot - 1);
		ArrayList<String> lore = new ArrayList<>();
		
		lore.addAll(ticketReward.getLore());
		lore.add("");
		lore.add("&7Cost: &a" + ticketReward.getCost() + " &7tickets");
		lore.add("");
		lore.add("&8&oThe Ticket Shop restocks daily with a");
		lore.add("&8&ovariety of items. Each player can purchase");
		lore.add("&8&oeach item up to 3 times per day.");
		lore.add("");
		lore.add("&7Click to purchase this item.");
		
		return lore;
		
	}
	
	public static void selectDailyOptions() {
		
		Calendar nextDay = Calendar.getInstance();
		
		nextDay.add(Calendar.DAY_OF_MONTH, 1);
		nextDay.set(Calendar.HOUR_OF_DAY, 0);
		nextDay.set(Calendar.MINUTE, 0);
		nextDay.set(Calendar.SECOND, 0);
		nextDay.set(Calendar.MILLISECOND, 0);
		
		nextDayTimestamp = nextDay.getTimeInMillis();
		
		slotOnePurchases.clear();
		slotTwoPurchases.clear();
		slotThreePurchases.clear();
		
		ArrayList<Integer> options = new ArrayList<>();
		
		for(int i = 0; i < 3; i++) {
			boolean newOption = false;
			while(newOption == false) {
				
				int random = new Random().nextInt(ticketRewards.size());
				
				if(!(options.contains(random))) {
					options.add(random);
					chosenRewards.set(i, ticketRewards.get(random));
					newOption = true;
				}
			}
		}
		
		Bukkit.broadcastMessage(Utils.chat("&aThe Ticket Shop has restocked!"));
		for(Player plr : Bukkit.getOnlinePlayers()) {
			plr.playSound(plr.getLocation(), Sound.LEVEL_UP, 1, 2F);
		}
	}
	
}
