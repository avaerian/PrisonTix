package org.avaeriandev.prisontix.quests;

import java.util.concurrent.TimeUnit;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.quests.QuestMaster.QuestStatus;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuestCommand implements CommandExecutor {

	public QuestCommand() {
		Main.PLUGIN.getCommand("quest").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// /quest <quest> <player>
		
		if(!(sender.hasPermission("minerift.admin"))) {
			return false;
		}
		
		Player plr;
		
		try {
			plr = Bukkit.getPlayer(args[1]);
		} catch (Exception e) {
			return false;
		}
		
		if(args.length > 0) {
			try {
								
				Quest quest = QuestMaster.getQuestByIdentifier(args[0]);
				
				if(quest == null) {
					plr.sendMessage(Utils.chat("&cInvalid quest."));
					return false;
				}
				
				if(QuestMaster.getQuestStatus(plr, quest) == QuestStatus.COMPLETED) {
					if(QuestMaster.isCooldownOver(plr, quest)) {
						// remove player from completed quests
						QuestMaster.removeCooldown(plr, quest);
						plr.openInventory(QuestUI.GUI(plr, quest));
					} else {
						
						long timestamp = QuestMaster.getTimestamp(plr, quest);
						long timeLeft = TimeUnit.MILLISECONDS.toSeconds(timestamp - System.currentTimeMillis());
						
						long hours = TimeUnit.SECONDS.toHours(timeLeft);
						long mins = TimeUnit.SECONDS.toMinutes(timeLeft) - (TimeUnit.SECONDS.toHours(timeLeft) * 60);
						long seconds = TimeUnit.SECONDS.toSeconds(timeLeft) - (TimeUnit.SECONDS.toMinutes(timeLeft) * 60);
						
						plr.playSound(plr.getLocation(), Sound.VILLAGER_NO, 1, 1);
						plr.sendMessage(Utils.chat("&cYou have already completed this quest and can repeat in " 
						+ hours + "h " + mins + "m " + seconds + "s."));
					}
				} else if(QuestMaster.getQuestStatus(plr, quest) == QuestStatus.ACTIVE) {
					// check player inventory
					if(QuestMaster.meetsRequirements(plr, quest)) {
						QuestMaster.rewardPlayer(plr, quest);
						QuestMaster.markQuestAsComplete(plr, quest);
						for(Object requirement : quest.getRequirements()) {
							if(requirement instanceof ItemStack) {
								ItemStack item = (ItemStack) requirement;
								QuestMaster.removeItem(plr, item);
							}
						}
						plr.playSound(plr.getLocation(), Sound.VILLAGER_YES, 1, 1);
						plr.playSound(plr.getLocation(), Sound.LEVEL_UP, 1, 1);
						plr.sendMessage(Utils.chat("&aYou completed the quest!"));
					} else {
						for(Object requirement : quest.getRequirements()) {
							if(requirement instanceof ItemStack) {
								ItemStack item = (ItemStack) requirement;
								int itemQuantity = item.getAmount();
								String itemName = item.getType().name();
								itemName = itemName.replaceAll("_", " ");
								itemName = itemName.toLowerCase();
								plr.playSound(plr.getLocation(), Sound.VILLAGER_HAGGLE, 1, 1);
								plr.sendMessage(Utils.chat("&2Bring me " + itemQuantity + " " + itemName + "."));
							}
						}
						return false;
					}
					
				} else if(QuestMaster.getQuestStatus(plr, quest) == QuestStatus.INACTIVE) {
					plr.openInventory(QuestUI.GUI(plr, quest));
				}
				
			} catch (Exception e) {
				return false;
			}
		} else {
			plr.sendMessage(Utils.chat("&cMissing arguments!"));
			return false;
		}
		
		return true;
	}

}
