package com.project.contactmessage.service;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.mapper.ContactMessageMapper;
import com.project.contactmessage.repository.ContactMessageRepository;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    private final ContactMessageMapper contactMessageMapper;

    public ResponseMessage<ContactMessageResponse> create(ContactMessageRequest contactMessageRequest) {

        ContactMessage contactMessage=contactMessageMapper.mapRequestToContactMessage(contactMessageRequest);
        ContactMessage savedContactMessage= contactMessageRepository.save(contactMessage); //id'li pojo class

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")//todo success messages olustur
                .httpStatus(HttpStatus.CREATED)
                .object(contactMessageMapper.mapContactMessageToResponse(savedContactMessage))
                .build();
    }
}
