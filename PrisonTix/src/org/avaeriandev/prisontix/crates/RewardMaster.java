package org.avaeriandev.prisontix.crates;

import java.util.ArrayList;
import java.util.Arrays;

public class RewardMaster {

	private static ArrayList<Reward> rewards = new ArrayList<>();
	
	public static void definePossibleCrateRewards() {
		rewards = new ArrayList<>(Arrays.asList(
			
			new Reward("&a$100,000 Balance", Rarity.UNCOMMON, 0.5F, "eco give %player% 100000"),
			new Reward("&dMythical Crate", Rarity.LEGENDARY, 0.2F, "crate give %player% Mythical"),
			new Reward("&bBountiful Pickaxe", Rarity.MYTHICAL, 0.1F, "wpick give %player% BPick")
			
		));
	}
	
}
