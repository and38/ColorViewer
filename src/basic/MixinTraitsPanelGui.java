package basic;

import breedingTraits.Trait;
import materials.ColourTrait;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import traitGuis.TraitsPanelGui;

import java.util.Iterator;

@Mixin(value = TraitsPanelGui.class, remap = false)
public abstract class MixinTraitsPanelGui {

    @Shadow private float yGap;

    @Shadow
    private void addDisplayButton(float yPos, float yGap, final Trait trait) {}

    @Inject(method = "init", at = @At(value = "FIELD", target = "yGap", opcode = Opcodes.GETFIELD, shift = At.Shift.BY, by = -2, ordinal = 2), remap = false, locals = LocalCapture.CAPTURE_FAILSOFT)
    protected void onInit(CallbackInfo info, float yPos, Trait trait, Iterator var3) {
        if (trait != null && var3 != null) {
            if (trait instanceof ColourTrait) {
                addDisplayButton(yPos, this.yGap, trait);
            }
        }
    }
}
