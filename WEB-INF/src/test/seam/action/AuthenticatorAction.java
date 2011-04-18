package test.seam.action;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

import test.biz.Profile;
import test.biz.User;
import test.seam.producer.LoggedUserProducer;

@Named("appAuthenticator")
public class AuthenticatorAction implements Authenticator {

	@Inject
	EntityManager em;

	@Inject
	Credentials credentials;

	@Inject
	Identity identity;

	AuthenticationStatus status;
	org.picketlink.idm.api.User plUser;

	@Inject
	LoggedUserProducer loggedUser;

	@Override
	public void authenticate() {
		List<?> results = em.createQuery("select u from User u where u.username=:username " +
				"and u.password=:password")
			.setParameter("username", credentials.getUsername())
			.setParameter("password", ((PasswordCredential) credentials.getCredential()).getValue())
			.getResultList();

		if (results.size() != 0) {
			User user = (User) results.get(0);
			identity.addGroup("main", "global");
			for (Profile p : user.getProfileList()) {
				identity.addRole(p.getDescription(), "main", "global");
			}
			status = AuthenticationStatus.SUCCESS;
			plUser = new SimpleUser(user.getUsername());
			loggedUser.setUser(user);
		} else {
			status = AuthenticationStatus.FAILURE;
			plUser = null;
		}
	}

	@Override
	public AuthenticationStatus getStatus() {
		return status;
	}

	@Override
	public void postAuthenticate() {
		// Empty
	}

	@Override
	public org.picketlink.idm.api.User getUser() {
		return plUser;
	}

}
