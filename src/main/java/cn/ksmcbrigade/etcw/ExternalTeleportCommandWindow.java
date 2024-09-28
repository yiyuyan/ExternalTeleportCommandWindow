package cn.ksmcbrigade.etcw;

import cn.ksmcbrigade.etcw.utils.Vec3String;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

@Mod(ExternalTeleportCommandWindow.MODID)
@Mod.EventBusSubscriber(modid = ExternalTeleportCommandWindow.MODID)
public class ExternalTeleportCommandWindow {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "etcw";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static boolean started = false;

    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID,"window"),()->"340",(a)->true,(b)->true);

    public ExternalTeleportCommandWindow() {
        MinecraftForge.EVENT_BUS.register(this);

        CHANNEL.registerMessage(0,Message.class,Message::encode,Message::decode,(msg,context)->{
            ServerPlayer player = context.get().getSender();
            if(player!=null){
                if(msg.error){
                    player.sendSystemMessage(Component.literal("Can't teleport there."));
                }
                else{
                    try {
                        Vec3 pos = msg.pos.get(player);
                        player.teleportTo(pos.x,pos.y,pos.z);
                        player.setPos(pos);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        player.sendSystemMessage(Component.literal("An error when teleport: "+e.getMessage()));
                    }
                }
            }
            context.get().setPacketHandled(true);
        });

        LOGGER.info("{} mod loaded.", ExternalTeleportCommandWindow.class.getSimpleName());
    }

    public static class Message {

        public final Vec3String pos;
        public final boolean error;

        public Message(Vec3String vec3String){
            this.pos = vec3String;
            this.error = false;
        }

        public Message(Vec3String vec3String,boolean error){
            this.pos = vec3String;
            this.error = error;
        }

        public Message(Vec3 pos,boolean error){
            this.pos = new Vec3String(pos);
            this.error = error;
        }

        public static void encode(Message message, FriendlyByteBuf buf){
            buf.writeUtf(message.pos.x+";"+message.pos.y+";"+message.pos.z);
        }

        public static Message decode(FriendlyByteBuf buf){
            try {
                String[] splits = buf.readUtf().split(";");
                return new Message(new Vec3String(splits[0],splits[1],splits[2]),false);
            }
            catch (Exception e){
                LOGGER.error("Error in decode the vec3 message.",e);
                return new Message(Vec3.ZERO,true);
            }
        }
    }
}
