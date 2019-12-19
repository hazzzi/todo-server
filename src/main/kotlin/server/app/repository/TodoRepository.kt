package server.app.repository

import org.springframework.data.jpa.repository.JpaRepository
import server.app.domain.model.Todo

interface TodoRepository : JpaRepository<Todo, Long>