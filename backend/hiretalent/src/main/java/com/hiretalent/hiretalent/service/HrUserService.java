package com.hiretalent.hiretalent.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.stereotype.Service;
import com.hiretalent.hiretalent.entity.HrUser;
import com.hiretalent.hiretalent.repository.HrUserRepository;

import javax.naming.directory.Attributes;

@Service
public class HrUserService {

    private final HrUserRepository hrUserRepository;

	private final LdapTemplate ldapTemplate;

	private static final Logger logger = LoggerFactory.getLogger(HrUserService.class);

	@Autowired
	public HrUserService(HrUserRepository hrUserRepository, LdapTemplate ldapTemplate) {
		this.hrUserRepository = hrUserRepository;
		this.ldapTemplate = ldapTemplate;
	}

    public HrUser getHrUserById(Long hrId) {
        return hrUserRepository.findById(hrId).orElse(null);
    }

	public HrUser getUserByUsername(String username) {
		return hrUserRepository.findByUsername(username);
	}

	public HrUser createByLogin(String uid) {
		HrUser user = new HrUser();

		AttributesMapper<String[]> attributesMapper = (Attributes attrs) -> {
			String[] userAttributes = new String[2];
			userAttributes[0] = attrs.get("cn").get().toString();
			userAttributes[1] = attrs.get("sn").get().toString();
			return userAttributes;
		};

		String[] userAttributes = ldapTemplate.lookup("uid=" + uid, attributesMapper);

		String cn = userAttributes[0];
		String sn = userAttributes[1];

		logger.info("CN: {} , SN: {}", cn, sn);

		user.setUsername(uid);
		user.setName(cn);
		user.setSurname(sn);
		return hrUserRepository.save(user);
	}


}
