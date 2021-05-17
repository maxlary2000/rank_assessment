package com.rank.assess.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TransactionRepository transactionRepository) {
        this.playerRepository = playerRepository;
        this.transactionRepository = transactionRepository;
    }

    public Player getBalance(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid player id"));

        return player;
    }

    public Player wager(Long playerId, Transaction transaction)  {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid player id"));

        boolean exist = transactionRepository.existsById(transaction.getId());
        if(!exist) {
            if (player.getBalance().compareTo(BigDecimal.ZERO) == 0) {
                throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Insufficient funds");
            }

            if (player.getBalance().compareTo(transaction.getAmount()) == -1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
            }
            transaction.setPlayer(player);
            transactionRepository.save(transaction);

            if (transaction.getPromo() != null && transaction.getPromo().equals("paper")) {
                player.setPromoCount(5);
            }

            if (player.getPromoCount() == null || player.getPromoCount() == 0){
                BigDecimal balance = player.getBalance().subtract(transaction.getAmount());
                player.setBalance(balance);
                playerRepository.save(player);
            } else {
                if(player.getPromoCount() != null && player.getPromoCount() > 0) {
                    player.setPromoCount(player.getPromoCount() - 1);
                    playerRepository.save(player);
                }
            }
        }

        return player;
    }

    public Player win(Long playerId, Transaction transaction) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid player id"));

        boolean exist = transactionRepository.existsById(transaction.getId());
        if(!exist){
            transaction.setPlayer(player);
            transactionRepository.save(transaction);

            BigDecimal balance = player.getBalance().add(transaction.getAmount());
            player.setBalance(balance);
            playerRepository.save(player);
        }

        return player;
    }

    public List<Transaction> getTransactions(Player player) {
        Optional<Player> playerOptional = playerRepository.findPlayerByUsername(player.getUsername());

        if(player.getPassword() != null && player.getPassword().equals("swordfish")) {
            if (!playerOptional.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid username");
            } else {
                //return transactionRepository.findDistinctAll();
                return transactionRepository.findByPlayer(player, PageRequest.of(0, 10, Sort.by("id").descending()));
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password");
        }
    }
}
