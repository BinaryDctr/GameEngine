package repo.binarydctr.gameengine.spleef;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import repo.binarydctr.gameengine.game.Game;
import repo.binarydctr.gameengine.game.GameSettings;

import java.util.ArrayList;
import java.util.List;

public class Spleef extends Game {

    public Spleef() {
        super("Spleef", "1.0", 30, 2, 16, false);
        GameSettings gameSettings = new GameSettings(this);
        gameSettings.setBlockBreak(true);
        gameSettings.setBlockPlace(false);
        gameSettings.setStorm(false);
        gameSettings.setLockTime(true);
        gameSettings.setPvp(false);
        gameSettings.setTime(0L);
        gameSettings.setWaterDamage(false);
        gameSettings.setThunder(false);

        setGameSettings(gameSettings);
    }

    List<Block> blocks = new ArrayList<>();

    @Override
    public void start() {
        Bukkit.broadcastMessage(ChatColor.AQUA + "Players have fun, and spleef your hearts away.");
    }

    @Override
    public void end() {
        if(getAlive().size() == 1) {
            Bukkit.broadcastMessage(ChatColor.GOLD + "The winner is " + getAlive().get(0).getName());
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

    }
}
