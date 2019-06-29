package ru.itpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itpark.entity.EventEntity;

import javax.transaction.Transactional;
import java.util.List;

public interface EventRepository
        extends JpaRepository<EventEntity, Integer> {

    List<EventEntity> findAllByName(String name);

    @Transactional
    @Modifying
    @Query("update EventEntity e\n" +
            "set e.likes = e.likes + 1\n" +
            "where e.id = :id")
    void likeById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("update EventEntity e\n" +
            "set e.dislikes = e.dislikes + 1\n" +
            "where e.id = :id")
    void dislikeById(@Param("id") int id);


}
