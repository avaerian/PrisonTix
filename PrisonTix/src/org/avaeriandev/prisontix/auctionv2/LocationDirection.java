package org.avaeriandev.prisontix.auctionv2;

import org.bukkit.Location;

enum Direction {
	NORTH, SOUTH, EAST, WEST
}

public class LocationDirection {

	private Location location;
	private Direction direction;
	
	public LocationDirection(Location location, Direction direction) {
		this.location = location;
		this.direction = direction;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public Direction getDirection() {
		return direction;
	}
}
