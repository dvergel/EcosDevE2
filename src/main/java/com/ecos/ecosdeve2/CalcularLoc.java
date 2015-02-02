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

    private int indexClass = -1;
    private int indexMetodos = -1;
    private BigInteger contadorLMod = BigInteger.ZERO;
    private BigInteger contadorLEli = BigInteger.ZERO;
    private BigInteger contadorLoc = BigInteger.ZERO;
    private List<BigInteger> contadorLocClases = new ArrayList<BigInteger>();
    private List<BigInteger> contadorClases = new ArrayList<BigInteger>();
    private List<BigInteger> contadorAtributos = new ArrayList<BigInteger>();
    private List<BigInteger> contadorMetodos = new ArrayList<BigInteger>();
    private List<List<String>> nombreClases = new ArrayList<List<String>>();
    private List<List<String>> nombreMetodos = new ArrayList<List<String>>();
    private List<List<List<String>>> parametrosMetodos = new ArrayList<List<List<String>>>();
    private List<List<String>> nombreAtributos = new ArrayList<List<String>>();

    public void leerRuta(String ruta, String separador) throws Exception {
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
                        fstream = new FileInputStream(ruta + separador + ficheros[x]);//m
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

    private void SumarVariables(String linea) {
        boolean flag = true;
        Pattern pt = Pattern.compile("^(?![ \\s]*\\r?\\n|import|package|[ \\s]*}\\r?\\n|[ \\s]*//|[ \\s]*/\\*|[ \\s]*\\*).*\\r?\\n", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pt.matcher(linea);
        while (matcher.find()) {
            contadorLocClases.set(indexClass, contadorLocClases.get(indexClass).add(BigInteger.ONE));
            contadorLoc.add(BigInteger.ONE);
        }
        if (flag) {
            pt = Pattern.compile("(public\\s|private\\s|protected\\s)(class)+([\\s\\w]*\\s)+(\\w+)", Pattern.CASE_INSENSITIVE);
            matcher = pt.matcher(linea);
            while (matcher.find()) {
                indexClass++;
                nombreClases.add(new ArrayList<String>());
                nombreAtributos.add(new ArrayList<String>());
                nombreMetodos.add(new ArrayList<String>());
                contadorClases.add(BigInteger.ZERO);
                contadorAtributos.add(BigInteger.ZERO);
                contadorMetodos.add(BigInteger.ZERO);
                parametrosMetodos.add(new ArrayList<List<String>>());
                indexMetodos = 0;
                contadorClases.add(indexClass, contadorClases.get(indexClass).add(BigInteger.ONE));
                nombreClases.get(indexClass).add(matcher.group(1) + matcher.group(2)+ " " + matcher.group(4));
                flag = false;
            }
        }
        if (flag) {
            pt = Pattern.compile("(public\\s|private\\s|protected\\s)\\s*(?:readonly\\s+)?(?!class)([a-zA-Z0-9]*<?[a-zA-Z0-9]*>?+)\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
            matcher = pt.matcher(linea);
            while (matcher.find()) {
                contadorAtributos.add(indexClass, contadorAtributos.get(indexClass).add(BigInteger.ONE));
                nombreAtributos.get(indexClass).add(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
                flag = false;
            }
        }
        if (flag) {
            pt = Pattern.compile("(public\\s|private\\s|protected\\s|internal\\s)?([\\s\\w]*)\\s+(\\w+)\\s*\\(\\s*(?:(ref\\s|in\\s|out\\s)?\\s*(\\w+)\\s+(\\w+)\\s*,?\\s*)+\\)", Pattern.CASE_INSENSITIVE);
            matcher = pt.matcher(linea);
            while (matcher.find()) {
                indexMetodos++;
                contadorMetodos.add(indexClass, contadorMetodos.get(indexClass).add(BigInteger.ONE));
                nombreMetodos.get(indexClass).add(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
                parametrosMetodos.get(indexClass).get(indexMetodos).add(matcher.group(4) + " " + matcher.group(5));
                flag = false;
            }
        }
        if (linea.toLowerCase().contains("//m")) {
            contadorLMod.add(BigInteger.ONE);
        }
        if (linea.toLowerCase().contains("//e")) {
            contadorLEli.add(BigInteger.ONE);
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

    public List<BigInteger> getContadorLocClases() {
        return contadorLocClases;
    }

    public void setContadorLocClases(List<BigInteger> contadorLocClases) {
        this.contadorLocClases = contadorLocClases;
    }

    public BigInteger getContadorLMod() {
        return contadorLMod;
    }

    public void setContadorLMod(BigInteger contadorLMod) {
        this.contadorLMod = contadorLMod;
    }

    public BigInteger getContadorLEli() {
        return contadorLEli;
    }

    public void setContadorLEli(BigInteger contadorLEli) {
        this.contadorLEli = contadorLEli;
    }
}
