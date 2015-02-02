/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecos.ecosdeve2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dev
 */
public class CalcularLoc {

    private int indexClass=0;
    private int indexMetodos=0;
    private BigInteger contadorLoc;
    private List<BigInteger> contadorClases = new ArrayList<BigInteger>();
    private List<BigInteger> contadorAtributos = new ArrayList<BigInteger>();
    private List<BigInteger> contadorMetodos = new ArrayList<BigInteger>();
    private List<List<String>> nombreClases = new ArrayList<List<String>>();
    private List<List<String>> nombreMetodos = new ArrayList<List<String>>();
    private List<List<List<String>>> parametrosMetodos = new ArrayList<List<List<String>>>();
    private List<List<String>> nombreAtributos = new ArrayList<List<String>>();

    public void leerRuta(String ruta) throws Exception {
        try {
            
            File path = new File(ruta);
            String[] ficheros = path.list();
            FileInputStream fstream;
            DataInputStream entrada = null;
            BufferedReader buffer;
            String strLinea = null;
            if (ficheros == null) {
                System.out.println("No hay ficheros en el directorio especificado");
            } else {

                for (int x = 0; x < ficheros.length; x++) {
                    if (ficheros[x].toLowerCase().contains(".java")) {
                        fstream = new FileInputStream(ficheros[x]);
                        entrada = new DataInputStream(fstream);
                        buffer = new BufferedReader(new InputStreamReader(entrada));
                        while ((strLinea = buffer.readLine()) != null) {
                            SumarVariables(strLinea);
                        }
                    }
                    entrada.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void SumarVariables(String linea) {
        Pattern pt = Pattern.compile("^(?![ \\s]*\\r?\\n|import|package|[ \\s]*}\\r?\\n|[ \\s]*//|[ \\s]*/\\*|[ \\s]*\\*).*\\r?\\n", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pt.matcher(linea);
        while (matcher.find()) {
            contadorLoc.add(BigInteger.ONE);
        }
        pt = Pattern.compile("(?<Access>public\\s|private\\s|protected\\s)(class)+[\\s\\w]*\\s+(?<className>\\w+)", Pattern.CASE_INSENSITIVE);
        matcher = pt.matcher(linea);
        while (matcher.find()) {
            indexClass++;
            indexMetodos=0;
            contadorClases.add(indexClass,contadorClases.get(indexClass).add(BigInteger.ONE));
            nombreClases.get(indexClass).add(matcher.group(0)+" "+matcher.group(1));
        }
        pt = Pattern.compile("(?<Access>public\\s|private\\s|protected\\s)\\s*(?:readonly\\s+)?(?!class)(?<type>[a-zA-Z0-9]*<?[a-zA-Z0-9]*>?+)\\s+(?<AtrrName>\\w+)", Pattern.CASE_INSENSITIVE);
        matcher = pt.matcher(linea);
        while (matcher.find()) {
            contadorAtributos.add(indexClass,contadorAtributos.get(indexClass).add(BigInteger.ONE));
            nombreAtributos.get(indexClass).add(matcher.group(0)+" "+matcher.group(1)+" "+matcher.group(2));
        }
        pt = Pattern.compile("(?<Access>public\\s|private\\s|protected\\s|internal\\s)?(?<return>[\\s\\w]*)\\s+(?<methodName>\\w+)\\s*\\(\\s*(?:(ref\\s|in\\s|out\\s)?\\s*(?<parameterType>\\w+)\\s+(?<parameter>\\w+)\\s*,?\\s*)+\\)", Pattern.CASE_INSENSITIVE);
        matcher = pt.matcher(linea);
        while (matcher.find()) {
            indexMetodos++;
            contadorMetodos.add(indexClass,contadorMetodos.get(indexClass).add(BigInteger.ONE));
            nombreMetodos.get(indexClass).add(matcher.group(0)+" "+matcher.group(1)+" "+matcher.group(2));
            parametrosMetodos.get(indexClass).get(indexMetodos).add(matcher.group(3)+" "+matcher.group(4));
        }
    }

    public BigInteger getContadorLoc() {
        return contadorLoc;
    }

    public void setContadorLoc(BigInteger contadorLoc) {
        this.contadorLoc = contadorLoc;
    }



    public List<BigInteger> getContadorClases() {
        return contadorClases;
    }

    public void setContadorClases(List<BigInteger> contadorClases) {
        this.contadorClases = contadorClases;
    }

    public List<BigInteger> getContadorAtributos() {
        return contadorAtributos;
    }

    public void setContadorAtributos(List<BigInteger> contadorAtributos) {
        this.contadorAtributos = contadorAtributos;
    }

    public List<BigInteger> getContadorMetodos() {
        return contadorMetodos;
    }

    public void setContadorMetodos(List<BigInteger> contadorMetodos) {
        this.contadorMetodos = contadorMetodos;
    }

    public List<List<String>> getNombreClases() {
        return nombreClases;
    }

    public void setNombreClases(List<List<String>> nombreClases) {
        this.nombreClases = nombreClases;
    }

    public List<List<String>> getNombreMetodos() {
        return nombreMetodos;
    }

    public void setNombreMetodos(List<List<String>> nombreMetodos) {
        this.nombreMetodos = nombreMetodos;
    }

    public List<List<List<String>>> getParametrosMetodos() {
        return parametrosMetodos;
    }

    public void setParametrosMetodos(List<List<List<String>>> parametrosMetodos) {
        this.parametrosMetodos = parametrosMetodos;
    }

    public List<List<String>> getNombreAtributos() {
        return nombreAtributos;
    }

    public void setNombreAtributos(List<List<String>> nombreAtributos) {
        this.nombreAtributos = nombreAtributos;
    } 

    public int getIndexClass() {
        return indexClass;
    }

    public void setIndexClass(int indexClass) {
        this.indexClass = indexClass;
    }

    public int getIndexMetodos() {
        return indexMetodos;
    }

    public void setIndexMetodos(int indexMetodos) {
        this.indexMetodos = indexMetodos;
    }
    
}
