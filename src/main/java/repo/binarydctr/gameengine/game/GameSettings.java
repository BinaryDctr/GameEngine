package repo.binarydctr.gameengine.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import repo.binarydctr.gameengine.GameEngine;

@Getter
@Setter
public class GameSettings implements Listener {

    private Game game;

    private boolean pvp;
    private boolean blockBreak;
    private boolean blockPlace;
    private boolean waterDamage;
    private boolean lockTime;
    private boolean storm;
    private boolean thunder;
    private Long time;

    public GameSettings(Game game) {
        this.game = game;
        Bukkit.getServer().getPluginManager().registerEvents(this, GameEngine.getInstace());
        Bukkit.getScheduler().scheduleSyncRepeatingTask(GameEngine.getInstace(), new Runnable() {
            @Override
            public void run() {
                checkInWater();
                onWeather();
                timeSet();
            }
        }, 0, 20);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(event.getEntity() instanceof  Player) {
                if(pvp) {
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(blockBreak) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(blockPlace) {
            event.setCancelled(false);
        } else {
            event.setCancelled(true);
        }
    }

    public void checkInWater() {
        for(Player player : game.getAlive()) {
            if(player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.WATER) {
                if (waterDamage) {
                    player.damage(1D);
                }
            }
        }
    }

    public void onWeather() {
        World world = game.getAlive().get(0).getWorld();
        world.setThundering(thunder);
        world.setStorm(storm);
    }

    public void timeSet() {
        if(lockTime) {
            World world = game.getAlive().get(0).getWorld();
            world.setTime(time);
        }
    }
}
