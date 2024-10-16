//package org.clx.library.bookabhay.entities;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.clx.library.user.model.User;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//public class Book  {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private String title;
//    private String ISBN;
//
//    @ManyToOne
//    @JoinColumn(name = "author_id")
//    private Author author;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "book_status")
//    private BookStatus status;
//
//    private LocalDateTime createdAt;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//}
