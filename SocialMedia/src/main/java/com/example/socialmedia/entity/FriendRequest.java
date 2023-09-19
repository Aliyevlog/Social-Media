package com.example.socialmedia.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "friend_requests")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne
    private User sender;

    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @ManyToOne
    private User receiver;
}
