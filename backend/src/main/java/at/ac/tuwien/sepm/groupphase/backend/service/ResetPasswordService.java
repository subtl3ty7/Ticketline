package at.ac.tuwien.sepm.groupphase.backend.service;

public interface ResetPasswordService {

    void resetPasswordRequest(String email);

    String getResetPasswordEmailWithCode(String resetPasswordCode);

    void deleteExpiredResetPasswordCodes();

    String generateRandomPasswordFor(String email);
}
