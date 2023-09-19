//package com.squarecross.photoalbum.service;
//
//import com.squarecross.photoalbum.repository.UserRepository;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService implements UserDetailsService {
//
//    private UserRepository userRepository;
//
//    public UserService(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//        return Optional.ofNullable(
//                userRepository.findByEmail(username)
//        ).orElseThrow(
//                () -> new BadCredentialsException("회원 정보를 확인해주세요")
//        );
//    }
//}
