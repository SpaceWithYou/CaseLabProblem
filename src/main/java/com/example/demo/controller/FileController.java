package com.example.demo.controller;

import com.example.demo.entity.FileEntity;
import com.example.demo.repository.FileRepo;
import com.example.demo.repository.PagingFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**REST контроллер с необходимыми API*/
@RestController
@RequestMapping
public class FileController {

    @Autowired
    private FileRepo repo;

    @Autowired
    private PagingFileRepository pagingRepo;

    /** Отправить файл */
    @PostMapping("/file")
    public UUID createFile(@RequestBody FileEntity file) {
        return repo.save(file).getId();
    }

    /** Получить файл по id*/
    @GetMapping("/file/{id}")
    public FileEntity getFile(@PathVariable UUID id) {
        return repo.findById(id).orElse(null);
    }

    /**Получить все файлы по странично с сортировкой по дате создания*/
    @GetMapping("/file")
    public Iterable<FileEntity> getFiles(@RequestParam(defaultValue = "true") boolean isAscending,
                                     @RequestParam(defaultValue = "0") int pageNumber,
                                     @RequestParam(defaultValue = "5") int pageSize) {
        if(pageNumber < 0 || pageSize <= 0) return null;
        Pageable paging;
        if(isAscending) {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by("creationDate").ascending());
        } else {
            paging = PageRequest.of(pageNumber, pageSize, Sort.by("creationDate").descending());
        }
        return pagingRepo.findAll(paging).getContent();
    }

}
