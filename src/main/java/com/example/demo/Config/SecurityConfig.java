package com.example.demo.Config;

import static com.example.demo.Config.ApplicationUserRole.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.auth.ApplicationUserService;

@Configuration //設定クラス
@EnableWebSecurity //設定クラスの有効化
@EnableGlobalMethodSecurity(prePostEnabled = true) //コントローラーのメソッド単位の権限認証の設定を有効化
public class SecurityConfig extends WebSecurityConfigurerAdapter{ //ログイン機能を持つメソッドを継承

	private final ApplicationUserService applicationUserService;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired // コンストラクターでpasswordEncoderとapplicationUserServiceのオブジェクトを自動生成
	public SecurityConfig(ApplicationUserService applicationUserService, PasswordEncoder passwordEncoder) {
		this.applicationUserService = applicationUserService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable() //悪質な攻撃を防ぐための設定をオフ
			.authorizeRequests()
			.antMatchers("/","index","/newUser","/css/*","/js/*").permitAll()
			.antMatchers("/api/**").hasRole(STUDENT.name())
//			.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//			.antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
			.anyRequest()
			.authenticated()
			.and()
//			.httpBasic(); //Basic認証を使う場合
			.formLogin() //formログインを使う場合
				.loginPage("/login").permitAll() //ログインページの指定、指定しないとSpringSecurityのデフォルトページが表示される
				.defaultSuccessUrl("/courses", true) //ログイン成功後のページの指定、true/falseで常に適応するかの指定
//				.usernameParameter("username") //formログインで送るユーザー名のパラメーター名を変更できる(デフォルトはusername)
//				.passwordParameter("password") //formログインで送るパスワードのパラメーター名を変更できる(デフォルトはpassword)
			.and()
//			.rememberMe() //sessionの有効時間を変更できる(デフォルトは30分)
//				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) //tokenの有効時間を設定
//				.key("somethingverysecured") //tokenのkey名を指定、デフォルトはランダムな値
////				.rememberMeParameter("remember-me") //formログインで送るremember-meのパラメーター名を変更できる(デフォルトはremember-me)
//			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET")) //csrf.disableをしているなら記載、していないならlogoutをpost
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID", "remember-me")
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/login");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(applicationUserService).passwordEncoder(passwordEncoder);
	}
	
//	@Bean
//	public DaoAuthenticationProvider daoAuthenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(passwordEncoder());
//		provider.setUserDetailsService(applicationUserService);
//		return provider;
//	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//////		return NoOpPasswordEncoder.getInstance();
//	}

}
