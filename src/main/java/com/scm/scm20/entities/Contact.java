package com.scm.scm20.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 512)
    private String description;
    private boolean favorate = false;

    private String websiteLink;
    private String linkedInLink;
    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "contact",cascade = CascadeType.ALL)
    private List<SocailLink> links = new ArrayList<>();

    // private List<SocialLink> socialLink

}
