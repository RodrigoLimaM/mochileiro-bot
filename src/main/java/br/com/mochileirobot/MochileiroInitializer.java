package br.com.mochileirobot;

import br.com.mochileirobot.commands.AddItem;
import br.com.mochileirobot.commands.AddStats;
import br.com.mochileirobot.commands.Help;
import br.com.mochileirobot.commands.RemoveItem;
import br.com.mochileirobot.commands.RemoveStats;
import br.com.mochileirobot.commands.ShowItemsByPlayerName;
import br.com.mochileirobot.commands.ShowPlayersItems;
import br.com.mochileirobot.commands.ShowPlayersStatus;
import br.com.mochileirobot.commands.ShowStatsByPlayerName;
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

    @Autowired
    AddStats addStats;

    @Autowired
    RemoveStats removeStats;

    @Autowired
    ShowStatsByPlayerName showStatsByPlayerName;

    @Autowired
    ShowPlayersStatus showPlayersStatus;

    @Autowired
    Help help;

    @SneakyThrows
    public void run() {
        JDA jda = JDABuilder.createDefault(System.getenv("BOT_KEY")).build();
        jda.getPresence().setPresence(Activity.playing("RPG do Thynanzão"), true);

        jda.addEventListener(addItem);
        jda.addEventListener(removeItem);
        jda.addEventListener(showItemsByPlayerName);
        jda.addEventListener(showPlayersItems);
        jda.addEventListener(addStats);
        jda.addEventListener(removeStats);
        jda.addEventListener(showStatsByPlayerName);
        jda.addEventListener(showPlayersStatus);
        jda.addEventListener(help);
    }

}
