package org.skhu.doing.folder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skhu.doing.folder.dto.FolderRequestDTO;
import org.skhu.doing.folder.dto.FolderResponseDTO;
import org.skhu.doing.folder.service.FolderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/folder")
@Tag(name = "Folder", description = "Folder Controller")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @Operation(summary = "폴더 생성", description = "새로운 폴더를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "폴더 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping
    public ResponseEntity<FolderResponseDTO> createFolder(@RequestBody FolderRequestDTO folderRequestDTO) {
        FolderResponseDTO response = folderService.createFolder(folderRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "폴더 상세 조회", description = "특정 폴더의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "폴더 조회 성공"),
            @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음")
    })
    @GetMapping("/{folder-id}/detail")
    public ResponseEntity<FolderResponseDTO> getFolderDetail(@PathVariable("folder-id") Long folderId) {
        FolderResponseDTO response = folderService.getFolderDetail(folderId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "폴더 수정", description = "특정 폴더의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "폴더 수정 성공"),
            @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음")
    })
    @PutMapping("/{folder-id}")
    public ResponseEntity<FolderResponseDTO> updateFolder(@PathVariable("folder-id") Long folderId,
                                                          @RequestBody FolderRequestDTO folderRequestDTO) {
        FolderResponseDTO response = folderService.updateFolder(folderId, folderRequestDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "폴더 삭제", description = "특정 폴더를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "폴더 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음")
    })
    @DeleteMapping("/{folder-id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable("folder-id") Long folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "특정 회원의 모든 폴더 조회", description = "특정 회원에 속한 모든 폴더를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원의 폴더 조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음")
    })
    @GetMapping("/member/{member-id}")
    public ResponseEntity<List<FolderResponseDTO>> getFoldersByMember(@PathVariable("member-id") Long memberId) {
        List<FolderResponseDTO> response = folderService.getFoldersByMember(memberId);
        return ResponseEntity.ok(response);
    }
}