package basic;

import breedingTraits.FloatTrait;
import breedingTraits.Trait;
import componentArchitecture.ComponentType;
import entityBundle.EntityBundle;
import fontRendering.Text;
import instances.Entity;
import materials.ColourTrait;
import org.lwjgl.util.vector.Vector3f;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import text3D.Text3D;
import text3D.TraitDisplayOptionUi;

import java.lang.reflect.Field;
import java.util.Iterator;

@Mixin(value = TraitDisplayOptionUi.class, remap = false)
public abstract class MixinTraitDisplayOptionUi {

    @Shadow private ComponentType component;
    @Shadow private int traitIndex;

    @Shadow
    public abstract String getText(Entity entity);

    @Inject(method = "getText", at = @At("HEAD"), cancellable = true)
    private void onGetText(Entity entity, CallbackInfoReturnable<String> info) {
        Trait trait = entity.getComponent(this.component).getTrait(this.traitIndex);
        String textInfo = "Trait not displayable";

        if (trait instanceof ColourTrait) {
            textInfo = "Colour";
        } else if (trait instanceof FloatTrait) {
            textInfo = trait.blueprint.getName() + ": " + ((FloatTrait) trait).getFormattedTrait();
        }

        info.setReturnValue(textInfo);
        info.cancel();
    }

    @Inject(method = "loopNearbyEntities", at = @At(value = "FIELD", target = "activeTexts", opcode = Opcodes.GETFIELD), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onLoopNearbyEntities(EntityBundle entityBundle, CallbackInfo info,
                                      float camDis, Entity entity, Iterator<?> var4,
                                      Text3D text, Vector3f entityPos, float dis,
                                      float factor, float heightFactor) {
        if (entity != null && text != null && getText(entity).equals("Colour")) {
            try {
                Field field = text.getClass().getDeclaredField("text");
                field.setAccessible(true);
                Text rawText = (Text) field.get(text);
                rawText.setColour(((ColourTrait) entity.getComponent(this.component).getTrait(this.traitIndex)).getValue());
                field.setAccessible(false);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

}
