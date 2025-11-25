package com.jnnieto.invetryx.product.catalog.service.common.exceptions;

import com.jnnieto.invetryx.product.catalog.service.dto.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private WebRequest webRequest;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        webRequest = mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/test-endpoint");
    }

    @Test
    void handleBadRequest_ShouldReturn400() {
        BadRequestException ex = new BadRequestException("Bad request error");

        ResponseEntity<ApiError> response = handler.handleBadRequest(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody().message()).isEqualTo("Bad request error");
        assertThat(response.getBody().path()).isEqualTo("/test-endpoint");
    }

    @Test
    void handleUnauthorized_ShouldReturn401() {
        UnauthorizedException ex = new UnauthorizedException("Unauthorized");

        ResponseEntity<ApiError> response = handler.handleUnauthorized(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(401);
        assertThat(response.getBody().message()).isEqualTo("Unauthorized");
    }

    @Test
    void handleForbidden_ShouldReturn403() {
        ForbiddenException ex = new ForbiddenException("Forbidden");

        ResponseEntity<ApiError> response = handler.handleForbidden(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(403);
        assertThat(response.getBody().message()).isEqualTo("Forbidden");
    }

    @Test
    void handleNotFound_ShouldReturn404() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Not found");

        ResponseEntity<ApiError> response = handler.handleNotFound(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody().message()).isEqualTo("Not found");
    }

    @Test
    void handleConflict_ShouldReturn409() {
        ResourceConflictException ex = new ResourceConflictException("Already exists");

        ResponseEntity<ApiError> response = handler.handleConflict(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(409);
        assertThat(response.getBody().message()).isEqualTo("Already exists");
    }

    @Test
    void handleUnprocessable_ShouldReturn422() {
        UnprocessableEntityException ex = new UnprocessableEntityException("Invalid entity");

        ResponseEntity<ApiError> response = handler.handleUnprocessable(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(422);
        assertThat(response.getBody().message()).isEqualTo("Invalid entity");
    }

    @Test
    void handleAll_ShouldReturn500() {
        Exception ex = new Exception("Internal error");

        ResponseEntity<ApiError> response = handler.handleAll(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody().message()).isEqualTo("Internal error");
    }

    @Test
    void handleValidationErrors_ShouldReturn400_WithFieldErrors() {
        // Mock del BindingResult
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError error1 = new FieldError("product", "name", "Name is required");
        FieldError error2 = new FieldError("product", "price", "Price must be > 0");

        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(error1, error2));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiError> response = handler.handleValidationErrors(ex, webRequest);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody().message())
                .contains("name: Name is required")
                .contains("price: Price must be > 0");
    }
}
