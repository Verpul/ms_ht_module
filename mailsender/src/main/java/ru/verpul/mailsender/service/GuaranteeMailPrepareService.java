package ru.verpul.mailsender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.verpul.mailsender.model.Guarantee;

import java.time.format.DateTimeFormatter;


@Service
public class GuaranteeMailPrepareService {

    @Autowired
    private RestTemplate restTemplate;
    private final MailSendService mailSendService;

    public GuaranteeMailPrepareService(MailSendService mailSendService) {
        this.mailSendService = mailSendService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    private void receiveGuaranteeDataAndSendMail() {
        System.out.println("Do you even trying");
        ResponseEntity<Guarantee[]> response = restTemplate.getForEntity("http://localhost:9000/purchases/guarantee", Guarantee[].class);
        Guarantee[] guarantees = response.getBody();
        if (guarantees != null && guarantees.length != 0) {
            String message = makeMessage(guarantees);
            String subject = "Окончание действия гарантии";
            mailSendService.send(subject, message);
        }
    }

    private String makeMessage(Guarantee[] guarantees) {
        StringBuilder message = new StringBuilder("Истек срок гарантии: \n\n");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        for (Guarantee guarantee: guarantees) {
            message.append(guarantee.getTitle());
            message.append(", окончание гарантии - ");
            message.append(guarantee.getGuaranteeExpireDate().format(dateTimeFormatter));
            message.append("\n");
        }

        return message.toString();
    }
}
