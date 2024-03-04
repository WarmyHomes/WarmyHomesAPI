package com.project.contactmessage.controller;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.service.ContactMessageService;
import com.project.payload.response.business.ResponseMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    //J01:It will create a contact message
    @PostMapping("/create") //http://localhost:8080/contactMessages/create + POST + JSON
    public ResponseMessage<ContactMessageResponse> create(@RequestBody @Valid ContactMessageRequest contactMessageRequest){

        return contactMessageService.create(contactMessageRequest);
    }



    //J02:It will get contact messages
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<ContactMessageResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0")int page,
            @RequestParam(value = "size", defaultValue = "20")int size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type){

        return contactMessageService.getAll(page,size,sort,type);

    }



    //J03: It will get a contact message by given id
    @GetMapping("/getById/{contactMessageId}") //http://localhost:8080/contactMessages/getById/1 + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<ContactMessage> getByIdPath(@PathVariable Long contactMessageId){

        return ResponseEntity.ok(contactMessageService.getContactMessageById(contactMessageId));
    }



    //J04: It will delete a contact message
    @DeleteMapping("/deleteById/{contactMessageId}") //http://localhost:8080/contactMessages/deleteById/1
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<ContactMessageResponse> deleteByIdPath(@PathVariable Long contactMessageId){

        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }




}
