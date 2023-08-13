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
    @JsonIgnore // da ne bi doslo do overflow-a jer i user dodaje rolove i obrnuto u isto vrijeme
    @ManyToMany(mappedBy = "roles") // jer smo u user definisali fetchiranje i napravili medju-tabelu tako da je dovoljno
    private Collection<User> users = new HashSet<>(); // ovo smo mogli u konstruktoru da inicijalizujemo inace ne bi radilo

    public Role(String name) {
        this.name = name;
    }

    public void removeAllUsersFromRole() { // svi useri vezani za ovu ulogu se brisu
        if(users != null) {
            List<User> usersRole = users.stream().toList();
            usersRole.forEach(this::removeUserFromRole);
        }
    }

    public void removeUserFromRole(User user) { // jedan user se brise iz uloge
        user.getRoles().remove(this); // sve uloge brisemo za tog usera
        users.remove(user);
    }

    public void assignUserToRole(User user) { // jedan user se dodaje u ulogu
        user.getRoles().add(this); // dodajemo ulogu useru
        users.add(user);
    }

    public void assignUsersToRole(List<User> users) { // vise usera se dodaje u ulogu
        users.forEach(this::assignUserToRole);
    }
}
