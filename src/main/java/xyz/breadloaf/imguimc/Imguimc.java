package xyz.breadloaf.imguimc;

import net.minecraft.client.Minecraft;
import xyz.breadloaf.imguimc.interfaces.Renderable;
import java.util.ArrayList;


public class Imguimc {
    public static final Minecraft MINECRAFT = Minecraft.getInstance();
    public static ArrayList<Renderable> renderstack = new ArrayList<>();

    public static ArrayList<Renderable> toRemove = new ArrayList<>();

    public static Renderable pushRenderable(Renderable renderable) {
        renderstack.add(renderable);
        return renderable;
    }

    public static Renderable pullRenderable(Renderable renderable) {
        renderstack.remove(renderable);
        return renderable;
    }

    public static void pullAllRenderables() {
        renderstack.clear();
    }
}
