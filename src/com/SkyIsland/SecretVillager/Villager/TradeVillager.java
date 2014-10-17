package com.SkyIsland.SecretVillager.Villager;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.SkyIsland.SecretVillager.SecretVillagerPlugin;
import com.SkyIsland.SecretVillager.VillagerType;
import com.gmail.fedmanddev.VillagerTrade;
import com.gmail.fedmanddev.VillagerTradeApi;

/**
 * A villager that you can modify the trades of.<br />
 * This implements SecretVillager, meaning it is created from a configuration file like Invincible Villager.
 * <p />
 * This implementation corresponds to the TradeSwap VillagerType
 * @author Skyler
 *
 */
public class TradeVillager implements SecretVillager {
	
	private BukkitRunnable initTrades = new BukkitRunnable() {
		
		@Override
		public void run() {
			//get trades and load them up
			//first clear out old trades
			
			VillagerTradeApi.clearTrades(villager);
			
			ConfigurationSection trades = config.getConfigurationSection("trades"), tradeS;
			ItemStack item1, item2, reward;
			for (String key : trades.getKeys(false)) {
							
				tradeS = trades.getConfigurationSection(key);
				//Object i1, i2, re;
				item1 =  tradeS.getItemStack("item1", null);
				item2 = tradeS.getItemStack("item2" , null);
				reward = tradeS.getItemStack("reward", null);
				if (item1 == null || reward == null) {
					continue;
  		    	}
//				item1 = ItemStack.deserialize((Map<String, Object>) i1);
//				if (i2 != null) {
//					item2 = ItemStack.deserialize((Map<String, Object>) i2);
//				}
//				else {
//					item2 = null;
//				}
//				reward = ItemStack.deserialize((Map<String, Object>) re);
				VillagerTrade trade;
				
				if (item2 != null) {
					trade = new VillagerTrade(item1, item2, reward);
				}
				else {
					trade = new VillagerTrade(item1, reward);
				}
				
				addTrade(trade);
			}
		}
	};
	
	
	private Villager villager;
	private List<VillagerTrade> tradeList;
	private ConfigurationSection config;
	
	public TradeVillager(ConfigurationSection config) {
		tradeList = new LinkedList<VillagerTrade>();
		load(config);
	}
	
	/**
	 * Adds the passed trade to the underlying villager
	 * @param trade
	 */
	public void addTrade(VillagerTrade trade) {
		if (villager == null || trade == null) {
			return;
		}
		VillagerTradeApi.addTrade(villager, trade);
		tradeList.add(trade);
	}
	
	public void clearTrades() {
		if (villager == null) {
			return;
		}
		VillagerTradeApi.clearTrades(villager);
		tradeList.clear();
	}
	
	@Override
	public YamlConfiguration toConfig() {
		/*
		 * type:
		 * world:
		 * location:
		 * trades:
		 * name:
		 * profession:
		 */
		YamlConfiguration config = new YamlConfiguration();
		
		config.set("type", VillagerType.TRADE_SWAPPED.getIndex());
		config.set("world", villager.getWorld().getName());
		config.set("location", villager.getLocation().toVector());
		config.set("name", villager.getCustomName());
		config.set("profession", villager.getProfession().toString());
		
		ConfigurationSection trades = config.createSection("trades");
		int index = 0;
		//add all trades
		for (VillagerTrade trade : tradeList) {
			//going to save in format 
			/*
			 * 
			 * 	trade1:
			 * 		item1:
			 * 		item2:
			 * 		reward:
			 * 	trade2:
			 * 		item1:
			 * 		
			 */
			trades.set("trade" + index + ".item1", VillagerTrade.getItem1(trade));//.serialize());
			if (VillagerTrade.getItem2(trade) != null) {
				trades.set("trade" + index + ".item2", VillagerTrade.getItem2(trade));//.serialize());
			}
			
			trades.set("trade" + index + ".reward", VillagerTrade.getRewardItem(trade));//.serialize());
			
			index++;
		}
		
		return config;
	}

	@Override
	public void load(ConfigurationSection config) {
		unload();
		
		this.config = config;
		
		World world;
		Location location;
		String name;
		Villager.Profession prof;
		Vector vect;
		
		world = Bukkit.getWorld((String) config.get("world", null)); //homeworld for the lulz
		vect = config.getVector("location", null);
		
		prof = Villager.Profession.valueOf((String) config.get("profession", null));
		name = (String) config.get("name", null);
		
		//check that everything was successful
		if (prof == null || name == null || vect == null || world == null) {
			if (name == null) 
				name = "null"; //for effect
			SecretVillagerPlugin.plugin.getLogger().info("Error creating villager (" + name + ") from file due to"
					+ "bad parameters!" );
			villager = null;
			return;
		}
			

		location = new Location(world, vect.getBlockX(), vect.getBlockY(), vect.getBlockZ());
		
		//create villager based on our stats
		villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
		villager.setCustomName(name);
		villager.setProfession(prof);
		
		initTrades.runTaskLater(SecretVillagerPlugin.plugin, 20);
	}

	@Override
	public Entity getVillager() {
		return villager;
	}

	@Override
	public void setVillager(Entity e) {
		if (e instanceof Villager) {
			villager = (Villager) e;
			return;
		}
		SecretVillagerPlugin.plugin.getLogger().info("Unable to set passed entity to villager, as it's not"
				+ "a villager");
		
	}
	
	@Override
	public void unload() {
		if (villager != null && !villager.isDead()) {
			villager.damage(villager.getMaxHealth());
			villager = null;
		}
	}
}
