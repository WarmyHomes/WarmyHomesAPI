package com.project.contactmessage.service;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.exception.ResourceNotFoundException;
import com.project.contactmessage.mapper.ContactMessageMapper;
import com.project.contactmessage.message.Messages;
import com.project.contactmessage.repository.ContactMessageRepository;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    private final ContactMessageMapper contactMessageMapper;

    private final PageableHelper pageableHelper;



    //J01
    public ResponseMessage<ContactMessageResponse> create(ContactMessageRequest contactMessageRequest) {

        ContactMessage contactMessage=contactMessageMapper.mapRequestToContactMessage(contactMessageRequest);
        ContactMessage savedContactMessage= contactMessageRepository.save(contactMessage); //id'li pojo class

        return ResponseMessage.<ContactMessageResponse>builder()
                .httpStatus(HttpStatus.CREATED)
                .object(contactMessageMapper.mapContactMessageToResponse(savedContactMessage))
                .build();
    }


    //J02
    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        return contactMessageRepository.findAll(pageable).map(contactMessageMapper::mapContactMessageToResponse);
    }


    //J03
    public ContactMessage getContactMessageById(Long contactMessageId) { //delete methodunda bizim pojo döndürmemiz lazım, dto olmaz.o yüzden pojo döndürdük

        return  contactMessageRepository.findById(contactMessageId)
                .map(message -> {
                    message.setStatus(1); // Set status to 1
                    return message; //returns the updated ContactMessage object.
                })
                .orElseThrow(()->
                new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));

        //retrieve the ContactMessage object by its ID using findById.
        //then use map to update the status of the retrieved ContactMessage object.
        //return the updated ContactMessage object.
        //If the ContactMessage object doesn't exist, we throw a ResourceNotFoundException

    }


    //J04
    public ContactMessageResponse deleteById(Long contactMessageId) {

        ContactMessage contactMessage = getContactMessageById(contactMessageId);
        contactMessageRepository.delete(contactMessage);
        return contactMessageMapper.mapContactMessageToResponse(contactMessage);

    }


}
