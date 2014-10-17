package com.SkyIsland.SecretVillager.Villager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;


/**
 * Represents a secret villager, which is both the in-game villager as well as that villager's API-defined
 * specs.
 * <p>
 * A big consideration is whether InvincibleVillager and TradeVillager should be interfaces or abstract classes
 * or what. If there were only going to be two types (like invincible and trade) it would make sense the way
 * things are set up now. They have a little duplicated code, but generally it's not exactly the same. The
 * situation becomes a little less stable when you want to create a InvincibleTrade villager. It's pretty
 * much just a TradeVillager, but has the event catching the Invincible has. It has the config and everything of
 * a trade villager. So invincible should be abstract, maybe? Or just a class that you extend... But then
 * trade villager and invincible trade villager will have the exact same code, except that InvincibleTrade
 * villager will also extend InvincibleVillager and have one line added to the constructor. hmm...
 * </p>
 * <p >
 * For now, each class is their own concrete class and can be instantiated at any stage. There's a lot of
 * code sharing going on and not a whole lot of object oriented design.</p>
 * @author Skyler
 *
 */
public interface SecretVillager {
	
	/**
	 * Converts this villager's unique information to a YAML configuration file and returns it.
	 * <p>
	 * When preparing a config file version of this villager, implementations <b>must</b> make sure to
	 * set the type of this villager under the path "type". The type should be the integer index returned
	 * from the corresponding {@link com.SkyIsland.SecretVillager.VillagerType#getIndex getIndex} method in
	 *  the {@link com.SkyIsland.SecretVillager.VillagerType VillagerType} enumeration.
	 * </p>
	 * @return
	 */
	public YamlConfiguration toConfig();
	
	/**
	 * Create a villager from a config. Each implementation of SecretVillager should take the various parameters
	 * in the passed configuration and start the villager from it.
	 * <p>
	 * Implementations can expect to not be tried to be created with a configuration section that wasn't
	 * saved with this secret villager's unique type.</p>
	 * @return
	 */
	public void load(ConfigurationSection config);
	
	/**
	 * Returns the entity that is stored and accepted to be the villager.<br />
	 * This does not have to be an instance of {@link org.bukkit.entity.Villager Villager}!!
	 * @return The villager entity.
	 */
	public Entity getVillager();
	
	/**
	 * Sets the entity to be considered the villager.<br />
	 * If an implementation uses a specific subinterface of Entity, they should figure out if the passed
	 * entity should be checked in-method.
	 * @param e The entity you want to set as the villager
	 */
	public void setVillager(Entity e);
	
	/**
	 * Unloads the villager.<br />
	 * Implementations should make sure to <i>kill</i> their villager entity and just create a new one
	 * on instantiation
	 */
	public void unload();
	
}
