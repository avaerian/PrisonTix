package org.avaeriandev.prisontix.events;

import org.avaeriandev.prisontix.auctionv2.AuctionItemPanel;
import org.avaeriandev.prisontix.auctionv2.AuctionMaster;
import org.avaeriandev.prisontix.auctionv2.Listing;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AuctionStandInteract implements Listener {

	@EventHandler
	public void onArmorStandManipulate(PlayerArmorStandManipulateEvent e) {
		
		Player plr = e.getPlayer();
		ArmorStand as = e.getRightClicked();
		Listing listing = null;
		
		if(as.getHelmet().getType() == Material.GLASS) {
			for(Listing tempListing : AuctionMaster.listings.values()) {
				if(tempListing.getArmorStand().equals(as)) {
					listing = tempListing;
				}
			}
			
			if(listing != null) {
				plr.openInventory(AuctionItemPanel.GUI(plr, listing));
			} else {
				plr.sendMessage(Utils.chat("&cAn error occurred while opening the panel."));
			}
		}
		
		e.setCancelled(true);
	}
	
}
