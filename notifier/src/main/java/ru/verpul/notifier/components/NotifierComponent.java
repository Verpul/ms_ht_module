package ru.verpul.notifier.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.verpul.notifier.model.Guarantee;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Component
public class NotifierComponent {

    @Autowired
    private RestTemplate restTemplate;

//    @Scheduled(cron = "0 0 * * * ?")
    @Scheduled(cron = "0 * * * * ?")
    private void GuaranteesNotifications() {
        ResponseEntity<Guarantee[]> response = restTemplate.getForEntity("http://localhost:9000/purchases/guarantee", Guarantee[].class);
        Guarantee[] guarantees = response.getBody();
        if (guarantees != null && guarantees.length != 0) {
            String message = makeMessage(guarantees);

            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            restTemplate.postForObject("http://localhost:9000/tg", message, String.class);
            restTemplate.postForObject("http://localhost:9000/mail", message, String.class);
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
