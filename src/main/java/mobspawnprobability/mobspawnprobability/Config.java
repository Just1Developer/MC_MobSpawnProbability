package mobspawnprobability.mobspawnprobability;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Config {
	
	static File file = new File(MobSpawnProbability.Instance.getDataFolder() + "/probabilities.yml");
	static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
	
	public static HashMap<EntityType, Double> probabilities;
	public static double Overworld = 1.0, Nether = 1.0, End = 1.0;
	
	public static final List<EntityType> Excluded = new ArrayList<EntityType>();
	
	public static void loadProbabilites()
	{
		probabilities = new HashMap<>();
		// First, add all excluded types.
		// Sadly, List.of is not available in this java version, so I'm doing it the ugly way
		if(Excluded.isEmpty())
		{
			// Add all non-alive or banned entities
			// I could make a config for this but then I'd need to update the real config if something here changes
			// And I dont wanna do that
			// So if you desire to change the banned entities, do it here and recompile
			// Or make a pull request where you do it lol.
			Excluded.add(EntityType.ARROW);
			Excluded.add(EntityType.GLOW_ITEM_FRAME);
			Excluded.add(EntityType.THROWN_EXP_BOTTLE);
			Excluded.add(EntityType.ITEM_FRAME);
			Excluded.add(EntityType.DROPPED_ITEM);
			Excluded.add(EntityType.AREA_EFFECT_CLOUD);
			Excluded.add(EntityType.ARMOR_STAND);
			Excluded.add(EntityType.FALLING_BLOCK);
			Excluded.add(EntityType.BOAT);
			Excluded.add(EntityType.CHEST_BOAT);
			Excluded.add(EntityType.MINECART_FURNACE);
			Excluded.add(EntityType.MINECART_CHEST);
			Excluded.add(EntityType.MINECART_COMMAND);
			Excluded.add(EntityType.MINECART_HOPPER);
			Excluded.add(EntityType.MINECART_MOB_SPAWNER);
			Excluded.add(EntityType.MINECART_TNT);
			Excluded.add(EntityType.DRAGON_FIREBALL);
			Excluded.add(EntityType.EGG);
			Excluded.add(EntityType.ENDER_CRYSTAL);
			Excluded.add(EntityType.ENDER_PEARL);
			Excluded.add(EntityType.ENDER_SIGNAL);
			Excluded.add(EntityType.ENDER_DRAGON);
			Excluded.add(EntityType.EXPERIENCE_ORB);
			Excluded.add(EntityType.FIREBALL);
			Excluded.add(EntityType.FIREWORK);
			Excluded.add(EntityType.EVOKER_FANGS);		// Dont know what this is but i think this is good
			Excluded.add(EntityType.PAINTING);
			Excluded.add(EntityType.LEASH_HITCH);
			Excluded.add(EntityType.LIGHTNING);
			Excluded.add(EntityType.MARKER);
			Excluded.add(EntityType.PLAYER);
			Excluded.add(EntityType.PRIMED_TNT);
			Excluded.add(EntityType.SHULKER_BULLET);	// Yup
			Excluded.add(EntityType.SMALL_FIREBALL);
			Excluded.add(EntityType.SNOWBALL);
			Excluded.add(EntityType.SPECTRAL_ARROW);
			Excluded.add(EntityType.SPLASH_POTION);
			Excluded.add(EntityType.TRIDENT);
			Excluded.add(EntityType.UNKNOWN);
			Excluded.add(EntityType.WITHER_SKULL);
		}
		
		// Maybe add other lists later, like hostile / friendly / neutral or based on dimension
		
		if(!file.exists())
		{
			probabilities = new HashMap<>();
			
			//cfg.set("Btw,_values_go_from_0.00_to_1.00._All_else_set_to_the_nearest_border_value.", 0);
			//cfg.set("Tip:_I_left_the_wandering_trader_in,_but_please_do_reconsider_if_you_want_to_change_that_probability.", 0);
			//cfg.set("#", 0);
			
			//cfg.addDefault("Dimensions:", "");
			cfg.addDefault("Dimension.OVERWORLD", 1.0);
			cfg.addDefault("Dimension.NETHER", 1.0);
			cfg.addDefault("Dimension.END", 1.0);
			cfg.set("Dimension.OVERWORLD", 1.0);
			cfg.set("Dimension.NETHER", 1.0);
			cfg.set("Dimension.END", 1.0);
			
			//cfg.addDefault("Btw, values go from 0.00 to 1.00. All else set to the nearest border value.", 0);
			//cfg.addDefault("Tip: I left the wandering trader in, but please do reconsider if you want to change that probability.", 0);
			for(EntityType type : EntityType.values())
			{
				if(Excluded.contains(type)) continue;
				cfg.addDefault("Type." + type.toString(), 1.0);
				cfg.set("Type." + type, 1.0);
				//probabilities.put(type, 1.00);	// I removed the return statement so this is covered by the code block below
			}
			try {
				// Idk why this doesnt work the way its supposed to. I'd have to look it up but im too lazy rn so imma maybe do it later
				Objects.requireNonNull(cfg.getDefaults()).options().copyDefaults(true);
			} catch(Exception ignored)
			{
				// Yeah it just does that...
				System.out.println("Error: Failed to save default config");
			}
			saveCfg();
		}
		for(EntityType type : EntityType.values())
		{
			if(Excluded.contains(type)) continue;
			double value = cfg.getDouble("Type." + type.toString());
			// Remove caps
			//if(value > 1.0) value = 1.0;
			//if(value < 0.0) value = 0.0;
			probabilities.put(type, value);
		}
		Overworld = cfg.getDouble("Dimension.OVERWORLD");
		Nether = cfg.getDouble("Dimension.NETHER");
		End = cfg.getDouble("Dimension.END");
	}
	
	private static void saveCfg()
	{
		try {
			cfg.save(file);
		} catch (IOException e) {
			System.out.println("Warning: Failed to save file.");
		}
	}
	
}
