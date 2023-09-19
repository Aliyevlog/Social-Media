package com.example.socialmedia.mapper;

import com.example.socialmedia.dto.response.FriendResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendResponse;
import com.example.socialmedia.entity.Friend;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendMapper {
    private final ModelMapper modelMapper;

    public FriendResponse map(Friend source) {
        FriendResponse target = modelMapper.map(source, FriendResponse.class);
        target.setUser1FullName(source.getUser1().getName() + " " + source.getUser1().getSurname());
        target.setUser2FullName(source.getUser2().getName() + " " + source.getUser2().getSurname());

        return target;
    }

    public ShortFriendResponse mapToShortResponse(Friend source, Long userId) {
        ShortFriendResponse target = modelMapper.map(source, ShortFriendResponse.class);
        if (source.getUser1().getId().equals(userId))
            target.setFullName(source.getUser2().getName() + " " + source.getUser2().getSurname());
        else
            target.setFullName(source.getUser1().getName() + " " + source.getUser1().getSurname());

        return target;
    }

    public PageResponse<ShortFriendResponse> map(Page<Friend> source, Long userId) {
        PageResponse<ShortFriendResponse> target = new PageResponse<>();
        List<ShortFriendResponse> friendResponses = new ArrayList<>();

        source.forEach(friend -> friendResponses.add(mapToShortResponse(friend, userId)));
        target.setData(friendResponses);
        target.setPage(source.getNumber() + 1);
        target.setPageSize(source.getSize());
        target.setTotalPages(source.getTotalPages());

        return target;
    }
}
