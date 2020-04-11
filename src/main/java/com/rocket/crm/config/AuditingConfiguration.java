package com.rocket.crm.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import com.rocket.crm.entity.User;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfiguration implements AuditorAware<User> {

	@Override
	public Optional<User> getCurrentAuditor() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return Optional.of(new User(username));
	}

	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditingConfiguration();
	}
}
