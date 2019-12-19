package server.app.web.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import server.app.domain.vo.TodoVo
import server.app.service.TodoService

@RestController
@RequestMapping("/api/todos")
class TodoController @Autowired constructor(
    val todoService: TodoService
){

    /**
     * todo 하나 등록
     */
    @PostMapping
    fun createTodo(@RequestBody todo: TodoVo) : ResponseEntity<Any>{
        val newTodo = todoService.save(todo)
        val createUri = linkTo(TodoController::class.java).slash(newTodo.todoId).toUri()
        return ResponseEntity.created(createUri).body(newTodo)
    }

    /**
     * todo 목록 조회
     */
    @GetMapping
    fun findTodoList(pageable: Pageable) : ResponseEntity<Any>{
        val todoList = todoService.findAll(pageable)
        return ResponseEntity.ok().body(todoList)
    }

    /**
     * todo 하나 조회
     */
    @GetMapping("/{id}")
    fun findTodoById(@PathVariable id: Long) : ResponseEntity<Any> {
        val findTodo = todoService.findById(id)
        return ResponseEntity.ok().body(findTodo)
    }

    /**
     * todo 수정
     */
    @PutMapping("/{id}")
    fun updateTodoById(@PathVariable id: Long, @RequestBody todo: TodoVo) : ResponseEntity<Any> {
        val updateTodo = todoService.updateTodoById(id, todo)

        return ResponseEntity.ok().body(updateTodo)
    }

    /**
     * todo 삭제
     */
    @DeleteMapping("/{id}")
    fun deleteTodoById(@PathVariable id:Long) : ResponseEntity<Any> {
        todoService.deleteTodoById(id)
        return ResponseEntity.ok().build()
    }
}