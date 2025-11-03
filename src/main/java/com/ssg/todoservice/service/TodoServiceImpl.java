package com.ssg.todoservice.service;

import com.ssg.todoservice.domain.TodoVO;
import com.ssg.todoservice.dto.PageRequestDTO;
import com.ssg.todoservice.dto.PageResponseDTO;
import com.ssg.todoservice.dto.TodoDTO;
import com.ssg.todoservice.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    // 스프링컨테이너가 관리하는 빈을 주입 , DTO, VO 변환 작업 서비스 제공

    private final TodoMapper todoMapper;
    private final ModelMapper modelMapper;


    @Override
    public void register(TodoDTO todoDTO) {
        log.info(modelMapper);
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        log.info(todoVO);
        todoMapper.insert(todoVO);
    }

//    @Override
//    public List<TodoDTO> getAll() {
//       List<TodoVO> todoList = todoMapper.selectAll();
//        return todoList.stream()
//               .map(todoVO -> modelMapper.map(todoVO, TodoDTO.class))
//                .sorted(Comparator.comparing(TodoDTO::getTno).reversed())
//                .collect(Collectors.toList());
//    }

    @Override
    public TodoDTO getOne(Long tno) {
        TodoVO todoVO = todoMapper.selectOne(tno);
        return modelMapper.map(todoVO, TodoDTO.class);
    }

    @Override
    public void remove(Long tno) {
        todoMapper.delete(tno);
    }

    @Override
    public void modify(TodoDTO todoDTO) {
        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        todoMapper.update(todoVO);
    }

    @Override
    public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {
        List<TodoVO> voList = todoMapper.selectList(pageRequestDTO);
        List<TodoDTO> dtoList = voList.stream()
                .map(vo->modelMapper.map(vo,TodoDTO.class))
                .collect(Collectors.toList());

        int total = todoMapper.getCount(pageRequestDTO);

        PageResponseDTO<TodoDTO> pageResponseDTO =
                PageResponseDTO.<TodoDTO>withAll()
                        .dtoList(dtoList)
                        .total(total)
                        .pageRequestDTO(pageRequestDTO)
                        .build();

        return pageResponseDTO;
    }
}