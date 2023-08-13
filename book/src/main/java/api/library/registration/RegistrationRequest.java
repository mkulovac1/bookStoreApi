package api.library.registration;

import api.library.role.Role;

import java.util.Set;

public record RegistrationRequest(String firstName, String lastName, String email, String password, Set<Role> roles) {

}
