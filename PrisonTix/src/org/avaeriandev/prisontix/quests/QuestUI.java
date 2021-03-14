package org.avaeriandev.prisontix.quests;

import java.util.ArrayList;

import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestUI implements Listener {
	
	public static Inventory inv;
	public static String inventory_name;
	public static int inv_rows = 3 * 9;
	
	// Define inventory contents
	@SuppressWarnings("deprecation")
	public static Inventory GUI (Player plr, Quest quest) {
		inventory_name = Utils.chat(quest.getName() + "'s Quest");
		
		inv = Bukkit.createInventory(null, inv_rows);
		
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
		
		// Filler
		for(int i = 0; i < inv.getSize(); i++) {
			Utils.createItemByte(inv, Material.STAINED_GLASS_PANE.getId(), 7, 1, (i + 1), "&r");
		}
		
		ArrayList<String> questInfoLore = new ArrayList<>();
		questInfoLore.add("");
		
		for(String lore : quest.getLore()) {
			questInfoLore.add("&7" + lore);
		}
		
		questInfoLore.add("");
		
		for(Object requirement : quest.getRequirements()) {
			if(requirement instanceof ItemStack) {
				ItemStack item = (ItemStack) requirement;
				
				String itemName = item.getType().name();
				itemName = itemName.replaceAll("_", " ");
				itemName = itemName.toLowerCase();
				int itemQuantity = item.getAmount();
				
				if(itemQuantity > 1) {
					questInfoLore.add("&7Return with &f" + itemQuantity + " " + itemName + "s");
				} else {
					questInfoLore.add("&7Return with &f" + itemQuantity + " " + itemName);
				}
			}
		}
		
		questInfoLore.add("");
		questInfoLore.add("&7Rewards:");
		questInfoLore.add("&7+ &a$" + quest.getMoneyReward());
		questInfoLore.add("");
		questInfoLore.add("&8&oQuests can be completed once every day.");
		questInfoLore.add("");
				
		ItemStack map = new ItemStack(Material.MAP);
		ItemMeta mapMeta = map.getItemMeta();
		mapMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS,
							 ItemFlag.HIDE_ATTRIBUTES,
							 ItemFlag.HIDE_DESTROYS,
							 ItemFlag.HIDE_ENCHANTS,
							 ItemFlag.HIDE_PLACED_ON,
							 ItemFlag.HIDE_UNBREAKABLE
		);
		map.setItemMeta(mapMeta);
		
		// Buttons
		Utils.createItemByte(inv, Material.INK_SACK.getId(), 10, 1, 11, "&aConfirm");
		Utils.createItem(inv, map, 1, 14, "&6Quest Information", questInfoLore);
		Utils.createItemByte(inv, Material.INK_SACK.getId(), 1, 1, 17, "&cCancel");
		
		toReturn.setContents(inv.getContents());
		return toReturn;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if(e.getInventory().getName().equals(inventory_name)) {
			Player plr = (Player) e.getWhoClicked();
			
			String action = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
			
			if(action.equalsIgnoreCase("Confirm")) {
				String questName = e.getInventory().getName();
				questName = questName.replaceAll("'s Quest", "");
				Quest quest = QuestMaster.getQuest(questName);
				
				if(quest == null) {
					System.out.println("Error finding quest on inventory click");
					System.out.println(questName);
					return;
				}
				
				QuestMaster.assignQuest(plr, quest);
				plr.closeInventory();
			} else if(action.equalsIgnoreCase("Cancel")) {
				plr.closeInventory();
			}
			
			e.setCancelled(true);
		}
	}
	
}
