package at.ac.tuwien.sepm.groupphase.backend.service;

import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;

public interface EmailService {

    void sendResetPasswordEmail(ResetPassword resetPassword);
}
