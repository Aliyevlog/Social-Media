package com.example.socialmedia.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user1_id", referencedColumnName = "id")
    @ManyToOne
    private User user1;

    @JoinColumn(name = "user2_id", referencedColumnName = "id")
    @ManyToOne
    private User user2;
}
