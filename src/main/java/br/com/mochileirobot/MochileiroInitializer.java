package br.com.mochileirobot;

import br.com.mochileirobot.commands.AddItem;
import br.com.mochileirobot.commands.RemoveItem;
import br.com.mochileirobot.commands.ShowItemsByPlayerName;
import br.com.mochileirobot.commands.ShowPlayersItems;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MochileiroInitializer {

    @Autowired
    AddItem addItem;

    @Autowired
    RemoveItem removeItem;

    @Autowired
    ShowItemsByPlayerName showItemsByPlayerName;

    @Autowired
    ShowPlayersItems showPlayersItems;

    @SneakyThrows
    public void run() {
        JDA jda = JDABuilder.createDefault("ODEyMDUxODk1MjkyMDAyMzU2.YC7IQw.BqyOoYUIyJcYGRbiwRonkZkThms").build();
        jda.getPresence().setPresence(Activity.playing("RPG do Thynanzão"), true);

        jda.addEventListener(addItem);
        jda.addEventListener(removeItem);
        jda.addEventListener(showItemsByPlayerName);
        jda.addEventListener(showPlayersItems);
    }

}
