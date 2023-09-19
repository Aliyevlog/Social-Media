package com.example.socialmedia.mapper;

import com.example.socialmedia.dto.response.FriendRequestResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.ShortFriendRequestResponse;
import com.example.socialmedia.entity.FriendRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendRequestMapper {
    private final ModelMapper modelMapper;

    public FriendRequestResponse map(FriendRequest source) {
        FriendRequestResponse target = modelMapper.map(source, FriendRequestResponse.class);
        target.setSenderFullName(source.getSender().getName() + " " + source.getSender().getSurname());
        target.setReceiverFullName(source.getReceiver().getName() + " " + source.getReceiver().getSurname());

        return target;
    }

    public ShortFriendRequestResponse mapShortFriendRequestResponse(FriendRequest source) {
        ShortFriendRequestResponse target = modelMapper.map(source, ShortFriendRequestResponse.class);
        target.setFullName(source.getSender().getName() + " " + source.getSender().getSurname());

        return target;
    }

    public PageResponse<ShortFriendRequestResponse> map(Page<FriendRequest> source) {
        PageResponse<ShortFriendRequestResponse> target = new PageResponse<>();
        List<ShortFriendRequestResponse> responses = new ArrayList<>();

        source.forEach(friendRequest -> responses.add(mapShortFriendRequestResponse(friendRequest)));
        target.setData(responses);
        target.setPage(source.getNumber() + 1);
        target.setPageSize(source.getSize());
        target.setTotalPages(source.getTotalPages());

        return target;
    }
}
