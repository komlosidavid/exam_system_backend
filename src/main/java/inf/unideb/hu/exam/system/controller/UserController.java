package inf.unideb.hu.exam.system.controller;

import inf.unideb.hu.exam.system.dto.UserDto;
import inf.unideb.hu.exam.system.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl service;
    private final ModelMapper modelMapper;

    @RequestMapping(
            method = RequestMethod.GET,
            produces = APPLICATION_JSON_VALUE
    )
    public Page<UserDto> getAllUsers(Pageable pageable) {
        var result = service.getAllUsers(pageable);
        return result.map(user ->
                modelMapper.map(user, UserDto.class));
    }

}
