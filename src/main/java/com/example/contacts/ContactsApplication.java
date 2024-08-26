package com.example.contacts;

import com.example.contacts.model.Contact;
import com.example.contacts.repository.ContactRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootApplication
public class ContactsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactsApplication.class, args);
    }
//    @Bean
//    CommandLineRunner runner(ContactRepo repository) {
//        return args -> {
//
//            Contact contact = new Contact();
//            contact.setName("kieuducmanh");
//
//            repository.save(contact);
////            Optional<Contact> saved = repository.findById(contact.getId());
////            System.out.println(saved);
//        };
//    }
}
