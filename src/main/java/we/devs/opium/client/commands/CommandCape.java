package we.devs.opium.client.commands;

import net.minecraft.util.Identifier;
import we.devs.opium.api.manager.CapeManager;
import we.devs.opium.api.manager.command.Command;
import we.devs.opium.api.manager.command.RegisterCommand;
import we.devs.opium.api.utilities.ChatUtils;

import java.util.List;

@RegisterCommand(name="Cape", description="Let's you change your cape", syntax="cape static/animated <name>", aliases={"cape"})
public class CommandCape extends Command {
    @Override
    public void onCommand(String[] args) {
        if (args.length == 2) {
            if (args[0].equals("static")) {
                setCape(args[1].toLowerCase());
            } else if (args[0].equals("animated")) {
                setAnimatedCape(args[1].toLowerCase());
            } else {
                this.sendSyntax();
            }
            ChatUtils.sendMessage("Cape set to " + args[1],"Cape");
        } else {
            this.sendSyntax();
        }
    }

    private static void setCape(String capeName) {
        Identifier newCape = Identifier.of("opium", "textures/capes/" + capeName + ".png");
        CapeManager.setCurrentCape(newCape);
    }
    private static void setAnimatedCape(String animationName) {
        List<Identifier> frames = CapeManager.loadAnimationFrames(animationName);
        if (frames.isEmpty()) {
            ChatUtils.sendMessage("No animated cape found for animation " + animationName, "Cape");
            return;
        }
        CapeManager.setAnimatedCape(animationName);
    }
}
