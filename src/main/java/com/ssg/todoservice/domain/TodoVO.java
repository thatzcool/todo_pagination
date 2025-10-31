package com.ssg.todoservice.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoVO {
     private Long tno;
     private String title;
     private LocalDate dueDate;
     private String writer;
     private boolean finished;
}
