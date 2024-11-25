package org.skhu.doing.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skhu.doing.entity.Folder;
import org.skhu.doing.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
@Tag(name = "Folder", description = "Folder Controller")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @Operation(summary = "폴더 생성", description = "폴더를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "폴더 생성 성공")
    })
    @PostMapping
    public ResponseEntity<Folder> createFolder(@RequestBody Folder folder) {
        Folder createdFolder = folderService.createFolder(folder);
        return new ResponseEntity<>(createdFolder, HttpStatus.CREATED);
    }

    @Operation(summary = "폴더 아이디 검색", description = "폴더 아이디로 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이디 검색 성공")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Folder> getFolderById(@PathVariable Long id) {
        Folder folder = folderService.getFolderById(id);
        return new ResponseEntity<>(folder, HttpStatus.OK);
    }

    @Operation(summary = "멤버 아이디로 검색", description = "멤버 아이디로 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "멤버 아이디로 검색 성공")
    })
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Folder>> getFoldersByMemberId(@PathVariable Long memberId) {
        List<Folder> folders = folderService.getFoldersByMemberId(memberId);
        return new ResponseEntity<>(folders, HttpStatus.OK);
    }

    @Operation(summary = "폴더 업데이트", description = "폴더 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "폴더 업데이트 성공")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Folder> updateFolder(@PathVariable Long id, @RequestBody Folder folder) {
        Folder updatedFolder = folderService.updateFolder(id, folder);
        return new ResponseEntity<>(updatedFolder, HttpStatus.OK);
    }

    @Operation(summary = "폴더 삭제", description = "폴더 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "폴더 삭제 성공")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolderById(@PathVariable Long id) {
        folderService.deleteFolderById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
