package at.ac.tuwien.sepm.groupphase.backend.service;

public interface ResetPasswordService {

    /**
     * Send a reset password link via email to user
     *
     * @param email - email of the user sending the request
     */
    void resetPasswordRequest(String email);

    /**
     * Gets the password code (when a user clicks on the link) and retrieve and check if user email matches
     * and reset password code exists in database
     *
     * @param resetPasswordCode - code that the link which is sent to email contains
     * @return - the email associated with reset password code if exists
     */
    String getResetPasswordEmailWithCode(String resetPasswordCode);

    /**
     * Delete all passwords which are expired from database
     *
     */
    void deleteExpiredResetPasswordCodes();

    /**
     * Generates a random password for the user which will be used to login via email link.
     *
     * @param email - email of the user sending the request
     */
    String generateRandomPasswordFor(String email);
}
