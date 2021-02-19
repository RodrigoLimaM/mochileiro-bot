package br.com.mochileirobot.service;

import br.com.mochileirobot.config.NotEnoughItemsException;
import br.com.mochileirobot.model.Player;
import br.com.mochileirobot.model.Player.Item;
import br.com.mochileirobot.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            playerRepository.save(buildPlayer(playerName, item, quantity));

            return playerRepository.save(player.get());
        }
    }

    private Player buildPlayer(String playerName, String item, int quantity) {
        return Player.builder()
                .name(playerName)
                .items(Collections.singletonList(Player.Item
                        .builder()
                        .name(item)
                        .quantity(quantity)
                        .build()))
                .build();
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
            player.get().getItems().forEach(it -> {
                if (it.getName().equals(item)) {
                    int difference = it.getQuantity() - quantity;
                    if (difference < 0) {
                        throw new NotEnoughItemsException();
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

    public List<Player> getPlayersItems() {
        return playerRepository.findAll();
    }
}
