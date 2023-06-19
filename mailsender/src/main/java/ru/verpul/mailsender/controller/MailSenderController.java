package ru.verpul.mailsender.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.verpul.mailsender.service.MailSendService;

@RestController
@RequestMapping("/mail")
public class MailSenderController{

    private final MailSendService mailSendService;

    public MailSenderController(MailSendService mailSendService) {
        this.mailSendService = mailSendService;
    }

    @PostMapping
    public void receiveMessage(@RequestBody String message) {
        mailSendService.send("Окончание действия гарантии", message);
    }
}

