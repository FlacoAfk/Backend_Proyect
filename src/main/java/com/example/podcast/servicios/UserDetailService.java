package com.example.podcast.servicios;





import com.example.podcast.entidades.UserRecord;
import com.example.podcast.repos.UserRepository;
import com.example.podcast.respuesta.LoadUserResponse;
import com.example.podcast.respuesta.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailService( UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public String flag;

    public void setFlag(String flag){
        this.flag=flag;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        LoadUserResponse loadUserResponse = new LoadUserResponse();
        try {
            Optional<UserRecord> clientRecord = userRepository.findByDocument(username);
            if(clientRecord.isPresent() && this.flag=="CLIENT"){
                loadUserResponse.setId(clientRecord.get().getId());
                loadUserResponse.setUsername(clientRecord.get().getDocument());
                loadUserResponse.setPassword(clientRecord.get().getPassword());
                loadUserResponse.setEnable(clientRecord.get().isStatus());
            }
        } catch (RuntimeException e){
            log.error("Authentication Failed. Username or Password not valid.");
        }
        return new UserResponse(loadUserResponse.getId(), loadUserResponse.getUsername(),  loadUserResponse.getPassword(),true, true, true, true, loadUserResponse.isEnable());
    }

}
