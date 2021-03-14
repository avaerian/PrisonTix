package org.avaeriandev.prisontix.auctionv2;

import org.bukkit.Location;

public class LocationUtils {

	public static Location getLocationFromDirection(LocationDirection locDir) {
		
		/*
		 * South = + 1 Z
		 * North = - 1 Z
		 * East = + 1 X
		 * West = - 1 X
		 * 
		 */
		
		Location loc = locDir.getLocation();
		Direction direction = locDir.getDirection();
		
		double x = loc.getX();
		double y = loc.getY() + 1;
		double z = loc.getZ();
		
		if(direction == Direction.NORTH) {
			z-=1;
		} else if(direction == Direction.SOUTH) {
			z+=1;
		} else if(direction == Direction.EAST) {
			x+=1;
		} else if(direction == Direction.WEST) {
			x-=1;
		}
		
		return new Location(loc.getWorld(), x, y, z);		
	}
	
	public static Byte getDirectionID(Direction direction) {
		
		/*
		 * NORTH 2 [0]
		 * SOUTH 3 [1]
		 * WEST 4 [2]
		 * EAST 5 [3]
		 */
		
		if(direction == Direction.NORTH) {
			return 2;
		} else if(direction == Direction.SOUTH) {
			return 3;
		} else if(direction == Direction.EAST) {
			return 5;
		} else if(direction == Direction.WEST) {
			return 4;
		} else {
			return -1;
		}
	}
	
}
