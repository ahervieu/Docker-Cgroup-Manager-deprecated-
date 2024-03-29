package org.kevoree.docker.containerdriver.cgroupDriver;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.kevoree.docker.containerdriver.cgroupDriver.CgroupStructure ;

/**
 * Created by aymeric on 27/11/14.
 */
 public class  CGroupReader {


    public static String readInfo(String subsystem, String fileName ,String Id) {
        File f = new File(CgroupStructure.cGroupURI + "/" + subsystem + "/docker/" + Id + "/" + fileName) ;
        String res = "" ;
        if(f.canRead()){
        try {
          res = new String(Files.readAllBytes(Paths.get(CgroupStructure.cGroupURI + "/" + subsystem + "/docker/" + Id + "/" + fileName))) ;
        } catch (IOException e) {
            System.err.println("Unable to read file " + fileName);
            e.printStackTrace();
        }
        }
        return res ;
    }


}
