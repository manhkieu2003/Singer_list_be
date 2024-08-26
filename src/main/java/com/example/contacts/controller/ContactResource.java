package com.example.contacts.controller;



import com.example.contacts.model.Contact;
import com.example.contacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;


import static com.example.contacts.constant.Constant.PHOTO_DIRECTORY;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

/**
 * @author Roland Junior Toussaint
 * @version 1.0
 * @since 4/9/2021
 */

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactResource {
    private final ContactService contactService;

    @PostMapping("")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        return ResponseEntity.ok().body(contactService.createContact(contact));
    }

    @GetMapping
    public ResponseEntity<Page<Contact>> getContacts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(contactService.getContactById(id));
    }



    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(contactService.uploadPhoto(id, file));
    }
    @GetMapping(path = "/image/{fileName}",produces = { IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
    public byte[] getPhoto(@PathVariable("fileName") String fileName) throws Exception {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + fileName));
    }

    @PostMapping(value="/create")
    public ResponseEntity<String> createRole(@RequestParam(name = "files") Collection<MultipartFile> files, @RequestParam("name") String name){
        files.forEach(file -> {
            System.out.println(file.getOriginalFilename());
        });
        System.out.println(name);
        return ResponseEntity.ok().body("This is cool");
    }
    @DeleteMapping("/delete")
    public void deleteContact(@RequestParam("id") String id) {
        contactService.deleteContact(id);
    }

}
