package org.kevoree.docker.containerdriver.cgroupDriver;

import org.kevoree.docker.containerdriver.model.CommitConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by aymeric on 27/11/14.
 */
public class BlkioDriver {


    public static String getWriteValue(String containerId) {

       String value = GenericDriver.ReadValue(containerId,CgroupStructure.blkio_subsystem,CgroupStructure.blkio_write) ;

                if(!value.isEmpty())
                {
                    value = value.split(" ")[1] ;
                }

        return value ;
    }


    public static String getReadValue(String containerId) {
        String value = GenericDriver.ReadValue(containerId,CgroupStructure.blkio_subsystem,CgroupStructure.blkio_read) ;

        if(!value.isEmpty())
        {
            value = value.split(" ")[1] ;
        }

        return value ;
        }

    public static void setReadValue(String containerId, String value) {
        value = "8:0 " + value ;
        GenericDriver.SetValue(containerId,CgroupStructure.blkio_subsystem,CgroupStructure.blkio_read,value) ;
    }

    public static void setWriteValue(String containerId, String value) {
        value = "8:0 " + value ;
        GenericDriver.SetValue(containerId,CgroupStructure.blkio_subsystem,CgroupStructure.blkio_write,value) ;
    }


}
