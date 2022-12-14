package com.company.my_app.user;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll(Sort.by("id"))
                .stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setAddress(user.getAddress());
        userDTO.setBirthDate(user.getBirthDate());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setName(userDTO.getName());
        user.setRole(userDTO.getRole());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setBirthDate(userDTO.getBirthDate());
        user.setRefreshToken(userDTO.getRefreshToken());
        return user;
    }

//    public UserDetails loadUserByUsername(String username) {
//        User user = userRepository.findByUsername(username);
//        if(user == null){
//            throw new UsernameNotFoundException("User not found in the database");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRole());
//    }
}
