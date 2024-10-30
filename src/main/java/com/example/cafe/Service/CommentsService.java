package com.example.cafe.Service;

import com.example.cafe.Repository.Comments.CommentsRepository;
import com.example.cafe.dto.CommentsDto;
import com.example.cafe.entity.Artist;
import com.example.cafe.entity.Comments;
import com.example.cafe.entity.Community;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
