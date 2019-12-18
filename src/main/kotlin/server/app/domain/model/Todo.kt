package server.app.domain.model

import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Todo constructor(

    @Id
    @GeneratedValue
    var todoId: Long,

    @Column(nullable = true)
    val contents: String,

//    @org.hibernate.annotations.Type(type = "server.base.hibernate.type.ZeroOrOneType")
    @Column(nullable = false)
    val isComplete: Boolean,

    @LastModifiedDate
    @Column(nullable = false)
    var createTimestamp: Instant? = null,

    @LastModifiedDate
    @Column(nullable = false)
    var changeTimestamp: Instant? = null
)
