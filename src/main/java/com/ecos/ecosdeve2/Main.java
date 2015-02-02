package com.ecos.ecosdeve2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

public class Main extends HttpServlet {

    private String sDirectorioTrabajo = System.getProperty("user.dir");
    private String sSeparator = System.getProperty("file.separator");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        showHome(req, resp);
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        CalcularLoc e1 = new CalcularLoc();
        CalcularLoc e2 = new CalcularLoc();
        try {
            e1.leerRuta("src" + sSeparator + "main" + sSeparator + "java" + sSeparator + "com" + sSeparator + "ecos" + sSeparator + "ecosdeve1"+ sSeparator,sSeparator);//m
            //e2.leerRuta("src" + sSeparator + "main" + sSeparator + "java" + sSeparator + "com" + sSeparator + "ecos" + sSeparator + "ecosdeve2"+ sSeparator,sSeparator);//m
            resp.getWriter().println("Medicion de LOC");
            resp.getWriter().println("<table style='width:100%'>");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>Numero Programa</td>");
            resp.getWriter().println("<td>Tipo de parte</td>");
            resp.getWriter().println("<td>Acceso, tipo, Nombre de parte</td>");
            resp.getWriter().println("<td>Numero de items</td>");
            resp.getWriter().println("<td>Total de la parte</td>");
            resp.getWriter().println("<td>Total tamaño</td>");
            resp.getWriter().println("</tr>");
            resp.getWriter().println("</table>");
            for (int x = 0; x < e1.getNombreClases().size(); x++) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td>1</td>");
                resp.getWriter().println("<td>Clase</td>");
                resp.getWriter().println("<td>"+e1.getNombreClases().get(x) +"</td>");
                resp.getWriter().println("<td>Numero de items</td>");
                resp.getWriter().println("<td>"+ e1.getContadorLocClases().get(x) +"</td>");
                resp.getWriter().println("<td></td>");
                resp.getWriter().println("</tr>");
            }
        } catch (Exception e) {
            resp.getWriter().print("Ocurrio un Error : " + e.getMessage());
        }

    }

    public static void main(String[] args) throws Exception {
        //new Server(Integer.valueOf(System.getenv("PORT")));
        Server server = new Server(80);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new Main()), "/*");
        server.start();
        server.join();
    }

    public String getsDirectorioTrabajo() {
        return sDirectorioTrabajo;
    }

    public void setsDirectorioTrabajo(String sDirectorioTrabajo) {
        this.sDirectorioTrabajo = sDirectorioTrabajo;
    }

    public String getsSeparator() {
        return sSeparator;
    }

    public void setsSeparator(String sSeparator) {
        this.sSeparator = sSeparator;
    }
}
