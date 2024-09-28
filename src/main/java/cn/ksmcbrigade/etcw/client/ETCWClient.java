package cn.ksmcbrigade.etcw.client;

import cn.ksmcbrigade.etcw.ExternalTeleportCommandWindow;
import cn.ksmcbrigade.etcw.window.CommandWindow;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cn.ksmcbrigade.etcw.ExternalTeleportCommandWindow.started;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = ExternalTeleportCommandWindow.MODID,value = Dist.CLIENT)
public class ETCWClient {

    // Make a keymapping to start the external teleport command's window.
    public static final KeyMapping key = new KeyMapping("key.etcw.window", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM,InputConstants.KEY_Y,"key.categories.gameplay");

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event){
        if (Boolean.parseBoolean(System.getProperty("java.awt.headless"))){
            System.setProperty("java.awt.headless","false");
        }
    }

    @SubscribeEvent
    public static void registerKey(final RegisterKeyMappingsEvent event){
        event.register(key);
    }

    @SubscribeEvent
    public static void input(InputEvent.Key event){
        if(Minecraft.getInstance().player==null){
            return;
        }
        if(key.isDown() && Minecraft.getInstance().player.getPermissionLevel()>=2 && !started){
            CommandWindow.open();
            started = true;
        }
    }
}
