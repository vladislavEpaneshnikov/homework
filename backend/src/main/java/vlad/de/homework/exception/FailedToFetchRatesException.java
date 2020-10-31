package vlad.de.homework.exception;

public class FailedToFetchRatesException extends Exception {

    public FailedToFetchRatesException(final String errorMessage) {
        super(errorMessage);
    }
}
