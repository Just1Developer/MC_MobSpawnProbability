package mobspawnprobability.mobspawnprobability;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class MobSpawnProbability extends JavaPlugin implements Listener {
    
    public static MobSpawnProbability Instance;
    
    @Override
    public void onEnable() {
        Instance = this;
        // Plugin startup logic
        Config.loadProbabilites();
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler
    public void onSpawn(EntitySpawnEvent e)
    {
        if(!Config.probabilities.containsKey(e.getEntityType())) return;
        double worldSpawnRate;
        String worldName = e.getLocation().getWorld().getName();
        if(worldName.endsWith("_nether")) worldSpawnRate = Config.Nether;
        else if(worldName.endsWith("_the_end")) worldSpawnRate = Config.End;
        else worldSpawnRate = Config.Overworld;

        double attitude = 1.0;
        if(Config.FriendlyMobs.contains(e.getEntityType())) attitude = Config.Friendly;
        else if(Config.HostileMobs.contains(e.getEntityType())) attitude = Config.Hostile;
        
        double Probability = worldSpawnRate * Config.probabilities.get(e.getEntityType()) * attitude;
        // Deny cancel if the probability says so
        if(Math.random() > Probability) e.setCancelled(true);
    }
}
