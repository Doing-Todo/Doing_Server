package org.skhu.doing.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.skhu.doing.entity.Folder;
import org.skhu.doing.entity.Todo;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDTO {
    private Long id;
    private String description;
    private Todo.Status status;
    private Folder folder;  // Folder 객체로 변경
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TodoResponseDTO fromEntity(Todo todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getDescription(),
                todo.getStatus(),
                todo.getFolder(),
                todo.getCreatedAt(),
                todo.getUpdatedAt()
        );
    }
}
