package net.myjdbc.jdbc.service.impl;

import net.myjdbc.jdbc.dto.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CrudOperationServiceImplTest {
    CrudOperationServiceImpl service = new CrudOperationServiceImpl();

    @Test
    void insert() {
        List<User> oldTable = service.getAll();
        User user = service.insert("Name", 16, "email");
        List<User> updatedTable = service.getAll();
        assertNotEquals(updatedTable.size(),oldTable.size());
        assertEquals(user.getName(), "Name");
    }

    @Test
    void getAll() {
        List<User> table = service.getAll();
        assertNotNull(table);
    }

    @Test
    void updateUserEmailById() {
        User oldUser = service.getUserById(String.valueOf(6));
        String oldEmail = oldUser.getEmail();
        String newEmail = "NewEmail@test.ru";

        service.updateUserEmailById(String.valueOf(6), newEmail);

        User newUser = service.getUserById(String.valueOf(6));

        assertNotEquals(oldEmail,newUser.getEmail());
        assertEquals(newEmail,newUser.getEmail());
    }

    @Test
    void deleteUserById() {
        User oldUser = service.getUserById(String.valueOf(4));
        service.deleteUserById(String.valueOf(4));
        List<User> allUsers = service.getAll();
        for(int i = 0; i < allUsers.size(); i++){
            User user = allUsers.get(i);
            assertNotEquals(oldUser.getId(),user.getId());
        }
    }

}