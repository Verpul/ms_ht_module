package ru.verpul.tgbot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.verpul.tgbot.service.TGBotService;

@RestController
@RequestMapping("/tg")
public class TGBotController {
    private final TGBotService tgBotService;

    public TGBotController(TGBotService tgBotService) {
        this.tgBotService = tgBotService;
    }

    @PostMapping
    public void receiveMessage(@RequestBody String message) {
        tgBotService.sendMessage(message);
    }
}
