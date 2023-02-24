package com.neohackathon.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "student")
@SequenceGenerator(name = "student_id", sequenceName = "student_id_seq", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn
                    (name = "role_id",
                            referencedColumnName = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String firstName, String lastName,
                String email, String password,
                Collection<Role> roles) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

}
