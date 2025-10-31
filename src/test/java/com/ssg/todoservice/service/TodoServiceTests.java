package com.ssg.todoservice.service;

import com.ssg.todoservice.dto.PageRequestDTO;
import com.ssg.todoservice.dto.PageResponseDTO;
import com.ssg.todoservice.dto.TodoDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TodoServiceTests {
        @Autowired(required = false)
        private  TodoService todoService;

        @Test
    public void testPaging() {
            PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(3).size(10).build();
            PageResponseDTO<TodoDTO> responseDTO =
                       todoService.getList(pageRequestDTO);
            log.info(responseDTO);

            responseDTO.getDtoList().stream().forEach(todoDTO -> log.info(todoDTO));

        }


}
