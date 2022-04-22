package dispatchers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import objects.PromptPool;

@WebListener
public class Context implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent e) {
		System.out.println("INITIALIZING PROMPTS.......");
		PromptPool.init();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent e) {
		
	}
}
