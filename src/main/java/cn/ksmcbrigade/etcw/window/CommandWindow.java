package cn.ksmcbrigade.etcw.window;

import cn.ksmcbrigade.etcw.ExternalTeleportCommandWindow;
import cn.ksmcbrigade.etcw.utils.Vec3String;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@OnlyIn(Dist.CLIENT)
public class CommandWindow {
    public static void open(){
        while (Boolean.parseBoolean(System.getProperty("java.awt.headless"))){
            System.setProperty("java.awt.headless","false");
        }
        new CommandWindow();
    }

    public CommandWindow(){
        Dialog dialog = new Dialog((Frame) null,I18n.get("key.etcw.window"),false);
        //frame.setResizable(true);
        dialog.setSize(180,100);
        dialog.setMinimumSize(new Dimension(180,100));

        dialog.setLocationRelativeTo(null);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ExternalTeleportCommandWindow.started = false;
                e.getWindow().hide();
            }
        });

        JPanel frame = new JPanel(new FlowLayout());

        frame.setSize(180,100);

        JTextField x = new JTextField("~",4);
        JTextField y = new JTextField("~",4);
        JTextField z = new JTextField("~",4);

        x.setSize(40,25);
        y.setSize(40,25);
        z.setSize(40,25);

        frame.add(x);
        frame.add(y);
        frame.add(z);

        JButton tp = new JButton(CommonComponents.GUI_DONE.getString());
        JButton cancel = new JButton(CommonComponents.GUI_CANCEL.getString());

        tp.setSize(60,30);
        cancel.setSize(60,30);

        tp.addActionListener(e -> ExternalTeleportCommandWindow.CHANNEL.sendToServer(new ExternalTeleportCommandWindow.Message(new Vec3String(x.getText(),y.getText(),z.getText()))));
        cancel.addActionListener(e->{
            ExternalTeleportCommandWindow.started = false;
            dialog.hide();
        });

        frame.add(tp,BorderLayout.WEST);
        frame.add(cancel,BorderLayout.EAST);

        dialog.add(frame);

        dialog.setVisible(true);
    }
}
