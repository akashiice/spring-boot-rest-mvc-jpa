package com.springboot.rest.springbootrest.service;

import com.springboot.rest.springbootrest.dto.UserDto;
import com.springboot.rest.springbootrest.entity.UserEntity;
import com.springboot.rest.springbootrest.repository.UserRepository;
import com.springboot.rest.springbootrest.service.UserService;
import com.springboot.rest.springbootrest.utils.Utility;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utility utility;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto userDto) {
        UserEntity storedUserDetails = userRepository.findByEmail(userDto.getEmail());

        if (storedUserDetails != null) throw new RuntimeException("user details already exists...");
        UserEntity userEntity = new UserEntity();
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setUserId(utility.generateUserID());
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        UserEntity savedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(savedUserDetails, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity =  userRepository.findByEmail(email);
        if(userEntity == null) throw new UsernameNotFoundException(email);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity userEntity =  userRepository.findByEmail(email);
       if(userEntity == null) throw new UsernameNotFoundException(email);

       return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList());
    }
}
