package com.cc.creativecraze.service;

import com.cc.creativecraze.dto.UserDto;
import com.cc.creativecraze.model.Role;
import com.cc.creativecraze.model.User;
import com.cc.creativecraze.repository.RoleRepository;
import com.cc.creativecraze.repository.UserRepository;
import jakarta.annotation.Resource;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService{
private final UserRepository userRepository;
private final RoleRepository roleRepository;
private final PasswordEncoder passwordEncoder;
private final JavaMailSender mailSender;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.roleRepository= roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setNumber(userDto.getNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role modelRole = roleRepository.findByName("Artist");
        if (modelRole == null){
            modelRole = new Role();
            modelRole.setName("Artist");
            roleRepository.save(modelRole);
        }
        List<Role> roles = new ArrayList<>();
        roles.add(modelRole);
        user.setRoles(roles);
        userRepository.save(user);
        sendWelcomeEmail(userDto.getName(), userDto.getEmail());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());

    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setNumber(user.getNumber());
        return userDto;
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.getTime();
    }

    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
    private void sendWelcomeEmail(String userName, String userEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(userEmail);
            helper.setSubject("Welcome to Creative Craze");

            // Read the HTML content from the template file
            String htmlContent = readHtmlTemplate("welcome_email.html");

            // Replace placeholders with actual values
            htmlContent = htmlContent.replace("[user_name]", userName);

            // Set the HTML content of the email
            helper.setText(htmlContent, true);

            // Send the email
            mailSender.send(message);
        } catch (Exception e) {
            // Handle exceptions (e.g., mail sending failure)
            e.printStackTrace();
        }
    }
    private String readHtmlTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
