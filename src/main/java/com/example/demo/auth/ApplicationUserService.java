package com.example.demo.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SecurityUser;

@Service
public class ApplicationUserService implements UserDetailsService{ //UserDetailsService: ユーザー情報をDBから取得するインターフェース

//	private final ApplicationUserDao applicationUserDao; //DIコンテナにinterfaceの実装クラスが登録された場合、実装クラスでオーバーライドされたメソッドを使用できる
	
//	@Autowired
//	public ApplicationUserService(@Qualifier("fake") ApplicationUserDao applicationUserDao) { //@Qualifier: DIコンテナに@Repository("fake")を設定してApplicationUserDaoの実装メソッドを参照
//		this.applicationUserDao = applicationUserDao;
//	}
	
	@Autowired
	ApplicationUserDao applicationUserDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SecurityUser> user =applicationUserDao.findByUserName(username);
		user.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return user.map(ApplicationUser::new).get();
	}
	
	public SecurityUser createUser(SecurityUser user, String rawPassword) {
		String password = passwordEncoder().encode(rawPassword);
		user.setPassword(password);
		return applicationUserDao.save(user);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
////		return NoOpPasswordEncoder.getInstance();
	}

}
