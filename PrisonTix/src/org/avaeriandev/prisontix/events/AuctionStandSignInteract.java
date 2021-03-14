package org.avaeriandev.prisontix.events;

import org.avaeriandev.prisontix.auctionv2.AuctionItemPanel;
import org.avaeriandev.prisontix.auctionv2.AuctionMaster;
import org.avaeriandev.prisontix.auctionv2.Listing;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AuctionStandSignInteract implements Listener {

	@EventHandler
	public void onSignInteract(PlayerInteractEvent e) {
		
		Player plr = e.getPlayer();
		if(!(e.hasBlock() || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		Block block = e.getClickedBlock();
		
		try {
			if(block != null && (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST)) {
				for(Listing listing : AuctionMaster.listings.values()) {
					if(listing.getSign().equals(block)) {
						plr.openInventory(AuctionItemPanel.GUI(plr, listing));
					}
				}
			} else {
				return;
			}
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
	
}
