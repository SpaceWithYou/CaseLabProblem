package com.example.demo.repository;

import com.example.demo.entity.FileEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**Репозиторий для пагинации*/
@Repository
public interface PagingFileRepository extends PagingAndSortingRepository<FileEntity, UUID> {
}
