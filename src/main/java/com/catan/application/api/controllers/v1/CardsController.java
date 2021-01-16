package com.catan.application.api.controllers.v1;

import com.catan.domain.entity.Card;
import com.catan.domain.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cards")
public class CardsController {

    private CardRepository cardRepository;

    @Autowired
    CardsController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public List<Card> getCards() {
        return cardRepository.getCards();
    }

    @PostMapping
    public Card insertCard(@RequestBody Card card){
         return cardRepository.insertCard(card);
    }

    @GetMapping("/{id}")
    public Card getCardById(@PathVariable String id){
        return cardRepository.getCardById(id);
    }

    @DeleteMapping("/{id}")
    public Card deleteCardById(@PathVariable String id){
        return  cardRepository.deleteCard(id);
    }
}
