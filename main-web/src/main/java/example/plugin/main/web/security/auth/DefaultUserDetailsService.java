package example.plugin.main.web.security.auth;

import example.plugin.main.domain.user.User;
import example.plugin.main.domain.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByLoginId(loginId);
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("can't find user. loginId:" + loginId);
        User user = userOptional.get();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add((new SimpleGrantedAuthority(user.getRoleId())));
        return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(), authorityList);
    }
}
