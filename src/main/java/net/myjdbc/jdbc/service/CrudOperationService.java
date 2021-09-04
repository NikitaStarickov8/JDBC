package net.myjdbc.jdbc.service;

import net.myjdbc.jdbc.dto.User;

import java.util.List;


public interface CrudOperationService {

    User insert(String name, int age, String email);

    List<User> getAll();

    User updateUserEmailById(String id, String email);

    void deleteUserById(String id);

    User getUserById(String id);


}
