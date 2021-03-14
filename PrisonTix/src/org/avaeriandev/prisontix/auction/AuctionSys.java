package org.avaeriandev.prisontix.auction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.avaeriandev.prisontix.Main;
import org.avaeriandev.prisontix.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.lightshard.prisoncells.cell.PrisonCell;
import net.lightshard.prisoncells.cell.world.region.Region;
import net.lightshard.prisoncells.event.CellUnclaimEvent;

public class AuctionSys implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public AuctionSys(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCellExpire(CellUnclaimEvent e) {
		
		// Get cell owner name
		
		PrisonCell cell = e.getCell();
		String previousOwner = String.valueOf(e.getPreviousOwner());
		
		System.out.println(previousOwner);
		
		char[] pOwnerChars = previousOwner.toCharArray();
		
		// Parse for player name
		for(int i = 0; i < pOwnerChars.length; i++) {
			if(previousOwner.charAt(0) == ',' && !(Character.isLetterOrDigit(previousOwner.charAt(0)))) {
				StringBuilder sb = new StringBuilder(previousOwner);
				sb.deleteCharAt(0);
				previousOwner = sb.toString();
				break;
			} else {
				StringBuilder sb = new StringBuilder(previousOwner);
				sb.deleteCharAt(0);
				previousOwner = sb.toString();
				continue;
			}
		}
		
		
		
		Region cellRegion = cell.getRegion();
		
		String cellID = cell.getName();
		
		System.out.println(cellID);
		
		// Find cell ward
		
		String cellWard = cellID;
		
		char[] cellWardChars = cellWard.toCharArray();
		StringBuilder sb = new StringBuilder(cellWard);
		int charOffset = 0;
		
		for(int i = 0; i < cellWardChars.length; i++) {
			if(Character.isLetter(cellWard.charAt(charOffset))) {
				charOffset++;
				continue;
			} else {
				sb.deleteCharAt(charOffset);
			}
		}
		
		cellWard = sb.toString();
		
		// Get region
		
		RegionManager rm = WorldGuardPlugin.inst().getRegionManager(cell.getWorld());
		ProtectedRegion wgRegion = rm.getRegion(cellID + "mainregion");
		
		World world = Bukkit.getWorld("Titan");
		
		double xMin = wgRegion.getMinimumPoint().getX();
		double xMax = wgRegion.getMaximumPoint().getX();
		double yMin = wgRegion.getMinimumPoint().getY();
		double yMax = wgRegion.getMaximumPoint().getY();
		double zMin = wgRegion.getMinimumPoint().getZ();
		double zMax = wgRegion.getMaximumPoint().getZ();
		
		
		List<ItemStack> parsedContents = new ArrayList<ItemStack>();
		ItemStack[] tempParse = {}; // size of chest inv
		int tempParseOffset = 0; 
		
		List<Location> chestIgnore = new ArrayList<Location>();
		
		for(double y = yMin; y < (yMax + 1); y++) {
			for(double z = zMin; z < (zMax + 1); z++){
				for(double x = xMin; x < (xMax + 1); x++) {
					
					Location bloc = new Location(world, x,y,z);
					if(bloc.getBlock().getTypeId() == 54 || bloc.getBlock().getTypeId() == 146) {
						Chest chest = (Chest) bloc.getBlock().getState();
						InventoryHolder holder = chest.getInventory().getHolder();
						ItemStack[] contents = chest.getInventory().getContents();
						if(contents != null) { // skip chest - save resources
							// Detect chest type
							if(holder instanceof DoubleChest) { // double chest
								DoubleChest doubleChest = ((DoubleChest) holder);
								Chest leftChest = (Chest) doubleChest.getLeftSide();
								Chest rightChest = (Chest) doubleChest.getRightSide();
								
								Location leftLoc = leftChest.getLocation();
								Location rightLoc = rightChest.getLocation();
								
								if(!(chestIgnore.contains(leftLoc) || chestIgnore.contains(rightLoc))) {
									// New chest; add to blacklist
									if(leftLoc == bloc) {
										// Selected left
										// Ignore right block
										chestIgnore.add(rightLoc);
									} else {
										// Selected right
										// Ignore left
										chestIgnore.add(leftLoc);
									}
									
									// get contents of chest
									
									for(int i = 0; i < contents.length; i++) {
										// parse contents, remove NULL
										
										if(!(contents[i] == null)) {
											parsedContents.add(new ItemStack(contents[i]));
											//Bukkit.broadcastMessage(String.valueOf(new ItemStack(contents[i])));
										} else {
											continue;
										}
									}
								}
								
							} else { // normal chest
								for(int i = 0; i < contents.length; i++) {
									// parse contents, remove NULL
									
									if(!(contents[i] == null)) {
										
										/*
										tempParse[tempParseOffset] = contents[i];
										tempParseOffset++;*/
										parsedContents.add(new ItemStack(contents[i]));
										//Bukkit.broadcastMessage(String.valueOf(new ItemStack(contents[i])));
									} else {
										continue;
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(parsedContents.isEmpty()) {
			return;
		}
		
		// ARCHIVE FOR STACKING ITEMS
		
		/*
		for(int i = 0; i < parsedContents.size(); i++) {
			List<ItemStack> tempContents = parsedContents;
			//tempContents.remove(i);
			for(int k = 1; k < tempContents.size(); k++) {
				if(tempContents.get(i).isSimilar(tempContents.get(k))) {
					tempContents.remove(k);
					tempContents.add(new ItemStack(tempContents.get(i).getType(), tempContents.get(k)));
				}
			}
		}*/
				
		/*
		 * South = + 1 Z
		 * North = - 1 Z
		 * East = + 1 X
		 * West = - 1 X
		 * 
		 */
		
		Block bLoc = null;
		Byte[] dirList = {2,3,4,5};
		Random rn = new Random();
		
		// Get chest count
		int chestCount = 1;
		while((chestCount * 27) < parsedContents.size()) {
			if((chestCount * 27) >= parsedContents.size()) {
				break;
			} else {
				chestCount++;
			}
		}
		
		// Check Ward
		
		if(cellWard.equalsIgnoreCase("A")) {
			// rows
						int min = 0;
						int max = 1;
						
						int sideMin = 0;
						int sideMax = 0;
						
						int randomWall = (int)(Math.random() * 10); // 3 - 5 (NORTH, WEST, EAST - relative to E ward)
						int direction = 0;
						
						int randomRowY = 0; // relative to WALL, bottom row
						int randomNum = (int) (Math.random() * 10);
						
						if(randomNum <= 4) {
							randomRowY = 0;
						} else {
							randomRowY = 4;
						}
						
						//int randomWallTemp = 3;
						int currentItem = 0;
						int currentChest = 0;
						
						int[] side = {};
						
						/*
						 * NORTH 2 [0]
						 * SOUTH 3 [1]
						 * WEST 4 [2]
						 * EAST 5 [3]
						 */
						
						/*
						 * South = + 1 Z
						 * North = - 1 Z
						 * East = + 1 X
						 * West = - 1 X
						 * 
						 */
						
						int ahPos = 0;
						
						if(randomWall <= 4) {
							direction = 4;
							side = AuctionPos.A_side_EAST;
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
							
							// horizontal spaces
							sideMin = side[2];
							sideMax = side[5];
							
						}
						
						if(randomWall >= 5) {
							direction = 5;
							side = AuctionPos.A_side_WEST;
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
							
							// horizontal spaces
							sideMin = side[2];
							sideMax = side[5];
						}
						
						// Repeat for each chest
						while(currentChest < chestCount) {
							currentChest++;
							//Bukkit.broadcastMessage(Integer.toString(currentChest));
							// scan entire row
							
							ahPos = 0;
							
							while(ahPos < (sideMax - sideMin)) {
								// create true/false value for chestPos
								boolean pickPos = rn.nextBoolean();
								
								if(pickPos == true) {
									
									// UPDATE AHPOS!
									if(direction == 4) { // WEST
										bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));								
									} else if(direction == 5) { // EAST
										bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2]  + ahPos));
									}
									
									// check if block is available
									//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + ahPos));
									if(bLoc.getType().equals(Material.BARRIER)) {
										
										//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + randomSidePos));
										bLoc.setType(Material.CHEST);
										BlockState state = bLoc.getState();
										state.setData(new MaterialData(Material.CHEST, dirList[direction - 2])); // WEST
										state.update();
										
										// Create chest
										
										Chest chest = (Chest) bLoc.getLocation().getBlock().getState();
										
										for(int i = 0; i < 27; i++) {
											if(i >= parsedContents.size()) {
												break;
											} else {
												if(!(currentItem >= parsedContents.size()-1)) {
													chest.getInventory().setItem(i, parsedContents.get(currentItem));
													currentItem++;
												} else {
													break;
												}
											}
										}
										chest.update();
										
										if(chest.getInventory().getContents() == null) {
											bLoc.setType(Material.BARRIER);
											return;
										} else {
											
											// Setup Sign
											Location signLoc = null;
											
											// Create sign
											int price = (int)((Math.random() * (5000 - 10) + 1) + 1);
											
											// Attach sign to chest correctly
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[0]))) { // NORTH
												signLoc = chest.getBlock().getLocation().subtract(0, 0, 1);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[1]))) { // SOUTH
												signLoc = chest.getBlock().getLocation().add(0, 0, 1);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[2]))) { // WEST
												signLoc = chest.getBlock().getLocation().subtract(1, 0, 0);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[3]))) { // EAST
												signLoc = chest.getBlock().getLocation().add(1, 0, 0);
											}
											
											signLoc.getBlock().setType(Material.WALL_SIGN);
											Sign s  = (Sign) signLoc.getBlock().getState();
											
											s.setRawData(chest.getRawData());
											s.setLine(0, Utils.chat("&e[BuyChest]"));
											s.setLine(1, previousOwner);
											s.setLine(3, Integer.toString(price));
											s.update();
											
											break;
										}
									} else {
										ahPos = ahPos + 2;
									}
								} else {
									ahPos = ahPos + 2;
								}
							}
						}
		}
		
		if(cellWard.equalsIgnoreCase("B")) {
			// rows
						int min = 0;
						int max = 7;
						
						int sideMin = 0;
						int sideMax = 0;
						
						int randomWall = (int)(Math.random() * 10); // 3 - 5 (NORTH, WEST, EAST - relative to E ward)
						int direction = 0;
						int randomRowY = rn.nextInt((max - min) + min) * 2; // relative to WALL, bottom row
						
						//int randomWallTemp = 3;
						int currentItem = 0;
						int currentChest = 0;
						
						int[] side = {};
						
						/*
						 * NORTH 2 [0]
						 * SOUTH 3 [1]
						 * WEST 4 [2]
						 * EAST 5 [3]
						 */
						
						/*
						 * South = + 1 Z
						 * North = - 1 Z
						 * East = + 1 X
						 * West = - 1 X
						 * 
						 */
						
						int ahPos = 0;
						
						if(randomWall == 0 || randomWall == 1 || randomWall == 2) {
							direction = 3;
							side = AuctionPos.B_side_SOUTH;
							bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));
							
							// horizontal spaces
							sideMin = side[0];
							sideMax = side[3];
							
						}
						
						if(randomWall == 3 || randomWall == 4 || randomWall == 5 || randomWall == 6) {
							direction = 4;
							side = AuctionPos.B_side_WEST;
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
							
							// horizontal spaces
							sideMin = side[2];
							sideMax = side[5];
						}
						
						if(randomWall == 7 || randomWall == 8 || randomWall == 9 || randomWall == 10) {
							direction = 5;
							side = AuctionPos.B_side_EAST;
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
							
							// horizontal spaces
							sideMin = side[2];
							sideMax = side[5];
						}
						
						// Repeat for each chest
						while(currentChest < chestCount) {
							currentChest++;
							//Bukkit.broadcastMessage(Integer.toString(currentChest));
							// scan entire row
							
							ahPos = 0;
							
							while(ahPos < (sideMax - sideMin)) {
								// create true/false value for chestPos
								boolean pickPos = rn.nextBoolean();
								
								if(pickPos == true) {
									
									// UPDATE AHPOS!
									if(direction == 3) { // SOUTH
										bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));								
									} else if(direction == 4 || direction == 5) { // WEST/EAST
										bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
									}
									
									// check if block is available
									//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + ahPos));
									if(bLoc.getType().equals(Material.BARRIER)) {
										
										//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + randomSidePos));
										bLoc.setType(Material.CHEST);
										BlockState state = bLoc.getState();
										state.setData(new MaterialData(Material.CHEST, dirList[direction - 2])); // WEST
										state.update();
										
										// Create chest
										
										Chest chest = (Chest) bLoc.getLocation().getBlock().getState();
										
										for(int i = 0; i < 27; i++) {
											if(i >= parsedContents.size()) {
												break;
											} else {
												if(!(currentItem >= parsedContents.size()-1)) {
													chest.getInventory().setItem(i, parsedContents.get(currentItem));
													currentItem++;
												} else {
													break;
												}
											}
										}
										chest.update();
										
										if(chest.getInventory().getContents() == null) {
											bLoc.setType(Material.BARRIER);
											return;
										} else {
											
											// Setup Sign
											Location signLoc = null;
											
											// Create sign
											int price = (int)((Math.random() * (5000 - 10) + 1) + 1);
											
											// Attach sign to chest correctly
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[0]))) { // NORTH
												signLoc = chest.getBlock().getLocation().subtract(0, 0, 1);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[1]))) { // SOUTH
												signLoc = chest.getBlock().getLocation().add(0, 0, 1);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[2]))) { // WEST
												signLoc = chest.getBlock().getLocation().subtract(1, 0, 0);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[3]))) { // EAST
												signLoc = chest.getBlock().getLocation().add(1, 0, 0);
											}
											
											signLoc.getBlock().setType(Material.WALL_SIGN);
											Sign s  = (Sign) signLoc.getBlock().getState();
											
											s.setRawData(chest.getRawData());
											s.setLine(0, Utils.chat("&e[BuyChest]"));
											s.setLine(1, previousOwner);
											s.setLine(3, Integer.toString(price));
											s.update();
											
											break;
										}
									} else {
										ahPos = ahPos + 2;
									}
								} else {
									ahPos = ahPos + 2;
								}
							}
						}
		}
		
		if(cellWard.equalsIgnoreCase("C")) {
			// rows
						int min = 0;
						int max = 7;
						
						int sideMin = 0;
						int sideMax = 0;
						
						int randomWall = (int)(Math.random() * 10); // 3 - 5 (NORTH, WEST, EAST - relative to E ward)
						int direction = 0;
						int randomRowY = rn.nextInt((max - min) + min) * 2; // relative to WALL, bottom row
						
						//int randomWallTemp = 3;
						int currentItem = 0;
						int currentChest = 0;
						
						int[] side = {};
						
						/*
						 * NORTH 2 [0]
						 * SOUTH 3 [1]
						 * WEST 4 [2]
						 * EAST 5 [3]
						 */
						
						/*
						 * South = + 1 Z
						 * North = - 1 Z
						 * East = + 1 X
						 * West = - 1 X
						 * 
						 */
						
						int ahPos = 0;
						
						if(randomWall == 0 || randomWall == 1 || randomWall == 2) {
							direction = 3;
							side = AuctionPos.C_side_SOUTH;
							bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));
							
							// horizontal spaces
							sideMin = side[0];
							sideMax = side[3];
							
						}
						
						if(randomWall == 3 || randomWall == 4 || randomWall == 5 || randomWall == 6) {
							direction = 4;
							side = AuctionPos.C_side_WEST;
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
							
							// horizontal spaces
							sideMin = side[2];
							sideMax = side[5];
						}
						
						if(randomWall == 7 || randomWall == 8 || randomWall == 9 || randomWall == 10) {
							direction = 5;
							side = AuctionPos.C_side_EAST;
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
							
							// horizontal spaces
							sideMin = side[2];
							sideMax = side[5];
						}
						
						// Repeat for each chest
						while(currentChest < chestCount) {
							currentChest++;
							//Bukkit.broadcastMessage(Integer.toString(currentChest));
							// scan entire row
							
							ahPos = 0;
							
							while(ahPos < (sideMax - sideMin)) {
								// create true/false value for chestPos
								boolean pickPos = rn.nextBoolean();
								
								if(pickPos == true) {
									
									// UPDATE AHPOS!
									if(direction == 3) { // SOUTH
										bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));								
									} else if(direction == 4 || direction == 5) { // EAST/WEST
										bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
									}
									
									// check if block is available
									//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + ahPos));
									if(bLoc.getType().equals(Material.BARRIER)) {
										
										//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + randomSidePos));
										bLoc.setType(Material.CHEST);
										BlockState state = bLoc.getState();
										state.setData(new MaterialData(Material.CHEST, dirList[direction - 2])); // WEST
										state.update();
										
										// Create chest
										
										Chest chest = (Chest) bLoc.getLocation().getBlock().getState();
										
										for(int i = 0; i < 27; i++) {
											if(i >= parsedContents.size()) {
												break;
											} else {
												if(!(currentItem >= parsedContents.size()-1)) {
													chest.getInventory().setItem(i, parsedContents.get(currentItem));
													currentItem++;
												} else {
													break;
												}
											}
										}
										chest.update();
										
										if(chest.getInventory().getContents() == null) {
											bLoc.setType(Material.BARRIER);
											return;
										} else {
											
											// Setup Sign
											Location signLoc = null;
											
											// Create sign
											int price = (int)((Math.random() * (5000 - 10) + 1) + 1);
											
											// Attach sign to chest correctly
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[0]))) { // NORTH
												signLoc = chest.getBlock().getLocation().subtract(0, 0, 1);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[1]))) { // SOUTH
												signLoc = chest.getBlock().getLocation().add(0, 0, 1);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[2]))) { // WEST
												signLoc = chest.getBlock().getLocation().subtract(1, 0, 0);
											}
											if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[3]))) { // EAST
												signLoc = chest.getBlock().getLocation().add(1, 0, 0);
											}
											
											signLoc.getBlock().setType(Material.WALL_SIGN);
											Sign s  = (Sign) signLoc.getBlock().getState();
											
											s.setRawData(chest.getRawData());
											s.setLine(0, Utils.chat("&e[BuyChest]"));
											s.setLine(1, previousOwner);
											s.setLine(3, Integer.toString(price));
											s.update();
											
											break;
										}
									} else {
										ahPos = ahPos + 2;
									}
								} else {
									ahPos = ahPos + 2;
								}
							}
						}
		}
		
		if(cellWard.equalsIgnoreCase("D")) {
			// rows
			int min = 0;
			int max = 7;
			
			int sideMin = 0;
			int sideMax = 0;
			
			int randomWall = (int)(Math.random() * 10); // 3 - 5 (NORTH, WEST, EAST - relative to E ward)
			int direction = 0;
			int randomRowY = rn.nextInt((max - min) + min) * 2; // relative to WALL, bottom row
			
			//int randomWallTemp = 3;
			int currentItem = 0;
			int currentChest = 0;
			
			int[] side = {};
			
			/*
			 * NORTH 2 [0]
			 * SOUTH 3 [1]
			 * WEST 4 [2]
			 * EAST 5 [3]
			 */
			
			/*
			 * South = + 1 Z
			 * North = - 1 Z
			 * East = + 1 X
			 * West = - 1 X
			 * 
			 */
			
			int ahPos = 0;
			
			if(randomWall == 0 || randomWall == 1 || randomWall == 2) {
				direction = 2;
				side = AuctionPos.D_side_NORTH;
				bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));
				
				// horizontal spaces
				sideMin = side[0];
				sideMax = side[3];
				
			}
			
			if(randomWall == 3 || randomWall == 4 || randomWall == 5 || randomWall == 6) {
				direction = 4;
				side = AuctionPos.D_side_WEST;
				bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
				
				// horizontal spaces
				sideMin = side[2];
				sideMax = side[5];
			}
			
			if(randomWall == 7 || randomWall == 8 || randomWall == 9 || randomWall == 10) {
				direction = 3;
				side = AuctionPos.D_side_SOUTH;
				bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));
				
				// horizontal spaces
				sideMin = side[0];
				sideMax = side[3];
			}
			
			// Repeat for each chest
			while(currentChest < chestCount) {
				currentChest++;
				//Bukkit.broadcastMessage(Integer.toString(currentChest));
				// scan entire row
				
				ahPos = 0;
				
				while(ahPos < (sideMax - sideMin)) {
					// create true/false value for chestPos
					boolean pickPos = rn.nextBoolean();
					
					if(pickPos == true) {
						
						// UPDATE AHPOS!
						if(direction == 4) { // WEST
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));								
						} else if(direction == 2 || direction == 3) { // NORTH/SOUTH
							bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));
						}
						
						// check if block is available
						//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + ahPos));
						if(bLoc.getType().equals(Material.BARRIER)) {
							
							//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + randomSidePos));
							bLoc.setType(Material.CHEST);
							BlockState state = bLoc.getState();
							state.setData(new MaterialData(Material.CHEST, dirList[direction - 2])); // WEST
							state.update();
							
							// Create chest
							
							Chest chest = (Chest) bLoc.getLocation().getBlock().getState();
							
							for(int i = 0; i < 27; i++) {
								if(i >= parsedContents.size()) {
									break;
								} else {
									if(!(currentItem >= parsedContents.size()-1)) {
										chest.getInventory().setItem(i, parsedContents.get(currentItem));
										currentItem++;
									} else {
										break;
									}
								}
							}
							chest.update();
							
							if(chest.getInventory().getContents() == null) {
								bLoc.setType(Material.BARRIER);
								return;
							} else {
								
								// Setup Sign
								Location signLoc = null;
								
								// Create sign
								int price = (int)((Math.random() * (5000 - 10) + 1) + 1);
								
								// Attach sign to chest correctly
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[0]))) { // NORTH
									signLoc = chest.getBlock().getLocation().subtract(0, 0, 1);
								}
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[1]))) { // SOUTH
									signLoc = chest.getBlock().getLocation().add(0, 0, 1);
								}
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[2]))) { // WEST
									signLoc = chest.getBlock().getLocation().subtract(1, 0, 0);
								}
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[3]))) { // EAST
									signLoc = chest.getBlock().getLocation().add(1, 0, 0);
								}
								
								signLoc.getBlock().setType(Material.WALL_SIGN);
								Sign s  = (Sign) signLoc.getBlock().getState();
								
								s.setRawData(chest.getRawData());
								s.setLine(0, Utils.chat("&e[BuyChest]"));
								s.setLine(1, previousOwner);
								s.setLine(3, Integer.toString(price));
								s.update();
								
								break;
							}
						} else {
							ahPos = ahPos + 2;
						}
					} else {
						ahPos = ahPos + 2;
					}
				}
			}
		}
		
		if(cellWard.equalsIgnoreCase("E")) {
			// rows
			int min = 0;
			int max = 7;
			
			int sideMin = 0;
			int sideMax = 0;
			
			int randomWall = (int)(Math.random() * 10); // 3 - 5 (NORTH, WEST, EAST - relative to E ward)
			int direction = 0;
			int randomRowY = rn.nextInt((max - min) + min) * 2; // relative to WALL, bottom row
			
			//int randomWallTemp = 3;
			int currentItem = 0;
			int currentChest = 0;
			
			int[] side = {};
			
			/*
			 * NORTH 2 [0]
			 * SOUTH 3 [1]
			 * WEST 4 [2]
			 * EAST 5 [3]
			 */
			
			int ahPos = 0;
			
			if(randomWall == 0 || randomWall == 1 || randomWall == 2) {
				direction = 2;
				side = AuctionPos.E_side_NORTH;
				bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));
				
				// horizontal spaces
				sideMin = side[0];
				sideMax = side[3];
				
			}
			
			if(randomWall == 3 || randomWall == 4 || randomWall == 5 || randomWall == 6) {
				direction = 4;
				side = AuctionPos.E_side_WEST;
				bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
				
				// horizontal spaces
				sideMin = side[2];
				sideMax = side[5];
			}
			
			if(randomWall == 7 || randomWall == 8 || randomWall == 9 || randomWall == 10) {
				direction = 5;
				side = AuctionPos.E_side_EAST;
				bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
				
				// horizontal spaces
				sideMin = side[2];
				sideMax = side[5];
			}
			
			// Repeat for each chest
			while(currentChest < chestCount) {
				currentChest++;
				//Bukkit.broadcastMessage(Integer.toString(currentChest));
				// scan entire row
				
				ahPos = 0;
				
				while(ahPos < (sideMax - sideMin)) {
					// create true/false value for chestPos
					boolean pickPos = rn.nextBoolean();
					
					if(pickPos == true) {
						
						// UPDATE AHPOS!
						if(direction == 2) {
							bLoc = world.getBlockAt(side[0] + ahPos, (side[1] + randomRowY), (side[2]));								
						} else if(direction == 4 || direction == 5) {
							bLoc = world.getBlockAt(side[0], (side[1] + randomRowY), (side[2] + ahPos));
						}
						
						// check if block is available
						//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + ahPos));
						if(bLoc.getType().equals(Material.BARRIER)) {
							
							//bLoc = world.getBlockAt(E_side1_WEST[0], (E_side1_WEST[1] + randomRowY), (E_side1_WEST[2] + randomSidePos));
							bLoc.setType(Material.CHEST);
							BlockState state = bLoc.getState();
							state.setData(new MaterialData(Material.CHEST, dirList[direction - 2])); // WEST
							state.update();
							
							// Create chest
							
							Chest chest = (Chest) bLoc.getLocation().getBlock().getState();
							
							for(int i = 0; i < 27; i++) {
								if(i >= parsedContents.size()) {
									break;
								} else {
									if(!(currentItem >= parsedContents.size()-1)) {
										chest.getInventory().setItem(i, parsedContents.get(currentItem));
										currentItem++;
									} else {
										break;
									}
								}
							}
							chest.update();
							
							if(chest.getInventory().getContents() == null) {
								bLoc.setType(Material.BARRIER);
								return;
							} else {
								
								// Setup Sign
								Location signLoc = null;
								
								// Create sign
								int price = (int)((Math.random() * (5000 - 10) + 1) + 1);
								
								// Attach sign to chest correctly
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[0]))) { // NORTH
									signLoc = chest.getBlock().getLocation().subtract(0, 0, 1);
								}
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[1]))) { // SOUTH
									signLoc = chest.getBlock().getLocation().add(0, 0, 1);
								}
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[2]))) { // WEST
									signLoc = chest.getBlock().getLocation().subtract(1, 0, 0);
								}
								if(chest.getData().equals(new MaterialData(Material.CHEST,dirList[3]))) { // EAST
									signLoc = chest.getBlock().getLocation().add(1, 0, 0);
								}
								
								signLoc.getBlock().setType(Material.WALL_SIGN);
								Sign s  = (Sign) signLoc.getBlock().getState();
								
								s.setRawData(chest.getRawData());
								s.setLine(0, Utils.chat("&e[BuyChest]"));
								s.setLine(1, previousOwner);
								s.setLine(3, Integer.toString(price));
								s.update();
								
								break;
							}
						} else {
							ahPos = ahPos + 2;
						}
					} else {
						ahPos = ahPos + 2;
					}
				}
			}
			
		}
		
		// Make announcement!
	}
}