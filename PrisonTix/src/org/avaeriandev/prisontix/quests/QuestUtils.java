package org.avaeriandev.prisontix.quests;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestUtils {

	public static ArrayList<Object> requirements(Object...requirements) {
		return new ArrayList<>(Arrays.asList(requirements));
	}
	
	public static ArrayList<String> lore(String...lore) {
		return new ArrayList<>(Arrays.asList(lore));
	}
	
}
