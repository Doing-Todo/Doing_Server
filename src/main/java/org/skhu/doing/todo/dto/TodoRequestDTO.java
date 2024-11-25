package org.skhu.doing.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.skhu.doing.entity.Todo;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDTO {
    private String description;
    private Todo.Status status;
    private Long folderId;
}
