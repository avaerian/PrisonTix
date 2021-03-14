package org.avaeriandev.prisontix.auctionv2;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class Listing {

	private final ItemStack item;
	private final UUID seller;
	
	private final int startingPrice;
	private final int bidIncrement;
	private int currentPrice;
	private HashMap<UUID, Integer> bids = new HashMap<>();
	
	private LocationDirection locDir;
	private ArmorStand armorstand;
	private Block sign;
	private Item entityItem;
	
	public Listing(ItemStack item, int startingPrice, int bidIncrement, UUID seller) {
		this.item = item;
		this.seller = seller;
		this.startingPrice = startingPrice;
		this.currentPrice = startingPrice;
		this.bidIncrement = bidIncrement;
	}
	
	public void setSign(Block sign) {
		this.sign = sign;
	}
	
	public Block getSign() {
		return sign;
	}
	
	public void bid(UUID plr, int amount) {
		bids.put(plr, amount);
	}
	
	public void setLocation(LocationDirection locDir) {
		this.locDir = locDir;
	}
	
	public void setArmorStand(ArmorStand armorstand) {
		this.armorstand = armorstand;
	}
	
	public LocationDirection getLocation() {
		return locDir;
	}
	
	public ArmorStand getArmorStand() {
		return armorstand;
	}
	
	public void setEntityItem(Item entityItem) {
		this.entityItem = entityItem;
	}
	
	public Item getEntityItem() {
		return entityItem;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public UUID getSeller() {
		return seller;
	}
	
	public HashMap<UUID, Integer> getBids() {
		return bids;
	}
	
	public int getStartingPrice() {
		return startingPrice;
	}
	
	public int getBidIncrement() {
		return bidIncrement;
	}
	
	public int getCurrentPrice() {
		return currentPrice;
	}
	
}
