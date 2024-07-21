package com.example.demo;

import com.example.demo.entity.FileEntity;
import com.example.demo.repository.FileRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@DataJpaTest(properties = {"spring.config.location=classpath:application-test.properties"})
public class APITests {
    /**Класс для юнит тестов, используется бд - h2.
     * Тестов для репозитория и сущности нет необходимости делать.
     * Просто проверяем возможность загрузить/получить сущность*/
    @Autowired
    private FileRepo repo;

    /**Начальный размер файла*/
    private static final int INITIAL_SIZE = 1 << 10;
    /**Файл*/
    private final ClassPathResource resource = new ClassPathResource("TestFiles.txt");
    /**Чтение тестового файла*/
    private static byte[] loadTestFile(File file) throws IOException {
        int c;
        StringBuilder builder = new StringBuilder(INITIAL_SIZE);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
        }
        return builder.toString().getBytes();
    }

    @BeforeEach
    public void createDataForTests() throws IOException {
        //Подгружаем нужный файл
        ClassPathResource resource = new ClassPathResource("TestFiles.txt");
        FileEntity file1 = new FileEntity();
        //В принципе не важно есть ли тут Base64 или нет, т.к. работаем с массивом байтов
        FileEntity file2 = new FileEntity("File2", ZonedDateTime.now(),
                "This is File2", UUID.randomUUID(),
                Base64.encode(loadTestFile(resource.getFile())));
        FileEntity file3 = new FileEntity();
        file3.setId(UUID.randomUUID());
        file3.setTitle("File3");
        file3.setCreationDate(ZonedDateTime.now());
        repo.save(file1);
        repo.save(file2);
        repo.save(file3);
    }

    @Test
    public void loadAndGetTest() throws IOException {
        UUID id = UUID.randomUUID();
        ArrayList<FileEntity> files = new ArrayList<>(3);
        for (FileEntity file: repo.findAll()) {
            //Проверим файл 2
            if(file.getTitle() != null && file.getTitle().equals("File2")) id = file.getId();
            files.add(file);
        }
        //3 файла
        assert files.size() == 3;
        //Без проверки времени
        //Проверка уже есть
        FileEntity file2 = repo.findById(id).get();
        //Описание
        assert file2.getDescription().equals("This is File2");
        //Декодируем Base64 и сравниваем с эталонным значением
        assert Arrays.equals(Base64.decode(file2.getContent()),
                loadTestFile(resource.getFile()));
    }

}
