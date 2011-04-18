package test.seam.producer;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import test.biz.User;

@Named
@SessionScoped
public class LoggedUserProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user;

	@Produces @Named("loggedUser") User getUser() {
		System.out.println("get " + user);
		return user;
	}

	public void setUser(User user) {
		System.out.println("set " + user);
		this.user = user;
	}

}
