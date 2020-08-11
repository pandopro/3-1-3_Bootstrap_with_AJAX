package com.stecenko.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String city;
    @Column(unique = true)
    private String login;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;
    public User() {
    }

    public User(String name, String city, String login, String password, Set<Role> roles) {
        this.name = name;
        this.city = city;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String city, String login, String password) {
        this.name = name;
        this.city = city;
        this.login = login;
        this.password = password;
    }

    @Transient
    @Override
    public String toString() {
        return id + ", " + name + ", " + city + ", " + login + ", " + password + ", " + this.getStringRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public Set<Role> getRoles() {

        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public String[] getArrayOfRoles() {
        try {
            String[] arrRoles = new String[roles.size()];
            Iterator<Role> iter = roles.iterator();
            int i = 0;
            while (iter.hasNext()) {
                arrRoles[i++] = iter.next().getRole();
            }
            return arrRoles;
        } catch (Exception e) {
            return new String[]{"fatal_error"};
        }
    }

    @Transient
    public String getStringRoles() {
        return Arrays.toString(getArrayOfRoles())
                .replace("[", "")
                .replace("]", "");
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @Transient
    public String getUsername() {
        return login;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}
