package com.ecos.ecosdeve2.controller;

import com.ecos.ecosdeve2.CalcularLoc;
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
        resp.setContentType("text/html");
        resp.getWriter().println("<style type=\"text/css\">");
        resp.getWriter().println(".myTable { background-color:#eee;border-collapse:collapse; }");
        resp.getWriter().println(".myTable th { background-color:#000;color:white;width:50%; }");
        resp.getWriter().println(".myTable td, .myTable th { padding:5px;border:1px solid #000; }");
        resp.getWriter().println("</style>");

        resp.getWriter().println("<style type=\"text/css\">");
        resp.getWriter().println(".myOtherTable { background-color:#FFFFE0;border-collapse:collapse;color:#000;font-size:18px; }");
        resp.getWriter().println(".myOtherTable th { background-color:#BDB76B;color:white;width:50%; }");
        resp.getWriter().println(".myOtherTable td, .myOtherTable th { padding:5px;border:0; }");
        resp.getWriter().println(".myOtherTable td { border-bottom:1px dotted #BDB76B; }");
        resp.getWriter().println("</style>");



        try {
            e1.leerRuta("src" + sSeparator + "main" + sSeparator + "java" + sSeparator + "com" + sSeparator + "ecos" + sSeparator + "ecosdeve1" + sSeparator, sSeparator);//m
            e2.leerRuta("src" + sSeparator + "main" + sSeparator + "java" + sSeparator + "com" + sSeparator + "ecos" + sSeparator + "ecosdeve2" + sSeparator, sSeparator);//m
            resp.getWriter().println("Medicion de LOC");
            resp.getWriter().println("<table class=\"myTable\">");
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>Numero Programa</td>");
            resp.getWriter().println("<td>Tipo de parte</td>");
            resp.getWriter().println("<td>Acceso, tipo, Nombre de parte</td>");
            resp.getWriter().println("<td>Numero de items</td>");
            resp.getWriter().println("<td>Total de la parte</td>");
            resp.getWriter().println("<td>Total tama�o</td>");
            resp.getWriter().println("</tr>");
            agregarPrograma(resp, e1,1);
            agregarPrograma(resp, e2,2);
            resp.getWriter().println("</table>");
        } catch (Exception e) {
            resp.getWriter().print("Ocurrio un Error : " + e.getMessage());
        }
    }
    
    private void agregarPrograma(HttpServletResponse resp,CalcularLoc e1,int numeroPrograma) throws Exception{
        for (int x = 0; x < e1.getNombreClases().size(); x++) {
                resp.getWriter().println("<tr>");
                resp.getWriter().println("<td>"+numeroPrograma+"</td>");
                resp.getWriter().println("<td>Clase</td>");
                resp.getWriter().println("<td>" + e1.getNombreClases().get(x).toString() + "</td>");
                resp.getWriter().println("<td>" + (e1.getNombreMetodos().get(x).size() + e1.getNombreAtributos().get(x).size()) + "</td>");
                resp.getWriter().println("<td>" + e1.getContadorLocClases().get(x).toString() + "</td>");
                resp.getWriter().println("<td></td>");
                resp.getWriter().println("</tr>");
                if (!e1.getNombreAtributos().get(x).isEmpty()) {
                    for (int i = 0; i < e1.getNombreAtributos().get(x).size(); i++) {
                        resp.getWriter().println("<tr>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("<td>Atributo</td>");
                        resp.getWriter().println("<td>               " + e1.getNombreAtributos().get(x).get(i).toString() + "</td>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("</tr>");
                    }
                }
                if (!e1.getNombreMetodos().get(x).isEmpty()) {
                    for (int j = 0; j < e1.getNombreMetodos().get(x).size(); j++) {
                        resp.getWriter().println("<tr>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("<td>Metodo</td>");
                        resp.getWriter().println("<td>                              " + e1.getNombreMetodos().get(x).get(j).toString() + "</td>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("<td></td>");
                        resp.getWriter().println("</tr>");

                    }
                }
            }
            resp.getWriter().println("<tr>");
            resp.getWriter().println("<td>"+numeroPrograma+"</td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("<td></td>");
            resp.getWriter().println("<td>Lineas Eliminadas : " + e1.getContadorLEli() + "</td>");
            resp.getWriter().println("<td>Lineas Modificadas : " + e1.getContadorLMod() + "</td>");
            resp.getWriter().println("<td>Total :" + e1.getContadorLoc().toString() + "</td>");
            resp.getWriter().println("</tr>");
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