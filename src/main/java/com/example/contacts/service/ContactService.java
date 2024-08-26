package com.example.contacts.service;

import com.example.contacts.constant.Constant;
import com.example.contacts.model.Contact;
import com.example.contacts.repository.ContactRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ContactService  {
    private final ContactRepo contactRepo;
    // lấy all sắp xeep và phân trang
    public Page<Contact> getAllContacts(int page, int size) {
      return  contactRepo.findAll(PageRequest.of(page,size, Sort.by("name")));
    }
    // lấy 1 contact theo id
    public Contact getContactById(String id) {
        return contactRepo.findById(id).orElse(null);

    }
    // create contact
    public Contact createContact(Contact contact) {
        return contactRepo.save(contact);
    }
    public void deleteContact(String id) {
        contactRepo.deleteById(id);
    }
    public String uploadPhoto(String id, MultipartFile file) {
        Contact contact = getContactById(id);
        String urlphoto =photoFuntion.apply(id, file);
        contact.setPhotoUrl(urlphoto);
        contactRepo.save(contact);
        return urlphoto;
    }
    private final Function<String,String> fileException = filename -> Optional.of(filename).filter(name->name.contains("."))
            .map(name->"."+name.substring(filename.lastIndexOf(".")+1)).orElse(".png");
    private final BiFunction<String,MultipartFile,String> photoFuntion=(id, image)->{
        try
        {
            Path filePath = Paths.get(Constant.PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(filePath))
            {
                Files.createDirectory(filePath);
            }
            Files.copy(image.getInputStream(),filePath.resolve(id + fileException.apply(image.getOriginalFilename())), StandardCopyOption.REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/contacts/image/"+id+fileException.apply(image.getOriginalFilename())).toUriString();
        }catch(Exception exception)
        {
            throw new RuntimeException("Unable to upload photo");
        }
    };




}
