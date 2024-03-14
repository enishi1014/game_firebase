package com.example.gameapp.service;

import com.example.gameapp.model.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;

@Service
public class GameService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(supabaseUrl)
                .defaultHeader("apikey", supabaseKey)
                .defaultHeader("Authorization", "Bearer " + supabaseKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }


    public Flux<Game> findByPlayersAndPlatform(int players, String platform) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/rest/v1/game")
                        .queryParam("minPlayers", "lte." + players)
                        .queryParam("maxPlayers", "gte." + players)
                        .queryParam("platform", "eq." + platform)
                        .build())
                .retrieve()
                .bodyToFlux(Game.class); // 直接Flux<Game>を返す
    }

}
