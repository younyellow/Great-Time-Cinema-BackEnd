package movie.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import movie.dto.LoginDto;
import movie.dto.UserDto;

public interface LoginService extends UserDetailsService {
	public UserDto login(LoginDto loginDto) throws Exception;
	public int registUser(UserDto userDto) throws Exception;
}
