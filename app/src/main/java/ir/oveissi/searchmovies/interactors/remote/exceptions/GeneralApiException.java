package ir.oveissi.searchmovies.interactors.remote.exceptions;

import retrofit2.Response;

/**
 * Created by abbas on 6/15/17.
 */

public class GeneralApiException extends Exception {
    public String message;
    public String errors;

    private final transient Response<?> response;


    public GeneralApiException(Response<?> response) {
        this.response = response;
    }

    /**
     * HTTP status code.
     */
    public int code() {
        return response.code();
    }

    /**
     * HTTP status message.
     */
    public String message() {
        return message;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public Response<?> response() {
        return response;
    }
}
