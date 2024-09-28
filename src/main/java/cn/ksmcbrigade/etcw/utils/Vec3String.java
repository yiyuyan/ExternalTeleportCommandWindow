package cn.ksmcbrigade.etcw.utils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class Vec3String {

    public ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();

    public Vec3 vec3;

    public String x;
    public String y;
    public String z;

    public Vec3String(String x,String y,String z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3String(Vec3 vec3){
        this.vec3 = vec3;
        this.x = String.valueOf(this.vec3.x);
        this.y = String.valueOf(this.vec3.y);
        this.z = String.valueOf(this.vec3.z);
    }

    public Vec3 get(ServerPlayer player){
        return vec3!=null?vec3:new Vec3(x.contains("~")?player.getX()+(expressionEvaluator.evaluate(x.replace("~",""))):(expressionEvaluator.evaluate(x)),y.contains("~")?player.getY()+(expressionEvaluator.evaluate(y.replace("~",""))):(expressionEvaluator.evaluate(y)),z.contains("~")?player.getZ()+(expressionEvaluator.evaluate(z.replace("~",""))):(expressionEvaluator.evaluate(z)));
    }
}
