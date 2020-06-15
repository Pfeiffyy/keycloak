package com.keycoak.userfed;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class FedRepository {

    private List<FedUser> users;

    FedRepository() {
        users = Arrays.asList(
                new FedUser("1", "Tom", "Cat"),
                new FedUser("2", "Jerry", "Mouse"),
                new FedUser("3", "Donald", "Duck")
        );
    }

    List<FedUser> getAllUsers() {
        return users;
    }

    int getUsersCount() {
        return users.size();
    }

    FedUser findUserById(String id) {

    	return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    FedUser findUserByUsernameOrEmail(String username) {
    	 System.out.println(">>>>>>user"+username);
       
    	 return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(username))
                .findFirst().orElse(null);
    }

    List<FedUser> findUsers(String query) {
        return users.stream()
                .filter(user -> user.getUsername().contains(query) || user.getEmail().contains(query))
                .collect(Collectors.toList());
    }

    boolean validateCredentials(String username, String password) {
        return findUserByUsernameOrEmail(username).getPassword().equals(password);
    }

    boolean updateCredentials(String username, String password) {
        findUserByUsernameOrEmail(username).setPassword(password);
        return true;
    }

}
