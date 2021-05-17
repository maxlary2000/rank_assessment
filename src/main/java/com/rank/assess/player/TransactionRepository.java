package com.rank.assess.player;

import net.bytebuddy.TypeCache;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByPlayer(Player player, Pageable pageable);

    @Query("SELECT DISTINCT t FROM Transaction t")
    List<Transaction> findDistinctAll(Pageable pageable);
}
