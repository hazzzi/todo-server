package server.app.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import server.app.domain.model.Todo
import server.app.domain.vo.TodoVo
import server.app.repository.TodoRepository

@Service
class TodoService(
    val todoRepository: TodoRepository
) {
    @Transactional
    fun save(todo: TodoVo): Todo {
        return todoRepository.save(Todo(
            todoId = null,
            contents = todo.contents,
            isComplete = todo.isComplete
        ))
    }

    fun findAll(pageable: Pageable): Page<Todo> {
        return todoRepository.findAll(pageable)
    }

    fun findById(id: Long): Todo? {
        return todoRepository.findByIdOrNull(id)
    }

    @Transactional
    fun updateTodoById(id: Long, todo: TodoVo): Todo? {
        val oldTodo = todoRepository.findByIdOrNull(id) ?: return null

        return todoRepository.save(oldTodo.copy(
            contents = todo.contents,
            isComplete = todo.isComplete
        ))
    }

    @Transactional
    fun deleteTodoById(id: Long) {
        todoRepository.deleteById(id)
    }
}