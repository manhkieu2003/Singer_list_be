package com.example.contacts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @UuidGenerator
   @Column(name = "id",unique = true,nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // cột lafkhoasas chính , cột giá trị duy nhất unique , và không được null
    private String id;
    private String name;
    private String email;
    private String title;
    private String phone;
    private String address;
    private String status;
    private String photoUrl;
}
