package com.example.demo.repository;

import com.example.demo.entity.FileEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/**CRUD - репозиторий для работы с бд*/
@Repository
public interface FileRepo extends CrudRepository<FileEntity, UUID> {
}
