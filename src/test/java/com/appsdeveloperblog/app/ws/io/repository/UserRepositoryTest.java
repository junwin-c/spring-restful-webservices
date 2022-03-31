package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import org.junit.jupiter.api.Assertions;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setup() throws Exception {
        if(!recordsCreated) createRecords();
    }

    @Test
    final void testGetVerifiedUser() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUserWithConfirmedEmailAddress(pageableRequest);
        Assertions.assertNotNull(pages);

        List<UserEntity> userEntities = pages.getContent();
        Assertions.assertNotNull(userEntities);
        Assertions.assertTrue(userEntities.size() >= 1);
    }

    @Test
    final void findUserByFirstName() {
        String firstName = "abc";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.size() > 2);

        UserEntity user = users.get(0);
        Assertions.assertTrue(user.getFirstName().equals(firstName));
    }

    @Test
    final void findUserByLastName() {
        String lastName = "def";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.size() > 2);

        UserEntity user = users.get(0);
        Assertions.assertTrue(user.getLastName().equals(lastName));
    }

    @Test
    final void findUserByKeyword() {
        String keyword = "de";
        List<UserEntity> users = userRepository.findUserByKeyword(keyword);
        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.size() > 2);

        UserEntity user = users.get(0);
        Assertions.assertTrue(user.getLastName().contains(keyword) || user.getFirstName().contains(keyword));
    }

    @Test
    final void findUserFirstNameAndLastNameByKeyword() {
        String keyword = "de";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        Assertions.assertNotNull(users);
        Assertions.assertTrue(users.size() > 2);

        Object[] user = users.get(0);
        String firstName = String.valueOf(user[0]);
        String lastName = String.valueOf(user[1]);

        Assertions.assertNotNull(firstName);
        Assertions.assertNotNull(lastName);

        System.out.println("first name : " + firstName);
        System.out.println("last name : " + lastName);
    }

    private void createRecords() {
        // Prepare User Entity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("abc");
        userEntity.setLastName("def");
        userEntity.setUserId("1a2b3c");
        userEntity.setEncryptedPassword("qwerty");
        userEntity.setEmail("abc.def@test.com");
        userEntity.setEmailVerificationStatus(true);

        // Prepare User Addressess
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setCity("Medan");
        addressEntity.setCountry("Indonesia");
        addressEntity.setAddressId("asdf3ah83");
        addressEntity.setPostalCode("123456");
        addressEntity.setStreetName("123 ABC street");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);
    }
}
