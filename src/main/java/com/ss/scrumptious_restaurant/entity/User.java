package com.ss.scrumptious_restaurant.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.Builder.Default;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name ="USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)", name = "userId", updatable = false)
    private UUID userId;

    @NotBlank
    @Email
    private String email;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @NotBlank
    private String password;

    @Default
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.DEFAULT;

    @Column(name="createdAt", updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationDateTime;

    @UpdateTimestamp
	@Column(name="updatedAt")
    private ZonedDateTime lastModifiedDateTime;

	@Default
	private boolean accountNonExpired = true;
	@Default
	private boolean accountNonLocked = true;
	@Default
    private boolean credentialsNonExpired = true;
    @Default
    private boolean enabled = true;
    @Default
    private boolean confirmed = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> set = new HashSet<>();
        if (userRole != null) {
            GrantedAuthority authority = new SimpleGrantedAuthority(userRole.getRole());
            set.add(authority);
        }
        return set;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
