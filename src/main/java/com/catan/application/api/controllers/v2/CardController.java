package com.catan.application.api.controllers.v2;

import com.catan.application.api.controllers.v2.mapper.CardEspMapper;
import com.catan.application.api.controllers.v2.resource.CardEspResource;
import com.catan.domain.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/v2/cards")
public class CardController {

    private CardRepository cardRepository;
    private CardEspMapper cardEspMapper;

    @Autowired
    CardController(CardRepository cardRepository, CardEspMapper cardEspMapper) {
        this.cardRepository = cardRepository;
        this.cardEspMapper = cardEspMapper;
    }

    @GetMapping
    public List<CardEspResource> getCards(){
        return  cardRepository.getCards().stream().map(cardEspMapper::fromCard).collect(toList());
    }
}
