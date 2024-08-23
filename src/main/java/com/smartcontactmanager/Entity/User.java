//package com.smartcontactmanager.Entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Entity
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int userId;
//
//    @Column(nullable = false)
//    private String firstName;
//
//    private String lastName;
//    @Column(nullable = false, unique = true)
//    private String email;
//    @Column(nullable = false, length = 10000)
//    private String password;
//
//    @Column(nullable = false, length = 10000)
//    private String phoneNumber;
//
//    private String role;
//    private String profilePic;
//    private boolean emailVerified = false;
//    private boolean phoneNumberVerified = false;
//
//
//    private String providerId;
//
////    @Enumerated(value = EnumType.STRING)
////    // SELF, GOOGLE, FACEBOOK, TWITTER, LINKEDIN, GITHUB
////    private Providers provider = Providers.SELF;
//
//    @Getter(value = AccessLevel.NONE)
//    private boolean active = true;
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    private List<String> rolesList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    private List<Contact> contacts = new ArrayList<>();
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        List<SimpleGrantedAuthority> collect = rolesList.stream().map(s -> new SimpleGrantedAuthority(s))
//                .collect(Collectors.toList());
//        return collect;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return this.active;
//    }
//
//
//}

package com.smartcontactmanager.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)

    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    // private String role;

    private String picture;
    private String cloudinaryImagePublicId;
    private String about;

    private boolean emailVerified = false;

    private boolean phoneNumberVerified = false;

    private String providerId;
    private String emailToken;

    private boolean active = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rolesList = new ArrayList<>();

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch =
    // FetchType.LAZY, orphanRemoval = true)
    // private List<Contact> contacts = new ArrayList<>();

    // add more fields if needed
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return rolesList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

}
