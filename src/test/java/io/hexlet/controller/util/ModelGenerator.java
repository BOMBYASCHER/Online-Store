package io.hexlet.controller.util;

import io.hexlet.dto.RegistrationDTO;
import io.hexlet.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@Getter
public class ModelGenerator {
    private final Faker faker = new Faker(Locale.ENGLISH);

    private Model<User> userModel;
    private Model<RegistrationDTO> registrationModel;
    @PostConstruct
    void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.internet().password(8, 16))
                .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
                .supply(Select.field(User::getLastName), () -> faker.name().lastName())
                .toModel();

        registrationModel = Instancio.of(RegistrationDTO.class)
                .supply(Select.field(RegistrationDTO::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(RegistrationDTO::getPassword), () -> faker.internet().password(8, 32))
                .supply(Select.field(RegistrationDTO::getFirstName), () -> faker.name().firstName())
                .supply(Select.field(RegistrationDTO::getLastName), () -> faker.name().lastName())
                .toModel();
    }
}
