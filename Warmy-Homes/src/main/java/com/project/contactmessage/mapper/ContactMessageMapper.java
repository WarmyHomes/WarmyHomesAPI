package com.project.contactmessage.mapper;

import com.project.contactmessage.dto.ContactMessageRequest;
import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ContactMessageMapper {

    public ContactMessage mapRequestToContactMessage(ContactMessageRequest contactMessageRequest){

        return ContactMessage.builder()
                .first_name(contactMessageRequest.getFirst_name())
                .last_name(contactMessageRequest.getLast_name())
                .email(contactMessageRequest.getEmail())
                .message(contactMessageRequest.getMessage())
                .status(contactMessageRequest.getStatus())
                .date_time(LocalDateTime.now())
                .build();

    }

    public ContactMessageResponse mapContactMessageToResponse(ContactMessage contactMessage){

        return new ContactMessageResponse().toBuilder()
                .id(contactMessage.getId())
                .first_name(contactMessage.getFirst_name())
                .last_name(contactMessage.getLast_name())
                .email(contactMessage.getEmail())
                .message(contactMessage.getMessage())
                .status(contactMessage.getStatus())
                .date_time(contactMessage.getDate_time())
                .build();
    }
}
