package xyz.ms.social_spring.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.generator.EventType;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.ms.social_spring.users.entity.type.Role;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

//@Entity
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Table(name = "users",
//        uniqueConstraints = @UniqueConstraint(name = "users_username_unique", columnNames = "username"))
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @NotBlank(message = "Username is required")
//    @Column(nullable = false, length = 255)
//    private String username;
//
//    @NotBlank(message = "Password is required")
//    @Column(nullable = false, length = 255)
//    private String password;
//
//    @Builder.Default
//    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
//    @Column(nullable = false)
//    private Role role = Role.USER;
//
//    @CreationTimestamp
//    @Column(nullable = false, updatable = false)
//    private Instant createdAt;
//
//    @UpdateTimestamp
//    @Column(nullable = false)
//    private Instant updatedAt;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//}

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicInsert // Crucial: Omits null fields from INSERT, triggering DB defaults
@Table(name = "users",
        uniqueConstraints = @UniqueConstraint(name = "users_username_unique", columnNames = "username"))
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Generated(event = EventType.INSERT) // Tells Hibernate the DB will generate this on insert, and to fetch it back
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()", insertable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "Username is required")
    @Column(nullable = false, length = 255)
    private String username;

    @NotBlank(message = "Password is required")
    @Column(nullable = false, length = 255)
    private String password;


//    @Builder.Default
    @Generated(event = EventType.INSERT)
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "role DEFAULT 'USER'") // if null @DynamicInsert triggers DB default 'USER'
    private Role role;


//    @CreationTimestamp
    @Generated(event = EventType.INSERT) // Tells Hibernate the DB will generate this on insert (via defaultNow())
    @Column(nullable = false, updatable = false, columnDefinition = "timestamp(6) with time zone DEFAULT now()")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updatedAt;

}