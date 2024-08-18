package smartcontactmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.smartcontactmanager.services.ContactService;
import com.smartcontactmanager.services.EmailService;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ContactService contactService;

    // @Test
    // void sendEmail() {

    // emailService.sendMail("goutamnamdev8120@gmail.com", "just for testing Third
    // time...😃",
    // "spring boot project okay ...");
    // }

}
