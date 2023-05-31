package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskMapperTest {

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void MapperTaskTest() {
        TaskDto taskDto = new TaskDto(1L,"test1","test1");

        Task task = taskMapper.mapToTask(taskDto);

        assertEquals(task.getId(),taskDto.getId());
    }
    @Test
    public void MapperTaskTestDto() {
        Task task = new Task(2L,"test2","test2");

        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        assertEquals(taskDto.getTitle(), task.getTitle());
    }
    @Test
    public void MapperTaskTestList() {
        Task task = new Task(3L,"test3","test3");

        List<Task> taskList = new ArrayList<>();

        taskList.add(task);

        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        assertEquals(taskDtos.get(0).getContent(),task.getContent());
    }

}
