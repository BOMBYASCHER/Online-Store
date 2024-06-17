package io.hexlet.mapper;

import io.hexlet.dto.RegistrationDTO;
import io.hexlet.model.User;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        uses = { ReferenceMapper.class },
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeMapping
    public void encryptPassword(RegistrationDTO registrationDTO) {
        var password = registrationDTO.getPassword();
        var encryptedPassword = passwordEncoder.encode(password);
        registrationDTO.setPassword(encryptedPassword);
    }

    public abstract User map(RegistrationDTO registrationDTO);
}
