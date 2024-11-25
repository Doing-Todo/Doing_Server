package org.skhu.doing.memo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skhu.doing.memo.dto.MemoRequestDTO;
import org.skhu.doing.memo.dto.MemoResponseDTO;
import org.skhu.doing.memo.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memo")
@Tag(name = "Memo", description = "Memo Controller")
public class MemoController {

    private final MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @Operation(summary = "메모 생성", description = "새로운 메모를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "메모 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<MemoResponseDTO> createMemo(@RequestBody MemoRequestDTO requestDto) {
        MemoResponseDTO responseDto = memoService.createMemo(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "메모 상세 조회", description = "특정 메모를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메모 조회 성공"),
            @ApiResponse(responseCode = "404", description = "메모를 찾을 수 없음")
    })
    @GetMapping("/{memoId}/detail")
    public ResponseEntity<MemoResponseDTO> getMemo(@PathVariable("memoId") Long memoId) {
        MemoResponseDTO responseDto = memoService.getMemo(memoId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "메모 수정", description = "특정 메모를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메모 수정 성공"),
            @ApiResponse(responseCode = "404", description = "메모를 찾을 수 없음")
    })
    @PutMapping("/{memoId}")
    public ResponseEntity<MemoResponseDTO> updateMemo(
            @PathVariable("memoId") Long memoId,
            @RequestBody MemoRequestDTO requestDto) {
        MemoResponseDTO responseDto = memoService.updateMemo(memoId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "메모 삭제", description = "특정 메모를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "메모 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "메모를 찾을 수 없음")
    })
    @DeleteMapping("/{memoId}")
    public ResponseEntity<Void> deleteMemo(@PathVariable("memoId") Long memoId) {
        memoService.deleteMemo(memoId);
        return ResponseEntity.noContent().build();
    }
}
