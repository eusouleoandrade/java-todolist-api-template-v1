package com.mycompany.javatodolistapitemplatev1.shared.ultils;

public class MsgUltil {

    // COD0001
    public static String INTERNAL_SERVER_ERROR_COD() {
        return "COD0001";
    };

    public static String INTERNAL_SERVER_ERROR_TXT() {
        return "Internal server error.";
    };

    // COD0002
    public static String X0_IS_REQUIRED_COD() {
        return "COD0002";
    }

    public static String X0_IS_REQUIRED_TXT(String param0) {
        return param0 + " is required.";
    }

    // COD0003
    public static String DATA_BASE_SERVER_ERROR_COD() {
        return "COD0003";
    }

    public static String DATA_BASE_SERVER_ERROR_TXT() {
        return "Database server error.";
    }

    // COD0004
    public static String DATA_OF_X0_X1_NOT_FOUND_COD() {
        return "COD0004";
    }

    public static String DATA_OF_X0_X1_NOT_FOUND_TXT(String param0, String param1) {
        return "Data of " + param0 + " " + param1 + " not found.";
    }

    // COD0005
    public static String IDENTIFIER_X0_IS_INVALID_COD() {
        return "COD0005";
    }

    public static String IDENTIFIER_X0_IS_INVALID_TXT(String param0) {
        return "Identifier " + param0 + " is invalid.";
    }

    // COD0006
    public static String FAILED_TO_UPDATE_X0_COD() {
        return "COD0006";
    }

    public static String FAILED_TO_UPDATE_X0_TXT(String entityName) {
        return "Failed to update " + entityName + ".";
    }

    // COD0007
    public static String RESPONSE_FAILED_MESSAGE_COD() {
        return "COD0007";
    }

    public static String RESPONSE_FAILED_MESSAGE_TXT(String entityName) {
        return "Failed to update " + entityName + ".";
    }

    // COD0008
    public static String RESPONSE_SUCCEEDED_MESSAGE_COD() {
        return "COD0008";
    }

    public static String RESPONSE_SUCCEEDED_MESSAGE_TXT() {
        return "Request processed.";
    }

    // COD0009
    public static String OBJECT_X0_IS_NULL_COD() {
        return "COD0009";
    }

    public static String OBJECT_X0_IS_NULL_TXT(String objectName) {
        return "Object " + objectName + " is null.";
    }

    // COD0010
    public static String FAILED_TO_REMOVE_X0_COD() {
        return "COD0010";
    }

    public static String FAILED_TO_REMOVE_X0_TXT(String entityName) {
        return "Failed to remove " + entityName + ".";
    }
}
