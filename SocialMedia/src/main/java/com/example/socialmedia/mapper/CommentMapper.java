package com.example.socialmedia.mapper;

import com.example.socialmedia.config.SecurityConfig;
import com.example.socialmedia.dao.PostDao;
import com.example.socialmedia.dao.UserDao;
import com.example.socialmedia.dto.request.CreateCommentRequest;
import com.example.socialmedia.dto.request.UpdateCommentRequest;
import com.example.socialmedia.dto.response.CommentResponse;
import com.example.socialmedia.dto.response.PageResponse;
import com.example.socialmedia.entity.Comment;
import com.example.socialmedia.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final ModelMapper modelMapper;
    private final PostDao postDao;
    private final SecurityConfig securityConfig;
    private final UserDao userDao;

    public Comment map(CreateCommentRequest source, Long postId) throws NotFoundException {
        Comment target = modelMapper.map(source, Comment.class);
        target.setUser(userDao.getByUsername(securityConfig.getLoggedInUsername()));
        target.setPost(postDao.getByPostId(postId));

        return target;
    }

    public CommentResponse map(Comment source) {
        CommentResponse target = modelMapper.map(source, CommentResponse.class);
        String fullName = source.getUser().getName() + " " + source.getUser().getSurname();
        target.setFullName(fullName);

        return target;
    }

    public void map(UpdateCommentRequest source, Comment target) {
        modelMapper.map(source, target);
    }

    public PageResponse<CommentResponse> map(Page<Comment> source) {
        PageResponse<CommentResponse> pageResponse = new PageResponse<>();
        List<CommentResponse> commentResponses = new ArrayList<>();

        source.forEach(comment -> commentResponses.add(map(comment)));
        pageResponse.setData(commentResponses);
        pageResponse.setPage(source.getNumber() + 1);
        pageResponse.setPageSize(source.getSize());
        pageResponse.setTotalPages(source.getTotalPages());

        return pageResponse;
    }
}
