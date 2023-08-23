package api.library.role;

import api.library.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    private String name;
    @JsonIgnore // preventing overflow cause user is adding role and role is adding user in the same time - circle
    @ManyToMany(mappedBy = "roles") // because we defined fetching in user and made middle table so it is enough
    private Collection<User> users = new HashSet<>(); // we could initialize this in constructor otherwise it would not work (null)

    public Role(String name) {
        this.name = name;
    }

    public void removeAllUsersFromRole() { // every user for this role is removed
        if(users != null) {
            List<User> usersRole = users.stream().toList();
            usersRole.forEach(this::removeUserFromRole);
        }
    }

    public void removeUserFromRole(User user) { // one user is deleted from role
        user.getRoles().remove(this); // all roles are deleted for this user
        users.remove(user);
    }

    public void assignUserToRole(User user) { // adding one user to the role
        user.getRoles().add(this); // adding role to user
        users.add(user);
    }

    public void assignUsersToRole(List<User> users) { // vise usera se dodaje u ulogu
        users.forEach(this::assignUserToRole);
    }
}
