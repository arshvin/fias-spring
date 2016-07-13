package medved.fias.admin;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.vaadin.server.VaadinServlet;

@WebServlet(
    asyncSupported=false,
    urlPatterns={"/*","/VAADIN/*"},
    initParams={
        @WebInitParam(name="ui", value="medved.fias.admin.FiasAdminUI")
    })
public class FiasAdminServlet extends VaadinServlet { }
