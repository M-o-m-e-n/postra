package com.devtiro.blog.services;

import com.devtiro.blog.domain.entities.User;
import com.devtiro.blog.repositories.UserRepository;
import com.devtiro.blog.security.BlogUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class BlogUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->  new UsernameNotFoundException("User not found with this email:" + email));
        return new BlogUserDetails(user);
    }
}
