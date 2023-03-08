package com.example.jwtprj.Service;

import com.example.jwtprj.domain.Role;
import com.example.jwtprj.domain.User;
import com.example.jwtprj.repo.RoleRepo;
import com.example.jwtprj.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService , UserDetailsService {

    @Autowired
    private JavaMailSender mailSender;

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    @Override
    public User UpdateUser(User user , long id) {

        User userToUpdate = userRepo.findById(id).get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());

        return userRepo.save(userToUpdate);

    }


    @Override
    public Role saveRole(Role role) {
            return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("user not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add (new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }

    public void sendPassMail(String userEmail) throws MessagingException, UnsupportedEncodingException {

        String toAddress = userEmail;
        String fromAddress = "doulakamel@gmail.com";
        String senderName = "RE-XPERT";
        String subject = "Your RE-Xpert password";
        String content = "Dear Mr/Madame, did you want to reset your password ? "
                + "Someone (hopefully you) has asked us to reset the password for your<br>"
                + "account. Please click the button below to do so. If you didn't<br>"
                + "request this password reset, you can go ahead and ignore this email!"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>"
                + "Thank you,<br>"
                + "RE-XPERT.";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        String token = UUID.randomUUID().toString();

        String verifyURL = "http://localhost:8001/oauth/resetPassword/" + token;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);


        mailSender.send(message);

        Optional<User> user = userRepo.findByEmail(userEmail);


        user.get().setPasswordToken(token);
        userRepo.save(user.get());


    }

    private void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {

        String toAddress = user.getEmail();
        String fromAddress = "doulakamel@gmail.com";
        String senderName = "RE-Xpert";
        String subject = "Please verify your registration";
        String content = "Dear Mr/Madame,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "RE-Xpert.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String verifyURL = "http://localhost:8085/oauth/verify/" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

        System.out.println("Email has been sent");
    }
    public boolean existsEmail(String email) {

        return userRepo.existsByEmail(email);
    }

    public ResponseEntity<User> verify(String verificationCode) {
        if (verificationCode == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepo.findByVerificationCode(verificationCode);

        if (user.isPresent()) {
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            userRepo.save(user.get());
            return ResponseEntity.ok(user.get());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public void verifyPassToken(String token,String newPassword) {

        Optional<User> user = userRepo.findByPasswordToken(token);


        String encodedPassword = passwordEncoder.encode(newPassword);
        user.get().setPassword(encodedPassword);

        userRepo.save(user.get());




    }

    public boolean ifEmailExist(String email){
        return  userRepo.existsByEmail(email);
    }
}
