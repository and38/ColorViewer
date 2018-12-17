package basic;


import equilinoxmodkit.mod.EquilinoxMod;
import equilinoxmodkit.mod.ModInfo;
import equilinoxmodkit.util.Logger;
import org.spongepowered.asm.mixin.Mixins;

@ModInfo(
        id = "libraryaddictfan.colorviewer",
        name = "Color Viewer",
        author = "libraryaddictfan",
        description = "A mod which allows the user to view the color traits of all entities of the same type in a certain area.",
        version = "0.0.1"
)
public class ColorViewer extends EquilinoxMod {

        public void preInit() {
        Logger.logMsg("!!!!!! Color Viewer Mod pre-initialized !!!!!!");
        Mixins.addConfiguration("mixins.colorviewer.json");
    }

    public void init() {
        Logger.logMsg("!!!!!!!! Color Viewer Mod initialized !!!!!!!!");
    }
}
