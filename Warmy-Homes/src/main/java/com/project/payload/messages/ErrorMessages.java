package com.project.payload.messages;

public class ErrorMessages {
    private ErrorMessages(){}

    public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education Term not found with id %s";

    public static final String TOUR_REQUEST_NOT_FOUND = "Error: Tour Request not found with id %s";

    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error : User with email %s is already registered";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
    public static final String NOT_FOUND_USER_USERROLE_MESSAGE = "Error: User not found with user-role %s";


    public static final String IMAGE_NOT_FOUND_MESSAGE = "Image not found with id: %s";


}
