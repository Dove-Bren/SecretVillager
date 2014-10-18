package com.SkyIsland.SecretVillager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.SpawnEgg;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.SkyIsland.SecretVillager.Villager.InvincibleTradeVillager;
import com.SkyIsland.SecretVillager.Villager.InvincibleVillager;
import com.SkyIsland.SecretVillager.Villager.SecretVillager;
import com.SkyIsland.SecretVillager.Villager.TradeVillager;
import com.gmail.fedmanddev.VillagerTrade;

public class SecretVillagerPlugin extends JavaPlugin {
	
	public static SecretVillagerPlugin plugin;
	
	private static final double version = 0.143;
	
	private YamlConfiguration config;
	private YamlConfiguration villagerConfig;
	private File configFile, villagerFile;
	private List<SecretVillager> villagers;
	private boolean update;
	
	private BukkitRunnable waitForLoad = new BukkitRunnable(){
		
		public void run() {
//			YamlConfiguration villager = new YamlConfiguration();
//			ItemStack egg = new SpawnEgg(EntityType.ENDER_DRAGON).toItemStack(1);
//			ItemMeta meta = egg.getItemMeta();
//			
//			meta.setDisplayName("Easter Egg: " + "Dragon Egg");
//			
//			List<String> lore = new LinkedList<String>();
//			lore.add(ChatColor.BLACK + "The Egg of an Ender Dragon");
//			meta.setLore(lore);
//			
//			egg.setItemMeta(meta);
//			
//			ItemStack tmpItem;
//			
//			
//			Vector vect = new Vector(1494, 64, 60);
//			villager.set("name", "Noah");
//			villager.set("world", "HomeWorld");
//			villager.set("location", vect);
//			villager.set("profession", Villager.Profession.BLACKSMITH.toString());
			
			

//			villager.set("trades.trade0.item1", egg);//.serialize());
//			villager.set("trades.trade0.reward", new ItemStack(Material.SPONGE, 6));//.serialize());
//
//			tmpItem = new ItemStack(Material.GOLDEN_APPLE, 20, (short) 1);
//			villager.set("trades.trade1.item1", egg);//.serialize());
//			villager.set("trades.trade1.reward", tmpItem);//.serialize());
//
//			villager.set("trades.trade2.item1", egg);//.serialize());
//			villager.set("trades.trade2.reward", new ItemStack(Material.SKULL_ITEM, 1, (byte) 1));//.serialize());
//
//			villager.set("trades.trade4.item1", egg);//.serialize());
//			villager.set("trades.trade4.reward", new ItemStack(Material.EXP_BOTTLE, 64));//.serialize());
//
//			villager.set("trades.trade5.item1", egg);//.serialize());
//			villager.set("trades.trade5.reward", new ItemStack(Material.NAME_TAG, 5));//.serialize());
//
//			villager.set("trades.trade6.item1", egg);//.serialize());
//			tmpItem = new ItemStack(Material.MAP, 1);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName("Return to Spawn Scroll");
//			lore = new ArrayList<String>();
//			lore.add(ChatColor.GREEN + "Teleports the user back to spawn.");
//			lore.add(ChatColor.RED + "Scroll is destroyed upon use!" + ChatColor.RESET);
//			meta.setLore(lore);
//			tmpItem.setItemMeta(meta);			
//			
//			villager.set("trades.trade6.reward", tmpItem);//.serialize());
//			
//			
//			tmpItem = new ItemStack(Material.DIAMOND_PICKAXE);
//			tmpItem.addUnsafeEnchantment(Enchantment.DIG_SPEED, 8);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.GOLD + "Pix Or Dnt Hppn" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade11.item1", egg);//.serialize());
//			villager.set("trades.trade11.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_LEGGINGS);
//			tmpItem.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 7);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.RED + "Hot Pants" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade10.item1", egg);//.serialize());
//			villager.set("trades.trade10.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.BOW);
//			tmpItem.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 7);
//			tmpItem.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.GRAY + "LEEgit Snipes" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade9.item1", egg);//.serialize());
//			villager.set("trades.trade9.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_SPADE);
//			tmpItem.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
//			tmpItem.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.GREEN + "Dat Grass" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade8.item1", egg);//.serialize());
//			villager.set("trades.trade8.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.BOW);
//			tmpItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
//			tmpItem.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 2);
//			tmpItem.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.LIGHT_PURPLE + "You Mad, Bro?" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade7.item1", egg);//.serialize());
//			villager.set("trades.trade7.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_SWORD);
//			tmpItem.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.YELLOW + "Back Up Off Me" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade6.item1", egg);//.serialize());
//			villager.set("trades.trade6.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_SWORD);
//			tmpItem.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 6);
//			tmpItem.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.MAGIC + "Excalipoor" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade5.item1", egg);//.serialize());
//			villager.set("trades.trade5.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_AXE);
//			tmpItem.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
//			tmpItem.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 15);
//			tmpItem.addUnsafeEnchantment(Enchantment.WATER_WORKER, 10);
//			tmpItem.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 22);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.AQUA + "Thor's Hammer" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade4.item1", egg);//.serialize());
//			villager.set("trades.trade4.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_BOOTS);
//			tmpItem.addUnsafeEnchantment(Enchantment.PROTECTION_FALL, 5);
//			tmpItem.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.DARK_PURPLE + "Long Fall Boot" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade3.item1", egg);//.serialize());
//			villager.set("trades.trade3.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_HELMET);
//			tmpItem.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
//			tmpItem.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 4);
//			tmpItem.addUnsafeEnchantment(Enchantment.OXYGEN, 3);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.DARK_GRAY + "Tin Cap" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade2.item1", egg);//.serialize());
//			villager.set("trades.trade2.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_CHESTPLATE);
//			tmpItem.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 6);
//			tmpItem.addUnsafeEnchantment(Enchantment.THORNS, 1);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.WHITE + "Bathing Suit" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade1.item1", egg);//.serialize());
//			villager.set("trades.trade1.reward", tmpItem);//.serialize());
//
//			tmpItem = new ItemStack(Material.DIAMOND_SWORD);
//			tmpItem.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 5);
//			meta = tmpItem.getItemMeta();
//			meta.setDisplayName(ChatColor.MAGIC + "Excalibur" + ChatColor.RESET);
//			tmpItem.setItemMeta(meta);
//			villager.set("trades.trade0.item1", egg);//.serialize());
//			villager.set("trades.trade0.reward", tmpItem);//.serialize());
			
			
			/*
			 * 
			villager.set("trades.trade0.item1", egg);//.serialize());
			villager.set("trades.trade0.reward", new ItemStack(Material.DIAMOND, 20));//.serialize());
			 */
			
//			villagers.add(new InvincibleTradeVillager(villager));
			
			extractVillagers(villagerConfig);
		}
	};
	

	@Override
	public void onLoad() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		configFile = new File(this.getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			getLogger().info("Configuration file for Secret Villager plugin not found in data folder!\n"
					+ "Making a default one...");
			config = defaultConfig();
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
				getLogger().info("Unable to save default config!\nAborting plugin load...");
				config = null;
				return;
			}
		}
		else {
			try {
				config = new YamlConfiguration();
				config.load(configFile);
				if (config.getDouble("version", 0.00) - SecretVillagerPlugin.version > .00001) {
					//if versions aren't equal
					getLogger().info("Found a config file with an old version!\n"
							+ "Ignoring. Newer versions might specify more parameters that are required"
							+ "to properly use this plugin! Please upgrade your config file to version " + SecretVillagerPlugin.version  + "!");
										
				}
			} catch (IOException
					| InvalidConfigurationException e) {
				e.printStackTrace();
				getLogger().info("Unable to load config from config.yml!!\nAborting plugin load...");
				config = null;
				return;
			}
		}
		
		//config loaded/was created correctly
		//do villager config now
		villagerFile = new File(this.getDataFolder(), "villagers.yml");
		if (!villagerFile.exists()) {
			//we don't really have a default. Just make an empty one and have an empty config.
			try {
				villagerFile.createNewFile();
				villagerConfig = new YamlConfiguration();
			} catch (IOException e) {
				e.printStackTrace();
				getLogger().info("Unable to place a new file named 'villagers.yml' in resource folder.\nAborting plugin load...");
				config = null;
				villagerConfig = null;
				return;
			}
		}
		else {
			villagerConfig = new YamlConfiguration();
			try {
				villagerConfig.load(villagerFile);
			} catch (IOException
					| InvalidConfigurationException e) {
				getLogger().info("Unable to load villager config from \"villagers.yml\" in resource folder.\nAborting plugin load...");
				config = null;
				villagerConfig = null;
			}
		}
		
		
	}
	
	@Override
	public void onEnable() {
		//first thing we do is make sure config isn't null. If it is, there was a problem during
		//load and we just want to stop the plugin for even enabling
		if (config == null || villagerConfig == null) {
			this.getPluginLoader().disablePlugin(this);
			return;
		}
		//are we going to save out the new location of villagers each time the plugin is unloaded?
		this.update = config.getBoolean("update_villagers", true);
		
		SecretVillagerPlugin.plugin = this;
		villagers = new LinkedList<SecretVillager>();
		
		this.waitForLoad.runTaskLater(this, 10);
	}
	
	@Override
	public void onDisable() {
		try {
			config.save(configFile);
			villagerConfig.save(villagerFile);
		} catch (IOException e) {
			getLogger().info("\n\n\nUnable to save config files for SecretVillager!!!!!!!!!!!!!!!!!!!\n\n\n");
		}
		int i = 0;
		villagerConfig = null;
		villagerConfig = new YamlConfiguration();
		//Iterate over every villager and save out their data to a unique key
		for (SecretVillager SV : villagers) {
			if (update) {
				//Saves Villager name as "Villager_n"
				villagerConfig.set("Villager_" + i, SV.toConfig());
			}
			//Unload Villager from memory
			SV.unload();
			i++;
		}
		if (update) {
			try {
				villagerConfig.save(villagerFile);
			} catch (IOException e) {
				//This should never ever happen unless something catastrophic happens
				//Pray to Jesus that it doesn't
				getLogger().info("\n\n\nCOULD NOT SAVE CONFIGURATION DATA ON DISABLE\n\n\n");
				e.printStackTrace();
			}
		}
		villagers.clear();
	}
	
	
	
	
	private YamlConfiguration defaultConfig() {
		YamlConfiguration def = new YamlConfiguration();
		
		def.set("version", SecretVillagerPlugin.version);
		def.set("update_villagers", true);
		
		
		return def;
	}
	
	private void extractVillagers(YamlConfiguration villager) {
		Set <String> villagers;
		//YamlConfiguration vilConfig = new YamlConfiguration();
		villagers = villager.getKeys(false);
		for (String s : villagers) {
			if (s.trim().isEmpty())
				continue;
			createFromConfig(villager.getConfigurationSection(s));
		}
	}
	
	private SecretVillager createFromConfig(ConfigurationSection villager) {
		if (villager.getInt("type", -1) == -1) {
			//defaults to -1
			getLogger().info("Error when parsing villagers: Unable to find villager type in " + villager.getName());
			return null;
		}
		
		SecretVillager sVil = null;
		VillagerType type = VillagerType.fromIndex(villager.getInt("type", 0));
		
		switch (type) {
		case INVINCIBLE:
			sVil = new InvincibleVillager(villager);
			villagers.add(sVil);
			break;
		case TRADE_SWAPPED:
			sVil = new TradeVillager(villager);
			villagers.add(sVil);
			break;
		case BOTH:
			sVil = new InvincibleTradeVillager(villager);
			villagers.add(sVil);
			break;
		}
		
		
		
		return sVil;
	}
}
