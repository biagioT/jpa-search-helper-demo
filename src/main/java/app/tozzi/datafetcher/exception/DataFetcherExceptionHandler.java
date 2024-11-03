package app.tozzi.datafetcher.exception;

import app.tozzi.exception.JPASearchException;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class DataFetcherExceptionHandler implements graphql.execution.DataFetcherExceptionHandler {

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {

        var message = switch (handlerParameters.getException()) {
            case JPASearchException e -> e.getMessage();
            default -> "Unexpected error";
        };

        var error = GraphQLError.newError()
                .message(message)
                .locations(List.of(handlerParameters.getSourceLocation()))
                .path(handlerParameters.getPath())
                .build();

        return CompletableFuture.completedFuture(graphql.execution.DataFetcherExceptionHandlerResult.newResult().error(error).build());
    }
}
