package org.skhu.doing.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skhu.doing.todo.dto.TodoRequestDTO;
import org.skhu.doing.todo.dto.TodoResponseDTO;
import org.skhu.doing.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo")
@Tag(name = "TODO", description = "TODO Controller")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "TODO 생성", description = "새로운 TODO를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TODO 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(@RequestBody TodoRequestDTO requestDto) {
        TodoResponseDTO responseDto = todoService.createTodo(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "TODO 상세 조회", description = "특정 TODO를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TODO 조회 성공"),
            @ApiResponse(responseCode = "404", description = "TODO를 찾을 수 없음")
    })
    @GetMapping("/{todoId}/detail")
    public ResponseEntity<TodoResponseDTO> getTodo(@PathVariable("todoId") Long todoId) {
        TodoResponseDTO responseDto = todoService.getTodo(todoId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "TODO 수정", description = "특정 TODO를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TODO 수정 성공"),
            @ApiResponse(responseCode = "404", description = "TODO를 찾을 수 없음")
    })
    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponseDTO> updateTodo(
            @PathVariable("todoId") Long todoId,
            @RequestBody TodoRequestDTO requestDto) {
        TodoResponseDTO responseDto = todoService.updateTodo(todoId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "TODO 삭제", description = "특정 TODO를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TODO 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "TODO를 찾을 수 없음")
    })
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("todoId") Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
}
