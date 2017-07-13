package repo.binarydctr.gameengine.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import repo.binarydctr.gameengine.GameEngine;

public class GameManager implements Listener {

    private Game game;

    public GameManager(Game game) {
        this.game = game;
        Bukkit.getServer().getPluginManager().registerEvents(this, GameEngine.getInstace());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        game.getAlive().add(event.getPlayer());
        if(game.getAlive().size() >= game.getMinPlayers()) {
            if(game.getGameState() != Game.GameState.COUNTING) {
                game.countdown();
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Game will be starting shortly...");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        game.getAlive().remove(event.getPlayer());
        if(game.getAlive().size() < game.getMinPlayers()) {
            if(game.getGameState() == Game.GameState.COUNTING) {
                game.endCountdown();
            }
        } else if(game.getAlive().size() == 1) {
            if(game.getGameState() == Game.GameState.STARTED) {
                game.endGame();
            }
        }
    }

}
