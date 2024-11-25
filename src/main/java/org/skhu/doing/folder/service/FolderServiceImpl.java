package org.skhu.doing.folder.service;

import org.skhu.doing.entity.Folder;
import org.skhu.doing.entity.Member;
import org.skhu.doing.entity.MemberFolder;
import org.skhu.doing.folder.dto.FolderRequestDTO;
import org.skhu.doing.folder.dto.FolderResponseDTO;
import org.skhu.doing.folder.repository.FolderRepository;
import org.skhu.doing.user.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;

    public FolderServiceImpl(FolderRepository folderRepository, MemberRepository memberRepository) {
        this.folderRepository = folderRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public FolderResponseDTO createFolder(FolderRequestDTO requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member를 찾을 수 없습니다. id: " + requestDto.getMemberId()));

        Folder folder = new Folder();
        folder.setName(requestDto.getName());

        MemberFolder memberFolder = new MemberFolder(member, folder);
        folder.getMemberFolders().add(memberFolder);

        Folder savedFolder = folderRepository.save(folder); // Folder 저장
        return FolderResponseDTO.fromEntity(savedFolder);

    }

    @Override
    public FolderResponseDTO getFolderDetail(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder를 찾을 수 없습니다. id: " + folderId));
        return FolderResponseDTO.fromEntity(folder);
    }

    @Override
    public FolderResponseDTO updateFolder(Long folderId, FolderRequestDTO requestDto) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder를 찾을 수 없습니다. id: " + folderId));

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member를 찾을 수 없습니다. id: " + requestDto.getMemberId()));

        folder.setName(requestDto.getName());
        folder.getMemberFolders().clear();
        MemberFolder memberFolder = new MemberFolder(member, folder);
        folder.getMemberFolders().add(memberFolder);

        Folder updatedFolder = folderRepository.save(folder); // Folder 업데이트
        return FolderResponseDTO.fromEntity(updatedFolder);
    }

    @Override
    public void deleteFolder(Long folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder를 찾을 수 없습니다. id: " + folderId));
        folderRepository.delete(folder);
    }

    @Override
    public List<FolderResponseDTO> getFoldersByMember(Long memberId) {
        List<Folder> folders = folderRepository.findByMemberId(memberId);
        if (folders.isEmpty()) {
            throw new IllegalArgumentException("해당 회원의 폴더를 찾을 수 없습니다. memberId: " + memberId);
        }
        return folders.stream()
                .map(FolderResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
