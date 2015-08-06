package com.ceres.load;

import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.message.ChatMessage;
import com.ceres.domain.repository.human.NutritionistRepository;
import com.ceres.domain.repository.human.PatientRepository;
import com.ceres.domain.repository.message.ChatRepository;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalTime;

/**
 * Created by leonardo on 17/03/15.
 */
@Component
public class ChatDataload implements DataLoad, Ordered {

    @Inject
    private PatientRepository patientRep;

    @Inject
    private NutritionistRepository nutriRep;

    @Inject
    private ChatRepository chatRepository;

    @Override
    public void load() {

        Patient pat = patientRep.findOne(1L);
        Nutritionist nutri = nutriRep.findOne(3L);
        Nutritionist nutri1 = nutriRep.findOne(4L);

        LocalTime time = LocalTime.now();

        chatRepository.save(new ChatMessage("Posso trocar arroz integral por arroz normal?", nutri, pat, time.plusMinutes(5)));
        chatRepository.save(new ChatMessage("Não", pat, nutri, time.plusMinutes(10)));
        chatRepository.save(new ChatMessage("Posso tomar açaí no lugar da agua?", nutri, pat, time.plusMinutes(15)));
        chatRepository.save(new ChatMessage("Não", pat, nutri, time.plusMinutes(20)));
        chatRepository.save(new ChatMessage("Podemos adiar nossa próxima consulta?", nutri, pat, time.plusMinutes(25)));
        chatRepository.save(new ChatMessage("Não", pat, nutri, time.plusMinutes(30)));

        chatRepository.save(new ChatMessage("Posso tomar açaí no lugar da agua?", nutri1, pat, time.plusMinutes(35)));
        chatRepository.save(new ChatMessage("Não", pat, nutri1, time.plusMinutes(40)));
        chatRepository.save(new ChatMessage("Podemos adiar nossa próxima consulta?", nutri1, pat, time.plusMinutes(45)));
        chatRepository.save(new ChatMessage("Não", pat, nutri1, time.plusMinutes(50)));

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
