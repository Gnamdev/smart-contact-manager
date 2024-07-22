package com.smartcontactmanager.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    private String link;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Contact contact;
}
