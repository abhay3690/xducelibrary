package org.clx.library.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clx.library.book.entity.Book;

import java.util.ArrayList;
import java.util.List;

// Use plural for table naming convention
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users") // Use plural for table naming convention
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Long contactNumber;
    private String collegeName;
    private String email;
    private String gender;
    private String password;
    private String status;
    @ManyToMany
    @JsonIgnore
    private List<Book> savedPost = new ArrayList<>();
}