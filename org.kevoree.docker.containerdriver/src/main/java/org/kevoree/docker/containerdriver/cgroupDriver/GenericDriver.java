package org.kevoree.docker.containerdriver.cgroupDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by aymeric on 28/11/14.
 */
public class GenericDriver {

    public static String ReadValue(String ContainerId, String Subsystem, String file){

       String fileUri = CgroupStructure.cGroupURI + "/" + Subsystem + "/docker/" + ContainerId + "/" + file;

        String value = "";
        File f = new File(fileUri) ;
        if(f.canRead())
        {
            try {
                value =  new String(Files.readAllBytes(Paths.get(f.toURI())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return value ;
    }


    public static void SetValue(String ContainerId, String Subsystem, String file,String Value){

        String fileUri = CgroupStructure.cGroupURI + "/" + Subsystem + "/docker/" + ContainerId + "/" + file;

        String value = "";
        File f = new File(fileUri) ;
        if(f.canRead())
        {
            try {
                Files.write(Paths.get(f.toURI()), value.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
