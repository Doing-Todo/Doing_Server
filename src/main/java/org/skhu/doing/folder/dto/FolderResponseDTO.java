package org.skhu.doing.folder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.skhu.doing.entity.Folder;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderResponseDTO {
    private Long id;
    private String name;
    private List<Long> memberIds;

    public static FolderResponseDTO fromEntity(Folder folder) {
        List<Long> memberIds = folder.getMemberFolders().stream()
                .map(memberFolder -> memberFolder.getMember().getId())
                .collect(Collectors.toList());

        return new FolderResponseDTO(
                folder.getId(),
                folder.getName(),
                memberIds
        );
    }
}
