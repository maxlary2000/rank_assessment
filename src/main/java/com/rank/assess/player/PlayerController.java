package com.rank.assess.player;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController  {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/{id}")
    public Player getBalance(@PathVariable("id") Long playerId) {
        return playerService.getBalance(playerId);
    }

    @PostMapping("/wager/{id}")
    public Player wager(@PathVariable("id") Long playerId, @RequestBody Transaction transaction) {
        return playerService.wager(playerId, transaction);
    }

    @PostMapping("/win/{id}")
    public Player win(@PathVariable("id") Long playerId, @RequestBody Transaction transaction ) {
        return playerService.win(playerId, transaction );
    }
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestBody Player player){
        return playerService.getTransactions(player);
    }
}