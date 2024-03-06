package com.project.payload.messages;

public class ErrorMessages {


    public static final String ADVERT_ALREADY_EXIST = "Error : Advert is already exist";

    public static final String ADVERT_NOT_FOUND = "Error: Advert is not found" ;

    private ErrorMessages(){}

    public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education Term not found with id %s";

    public static final String TOUR_REQUEST_NOT_FOUND = "Error: Tour Request not found with id %s";

    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error : User with email %s is already registered";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
    public static final String NOT_FOUND_USER_USERROLE_MESSAGE = "Error: User not found with user-role %s";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";
    public static final String USER_CAN_NOT_DELETED = "Error: User cannot be deleted";
    public static final String NOT_VALID_CODE= "The reset code is not valid.";


    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";
    public static final String PASSWORD_NOT_MATCHED = "Your passwords are not matched";

    public static final String IMAGE_NOT_FOUND_MESSAGE = "Image not found with id: %s";

    public static final String FETCH_ADVERT_TYPES_ERROR_MESSAGE = "Error occurred while fetching advert types";

    public static final String ADVERT_BUILD_IN = "Error Advert is Built-in";

    public static final String NOT_RETRIEVE_USER_ID = "Unable to retrieve authenticated user's ID";

    public static final String ADVERT_TYPE_IN_USE_ERROR_MESSAGE="Deletion cannot be performed because this advert type is currently in use";
}
