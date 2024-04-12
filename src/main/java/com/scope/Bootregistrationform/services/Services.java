
package com.scope.Bootregistrationform.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scope.Bootregistrationform.model.Users;
import com.scope.Bootregistrationform.repository.userRepository;
import com.scope.Bootregistrationform.repository.userRepository2;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;
import net.bytebuddy.utility.RandomString;

@Service
public class Services {

    @Autowired	
    private userRepository repo;
    
    @Autowired	
    private userRepository2 repos;
    
    @Autowired	
    private JavaMailSender sender;

    public void Insert(Users user, String siteUrl) throws UnsupportedEncodingException, MessagingException {
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        repo.save(user);
        sendVerificationEmail(user, siteUrl);
    }

    private void sendVerificationEmail(Users user, String siteUrl) throws UnsupportedEncodingException, MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "adwaythmanu@gmail.com";
        String senderName = "Adwayth";
        String subject = "Verify Registration";
        String message = "Dear [[firstname]], please click the link below to verify: <h3><a href=\"[[URL]]\" target=\"_blank\">Verify</a></h3>";
        MimeMessage msg = sender.createMimeMessage();
         MimeMessageHelper msgHelper = new MimeMessageHelper(msg,"utf-8");
        
        msgHelper.setFrom(fromAddress, senderName);
        msgHelper.setTo(toAddress);
        msgHelper.setSubject(subject);
        message = message.replace("[[firstname]]", user.getFirstname());
        String url = siteUrl + "/verify?code=" + user.getVerificationCode();
        message = message.replace("[[URL]]", url);
        msgHelper.setText(message, true);
        sender.send(msg);
    }

    public boolean verify(String verificationCode) {
        Users user = repo.findByVerificationCode(verificationCode);
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repo.save(user);
            return true;
        }
    }
    
    public void sendEmail(String toAddress, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
       // String toAddress = user.getEmail();
        message.setTo(toAddress);
        message.setSubject("OTP for Verification");
        message.setText("Your OTP for verification is: " + otp);
        sender.send(message);
    }
    
    public void updatePassword(String email,String password) {
    	 Users user = repos.findByEmail(email);
         if (user != null) {
             user.setPassword(password);
             repos.save(user);
    	
    }
    }
    
public Users getuserbyemail(String email) {
	return repos.findByEmail(email);
}

public void updatepassword(Users user) {
	repos.save(user);
}

public Users getCurrentUser(String email) {
    return repos.findByEmail(email);
}

public void saveUser(Users user) {
    repos.save(user);
}

public void updateUserdetails(Users updatedUser) {
    // Retrieve the existing user from the repository
    Users existingUser = repos.findByEmail(updatedUser.getEmail());
    
    if (existingUser != null) {
        // Update user details
        existingUser.setFirstname(updatedUser.getFirstname());
        existingUser.setLastname(updatedUser.getLastname());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setDateofbirth(updatedUser.getDateofbirth());
        existingUser.setPhonenumber(updatedUser.getPhonenumber());
        existingUser.setCountry(updatedUser.getCountry());
        existingUser.setState(updatedUser.getState());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setHobbie(updatedUser.getHobbie());
         existingUser.setAvatar(updatedUser.getAvatar());
        
        
        
        repos.save(existingUser);
    } 
}

public void sendingmail(String to, String from, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setFrom(from); // Set the sender's email address
    message.setSubject(subject);
    message.setText(text);
    sender.send(message);
}

public String getUserPassword(String email) {
    Users user = repos.findByEmail(email);
   
        return user.getPassword();
    }
	

}





