package com.catan.infrastructure.persistence.redis.repository;

import com.catan.domain.entity.Card;
import com.catan.domain.repository.CardRepository;
import com.catan.infrastructure.persistence.redis.RedisClient;
import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static redis.clients.jedis.ScanParams.SCAN_POINTER_START;

@Component("redisCardRepositoryImpl")
@Primary
public class CardRepositoryImpl implements CardRepository {

    private static final String CARD_NAMESPACE_PREFIX = "catan:card:";
    private static final String ALL_CARDS_KEY = CARD_NAMESPACE_PREFIX + "*";
    private JedisPool jedisPool;

    public CardRepositoryImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public List<Card> getCards() {
        Jedis jedis = jedisPool.getResource();
        Gson gson = new Gson();
        List<Card> cards = new ArrayList<>();
        ScanParams scanParams = new ScanParams().match(ALL_CARDS_KEY).count(100);
        String cur = SCAN_POINTER_START;

         do {
            ScanResult<String> scanResult = jedis.scan(cur, scanParams);
            List<String> result = scanResult.getResult();
            List<Card> iterationCards = jedis.mget(result.toArray(new String[result.size()]))
                    .stream()
                    .map(json -> gson.fromJson(json, Card.class))
                    .collect(Collectors.toList());

            cards.addAll(iterationCards);
            cur = scanResult.getStringCursor();
        }while(!cur.equals(SCAN_POINTER_START));

         jedis.close();
        return cards;
    }

    @Override
    public Card insertCard(Card card) {
        String id = new ObjectId().toString();
        card.setId(id);

        Gson gson = new Gson();

        String json = gson.toJson(card);

        Jedis redis = jedisPool.getResource();

        redis.set(keyForId(id),json);

        return card;
    }

    private static String keyForId(String id){
        return CARD_NAMESPACE_PREFIX + id;
    }

    @Override
    public Card getCardById(String id) {
        Gson gson = new Gson();

        Jedis redis = jedisPool.getResource();
        String response = redis.get(keyForId(id));

        Card card = gson.fromJson(response,Card.class);

        return card;
    }

    @Override
    public Card deleteCard(String id) {
        return null;
    }
}
