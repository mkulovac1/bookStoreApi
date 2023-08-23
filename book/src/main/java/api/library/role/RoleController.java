package api.library.role;

import api.library.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role theRole) {
        return new ResponseEntity<>(roleService.createRole(theRole), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
    }

    @PostMapping("/removeAllUsersFromRole/{id}")
    public Role removeAllUsersFromRole(@PathVariable("id") Long roleId) { // need "id" annotation cause variable is named as roleId, if it was id it would work without it.
        return roleService.removeAllUsersFromRole(roleId);
    }

    @PostMapping("/removeUserFromRole")
    public User removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        return roleService.removeUserFromRole(userId, roleId);
    }

    @PostMapping("/assignUserToRole")
    public User removeUserToRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
        return roleService.assignUserToRole(userId, roleId);
    }
}
