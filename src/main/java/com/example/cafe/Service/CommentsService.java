package com.example.cafe.Service;

import com.example.cafe.Repository.Comments.CommentsRepository;
import com.example.cafe.dto.ArtistDto;
import com.example.cafe.dto.CommentsDto;
import com.example.cafe.dto.CommunityDto;
import com.example.cafe.entity.Artist;
import com.example.cafe.entity.Comments;
import com.example.cafe.entity.Community;
import com.example.cafe.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsService {
    private final CommentsRepository commentsRepository;

    public void saveComments(CommentsDto commentsDto) {
        // Dto -> Entity
        Comments comments = new Comments();
        comments.setCommentsContent(commentsDto.getCommentsContent());
        comments.setCommentsAuthor(commentsDto.getCommentsAuthor());
        if(commentsDto.getParentCpost() != null) {
            // Community Dto -> Entity
            Community community = new Community();
            community.setCommunityId(commentsDto.getParentCpost().getCommunityId());
            community.setCommunityTitle(commentsDto.getParentCpost().getCommunityTitle());
            community.setCommunityContent(commentsDto.getParentCpost().getCommunityContent());
            community.setCommunityAuthor(commentsDto.getParentCpost().getCommunityAuthor());
            community.setCreateDate(commentsDto.getParentCpost().getCreateDate());
            community.setUpdateDate(commentsDto.getParentCpost().getUpdateDate());

            comments.setParentCpost(community);
        } else {
            // Artist Dto -> Entity
            Artist artist = new Artist();
            artist.setArtistId(commentsDto.getParentApost().getArtistId());
            artist.setArtistImage(commentsDto.getParentApost().getArtistImage());
            artist.setArtistImagePath(commentsDto.getParentApost().getArtistImagePath());
            artist.setArtistContent(commentsDto.getParentApost().getArtistContent());
            artist.setCreateDate(commentsDto.getParentApost().getCreateDate());
            artist.setUpdateDate(commentsDto.getParentApost().getUpdateDate());

            comments.setParentApost(artist);
        }

        commentsRepository.save(comments);
    }

    public void modifyComments(CommentsDto commentsDto) {
        // Dto -> Entity
        Comments comments = new Comments();
        comments.setCommentsId(commentsDto.getCommentsId());
        comments.setCommentsContent(commentsDto.getCommentsContent());
        comments.setCommentsAuthor(commentsDto.getCommentsAuthor());
        if(commentsDto.getParentCpost() != null) {
            // Community Dto -> Entity
            Community community = new Community();
            community.setCommunityId(commentsDto.getParentCpost().getCommunityId());
            community.setCommunityTitle(commentsDto.getParentCpost().getCommunityTitle());
            community.setCommunityContent(commentsDto.getParentCpost().getCommunityContent());
            community.setCommunityAuthor(commentsDto.getParentCpost().getCommunityAuthor());
            community.setCreateDate(commentsDto.getParentCpost().getCreateDate());
            community.setUpdateDate(commentsDto.getParentCpost().getUpdateDate());

            comments.setParentCpost(community);
        } else {
            // Artist Dto -> Entity
            Artist artist = new Artist();
            artist.setArtistId(commentsDto.getParentApost().getArtistId());
            artist.setArtistImage(commentsDto.getParentApost().getArtistImage());
            artist.setArtistImagePath(commentsDto.getParentApost().getArtistImagePath());
            artist.setArtistContent(commentsDto.getParentApost().getArtistContent());
            artist.setCreateDate(commentsDto.getParentApost().getCreateDate());
            artist.setUpdateDate(commentsDto.getParentApost().getUpdateDate());

            comments.setParentApost(artist);
        }

        commentsRepository.save(comments);
    }


    public void deleteComments(Long commentsId) {
        commentsRepository.deleteById(commentsId);
    }

    public Page<CommentsDto> findByMemberId(Member member, String postType, Pageable pageable) {
        List<CommentsDto> commentsDtos = new ArrayList<>();
        if(postType.equals("C")) {  // Community 댓글만 가져오기
            Page<Comments> comments = commentsRepository.findCByMemberId(member, pageable);

            if(comments != null) {
                for(Comments c : comments) {
                    CommunityDto communityDto = CommunityDto.builder()
                            .communityId(c.getParentCpost().getCommunityId())
                            .communityTitle(c.getParentCpost().getCommunityTitle())
                            .communityContent(c.getParentCpost().getCommunityContent())
                            .communityAuthor(c.getParentCpost().getCommunityAuthor())
                            .createDate(c.getCreateDate())
                            .updateDate(c.getUpdateDate())
                            .build();

                    CommentsDto dto = CommentsDto.builder()
                            .commentsId(c.getCommentsId())
                            .commentsContent(c.getCommentsContent())
                            .commentsAuthor(c.getCommentsAuthor())
                            .parentCpost(communityDto)
                            .createDate(c.getCreateDate())
                            .updateDate(c.getUpdateDate())
                            .build();

                    commentsDtos.add(dto);  // 리스트에 추가
                }
            }
            return new PageImpl<>(commentsDtos, comments.getPageable(), comments.getTotalElements());
        } else {    // Artist 댓글만 가져오기
            Page<Comments> comments = commentsRepository.findAByMemberId(member, pageable);

            if(comments != null) {
                for(Comments c : comments) {
                    ArtistDto artist = ArtistDto.builder()
                            .artistId(c.getParentApost().getArtistId())
                            .artistContent(c.getParentApost().getArtistContent())
                            .artistImage(c.getParentApost().getArtistImage())
                            .artistImagePath(c.getParentApost().getArtistImagePath())
                            .createDate(c.getCreateDate())
                            .updateDate(c.getUpdateDate())
                            .build();

                    CommentsDto dto = CommentsDto.builder()
                            .commentsId(c.getCommentsId())
                            .commentsContent(c.getCommentsContent())
                            .commentsAuthor(c.getCommentsAuthor())
                            .parentApost(artist)
                            .createDate(c.getCreateDate())
                            .updateDate(c.getUpdateDate())
                            .build();

                    commentsDtos.add(dto);  // 리스트에 추가
                }
            }
            return new PageImpl<>(commentsDtos, comments.getPageable(), comments.getTotalElements());
        }
    }

    public CommentsDto findByCommentsId(Long commentsId, String type) {
        Optional<Comments> comments = commentsRepository.findById(commentsId);
        CommentsDto commentsDto = new CommentsDto();
        if(comments.isPresent()) {
            if(type.equals("C")) {
                CommunityDto communityDto = new CommunityDto().builder()
                        .communityId(comments.get().getParentCpost().getCommunityId())
                        .communityTitle(comments.get().getParentCpost().getCommunityTitle())
                        .communityContent(comments.get().getParentCpost().getCommunityContent())
                        .communityAuthor(comments.get().getParentCpost().getCommunityAuthor())
                        .createDate(comments.get().getCreateDate())
                        .updateDate(comments.get().getUpdateDate())
                        .build();

                commentsDto = new CommentsDto().builder()
                        .commentsId(comments.get().getCommentsId())
                        .commentsContent(comments.get().getCommentsContent())
                        .parentCpost(communityDto)
                        .commentsAuthor(comments.get().getCommentsAuthor())
                        .createDate(comments.get().getCreateDate())
                        .updateDate(comments.get().getUpdateDate())
                        .build();
            } else {
                ArtistDto artistDto = new ArtistDto().builder()
                        .artistId(comments.get().getParentApost().getArtistId())
                        .artistContent(comments.get().getParentApost().getArtistContent())
                        .artistImage(comments.get().getParentApost().getArtistImage())
                        .artistImagePath(comments.get().getParentApost().getArtistImagePath())
                        .createDate(comments.get().getCreateDate())
                        .updateDate(comments.get().getUpdateDate())
                        .build();

                commentsDto = new CommentsDto().builder()
                        .commentsId(comments.get().getCommentsId())
                        .commentsContent(comments.get().getCommentsContent())
                        .parentApost(artistDto)
                        .commentsAuthor(comments.get().getCommentsAuthor())
                        .createDate(comments.get().getCreateDate())
                        .updateDate(comments.get().getUpdateDate())
                        .build();
            }
        }
        return commentsDto;
    }
}
