package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    void getTasksTest() throws Exception {
        when(taskMapper.mapToTaskDtoList(dbService.getAllTasks())).thenReturn(List.of());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }
    @Test
    void getTaskTest() throws Exception {
        List<TaskDto> taskListDto = new ArrayList<>();
        taskListDto.add(new TaskDto(1L, "test", "test"));

        when(taskMapper.mapToTaskDto(dbService.getTask(1L))).thenReturn(taskListDto.get(0));

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test")));
    }

    @Test
    void deleteTaskTest() throws Exception {
        Task task = new Task(1L,"test","test");

        when(dbService.getTask(1L)).thenReturn(task);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void updateTaskTest() throws Exception {
        TaskDto taskDto1 = new TaskDto(1L, "test", "test");
        TaskDto taskDto2 = new TaskDto(1L, "test2", "test2");

        Task task1 = new Task(1L, "test1", "test1");
        Task task2 = new Task(1L, "test2", "test2");

        when(taskMapper.mapToTask(taskDto1)).thenReturn(task1);
        when(dbService.saveTask(task1)).thenReturn(task1);
        when(taskMapper.mapToTaskDto(dbService.saveTask(task2))).thenReturn(taskDto2);

        Gson gson = new Gson();
        String content = gson.toJson(taskDto1);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(content))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test2")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("test2")));
    }
    @Test
    void createTaskTest() throws Exception {
        TaskDto taskDto = new TaskDto(1L, "test", "test");
        Task task = new Task(1L, "test", "test");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);

        Gson gson = new Gson();
        String content = gson.toJson(taskDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(content))
                .andExpect(status().is(200));
    }

}
