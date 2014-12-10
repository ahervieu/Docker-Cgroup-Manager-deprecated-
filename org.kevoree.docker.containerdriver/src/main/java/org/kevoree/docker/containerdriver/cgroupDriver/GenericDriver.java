package org.kevoree.docker.containerdriver.cgroupDriver;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by aymeric on 28/11/14.
 */
public class GenericDriver {

    @NotNull
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
        System.out.println("Reading value : " + ContainerId + " /" + Subsystem + "/" +  file +" : " + value ) ;
        return value ;
    }


    public static void SetValue(String ContainerId, String Subsystem, String file,String Value){
        System.out.println("Reading value : " + ContainerId + " /" + Subsystem + "/" +  file +" : " + Value) ;
        String fileUri = CgroupStructure.cGroupURI + "/" + Subsystem + "/docker/" + ContainerId + "/" + file;
        DataOutputStream os = null;
        String value = "";
        try {
            Process p ;
            p = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("echo '"+Value + "' > " + fileUri );
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}