package repo.binarydctr.gameengine;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import repo.binarydctr.gameengine.spleef.Spleef;

public final class GameEngine extends JavaPlugin {

    @Getter
    private static GameEngine instace;

    @Override
    public void onEnable() {
        instace = this;

        new Spleef();
    }

    @Override
    public void onDisable() {

    }
}
