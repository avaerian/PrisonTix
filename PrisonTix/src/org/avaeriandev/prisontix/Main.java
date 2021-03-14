package org.avaeriandev.prisontix;

import org.avaeriandev.prisontix.auction.AuctionSys;
import org.avaeriandev.prisontix.auction.SignListener;
import org.avaeriandev.prisontix.auctionv2.AuctionUtils;
import org.avaeriandev.prisontix.commands.CommissaryCommand;
import org.avaeriandev.prisontix.commands.CommissaryOpenUI;
import org.avaeriandev.prisontix.commands.InstantFireworks;
import org.avaeriandev.prisontix.commands.StaffChatCMD;
import org.avaeriandev.prisontix.commands.TicketCommand;
import org.avaeriandev.prisontix.commands.WardenOpenUI;
import org.avaeriandev.prisontix.crates.CrateMaster;
import org.avaeriandev.prisontix.events.BlockBreak;
import org.avaeriandev.prisontix.events.InventoryClick;
import org.avaeriandev.prisontix.events.PVP;
import org.avaeriandev.prisontix.events.PlayerJoin;
import org.avaeriandev.prisontix.events.PlayerLeave;
import org.avaeriandev.prisontix.events.RegionEnter;
import org.avaeriandev.prisontix.quests.QuestCommand;
import org.avaeriandev.prisontix.quests.QuestMaster;
import org.avaeriandev.prisontix.quests.QuestUI;
import org.avaeriandev.prisontix.sys.CommissarySystem;
import org.avaeriandev.prisontix.sys.TixSystem;
import org.avaeriandev.prisontix.ticketshop.TicketShopCommand;
import org.avaeriandev.prisontix.ticketshop.TicketShopMaster;
import org.avaeriandev.prisontix.ticketshop.TicketShopUI;
import org.avaeriandev.prisontix.ui.CommissaryUI;
import org.avaeriandev.prisontix.ui.WardenUI;
import org.avaeriandev.prisontix.utils.Utils;
import org.avaeriandev.prisontix.vexarprison.FlyParticles;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {
	
	public static Main PLUGIN;
	
	public void onEnable() {
		
		PLUGIN = this;
		
		QuestMaster.defineQuests();
		CrateMaster.defineCrates();
		AuctionUtils.defineLocations();
		TicketShopMaster.defineRewards();
		TicketShopMaster.selectDailyOptions();
		TicketShopMaster.ticketResetTimer();
		
		new CommissaryOpenUI(this);
		new WardenOpenUI(this);
		new StaffChatCMD(this);
		new InstantFireworks(this);
		//new ToggleHaste(this);
		//new ToggleNightVision(this);
		//new ToggleSpeed(this);
		//new GiveXPPickaxe(this);
		
		new QuestCommand();
		new TicketShopCommand();
		new CommissaryCommand();
		new TicketCommand();
		//new EnchantCommand();
		//new PresentDebugCommand();
		//new CrateCommand();
		//new AuctionDebugCommand();
		
		//AuctionItemPanel.intialize();
		TicketShopUI.intialize();
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new TixSystem(this), this);
		pm.registerEvents(new AuctionSys(this), this);
		pm.registerEvents(new CommissarySystem(), this);
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(new InventoryClick(this), this);
		pm.registerEvents(new PVP(this), this);
		pm.registerEvents(new BlockBreak(this), this);
		pm.registerEvents(new SignListener(this), this);
		pm.registerEvents(new RegionEnter(this), this);
		pm.registerEvents(new QuestUI(), this);
		pm.registerEvents(new FlyParticles(), this);
		//pm.registerEvents(new ItemSpawn(), this);
		//pm.registerEvents(new CrateMaster(), this);
		//pm.registerEvents(new AuctionStandInteract(), this);
		//pm.registerEvents(new AuctionStandSignInteract(), this);
		//pm.registerEvents(new ItemDespawn(), this);
		//pm.registerEvents(new AuctionItemPanel(), this);
		
		// Enchantment stuff here
		//EnchantMaster.defineEnchantments();
		//pm.registerEvents(new Molten(), this);
		
		CommissaryUI.intialize();
		WardenUI.intialize();
		
		FlyParticles.checkPlayers();
		
		//Vacuum.startTimer();
		
		new BukkitRunnable() {

		int timer = 300; // seconds
			@Override
			public void run() {
				
				if(timer > 0) {
					if(timer == 10) {
						Bukkit.broadcastMessage(Utils.chat("&c&lRESETTING CLAIMED CHESTS IN AUCTION HOUSES IN &4&l10 &c&lSECONDS!"));
					}
					timer--;
				} else {
					SignListener.checkChests();
					timer = 300;
				}
				
			}
			
		}.runTaskTimer(this, 0, 20L * 1);
		
	}
	
	public static Main getInstance() {
		return PLUGIN;
	}
	
}
