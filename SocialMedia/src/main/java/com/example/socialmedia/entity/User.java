package com.example.socialmedia.entity;

import com.example.socialmedia.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String address;
    private String phone;
    private String email;
    private String username;
    private String password;
    private Date createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities ()
    {
        return null;
    }

    @Override
    public String getPassword ()
    {
        return this.password;
    }

    @Override
    public String getUsername ()
    {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired ()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked ()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired ()
    {
        return true;
    }

    @Override
    public boolean isEnabled ()
    {
        return true;
    }
}
