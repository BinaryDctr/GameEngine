package repo.binarydctr.gameengine.game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import repo.binarydctr.gameengine.GameEngine;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Game implements Listener {

    int task;
    private List<Player> alive = new ArrayList<>();
    private String name;
    private String version;
    private Integer countdown;
    private GameSettings gameSettings;
    private Integer minPlayers;
    private Integer maxPlayers;
    private boolean restart;

    private boolean counting;
    private GameState gameState;

    public Game(String name, String version, Integer countdown, Integer minPlayers, Integer maxPlayers, boolean restart) {
        this.name = name;
        this.version = version;
        this.countdown = countdown;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.restart = restart;
        new GameManager(this);
        Bukkit.getServer().getPluginManager().registerEvents(this, GameEngine.getInstace());
    }

    public abstract void start();

    public abstract void end();

    public void startGame() {
        setGameState(GameState.STARTED);
        start();
    }

    public void endGame() {
        setGameState(GameState.WAITING);
        end();
        alive.removeAll(alive);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (restart) {
                    Bukkit.getServer().shutdown();
                }
            }
        }.runTaskLater(GameEngine.getInstace(), 60L);
    }

    private Integer currentCD;

    public void countdown() {
        currentCD = countdown;
        setGameState(GameState.COUNTING);
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(GameEngine.getInstace(), new Runnable() {
            @Override
            public void run() {
                switch(countdown) {
                    case 10:
                        Bukkit.broadcastMessage(ChatColor.RED + "Starting in 10 seconds.");
                        break;
                    case 3:
                    case 2:
                    case 1:
                        Bukkit.broadcastMessage(ChatColor.GOLD + "Starting in " + countdown);
                        break;
                    case 0:
                        startGame();
                        break;
                }
                countdown--;
            }
        }, 0L, 20L);
    }

    public void endCountdown() {
        Bukkit.getScheduler().cancelTask(task);
    }

    public enum GameState {
        WAITING, COUNTING, STARTED
    }
}
