package com.example.springsecurityjwt.users;

import com.example.springsecurityjwt.BaseEntity;
import com.example.springsecurityjwt.security.AuthorityType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "TBL_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(nullable = false, length = 20)
    private String name;

    private String email;

    @Column(nullable = false)
    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserType type;

    @ElementCollection(targetClass = AuthorityType.class)
    @CollectionTable(name = "TBL_USER_AUTHORITY", joinColumns = @JoinColumn(name="USER_ID"))
    @Enumerated(EnumType.STRING)
    private List<AuthorityType> authorities = new ArrayList<>();

    @Builder
    public User(String username, String name, String email, String password, UserType type){
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities.add(AuthorityType.ROLE_MEMBER);
        this.type = type;
    }

    public String getUsername(){
        return email;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).collect(Collectors.toList());
    }

}