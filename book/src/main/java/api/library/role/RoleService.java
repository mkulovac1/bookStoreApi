package api.library.role;

import api.library.user.User;
import api.library.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    // private final UserService userService;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role role) {
        Optional<Role> theRole = roleRepository.findByName(role.getName());
        if (theRole.isPresent()) {
            throw new RuntimeException("Role with name " + role.getName() + " already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        // first you need to remove all users from this role and then delete the role because of links in database
        // roleRepository.deleteById(roleId);
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role with name " + name + " does not exist"));
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role with id " + roleId + " does not exist"));
    }

    @Override
    public Role updateRole(Long roleId, Role role) {
        return null;
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> theUser = userRepository.findById(userId);
        Optional<Role> theRole = roleRepository.findById(roleId);

        if(theRole.isPresent() && theRole.get().getUsers().contains(theUser.get())) {
            theRole.get().removeUserFromRole(theUser.get());
            roleRepository.save(theRole.get());
            // return userRepository.save(theUser.get());
            return theUser.get();
        }
        else {
            throw new UsernameNotFoundException("User with id " + userId + " does not have role with id " + roleId);
        }
    }

    @Override
    public User assignUserToRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if(user.isPresent() && user.get().getRoles().contains(role.get())) {
            throw new RuntimeException("User " + user.get().getFirstName() + " already has role  " + role.get().getName());
        }

        role.ifPresent(theRole -> theRole.assignUserToRole(user.get()));
        roleRepository.save(role.get());

        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> theRole = roleRepository.findById(roleId);
        theRole.ifPresent(Role::removeAllUsersFromRole); // otkantaj usere za tu ulogu
        return roleRepository.save(theRole.get());
    }
}
