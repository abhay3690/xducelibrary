package org.clx.library.user.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {
    // Additional admin-specific fields (optional)
    private String adminSpecificField;
}
