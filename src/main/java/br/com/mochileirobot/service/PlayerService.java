package br.com.mochileirobot.service;

import br.com.mochileirobot.config.NotEnoughException;
import br.com.mochileirobot.config.NonExistentItemException;
import br.com.mochileirobot.model.Player;
import br.com.mochileirobot.model.Player.Item;
import br.com.mochileirobot.model.Player.Stat;
import br.com.mochileirobot.model.enums.Stats;
import br.com.mochileirobot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public Player addItem(String playerName, String item, int quantity) {

        Optional<Player> player = playerRepository.findPlayerByName(playerName);

        if(player.isPresent()) {
            List<Item> items = player.get().getItems();
            final String requestedItem = item;

            if(isExistentItem(items, requestedItem)){
                items.forEach(it -> {
                    if (it.getName().toUpperCase().equals(requestedItem.toUpperCase()))
                        it.setQuantity(it.getQuantity() + quantity);

                });
            } else {
                items.add(buildItem(item, quantity));
            }
            return playerRepository.save(player.get());
        } else {
            return playerRepository.save(buildPlayerForItems(playerName, item, quantity));
        }
    }

    private Player buildPlayerForItems(String playerName, String item, int quantity) {
        return Player.builder()
                .name(playerName)
                .items(Collections.singletonList(buildItem(item, quantity)))
                .stats(buildStatsForItems())
                .build();
    }

    private List<Stat> buildStatsForItems() {
        return Arrays.asList(
                Stat.builder().attribute(Stats.HP.name()).value(0).build(),
                Stat.builder().attribute(Stats.XP.name()).value(0).build(),
                Stat.builder().attribute(Stats.FORCA.name()).value(0).build(),
                Stat.builder().attribute(Stats.AGILIDADE.name()).value(0).build(),
                Stat.builder().attribute(Stats.MAGIA.name()).value(0).build(),
                Stat.builder().attribute(Stats.INTELIGENCIA.name()).value(0).build(),
                Stat.builder().attribute(Stats.CARISMA.name()).value(0).build()
        );
    }

    private Item buildItem(String item, int quantity) {
        return Item
                .builder()
                .name(item)
                .quantity(quantity)
                .build();
    }

    private boolean isExistentItem(List<Item> items, String requestedItem) {
        return items.stream().anyMatch(it -> it.getName().equals(requestedItem));
    }

    public Player removeItem(String playerName, String item, int quantity) {
        Optional<Player> player = playerRepository.findPlayerByName(playerName);

        if(player.isPresent()) {
            List<Item> items = player.get().getItems();

            if (items.stream().noneMatch(it -> it.getName().equals(item)))
                throw new NonExistentItemException();

            player.get().getItems().forEach(it -> {
                if (it.getName().equals(item)) {
                    int difference = it.getQuantity() - quantity;
                    if (difference < 0) {
                        throw new NotEnoughException();
                    } else {
                        it.setQuantity(difference);
                    }
                }
            });
            return playerRepository.save(player.get());
        }

        return null;
    }

    public List<Item> getItemsByName(String playerName) {
        return playerRepository.findPlayerByName(playerName)
                .map(Player::getItems)
                .orElse(null);
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    public Player addStat(String playerName, String stat, int value) {
        Optional<Player> player = playerRepository.findPlayerByName(playerName);

        if(player.isPresent()) {
            List<Stat> stats = player.get().getStats();
            stats.forEach(st -> {
                if (st.getAttribute().equals(stat)) {
                    st.setValue(st.getValue() + value);
                }
            });

            return playerRepository.save(player.get());
        } else {
            return playerRepository.save(buildPlayerForStats(playerName, stat, value));
        }
    }

    private Player buildPlayerForStats(String playerName, String stat, int value) {
        return Player.builder()
                .name(playerName)
                .items(Collections.emptyList())
                .stats(buildStatsForStats(stat, value))
                .build();
    }

    private List<Stat> buildStatsForStats(String stat, int value) {
        List<Stat> stats = Arrays.asList(
                Stat.builder().attribute(Stats.HP.name()).value(0).build(),
                Stat.builder().attribute(Stats.XP.name()).value(0).build(),
                Stat.builder().attribute(Stats.FORCA.name()).value(0).build(),
                Stat.builder().attribute(Stats.AGILIDADE.name()).value(0).build(),
                Stat.builder().attribute(Stats.MAGIA.name()).value(0).build(),
                Stat.builder().attribute(Stats.INTELIGENCIA.name()).value(0).build(),
                Stat.builder().attribute(Stats.CARISMA.name()).value(0).build()
        );

        stats.forEach(st -> {
            if (st.getAttribute().equals(stat)) {
                st.setValue(value);
            }
        });

        return stats;
    }

    public boolean isValidAttribute(String finalStat) {
        return Arrays.stream(Stats.values()).anyMatch(st -> st.name().equalsIgnoreCase(finalStat));
    }

    public Player removeStat(String playerName, String stat, int value) {
        Optional<Player> player = playerRepository.findPlayerByName(playerName);

        if(player.isPresent()) {
            player.get().getStats().forEach(st -> {
                if (st.getAttribute().equals(stat)) {
                    int difference = st.getValue() - value;
                    st.setValue(Math.max(difference, 0));
                }
            });
            return playerRepository.save(player.get());
        }
        return null;
    }

    public List<Stat> getStatsByName(String playerName) {
        return playerRepository.findPlayerByName(playerName)
                .map(Player::getStats)
                .orElse(null);
    }
}
