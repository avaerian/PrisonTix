package org.avaeriandev.prisontix.ticketshop;

import java.util.ArrayList;
import java.util.Arrays;

import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

enum Error {
	OUT_OF_STOCK, NOT_ENOUGH_TICKETS
}

public class TicketShopUtils {
	
	public static ArrayList<String> lore(String...lines) {
		return new ArrayList<>(Arrays.asList(lines));
	}
	
	public static void error(Player plr, Error errorType) {
		
		if(errorType == Error.NOT_ENOUGH_TICKETS) {
			plr.sendMessage(Utils.chat("&cYou do not have enough tickets!"));
		} else if(errorType == Error.OUT_OF_STOCK) {
			plr.sendMessage(Utils.chat("&cYou can not purchase any more of this item for today!"));
		}
		
		plr.playSound(plr.getLocation(), Sound.NOTE_PLING, 1, 0.5F);
	}
	
}
