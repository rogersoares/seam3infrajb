package test.conf;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.solder.core.ExtensionManaged;

public class PersistenceContextProvider {

	@ExtensionManaged
	@Produces
	@PersistenceUnit(unitName="testDatabase")
	@ConversationScoped
	@Default
	EntityManagerFactory producerField;

}