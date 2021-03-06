/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cirdles.fileinputstuff;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author RyanBarrett
 */
public class XStreamSerialization {

    public static XStream getXStream() {
        XStream xstream = new XStream();
        xstream.alias("people", People.class);
        xstream.alias("list", ArrayList.class);
        xstream.alias("person", Person.class);
        xstream.registerConverter(new PeopleConverter());
        return xstream;
    }

    //returns list of persons
    public static People getList(File file) {
        XStream xStream = getXStream();
        People list = (People) xStream.fromXML(file);
        return list;
    }

    //makes list of persons on file
    public static void makeList(People list, File file) throws Exception {
        XStream xstream = getXStream();
        FileOutputStream fos = new FileOutputStream(file);
        PrintWriter fileWriter = new PrintWriter(fos);
        fileWriter.print(xstream.toXML(list));
        fileWriter.close();
        fos.close();
    }

    //gets more people from user
    public static void getPeople(String entry, Scanner userInput, People list) {
        while (!entry.equals("q")) {
            if (entry.equals("a")) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(list.get(i).personToString());
                }
            } else if (!entry.equals("")) {
                list.add(getPerson(entry));
            }
            System.out.println("To add new people enter people in the form \"first name, last name, DOB\", enter q to quit,");
            System.out.println("or to  see current people on file enter a:");
            entry = userInput.nextLine();
        }
    }

    //creates a person from a line of code
    public static Person getPerson(String entry) {
        String firstName = entry.substring(0, entry.indexOf(",")).trim();
        String lastName = entry.substring(entry.indexOf(",") + 1, entry.lastIndexOf(",")).trim();
        String DOB = entry.substring(entry.lastIndexOf(",") + 1).trim();
        return new Person(firstName, lastName, DOB);
    }
}