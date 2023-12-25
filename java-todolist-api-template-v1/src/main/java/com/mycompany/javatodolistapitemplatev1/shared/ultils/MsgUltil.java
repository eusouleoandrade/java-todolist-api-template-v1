package com.mycompany.javatodolistapitemplatev1.shared.ultils;

import io.micrometer.common.lang.Nullable;

public class MsgUltil {

    // COD0001
    public static String[] INTERNAL_SERVER_ERROR() {
        return new String[] { "COD0001", "Internal server error." };
    };

    // COD0002
    public static String[] X0_IS_REQUIRED(String param0) {
        return new String[] { "COD0002", String.format("%s is required.", param0) };
    }

    // COD0003
    public static String[] DATA_BASE_SERVER_ERROR() {
        return new String[] { "COD0003", "Database server error." };
    }

    // COD0004
    public static String[] DATA_OF_X0_X1_NOT_FOUND(@Nullable String param0, @Nullable String param1) {
        return new String[] { "COD0004", String.format("Data of %s %s not found.", param0, param1) };
    }

    // COD0005
    public static String[] IDENTIFIER_X0_IS_INVALID(@Nullable String param0) {
        return new String[] { "COD0005", String.format("Identifier %s is invalid.", param0) };
    }

    // COD0006
    public static String[] FAILED_TO_UPDATE_X0(@Nullable String entityName) {
        return new String[] { "COD0006", String.format("Failed to update %s.", entityName) };
    }

    // COD0007
    public static String[] RESPONSE_SUCCEEDED_MESSAGE() {
        return new String[] { "COD0007", "Request processed." };
    }

    // COD0008
    public static String[] OBJECT_X0_IS_NULL(@Nullable String objectName) {
        return new String[] { "COD0008", String.format("Object %s is null.", objectName) };
    }

    // COD0009
    public static String[] FAILED_TO_REMOVE_X0(@Nullable String entityName) {
        return new String[] { "COD0009", String.format("Failed to remove %s.", entityName) };
    }

    // COD0010
    public static String[] RESPONSE_FAILED_PROCESS_REQUEST() {
        return new String[] { "COD0010", "Failed to process the request." };
    }

    // COD0011
    public static String[] BAD_REQUEST() {
        return new String[] { "COD0011", "Bad request." };
    }
}
