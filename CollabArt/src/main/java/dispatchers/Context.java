package dispatchers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Context implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent e) {
		System.out.println("SERVLET HAS BEEN INITIALIZED!@@@!!!");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		
	}
}
