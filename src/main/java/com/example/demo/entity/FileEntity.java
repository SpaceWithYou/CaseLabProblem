package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.UUID;

/**Класс сущности представляющий файл в бд для ORM*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "filedatabase")
public class FileEntity {
    @Column(columnDefinition = "varchar(255)")
    private String title;

    @Column(name = "creation_date", columnDefinition = "timestamp with time zone")
    private ZonedDateTime creationDate;

    @Column(columnDefinition = "varchar(255)")
    private String description;

    @Id @Column(columnDefinition = "uuid") @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "text")
    private byte[] content;
}
