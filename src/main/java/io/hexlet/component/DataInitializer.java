package io.hexlet.component;

import io.hexlet.model.Category;
import io.hexlet.model.Product;
import io.hexlet.repository.CategoryRepository;
import io.hexlet.repository.ProductRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) {
        var faker = new Faker(Locale.ENGLISH);
        var title = "cameras";
        Category smartphonesCategory;
        var optionalCategory = categoryRepository.findByTitle(title);
        if (optionalCategory.isPresent()) {
            smartphonesCategory = optionalCategory.get();
        } else {
            var category = new Category();
            category.setTitle(title);
            smartphonesCategory = category;
            categoryRepository.save(smartphonesCategory);
        }
        for (int i = 0; i < 100; i++) {
            var product = new Product();
            product.setTitle(faker.camera().brandWithModel());
            product.setPrice(faker.number().numberBetween(100L, 20000L));
            product.setRating(faker.number().randomDouble(1, 1, 5));
            product.setCategory(smartphonesCategory);
            product.setAvailability(faker.number().numberBetween(1, 10) != 1);
            product.setDescription(faker.lorem().paragraph(6));
            productRepository.save(product);
        }
    }
}
