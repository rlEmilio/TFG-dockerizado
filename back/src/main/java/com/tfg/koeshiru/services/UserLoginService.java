package com.tfg.koeshiru.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.koeshiru.entities.Usuario;
import com.tfg.koeshiru.repositories.UsuarioRepository;

@Service
public class UserLoginService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> optionalUser = repository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("El usuario " + username + " no existe en el sistema");
        }

        Usuario usuario = optionalUser.orElseThrow();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new User(username, usuario.getContrasenia(),
                true,
                true,
                true,
                true,
                authorities);
    }

}
