package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.UserDTO;
import com.devsuperior.dscommerce.entities.Role;
import com.devsuperior.dscommerce.entities.User;
import com.devsuperior.dscommerce.projections.UserDetailsProjection;
import com.devsuperior.dscommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = repository.loadUserAndRolesByEmail(username);
        if(result.size() == 0) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        for (UserDetailsProjection role : result) {
            user.addRole(new Role(role.getRoleId(), role.getAuthority()));
        }
        return user;
    }

    //Obter o usuário Logado
    protected User authenticated() {
        try {
            //Ser tive um cara logado vai pego o obj pra me e dentro desse obj, como estamos usando o padrao JWT para o token do usuario
            //aparti dele eu consigo chama o getPrincipal(); fazendo o cast para o tipo JWT esse tipo tem o claims, exemplo esta na classe AuthorizationServerConfig nome do metado: OAuth2TokenCustomizer
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");// aqui eu consigo pega o e-mail do cara, o e-mail do cara que esta no token vai cair nessa variavel
            return repository.findByEmail(username).get();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Email not found");
        }
    }

    @Transactional(readOnly = true)
    // vai pega o usuario logado e vai retorna um DTO
    public UserDTO getMe(){
        User user = authenticated();
        return new UserDTO(user);
    }

}
