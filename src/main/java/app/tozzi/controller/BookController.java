package app.tozzi.controller;

import app.tozzi.manager.BookManager;
import app.tozzi.model.Book;
import app.tozzi.model.input.JPASearchInput;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
@Observed(name = "books", contextualName = "controller")
@Validated
public class BookController {

    @Autowired
    private BookManager bookManager;

    @Operation(operationId = "findBookGet", summary = "Books", description = "Find books Paginated.", tags = {"Books"}, responses = {
            @ApiResponse(responseCode = "200", description = "Books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Book.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> findBook(@RequestParam Map<String, String> requestParams) {
        return bookManager.findBooks(requestParams);
    }

    @Operation(operationId = "findBookPost", summary = "Books", description = "Find books. Paginated.", tags = {"Books"}, responses = {
            @ApiResponse(responseCode = "200", description = "Books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Book.class)))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> findBook(@RequestBody @Valid JPASearchInput request) {
        return bookManager.findBooks(request);
    }

}
