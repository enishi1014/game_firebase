package com.example.gameapp.controller;

import com.example.gameapp.model.Game;
import com.example.gameapp.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/game")
    public Flux<Game> getGames(@RequestParam int players, @RequestParam String platform) {
        return gameService.findByPlayersAndPlatform(players, platform);
    }
}
