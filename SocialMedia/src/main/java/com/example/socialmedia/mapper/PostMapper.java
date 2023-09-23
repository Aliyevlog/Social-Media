package com.example.socialmedia.mapper;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.LikeDao;
import com.example.socialmedia.dao.UserDao;
import com.example.socialmedia.dto.request.CreatePostRequest;
import com.example.socialmedia.dto.request.UpdatePostRequest;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.dto.response.PostResponse;
import com.example.socialmedia.entity.Post;
import com.example.socialmedia.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostMapper {

    private final ModelMapper modelMapper;
    private final LikeDao likeDao;
    private final SecurityConfig securityConfig;
    private final UserDao userDao;

    public void map(UpdatePostRequest source, Post target) {
        modelMapper.map(source, target);
    }

    public PostResponse map(Post source) {
        PostResponse target = modelMapper.map(source, PostResponse.class);
        String fullName = source.getUser().getName() + " " + source.getUser().getSurname();
        target.setFullName(fullName);

        Long likeCount = likeDao.countByPostIdAndReaction(source.getId(), true);
        Long dislikeCount = likeDao.countByPostIdAndReaction(source.getId(), false);
        target.setLikeCount(likeCount);
        target.setDislikeCount(dislikeCount);

        return target;
    }

    public Post map(CreatePostRequest source) throws NotFoundException {
        Post target = modelMapper.map(source, Post.class);
        target.setUser(userDao.getByUsername(securityConfig.getLoggedInUsername()));

        return target;
    }

    public PageResponse<PostResponse> map(Page<Post> source) {
        PageResponse<PostResponse> target = new PageResponse<>();
        List<PostResponse> posts = new ArrayList<>();

        source.forEach(post -> posts.add(map(post)));
        target.setData(posts);
        target.setPage(source.getNumber() + 1);
        target.setPageSize(source.getSize());
        target.setTotalPages(source.getTotalPages());

        return target;
    }
}
