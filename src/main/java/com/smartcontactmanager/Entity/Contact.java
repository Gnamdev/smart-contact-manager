package com.smartcontactmanager.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    private String address;

    private String description;
    private boolean favorite = false;

    private String linkedInLink;
    private String picture;

    private String cloudinaryImagePublicId;

    private String userId;

    @ManyToOne
    private User user;

    // @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch =
    // FetchType.EAGER, orphanRemoval = true)

    // @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, orphanRemoval =
    // true)

    // private List<SocialLinks> links = new ArrayList<>();

}
