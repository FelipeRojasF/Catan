package com.catan.application.api.controllers.v1;

import com.catan.domain.entity.Card;
import com.catan.domain.entity.Deck;
import com.catan.domain.repository.DeckRepository;
import com.catan.domain.service.DeckService;
import com.catan.infrastructure.persistence.mongo.repository.DeckRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/deck")
public class DeckController{


    private DeckService deckService;
    private DeckRepository deckRepository;

    @Autowired
    public DeckController(DeckService deckService, DeckRepository deckRepository){
        this.deckService = deckService;
        this.deckRepository = deckRepository;
    }

    @PostMapping
    public Deck createDeck(){
        return deckService.createDeck();
    }

    @GetMapping("/{deckId}")
    public Deck getById(@PathVariable String deckId){
        return deckRepository.getById(deckId);
    }

    @GetMapping("/{deckId}/card")
    public Card getCardFrom(@PathVariable String deckId){

        return deckService.findCard(deckId);
    }


}
