package com.cshep4.wcpredictor.component.resetpassword

import com.cshep4.wcpredictor.constant.APIConstants.RESET_PASSWORD_LINK
import com.cshep4.wcpredictor.email.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ResetEmail {
    private val subject = "World Cup Predictor 2018 Password Reset"

    @Autowired
    private lateinit var emailSender: Email

    fun send(email: String, signature: String) {
        val message = buildMessage(email, signature)

        emailSender.send(email, subject, message)
    }

    private fun buildMessage(email: String, signature: String): String {
        val link = "$RESET_PASSWORD_LINK?email=$email&signature=$signature"

        return "Hi,\n \n" +
                "We have received a request to reset your password.\n \n" +
                "To reset your password click on the following link or copy and paste this URL into your browser (link expires in 24 hours):\n \n" +
                "$link\n \n" +
                "If you don't want to reset your password then please ignore this message.\n \n" +
                "Regards,\n \n" +
                "The World Cup 2018 Predictor Team"
    }
}