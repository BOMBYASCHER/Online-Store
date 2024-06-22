package io.hexlet.component;

import io.hexlet.model.Category;
import io.hexlet.model.Product;
import io.hexlet.model.User;
import io.hexlet.repository.CategoryRepository;
import io.hexlet.repository.ProductRepository;
import io.hexlet.repository.UserRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        var user = new User();
        user.setEmail("admin@pro.io");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstName("DJ");
        user.setLastName("Feel");
        userRepository.save(user);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        var faker = new Faker(Locale.ENGLISH);
        var camerasCategory = new Category();
        camerasCategory.setTitle("cameras");
        categoryRepository.save(camerasCategory);
        for (int i = 0; i < 100; i++) {
            var product = new Product();
            var brandWithModel = faker.camera().brandWithModel();
            product.setTitle(brandWithModel);
            product.setPrice(faker.number().numberBetween(25L, 2000L) * 10);
            product.setRating(faker.number().randomDouble(1, 1, 5));
            product.setCategory(camerasCategory);
            product.setAvailability(faker.number().numberBetween(1, 10) != 1);
            product.setDescription(faker.lorem().paragraph(6));
            product.setImage("image/" + brandWithModel + ".jpg");
            productRepository.save(product);
        }
    }
}
