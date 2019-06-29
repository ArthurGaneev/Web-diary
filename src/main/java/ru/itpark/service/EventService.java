package ru.itpark.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.dto.EventDto;
import ru.itpark.entity.EventEntity;
import ru.itpark.exception.EventNotFoundException;
import ru.itpark.exception.UnsupportedFileContentTypeException;
import ru.itpark.exception.UploadFileException;
import ru.itpark.repository.EventRepository;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository repository;
    private final Path uploadPath;

    public EventService(
            EventRepository repository,
            @Value("${spring.resources.static-locations}") String uploadPath
    ) {
        this.repository = repository;
        this.uploadPath = Path.of(URI.create(uploadPath)).resolve("media");
    }

    public List<EventEntity> getAll() {
        return repository.findAll();
    }

    public EventEntity getById(int id) {
        return repository.findById(id)
                .orElseThrow(EventNotFoundException::new);
    }

    public EventEntity getByIdOrEmpty(int id) {
        return repository.findById(id)
                .orElse(new EventEntity());
    }

    public void save(EventDto item) {
        EventEntity entity = getByIdOrEmpty(item.getId());
        entity.setName(item.getName());
        entity.setDuration(item.getDuration());
        entity.setDescription(item.getDescription());

        MultipartFile file = item.getFile();
        if (!file.isEmpty() && file.getContentType() != null) {
            String ext;
            if (file.getContentType().equals(MediaType.IMAGE_PNG_VALUE)) {
                ext = ".png";
            } else if (file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
                ext = ".jpg";
            } else {
                throw new UnsupportedFileContentTypeException();
            }

            String name = UUID.randomUUID().toString() + ext;

            try {
                file.transferTo(uploadPath.resolve(name));

                if (entity.getPath() != null) {
                    Files.deleteIfExists(uploadPath.resolve(entity.getPath()));
                }
            } catch (IOException e) {
                throw new UploadFileException(e);
            }

            entity.setPath(name);
        }

        repository.save(entity);
    }

    public void removeById(int id) {
        repository.deleteById(id);
    }

    public List<EventEntity> findByName(String name) {
        return repository.findAllByName(name);
    }

    public void likeById(int id) {
        repository.likeById(id);
    }

    public void dislikeById(int id) {
        repository.dislikeById(id);
    }


}