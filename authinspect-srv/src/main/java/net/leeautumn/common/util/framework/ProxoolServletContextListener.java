package net.leeautumn.common.util.framework;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: peng
 * Date: 15-11-29
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class ProxoolServletContextListener implements ServletContextListener
{
    private static final Log LOG = LogFactory.getLog(ProxoolServletContextListener.class);

    private static final String XML_FILE_PROPERTY = "proxoolConfigLocation";

    private static final String PROPERTY_FILE_PROPERTY = "propertyFile";

    private static final String AUTO_SHUTDOWN_PROPERTY = "autoShutdown";

    @SuppressWarnings("unused")
    private boolean autoShutdown = true;

    public void contextDestroyed(ServletContextEvent arg0)
    {
        System.out.println("destroy database pool....");
    }

    public void contextInitialized(ServletContextEvent contextEvent)
    {
        ServletContext context = contextEvent.getServletContext(); //对应servlet的init方法中ServletConfig.getServletContext()
        String appDir = context.getRealPath("/");
        Properties properties = new Properties();
        Enumeration names = contextEvent.getServletContext().getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = context.getInitParameter(name);

            if (name.equals(XML_FILE_PROPERTY)) {
                try {
                    File file = new File(value);
                    if (file.isAbsolute()) {
                        JAXPConfigurator.configure(value, false);
                    } else {
                        JAXPConfigurator.configure(appDir + File.separator + value, false);
                    }
                } catch (ProxoolException e) {
                    LOG.error("Problem configuring " + value, e);
                }
            } else if (name.equals(PROPERTY_FILE_PROPERTY)) {
                try {
                    File file = new File(value);
                    if (file.isAbsolute()) {
                        PropertyConfigurator.configure(value);
                    } else {
                        PropertyConfigurator.configure(appDir + File.separator + value);
                    }
                } catch (ProxoolException e) {
                    LOG.error("Problem configuring " + value, e);
                }
            } else if (name.equals(AUTO_SHUTDOWN_PROPERTY)) {
                autoShutdown = Boolean.valueOf(value).booleanValue();
            } else if (name.startsWith("jdbc")) { //此处以前是PropertyConfigurator.PREFIX改为jdbc
                properties.setProperty(name, value);
            }
        }

        if (properties.size() > 0) {
            try {
                PropertyConfigurator.configure(properties);
            } catch (ProxoolException e) {
                LOG.error("Problem configuring using init properties", e);
            }
        }

    }

}