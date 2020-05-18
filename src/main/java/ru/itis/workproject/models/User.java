package ru.itis.workproject.models;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private State state;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "owner")
    @Where(clause = "type = 'image/png'")
    private List<Document> pngDocuments;

    @OneToMany(mappedBy = "owner")
    private List<Document> documents;
}
