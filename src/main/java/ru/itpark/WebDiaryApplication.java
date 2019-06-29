package ru.itpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.itpark.entity.EventEntity;
import ru.itpark.repository.EventRepository;

import java.util.List;

@SpringBootApplication
public class WebDiaryApplication {

    public static void main(String[] args) {

        var context = SpringApplication.run(WebDiaryApplication.class, args);

        var repository = context.getBean(EventRepository.class);

        repository.saveAll(List.of(
                new EventEntity(0, "Утренняя тренировка", 40, 0, 0, "images 1.jpg", "Пробежка в парке"),
                new EventEntity(0, "Завтрак", 15, 0, 0, "download2.jpg", "Поел яичницу"),
                new EventEntity(0, "Утренняя учеба", 120, 0, 0, "download3.jpg", "Изучение проектирования"),
                new EventEntity(0, "Дневная тренировка", 30, 0, 0, "download4.jpg", "Тренировка в зале"),
                new EventEntity(0, "Обед", 15, 0, 0, "download5.jpg", "Поел картофельное блюдо"),
                new EventEntity(0, "Вечерняя тренировка", 20, 0, 0, "download6.jpg", "Пробежка на природе"),
                new EventEntity(0, "Вечерняя учеба", 50, 0, 0, "download7.jpg", "Курсы программирования")

        ));
    }

}

