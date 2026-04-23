package com.mycompany.javatodolistapitemplatev1.shared.ultils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MsgUtilTest {

    @DisplayName("Should return INTERNAL_SERVER_ERROR message")
    @Test
    public void shouldReturnInternalServerErrorMessage() {

        // Arrange
        // Act
        var result = MsgUltil.INTERNAL_SERVER_ERROR();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0001");
        assertThat(result[1]).isEqualTo("Internal server error.");
    }

    @DisplayName("Should return X0_IS_REQUIRED message with parameter")
    @ParameterizedTest
    @ValueSource(strings = { "Title", "Field1", "Parameter" })
    public void shouldReturnX0IsRequiredMessage(String param) {

        // Arrange
        // Act
        var result = MsgUltil.X0_IS_REQUIRED(param);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0002");
        assertThat(result[1]).isEqualTo(param + " is required.");
    }

    @DisplayName("Should return DATABASE_SERVER_ERROR message")
    @Test
    public void shouldReturnDatabaseServerErrorMessage() {

        // Arrange
        // Act
        var result = MsgUltil.DATA_BASE_SERVER_ERROR();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0003");
        assertThat(result[1]).isEqualTo("Database server error.");
    }

    @DisplayName("Should return DATA_OF_X0_X1_NOT_FOUND message with parameters")
    @Test
    public void shouldReturnDataOfX0X1NotFoundMessage() {

        // Arrange
        String param0 = "Todo";
        String param1 = "123";

        // Act
        var result = MsgUltil.DATA_OF_X0_X1_NOT_FOUND(param0, param1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0004");
        assertThat(result[1]).isEqualTo("Data of " + param0 + " " + param1 + " not found.");
    }

    @DisplayName("Should return IDENTIFIER_X0_IS_INVALID message with parameter")
    @Test
    public void shouldReturnIdentifierX0IsInvalidMessage() {

        // Arrange
        String param = "999";

        // Act
        var result = MsgUltil.IDENTIFIER_X0_IS_INVALID(param);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0005");
        assertThat(result[1]).isEqualTo("Identifier " + param + " is invalid.");
    }

    @DisplayName("Should return FAILED_TO_UPDATE_X0 message with entity name")
    @Test
    public void shouldReturnFailedToUpdateX0Message() {

        // Arrange
        String entityName = "Todo";

        // Act
        var result = MsgUltil.FAILED_TO_UPDATE_X0(entityName);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0006");
        assertThat(result[1]).isEqualTo("Failed to update " + entityName + ".");
    }

    @DisplayName("Should return RESPONSE_SUCCEEDED_MESSAGE")
    @Test
    public void shouldReturnResponseSucceededMessage() {

        // Arrange
        // Act
        var result = MsgUltil.RESPONSE_SUCCEEDED_MESSAGE();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0007");
        assertThat(result[1]).isEqualTo("Request processed.");
    }

    @DisplayName("Should return OBJECT_X0_IS_NULL message with object name")
    @Test
    public void shouldReturnObjectX0IsNullMessage() {

        // Arrange
        String objectName = "User";

        // Act
        var result = MsgUltil.OBJECT_X0_IS_NULL(objectName);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0008");
        assertThat(result[1]).isEqualTo("Object " + objectName + " is null.");
    }

    @DisplayName("Should return FAILED_TO_REMOVE_X0 message with entity name")
    @Test
    public void shouldReturnFailedToRemoveX0Message() {

        // Arrange
        String entityName = "Record";

        // Act
        var result = MsgUltil.FAILED_TO_REMOVE_X0(entityName);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0009");
        assertThat(result[1]).isEqualTo("Failed to remove " + entityName + ".");
    }

    @DisplayName("Should return RESPONSE_FAILED_PROCESS_REQUEST message")
    @Test
    public void shouldReturnResponseFailedProcessRequestMessage() {

        // Arrange
        // Act
        var result = MsgUltil.RESPONSE_FAILED_PROCESS_REQUEST();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0010");
        assertThat(result[1]).isEqualTo("Failed to process the request.");
    }

    @DisplayName("Should return BAD_REQUEST message")
    @Test
    public void shouldReturnBadRequestMessage() {

        // Arrange
        // Act
        var result = MsgUltil.BAD_REQUEST();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result[0]).isEqualTo("COD0011");
        assertThat(result[1]).isEqualTo("Bad request.");
    }

    @DisplayName("Should handle null parameters in formatted messages")
    @Test
    public void shouldHandleNullParametersInFormattedMessages() {

        // Arrange
        // Act
        var result1 = MsgUltil.X0_IS_REQUIRED(null);
        var result2 = MsgUltil.DATA_OF_X0_X1_NOT_FOUND(null, null);
        var result3 = MsgUltil.IDENTIFIER_X0_IS_INVALID(null);

        // Assert
        assertThat(result1[1]).contains("null");
        assertThat(result2[1]).contains("null");
        assertThat(result3[1]).contains("null");
    }
}