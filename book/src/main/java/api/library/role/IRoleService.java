package api.library.role;

import api.library.user.User;

import java.util.List;

public interface IRoleService {
    List<Role> getAllRoles();
    Role createRole(Role role);

    void deleteRole(Long roleId);

    Role findByName(String name);

    Role findById(Long roleId);

    Role updateRole(Long roleId, Role role);

    User removeUserFromRole(Long userId, Long roleId);

    User assignUserToRole(Long userId, Long roleId);

    Role removeAllUsersFromRole(Long roleId);
}
