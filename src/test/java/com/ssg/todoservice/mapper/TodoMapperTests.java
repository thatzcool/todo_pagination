package com.ssg.todoservice.mapper;

import com.ssg.todoservice.domain.TodoVO;
import com.ssg.todoservice.dto.PageRequestDTO;
import com.ssg.todoservice.mapper.TodoMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class TodoMapperTests {

    // Mapper 인터페이스 -> required=false
    @Autowired(required = false)
    private TodoMapper todoMapper;

    @Test
    public void testGetTime() {
        log.info(todoMapper.getTime());
    }

    @Test
    public void testInsert() {
        TodoVO todoVO = TodoVO.builder()
                .title("게시글 입력 테스트1")
                .dueDate(LocalDate.parse("2025-10-31"))
                .writer("test1")
                .build();
        todoMapper.insert(todoVO);
    }

    @Test
    public void testGetAll() {
        List<TodoVO> todoList = todoMapper.selectAll();
        todoList.forEach(log::info);
    }

    @Test
    public void testGetOne() {
        Long tno = 9L;
        TodoVO todoVO = todoMapper.selectOne(9L);
        log.info(todoVO);
        Assertions.assertNotNull(todoVO);
    }

    @Test
    public void testDelete() {
        Long tno = 9L;
        todoMapper.delete(tno);
    }

    @Test
    public void testUpdate() {
        Long tno = 9L;
        TodoVO todoVO = TodoVO.builder()
                .tno(tno)
                .title("게시글 입력 테스트입_update")
                .dueDate(LocalDate.parse("2025-12-01"))
                .writer("user00")
                .build();
        todoMapper.update(todoVO);
    }

    @Test
    public void testSelectAll() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(2).size(10).build();
        List<TodoVO> voList = todoMapper.selectList(pageRequestDTO);
        voList.forEach(log::info);


    }


}