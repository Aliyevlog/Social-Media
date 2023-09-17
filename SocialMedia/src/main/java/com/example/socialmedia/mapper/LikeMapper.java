package com.example.socialmedia.mapper;

import com.example.socialmedia.dto.response.LikeResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortLikeResponse;
import com.example.socialmedia.entity.Like;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeMapper {
    private final ModelMapper modelMapper;
    private final PostMapper postMapper;

    public LikeResponse map(Like source) {
        LikeResponse target = modelMapper.map(source, LikeResponse.class);
        target.setUsername(source.getUser().getUsername());
        target.setPostResponse(postMapper.map(source.getPost()));

        return target;
    }

    public ShortLikeResponse mapToShortLikeResponse(Like source) {
        ShortLikeResponse target = new ShortLikeResponse();
        target.setUserId(source.getUser().getId());
        target.setFullName(source.getUser().getName() + " " + source.getUser().getSurname());

        return target;
    }

    public PageResponse<ShortLikeResponse> map(Page<Like> source) {
        PageResponse<ShortLikeResponse> target = new PageResponse<>();
        List<ShortLikeResponse> shortLikeResponses = new ArrayList<>();

        for (Like like : source) {
            ShortLikeResponse shortLikeResponse = mapToShortLikeResponse(like);
            shortLikeResponses.add(shortLikeResponse);
        }
        target.setData(shortLikeResponses);
        target.setPage(source.getNumber() + 1);
        target.setPageSize(source.getSize());
        target.setTotalPages(source.getTotalPages());

        return target;
    }
}
