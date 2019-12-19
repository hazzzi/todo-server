package server.app.web.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import server.app.domain.model.Todo
import server.app.domain.vo.TodoVo

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `todo 생성 테스트`() {
        val todo = Todo(
            todoId = null,
            contents = "오늘의 할 일",
            isComplete = false
        )

        mockMvc.perform(post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(todo)))
            .andDo(print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `todo 목록 조회 테스트`() {
        mockMvc.perform(get("/api/todos")
            .param("page", "0")
            .param("size", "10")
            .param("sort", "todoId,DESC"))
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun `todo 조회 테스트`() {
        mockMvc.perform(get("/api/todos/1"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("todoId").value("1"))
    }

    @Test
    fun `todo 수정 테스트`() {
        val updateData = TodoVo(contents = "todo update test", isComplete = true)

        mockMvc.perform(put("/api/todos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateData)))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("todoId").value("1"))
    }

    @Test
    fun `todo 삭제 테스트`() {
        mockMvc.perform(delete("/api/todos/1"))
            .andDo(print())
            .andExpect(status().isOk)
    }
}