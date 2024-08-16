package com.company.firstprojectspring.repository;

import com.company.firstprojectspring.module.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByCardIdAndDeletedAtIsNull(Integer cardId);
    List<Card> findAllByDeletedAtIsNull();

    List<Card> findAllByUserIdAndDeletedAtIsNull(Integer userId);



}
